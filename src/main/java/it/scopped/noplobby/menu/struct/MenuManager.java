package it.scopped.noplobby.menu.struct;

import it.scopped.noplobby.NopLobby;
import it.scopped.noplobby.menu.model.CustomMenu;
import it.scopped.noplobby.menu.model.MenuItem;
import it.scopped.noplobby.utilities.Messages;
import it.scopped.noplobby.utilities.common.Utils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;

@RequiredArgsConstructor
public class MenuManager {
    private final NopLobby lobby;

    @Getter
    private final HashMap<String, CustomMenu> menus = new HashMap<>();

    public void loadAllMenus() {
        ConfigurationSection section = lobby.getYamlFile().getMenus().getConfigurationSection("menus");
        if (section == null) {
            NopLobby.LOGGER.warning("0 Menus loaded.");
            return;
        }
        section.getKeys(false).forEach(menuName -> {
            ConfigurationSection menuSection = section.getConfigurationSection(menuName);
            if (menuSection != null) {
                CustomMenu customMenu = new CustomMenu(menuSection.getString("title"), Utils.parseInt(menuSection.getString("rows")));
                loadMenuItems(customMenu, menuSection);
                menus.put(menuName, customMenu);
            } else {
                NopLobby.LOGGER.severe("Menu " + menuName + " has no configuration section.");
            }
        });
    }

    private void loadMenuItems(CustomMenu customMenu, ConfigurationSection menuSection) {
        ConfigurationSection itemsSection = menuSection.getConfigurationSection("items");
        if (itemsSection == null) {
            NopLobby.LOGGER.severe("Menu " + customMenu.getTitle() + " has no items configuration section.");
            return;
        }

        itemsSection.getKeys(false).forEach(slot -> {
            ConfigurationSection itemSection = itemsSection.getConfigurationSection(slot);
            if (itemSection != null) {
                MenuItem customItem = new MenuItem(
                        Utils.parseMaterial(itemSection.getString("type")),
                        Utils.parseInt(slot),
                        Utils.parseInt(itemSection.getString("amount"))
                );

                customItem.setGlow(itemSection.getBoolean("glow"));
                customItem.setDisplayName(itemSection.getString("display_name"));
                customItem.setLore(itemSection.getStringList("lore"));
                customItem.setLeftActions(itemSection.getStringList("actions.left_click"));
                customItem.setRightActions(itemSection.getStringList("actions.right_click"));

                customMenu.getItems().put(Utils.parseInt(slot), customItem);
            } else {
                NopLobby.LOGGER.severe("Item " + slot + " in menu " + customMenu.getTitle() + " has no configuration section.");
            }
        });
    }

    public void openToPlayer(Player player, String menuName) {
        CustomMenu customMenu = menus.get(menuName);
        if (customMenu == null) {
            Messages.send(player, "&cMenu not found!");
            return;
        }
        customMenu.showMenu(player);
    }
}