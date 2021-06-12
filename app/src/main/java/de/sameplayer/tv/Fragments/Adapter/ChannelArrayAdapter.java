package de.sameplayer.tv.Fragments.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import de.sameplayer.tv.Classes.Elements.Channel;
import de.sameplayer.tv.FontAwesome;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

public class ChannelArrayAdapter extends ArrayAdapter<Channel> {

    private final Context context;
    private ListView listView;
    private List<Channel> channels;

    public ChannelArrayAdapter(@NonNull Context context, @NonNull List<Channel> channels) {
        super(context, R.layout.listview_channel, channels);
        this.context = context;
        this.channels = channels;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.listview_channel, parent, false);

        rowView.setMinimumHeight(80);

        final Channel channel = channels.get(position);

        final TextView titleView = (TextView) rowView.findViewById(R.id.name);
        final FontAwesome star = (FontAwesome) rowView.findViewById(R.id.fav);
        final FontAwesome opt = (FontAwesome) rowView.findViewById(R.id.opt);

        titleView.setText(channel.getProgram());

        if (channel.Favorite)
            star.setTextColor(context.getResources().getColor(R.color.yellow));

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                channel.setFavorite(!channel.isFavorite());
                if (!channel.Favorite)
                    star.setTextColor(context.getResources().getColor(R.color.gray));
                if (channel.Favorite)
                    star.setTextColor(context.getResources().getColor(R.color.yellow));
                MainActivity.ChannelManager.update();
            }
        });

        return rowView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

}
