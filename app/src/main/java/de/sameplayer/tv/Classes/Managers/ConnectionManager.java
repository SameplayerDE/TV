package de.sameplayer.tv.Classes.Managers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.HttpRequest;

public class ConnectionManager {

    private static HttpRequest httpRequest;
    private static boolean connection = false;
    private static boolean init = false;

    private static String STATUS_OK = "ok";
    private static String STATUS_FAIL = "failed";

    public static void init() {
        try {
            httpRequest = new HttpRequest("10.0.2.2", 6000, false);
            //httpRequest = new HttpRequest("192.168.178.63", 6000, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        init = true;
    }


    public static boolean hasConnection() {
        return connection;
    }

    public static boolean establishConnection() {
        JSONObject result = processCommand();
        if (result == null) {
            return connection;
        }
        if (result.has("status")) {
            try {
                if (result.getString("status").equalsIgnoreCase(STATUS_OK)) {
                    connection = true;
                }
                return connection;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static JSONObject processCommand(String... commands) {
        if (!init) {
            System.err.println("INIT() has not been called");
            return null;
        }
        try {
            //String.join("&", commands);
            String processedCommands = "";
            int i = 1;
            for (String command : commands) {
                processedCommands += command;
                if (commands.length > 1 && i < commands.length) {
                    processedCommands += "&";
                }
                i++;
            }
            return httpRequest.execute(processedCommands);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void powerOff() {
        processCommand("powerOff");
        connection = false;
        init = false;
    }

    private static JSONObject changeVolumeBy(int amount) {
        return processCommand("volume=" + (AppState.getVolume() + amount));
    }

    public static JSONObject IncreaseVolume() {
        return changeVolumeBy(1);
    }

    public static JSONObject IncreaseVolume(int amount) {
        return changeVolumeBy(amount);
    }

    public static JSONObject scanChannels() {
        //new Thread(() -> {
        //    JSONObject jsonObject = ConnectionManager.scanChannels();
        //    System.out.println(jsonObject.toString());
        //}).start();
        return processCommand("scanChannels");
    }

}
