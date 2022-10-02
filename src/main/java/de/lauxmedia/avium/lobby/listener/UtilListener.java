package de.lauxmedia.avium.lobby.listener;

import de.lauxmedia.avium.lobby.inventories.Items;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class UtilListener implements Listener {

    // Player Join
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // get Player
        Player player = event.getPlayer();
        // set join Message to null
        event.setJoinMessage(null);
        // Teleport to spawn
        if (!player.hasPlayedBefore()) {
            player.performCommand("spawn");
            player.playEffect(player.getLocation(), Effect.PORTAL_TRAVEL, 1);
            // Start Items
            Items.setStartItems(player);
        } else if (player.getInventory().getItem(4) == null) {
            Items.setStartItems(player);
        }
        // Set Health
        player.setHealth(20);
        player.setHealthScale(2);
        player.setFoodLevel(20);
        // Set Level
        player.setLevel(0);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // get Player
        Player player = event.getPlayer();
        // set join Message to null
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onPlayerChangeItemHeld(PlayerItemHeldEvent event) {
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 0.05f, 1.75f);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (Utility.getInAnimation().contains(player)) {
            Utility.getInAnimation().remove(player);
        }
    }

}
