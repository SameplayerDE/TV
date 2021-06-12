package de.sameplayer.tv.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.sameplayer.tv.Fragments.Adapter.ChannelArrayAdapter;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

@SuppressLint("ValidFragment")
public class ChannelListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channellist, container, false);

        ChannelArrayAdapter adapter = new ChannelArrayAdapter(MainActivity.Instance, MainActivity.Instance.ChannelManager.getChannels());
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        adapter.setListView(listView);
        return view;
    }
}
