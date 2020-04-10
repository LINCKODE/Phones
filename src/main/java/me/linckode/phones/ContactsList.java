package me.linckode.phones;

import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class ContactsList {

    Player owner;
    ArrayList<PhoneNumber> phoneNumbers;

    public ContactsList(Player owner, ArrayList<PhoneNumber> phoneNumbers) {
        this.owner = owner;
        this.phoneNumbers = phoneNumbers;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public ArrayList<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void saveToConfig(){
        File contactListFile = new File(Main.getInstance().getDataFolder() + File.separator + "contactLists" + File.separator + this.owner.getUniqueId().toString() + ".yml");

        Config.set(contactListFile,"count", phoneNumbers.size());

        for (int i = 0; i < phoneNumbers.size(); i++) {
            PhoneNumber phoneNumber = phoneNumbers.get(i);
            Config.set(contactListFile, String.valueOf(i+1), phoneNumber.uuid + " " + phoneNumber.number);
        }
    }

    public static ContactsList getFromConfig(Player player){
        File contactListFile = new File(Main.getInstance().getDataFolder() + File.separator + "contactLists" + File.separator + player.getUniqueId().toString() + ".yml");
        int count = Config.getInt(contactListFile, "count");
        ArrayList<PhoneNumber> phoneNumbers = new ArrayList<>();

        for (int i = 0; i < count; i++){
            String data = Config.getString(contactListFile, String.valueOf(i + 1));
            String[] parsedData = data.split("\\s");
            phoneNumbers.add(new PhoneNumber(UUID.fromString(parsedData[0]), parsedData[1]));
        }
        return new ContactsList(player,phoneNumbers);
    }
}
