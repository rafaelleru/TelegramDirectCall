package com.platanoenterprise.telegramdirectcall;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;


/**
 * Created by rafa on 24/11/15.
 */
public class DirectCall implements IXposedHookLoadPackage {
    private Object telefono;

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("org.telegram.messenger"))
            return;


        XposedBridge.log("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

        //XposedBridge.log("Loaded app: " + lpparam.packageName);

        findAndHookMethod("org.telegram.messenger.ui.LaunchActivity", lpparam.classLoader, "onFragmentCreate", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                Object other = XposedHelpers.getSurroundingThis(param.thisObject);
                telefono = XposedHelpers.getObjectField(param, "User.phone");

            }
        });

        Log.wtf("Debuggin", "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
    }
}
