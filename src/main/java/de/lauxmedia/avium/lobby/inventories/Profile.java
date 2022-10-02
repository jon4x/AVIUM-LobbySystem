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

public class Profile {

    static ItemStack settings = new ItemManager(Material.REDSTONE, 1)
            .setName("§cSettings")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .toItemStack();
    static ItemStack cosmetics = new ItemManager(Material.ARMOR_STAND, 1)
            .setName("§6Cosmetics")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .toItemStack();
    static ItemStack friends = new ItemManager(Material.PLAYER_HEAD, 1)
            .setName("§a§mFriends")
            .setLore("§7§ocoming soon...")
            .setCustomSkullURL("42843c3c235716f3eb5cd9c3bdbf20853f50a65dc223058b1e1eeffde99f6110")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .toItemStack();

    public static void openProfileInventory(Player player) {
        long delay = 3;
        // create Inv
        int rows = 3;
        Inventory inventory = Bukkit.createInventory(null, rows * 9, "§lPROFILE");
        // open Inv
        player.openInventory(inventory);
        // Start Fill Inv Animation
        Utility.getInAnimation().add(player);
        for (int row = rows; row >= 1; row--) {
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
                inventory.setItem(9 + 1, settings);
                inventory.setItem(9 + 7, friends);
                inventory.setItem(9 + 4, cosmetics);
                player.playSound(player.getLocation(),  Sound.BLOCK_GLASS_BREAK, 0.1f, 1f);
                Utility.getInAnimation().remove(player);
            }
        }, delay + 5);
    }

}
