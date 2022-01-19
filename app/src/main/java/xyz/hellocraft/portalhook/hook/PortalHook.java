package xyz.hellocraft.portalhook.hook;

import android.content.Intent;
import android.content.Context;
import android.net.Uri;

import de.robv.android.xposed.XC_MethodReplacement;
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

        XposedHelpers.findAndHookMethod(clazz, "openGlobalSearch", Context.class, String.class, String.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("Hook到全局搜索打开,关键词："+param.args[1].toString()+"，来源："+param.args[2].toString());
                try {
                    String targetUrl = "https://www.baidu.com/s?word=" + param.args[1].toString();
                    // TODO: 自定义搜索引擎
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri uri = Uri.parse(targetUrl);
                    intent.setData(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ((Context) param.args[0]).startActivity(intent);
                } catch (Exception e) {
                    XposedBridge.log(e);
                }
                return null;
            }
        });
    }
}
