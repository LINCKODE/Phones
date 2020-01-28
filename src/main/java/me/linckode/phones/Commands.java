package me.linckode.phones;

import me.linckode.phones.Guis.MainMenu;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){

            Player player = (Player) sender;
            if (!player.hasPermission(Main.permission))
                player.sendMessage(ChatColor.RED + "You don't have the permission to do this!");

            if (args.length == 0){
                MainMenu mainMenu = new MainMenu();
                mainMenu.show(player);
                //open GUI
            }
            if (args.length == 1){
                //TODO: args: info
            }
            if (args.length == 2){
                //TODO: set signal beacons

                Location location = player.getLocation().getBlock().getLocation();
            }

        }
        else {
            sender.sendMessage(ChatColor.RED + "This command can only be issued by a player.");
        }

        return true;
    }
}
