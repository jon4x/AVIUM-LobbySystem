package de.lauxmedia.avium.lobby.inventories;

import de.lauxmedia.avium.lobby.utils.ItemManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Items {

    static ItemStack navigator = new ItemManager(Material.COMPASS, 1)
            .setName("§e§lNavigator §7(Rightclick)")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .toItemStack();
    static ItemStack lobbySwitcher = new ItemManager(Material.NETHER_STAR, 1)
            .setName("§6§lLobby switcher §7(Rightclick)")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .toItemStack();
    static ItemStack enderPearlLauncher = new ItemManager(Material.BLAZE_ROD, 1)
            .setName("§b§lFun Gun §7(Rightclick)")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .toItemStack();
    static ItemStack playerOn = new ItemManager(Material.MUSIC_DISC_CAT, 1)
            .setName("§a§lAll Players visible §7(Rightclick)")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .toItemStack();
    static ItemStack playerOff = new ItemManager(Material.MUSIC_DISC_11, 1)
            .setName("§c§lNo Players visible §7(Rightclick)")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .toItemStack();
    static ItemStack placeholder = new ItemManager(Material.GRAY_STAINED_GLASS_PANE, 1)
            .setName("§r")
            .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
            .toItemStack();

    static ItemStack airItem = new ItemManager(Material.AIR, 1)
            .toItemStack();

    public static void setStartItems(Player player) {
        // create Profile Item
        ItemStack profile = new ItemManager(Material.PLAYER_HEAD, 1).setName("§a§lProfile §7(Rightclick)").setSkullOwner(player.getName()).toItemStack();
        // set Inv
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();
        playerInventory.setHeldItemSlot(4);
        // set Items
        playerInventory.setItem(4, navigator);
        playerInventory.setItem(0, profile);
        playerInventory.setItem(1, enderPearlLauncher);
        playerInventory.setItem(7, playerOn);
        playerInventory.setItem(8, lobbySwitcher);
        // placeholder
        for (int i = 9; i < 36; i++) {
            playerInventory.setItem(i, placeholder);
        }
    }

    public static ItemStack getNavigator() {
        return navigator;
    }

    public static ItemStack getPlayerOn() {
        return playerOn;
    }

    public static ItemStack getPlayerOff() {
        return playerOff;
    }

    public static ItemStack getPlaceholder() {
        return placeholder;
    }

    public static ItemStack getLobbySwitcher() {
        return lobbySwitcher;
    }

    public static ItemStack getEnderPearlLauncher() {
        return enderPearlLauncher;
    }

    public static ItemStack getAirItem() {
        return airItem;
    }

}
