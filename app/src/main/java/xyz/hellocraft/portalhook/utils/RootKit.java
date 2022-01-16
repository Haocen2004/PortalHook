package xyz.hellocraft.portalhook.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class RootKit {

    private static final String TAG = "RootKit";
    private static boolean mHaveRoot = false;

    public static boolean haveRoot() {
        if (!mHaveRoot) {
            int ret = execRootCmdSilent("echo tryGetRoot");
            if (ret != -1) {
                Log.i(TAG, "Device root get.");
                mHaveRoot = true;
            } else {
                Log.i(TAG, "Device not root.");
            }
        } else {
            Log.i(TAG, "Already get root.");
        }
        return mHaveRoot;
    }

    public static int execRootCmdSilent(String cmd) {
        try {
            Process process = new ProcessBuilder("su").redirectErrorStream(true).start();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter((process.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Log.d(TAG, "execRootCmd: "+cmd);
            outputStreamWriter.write(cmd + "\n");
            outputStreamWriter.write("exit\n");
            outputStreamWriter.flush();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("Permission denied") || line.contains("inaccessible or not found")) {
                    return -1;
                }
                Log.d(TAG, "execReturn: "+line);
//                ToastUtils.show(line);
            }
            outputStreamWriter.close();
            bufferedReader.close();
            process.destroy();
            return 0;
        } catch (Exception e) {
//            e.printStackTrace();
            Log.e(TAG,"Command Execute Failed",e);
            return -1;
        }
    }
}