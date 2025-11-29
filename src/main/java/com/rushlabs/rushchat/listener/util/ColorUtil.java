package com.rushlabs.rushchat.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    /**
     * Translates Hex (&#123456) and Legacy (&a) codes into an Adventure Component.
     */
    public static Component parse(String text) {
        if (text == null) return Component.empty();
        
        // 1. Convert &#RRGGBB to &x&R&R&G&G&B&B (Bukkit standard for legacy hex)
        // OR convert to MiniMessage format <#RRGGBB>
        // For simplicity and compatibility with PAPI outputs, we use LegacySerializer with Hex support.
        
        String translated = translateHex(text);
        return LegacyComponentSerializer.legacyAmpersand().deserialize(translated);
    }

    private static String translateHex(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuilder buffer = new StringBuilder(message.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, "&x&" + group.charAt(0) + "&" + group.charAt(1) 
                    + "&" + group.charAt(2) + "&" + group.charAt(3) 
                    + "&" + group.charAt(4) + "&" + group.charAt(5));
        }
        return matcher.appendTail(buffer).toString();
    }
}