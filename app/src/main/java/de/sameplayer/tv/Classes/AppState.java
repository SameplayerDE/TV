package de.sameplayer.tv.Classes;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import de.sameplayer.tv.Classes.Managers.ConnectionManager;
import de.sameplayer.tv.MainActivity;

public class AppState implements Serializable {

    private static AppState instance;

    public int volume = 0;
    public boolean muted = false;


    private static File file;

    public AppState() {
        instance = this;
        file = new File(MainActivity.Instance.getFilesDir(), "appState.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveToFile() {
        //saves current state to file appState.json
        new Thread(() -> {
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter(file, false));
                Gson gson = new Gson();
                writer.write(gson.toJson(instance));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public static void loadFromFile() {
        //loads file and sets parameters
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            Gson gson = new Gson();
            AppState appState = null;
            while ((line = bufferedReader.readLine()) != null) {
                appState = gson.fromJson(line, AppState.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateConnection() {
        new Thread(() -> {
            ConnectionManager.processCommand("volume=" + (isMuted() ? 0 : instance.volume));
        }).start();
    }

    public static int getVolume() {
        return instance.volume;
    }

    public static boolean isMuted() {
        return instance.muted;
    }

    public static void setMuted(boolean muted) {
        if (isMuted() == muted) {
            return;
        }
        instance.muted = muted;
        saveToFile();
        new Thread(() -> {
            ConnectionManager.processCommand("volume=" + (isMuted() ? 0 : instance.volume));
        }).start();
    }

    public static void setVolume(int volume) {
        volume = Utils.ensureRange(volume, 0, 100);
        if (getVolume() == volume) {
            return;
        }
        //if (isMuted()) {
        //    toggleMute();
        //}
        instance.volume = volume;
        saveToFile();
        new Thread(() -> {
            ConnectionManager.processCommand("volume=" + (isMuted() ? 0 : instance.volume));
        }).start();
    }

    public static void increaseVolume() {
        changeVolumeBy(1);
    }

    public static void decreaseVolume() {
        changeVolumeBy(-1);
    }

    public static void toggleMute() {
        setMuted(!isMuted());
    }

    private static void changeVolumeBy(int amount) {
        setVolume(getVolume() + amount);
    }

}
