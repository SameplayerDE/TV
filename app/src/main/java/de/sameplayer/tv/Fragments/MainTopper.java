package de.sameplayer.tv.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.sameplayer.tv.FontAwesome;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

public class MainTopper extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu, container, false);

        final FontAwesome settingsButton = view.findViewById(R.id.Settings);
        final FontAwesome infoButton = view.findViewById(R.id.Info);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.Instance.changeTo("settings");
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.Instance.changeTo("info");
            }
        });

        return view;
    }

}
