package de.lauxmedia.avium.lobby.commands;

import de.lauxmedia.avium.lobby.Lobby;
import de.lauxmedia.avium.lobby.utils.Locations;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class WarpCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        // detect Console
        if (!(sender instanceof Player)) { return true; }
        // Cast Player
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("warp")) {

            if (args.length != 1) { return false; }

            String warpName = args[0];
            Location warp = Locations.getWarpLocation(warpName);
            if (warp == null) {
                player.sendMessage(Utility.getServerPrefix() + "§cThis warp does not exist!");
                return true;
            }
            Utility.teleportPlayer(player, warp);
        }
        return true;
    }

}
