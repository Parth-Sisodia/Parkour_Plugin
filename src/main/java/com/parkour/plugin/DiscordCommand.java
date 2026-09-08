package com.parkour.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DiscordCommand implements CommandExecutor {

    private static final String DISCORD_LINK = "https://discord.gg/eqGF3vfUF";

    private final Main plugin;

    public DiscordCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Component message = Component.text("Join our Discord server: ", NamedTextColor.GREEN)
                .append(Component.text(DISCORD_LINK)
                        .textColor(NamedTextColor.AQUA)
                        .decorate(TextDecoration.UNDERLINE)
                        .clickEvent(ClickEvent.openUrl(DISCORD_LINK))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to open the link!", NamedTextColor.YELLOW))));

        sender.sendMessage(message);
        return true;
    }
}
