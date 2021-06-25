package de.sameplayer.tv.Classes.Managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.Classes.Elements.Channel;
import de.sameplayer.tv.MainActivity;

public final class ChannelManager {

    static ArrayList<Channel> loadedChannels = new ArrayList<>();

    static ArrayList<String> pinnedChannels = new ArrayList<>();
    static String[] pinnedMainChannels = new String[3];
    static ArrayList<String> favoriteChannels = new ArrayList<>();

    public static JSONObject channelJson;

    static File pinned;
    static File pinnedMain;
    static File favorites;

    public ChannelManager() {
        pinned = new File(MainActivity.Instance.getFilesDir(), "pinned.json");
        pinnedMain = new File(MainActivity.Instance.getFilesDir(), "pinnedMain.json");
        favorites = new File(MainActivity.Instance.getFilesDir(), "favorites.json");

        checkPinned();
        checkPinnedMain();
        checkFavorites();

        save();
    }

    private static void checkPinned() {
        if (!pinned.exists()) {
            try {
                pinned.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Load file...");
            loadPinned();
        }
    }

    private static void checkPinnedMain() {
        if (!pinnedMain.exists()) {
            try {
                pinnedMain.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Load file...");
            loadPinned();
        }
    }

    private static void checkFavorites() {
        if (!favorites.exists()) {
            try {
                favorites.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Load file...");
            loadFavorites();
        }
    }

    public static boolean hasChannel(String name) {
        for ( Channel c : loadedChannels) {
            if (c.channel.equalsIgnoreCase(name)) {
                return true;
            }
            System.err.println(name);
        }
        System.err.println("NO CHANNEL WITH" + name);
        return false;
    }

    public static Channel getChannelBy(String name) {
        for ( Channel c : loadedChannels) {
            if (c.channel.equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    private static void loadPinned() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pinned));
            String line;
            String content = "";
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            while ((line = bufferedReader.readLine()) != null) {
                content += line;
            }
            pinnedChannels = gson.fromJson(content, ArrayList.class);
            if (pinnedChannels == null) {
                pinnedChannels = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadPinnedMain() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pinnedMain));
            String line;
            String content = "";
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            while ((line = bufferedReader.readLine()) != null) {
                content += line;
            }
            pinnedMainChannels = gson.fromJson(content, String[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadFavorites() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(favorites));
            String line;
            String content = "";
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            while ((line = bufferedReader.readLine()) != null) {
                content += line;
            }
            favoriteChannels = gson.fromJson(content, ArrayList.class);
            if (favoriteChannels == null) {
                favoriteChannels = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void savePinned() {
        BufferedWriter writer;
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(pinned));
            printWriter.flush();
            printWriter.close();
            writer = new BufferedWriter(new FileWriter(pinned, true));
            writer.newLine();
            Gson gson = new Gson();
            writer.write(gson.toJson(pinnedChannels));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void savePinnedMain() {
        BufferedWriter writer;
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(pinnedMain));
            printWriter.flush();
            printWriter.close();
            writer = new BufferedWriter(new FileWriter(pinnedMain, true));
            writer.newLine();
            Gson gson = new Gson();
            writer.write(gson.toJson(pinnedMainChannels));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveFavorites() {
        BufferedWriter writer;
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(favorites));
            printWriter.flush();
            printWriter.close();
            writer = new BufferedWriter(new FileWriter(favorites, true));
            writer.newLine();
            Gson gson = new Gson();
            writer.write(gson.toJson(favoriteChannels));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        saveFavorites();
        savePinned();
    }

    public static void setChannelJson(JSONObject json) {
        if (json == null) return;
        channelJson = json;
        System.out.println("Channels recieved...");
        System.out.println(json.toString());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Channel>>(){}.getType();
        // In this test code i just shove the JSON here as string.
        List<Channel> asd = null;
        try {
            asd = gson.fromJson(json.getString("channels"), listType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (Channel c: asd) {
            add(c);
        }
        if (AppState.getChannelMain() == "") {
            AppState.setChannelMain(getChannels().get(0).channel);
        }
        if (AppState.getChannelPip() == "") {
            AppState.setChannelPip(getChannels().get(0).channel);
        }
    }

    private static void add(Channel c) {
        for (Channel cc : loadedChannels) {
            if (cc.program.equalsIgnoreCase(c.program)) {
                if (cc.quality <= c.quality) {
                    loadedChannels.remove(cc);
                    loadedChannels.add(c);
                }
                return;
            }
        }
        loadedChannels.add(c);
    }

    public static ArrayList<Channel> getChannels() {
        return loadedChannels;
    }

    public static ArrayList<String> getPinnedChannels() {
        return pinnedChannels;
    }

    public static ArrayList<String> getFavoriteChannels() {
        return favoriteChannels;
    }

    public static boolean isPinned(String channel) {
        if (pinnedChannels == null) return false;
        return pinnedChannels.contains(channel);
    }

    public static boolean isFavorite(String channel) {
        if (favoriteChannels == null) return false;
        return favoriteChannels.contains(channel);
    }

    public static ArrayList<Channel> getSorted() {
        ArrayList<Channel> sorted = new ArrayList<>();
        for (Channel c: loadedChannels) {
            if (c.isFavorite()) {
                System.out.println("Fab: " + c.program);
                sorted.add(c);
            }
        }
        for (Channel c: loadedChannels) {
            if (!c.isFavorite()) {
                sorted.add(c);
            }
        }

        for (Channel c : sorted) {

            System.out.println("Sroted: " + c.channel);

        }

        return sorted;
    }

    public static void pin(int i, Channel c) {
        pinnedMainChannels[i] = c.channel;
    }

    public static String[] getPinnedMainChannels() {
        return pinnedMainChannels;
    }

    public static ArrayList<Channel> getPinned() {
        ArrayList<Channel> sorted = new ArrayList<>();
        for (Channel c: loadedChannels) {
            if (c.isPinned()) {
                System.out.println("Fab: " + c.program);
                sorted.add(c);
            }
        }
        return sorted;
    }

}
