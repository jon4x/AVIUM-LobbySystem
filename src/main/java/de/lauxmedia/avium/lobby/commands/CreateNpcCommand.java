package de.lauxmedia.avium.lobby.commands;

import de.lauxmedia.avium.lobby.Lobby;
import de.lauxmedia.avium.lobby.utils.NpcManager;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.UUID;

public class CreateNpcCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            return true;
        }
        Player player = (Player) commandSender;
        if (command.getName().equalsIgnoreCase("createnpc")) {
            if (args.length == 2) {
                FileConfiguration npcConfig = Lobby.getCustomNpcConfig();

                Random random = new Random();
                String npcID = String.valueOf(new UUID(random.nextLong(), 0));

                npcConfig.set("npcs." + npcID + ".displayName", args[1]);
                npcConfig.set("npcs." + npcID + ".skinOwner", args[0]);

                npcConfig.set("npcs." + npcID + ".location.x", player.getLocation().getX());
                npcConfig.set("npcs." + npcID + ".location.y", player.getLocation().getY());
                npcConfig.set("npcs." + npcID + ".location.z", player.getLocation().getZ());
                npcConfig.set("npcs." + npcID + ".location.yaw", player.getLocation().getYaw());
                npcConfig.set("npcs." + npcID + ".location.pitch", player.getLocation().getPitch());
                npcConfig.set("npcs." + npcID + ".location.world", player.getLocation().getWorld().getName());

                Utility.saveCustomYml(npcConfig, Lobby.getInstance().getCustomNPCYml());

                NpcManager.appendNPC(player.getLocation(), args[0], args[1]);

                player.sendMessage(Utility.getServerPrefix() + "§7NPC created §asuccessfully§7!");
            }
            else {
                return false;
            }
        }
        return true;
    }
}
