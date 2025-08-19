package net.runith.death.converter;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

@UtilityClass
public final class ItemConverter {

    public static String convertItemStackToJson(ItemStack itemStack) {
        try {
            net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
            NBTTagCompound compound = new NBTTagCompound();
            nmsItem.save(compound);
            return compound.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static TextComponent convertItemStackToComponents(final ItemStack itemStack) {
        final String json = convertItemStackToJson(itemStack);
        if (json == null) {
            return new TextComponent(ChatColor.RED + "Error on converting item to JSON.");
        }

        final String itemName;
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
            itemName = itemStack.getItemMeta().getDisplayName();
        } else {
            itemName = itemStack.getType().toString().toLowerCase(Locale.ENGLISH).replace("_", " ");
        }

        final TextComponent itemComponent = new TextComponent(ChatColor.AQUA + itemName);
        itemComponent.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_ITEM,
                new BaseComponent[]{new TextComponent(json)}
        ));

        return itemComponent;
    }
}