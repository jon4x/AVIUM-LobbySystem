package de.lauxmedia.avium.lobby.commands;

import de.lauxmedia.avium.lobby.Lobby;
import de.lauxmedia.avium.lobby.utils.Locations;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SetSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) { return true; }
        // Cast Player
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("setspawn")) {

            Plugin plugin = Lobby.getInstance();

            plugin.getConfig().set("locations.spawn.x", player.getLocation().getX());
            plugin.getConfig().set("locations.spawn.y", player.getLocation().getY());
            plugin.getConfig().set("locations.spawn.z", player.getLocation().getZ());
            plugin.getConfig().set("locations.spawn.yaw", player.getLocation().getYaw());
            plugin.getConfig().set("locations.spawn.pitch", player.getLocation().getPitch());
            plugin.getConfig().set("locations.spawn.world", player.getLocation().getWorld().getName());

            plugin.saveConfig();

            Location location = player.getLocation();
            Locations.setSpawnLocation(location);

            player.sendMessage("§7Spawn wurde §aerfolgreich §7gesetzt!");
        }
        return true;
    }
}
