package me.linckode.phones.Guis;

import me.linckode.gapi.GUI;
import me.linckode.phones.Config;
import me.linckode.phones.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;

public class MainMenu  {

    private GUI gui = new GUI();
    
    public MainMenu(){
        gui.init("Phone", 6);
        
        gui.fillBlank();

        //MSG button
        gui.multiSetItem(gui.createItem(Material.STAINED_GLASS_PANE,1,"&eSend Message", (byte)4,"&6Click to send a","&6message to a friend"), 36, 37, 45, 46);
        //MSG action
        gui.multiSetAction((InventoryClickEvent event) -> {
            PhoneNumberMenu phoneNumberMenu = new PhoneNumberMenu();
            phoneNumberMenu.show((Player) event.getWhoClicked());
        },36, 37, 45, 46);

        //Contacts buttons
        gui.multiSetItem(gui.createItem(Material.STAINED_GLASS_PANE,1,"&6Contact List", (byte)1,"&eClick to open","&eThe Contact List"), 43, 44, 52, 53);

        //signal bars
        gui.setItem(8, gui.createItem(Material.STAINED_GLASS_PANE,1,"&aSignal", (byte)5,"&2???ms &2delay"));
        gui.setItem(7, gui.createItem(Material.STAINED_GLASS_PANE,1,"&aSignal", (byte)5,"&2???ms &2delay"));
        gui.setItem(6, gui.createItem(Material.STAINED_GLASS_PANE,1,"&aSignal", (byte)5,"&2???ms &2delay"));
        gui.setItem(5, gui.createItem(Material.STAINED_GLASS_PANE,1,"&aSignal", (byte)5,"&2???ms &2delay"));

        //settings button
        gui.multiSetItem(gui.createItem(Material.STAINED_GLASS_PANE,1,"&9Phone", (byte)11,"&9settings"), 48, 49, 50);

        //Other ChestCommands
        if (Main.enableChestCommands) {
            //"other" button
            gui.multiSetItem(gui.createItem(Material.STAINED_GLASS_PANE, 1, "&cOther", (byte) 14), 25, 26);

            //"other action"
            gui.multiSetAction((InventoryClickEvent event) -> {
                ConsoleCommandSender console = Bukkit.getConsoleSender();
                Bukkit.dispatchCommand(console, "chestcommands open something.yml " + event.getWhoClicked().getName());

                event.setCancelled(true);
            }, 25, 26);
        }

        gui.setItem(40, gui.createItem(Material.REDSTONE, 1, "&4Manage Phones", (byte) 0));
        gui.setAction(40,(InventoryClickEvent event) ->{

            ManagementMenu managementMenu = new ManagementMenu();
            managementMenu.show((Player) event.getWhoClicked());
            event.setCancelled(true);
        });
    }

    public void show(Player player){

        String literalPhoneNumber = Config.getString(new File(Main.getInstance().getDataFolder() + File.separator + "players" + File.separator + player.getUniqueId() + ".yml"), "phoneNumber");
        gui.setItem(0, gui.createItem(Material.STAINED_GLASS_PANE,1,"&2Your phone number:", (byte)5,"&a" + literalPhoneNumber));
        gui.setAction(0, (InventoryClickEvent event) -> {
            event.getWhoClicked().sendMessage(ChatColor.DARK_GREEN + "Your phone number is: " + ChatColor.GREEN + literalPhoneNumber);
            event.setCancelled(true);
        });
        gui.openGui(player);
    }

}
