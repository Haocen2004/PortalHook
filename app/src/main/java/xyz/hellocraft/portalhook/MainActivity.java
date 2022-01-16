package xyz.hellocraft.portalhook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import xyz.hellocraft.portalhook.databinding.ActivityMainBinding;
import xyz.hellocraft.portalhook.utils.RootKit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private boolean haveRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        haveRoot = RootKit.haveRoot();
        binding.button.setOnClickListener(view -> {
            if (haveRoot) {
                RootKit.execRootCmdSilent("am force-stop com.miui.contentextension");
            } else {
                Toast.makeText(this, "未获取Root权限！", Toast.LENGTH_SHORT).show();
            }
        });
        binding.button2.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://github.com/Haocen2004/PortalHook");
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(uri);
            startActivity(intent);
        });
    }
}