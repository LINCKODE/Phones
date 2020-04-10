package me.linckode.phones.Guis.Management;

import me.linckode.gapi.GUI;
import me.linckode.phones.Antenna;
import me.linckode.phones.Config;
import me.linckode.phones.Guis.MainMenu;
import me.linckode.phones.Guis.ManagementMenu;
import me.linckode.phones.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;

public class AntennasMenu {

    private GUI gui = new GUI();

    public AntennasMenu(){

        gui.init("Antennas", 1);
        gui.fillBlank();

        gui.setItem(0, gui.createItem(Material.ANVIL,1,"&2Create new antenna", (byte) 0,"&aClicking this will", "&acreate a new antenna", "&aat your current location"));

        gui.setAction(0,(InventoryClickEvent event) -> {
            Antenna antenna = new Antenna(event.getWhoClicked().getLocation(),1, Main.antennas.size() + 1,"");
            antenna.saveToConfig();
            Main.antennas.add(antenna);
            event.setCancelled(true);
        });

        gui.setItem(1, gui.createItem(Material.BOOK, 1, "&bAntennas List", (byte) 0));

        gui.setAction(1, (InventoryClickEvent event) -> {
            AntennasList antennasList = new AntennasList();
            antennasList.show((Player) event.getWhoClicked());
        });
        gui.setItem(8,gui.createItem(Material.ARROW,1,"&cGo back.", (byte) 0));
        gui.setAction(8,(InventoryClickEvent event) -> {
            ManagementMenu managementMenu = new ManagementMenu();
            managementMenu.show((Player) event.getWhoClicked());
            event.setCancelled(true);
        });

    }

    public void show(Player player){
        gui.openGui(player);
    }

}
