package de.sameplayer.tv.Classes.Managers;

import android.annotation.SuppressLint;

import androidx.fragment.app.DialogFragment;

import java.util.HashMap;

import de.sameplayer.tv.Classes.FragmentPackage;

public class DialogManager {

    HashMap<String, DialogFragment> dialogs = new HashMap<>();

    public void add(DialogFragment dialogFragment, String name){
        if(!dialogs.containsKey(name)){
            dialogs.put(name, dialogFragment);
        }
        else{
            System.out.println(name + " already in DialogManager");
        }
    }

    public DialogFragment get(String name) {
        if (!dialogs.containsKey(name)) {
            return null;
        }
        return dialogs.get(name);
    }

    @SuppressLint("NewApi")
    public void set(String name, DialogFragment dialogFragment) {
        dialogs.replace(name, dialogFragment);
    }

}
