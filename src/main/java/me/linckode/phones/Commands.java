package me.linckode.phones;

import me.linckode.gapi.GUI;
import me.linckode.phones.Guis.MainMenu;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor {

    public static ItemStack phoneItem = GUI.createItem(Material.NETHER_BRICK_ITEM,1,"&6&lPhone", (byte) 0,"&eRight click to open the phone!");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){

            Player player = (Player) sender;
            if (!player.hasPermission(Main.permission)) {
                player.sendMessage(ChatColor.RED + "You don't have the permission to do this!");
                return true;
            }
                if (!player.getInventory().contains(phoneItem)){
                    player.getInventory().addItem(phoneItem);
                }
                else
                    player.sendMessage(ChatColor.GOLD + "You already have a phone!");
        }
        else {
            sender.sendMessage(ChatColor.RED + "This command can only be issued by a player.");
        }

        return true;
    }
}
