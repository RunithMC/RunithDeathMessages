package net.runith.death.config;

import lombok.RequiredArgsConstructor;
import net.runith.death.model.DeathMessages;
import net.runith.death.model.Titles;
import net.runith.death.util.MessageColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.github.paperspigot.Title;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@RequiredArgsConstructor
public final class ConfigLoader {

    private final DeathMessages deathMessages;
    private final Titles titles;

    private final Plugin plugin;
    private final Logger logger;

    public void load() {
        loadDeathMessages(createIfAbsentAndLoadConfig("messages.yml"));
        loadTitles(createIfAbsentAndLoadConfig("titles.yml"));
    }

    private FileConfiguration createIfAbsentAndLoadConfig(final String fileName) {
        final File file = new File(plugin.getDataFolder(), fileName);
        if (!(file.exists())) {
            plugin.saveResource(fileName, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public void loadDeathMessages(final FileConfiguration config) {
        final EntityDamageEvent.DamageCause[] causes = EntityDamageEvent.DamageCause.values();
        final String[] messages = new String[causes.length];

        for (final EntityDamageEvent.DamageCause cause : causes) {
            messages[cause.ordinal()] = MessageColor.color(config.getString(cause.name()));
        }
        deathMessages.setMessages(messages);
        deathMessages.setKillerExistFormat(MessageColor.color(config.getString("if-killer-exist")));
        deathMessages.setItemExistPrefix(MessageColor.color(config.getString("if-item-exist")));
    }

    public void loadTitles(final FileConfiguration config) {
        titles.setNext(0);
        titles.setEnable(config.getBoolean("enable"));
        titles.setTitles(new Title[0]);

        if (!titles.isEnable()) {
            return;
        }

        final List<Title> titleList = new ArrayList<>();
        final ConfigurationSection section = config.getConfigurationSection("death-titles");
        if (section == null) {
            logger.warning("Can't found any death title");
            return;
        }

        final int defaultFadeIn = config.getInt("fadeIn", 20);
        final int defaultStay = config.getInt("fadeIn", 80);
        final int defaultFadeOut = config.getInt("fadeIn", 20);

        final Set<String> subSections = section.getKeys(false);
        for (final String key : subSections) {
            final ConfigurationSection titleSection = section.getConfigurationSection(key);
            if (titleSection == null) {
                logger.warning("The title " + key + " isn't a configuration section");
                continue;
            }
            titleList.add(new Title(
                    MessageColor.color(titleSection.getString("title", "")),
                    MessageColor.color(titleSection.getString("subtitle")),
                    titleSection.getInt("fadeIn", defaultFadeIn),
                    titleSection.getInt("stay", defaultStay),
                    titleSection.getInt("fadeOut", defaultFadeOut)
            ));
        }

        titles.setTitles(titleList.toArray(new Title[0]));
    }
}