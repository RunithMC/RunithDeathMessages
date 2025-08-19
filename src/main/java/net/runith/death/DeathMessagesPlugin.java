package net.runith.death;

import net.runith.death.command.DeathMessagesReloadCommand;
import net.runith.death.config.ConfigLoader;
import net.runith.death.listener.PlayerDeathListener;
import net.runith.death.model.DeathMessages;
import net.runith.death.model.Titles;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathMessagesPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        final Titles titles = new Titles();
        final DeathMessages deathMessages = new DeathMessages();
        final ConfigLoader configLoader = new ConfigLoader(deathMessages, titles, this, getLogger());

        configLoader.load();

        getServer().getPluginManager().registerEvents(new PlayerDeathListener(titles, deathMessages), this);

        final PluginCommand command = getCommand("deathreload");
        if (command == null) {
            getLogger().warning("Can't found deathreload command in plugin.yml");
            return;
        }
        command.setExecutor(new DeathMessagesReloadCommand(configLoader));
    }
}