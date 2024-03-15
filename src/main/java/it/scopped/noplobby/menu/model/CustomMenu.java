package it.scopped.noplobby.menu.model;

import com.google.common.collect.Maps;
import it.scopped.noplobby.utilities.Messages;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Data
public class CustomMenu {
    private Inventory bukkitInv;
    private String title;
    private int rows;
    private HashMap<Integer, MenuItem> items;

    public CustomMenu(String title, int rows) {
        this.title = title;
        this.rows = rows * 9;
        this.items = Maps.newHashMap();
    }

    public Inventory build() {
        if (rows > 54)
            rows = 54;
        else if (rows < 9)
            rows = 9;
        bukkitInv = Bukkit.createInventory(null, rows, Messages.color(title));
        items.forEach((slot, item) -> bukkitInv.setItem(slot, item.build()));

        return bukkitInv;
    }

    public boolean isSimilar(Inventory secondInv) {
        if (this.build().getSize() != secondInv.getSize()) return false;
        if (!Objects.equals(this.build().getHolder(), secondInv.getHolder())) return false;

        List<ItemStack> firstInvItems = Arrays.stream(this.build().getContents()).toList();
        List<ItemStack> secondInvItems = Arrays.stream(secondInv.getContents()).toList();

        for (int i = 0; i < firstInvItems.size(); i++) {
            if (firstInvItems.get(i) == null || secondInvItems.get(i) == null) break;

            if (!firstInvItems.get(i).isSimilar(secondInvItems.get(i))) return false;
        }
        return true;
    }

    public void showMenu(Player player) {
        if (this.build() != null)
            player.openInventory(this.build());
    }
}