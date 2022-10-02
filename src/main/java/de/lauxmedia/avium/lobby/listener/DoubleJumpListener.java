package de.lauxmedia.avium.lobby.listener;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class DoubleJumpListener implements Listener {

    private final List<Player> doubleJump = new ArrayList<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setAllowFlight(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        doubleJump.remove(event.getPlayer());
    }

    @EventHandler
    public void onTriggerFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        GameMode gameMode = player.getGameMode();

        // check if he is able to fly
        if (gameMode.equals(GameMode.CREATIVE) || gameMode.equals(GameMode.SPECTATOR))
            return;

        // Check if he is still in DoubleJump
        if (doubleJump.contains(player)) {
            player.setAllowFlight(false);
            event.setCancelled(true);
            return;
        }

        // Add Player to doubleJump List
        doubleJump.add(player);
        event.setCancelled(true);

        // do Stuff to Player
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1));

        // Sound and Particle
        Location location = player.getLocation();
        player.playSound(location, Sound.ENTITY_ENDER_DRAGON_FLAP, 0.5f, 1.5f);
        player.playSound(location, Sound.ENTITY_ARROW_SHOOT, 0.5f, 1.5f);

        new ParticleBuilder(ParticleEffect.CLOUD, player.getLocation())
                .setAmount(5)
                .setOffset(0.5f, 0f, 0.5f)
                .setSpeed(0.01f)
                .display(player);
    }

    @EventHandler
    public void onHitGround(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if((player.isOnGround() || event.getTo().getBlock().isLiquid()) && doubleJump.remove(player)) {
            player.setAllowFlight(true);
        }
    }

}
