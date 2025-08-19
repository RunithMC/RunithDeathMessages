package net.runith.death.listener;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.TextComponent;
import net.runith.death.converter.ItemConverter;
import net.runith.death.model.DeathMessages;
import net.runith.death.model.Titles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class PlayerDeathListener implements Listener {

    private final Titles titles;
    private final DeathMessages deathMessages;

    @EventHandler
    public void onDeath(final PlayerDeathEvent event) {
        setDeathMessage(event);
        sendTitle(event.getEntity());
    }

    private void sendTitle(final Player player) {
        if (titles.isEnable()) {
            player.sendTitle(titles.next());
        }
    }

    private void setDeathMessage(PlayerDeathEvent event) {
        String deathMessage = deathMessages.getDeathMessage(event.getEntity().getLastDamageCause().getCause());
        if (deathMessage == null) {
            event.setDeathMessage(null);
            return;
        }
        deathMessage = deathMessage.replace("%v%", event.getEntity().getName());
        if (event.getEntity().getKiller() == null) {
            event.setDeathMessage(deathMessage);
            return;
        }

        final Player killer = event.getEntity().getKiller();
        final ItemStack itemInHand = killer.getItemInHand();

        deathMessage += deathMessages.getKillerExistFormat().replace("%k%", killer.getName());
        if (itemInHand == null) {
            event.setDeathMessage(deathMessage);
            return;
        }

        event.setDeathMessage(null);
        final TextComponent textComponent = new TextComponent(deathMessage + deathMessages.getItemExistPrefix());
        textComponent.addExtra(ItemConverter.convertItemStackToComponents(itemInHand));

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.spigot().sendMessage(textComponent);
        }
    }
}
