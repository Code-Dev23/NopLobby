package it.scopped.noplobby;

import co.aikar.commands.PaperCommandManager;
import it.scopped.noplobby.hotbar.struct.HotBarManager;
import it.scopped.noplobby.hotbar.struct.listeners.HotBarListeners;
import it.scopped.noplobby.menu.struct.MenuManager;
import it.scopped.noplobby.menu.struct.listeners.MenuListeners;
import it.scopped.noplobby.utilities.config.YamlFile;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public final class NopLobby extends JavaPlugin {

    public static final Logger LOGGER = Bukkit.getLogger();

    @Getter
    private static NopLobby instance;

    private YamlFile yamlFile;
    private HotBarManager hotBarManager;
    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;
        checkDependencies();
        init();

        loadCommands();
        loadListeners();

    }

    @Override
    public void onDisable() {

    }

    public void init() {
        yamlFile = new YamlFile(instance);

        hotBarManager = new HotBarManager(instance);
        hotBarManager.loadAllItems();

        menuManager = new MenuManager(instance);
        menuManager.loadAllMenus();
    }

    private void loadCommands() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(instance);

    }

    private void loadListeners() {
        PluginManager eventManager = getServer().getPluginManager();

        eventManager.registerEvents(new HotBarListeners(instance), this);
        eventManager.registerEvents(new MenuListeners(instance), this);
    }

    private void checkDependencies() {
        PluginManager pluginManager = getServer().getPluginManager();
        if (pluginManager.getPlugin("PlaceholderAPI") == null || !pluginManager.isPluginEnabled("PlaceholderAPI")) {
            LOGGER.severe("PlaceholderAPI Dependency not found!");
            pluginManager.disablePlugin(this);
        }
    }
}