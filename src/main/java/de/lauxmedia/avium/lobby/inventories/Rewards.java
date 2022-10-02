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

public class Rewards {

    static ItemStack dailyReward = new ItemManager(Material.GOLDEN_APPLE, 1)
            .setName("§6§nDaily Reward")
            .setLore("", "§a§o+100 Emeralds", "")
            .setUnbreakable()
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
            .toItemStack();
    static ItemStack dailyRewardCollected = new ItemManager(Material.APPLE, 1)
            .setName("§6§nDaily Reward")
            .setLore("", "§c§oAlready picked up!", "")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
            .toItemStack();

    public static void openRewardsInventory(Player player) {
        long delay = 3;
        // create Inv
        int rows = 3;
        Inventory inventory = Bukkit.createInventory(null, rows * 9, "§lREWARDS");
        // open Inv
        player.openInventory(inventory);
        // Start Fill Inv Animation
        Utility.getInAnimation().add(player);
        for (int row = rows; row >= 1; row--) {
            int finalRow = row;
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lobby.getInstance(), () -> {
                for (int slot = 9; slot >= 1; slot--) {
                    if (Utility.getInAnimation().contains(player)) {
                        inventory.setItem((finalRow*9) - slot, Items.getPlaceholder());
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_OFF, 0.1f, 1.25f);
                    }
                }
            }, delay);
            delay = delay + 3;
        }
        // Set Items
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lobby.getInstance(), () -> {
            if (Utility.getInAnimation().contains(player)) {
                inventory.setItem(9 + 4, dailyReward);
                player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 0.1f, 1f);
                Utility.getInAnimation().remove(player);
            }
        }, delay + 5);
    }
}
