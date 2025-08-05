package smn.practice.practice.smn.library.log;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import smn.practice.practice.smn.library.utils.StackTraceUtil;

/**
 * 1. 打印堆栈信息
 * 2. File输出
 * 3. 模拟控制台
 */
public class SMNLog {

    private static String SMN_LOG_PACKAGE;

    static {
        String className = SMNLog.class.getName();
        SMN_LOG_PACKAGE = className.substring(0, className.lastIndexOf('.') + 1);
    }

    public static void v(Object... contents) {
        log(SMNLogType.V, contents);
    }

    public static void vt(String tag, Object... contents) {
        log(SMNLogType.V, tag, contents);
    }

    public static void a(Object... contents) {
        log(SMNLogType.A, contents);
    }

    public static void at(String tag, Object... contents) {
        log(SMNLogType.A, tag, contents);
    }

    public static void d(Object... contents) {
        log(SMNLogType.D, contents);
    }

    public static void dt(String tag, Object... contents) {
        log(SMNLogType.D, tag, contents);
    }

    public static void e(Object... contents) {
        log(SMNLogType.E, contents);
    }

    public static void et(String tag, Object... contents) {
        log(SMNLogType.E, tag, contents);
    }

    public static void i(Object... contents) {
        log(SMNLogType.I, contents);
    }

    public static void it(String tag, Object... contents) {
        log(SMNLogType.I, tag, contents);
    }

    public static void w(Object... contents) {
        log(SMNLogType.W, contents);
    }

    public static void wt(String tag, Object... contents) {
        log(SMNLogType.W, tag, contents);
    }

    public static void log(@SMNLogType.TYPE int type, Object... contents) {
        log(type, LogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    public static void log(@SMNLogType.TYPE int type, @NonNull String tag, Object... contents) {
        log(LogManager.getInstance().getConfig(), type, tag, contents);
    }


    public static void log(@NonNull LogConfig config, @SMNLogType.TYPE int type, @NonNull String tag, Object... contents) {

        if (!config.enable()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        if (config.includeThread()) {
            String threadInfo = LogConfig.threadFormatter.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }

        if (config.stackTraceDepth() > 0) {
            String stackTrace = LogConfig.stackTraceFormatter.format(
                    StackTraceUtil.getCroppedRealStackTrack(new Throwable().getStackTrace(), SMN_LOG_PACKAGE, config.stackTraceDepth()));
            sb.append(stackTrace).append("\n");
        }

        String body = parseBody(contents, config);
        sb.append(body);

        List<LogPrinter> printers = config.printers() != null ? Arrays.asList(config.printers()) : LogManager.getInstance().getPrinters();

        if (printers == null) {
            return;
        }

        for (LogPrinter printer : printers) {
            printer.print(config, type, tag, sb.toString());
        }

    }

    /**
     * 解析内容数组，将多个对象用分号连接成字符串
     * @param contents 要解析的内容数组
     * @return 用分号连接的字符串
     */
    private static String parseBody(@NonNull Object[] contents, @NonNull LogConfig config) {

        if (contents.length == 0) {
            return "";
        }

        if (config.injectJsonParser() != null) {
            return config.injectJsonParser().toJson(contents);
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] != null) {
                sb.append(contents[i].toString());
            } else {
                sb.append("null");
            }
            
            // 如果不是最后一个元素，添加分号分隔符
            if (i < contents.length - 1) {
                sb.append(";");
            }
        }
        
        return sb.toString();

    }



}
