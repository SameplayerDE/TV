package de.sameplayer.tv.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.Classes.Elements.Channel;
import de.sameplayer.tv.Classes.Managers.ChannelManager;
import de.sameplayer.tv.Classes.Managers.ConnectionManager;
import de.sameplayer.tv.FontAwesome;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

public class MainFragment extends Fragment {

    private AlphaAnimation animation2;
    private AlphaAnimation animation1;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        final FontAwesome channelButton = view.findViewById(R.id.ch);
        final FontAwesome channelUpButton = view.findViewById(R.id.ch_up);
        final FontAwesome channelDownButton = view.findViewById(R.id.ch_down);
        final FontAwesome volDownButton = view.findViewById(R.id.vol_down);
        final FontAwesome volUpButton = view.findViewById(R.id.vol_up);
        final FontAwesome muteButton = view.findViewById(R.id.mute);
        final FontAwesome zoomInButton = view.findViewById(R.id.zoom_in);
        final FontAwesome zoomOutButton = view.findViewById(R.id.zoom_out);
        final ImageButton pipButton = view.findViewById(R.id.pip);

        final TextView curr = view.findViewById(R.id.currprogramm);

        final FontAwesome state = view.findViewById(R.id.state);
        final TextView live = view.findViewById(R.id.textView3);

        final LinearLayout livePause = view.findViewById(R.id.livepause);

        final TextView vol = view.findViewById(R.id.vol);

        final FontAwesome pauseButton = view.findViewById(R.id.swap);

        final FontAwesome powerButton = view.findViewById(R.id.power_btn);



        if (AppState.getVolume() <= 0) {
            AppState.setVolume(0);
            volDownButton.setEnabled(false);
        }

        livePause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.setPause(false);
                AppState.toLive();
                updatePause(view);
            }
        });

        pipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.setPip(!AppState.isPip());
                pipButton.setImageResource(AppState.isPip() ? R.drawable.pip_active : R.drawable.pip_inactive);
                MainActivity.vibrate(10);
                MainActivity.Instance.changeTo("main_pip");
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.setPause(!AppState.isPause());
                updatePause(view);
                MainActivity.vibrate(10);
            }
        });

        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> {
                    AppState.setStandBy(AppState.isStandBy() == 1 ? 0 : 1);
                }).start();
                MainActivity.vibrate(10);
                new Thread(new Runnable() {
                    public void run() {
                        powerButton.post(new Runnable() {
                            public void run() {
                                if (AppState.isStandBy() == 1) {
                                    hideAll(view);
                                }else{
                                    showAll(view);
                                }
                            }
                        });
                    }
                }).start();

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
                                AppState.setChannelPip(ChannelManager.getChannels().get(ChannelManager.getChannels().size() - 1).channel);
                            }else{
                                AppState.setChannelPip(ChannelManager.getChannels().get(i - 1).channel);
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
                                AppState.setChannelMain(ChannelManager.getChannels().get(ChannelManager.getChannels().size() - 1).channel);
                            }else{
                                AppState.setChannelMain(ChannelManager.getChannels().get(i - 1).channel);
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


        animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(500);

        animation2 = new AlphaAnimation(1.0f, 0.0f);
        animation2.setDuration(500);

        //animation1 AnimationListener
        animation1.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationEnd(Animation arg0) {
                // start animation2 when animation1 ends (continue)

                if (AppState.getBufferedMillis() > 0L || AppState.isPause()) {

                }else{
                    state.startAnimation(animation2);
                }
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }

        });



        //animation2 AnimationListener
        animation2.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationEnd(Animation arg0) {
                // start animation1 when animation2 ends (repeat)
                state.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });

        //state.startAnimation(animation1);
        pipButton.setImageResource(AppState.isPip() ? R.drawable.pip_active : R.drawable.pip_inactive);

        updateVolumeText(view);
        updatePause(view);
        updateLive(view);
        updateCurr(view);
        return view;
    }

    private void hideAll(View view) {

        LinearLayout channelGroup = view.findViewById(R.id.channelGroup);
        LinearLayout soundGroup = view.findViewById(R.id.soundGroup);
        LinearLayout actions = view.findViewById(R.id.actions);
        LinearLayout functionGroup = view.findViewById(R.id.functionGroup);

        TextView volume = view.findViewById(R.id.vol);
        TextView channel = view.findViewById(R.id.currprogramm);

        FontAwesome zoomIn = view.findViewById(R.id.zoom_in);
        FontAwesome zoomOut = view.findViewById(R.id.zoom_out);

        actions.setVisibility(View.INVISIBLE);
        soundGroup.setVisibility(View.INVISIBLE);
        functionGroup.setVisibility(View.INVISIBLE);
        channelGroup.setVisibility(View.INVISIBLE);
        volume.setVisibility(View.INVISIBLE);
        channel.setVisibility(View.INVISIBLE);
        zoomIn.setVisibility(View.INVISIBLE);
        zoomOut.setVisibility(View.INVISIBLE);

    }

    private void showAll(View view) {

        LinearLayout channelGroup = view.findViewById(R.id.channelGroup);
        LinearLayout soundGroup = view.findViewById(R.id.soundGroup);
        LinearLayout actions = view.findViewById(R.id.actions);
        LinearLayout functionGroup = view.findViewById(R.id.functionGroup);

        TextView volume = view.findViewById(R.id.vol);
        TextView channel = view.findViewById(R.id.currprogramm);

        FontAwesome zoomIn = view.findViewById(R.id.zoom_in);
        FontAwesome zoomOut = view.findViewById(R.id.zoom_out);

        actions.setVisibility(View.VISIBLE);
        soundGroup.setVisibility(View.VISIBLE);
        functionGroup.setVisibility(View.VISIBLE);
        channelGroup.setVisibility(View.VISIBLE);
        volume.setVisibility(View.VISIBLE);
        channel.setVisibility(View.VISIBLE);
        zoomIn.setVisibility(View.VISIBLE);
        zoomOut.setVisibility(View.VISIBLE);

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            public void run() {
                view.post(new Runnable() {
                    public void run() {
                        if (AppState.isStandBy() == 1) {
                            hideAll(view);
                        }else{
                            showAll(view);
                        }
                    }
                });
            }
        }).start();
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


    private void updateLive(View view) {
        final FontAwesome state = view.findViewById(R.id.state);
        final TextView live = view.findViewById(R.id.textView3);
        if (AppState.getBufferedMillis() > 0L || AppState.isPause()) {
            live.setTextColor(MainActivity.Instance.getApplicationContext().getResources().getColor(R.color.gray));
            state.setTextColor(MainActivity.Instance.getApplicationContext().getResources().getColor(R.color.gray));
        }else{
            live.setTextColor(MainActivity.Instance.getApplicationContext().getResources().getColor(R.color.warning));
            state.setTextColor(MainActivity.Instance.getApplicationContext().getResources().getColor(R.color.warning));
            //state.startAnimation(animation1);
        }
    }

    private void updatePause(View view) {
        final FontAwesome pause = view.findViewById(R.id.swap);
        if (AppState.isPause()) {
            new Thread(() -> {
                ConnectionManager.processCommand("timeShiftPause= ");
            }).start();
            pause.setText("\uf04b");
        }else{
            new Thread(() -> {
                ConnectionManager.processCommand("timeShiftPlay=" + (AppState.getBufferedMillis() / 1000L));
            }).start();
            pause.setText("\uf04c");
        }
        updateLive(view);
    }

    private void updateVolumeText(View view) {
        ((TextView)view.findViewById(R.id.vol)).setText(AppState.getVolume() + "%");
    }

}
