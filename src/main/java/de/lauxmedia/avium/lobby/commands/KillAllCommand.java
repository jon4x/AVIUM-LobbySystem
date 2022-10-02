package de.lauxmedia.avium.lobby.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KillAllCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;
        // get Player
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("killall")) {
            World world = player.getWorld();
            int counter = 0;
            for (Entity allEntity : world.getEntities()) {
                if (allEntity.getType() != EntityType.PLAYER) {
                    allEntity.remove();
                    counter++;
                }
            }
            player.sendMessage("§7Es wurden §c" + counter + "§7 Entities gefunden & getötet.");
        }

        return true;
    }
}
