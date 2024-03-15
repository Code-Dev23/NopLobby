package it.scopped.noplobby.utilities.common;

import it.scopped.noplobby.NopLobby;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;

@UtilityClass
public class Utils {
    public int parseInt(String str) {
        int value;
        try {
            value = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            NopLobby.LOGGER.severe("Value not found! Required a integer, key: " + str);
            return 0;
        }
        return value;
    }

    public Material parseMaterial(String str) {
        Material material = Material.getMaterial(str);
        if (material == null) {
            NopLobby.LOGGER.severe("Value not found! Required a valid block or item type, key: " + str);
            return Material.AIR;
        }
        return material;
    }
}