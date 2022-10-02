package de.lauxmedia.avium.lobby.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemManager {

    private ItemStack itemStack;

    /**
     * Create a new ItemBuilder from scratch.
     * @param material The material to create the ItemBuilder with.
     */
    public ItemManager(Material material){
        this(material, 1);
    }

    /**
     * Create a new ItemBuilder over an existing itemstack.
     * @param itemStack The itemstack to create the ItemBuilder over.
     */
    public ItemManager(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    /**
     * Create a new ItemBuilder from scratch.
     * @param material The material of the item.
     * @param amount The amount of the item.
     */
    public ItemManager(Material material, int amount){
        itemStack = new ItemStack(material, amount);
    }

    /**
     * Create a new ItemBuilder from scratch.
     * @param material The material of the item.
     * @param amount The amount of the item.
     * @param durability The durability of the item.
     */
    public ItemManager(Material material, int amount, byte durability){
        itemStack = new ItemStack(material, amount, durability);
    }

    /**
     * Clone the ItemBuilder into a new one.
     * @return The cloned instance.
     */
    public ItemManager clone(){
        return new ItemManager(itemStack);
    }

    /**
     * Change the durability of the item.
     * @param dur The durability to set it to.
     */
    public ItemManager setDurability(short dur){
        itemStack.setDurability(dur);
        return this;
    }

    /**
     * Set the displayname of the item.
     * @param name The name to change it to.
     */
    public ItemManager setName(String name){
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(name);
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Set the skull owner for the item. Works on skulls only.
     * @param owner The name of the skull's owner.
     */
    public ItemManager setSkullOwner(String owner){
        try {
            SkullMeta im = (SkullMeta)itemStack.getItemMeta();
            im.setOwner(owner);
            itemStack.setItemMeta(im);
        } catch (ClassCastException expected) {
            //
        }
        return this;
    }

    public ItemManager setCustomSkullURL(String url){

        SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", "https://textures.minecraft.net/texture/" + url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;

        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        assert profileField != null;
        profileField.setAccessible(true);

        try {
            profileField.set(headMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        itemStack.setItemMeta(headMeta);

        return this;
    }

    public ItemManager setFireworkMeta(Color color){
        ItemMeta itemMeta = itemStack.getItemMeta();
        FireworkEffectMeta fireworkEffectMeta = (FireworkEffectMeta) itemMeta;
        FireworkEffect effect = FireworkEffect.builder().withColor(color).build();
        fireworkEffectMeta.setEffect(effect);
        itemStack.setItemMeta(fireworkEffectMeta);
        return this;
    }

    /**
     * Sets infinity durability on the item by setting the durability to Short.MAX_VALUE.
     */
    public ItemManager setInfinityDurability(){
        itemStack.setDurability(Short.MAX_VALUE);
        return this;
    }

    /**
     * Sets unbreakable on the item by setting the durability to Short.MAX_VALUE.
     */
    public ItemManager setUnbreakable() {
        ItemMeta im = itemStack.getItemMeta();
        im.setUnbreakable(true);
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Re-sets the lore.
     * @param lore The lore to set it to.
     */
    public ItemManager setLore(String... lore){
        ItemMeta im = itemStack.getItemMeta();
        im.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(im);
        return this;
    }
    /**
     * Add an unsafe enchantment.
     * @param ench The enchantment to add.
     * @param level The level to put the enchant on.
     */
    public ItemManager addUnsafeEnchantment(Enchantment ench, int level){
        itemStack.addUnsafeEnchantment(ench, level);
        return this;
    }
    /**
     * Remove a certain enchant from the item.
     * @param ench The enchantment to remove
     */
    public ItemManager removeEnchantment(Enchantment ench){
        itemStack.removeEnchantment(ench);
        return this;
    }
    /**
     * Add an enchant to the item.
     * @param ench The enchant to add
     * @param level The level
     */
    public ItemManager addEnchant(Enchantment ench, int level){
        ItemMeta im = itemStack.getItemMeta();
        im.addEnchant(ench, level, true);
        itemStack.setItemMeta(im);
        return this;
    }
    /**
     * Re-sets the lore.
     * @param lore The lore to set it to.
     */
    public ItemManager setLore(List<String> lore) {
        ItemMeta im = itemStack.getItemMeta();
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Re-sets the itemFlags.
     * @param itemFlags The itemFlags to set it to.
     */
    public ItemManager setItemFlag(ItemFlag... itemFlags) {
        ItemMeta im = itemStack.getItemMeta();
        im.addItemFlags(itemFlags);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemStack toItemStack(){
        return itemStack;
    }

}
