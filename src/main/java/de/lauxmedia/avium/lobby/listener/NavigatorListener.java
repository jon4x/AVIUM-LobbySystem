package de.lauxmedia.avium.lobby.listener;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.BridgeServiceProperty;
import de.dytanic.cloudnet.ext.bridge.node.CloudNetBridgeModule;
import de.dytanic.cloudnet.ext.bridge.player.CloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.NetworkServiceInfo;
import de.dytanic.cloudnet.ext.bridge.player.executor.ServerSelectorType;
import de.lauxmedia.avium.lobby.Lobby;
import de.lauxmedia.avium.lobby.inventories.Navigator;
import de.lauxmedia.avium.lobby.inventories.Items;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class NavigatorListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {
                if (player.getInventory().getItemInMainHand() != null) {
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
            if (event.getView().getTitle().contains("Navigator")) {
                if (event.getCurrentItem() != null) {
                    if (Objects.requireNonNull(event.getCurrentItem()).getItemMeta().getDisplayName().equals(Navigator.getSpawn().getItemMeta().getDisplayName())) {
                        player.performCommand("spawn");
                    }
                }
            }
        }
    }

}
