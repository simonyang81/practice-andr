package smn.practice.practice.smn.library.log;

public abstract class SMNLogConfig {

    public static int MAX_LEN = 512;

    protected static SMNThreadFormatter threadFormatter = SMNThreadFormatter.getInstance();
    protected static SMNStackTraceFormatter stackTraceFormatter = SMNStackTraceFormatter.getInstance();

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

    public SMNLogPrinter[] printers() {
        return null;
    }

    public interface JsonParser {
        String toJson(Object src);
    }

}
