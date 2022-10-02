package de.lauxmedia.avium.lobby.commands;

import com.github.juliarn.npc.NPC;
import de.lauxmedia.avium.lobby.Lobby;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EditNpcCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) { return true; }
        Player player = (Player) commandSender;
        if (command.getName().equalsIgnoreCase("editnpc")) {
            if (player.hasPermission("system.dev")) {
                if (Lobby.getNpcPool().getNPCs().isEmpty()) {
                    return true;
                }
                for (NPC npc : Lobby.getNpcPool().getNPCs()) {
                    if (npc.getLocation().distance(player.getLocation()) <= 2) {
                        player.sendMessage("§7NPC found: " + npc.getProfile().getName());
                        return true;
                    }
                }
                player.sendMessage("§cNo NPC within a radius of 2 blocks found!");
                return true;
            }
            player.sendMessage(Utility.getNoPerm());
        }
        return true;
    }
}
