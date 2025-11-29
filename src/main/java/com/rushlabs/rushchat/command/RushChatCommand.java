package com.rushlabs.rushchat.command;

import com.rushlabs.rushchat.RushChat;
import com.rushlabs.rushchat.util.ColorUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RushChatCommand implements CommandExecutor {

    private final RushChat plugin;

    public RushChatCommand(RushChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("rushchat.admin")) {
            sender.sendMessage(ColorUtil.parse("&cYou do not have permission."));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ColorUtil.parse("&eRushChat Help: /rushchat <reload|clear|lock>"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.getConfigManager().loadConfigs();
                sender.sendMessage(ColorUtil.parse("&aRushChat configuration reloaded."));
                break;

            case "clear":
                int lines = plugin.getConfigManager().getClearLines();
                for (int i = 0; i < lines; i++) {
                    Bukkit.broadcast(Component.text(""));
                }
                Bukkit.broadcast(ColorUtil.parse(plugin.getConfigManager().getClearMessage()));
                break;

            case "lock":
                boolean isLocked = !plugin.isChatLocked();
                plugin.setChatLocked(isLocked);
                String status = isLocked ? "&cLOCKED" : "&aUNLOCKED";
                Bukkit.broadcast(ColorUtil.parse("&7Chat has been " + status + " &7by an administrator."));
                break;

            default:
                sender.sendMessage(ColorUtil.parse("&cUnknown subcommand."));
                break;
        }

        return true;
    }
}