package de.shiirroo.islands.gamedata.game;

import org.bukkit.ChatColor;

public enum Rarity {
    COMMON(ChatColor.WHITE,0,34),
    UNCOMMON(ChatColor.GREEN,35,54),
    RARE(ChatColor.BLUE,55,74),
    EPIC(ChatColor.DARK_PURPLE,75,89),
    LEGENDARY(ChatColor.GOLD, 90, 98),
    MYTHIC(ChatColor.LIGHT_PURPLE, 99, 100);

   private final ChatColor chatColor;
   private final Integer min;
   private final Integer max;

    Rarity(ChatColor chatColor, Integer min, Integer max){
        this.chatColor = chatColor;
        this.min = min;
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }
}
