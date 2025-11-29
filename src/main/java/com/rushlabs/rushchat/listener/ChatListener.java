package com.rushlabs.rushchat.listener;

import com.rushlabs.rushchat.RushChat;
import com.rushlabs.rushchat.util.ColorUtil;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;

public class ChatListener implements Listener {

    private final RushChat plugin;

    public ChatListener(RushChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        // 1. Check Lock
        if (plugin.isChatLocked() && !player.hasPermission("rushchat.admin")) {
            event.setCancelled(true);
            player.sendMessage(ColorUtil.parse(plugin.getConfigManager().getLockedMessage()));
            return;
        }

        // 2. Define Renderer
        // We use a custom lambda renderer to construct the message dynamically for each viewer
        // This is strictly async as per AsyncChatEvent.
        event.renderer((source, sourceDisplayName, message, viewer) -> {
            
            // --- Get Format Strings ---
            String rawFormat = plugin.getConfigManager().getChatFormat();
            List<String> rawHover = plugin.getConfigManager().getHoverMessage();
            String rawAction = plugin.getConfigManager().getClickAction();

            // --- Parse Placeholders (Async) ---
            String parsedFormat = PlaceholderAPI.setPlaceholders(source, rawFormat);

            // --- Create the Prefix Component (The part before the message) ---
            Component prefixComponent = ColorUtil.parse(parsedFormat);

            // --- Add Interactions (Hover/Click) ---
            // 1. Hover
            if (!rawHover.isEmpty()) {
                StringBuilder hoverText = new StringBuilder();
                for (int i = 0; i < rawHover.size(); i++) {
                    hoverText.append(rawHover.get(i));
                    if (i < rawHover.size() - 1) hoverText.append("\n");
                }
                String parsedHover = PlaceholderAPI.setPlaceholders(source, hoverText.toString());
                prefixComponent = prefixComponent.hoverEvent(HoverEvent.showText(ColorUtil.parse(parsedHover)));
            }

            // 2. Click
            if (rawAction != null && !rawAction.isEmpty()) {
                String parsedAction = PlaceholderAPI.setPlaceholders(source, rawAction);
                String[] parts = parsedAction.split(":", 2);
                if (parts.length == 2) {
                    try {
                        ClickEvent.Action actionType = ClickEvent.Action.valueOf(parts[0].toUpperCase());
                        prefixComponent = prefixComponent.clickEvent(ClickEvent.clickEvent(actionType, parts[1]));
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }
            return prefixComponent.append(message);
        });
    }
}