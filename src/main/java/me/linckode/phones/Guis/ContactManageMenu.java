package me.linckode.phones.Guis;

import me.linckode.gapi.GUI;
import me.linckode.phones.ContactsList;
import me.linckode.phones.PhoneNumber;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContactManageMenu {

    GUI gui = new GUI();


    public ContactManageMenu(PhoneNumber phoneNumber, ContactsList contactsList){
        gui.init("Delete?",1);
        gui.setItem(0,GUI.createItem(Material.ARROW,1,"&cGo back.", (byte) 0));
        gui.setAction(0,(InventoryClickEvent event) ->{
            ContactsMenu contactsMenu = new ContactsMenu((Player) event.getWhoClicked());
            contactsMenu.show((Player) event.getWhoClicked());
            event.setCancelled(true);
        });

        gui.setItem(8,GUI.createItem(Material.BARRIER,1,"&4DELETE", (byte) 0));
        gui.setAction(8,(InventoryClickEvent event) ->{

            contactsList.getPhoneNumbers().remove(phoneNumber);
            ContactsMenu contactsMenu = new ContactsMenu((Player) event.getWhoClicked());
            contactsMenu.show((Player) event.getWhoClicked());

            event.setCancelled(true);
        });

    }
    void show(Player player){
        gui.openGui(player);
    }
}
