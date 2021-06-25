package de.sameplayer.tv.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.Classes.Managers.ConnectionManager;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

public class SettingsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_settings, container, false);

        final TextView such = view.findViewById(R.id.such);
        final TextView debug = view.findViewById(R.id.debug);
        final TextView reset = view.findViewById(R.id.reset);

        such.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.Instance.show("connection");
            }
        });

        debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.setDebug(AppState.isDebug() == 1 ? 0 : 1);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.reset();
            }
        });

        return view;

    }
}
