package de.lauxmedia.avium.lobby.inventories;

import de.lauxmedia.avium.lobby.Lobby;
import de.lauxmedia.avium.lobby.utils.ItemManager;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class Navigator {

    static ItemStack spawn = new ItemManager(Material.PLAYER_HEAD, 1)
            .setName("§8» §aSpawn §8«")
            .setLore("§7§oClick to teleport...")
            .setCustomSkullURL("98daa1e3ed94ff3e33e1d4c6e43f024c47d78a57ba4d38e75e7c9264106")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .toItemStack();

    public static void openNavigatorInventory(Player player) {
        long delay = 3;
        // create Inv
        int rows = 3;
        Inventory inventory = Bukkit.createInventory(null, rows * 9, "§lNAVIGATOR");
        // open Inv
        player.openInventory(inventory);
        // Start Fill Inv Animation
        Utility.getInAnimation().add(player);
        for (int row = 3; row >= 1; row--) {
            int finalRow = row;
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
        // Set Items
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lobby.getInstance(), () -> {
            if (Utility.getInAnimation().contains(player)) {
                inventory.setItem(13, spawn);
                player.playSound(player.getLocation(),  Sound.BLOCK_GLASS_BREAK, 0.1f, 1f);
                Utility.getInAnimation().remove(player);
            }
        }, delay + 5);
    }

    public static ItemStack getSpawn() {
        return spawn;
    }
}
