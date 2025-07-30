package smn.practice.practice.smn.library.log;

import androidx.annotation.NonNull;

public interface SMNLogPrinter {

    void print(@NonNull SMNLogConfig config, int level, String tag, @NonNull String printString);

}
