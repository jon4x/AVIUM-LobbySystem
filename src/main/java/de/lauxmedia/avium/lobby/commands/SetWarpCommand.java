package de.lauxmedia.avium.lobby.commands;

import de.lauxmedia.avium.lobby.Lobby;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class SetWarpCommand implements CommandExecutor {

    private final Plugin plugin = Lobby.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        // detect Console
        if (!(sender instanceof Player)) { return true; }
        // Cast Player
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("setwarp")) {

            if (args.length != 1) { return false; }

            String warpName = args[0];

            plugin.getConfig().set("locations.warps." + warpName + ".x", player.getLocation().getX());
            plugin.getConfig().set("locations.warps." + warpName + ".y", player.getLocation().getY());
            plugin.getConfig().set("locations.warps." + warpName + ".z", player.getLocation().getZ());
            plugin.getConfig().set("locations.warps." + warpName + ".yaw", player.getLocation().getYaw());
            plugin.getConfig().set("locations.warps." + warpName + ".pitch", player.getLocation().getPitch());
            plugin.getConfig().set("locations.warps." + warpName + ".world", player.getLocation().getWorld().getName());

            plugin.saveConfig();

            player.sendMessage(Utility.getServerPrefix() + "§7Warp §a§l" + warpName.toUpperCase() + "§7 has been created!");
        }

        return true;
    }

}
