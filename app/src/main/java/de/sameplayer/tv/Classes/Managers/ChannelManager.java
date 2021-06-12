package de.sameplayer.tv.Classes.Managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import de.sameplayer.tv.Classes.Elements.Channel;
import de.sameplayer.tv.MainActivity;

public class ChannelManager {

    ArrayList<Channel> channels = new ArrayList<>();

    ArrayList<Channel> pinnedChannels = new ArrayList<>();
    ArrayList<Channel> favoriteChannels = new ArrayList<>();

    MainActivity activity;

    File file;

    public ChannelManager(MainActivity activity) {
        this.activity = activity;
        file = new File(activity.getFilesDir(), "channels.json");
        file.delete();
        if (!file.exists()) {
            try {
                file.createNewFile();
                load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Load file...");
            load();
        }
    }

    public void add(Channel channel) {
        if(channel == null) {
            return;
        }
        channels.add(channel);
        if (channel.isFavorite()) {
            favoriteChannels.add(channel);
        }
        if (channel.isPinned()) {
            pinnedChannels.add(channel);
        }
    }

    public void save(Channel channel) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.newLine();
            Gson gson = new Gson();
            writer.write(gson.toJson(channel));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public ArrayList<Channel> getPinnedChannels() {
        return pinnedChannels;
    }

    public ArrayList<Channel> getFavoriteChannels() {
        return favoriteChannels;
    }

    public void add(ArrayList<Channel> channels) {
        for (Channel channel : channels) {
            add(channel);
        }
        update();
    }

    public void load() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            builder.serializeNulls();
            Gson gson = builder.create();
            while ((line = bufferedReader.readLine()) != null) {
                Channel channel = gson.fromJson(line, Channel.class);
                System.err.println(line);
                if(channel == null) {
                    continue;
                }
                channels.add(channel);
                if (channel.isFavorite()) {
                    favoriteChannels.add(channel);
                }
                if (channel.isPinned()) {
                    pinnedChannels.add(channel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Channels loaded from file: " + channels.size());
    }

    public void update() {
        BufferedWriter writer;
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            printWriter.flush();
            printWriter.close();
            writer = new BufferedWriter(new FileWriter(file));
            for (Channel channel : channels) {
                writer.newLine();
                Gson gson = new Gson();
                writer.write(gson.toJson(channel));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getByName(String name) {

    }

    public ArrayList<Channel> getPinned() {
        return pinnedChannels;
    }

    public ArrayList<Channel> getFavorite() {
        return favoriteChannels;
    }

}
