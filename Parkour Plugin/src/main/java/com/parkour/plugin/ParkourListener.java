package com.parkour.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class ParkourListener implements Listener {

    private final Main plugin;

    public ParkourListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.hasChangedBlock()) return;

        Player player = event.getPlayer();
        if (!player.hasPermission("luckperms.parkour.checkpoint")) return;

        Location to = event.getTo();
        Block standingOn = to.getBlock().getRelative(0, -1, 0);

        if (standingOn.getType() == plugin.getCheckpointMaterial()) {
            Location blockLoc = standingOn.getLocation();
            
            if (player.getLocation().getY() >= blockLoc.getY() + 1.0 && player.getLocation().getY() <= blockLoc.getY() + 1.25) {
                Location currentCheckpoint = plugin.getParkourManager().getCheckpoint(player);
                
                if (currentCheckpoint == null || currentCheckpoint.getBlockX() != blockLoc.getX() || 
                    currentCheckpoint.getBlockY() != (blockLoc.getY() + 1) || currentCheckpoint.getBlockZ() != blockLoc.getZ()) {
                    
                    Location cpLoc = new Location(blockLoc.getWorld(), blockLoc.getX() + 0.5, blockLoc.getY() + 1.0, blockLoc.getZ() + 0.5, player.getLocation().getYaw(), player.getLocation().getPitch());
                    plugin.getParkourManager().setCheckpoint(player, cpLoc);
                    player.sendMessage(Component.text("Checkpoint set!", NamedTextColor.GREEN));
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().title().equals(ParkourGUI.GUI_TITLE)) return;
        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        ParkourManager manager = plugin.getParkourManager();

        if (clicked.getType() == Material.RED_BED) {
            manager.removeCheckpoint(player);
            player.sendMessage(Component.text("You have quit the parkour and your checkpoint was cleared.", NamedTextColor.RED));
            player.closeInventory();
        } else if (clicked.getType() == Material.ENDER_PEARL || clicked.getType() == Material.ENDER_EYE) {
            boolean isHidden = manager.hasHiddenPlayers(player);
            if (isHidden) {
                manager.setHiddenPlayers(player, false);
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(plugin, onlinePlayer);
                }
                player.sendMessage(Component.text("Other players are now visible.", NamedTextColor.GREEN));
            } else {
                manager.setHiddenPlayers(player, true);
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (!onlinePlayer.equals(player)) {
                        player.hidePlayer(plugin, onlinePlayer);
                    }
                }
                player.sendMessage(Component.text("Other players have been hidden.", NamedTextColor.GREEN));
            }
            player.closeInventory();
        }
    }
}
