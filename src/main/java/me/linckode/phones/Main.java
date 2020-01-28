package me.linckode.phones;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    void getAllNumbers(){
        Thread thread = new Thread(() -> {
            File folder = new File(getDataFolder() + File.separator + "players");
            for (final File file : folder.listFiles()) {
                allPhoneNumberList.add(Config.getString(file, "phoneNumber"));
            }
            this.getLogger().info("Successfully retrieved " + allPhoneNumberList.size() + " phone numbers!");
        });

        thread.start();
    }

    //config stuff
    public static File configFile;
    public static boolean enablePermissions;
    public static String permission;
    public static boolean enableChestCommands;
    public static String ChestCommandYML;
    public static String phoneNumberFormat;


    //Number management
    public static ArrayList<String> allPhoneNumberList = new ArrayList<>();
    public static ArrayList<UUID> UUIDList = new ArrayList<>();
    public static ArrayList<String> literalPhoneNumberList = new ArrayList<>();
    public static ArrayList<PhoneNumber> PhoneNumberList = new ArrayList<>();

    //Antenna stuff
    public static ArrayList<Antenna> antennas = new ArrayList<>();
    public void getAntennas(){
        Thread thread = new Thread(() -> {

            File folder = new File(getDataFolder() + File.separator + "antennas");
            for (final File file : folder.listFiles()) {
                World antennaWorld = Bukkit.getWorld(Config.getString(file, "world"));
                double antennaX = Config.getDouble(file, "x");
                double antennaY = Config.getDouble(file, "y");
                double antennaZ = Config.getDouble(file, "z");
                int antennaStrength = Config.getInt(file, "strength");
                int antennaId = Config.getInt(file, "id");
                Location antennaLocation = new Location(antennaWorld, antennaX, antennaY, antennaZ);
                antennas.add(new Antenna(antennaLocation, antennaStrength, antennaId));
            }
            this.getLogger().info("Successfully retrieved " + antennas.size() + " antennas!");
        });

        thread.start();

    }



    public static void loadConfigData() {

        enablePermissions = Config.getBool(configFile, "enablePermissions");
        permission = Config.getString(configFile, "permission");
        enableChestCommands = Config.getBool(configFile, "enableChestCommands");
        ChestCommandYML = Config.getString(configFile, "ChestCommandsYML");
        phoneNumberFormat = Config.getString(configFile, "phoneNumberFormat");

    }

    @Override
    public void onEnable() {

        this.getLogger().info("Retrieving antennas...");
        getAntennas();

        this.getLogger().info("Retrieving phone numbers...");
        getAllNumbers();

        instance = this;
        this.saveDefaultConfig();
        configFile = new File(getDataFolder() + File.separator + "config.yml");
        loadConfigData();

        this.getCommand("phone").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new Event(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Phones disabled.");
    }


}