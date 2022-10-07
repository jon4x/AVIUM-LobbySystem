package de.lauxmedia.avium.lobby.commands;

import de.lauxmedia.avium.coins.Coins;
import de.lauxmedia.avium.lobby.utils.UUIDFetcher;
import de.lauxmedia.avium.lobby.utils.Utility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CoinsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // check console
        if (!(sender instanceof Player)) { sender.sendMessage("You need to be a Player to perform this command!"); return true; }
        // cast Player
        Player player = (Player) sender;
        // check for command
        if (command.getName().equalsIgnoreCase("coins")) {
            // check perms
            if (!player.hasPermission("system.coins")) {
                player.sendMessage(Utility.getNoPerm());
                return true;
            }
            // check args
            if (args.length == 0) {
                return false;
            }
            // set coins
            else if (args[0].equalsIgnoreCase("set")) {
                if (args.length == 3) {
                    String playerName = args[1];
                    int amount = Integer.parseInt(args[2]);
                    if (amount > 999999999) {
                        player.sendMessage("§cSorry, the maximum amount of coins is 999999999");
                        return true;
                    }
                    else if (UUIDFetcher.getUUID(playerName) != null) {
                        UUID uuid = UUIDFetcher.getUUID(playerName);
                        player.sendMessage("§7Coins for §a" + playerName + "§7 have been set to §6" + amount + "§7." );
                        Coins.getApi().setCoins(String.valueOf(uuid), amount);
                    }
                    else {
                        player.sendMessage("§cThis player does not exist!");
                    }
                    return true;
                }
                else {
                    player.sendMessage("§cPlease use: /coins set <player> <amount>");
                    return true;
                }
            }
            // get coins
            else if (args[0].equalsIgnoreCase("get")) {
                if (args.length == 2) {
                    String playerName = args[1];
                    if (UUIDFetcher.getUUID(playerName) != null) {
                        UUID uuid = UUIDFetcher.getUUID(playerName);
                        int amount = Coins.getApi().getCoins(uuid.toString());
                        player.sendMessage("§7Player §a" + playerName + "§7 currently has §6" + amount + "§7." );
                    }
                    else {
                        player.sendMessage("§cThis player does not exist!");
                    }
                }
                else {
                    player.sendMessage("§cPlease use: /coins get <player>");
                }
                return true;
            }
        }
        return true;
    }
}
