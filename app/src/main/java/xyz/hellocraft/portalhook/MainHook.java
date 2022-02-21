package xyz.hellocraft.portalhook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import xyz.hellocraft.portalhook.hook.PortalHook;
import xyz.hellocraft.portalhook.hook.XiaoAiHook;
import xyz.hellocraft.portalhook.hook.SystemHook;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        switch (lpparam.packageName) {
            case "com.miui.contentextension":
                new PortalHook(lpparam);
                break;
            case "com.miui.voiceassist":
                new XiaoAiHook(lpparam);
                break;
            case "android":
                new SystemHook(lpparam);
                break;
        }
    }
}
