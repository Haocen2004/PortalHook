package xyz.hellocraft.portalhook;

import static xyz.hellocraft.portalhook.BuildConfig.APPLICATION_ID;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import xyz.hellocraft.portalhook.databinding.ActivityMainBinding;
import xyz.hellocraft.portalhook.utils.RootKit;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences app_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xyz.hellocraft.portalhook.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        haveRoot = RootKit.haveRoot();
        binding.btnPortal.setOnClickListener(view -> {
            if (RootKit.haveRoot()) {
                RootKit.execRootCmdSilent("am force-stop com.miui.contentextension");
            } else {
                Toast.makeText(this, "未获取Root权限！", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnAssist.setOnClickListener(view -> {
            if (RootKit.haveRoot()) {
                RootKit.execRootCmdSilent("am force-stop com.miui.voiceassist");
            } else {
                Toast.makeText(this, "未获取Root权限！", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnGithub.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://github.com/Haocen2004/PortalHook");
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(uri);
            startActivity(intent);
        });
        app_pref = getSharedPreferences("settings", 0);
        binding.iconSwitch.setChecked(app_pref.getBoolean("hideLauncher",false));
        binding.iconSwitch.setOnCheckedChangeListener((compoundButton, b) -> switchLauncherIcon(b));
    }

    private void switchLauncherIcon(boolean b){
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(this, APPLICATION_ID+".launcher"),b ? PackageManager.COMPONENT_ENABLED_STATE_DISABLED : PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        app_pref.edit().putBoolean("hideLauncher",b).apply();
    }
}