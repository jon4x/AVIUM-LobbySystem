package de.lauxmedia.avium.lobby.commands;

import de.lauxmedia.avium.lobby.utils.Locations;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // detect Console
        if (!(sender instanceof Player)) { return true; }
        // Cast Player
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("spawn")) {
            // Create Location
            Location location = Locations.getSpawnLocation();
            // Check Location
            if (location == null) { player.sendMessage("§cDer Spawn wurde noch nicht gesetzt!"); return true; }
            // Teleport Player
            Utility.teleportPlayer(player, location);
        }
        return true;
    }
}
