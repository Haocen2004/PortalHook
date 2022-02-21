package xyz.hellocraft.portalhook.hook;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class SystemHook {

    public SystemHook(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedBridge.log("Hook到系统进程！");
        final Class<?> clazz = XposedHelpers.findClass("android.app.ActivityManager", lpparam.classLoader);
        //getClassInfo(clazz);

        XposedHelpers.findAndHookMethod(clazz, "startActivity", Context.class, Intent.class,Bundle.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("Hook到方法:"+param.args[1].toString());
                // Uri uri = Uri.parse(param.args[0].toString());
                // Intent intent = new Intent();
                // intent.setAction("android.intent.action.VIEW");
                // intent.setData(uri);
                // return intent;
            }
        });

    }
}
