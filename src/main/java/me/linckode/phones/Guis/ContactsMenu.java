package me.linckode.phones.Guis;

import me.linckode.gapi.GUI;
import me.linckode.phones.ContactsList;
import me.linckode.phones.Main;
import me.linckode.phones.PhoneNumber;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class ContactsMenu {

    private GUI gui = new GUI();
    private ContactsList contactsList;
    private Player player;
    private int lastPage;

    public ContactsMenu(Player player){
        this.player = player;
        gui.init("Viewing contacts...", 6);
        gui.fillBlank();
        /*
        Thread runThread = new Thread(() ->{
            for (ContactsList contactsList : Main.contactsLists){
                if (contactsList.getOwner() == this.player){
                    this.contactsList = contactsList;
                    break;
                }
            }

            ArrayList<PhoneNumber> phoneNumbers = contactsList.getPhoneNumbers();
            for (PhoneNumber phoneNumber : phoneNumbers) {

                ItemStack itemStack = GUI.createItem(Material.SKULL,1,"&6" + Bukkit.getPlayer(phoneNumber.getUUID()), (byte) 0,"&e" + phoneNumber.getNumber(), "&cClick to delete.");
                SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(phoneNumber.getUUID()));
                itemStack.setItemMeta(skullMeta);
                gui.setItem(i,itemStack);
                gui.setAction(i,(InventoryClickEvent event) ->{
                    ContactManageMenu contactManageMenu = new ContactManageMenu(phoneNumber, contactsList);
                    contactManageMenu.show((Player) event.getWhoClicked());
                    event.setCancelled(true);
                });
            }


        });
        runThread.start();

*/

        //back Button
        gui.setItem(53, GUI.createItem(Material.ARROW,1,"&cGo back.", (byte) 0));
        gui.setAction(53,(InventoryClickEvent event) ->{
            MainMenu mainMenu = new MainMenu(player);
            mainMenu.show(player);
            event.setCancelled(true);
        });

    }



    public void show(Player player){
        gui.openGui(player);
    }

}
