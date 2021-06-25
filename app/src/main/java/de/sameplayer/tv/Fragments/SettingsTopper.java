package de.sameplayer.tv.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.FontAwesome;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

public class SettingsTopper extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_menu, container, false);

        final FontAwesome homeButton = view.findViewById(R.id.Home);

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
