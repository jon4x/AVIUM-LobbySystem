package de.lauxmedia.avium.lobby.inventories;

import de.lauxmedia.avium.lobby.Lobby;
import de.lauxmedia.avium.lobby.utils.ItemManager;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class LobbySwitcher {

    static ItemStack premiumLobby = new ItemManager(Material.GLOWSTONE_DUST, 1)
            .setName("§6PremiumLobby-1")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .toItemStack();
    static ItemStack normalLobby = new ItemManager(Material.GUNPOWDER, 1)
            .setName("§7Lobby-1")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .addUnsafeEnchantment(Enchantment.LOYALTY, 1)
            .toItemStack();

    public static void openLobbySwitcherInventory(Player player) {
        // size
        long delay = 3;
        int rows = 3;
        // cancel open when in animation
        if (Utility.getInAnimation().contains(player)) { return; }
        // create Inv
        Inventory inventory = Bukkit.createInventory(null, rows * 9, "§lLOBBY SWITCHER");
        // open Inv
        player.openInventory(inventory);
        // Start Fill Inv Animation
        Utility.getInAnimation().add(player);
        for (int row = 3; row >= 1; row--) {
            int finalRow = row;
            if (Utility.getInAnimation().contains(player)) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lobby.getInstance(), () -> {
                    for (int slot = 9; slot >= 1; slot--) {
                        if (Utility.getInAnimation().contains(player)) {
                            inventory.setItem((finalRow * 9) - slot, Items.getPlaceholder());
                            player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_OFF, 0.1f, 1.25f);
                        }
                    }
                }, delay);
                delay = delay + 3;
            }
        }
        // Set Items
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lobby.getInstance(), () -> {
            if (Utility.getInAnimation().contains(player)) {
                inventory.setItem(9 + 1, Items.getAirItem());
                inventory.setItem(9 + 2, Items.getAirItem());
                inventory.setItem(9 + 3, Items.getAirItem());
                inventory.setItem(9 + 4, Items.getAirItem());
                inventory.setItem(9 + 5, Items.getAirItem());
                inventory.setItem(9 + 6, Items.getAirItem());
                inventory.setItem(9 + 7, Items.getAirItem());

                inventory.setItem(9 + 1, premiumLobby);
                inventory.setItem(9 + 2, normalLobby);
                player.playSound(player.getLocation(),  Sound.BLOCK_GLASS_BREAK, 0.1f, 1f);
                Utility.getInAnimation().remove(player);
            }
        }, delay + 5);
    }
}
