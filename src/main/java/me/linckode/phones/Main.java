package me.linckode.phones;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
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
    public static ArrayList<ActionLinker> actionLinkers = new ArrayList<>();
    public static ArrayList<PhoneNumber> PhoneNumberList = new ArrayList<>();
    public static ArrayList<ContactsList> contactsLists = new ArrayList<>();
    void getAllNumbers(){
        Thread thread = new Thread(() -> {
            try{
                File folder = new File(getDataFolder() + File.separator + "players");
                for (final File file : folder.listFiles()) {
                    String number = Config.getString(file, "phoneNumber");
                    allPhoneNumberList.add(number);
                    PhoneNumberList.add(new PhoneNumber(UUID.fromString(file.getName().replace(".yml", "")),number));
                }
                this.getLogger().info("Successfully retrieved " + allPhoneNumberList.size() + " phone numbers!");
            }
            catch (NullPointerException e){
                this.getLogger().warning("No phone numbers found!");
            }
        });

        thread.start();
    }

    //config stuff
    public static File configFile;
    public static boolean enablePermissions;
    public static String permission;
    public static String managePermission;
    public static boolean enableChestCommands;
    public static String ChestCommandYML;
    public static String phoneNumberFormat;


    //Number management
    public static ArrayList<String> allPhoneNumberList = new ArrayList<>();
    public static ArrayList<UUID> UUIDList = new ArrayList<>();
    public static ArrayList<String> literalPhoneNumberList = new ArrayList<>();


    //Antenna stuff
    public static ArrayList<Antenna> antennas = new ArrayList<>();
    public void getAntennas(){
        Thread thread = new Thread(() -> {

            File folder = new File(getDataFolder() + File.separator + "antennas");
            try {
                for (final File file : folder.listFiles()) {

                    antennas.add(Antenna.loadFromConfig(Integer.parseInt(file.getName().replace(".yml",""))));
                }
                this.getLogger().info("Successfully retrieved " + antennas.size() + " antennas!");
            }
            catch (NullPointerException e){
                this.getLogger().warning("No antennas found!");
            }

        });

        thread.start();

    }



    public static void loadConfigData() {

        enablePermissions = Config.getBool(configFile, "enablePermissions");
        permission = Config.getString(configFile, "permission");
        enableChestCommands = Config.getBool(configFile, "enableChestCommands");
        ChestCommandYML = Config.getString(configFile, "ChestCommandsYML");
        phoneNumberFormat = Config.getString(configFile, "phoneNumberFormat");
        managePermission = Config.getString(configFile, "managePermission");

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