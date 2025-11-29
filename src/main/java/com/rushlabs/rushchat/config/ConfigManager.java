package com.rushlabs.rushchat.config;

import com.rushlabs.rushchat.RushChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {

    private final RushChat plugin;
    private File formatsFile;
    private FileConfiguration formatsConfig;

    public ConfigManager(RushChat plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        // Load default config.yml
        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        // Load formats/format.yml
        formatsFile = new File(plugin.getDataFolder(), "formats/format.yml");
        if (!formatsFile.exists()) {
            // Save resource from jar if not exists
            File formatsDir = new File(plugin.getDataFolder(), "formats");
            if (!formatsDir.exists()) formatsDir.mkdirs();
            plugin.saveResource("formats/format.yml", false);
        }
        formatsConfig = YamlConfiguration.loadConfiguration(formatsFile);
    }

    public FileConfiguration getFormatsConfig() {
        return formatsConfig;
    }
    
    // Config Getters
    public String getChatFormat() {
        return formatsConfig.getString("chat-format", "%player_name% : ");
    }

    public List<String> getHoverMessage() {
        return formatsConfig.getStringList("hover-message");
    }

    public String getClickAction() {
        return formatsConfig.getString("click-action", "");
    }
    
    public int getClearLines() {
        return plugin.getConfig().getInt("clear-chat-lines", 100);
    }
    
    public String getClearMessage() {
        return plugin.getConfig().getString("clear-chat-message", "Chat cleared.");
    }
    
    public String getLockedMessage() {
        return plugin.getConfig().getString("chat-locked-message", "Chat is locked.");
    }
}