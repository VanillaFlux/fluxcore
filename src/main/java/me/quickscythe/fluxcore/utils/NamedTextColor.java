package me.quickscythe.fluxcore.utils;

import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

public enum NamedTextColor {
    BLACK(TextColor.fromFormatting(Formatting.BLACK)),
    DARK_BLUE(TextColor.fromFormatting(Formatting.DARK_BLUE)),
    DARK_GREEN(TextColor.fromFormatting(Formatting.DARK_GREEN)),
    DARK_AQUA(TextColor.fromFormatting(Formatting.DARK_AQUA)),
    DARK_RED(TextColor.fromFormatting(Formatting.DARK_RED)),
    DARK_PURPLE(TextColor.fromFormatting(Formatting.DARK_PURPLE)),
    GOLD(TextColor.fromFormatting(Formatting.GOLD)),
    GRAY(TextColor.fromFormatting(Formatting.GRAY)),
    DARK_GRAY(TextColor.fromFormatting(Formatting.DARK_GRAY)),
    BLUE(TextColor.fromFormatting(Formatting.BLUE)),
    GREEN(TextColor.fromFormatting(Formatting.GREEN)),
    AQUA(TextColor.fromFormatting(Formatting.AQUA)),
    RED(TextColor.fromFormatting(Formatting.RED)),
    LIGHT_PURPLE(TextColor.fromFormatting(Formatting.LIGHT_PURPLE)),
    YELLOW(TextColor.fromFormatting(Formatting.YELLOW)),
    WHITE(TextColor.fromFormatting(Formatting.WHITE));

    private final TextColor color;

    NamedTextColor(TextColor color) {
        this.color = color;
    }

    public TextColor color() {
        return color;
    }
}
