package kafka.data;

import java.util.Random;

public class RandomDataGenerator {
    private long lat;
    private long lon;
    private String Phone;
    private String UserId;

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLon() {
        return lon;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }
    public RandomDataGenerator() {
        generateRandomLatLon();
        generateRandomUserId();
        generateRandomPhone();

    }
    public void generateRandomLatLon (){
        // Define the range for latitude and longitude in degrees
        int minLat = -90;
        int maxLat = 90;
        int minLon = -180;
        int maxLon = 180;

        // Create a Random object
        Random random = new Random();

        // Generate random latitude and longitude values
        long latitude = (long) minLat + (long) (random.nextDouble() * (maxLat - minLat));
        long longitude = (long) minLon + (long) (random.nextDouble() * (maxLon - minLon));
        setLat(latitude);
        setLon(longitude);
    }
    public void generateRandomUserId(){
        int length = 10; // You can adjust the length as needed
        StringBuilder sb = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        setUserId(sb.toString());
    }
    public void generateRandomPhone(){
        int length = 10; // You can adjust the length as needed
        StringBuilder sb = new StringBuilder();
        String digits = "0123456789";
        Random random = new Random();
        for (int i = 1; i < length; i++) {
            int index = random.nextInt(digits.length());
            sb.append(digits.charAt(index));
        }
        String phone;
        phone = "0" + sb;
        setPhone(phone);
    }

}
