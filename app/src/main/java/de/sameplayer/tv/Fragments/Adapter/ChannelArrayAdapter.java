package de.sameplayer.tv.Fragments.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.Classes.Elements.Channel;
import de.sameplayer.tv.Classes.Managers.ChannelManager;
import de.sameplayer.tv.Classes.Managers.ConnectionManager;
import de.sameplayer.tv.FontAwesome;
import de.sameplayer.tv.Fragments.MainFragment;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

public class ChannelArrayAdapter extends ArrayAdapter<Channel> {

    private final Context context;
    private final List<Channel> channelList;

    public ChannelArrayAdapter(@NonNull Context context, @NonNull List<Channel> channels) {
        super(context, R.layout.listview_channel, channels);
        channelList = channels;
        this.context = context;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.listview_channel, parent, false);

        rowView.setMinimumHeight(80);

        final Channel channel = channelList.get(position);

        final TextView titleView = (TextView) rowView.findViewById(R.id.name);
        final TextView channelID = (TextView) rowView.findViewById(R.id.channelID);
        final TextView show = (TextView) rowView.findViewById(R.id.show);
        final FontAwesome star = (FontAwesome) rowView.findViewById(R.id.fav);
        final FontAwesome pin = (FontAwesome) rowView.findViewById(R.id.opt);
        final ImageView preview = (ImageView) rowView.findViewById(R.id.preview);

        titleView.setText(channel.getProgram());
        channelID.setText(channel.getChannel());
        show.setText("");

        rowView.findViewById(R.id.name).setSelected(true);
        rowView.findViewById(R.id.show).setSelected(true);

        /*pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                channel.setPinned(!channel.isPinned());
                if (!channel.isPinned()) {
                    pin.setTextColor(context.getResources().getColor(R.color.gray));
                } else {
                    pin.setTextColor(context.getResources().getColor(R.color.white));
                }
                ChannelManager.save();
            }
        });*/

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> {
                    if (AppState.isPip()) {
                        AppState.setChannelPip(channelID.getText().toString());
                    }else{
                        AppState.setChannelMain(channelID.getText().toString());
                    }
                }).start();
            }
        });

        if (!channel.isFavorite()) {
            star.setTextColor(context.getResources().getColor(R.color.gray));
        } else {
            star.setTextColor(context.getResources().getColor(R.color.yellow));
        }

        if (!channel.isPinned()) {
            pin.setTextColor(context.getResources().getColor(R.color.gray));
        } else {
            pin.setTextColor(context.getResources().getColor(R.color.white));
        }

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                channel.setFavorite(!channel.isFavorite());
                if (!channel.isFavorite()) {
                    star.setTextColor(context.getResources().getColor(R.color.gray));
                } else {
                    star.setTextColor(context.getResources().getColor(R.color.yellow));
                }
                ChannelManager.save();
            }
        });

        return rowView;
    }

}
