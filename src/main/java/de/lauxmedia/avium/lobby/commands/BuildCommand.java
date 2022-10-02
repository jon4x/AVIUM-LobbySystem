package de.lauxmedia.avium.lobby.commands;

import de.lauxmedia.avium.lobby.inventories.Items;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        // get Player
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("build")) {
            if (player.hasPermission("system.build")) {
                if (args.length == 0) {
                    if (!Utility.getBuildMode().contains(player)) {
                        player.sendMessage(Utility.getServerPrefix() + "§7Build-Mode has been §aenabled§7.");
                        player.setGameMode(GameMode.CREATIVE);
                        Utility.getBuildMode().add(player);
                        player.getInventory().clear();
                    } else {
                        player.sendMessage(Utility.getServerPrefix() + "§7Build-Mode has been §cdisabled§7.");
                        Items.setStartItems(player);
                        player.setGameMode(GameMode.SURVIVAL);
                        Utility.getBuildMode().remove(player);
                    }
                    return true;
                } else if (args.length == 1) {
                    Player argsPlayer = player.getServer().getPlayer(args[0]);
                    if (argsPlayer != null) {
                        if (!Utility.getBuildMode().contains(argsPlayer)) {
                            player.sendMessage(Utility.getServerPrefix() + "§7Build-Mode for §e" + argsPlayer.getName() + " §7has been §aenabled§7.");
                            argsPlayer.setGameMode(GameMode.CREATIVE);
                            argsPlayer.getInventory().clear();
                            Utility.getBuildMode().add(argsPlayer);
                        } else {
                            player.sendMessage(Utility.getServerPrefix() + "§7Build-Mode for §e" + argsPlayer.getName() + " §7has been §cdisabled§7.");
                            argsPlayer.sendMessage(Utility.getServerPrefix() + "§7Build-Mode has been §cdisabled§7.");
                            argsPlayer.setGameMode(GameMode.SURVIVAL);
                            Items.setStartItems(argsPlayer);
                            Utility.getBuildMode().remove(argsPlayer);
                        }
                    } else {
                        player.sendMessage("§cThis player is not online.");
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                player.sendMessage(Utility.getNoPerm());
                return true;
            }
        }
        return false;
    }
}
