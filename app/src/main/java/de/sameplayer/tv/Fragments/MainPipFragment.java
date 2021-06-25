package de.sameplayer.tv.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.Classes.Elements.Channel;
import de.sameplayer.tv.Classes.Managers.ChannelManager;
import de.sameplayer.tv.Classes.Managers.ConnectionManager;
import de.sameplayer.tv.FontAwesome;
import de.sameplayer.tv.Fragments.Adapter.ChannelArrayAdapter;
import de.sameplayer.tv.Fragments.Adapter.PinnedChannelAdapter;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

public class MainPipFragment extends Fragment {

    Switch toggle;
    FontAwesome swap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main_pip, container, false);

        final FontAwesome channelButton = view.findViewById(R.id.ch);
        final FontAwesome channelUpButton = view.findViewById(R.id.ch_up);
        final FontAwesome channelDownButton = view.findViewById(R.id.ch_down);
        final FontAwesome volDownButton = view.findViewById(R.id.vol_down);
        final FontAwesome volUpButton = view.findViewById(R.id.vol_up);
        final FontAwesome muteButton = view.findViewById(R.id.mute);
        final FontAwesome zoomInButton = view.findViewById(R.id.zoom_in);
        final FontAwesome zoomOutButton = view.findViewById(R.id.zoom_out);
        swap = view.findViewById(R.id.swap);
        final ImageButton pipButton = view.findViewById(R.id.pip);
        toggle = view.findViewById(R.id.switch1);

        final TextView curr = view.findViewById(R.id.currprogramm);

        if (AppState.getVolume() <= 0) {
            AppState.setVolume(0);
            volDownButton.setEnabled(false);
        }

        pipButton.setImageResource(AppState.isPip() ? R.drawable.pip_active : R.drawable.pip_inactive);

        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppState.isShowPip()) {
                    AppState.swapChannels();
                    updateCurr(view);
                }
            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle.isChecked()) {
                    new Thread(() -> {
                        AppState.setShowPip(true);

                    }).start();
                    swap.setVisibility(View.VISIBLE);
                    MainActivity.vibrate(10);
                }else{
                    new Thread(() -> {
                        AppState.setShowPip(false);

                    }).start();
                    swap.setVisibility(View.INVISIBLE);
                    MainActivity.vibrate(10);
                }
            }
        });

        pipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.setPip(!AppState.isPip());
                MainActivity.vibrate(10);
                MainActivity.Instance.changeTo("main");
            }
        });

        updateMuteIcon(view);

        channelUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppState.isPip()) {
                    String current = AppState.getChannelPip();
                    for (int i = 0; i < ChannelManager.getChannels().size(); i++) {
                        Channel c = ChannelManager.getChannels().get(i);
                        if (c.channel.equalsIgnoreCase(current)) {
                            if (i + 1 >= ChannelManager.getChannels().size()) {
                                AppState.setChannelPip(ChannelManager.getChannels().get(0).channel);
                            }else{
                                AppState.setChannelPip(ChannelManager.getChannels().get(i + 1).channel);
                            }
                            i = ChannelManager.getChannels().size();
                        }
                    }
                }else{
                    String current = AppState.getChannelMain();
                    for (int i = 0; i < ChannelManager.getChannels().size(); i++) {
                        Channel c = ChannelManager.getChannels().get(i);
                        if (c.channel.equalsIgnoreCase(current)) {
                            if (i + 1 >= ChannelManager.getChannels().size()) {
                                AppState.setChannelMain(ChannelManager.getChannels().get(0).channel);
                            }else{
                                AppState.setChannelMain(ChannelManager.getChannels().get(i + 1).channel);
                            }
                            i = ChannelManager.getChannels().size();
                        }
                    }
                }
                MainActivity.vibrate(10);
                updateCurr(view);
            }

        });

        channelDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppState.isPip()) {
                    String current = AppState.getChannelPip();
                    for (int i = ChannelManager.getChannels().size() - 1; i >= 0; i--) {
                        Channel c = ChannelManager.getChannels().get(i);
                        if (c.channel.equalsIgnoreCase(current)) {
                            if (i - 1 < 0) {
                                String next = ChannelManager.getChannels().get(ChannelManager.getChannels().size() - 1).channel;
                                if (!next.equalsIgnoreCase(AppState.getChannelMain())) {
                                    AppState.setChannelPip(ChannelManager.getChannels().get(ChannelManager.getChannels().size() - 1).channel);
                                }else{
                                    AppState.setChannelPip(ChannelManager.getChannels().get(ChannelManager.getChannels().size() - 2).channel);
                                }
                            }else{
                                String next = ChannelManager.getChannels().get(i - 1).channel;
                                if (!next.equalsIgnoreCase(AppState.getChannelMain())) {
                                    AppState.setChannelPip(ChannelManager.getChannels().get(i - 1).channel);
                                }else{
                                    if (i - 2 > 0) {
                                        AppState.setChannelPip(ChannelManager.getChannels().get(i - 2).channel);
                                    }else{
                                        AppState.setChannelPip(ChannelManager.getChannels().get(ChannelManager.getChannels().size() - 1).channel);
                                    }
                                }
                            }
                            i = -1;
                        }
                    }
                }else{
                    String current = AppState.getChannelMain();
                    for (int i = ChannelManager.getChannels().size() - 1; i >= 0; i--) {
                        Channel c = ChannelManager.getChannels().get(i);
                        if (c.channel.equalsIgnoreCase(current)) {
                            if (i - 1 < 0) {
                                String next = ChannelManager.getChannels().get(ChannelManager.getChannels().size() - 1).channel;
                                if (!next.equalsIgnoreCase(AppState.getChannelPip())) {
                                    AppState.setChannelMain(ChannelManager.getChannels().get(ChannelManager.getChannels().size() - 1).channel);
                                }else{
                                    AppState.setChannelMain(ChannelManager.getChannels().get(ChannelManager.getChannels().size() - 2).channel);
                                }
                            }else{
                                String next = ChannelManager.getChannels().get(i - 1).channel;
                                if (!next.equalsIgnoreCase(AppState.getChannelPip())) {
                                    AppState.setChannelMain(ChannelManager.getChannels().get(i - 1).channel);
                                }else{
                                    if (i - 2 > 0) {
                                        AppState.setChannelMain(ChannelManager.getChannels().get(i - 2).channel);
                                    }else{
                                        AppState.setChannelMain(ChannelManager.getChannels().get(ChannelManager.getChannels().size() - 1).channel);
                                    }
                                }
                            }
                            i = -1;
                        }
                    }
                }
                MainActivity.vibrate(10);
                updateCurr(view);
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
                MainActivity.vibrate(10);
            }
        });

        volUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.increaseVolume();
                updateMuteIcon(view);
                updateVolumeText(view);
                MainActivity.vibrate(10);
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
                MainActivity.vibrate(10);
            }
        });

        zoomInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppState.isPip()) {
                    AppState.setZoomMain(1);
                }else{
                    AppState.setZoomPip(1);
                }
                MainActivity.vibrate(10);
            }
        });

        zoomOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppState.isPip()) {
                    AppState.setZoomMain(0);
                }else{
                    AppState.setZoomPip(0);
                }
                MainActivity.vibrate(10);
            }
        });

        if (toggle.isChecked()) {
            swap.setVisibility(View.VISIBLE);
        }else{
            swap.setVisibility(View.INVISIBLE);
        }

        updateVolumeText(view);
        updateCurr(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppState.isShowPip()) {
            toggle.setChecked(true);
            swap.setVisibility(View.VISIBLE);
        }else{
            toggle.setChecked(false);
            swap.setVisibility(View.INVISIBLE);
        }
    }

    private void updateMuteIcon(View view) {
        final FontAwesome muteButton = view.findViewById(R.id.mute);
        if (AppState.isMuted()) {
            muteButton.setText("\uf6a9");
        }else{
            muteButton.setText("\uf028");
        }
    }

    private void updateCurr(View view) {
        final TextView curr = view.findViewById(R.id.currprogramm);
        if (AppState.isPip()) {
            if (ChannelManager.hasChannel(AppState.getChannelPip()))
                curr.setText(ChannelManager.getChannelBy(AppState.getChannelPip()).program);
        }else{
            if (ChannelManager.hasChannel(AppState.getChannelMain()))
                curr.setText(ChannelManager.getChannelBy(AppState.getChannelMain()).program);
        }
        curr.setSelected(true);
    }

    private void updateVolumeText(View view) {
        ((TextView)view.findViewById(R.id.vol)).setText(AppState.getVolume() + "%");
    }

}
