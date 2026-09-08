package com.parkour.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ParkourGUI {

    public static final Component GUI_TITLE = Component.text("Parkour Menu", NamedTextColor.DARK_GRAY);

    public static void openGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, GUI_TITLE);

        ItemStack quitItem = new ItemStack(Material.RED_BED);
        ItemMeta quitMeta = quitItem.getItemMeta();
        if (quitMeta != null) {
            quitMeta.displayName(Component.text("Quit Parkour", NamedTextColor.RED));
            quitItem.setItemMeta(quitMeta);
        }

        boolean hidden = Main.getInstance().getParkourManager().hasHiddenPlayers(player);
        ItemStack hideItem = new ItemStack(hidden ? Material.ENDER_EYE : Material.ENDER_PEARL);
        ItemMeta hideMeta = hideItem.getItemMeta();
        if (hideMeta != null) {
            hideMeta.displayName(Component.text(hidden ? "Show Players" : "Hide Players", NamedTextColor.GREEN));
            hideItem.setItemMeta(hideMeta);
        }

        inv.setItem(3, quitItem);
        inv.setItem(5, hideItem);

        player.openInventory(inv);
    }
}
