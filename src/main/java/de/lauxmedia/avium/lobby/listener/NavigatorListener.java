package de.lauxmedia.avium.lobby.listener;

import de.lauxmedia.avium.lobby.inventories.Navigator;
import de.lauxmedia.avium.lobby.inventories.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class NavigatorListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {
                if (!(player.getInventory().getItemInMainHand().getType() == Material.AIR)) {
                    if (Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName()
                            .equalsIgnoreCase(Objects.requireNonNull(Items.getNavigator().getItemMeta()).getDisplayName())) {
                        Navigator.openNavigatorInventory(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() != null) {
            if (event.getView().getTitle().toLowerCase().contains("navigator")) {
                if (event.getCurrentItem() != null) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(Navigator.getSpawn().getItemMeta().getDisplayName())) {
                        player.performCommand("spawn");
                    }
                }
            }
        }
    }

}
