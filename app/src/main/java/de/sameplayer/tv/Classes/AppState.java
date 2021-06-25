package de.sameplayer.tv.Classes;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import de.sameplayer.tv.Classes.Managers.ChannelManager;
import de.sameplayer.tv.Classes.Managers.ConnectionManager;
import de.sameplayer.tv.MainActivity;

public class AppState implements Serializable {

    private static AppState instance;

    //TV Server
    public int volume = 0;
    public boolean muted = false;
    public int zoomMain = 0;
    public int zoomPip = 0;
    public boolean showPip = false;
    public String channelMain = "";
    public String channelPip = "";
    public int debug = 0;
    public int standBy = 1;

    //Logic
    public transient boolean pip = false;
    public transient boolean pause = false;
    public transient boolean hasBeenPaused = false;


    public transient long pausedOn = 0;
    public transient long bufferedMillis = 0;
    public transient long startTimeShift = 0;

    private static File file;

    public AppState() {
        instance = this;
        file = new File(MainActivity.Instance.getFilesDir(), "appState.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
                saveToFile();
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
            ConnectionManager.processCommand("channelMain=" + instance.channelMain);
            ConnectionManager.processCommand("showPip=" + (isShowPip() ? 1 : 0));
            ConnectionManager.processCommand("zoomMain=" + getZoomMain());
            ConnectionManager.processCommand("zoomMPip=" + getZoomPip());
            ConnectionManager.processCommand("channelPip=" + instance.channelPip);
            ConnectionManager.processCommand("standby=" + instance.standBy);
            ConnectionManager.processCommand("debug=" + instance.debug);
            ConnectionManager.processCommand("timeShiftPlay=0");
        }).start();
    }

    public static int getVolume() {
        return instance.volume;
    }

    public static boolean isMuted() {
        return instance.muted;
    }

    public static boolean isPip() {
        return instance.pip;
    }

    public static boolean isPause() { return instance.pause;}

    public static int getZoomMain() {
        return instance.zoomMain;
    }

    public static int getZoomPip() {
        return instance.zoomPip;
    }

    public static boolean isShowPip() {
        return instance.showPip;
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

    public static int isStandBy() {
        return instance.standBy;
    }

    public static void setStandBy(int mode) {
        mode = Utils.ensureRange(mode, 0, 1);
        System.out.println(mode);
        if (isStandBy() == mode) {
            return;
        }
        instance.standBy = mode;
        new Thread(() -> {
            ConnectionManager.processCommand("standby=" + isStandBy());
        }).start();
    }

    public static void setPip(boolean pip) {
        if (isPip() == pip) {
            return;
        }
        instance.pip = pip;
        saveToFile();
        new Thread(() -> {
            ConnectionManager.processCommand("volume=" + (isMuted() ? 0 : instance.volume));
        }).start();
    }

    public static void setShowPip(boolean mode) {
        if (isShowPip() == mode) {
            return;
        }
        instance.showPip = mode;
        saveToFile();
        new Thread(() -> {
            ConnectionManager.processCommand("showPip=" + (isShowPip() ? 1 : 0));
        }).start();
    }

    public static void setZoomMain(int zoom) {
        zoom = Utils.ensureRange(zoom, 0, 1);
        System.out.println(zoom);
        if (getZoomMain() == zoom) {
            return;
        }
        instance.zoomMain = zoom;
        saveToFile();
        new Thread(() -> {
            ConnectionManager.processCommand("zoomMain=" + getZoomMain());
        }).start();
    }

    public static void setZoomPip(int zoom) {
        zoom = Utils.ensureRange(zoom, 0, 1);
        System.out.println(zoom);
        if (getZoomPip() == zoom) {
            return;
        }
        instance.zoomPip = zoom;
        saveToFile();
        new Thread(() -> {
            ConnectionManager.processCommand("zoomPip=" + getZoomPip());
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

    public static String getChannelMain() {
        return instance.channelMain;
    }

    public static String getChannelPip() {
        return instance.channelPip;
    }

    public static void setChannelMain(String channelMain) {
        instance.channelMain = channelMain;
        saveToFile();
        new Thread(() -> {
            ConnectionManager.processCommand("channelMain=" + channelMain);
        }).start();
    }

    public static void setChannelPip(String channelPip) {
        instance.channelPip = channelPip;
        saveToFile();
        new Thread(() -> {
            ConnectionManager.processCommand("channelPip=" + channelPip);
        }).start();
    }

    public static void setPause(boolean mode) {
        instance.pause = mode;
        if (mode) {
            instance.startTimeShift = System.currentTimeMillis();
            System.out.println("Paused On: " + instance.startTimeShift);
        }else{
            instance.bufferedMillis += System.currentTimeMillis() - instance.startTimeShift;
            instance.startTimeShift = 0L;
            System.out.println("Played On: " + instance.bufferedMillis);
        }
    }

    public static long getBufferedMillis() {
        return instance.bufferedMillis;
    }

    public static long getStartTimeShift() {
        return instance.startTimeShift;
    }

    public static void toLive() {
        instance.bufferedMillis = 0L;
        instance.startTimeShift = 0L;
    }

    private static void changeVolumeBy(int amount) {
        setVolume(getVolume() + amount);
    }

    public static boolean WasPaused() {
        return instance.hasBeenPaused;
    }


    public static void swapChannels() {
        String temp = instance.channelMain;
        instance.channelMain = instance.channelPip;
        instance.channelPip = temp;

        AppState.setChannelMain(instance.channelMain);
        AppState.setChannelPip(instance.channelPip);

    }

    public static void setDebug(int mode) {
        mode = Utils.ensureRange(mode, 0, 1);
        if (isDebug() == mode) {
            return;
        }
        instance.debug = mode;
        saveToFile();
        new Thread(() -> {
            ConnectionManager.processCommand("debug=" + isDebug());
        }).start();
    }

    public static int isDebug() {
        return instance.debug;
    }

    public static void reset() {
        instance = new AppState();
        try {
            file.delete();
            file.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        MainActivity.Instance.show("connection");
        saveToFile();
        loadFromFile();
        updateConnection();
    }
}
