package me.linckode.phones;

import java.util.Random;
import java.util.UUID;

public class PhoneNumber {

     UUID uuid;
     String number;

     public UUID getUUID(){
         return uuid;
     }
     public String getNumber(){
         return number;
     }

    public PhoneNumber(UUID uuid, String number){
        this.uuid = uuid;
        this.number = number;

    }

    public static String newRandom(String format) {

         boolean isUnique = false;

         String literalNumber = "";

         while (!isUnique) {
             char[] chArray = format.toCharArray();
             for (int index = 0; index < format.length(); index++) {
                 if (chArray[index] == 'X') {
                     chArray[index] = Character.forDigit(randomInt(0, 9), 10);
                 }
             }

             literalNumber = String.valueOf(chArray);

             if (!Main.allPhoneNumberList.contains(literalNumber)) {
                 isUnique = true;

             }

         }
        return literalNumber;
    }

    private static int randomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
