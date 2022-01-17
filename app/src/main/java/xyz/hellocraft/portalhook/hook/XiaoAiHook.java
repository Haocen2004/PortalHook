package xyz.hellocraft.portalhook.hook;

import android.content.Intent;
import android.net.Uri;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XiaoAiHook {
    public XiaoAiHook(XC_LoadPackage.LoadPackageParam lpparam) {

        XposedBridge.log("Hook到小爱同学进程！");
        try {
            Class<?> clazz = XposedHelpers.findClass("e.D.L.pa.Wa", lpparam.classLoader);
            XposedHelpers.findAndHookMethod(clazz,"startActivityWithIntent", Intent.class, boolean.class,int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XposedBridge.log("0)Hook到Activity启动，开始判断");
                    Intent intent = (Intent) param.args[0];
                    XposedBridge.log(intent.toString());
                    try {
                        if (intent.getPackage().equals("com.android.browser")) {
                            XposedBridge.log("发现浏览器目标 Intent！");
                            XposedBridge.log("目标URL：" + intent.getDataString());
                            Uri uri = Uri.parse(intent.getDataString());
                            Intent newIntent = new Intent();
                            newIntent.setAction("android.intent.action.VIEW");
                            newIntent.setData(uri);
                            param.args[0] = newIntent;
                        }
                    } catch (Exception e) {
                        XposedBridge.log(e);
                    }
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }
}
