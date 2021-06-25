package de.sameplayer.tv.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.Classes.Elements.Channel;
import de.sameplayer.tv.Classes.Managers.ChannelManager;
import de.sameplayer.tv.FontAwesome;
import de.sameplayer.tv.MainActivity;
import de.sameplayer.tv.R;

public class InfoTopper extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.info_menu, container, false);

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
