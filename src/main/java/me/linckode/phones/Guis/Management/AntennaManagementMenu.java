package me.linckode.phones.Guis.Management;

import me.linckode.gapi.GUI;
import me.linckode.phones.ActionLinker;
import me.linckode.phones.Antenna;
import me.linckode.phones.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;

public class AntennaManagementMenu {

    private GUI gui = new GUI();

    public AntennaManagementMenu(Antenna antenna){

        gui.init("Managing antenna " + antenna.getId(), 1);
        gui.fillBlank();
        gui.setItem(0, gui.createItem(Material.ANVIL,1,"&aSet antenna to your current position",(byte) 0));
        gui.setAction(0,(InventoryClickEvent event) ->{
            antenna.setLocation(event.getWhoClicked().getLocation());
            antenna.saveToConfig();
            event.setCancelled(true);
        });
        gui.setItem(1,gui.createItem(Material.BARRIER,1,"&cRemove antenna", (byte) 0));
        gui.setAction(1,(InventoryClickEvent event) ->{
            Main.antennas.remove(antenna);
            File antennaFile = new File(Main.getInstance().getDataFolder() + File.separator + "antennas" + File.separator + antenna.getId() + ".yml");
            antennaFile.delete();
            event.setCancelled(true);
        });

        gui.setItem(2, gui.createItem(Material.NAME_TAG, 1, "&eSet carrier", (byte) 0));
        gui.setAction(2, (InventoryClickEvent event) -> {
            event.getWhoClicked().closeInventory();
            Player player = (Player) event.getWhoClicked();
            player.sendMessage(ChatColor.YELLOW + "Insert the carrier name:");

            Main.actionLinkers.add(new ActionLinker((String message)->{
                player.sendMessage(ChatColor.GREEN + "Antenna carrier set to: " + ChatColor.YELLOW + message);
                antenna.setCarrier(message);
                antenna.saveToConfig();
            },player));

            event.setCancelled(true);

        });

        gui.setItem(6,gui.createItem(Material.GREEN_GLAZED_TERRACOTTA,1,"&aSignal strength : 1", (byte) 0));
        gui.setItem(7,gui.createItem(Material.YELLOW_GLAZED_TERRACOTTA,1,"&eSignal strength : 2", (byte) 0));
        gui.setItem(8,gui.createItem(Material.RED_GLAZED_TERRACOTTA,1,"&cSignal strength : 3", (byte) 0));
        gui.multiSetAction((InventoryClickEvent event) -> {
            switch (event.getSlot()){
                case 6:
                    antenna.setSignalStrength(1);
                    break;
                case 7:
                    antenna.setSignalStrength(2);
                    break;
                case 8:
                    antenna.setSignalStrength(3);
                    break;
            }
            antenna.saveToConfig();
            event.setCancelled(true);
        },6, 7, 8);
        gui.setItem(4,gui.createItem(Material.ARROW,1,"&cGo back.", (byte) 0));
        gui.setAction(4,(InventoryClickEvent event) -> {
            AntennasList antennasList = new AntennasList();
            antennasList.show((Player) event.getWhoClicked());
        });

    }


    public void show(Player player){
        gui.openGui(player);
    }

}
