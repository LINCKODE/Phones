package me.linckode.phones.Guis.Management;

import me.linckode.gapi.GUI;
import org.bukkit.entity.Player;

public class AntennasList {

    private GUI gui = new GUI();

    public AntennasList(){
        gui.init("Antennas list", 6);
        gui.fillBlank();
        

        //for ()

    }

    public void show(Player player){
        gui.openGui(player);
    }

}
