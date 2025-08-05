package smn.practice.practice.smn.library.log;

public abstract class LogConfig {

    public static int MAX_LEN = 512;

    protected static ThreadFormatter threadFormatter = ThreadFormatter.getInstance();
    protected static StackTraceFormatter stackTraceFormatter = StackTraceFormatter.getInstance();

    public JsonParser injectJsonParser() {
        return null;
    }


    public String getGlobalTag() {
        return "SMNLog";
    }

    public boolean enable() {
        return true;
    }

    public boolean includeThread() {
        return false;
    }

    public int stackTraceDepth() {
        return 5;
    }

    public LogPrinter[] printers() {
        return null;
    }

    public interface JsonParser {
        String toJson(Object src);
    }

}
