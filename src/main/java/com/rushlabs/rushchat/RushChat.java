package com.rushlabs.rushchat;

import com.rushlabs.rushchat.command.RushChatCommand;
import com.rushlabs.rushchat.config.ConfigManager;
import com.rushlabs.rushchat.listener.ChatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RushChat extends JavaPlugin {

    private static RushChat instance;
    private ConfigManager configManager;
    private boolean chatLocked = false;

    @Override
    public void onEnable() {
        instance = this;

        // Check for PAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("PlaceholderAPI not found! RushChat functionality will be limited.");
        }

        // Initialize Config
        this.configManager = new ConfigManager(this);
        this.configManager.loadConfigs();

        // Register Commands
        getCommand("rushchat").setExecutor(new RushChatCommand(this));

        // Register Listeners
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        getLogger().info("RushChat 1.0 enabled successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RushChat disabled.");
    }

    public static RushChat getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public boolean isChatLocked() {
        return chatLocked;
    }

    public void setChatLocked(boolean chatLocked) {
        this.chatLocked = chatLocked;
    }
}