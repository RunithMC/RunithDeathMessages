package net.runith.death.command;

import lombok.RequiredArgsConstructor;
import net.runith.death.config.ConfigLoader;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public final class DeathMessagesReloadCommand implements CommandExecutor {

    private final ConfigLoader configLoader;

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        configLoader.load();
        sender.sendMessage(ChatColor.GREEN + "Plugin reloaded!");
        return true;
    }
}
