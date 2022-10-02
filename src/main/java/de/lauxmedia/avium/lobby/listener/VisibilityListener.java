package de.lauxmedia.avium.lobby.listener;

import de.lauxmedia.avium.lobby.Lobby;
import de.lauxmedia.avium.lobby.inventories.Items;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;


import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class VisibilityListener implements Listener {

    private static final ArrayList<Player> cooldown = new ArrayList<Player>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {
                if (event.getItem().getItemMeta().getDisplayName().contains("visible")) {
                    changePlayerVisibilityToo(player, player.getInventory().getItemInMainHand());
                }
            }
        }
    }

    public static void changePlayerVisibilityToo(Player player, ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (cooldown.contains(player)) {
            player.sendMessage("§7[§c!§7] §8» §cPlease wait 3 seconds before using again.");
            return;
        }
        java.awt.Color particleColor = Color.BLACK;
        assert meta != null;
        if (meta.hasDisplayName()) {
            String displayName = meta.getDisplayName();
            if (displayName.contains(Items.getPlayerOn().getItemMeta().getDisplayName())) {
                meta.setDisplayName(Objects.requireNonNull(Items.getPlayerOff().getItemMeta()).getDisplayName());
                itemStack.setType(Items.getPlayerOff().getType());
                particleColor = Color.DARK_GRAY;
            }
            if (displayName.contains(Items.getPlayerOff().getItemMeta().getDisplayName())) {
                meta.setDisplayName(Objects.requireNonNull(Items.getPlayerOn().getItemMeta()).getDisplayName());
                itemStack.setType(Items.getPlayerOn().getType());
                particleColor = Color.GREEN;
            }
            cooldown.add(player);
            Lobby.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Lobby.getInstance(), () -> cooldown.remove(player), 60L);

            new ParticleBuilder(ParticleEffect.REDSTONE, player.getEyeLocation())
                    .setAmount(100)
                    .setOffset(1f, 0.25f, 1f)
                    .setSpeed(0.5f)
                    .setColor(particleColor)
                    .display(player);

            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 22, 1));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1.5f);
        }
        itemStack.setItemMeta(meta);
        player.updateInventory();
    }
}
