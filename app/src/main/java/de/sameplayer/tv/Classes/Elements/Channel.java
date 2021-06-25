package de.sameplayer.tv.Classes.Elements;

import java.io.Serializable;

import de.sameplayer.tv.Classes.Managers.ChannelManager;

public class Channel implements Serializable {

    public int frequency;
    public String channel;
    public int quality;
    public String program;
    public String provider;

    //public boolean Favorite = false;
    //public boolean Pinned = false;

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
        if (favorite) {
            if (isFavorite()) {
                return;
            }else{
                ChannelManager.getFavoriteChannels().add(channel);
            }
        } else {
            if (!isFavorite()) {
                return;
            }else{
                ChannelManager.getFavoriteChannels().remove(channel);
            }
        }
    }

    public void setPinned(boolean pinned) {
        if (pinned) {
            if (isPinned()) {
                return;
            }else{
                ChannelManager.getPinnedChannels().add(channel);
            }
        } else {
            if (!isPinned()) {
                return;
            }else{
                ChannelManager.getPinnedChannels().remove(channel);
            }
        }
    }

    public boolean isFavorite() {
        return ChannelManager.isFavorite(channel);
    }

    public boolean isPinned() {
        return ChannelManager.isPinned(channel);
    }
}
