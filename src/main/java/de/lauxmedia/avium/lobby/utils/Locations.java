package de.lauxmedia.avium.lobby.utils;

import de.lauxmedia.avium.lobby.Lobby;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Locations {

    private static final Configuration configuration = Lobby.getInstance().getConfig();
    private static Location spawnLocation = getSpawnFromConfig();

    /**
     * @return SpawnLocation
     */

    public static Location getSpawnFromConfig() {

        if (configuration.get("locations.spawn") == null) { return null; }

        // Get all Information
        String worldString = configuration.getString("locations.spawn.world");
        assert worldString != null;
        World world = Lobby.getInstance().getServer().getWorld(worldString);

        double x;
        x = configuration.getDouble("locations.spawn.x");
        double y;
        y = configuration.getDouble("locations.spawn.y");
        double z;
        z = configuration.getDouble("locations.spawn.z");

        float yaw = (float) configuration.getDouble("locations.spawn.yaw");
        float pitch = (float) configuration.getDouble("locations.spawn.pitch");
        // create Location
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Location getWarpLocation(String warp) {
        if (configuration.get("locations.warps." + warp) == null) { return null; }
        // Get all Information
        String worldString = configuration.getString("locations.warps." + warp + ".world");
        assert worldString != null;
        World world = Lobby.getInstance().getServer().getWorld(worldString);

        double x = configuration.getDouble("locations.warps." + warp + ".x");
        double y = configuration.getDouble("locations.warps." + warp + ".y");
        double z = configuration.getDouble("locations.warps." + warp + ".z");

        float yaw = (float) configuration.getDouble("locations.warps." + warp + ".yaw");
        float pitch = (float) configuration.getDouble("locations.warps." + warp + ".pitch");
        // create Location
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Location getSpawnLocation() {
        return spawnLocation;
    }

    public static void setSpawnLocation(Location spawnLocation) {
        Locations.spawnLocation = spawnLocation;
    }

}
