package xyz.hellocraft.portalhook;

import android.content.Intent;
import android.net.Uri;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(!lpparam.packageName.equals("com.miui.contentextension")) return;
        XposedBridge.log("Hook到传送门进程！");
        final Class<?> clazz = XposedHelpers.findClass("com.miui.contentextension.utils.AppsUtils", lpparam.classLoader);
        //getClassInfo(clazz);

        XposedHelpers.findAndHookMethod(clazz, "getIntentWithBrowser", String.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("Hook到方法,目标URL："+param.args[0].toString());
                Uri uri = Uri.parse(param.args[0].toString());
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                return intent;
            }
        });

    }
}
