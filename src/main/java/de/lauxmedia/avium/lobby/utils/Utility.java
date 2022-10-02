package de.lauxmedia.avium.lobby.utils;

import de.lauxmedia.avium.lobby.Lobby;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    private static final List<Player> inAnimation = new ArrayList<>();
    private static final List<Player> buildMode = new ArrayList<>();

    private static final String serverPrefix = "§6Lobby §8» ";

    public static void teleportPlayer(Player player, Location location) {
        player.teleport(location);
        player.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 0.1f, 1.5f);
        new ParticleBuilder(ParticleEffect.PORTAL, player.getLocation())
                .setAmount(30)
                .setOffset(1.5f, 1f, 1.5f)
                .setSpeed(0.5f)
                .display(player);
    }

    public static void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Player> getInAnimation() {
        return inAnimation;
    }

    public static String getNoPerm() {
        return "§7[§c!§7] §cYou don't have permission to perform this command.";
    }

    public static String getServerPrefix() {
        return serverPrefix;
    }

    public static List<Player> getBuildMode() {
        return buildMode;
    }

}
