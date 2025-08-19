package net.runith.death.model;

import lombok.Data;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.Nullable;

@Data
public final class DeathMessages {
    private String[] messages;
    private String killerExistFormat;
    private String itemExistPrefix;

    public @Nullable String getDeathMessage(final EntityDamageEvent.DamageCause cause) {
        return messages[cause.ordinal()];
    }
}
