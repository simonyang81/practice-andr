package smn.practice.practice.smn.library.log;

import static android.util.Log.ASSERT;
import static android.util.Log.DEBUG;
import static android.util.Log.ERROR;
import static android.util.Log.INFO;
import static android.util.Log.VERBOSE;
import static android.util.Log.WARN;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SMNLogType {

    @IntDef({ASSERT, ERROR, WARN, INFO, DEBUG, VERBOSE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
    }

    public final static int A = ASSERT ;
    public final static int D = DEBUG;
    public final static int E = ERROR;
    public final static int I = INFO;
    public final static int V = VERBOSE;
    public final static int W = WARN;

}
