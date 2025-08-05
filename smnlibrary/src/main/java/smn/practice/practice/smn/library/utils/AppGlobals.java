package smn.practice.practice.smn.library.utils;

import android.app.Application;

public class AppGlobals {

    private static Application application;

    public static Application get() {
        if (application == null) {
            try {
                application = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication")
                        .invoke(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return application;
    }

}
