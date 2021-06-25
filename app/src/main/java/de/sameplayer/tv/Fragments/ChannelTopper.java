package de.sameplayer.tv.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.Classes.Elements.Channel;
import de.sameplayer.tv.Classes.Managers.ChannelManager;
import de.sameplayer.tv.FontAwesome;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

public class ChannelTopper extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.channellist_menu, container, false);

        final FontAwesome homeButton = view.findViewById(R.id.Home);
        final FontAwesome searchButton = view.findViewById(R.id.search);
        final EditText searchField = view.findViewById(R.id.searchChannel);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppState.isPip()) {
                    MainActivity.Instance.changeTo("main");
                }else{
                    MainActivity.Instance.changeTo("main_pip");
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!searchField.getText().toString().isEmpty()) {
                    searchField.setText("");
                    searchButton.setText("\uf002");
                }
            }
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String search = searchField.getText().toString().toLowerCase();
                if (search == "" || search.isEmpty()) {
                    searchButton.setText("\uf002");
                    ChannelListFragment.adapter.clear();
                    ChannelListFragment.adapter.addAll(ChannelManager.getSorted());
                }else{
                    searchButton.setText("\uf00d");
                    ChannelListFragment.adapter.clear();
                    for (Channel c : ChannelManager.getChannels()) {
                        String name = c.program.toLowerCase();
                        if (name.startsWith(search)) {
                            ChannelListFragment.adapter.add(c);
                        }
                    }
                }
                ChannelListFragment.adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = searchField.getText().toString().toLowerCase();
                if (search == "" || search.isEmpty()) {
                    searchButton.setText("\uf002");
                    ChannelListFragment.adapter.clear();
                    ChannelListFragment.adapter.addAll(ChannelManager.getSorted());
                }else{
                    searchButton.setText("\uf00d");
                    ChannelListFragment.adapter.clear();
                    for (Channel c : ChannelManager.getChannels()) {
                        String name = c.program.toLowerCase();
                        if (name.startsWith(search)) {
                            ChannelListFragment.adapter.add(c);
                        }
                    }
                }
                ChannelListFragment.adapter.notifyDataSetChanged();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppState.isPip()) {
                    MainActivity.Instance.changeTo("main");
                }else{
                    MainActivity.Instance.changeTo("main_pip");
                }
            }

        });

        return view;
    }

}
