package de.sameplayer.tv;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;

import de.sameplayer.tv.Classes.AppState;
import de.sameplayer.tv.Classes.FragmentPackage;
import de.sameplayer.tv.Classes.Managers.ChannelManager;
import de.sameplayer.tv.Classes.Managers.ConnectionManager;
import de.sameplayer.tv.Classes.Managers.DialogManager;
import de.sameplayer.tv.Classes.Managers.FragmentManager;
import de.sameplayer.tv.Fragments.ChannelListFragment;
import de.sameplayer.tv.Fragments.ChannelTopper;
import de.sameplayer.tv.Fragments.ConnectionDialog;
import de.sameplayer.tv.Fragments.ConnectionFailDialog;
import de.sameplayer.tv.Fragments.InfoFragment;
import de.sameplayer.tv.Fragments.InfoTopper;
import de.sameplayer.tv.Fragments.MainFragment;
import de.sameplayer.tv.Fragments.MainTopper;
import de.sameplayer.tv.Fragments.SettingsFragment;
import de.sameplayer.tv.Fragments.SettingsTopper;

public class MainActivity extends AppCompatActivity {

    public static MainActivity Instance;
    public static AppState AppState;
    public static FragmentManager FragmentManager;
    public static DialogManager DialogManager;
    public static ChannelManager ChannelManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Instance = this;

        AppState = new AppState();
        AppState.loadFromFile();

        ChannelManager = new ChannelManager(this);
        FragmentManager = new FragmentManager();
        DialogManager = new DialogManager();

        FragmentManager.add(new FragmentPackage(new MainFragment(), new MainTopper()), "main");
        FragmentManager.add(new FragmentPackage(new ChannelListFragment(), new ChannelTopper()), "ch");
        FragmentManager.add(new FragmentPackage(new SettingsFragment(), new SettingsTopper()), "settings");
        FragmentManager.add(new FragmentPackage(new InfoFragment(), new InfoTopper()), "info");

        DialogManager.add(new ConnectionDialog(), "connection");
        DialogManager.add(new ConnectionFailDialog(), "connection_fail");

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        changeTo("main");

        if (ConnectionManager.hasConnection() == false) {
            show("connection");
        }
    }

    public void changeContainerTo(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void changeTopperTo(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.topper, fragment).commit();
    }

    public void changeTo(FragmentPackage fragmentPackage) {
        if (fragmentPackage == null) return;
        changeContainerTo(fragmentPackage.Container);
        changeTopperTo(fragmentPackage.Topper);
    }

    public void changeTo(String name) {
        changeTo(FragmentManager.get(name));
    }

    public void show(DialogFragment dialogFragment) {
        if (dialogFragment == null) return;
        dialogFragment.show(getSupportFragmentManager(), null);
    }

    public void show(String name) {
        show(DialogManager.get(name));
    }

    public void dismiss(String name) {
        DialogManager.get(name).dismiss();
    }

    public static void vibrate(int time) {
        Vibrator v = (Vibrator) MainActivity.Instance.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(time);
    }

}