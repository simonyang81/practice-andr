package smn.practice.practice.smn.library.log;

public class SMNThreadFormatter implements SMNLogFormatter<Thread> {


    /**
     * 单例实例
     */
    private static volatile SMNThreadFormatter instance;

    /**
     * 私有构造方法，防止外部实例化
     */
    private SMNThreadFormatter() {
    }

    /**
     * 获取单例实例
     * 使用双重检查锁定模式确保线程安全和性能
     *
     * @return SMNStackTraceFormatter 单例实例
     */
    public static SMNThreadFormatter getInstance() {
        if (instance == null) {
            synchronized (SMNThreadFormatter.class) {
                if (instance == null) {
                    instance = new SMNThreadFormatter();
                }
            }
        }
        return instance;
    }

    @Override
    public String format(Thread data) {
        return "Thread: " + data.getName();
    }


}
