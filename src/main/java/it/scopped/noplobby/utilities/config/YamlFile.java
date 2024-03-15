package it.scopped.noplobby.utilities.config;

import it.scopped.noplobby.NopLobby;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class YamlFile {
    private final FileConfiguration config;
    private final FileConfiguration messages;
    private final FileConfiguration hotbar;
    private final FileConfiguration menus;

    private final NopLobby main;

    public YamlFile(NopLobby main) {
        this.main = main;

        this.config = saveConfig("config.yml");
        this.messages = saveConfig("messages.yml");
        this.hotbar = saveConfig("hotbar.yml");
        this.menus = saveConfig("menus.yml");
    }

    public void saveFile(FileConfiguration configuration, File file) {
        try {
            configuration.save(file);
        } catch (IOException exception) {
            NopLobby.LOGGER.warning("An error occurred while trying to save the config " + file.getName() + "! Error: " + exception.getMessage());
        }
    }

    private FileConfiguration saveConfig(String configName) {
        File file = new File(main.getDataFolder(), configName);

        if (!file.exists()) {
            main.saveResource(configName, false);
        }

        return loadConfig(file);
    }

    public FileConfiguration loadConfig(File file) {
        YamlConfiguration configuration = new YamlConfiguration();

        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            NopLobby.LOGGER.warning("An error occurred while trying to load the config " + file.getName() + "! Error: " + exception.getMessage());
        }

        return configuration;
    }
}