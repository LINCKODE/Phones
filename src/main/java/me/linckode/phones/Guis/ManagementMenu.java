package me.linckode.phones.Guis;

import me.linckode.gapi.GUI;
import me.linckode.phones.Guis.Management.AntennasMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

class ManagementMenu {

    private GUI gui = new GUI();

    ManagementMenu(){

        gui.init("Phones Management Menu", 4);
        gui.fillBlank();

        gui.setItem(0, gui.createItem(Material.NAME_TAG, 1, "Antennas", (byte) 0));
        gui.setAction(0, (InventoryClickEvent event) ->{
            AntennasMenu antennasMenu = new AntennasMenu();
            antennasMenu.show((Player) event.getWhoClicked());
            event.setCancelled(true);
        });


        gui.setItem(27, gui.createItem(Material.ARROW, 1, "&4Back", (byte) 0));
        gui.setAction(27, (InventoryClickEvent event) -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.show((Player) event.getWhoClicked());
            event.setCancelled(true);
        });

    }

    void show(Player player){
        gui.openGui(player);
    }

}
