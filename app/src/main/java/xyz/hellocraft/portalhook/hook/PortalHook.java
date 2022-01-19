package xyz.hellocraft.portalhook.hook;

import android.content.Intent;
import android.content.Context;
import android.net.Uri;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class PortalHook {

    public PortalHook(XC_LoadPackage.LoadPackageParam lpparam) {
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

        XposedHelpers.findAndHookMethod(clazz, "openGlobalSearch", Context.class, String.class String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XposedBridge.log("0)Hook到搜索打开");
                    for(Object obj:param.args) {
                        XposedBridge.log(obj.toString());
                    }
                }
            });
    }
}
