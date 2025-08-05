package smn.practice.practice.andr;

import android.app.Application;

import com.google.gson.Gson;

import smn.practice.practice.smn.library.log.ConsolePrinter;
import smn.practice.practice.smn.library.log.LogConfig;
import smn.practice.practice.smn.library.log.LogManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LogManager.init(new LogConfig() {

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

        }, new ConsolePrinter());

    }
}
