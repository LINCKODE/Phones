package me.linckode.phones.Guis;

import me.linckode.gapi.GUI;
import me.linckode.phones.ActionLinker;
import me.linckode.phones.ContactsList;
import me.linckode.phones.Main;
import me.linckode.phones.PhoneNumber;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

class PhoneNumberMenu {

    String senderPhoneNumber = "";

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

    PhoneNumberMenu(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;

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
            MainMenu mainMenu = new MainMenu((Player) event.getWhoClicked());
            mainMenu.show((Player) event.getWhoClicked());
        }, 36, 37, 45, 46);

        //Continue
        gui.multiSetItem(gui.createItem(Material.STAINED_GLASS_PANE, 1, "&eContinue", (byte) 4), 43, 44, 52, 53);
        gui.multiSetAction((InventoryClickEvent event) -> {
            if (!String.valueOf(phoneNumber).contains("X")){
                Thread thread = new Thread(()->{
                    String literalPhoneNumber = String.valueOf(phoneNumber);
                    event.getWhoClicked().closeInventory();
                    for (PhoneNumber phoneNumber : Main.PhoneNumberList){
                        if (Main.allPhoneNumberList.contains(literalPhoneNumber) && phoneNumber.getNumber().equals(literalPhoneNumber)){
                            //TODO: ADD OFFLINE PLAYER CAPABILITY
                            Player player = Bukkit.getPlayer(phoneNumber.getUUID());
                            if (player != null){
                                Thread thread1 = new Thread(() -> {
                                    event.getWhoClicked().sendMessage(ChatColor.YELLOW + "Type your message:");
                                    Main.actionLinkers.add(new ActionLinker((String message) ->{
                                        event.getWhoClicked().sendMessage(ChatColor.GREEN + "Message sent!");

                                        int time1 = (int) (MainMenu.calculateSignalDelay((Player) event.getWhoClicked())  * 1000);
                                        int time2 = (int) (MainMenu.calculateSignalDelay(player) * 1000);
                                        try {
                                            Thread.sleep(time1 + time2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        player.sendMessage(ChatColor.YELLOW + "Message from " + senderPhoneNumber + " (" + event.getWhoClicked().getName() + "):");
                                        player.sendMessage(ChatColor.YELLOW + message);
                                    }, (Player) event.getWhoClicked()));
                                });
                                thread1.start();
                            }
                            else {
                                event.getWhoClicked().sendMessage(ChatColor.RED + "The player you want to sent the message to is not online or phone number not found!");
                            }
                            break;
                        }
                    }
                });
                thread.start();
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

        //Add to contacts
        /*gui.multiSetItem(GUI.createItem(Material.NAME_TAG,1,"&aAdd to contacts", (byte) 0), 26);
        gui.multiSetAction((InventoryClickEvent event) ->{

            for (ContactsList contactsList : Main.contactsLists){
                if (contactsList.getOwner() == (Player) event.getWhoClicked()){
                    for (PhoneNumber phoneNumber1 : Main.PhoneNumberList){
                        if (phoneNumber1.getNumber().equals(String.valueOf(phoneNumber))){
                            ArrayList<PhoneNumber> phoneNumbers = contactsList.getPhoneNumbers();
                            phoneNumbers.add(new PhoneNumber(phoneNumber1.getUUID(),phoneNumber1.getNumber()));
                            contactsList.setPhoneNumbers(phoneNumbers);
                            break;
                        }
                    }
                    break;
                }
            }

            event.setCancelled(true);
        },26);
*/



    }

    void show(Player player) {
        gui.openGui(player);
    }


}
