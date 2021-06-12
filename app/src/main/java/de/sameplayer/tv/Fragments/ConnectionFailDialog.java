package de.sameplayer.tv.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Timer;
import java.util.TimerTask;

import de.sameplayer.tv.Classes.Managers.ConnectionManager;
import de.sameplayer.tv.FontAwesome;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;


public class ConnectionFailDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        MainActivity.vibrate(100);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_connection_fail,null);
        setCancelable(false);


        builder.setView(view)
                .setTitle("Verbindungsfehler")
                .setNegativeButton("App beenden", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        MainActivity.Instance.finish();
                    }
                })
                .setPositiveButton("Erneut versuchen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        MainActivity.Instance.show("connection");
                    }
                });



        return builder.create();
    }

}
