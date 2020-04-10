package me.linckode.phones;

import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;

public class Antenna {

    private Location location;
    private int signalStrength;
    private int id;
    private String carrier;

    public Antenna(Location location, int signalStrength, int id, String carrier){
        this.location = location;
        this.signalStrength = signalStrength;
        this.id = id;
        this.carrier = carrier;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
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

    public void saveToConfig(){

        File antennaFile = new File(Main.getInstance().getDataFolder() + File.separator + "antennas" + File.separator + this.id + ".yml");

        Config.set(antennaFile,"id", id);
        Config.set(antennaFile,"signalStrength", signalStrength);
        int x = (int) location.getX();
        int y = (int) location.getY();
        int z = (int) location.getZ();
        World world = location.getWorld();

        Config.set(antennaFile, "x", x);
        Config.set(antennaFile, "y", y);
        Config.set(antennaFile, "z", z);
        Config.set(antennaFile, "world", world.getName());
        Config.set(antennaFile, "carrier", carrier);

    }
    public static Antenna loadFromConfig(int id){
        File antennaFile = new File(Main.getInstance().getDataFolder() + File.separator + "antennas" + File.separator + id + ".yml");
        int x = (int) Config.getDouble(antennaFile, "x");
        int y = (int) Config.getDouble(antennaFile, "y");
        int z = (int) Config.getDouble(antennaFile, "z");
        int signalStrength = Config.getInt(antennaFile, "signalStrength");
        World world = Main.getInstance().getServer().getWorld(Config.getString(antennaFile, "world"));
        Location location = new Location(world, x, y, z);
        String carrier = Config.getString(antennaFile, "carrier");
        return new Antenna(location, signalStrength, id, carrier);
    }

}
