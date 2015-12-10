package com.platanoenterprise.telegramdirectcall;

import android.content.Intent;

import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.findMethodBestMatch;


/**
 * Created by rafa on 24/11/15.
 */
public class DirectCall implements IXposedHookLoadPackage {


    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws NoSuchFieldException {
        if (!lpparam.packageName.equals("org.telegram.messenger")) {
            return;
        }

        Class<?> profileClass = findClass("org.telegram.ui.ProfileActivity", lpparam.classLoader);
        if(profileClass != null)
            XposedBridge.log(profileClass.getName());
        else XposedBridge.log("EEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

        Method createView = findMethodBestMatch(profileClass, "createView", Intent.class, int.class);

        XposedBridge.hookMethod(createView, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("Start of startActivityForResult callback");

                Intent intent = (Intent) param.args[0];
                /**/

                XposedBridge.log("End of startActivityForResult callback");
            }
        });
    }
}
