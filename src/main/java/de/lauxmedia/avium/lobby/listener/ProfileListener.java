package de.lauxmedia.avium.lobby.listener;

import de.lauxmedia.avium.lobby.inventories.Items;
import de.lauxmedia.avium.lobby.inventories.Profile;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class ProfileListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {
                if (Objects.requireNonNull(event.getItem()).getItemMeta().getDisplayName().contains("Profile")) {
                    Profile.openProfileInventory(player);
                }
            }
        }
    }


}
