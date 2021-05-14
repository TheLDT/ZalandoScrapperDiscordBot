package main;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static main.Bot.BOT_TOKEN;
import static main.Bot.PREFIX;

public class Settings {
    private static final String FILE_NAME = "properties.txt";
    
    protected static void readSettings() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(FILE_NAME));
            PREFIX = p.getProperty("prefix");
            BOT_TOKEN = p.getProperty("bot_token");
        } catch (IOException ex) {
            System.out.println("No file");
        }
    }
    
    public static String readSetting(String key) {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(FILE_NAME));
            return p.getProperty(key);
        } catch (IOException ex) {
            System.out.println("No file");
        }
        return "";
    }
    
    public static void updateSetting(String key, String value){
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(FILE_NAME));
            p.setProperty(key, value);
            p.store(new FileWriter(FILE_NAME), "");
        } catch (IOException ex) {
            System.out.println("No file");
        }
    }
}