package de.lauxmedia.avium.lobby.utils;

import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.profile.Profile;
import de.lauxmedia.avium.lobby.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class NpcManager {

    /**
     * Appends a new NPC to the pool.
     *
     * @param location       The location the NPC will be spawned at
     */

    public static void appendNPC(Location location, String skinOwner, String displayName) {
        // building the NPC
        String coloredName = ChatColor.translateAlternateColorCodes('&', displayName);
        Lobby.getInstance().getServer().getConsoleSender().sendMessage("Spawning of " + coloredName + " §rconfirmed!");
        NPC npc = NPC.builder()
                .profile(createProfile(skinOwner, coloredName))
                .location(location)
                .imitatePlayer(false)
                .lookAtPlayer(true)
                // appending it to the NPC pool
                .build(Lobby.getNpcPool());
    }

    /**
     * Creates an example profile for NPCs.
     *
     * @return The new profile
     */

    public static Profile createProfile(String skinOwner, String displayName) {
        Profile profile = new Profile(UUIDFetcher.getUUID(skinOwner));
        // Synchronously completing the profile, as we only created the profile with a UUID.
        // Through this, the name and textures will be filled in.
        profile.complete();
        // we want to keep the textures, but change the name and UUID.
        Random random = new Random();
        profile.setName(displayName);
        profile.setUniqueId(new UUID(random.nextLong(), 0));

        return profile;
    }

    public static void getNpcsFromConfig() {
        if (Lobby.getCustomNpcConfig() == null) { Lobby.getInstance().getServer().getConsoleSender().sendMessage("No NPC Config found."); return; }
        if (Lobby.getCustomNpcConfig().getConfigurationSection("npcs") == null) { Lobby.getInstance().getServer().getConsoleSender().sendMessage("No NPCs found."); return; }
        ConfigurationSection configurationSection = Lobby.getCustomNpcConfig().getConfigurationSection("npcs");
        assert configurationSection != null;
        for (String npcID : configurationSection.getKeys(false)) {
            // Get all Information
            String worldString = Lobby.getCustomNpcConfig().getString("npcs." + npcID + ".location.world");
            assert worldString != null;
            World world = Lobby.getInstance().getServer().getWorld(worldString);
            double x;
            x = Lobby.getCustomNpcConfig().getDouble("npcs." + npcID + ".location.x");
            double y;
            y = Lobby.getCustomNpcConfig().getDouble("npcs." + npcID + ".location.y");
            double z;
            z = Lobby.getCustomNpcConfig().getDouble("npcs." + npcID + ".location.z");

            float yaw = (float) Lobby.getCustomNpcConfig().getDouble("npcs" + npcID + ".location.yaw");
            float pitch = (float) Lobby.getCustomNpcConfig().getDouble("npcs" + npcID + ".location.pitch");
            // create Location
            Location location = new Location(world, x, y, z, yaw, pitch);

            String skinOwner = Lobby.getCustomNpcConfig().getString("npcs." + npcID + ".skinOwner");
            String displayName = Lobby.getCustomNpcConfig().getString("npcs." + npcID + ".displayName");
            // spawn NPCs
            Lobby.getInstance().getServer().getConsoleSender().sendMessage("Spawning NPC: " + displayName);
            appendNPC(location, skinOwner, displayName);
        }
    }

}
