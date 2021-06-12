package de.sameplayer.tv.Classes.Managers;

import android.annotation.SuppressLint;
import java.util.HashMap;
import de.sameplayer.tv.Classes.FragmentPackage;

public class FragmentManager {

    HashMap<String, FragmentPackage> fragments = new HashMap<>();

    public void add(FragmentPackage fragmentPackage, String name){
        if(!fragments.containsKey(name)){
            fragments.put(name, fragmentPackage);
        }
        else{
            System.out.println(name + " already in FragmentManager");
        }
    }

    public FragmentPackage get(String name) {
        if (!fragments.containsKey(name)) {
            return null;
        }
        return fragments.get(name);
    }

    @SuppressLint("NewApi")
    public void set(String name, FragmentPackage fragmentPackage) {
        fragments.replace(name, fragmentPackage);
    }

}
