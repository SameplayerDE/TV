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

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.Classes.Elements.Channel;
import de.sameplayer.tv.Classes.Managers.ChannelManager;
import de.sameplayer.tv.Classes.Managers.ConnectionManager;
import de.sameplayer.tv.FontAwesome;
import de.sameplayer.tv.Fragments.MainFragment;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

public class PinnedChannelAdapter extends ArrayAdapter<Channel> {

    private final Context context;
    private final List<Channel> channelList;

    public PinnedChannelAdapter(@NonNull Context context, @NonNull List<Channel> channels) {
        super(context, R.layout.listview_pinned, channels);
        channelList = channels;
        this.context = context;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.listview_pinned, parent, false);

        rowView.setMinimumHeight(80);

        final Channel channel = channelList.get(position);

        final TextView titleView = (TextView) rowView.findViewById(R.id.name);
        //final TextView channelID = (TextView) rowView.findViewById(R.id.channelID);
        //final FontAwesome star = (FontAwesome) rowView.findViewById(R.id.fav);
        //final FontAwesome pin = (FontAwesome) rowView.findViewById(R.id.opt);
        //final ImageView preview = (ImageView) rowView.findViewById(R.id.preview);

        titleView.setText(channel.getProgram());
        //channelID.setText(channel.getChannel());

        rowView.findViewById(R.id.name).setSelected(true);
        //rowView.findViewById(R.id.show).setSelected(true);

        return rowView;
    }

}
