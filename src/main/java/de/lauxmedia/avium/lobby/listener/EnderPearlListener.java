package de.lauxmedia.avium.lobby.listener;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import de.lauxmedia.avium.lobby.Lobby;
import de.lauxmedia.avium.lobby.inventories.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;

public class EnderPearlListener implements Listener {

    private static final ArrayList<Player> cooldown = new ArrayList<>();

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEnderPearlInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null) { return; }
        if (event.getItem().getItemMeta().getDisplayName().contains("Fun Gun")) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                if (cooldown.contains(player)) {
                    event.setCancelled(true);
                    player.sendMessage("§7[§c!§7] §8» §cPlease wait 10 seconds before using again.");
                    return;
                }

                cooldown.add(player);
                Bukkit.getScheduler().scheduleAsyncDelayedTask(Lobby.getInstance(), () -> cooldown.remove(player), 200L);

                player.launchProjectile(EnderPearl.class);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_LAUNCH, 0.75f, 1.25f);

                event.setCancelled(false);
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEnderPearlThrow(PlayerLaunchProjectileEvent event) {
        Player player = event.getPlayer();
        if (event.getProjectile().getType() == EntityType.ENDER_PEARL) {
            cooldown.add(player);
            Bukkit.getScheduler().scheduleAsyncDelayedTask(Lobby.getInstance(), () -> cooldown.remove(player), 200L);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            Player player = event.getPlayer();
            Bukkit.getScheduler().scheduleAsyncDelayedTask(Lobby.getInstance(), () -> {
                for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                    allPlayers.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.7f, 1.25f);
                    allPlayers.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 0.5f, 1.25f);
                    allPlayers.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 0.5f, 1.5f);
                    new ParticleBuilder(ParticleEffect.HEART, player.getLocation())
                            .setAmount(15)
                            .setOffset(1f, 0.5f, 1f)
                            .setSpeed(0.5f)
                            .display(allPlayers);
                }
            }, 1L);
        }
    }
}
