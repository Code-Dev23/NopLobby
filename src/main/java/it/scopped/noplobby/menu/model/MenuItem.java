package it.scopped.noplobby.menu.model;

import it.scopped.noplobby.utilities.Messages;
import lombok.Data;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuItem {
    private ItemStack itemStack;

    private int slot;
    private Material material;
    private int amount;
    private boolean glow;
    private String displayName;
    private List<String> lore;
    private List<String> rightActions;
    private List<String> leftActions;

    public MenuItem(Material material, int slot, int amount) {
        this.amount = amount;
        this.slot = slot;
        this.material = material;
        this.glow = false;
        this.lore = new ArrayList<>();
        this.rightActions = new ArrayList<>();
        this.leftActions = new ArrayList<>();
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        meta.setLore(Bukkit.getOnlinePlayers().stream()
                .flatMap(player -> PlaceholderAPI.setPlaceholders(player, lore.stream().map(Messages::color).toList()).stream())
                .toList());
        meta.setDisplayName(Messages.color(displayName));
        if (glow) {
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else
            meta.removeEnchant(Enchantment.ARROW_INFINITE);

        item.setItemMeta(meta);
        return item;
    }
}