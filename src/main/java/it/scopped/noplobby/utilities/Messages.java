package it.scopped.noplobby.utilities;

import it.scopped.noplobby.NopLobby;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class Messages {
    public String color(String text) {
        text = ChatColor.translateAlternateColorCodes('&', text);
        text = hex(text);
        return text;
    }

    public String hex(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch)
                builder.append("&" + c);

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void send(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

    public void send(CommandSender sender, String... messages) {
        for (String s : messages)
            send(sender, s);
    }

    public void sendTitle(Player player, String title, String subTitle) {
        player.sendTitle(color(title), color(subTitle));
    }

    public void sendActionBar(Player player, String text) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(color(text)));
    }

    public String getMessage(String path) {
        return color(NopLobby.getInstance().getYamlFile().getMessages().getString(path));
    }
}