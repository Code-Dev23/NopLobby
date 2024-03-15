package it.scopped.noplobby.menu.struct.listeners;

import it.scopped.noplobby.NopLobby;
import it.scopped.noplobby.utilities.actions.ActionExecutor;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@RequiredArgsConstructor
public class MenuListeners implements Listener {
    private final NopLobby lobby;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null)
            return;
        event.setCancelled(true);
        if (event.isRightClick()) {
            lobby.getMenuManager().getMenus().values().forEach(menu -> {
                if (!menu.isSimilar(Objects.requireNonNull(event.getClickedInventory())))
                    return;
                menu.getItems().forEach((slot, item) -> {
                    if (item.getMaterial() != clickedItem.getType())
                        return;
                    item.getRightActions().forEach(action -> ActionExecutor.executeAction(player, action));
                });
            });
            return;
        }
        if (event.isLeftClick()) {
            lobby.getMenuManager().getMenus().values().forEach(menu -> {
                if (!menu.isSimilar(Objects.requireNonNull(event.getClickedInventory())))
                    return;
                menu.getItems().forEach((slot, item) -> {
                    if (item.getMaterial() != clickedItem.getType())
                        return;
                    item.getLeftActions().forEach(action -> ActionExecutor.executeAction(player, action));
                });
            });
        }
    }
}