package me.linckode.phones;

import org.bukkit.Location;

public class Antenna {

    private  Location location;
    private int signalStrength;
    private int id;

    public Antenna(Location location, int signalStrength, int id){
        this.location = location;
        this.signalStrength = signalStrength;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public void setSignalStrength(int signalStrength){
        this.signalStrength = signalStrength;
    }

    public Location getLocation() {
        return location;
    }

    public int getSignalStrength() {
        return signalStrength;
    }
}
