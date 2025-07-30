package smn.practice.practice.andr;

import android.app.Application;

import com.google.gson.Gson;

import smn.practice.practice.smn.library.log.SMNConsolePrinter;
import smn.practice.practice.smn.library.log.SMNLogConfig;
import smn.practice.practice.smn.library.log.SMNLogManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SMNLogManager.init(new SMNLogConfig() {

            @Override
            public JsonParser injectJsonParser() {
                return src -> new Gson().toJson(src);
            }

            @Override
            public String getGlobalTag() {
                return "[ICHEER]";
            }

            @Override
            public boolean enable() {
                return true;
            }

        }, new SMNConsolePrinter());

    }
}
