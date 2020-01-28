package me.linckode.phones;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.UUID;

public class Event implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
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
