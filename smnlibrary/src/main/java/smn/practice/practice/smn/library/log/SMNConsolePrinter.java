package smn.practice.practice.smn.library.log;

import static smn.practice.practice.smn.library.log.SMNLogConfig.MAX_LEN;

import android.util.Log;

import androidx.annotation.NonNull;

public class SMNConsolePrinter implements SMNLogPrinter {
    @Override
    public void print(@NonNull SMNLogConfig config, int level, String tag, @NonNull String printString) {
        int len = printString.length();
        int countOfSub = len / MAX_LEN;

        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {

                Log.println(level, tag, printString.substring(index, index+MAX_LEN));
                index+=MAX_LEN;
            }

            if (index != len) {
                Log.println(level, tag, printString.substring(index, len));
            }

        } else {
            Log.println(level, tag, printString);
        }

    }
}
