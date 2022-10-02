package de.lauxmedia.avium.lobby.listener;

import de.lauxmedia.avium.lobby.Lobby;
import de.lauxmedia.avium.lobby.utils.Locations;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.GameMode;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class ProtectListener implements Listener {

    @EventHandler
    public void onProtectedJoin (PlayerJoinEvent event) {
        if (Lobby.getInstance().getConfig().getBoolean("settings.dev") && !event.getPlayer().hasPermission("system.dev")) {
            event.getPlayer().kickPlayer("You are no Developer!");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE && Utility.getBuildMode().contains(player)) {
            event.setCancelled(false);
        } else { event.setCancelled(true); }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE && Utility.getBuildMode().contains(player)) {
            event.setCancelled(false);
        } else { event.setCancelled(true); }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.CUSTOM) { event.setCancelled(true); }
    }

    @EventHandler
    public void onDamageOther(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemPickup(PlayerAttemptPickupItemEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE && Utility.getBuildMode().contains(player)) {
            event.setCancelled(false);
        } else { event.setCancelled(true); }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE && Utility.getBuildMode().contains(player)) {
            event.setCancelled(false);
        } else { event.setCancelled(true); }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE && Utility.getBuildMode().contains(player)) {
            event.setCancelled(false);
        } else { event.setCancelled(true); }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) { event.setCancelled(true); }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() == GameMode.CREATIVE && Utility.getBuildMode().contains(player)) {
            event.setCancelled(false);
        } else { event.setCancelled(true); }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() == GameMode.CREATIVE && Utility.getBuildMode().contains(player)) {
            event.setCancelled(false);
        } else { event.setCancelled(true); }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE && Utility.getBuildMode().contains(player)) {
            event.setCancelled(false);
        } else { event.setCancelled(true); }
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getY() < 10 && !(event.getPlayer().getGameMode() == GameMode.CREATIVE && Utility.getBuildMode().contains(event.getPlayer()))) {
            Utility.teleportPlayer(player, Locations.getSpawnLocation());
        }
    }

}
