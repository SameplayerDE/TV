package de.sameplayer.tv.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Timer;
import java.util.TimerTask;

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.Classes.Managers.ConnectionManager;
import de.sameplayer.tv.FontAwesome;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

public class ConnectionDialog extends DialogFragment {

    private RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private FontAwesome loader;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_connection,null);
        setCancelable(false);
        loader = view.findViewById(R.id.loader);

        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(new LinearInterpolator());

        loader.startAnimation(rotateAnimation);

        builder.setView(view)
                .setTitle("Verbindungsaufbau");

        ConnectionManager.init();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                checkConnection();
            }
        }, 2000);

        return builder.create();
    }

    public void checkConnection() {
        if (ConnectionManager.establishConnection() == false) {
            dismiss();
            MainActivity.Instance.show("connection_fail");
        }else{
            dismiss();
            AppState.updateConnection();
        }
    }
}
