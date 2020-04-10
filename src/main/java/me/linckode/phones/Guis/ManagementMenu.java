package me.linckode.phones.Guis;

import me.linckode.gapi.GUI;
import me.linckode.phones.Guis.Management.AntennasMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ManagementMenu {

    private GUI gui = new GUI();

    public ManagementMenu(){

        gui.init("Phones Management Menu", 4);
        gui.fillBlank();

        gui.setItem(0, gui.createItem(Material.NAME_TAG, 1, "&bAntennas", (byte) 0));
        gui.setAction(0, (InventoryClickEvent event) ->{
            AntennasMenu antennasMenu = new AntennasMenu();
            antennasMenu.show((Player) event.getWhoClicked());
            event.setCancelled(true);
        });


        gui.setItem(27, gui.createItem(Material.ARROW, 1, "&cBack", (byte) 0));
        gui.setAction(27, (InventoryClickEvent event) -> {
            MainMenu mainMenu = new MainMenu((Player) event.getWhoClicked());
            mainMenu.show((Player) event.getWhoClicked());
            event.setCancelled(true);
        });

    }

    public void show(Player player){
        gui.openGui(player);
    }

}
