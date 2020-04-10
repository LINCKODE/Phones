package me.linckode.phones.Guis.Management;

import me.linckode.gapi.GUI;
import me.linckode.phones.Antenna;
import me.linckode.phones.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AntennasList {

    private GUI gui = new GUI();

    public AntennasList(){
        gui.init("Antennas list", 6);
        gui.fillBlank();
        


        Thread drawThread = new Thread(()->{
            ArrayList<Antenna> antennas = Main.antennas;
            for (int i = 0; i < antennas.size(); i++) {
                Antenna antenna = antennas.get(i);
                gui.setItem(i, gui.createItem(Material.BEACON, 1, "&b" + antenna.getId(), (byte) 0,"&eCarrier: " + antenna.getCarrier(), "&eSignal strength: " + antenna.getSignalStrength(), "&dLocation: ",
                        "&eWorld: " + antenna.getLocation().getWorld().getName(), "&eX: " + antenna.getLocation().getX(), "&eY: " + antenna.getLocation().getY(), "&eZ: " + antenna.getLocation().getZ()));
                gui.setAction(i, (InventoryClickEvent event) -> {
                    AntennaManagementMenu antennaManagementMenu = new AntennaManagementMenu(antenna);
                    antennaManagementMenu.show((Player) event.getWhoClicked());
                    event.setCancelled(true);

                });
            }
            gui.setItem(53,gui.createItem(Material.ARROW,1,"&cGo back.", (byte) 0));
            gui.setAction(53,(InventoryClickEvent event) -> {
                 AntennasMenu antennasMenu = new AntennasMenu();
                 antennasMenu.show((Player) event.getWhoClicked());
            });
        });
        drawThread.start();


    }

    public void show(Player player){
        gui.openGui(player);
    }

}
