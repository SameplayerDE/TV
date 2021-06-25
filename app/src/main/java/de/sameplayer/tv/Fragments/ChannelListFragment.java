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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import de.sameplayer.tv.Classes.Managers.ChannelManager;
import de.sameplayer.tv.Fragments.Adapter.ChannelArrayAdapter;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

@SuppressLint("ValidFragment")
public class ChannelListFragment extends Fragment {

    public static ChannelArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channellist, container, false);

        adapter = new ChannelArrayAdapter(MainActivity.Instance, ChannelManager.getSorted());
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.swip);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ChannelArrayAdapter adapter = new ChannelArrayAdapter(MainActivity.Instance, ChannelManager.getSorted());
                ListView listView = (ListView) view.findViewById(R.id.list);
                listView.setAdapter(adapter);
                registerForContextMenu(listView);
                pullToRefresh.setRefreshing(false);
                MainActivity.vibrate(100);
            }
        });

        return view;
    }
}
