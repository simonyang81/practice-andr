package smn.practice.practice.smn.library.log;

public class StackTraceFormatter implements LogFormatter<StackTraceElement[]> {
    
    /**
     * 单例实例
     */
    private static volatile StackTraceFormatter instance;
    
    /**
     * 私有构造方法，防止外部实例化
     */
    private StackTraceFormatter() {
    }
    
    /**
     * 获取单例实例
     * 使用双重检查锁定模式确保线程安全和性能
     * 
     * @return SMNStackTraceFormatter 单例实例
     */
    public static StackTraceFormatter getInstance() {
        if (instance == null) {
            synchronized (StackTraceFormatter.class) {
                if (instance == null) {
                    instance = new StackTraceFormatter();
                }
            }
        }
        return instance;
    }
    
    @Override
    public String format(StackTraceElement[] stackTrace) {

        StringBuilder sb = new StringBuilder(256);
        if (stackTrace == null || stackTrace.length == 0) {
            return null;
        } else if (stackTrace.length == 1) {
            return "\tat " + stackTrace[0].toString();
        } else {
            sb.append("Stack trace:\n");
            for (int i = 0, len = stackTrace.length; i < len; i++) {
                if (i == 0) {
                    sb.append("\tat ").append(stackTrace[i].toString()).append("\n");
                } else if (i == len - 1) {
                    sb.append("\tat ").append(stackTrace[i].toString()).append("\n");
                } else {
                    sb.append("\tat ").append(stackTrace[i].toString()).append("\n");
                }
            }
            return sb.toString();
        }

    }
}
