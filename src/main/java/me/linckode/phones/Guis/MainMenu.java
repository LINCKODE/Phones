package me.linckode.phones.Guis;

import me.linckode.gapi.GUI;
import me.linckode.phones.Antenna;
import me.linckode.phones.Config;
import me.linckode.phones.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.text.DecimalFormat;

public class MainMenu {

    private GUI gui = new GUI();

    private String literal = "";

    public MainMenu(Player player) {

        float playerSignalDelay = MainMenu.calculateSignalDelay(player);
        Antenna closestAntenna = MainMenu.getClosestAntenna(player);
        boolean hasSignal;

        if (closestAntenna != null)
            gui.init("Phone (" + closestAntenna.getCarrier() + ")", 6);
        else
            gui.init("Phone (No carrier)", 6);

        gui.fillBlank();

        int signalModifier = 0;
        if (closestAntenna != null && closestAntenna.getLocation().distance(player.getLocation()) <= closestAntenna.getSignalStrength() * 1000) {
            hasSignal = true;
            double distance = closestAntenna.getLocation().distance(player.getLocation());
            float quarterOfRange = closestAntenna.getSignalStrength() * 250;
            if (distance <= quarterOfRange)
                signalModifier = 1;
            else if (distance <= quarterOfRange * 2)
                signalModifier = 2;
            else if (distance <= quarterOfRange * 3)
                signalModifier = 3;
            else if (distance < quarterOfRange * 4)
                signalModifier = 4;

            int tempIndex = 5 - signalModifier;
            for (int index = 0; index < tempIndex; index++) {
                int drawingIndex = 8 - index;
                gui.setItem(drawingIndex, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&aSignal", (byte) 5, "&2" + playerSignalDelay + "s &2delay"));
            }
        } else {
            gui.setItem(8, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&cNo Signal", (byte) 14));
            hasSignal = false;
        }


        //MSG button
        gui.multiSetItem(gui.createItem(Material.STAINED_GLASS_PANE, 1, "&eSend Message", (byte) 4, "&6Click to send a", "&6message to a friend"), 36, 37, 45, 46);
        //MSG action
        gui.multiSetAction((InventoryClickEvent event) -> {
            if (hasSignal) {
                PhoneNumberMenu phoneNumberMenu = new PhoneNumberMenu(literal);
                phoneNumberMenu.show((Player) event.getWhoClicked());
            }

            event.setCancelled(true);
        }, 36, 37, 45, 46);

        //Contacts buttons
        gui.multiSetItem(gui.createItem(Material.STAINED_GLASS_PANE, 1, "&6Contact List", (byte) 1, "&eClick to open", "&eThe Contact List", "&6&lComing soon..."), 43, 44, 52, 53);
        /*gui.multiSetAction((InventoryClickEvent event) ->{

            ContactsMenu contactsMenu = new ContactsMenu((Player) event.getWhoClicked());
            contactsMenu.show((Player) event.getWhoClicked());

            event.setCancelled(true);
        },43,44,52,53);*/

        //TODO: ADD
        //settings button
        //gui.multiSetItem(gui.createItem(Material.STAINED_GLASS_PANE, 1, "&9Phone", (byte) 11, "&9settings"), 48, 49, 50);

        //Other ChestCommands
        if (Main.enableChestCommands) {
            //"other" button
            gui.multiSetItem(gui.createItem(Material.STAINED_GLASS_PANE, 1, "&cOther", (byte) 14), 25, 26);

            //"other action"
            gui.multiSetAction((InventoryClickEvent event) -> {
                ConsoleCommandSender console = Bukkit.getConsoleSender();
                Bukkit.dispatchCommand(console, "chestcommands open something.yml " + event.getWhoClicked().getName());

                event.setCancelled(true);
            }, 25, 26);
        }

        if (player.hasPermission(Main.managePermission)) {
            gui.setItem(40, gui.createItem(Material.REDSTONE, 1, "&4Manage Phones", (byte) 0));
            gui.setAction(40, (InventoryClickEvent event) -> {

                ManagementMenu managementMenu = new ManagementMenu();
                managementMenu.show((Player) event.getWhoClicked());
                event.setCancelled(true);
            });
        }
    }

    public static Antenna getClosestAntenna(Player player) {
        if (!Main.antennas.isEmpty()) {
            Antenna closestAntenna = Main.antennas.get(0);
            double closestAntennaDistance = closestAntenna.getLocation().distance(player.getLocation());
            for (Antenna antenna : Main.antennas) {
                if (closestAntennaDistance > antenna.getLocation().distance(player.getLocation())) {
                    closestAntenna = antenna;
                    closestAntennaDistance = antenna.getLocation().distance(player.getLocation());
                }
            }
            return closestAntenna;
        } else return null;
    }

    public static float calculateSignalDelay(Player player) {

        if (!Main.antennas.isEmpty()) {
            Antenna closestAntenna = Main.antennas.get(0);
            double closestAntennaDistance = closestAntenna.getLocation().distance(player.getLocation());
            for (Antenna antenna : Main.antennas) {
                if (closestAntennaDistance > antenna.getLocation().distance(player.getLocation())) {
                    closestAntenna = antenna;
                    closestAntennaDistance = antenna.getLocation().distance(player.getLocation());
                }
            }

            if (closestAntennaDistance <= 1000 * closestAntenna.getSignalStrength()) {
                DecimalFormat df = new DecimalFormat("#.##");
                float delay = Float.parseFloat(df.format(closestAntennaDistance / 100));
                return delay;
            } else return -1;
        } else return -1;
    }

    public void show(Player player) {
        String literalPhoneNumber = Config.getString(new File(Main.getInstance().getDataFolder() + File.separator + "players" + File.separator + player.getUniqueId() + ".yml"), "phoneNumber");
        this.literal = literalPhoneNumber;
        gui.setItem(0, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&2Your phone number:", (byte) 5, "&a" + literalPhoneNumber));
        gui.setAction(0, (InventoryClickEvent event) -> {
            event.getWhoClicked().sendMessage(ChatColor.DARK_GREEN + "Your phone number is: " + ChatColor.GREEN + literalPhoneNumber);
            event.setCancelled(true);
        });
        gui.openGui(player);
    }

}
