package it.scopped.noplobby.hotbar.struct;

import com.google.common.collect.Maps;
import it.scopped.noplobby.NopLobby;
import it.scopped.noplobby.hotbar.model.HotBarItem;
import it.scopped.noplobby.utilities.common.Utils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;

@RequiredArgsConstructor
public class HotBarManager {
    @Getter
    private final HashMap<Integer, HotBarItem> hotbarItems = Maps.newHashMap();
    private final NopLobby lobby;

    public void setHotbarItems(Player player) {
        hotbarItems.forEach((slot, customItem) -> player.getInventory().setItem(slot, customItem.build()));
    }

    public void loadAllItems() {
        ConfigurationSection section = lobby.getYamlFile().getHotbar().getConfigurationSection("items");
        if (section == null) {
            NopLobby.LOGGER.warning("0 HotBar Items loaded.");
            return;
        }
        section.getKeys(false).forEach(key -> {
            int slot = Utils.parseInt(key);
            Material material = Utils.parseMaterial(section.getString(key + ".type"));
            int amount = Utils.parseInt(section.getString(key + ".amount"));
            boolean glow = section.getBoolean(key + ".glow");
            HotBarItem customItem = new HotBarItem(material, slot, amount);
            customItem.setLore(section.getStringList(key + ".lore"));
            customItem.setDisplayName(section.getString(key + ".display_name"));
            customItem.setGlow(glow);
            customItem.setLeftActions(section.getStringList(key + ".actions.left_click"));
            customItem.setRightActions(section.getStringList(key + ".actions.right_click"));

            hotbarItems.put(slot, customItem);
        });
    }
}