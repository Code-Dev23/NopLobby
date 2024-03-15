package it.scopped.noplobby.hotbar.struct.listeners;

import it.scopped.noplobby.NopLobby;
import it.scopped.noplobby.utilities.actions.ActionExecutor;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class HotBarListeners implements Listener {
    private final NopLobby lobby;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack clickedItem = event.getItem();
        if (clickedItem == null) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            lobby.getHotBarManager().getHotbarItems().forEach((slot, item) -> {
                if (item.getMaterial() != clickedItem.getType())
                    return;
                item.getRightActions().forEach(action -> ActionExecutor.executeAction(player, action));
            });
            return;
        }
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            lobby.getHotBarManager().getHotbarItems().forEach((slot, item) -> {
                if (item.getMaterial() != clickedItem.getType())
                    return;
                item.getLeftActions().forEach(action -> ActionExecutor.executeAction(player, action));
            });
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        lobby.getHotBarManager().setHotbarItems(player);

        event.setJoinMessage(null);
    }
}
