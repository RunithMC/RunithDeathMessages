package net.runith.death.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.List;

@UtilityClass
public final class MessageColor {

    public static String color(final Object object) {
        if (object == null) {
            return null;
        }
        String message = object.toString();

        if (object instanceof List<?> list) {
            final StringBuilder builder = new StringBuilder();
            int size = list.size();
            int i = 0;
            for (final Object object2 : list) {
                builder.append(object2);
                if (++i == size) {
                    continue;
                }
                builder.append('\n');
            }
            message = builder.toString();
        }

        return message.isBlank()
            ? null
            : ChatColor.translateAlternateColorCodes('&', message);
    }
}