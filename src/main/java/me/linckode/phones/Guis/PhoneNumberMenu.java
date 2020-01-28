package me.linckode.phones.Guis;

import me.linckode.gapi.GUI;
import me.linckode.phones.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

class PhoneNumberMenu {

    private GUI gui = new GUI();
    private String format = Main.phoneNumberFormat;
    private char[] phoneNumber = format.toCharArray();
    private int index = 0;

    private void updateNumber() {
        gui.setItem(49, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&6Phone Number:", (byte) 1, "&e" + String.valueOf(phoneNumber)));

    }

    void addDigit(int digit) {
        if (index >= 0 && index < phoneNumber.length) {

            if (phoneNumber[index] == 'X') {
                phoneNumber[index] = Character.forDigit(digit, 10);
                index++;
            } else {
                while (phoneNumber[index] != 'X') {
                    index++;
                }
                if (phoneNumber[index] == 'X') {
                    phoneNumber[index] = Character.forDigit(digit, 10);
                    index++;
                }
            }
            updateNumber();
        }

    }

    PhoneNumberMenu() {

        gui.init("Insert phone number:", 6);
        gui.fillBlank();
        updateNumber();
        //Digits
        
        gui.setItem(3, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&71", (byte) 7));
        gui.setItem(4, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&72", (byte) 7));
        gui.setItem(5, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&73", (byte) 7));
        gui.setItem(12, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&74", (byte) 7));
        gui.setItem(13, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&75", (byte) 7));
        gui.setItem(14, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&76", (byte) 7));
        gui.setItem(21, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&77", (byte) 7));
        gui.setItem(22, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&78", (byte) 7));
        gui.setItem(23, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&79", (byte) 7));
        gui.setItem(31, gui.createItem(Material.STAINED_GLASS_PANE, 1, "&70", (byte) 7));

        gui.setAction(3, (InventoryClickEvent event) -> {
            addDigit(1);
            event.setCancelled(true);
        });
        gui.setAction(4, (InventoryClickEvent event) -> {
            addDigit(2);
            event.setCancelled(true);
        });
        gui.setAction(5, (InventoryClickEvent event) -> {
            addDigit(3);
            event.setCancelled(true);
        });
        gui.setAction(12, (InventoryClickEvent event) -> {
            addDigit(4);
            event.setCancelled(true);
        });
        gui.setAction(13, (InventoryClickEvent event) -> {
            addDigit(5);
            event.setCancelled(true);
        });
        gui.setAction(14, (InventoryClickEvent event) -> {
            addDigit(6);
            event.setCancelled(true);
        });
        gui.setAction(21, (InventoryClickEvent event) -> {
            addDigit(7);
            event.setCancelled(true);
        });
        gui.setAction(22, (InventoryClickEvent event) -> {
            addDigit(8);
            event.setCancelled(true);
        });
        gui.setAction(23, (InventoryClickEvent event) -> {
            addDigit(9);
            event.setCancelled(true);
        });
        gui.setAction(31, (InventoryClickEvent event) -> {
            addDigit(0);
            event.setCancelled(true);
        });

        //Go Back
        gui.multiSetItem(gui.createItem(Material.STAINED_GLASS_PANE, 1, "&cGo Back", (byte) 14), 36, 37, 45, 46);
        gui.multiSetAction((InventoryClickEvent event) -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.show((Player) event.getWhoClicked());
        }, 36, 37, 45, 46);

        //Continue
        gui.multiSetItem(gui.createItem(Material.STAINED_GLASS_PANE, 1, "&eContinue", (byte) 4), 43, 44, 52, 53);
        gui.multiSetAction((InventoryClickEvent event) -> {
            if (!String.valueOf(phoneNumber).contains("X")){
                //TODO: ADD SEND MESSAGE FUNCTIONALITY
            }
            event.setCancelled(true);
        }, 43, 44, 52, 53);

        //Reset
        gui.multiSetItem(gui.createItem(Material.STAINED_GLASS_PANE, 1, "&cReset Number", (byte) 14), 16, 17);
        gui.multiSetAction((InventoryClickEvent event) -> {
            index = 0;
            phoneNumber = format.toCharArray();
            updateNumber();
            event.setCancelled(true);
        }, 16, 17);



    }

    void show(Player player) {
        gui.openGui(player);
    }


}
