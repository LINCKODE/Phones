package me.linckode.phones.Guis.Management;

import me.linckode.gapi.GUI;
import me.linckode.phones.Antenna;
import me.linckode.phones.Config;
import me.linckode.phones.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;

public class AntennasMenu {

    private GUI gui = new GUI();

    public AntennasMenu(){

        gui.init("Antennas", 6);
        gui.fillBlank();

        gui.setItem(0, gui.createItem(Material.ANVIL,1,"&2Create new antenna", (byte) 0,"&aClicking this will", "&acreate a new antenna", "&aat your current location"));
        /*gui.setAction(0, (InventoryClickEvent event) ->{
            File folder = new File(Main.getInstance().getDataFolder() + File.separator + "antennas");
            for (final File file : folder.){

            }

                //TODO: make id file name
                
            Antenna antenna = new Antenna(event.getWhoClicked().getLocation(), 0);
            Location antennaLocation = antenna.getLocation();

            Config.set();


            Main.antennas.add(antenna);

            Thread thread = new Thread(() -> {

                File folder = new File(Main.getInstance().getDataFolder() + File.separator + "antennas");
                for (final File file : folder.listFiles()) {
                    World antennaWorld = Bukkit.getWorld;
                    double antennaX = Config.getDouble(file, "x");
                    double antennaY = Config.getDouble(file, "y");
                    double antennaZ = Config.getDouble(file, "z");
                    int antennaStrength = Config.getInt(file, "strength");
                    Location antennaLocation = new Location(antennaWorld, antennaX, antennaY, antennaZ);
                    antennas.add(new Antenna(antennaLocation, antennaStrength));
                }
                this.getLogger().info("Successfully retrieved " + antennas.size() + " antennas!");
            });

            thread.start();

            event.getWhoClicked().sendMessage(ChatColor.GREEN + "Antenna added, don't forget to set it's range!");
            event.setCancelled(true);
        });*/

        gui.setItem(1, gui.createItem(Material.BOOK, 1, "&bAntennas List", (byte) 0));


        //get antennas from static array in main
        //do stuff


    }

    public void show(Player player){
        gui.openGui(player);
    }

}
