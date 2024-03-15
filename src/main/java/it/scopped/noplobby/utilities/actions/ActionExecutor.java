package it.scopped.noplobby.utilities.actions;

import it.scopped.noplobby.NopLobby;
import it.scopped.noplobby.utilities.Messages;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@UtilityClass
public class ActionExecutor {
    public void executeAction(Player player, String action) {
        if (action.startsWith("[console]")) {
            String command = action.replace("[console] ", "");

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        } else if (action.startsWith("[command]")) {
            String command = action.replace("[command] ", "");

            Bukkit.dispatchCommand(player, command);
        } else if (action.startsWith("[message]")) {
            String message = action.replace("[message] ", "");

            Messages.send(player, message);
        } else if (action.startsWith("[openmenu]")) {
            String menuName = action.replace("[openmenu] ", "").replaceAll(" ", "-");

            System.out.println(menuName);
            NopLobby.getInstance().getMenuManager().openToPlayer(player, menuName);
        } else if (action.startsWith("[closemenu]")) {
            player.closeInventory();
        } else if (action.startsWith("[server]")) {
            String serverName = action.replace("[server] ", "").replaceAll(" ", "-");

            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream)) {
                dataOutputStream.writeUTF("Connect");
                dataOutputStream.writeUTF(serverName);
            } catch (IOException exception) {
                NopLobby.LOGGER.warning("An error occurred while sending player " + player.getName() + " to the server " + serverName);
            }
        }
    }
}