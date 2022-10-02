package de.lauxmedia.avium.lobby.listener;

import de.lauxmedia.avium.lobby.inventories.Items;
import de.lauxmedia.avium.lobby.inventories.LobbySwitcher;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class LobbySwitcherListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {
                if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {

                    if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
                            .equals(Items.getLobbySwitcher().getItemMeta().getDisplayName())) {

                        if (!Utility.getInAnimation().contains(player)) {
                            LobbySwitcher.openLobbySwitcherInventory(player);
                        }

                    }

                }
            }
        }
    }

}
