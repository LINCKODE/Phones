package me.linckode.phones;

import me.linckode.phones.Guis.MainMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.UUID;

public class Event implements Listener {


    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if (event.getHand() == EquipmentSlot.HAND && event.getMaterial() == Material.NETHER_BRICK_ITEM && event.getItem().hasItemMeta()){
            ItemMeta itemMeta = event.getItem().getItemMeta();
            if (itemMeta.getDisplayName().equals(Commands.phoneItem.getItemMeta().getDisplayName()) && itemMeta.getLore().equals(Commands.phoneItem.getItemMeta().getLore())){

                MainMenu mainMenu = new MainMenu(event.getPlayer());
                mainMenu.show(event.getPlayer());

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event){
        Thread thread = new Thread(()->{
            if (!Main.actionLinkers.isEmpty()){
                for (ActionLinker actionLinker : Main.actionLinkers){
                    if (actionLinker.getPlayer() == event.getPlayer()){
                        actionLinker.getMessageAction().execute(event.getMessage());
                        event.setCancelled(true);
                        Main.actionLinkers.remove(actionLinker);
                        break;
                    }
                }
            }
        });
        thread.start();

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Main.contactsLists.add(ContactsList.getFromConfig(event.getPlayer()));


        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        File playerFile = new File(Main.getInstance().getDataFolder() + File.separator + "players" + File.separator + uuid + ".yml");

        Main.UUIDList.add(uuid);

        if (playerFile.exists()){

            String literalNumber = Config.getString(playerFile, "phoneNumber");

            PhoneNumber number = new PhoneNumber(uuid, literalNumber);
            Main.PhoneNumberList.add(number);
            Main.literalPhoneNumberList.add(literalNumber);

        }

        else {

            String literalNumber = PhoneNumber.newRandom(Main.phoneNumberFormat);
            Config.set(playerFile,"phoneNumber", literalNumber);
            PhoneNumber number = new PhoneNumber(uuid,literalNumber);
            Main.allPhoneNumberList.add(literalNumber);
            Main.PhoneNumberList.add(number);
            Main.literalPhoneNumberList.add(literalNumber);
        }
    }


    @EventHandler
    public void onDisconnect(PlayerQuitEvent event){

        Thread thread = new Thread(() ->{
            for (ContactsList contactsList : Main.contactsLists){
                if (contactsList.getOwner().getUniqueId() == event.getPlayer().getUniqueId()){
                    contactsList.saveToConfig();
                    Main.contactsLists.remove(contactsList);
                    break;
                }
            }
        });
        thread.start();

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        File playerFile = new File(Main.getInstance().getDataFolder() + File.separator + "players" + File.separator + uuid + ".yml");

        Main.UUIDList.remove(uuid);

            String literalNumber = Config.getString(playerFile, "phoneNumber");
            PhoneNumber tempPhoneNumber = null;

            for (PhoneNumber phoneNumber : Main.PhoneNumberList){
                if (phoneNumber.getUUID() == uuid)
                    tempPhoneNumber = phoneNumber;
                    Main.literalPhoneNumberList.remove(literalNumber);
            }
            Main.PhoneNumberList.remove(tempPhoneNumber);


    }
}
