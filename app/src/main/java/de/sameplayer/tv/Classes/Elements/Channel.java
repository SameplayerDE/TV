package de.sameplayer.tv.Classes.Elements;

import java.io.Serializable;

public class Channel implements Serializable {

    public int frequency;
    public String channel;
    public int quality;
    public String program;
    public String provider;

    public boolean Favorite = false;
    public boolean Pinned = false;

    public int getFrequency() {
        return frequency;
    }

    public String getChannel() {
        return channel;
    }

    public int getQuality() {
        return quality;
    }

    public String getProgram() {
        return program;
    }

    public String getProvider() {
        return provider;
    }

    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }

    public void setPinned(boolean pinned) {
        Pinned = pinned;
    }

    public boolean isFavorite() {
        return Favorite;
    }

    public boolean isPinned() {
        return Pinned;
    }
}
