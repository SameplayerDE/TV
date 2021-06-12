package de.sameplayer.tv.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.Classes.Managers.ConnectionManager;
import de.sameplayer.tv.FontAwesome;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

public class MainFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        final FontAwesome channelButton = view.findViewById(R.id.ch);
        final FontAwesome channelUpButton = view.findViewById(R.id.ch_up);
        final FontAwesome channelDownButton = view.findViewById(R.id.ch_down);
        final FontAwesome volDownButton = view.findViewById(R.id.vol_down);
        final FontAwesome volUpButton = view.findViewById(R.id.vol_up);
        final FontAwesome muteButton = view.findViewById(R.id.mute);
        final TextView vol = view.findViewById(R.id.vol);

        final FontAwesome powerButton = view.findViewById(R.id.power_btn);

        if (AppState.getVolume() <= 0) {
            AppState.setVolume(0);
            volDownButton.setEnabled(false);
        }

        updateVolumeText(view);

        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> {
                    ConnectionManager.powerOff();
                }).start();
                MainActivity.Instance.show("connection");
            }
        });

        updateMuteIcon(view);

        channelUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        channelDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        channelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.Instance.changeTo("ch");
            }
        });

        volDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.decreaseVolume();
                updateMuteIcon(view);
                updateVolumeText(view);
            }
        });

        volUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.increaseVolume();
                updateMuteIcon(view);
                updateVolumeText(view);
            }
        });

        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.toggleMute();
                if (AppState.isMuted()) {
                    muteButton.setText("\uf6a9");
                }else{
                    muteButton.setText("\uf028");
                }
            }
        });

        return view;
    }

    private void updateMuteIcon(View view) {
        final FontAwesome muteButton = view.findViewById(R.id.mute);
        if (AppState.isMuted()) {
            muteButton.setText("\uf6a9");
        }else{
            muteButton.setText("\uf028");
        }
    }

    private void updateVolumeText(View view) {
        ((TextView)view.findViewById(R.id.vol)).setText(AppState.getVolume() + "%");
    }

}
