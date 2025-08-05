package smn.practice.practice.smn.library.log;

public class ThreadFormatter implements LogFormatter<Thread> {


    /**
     * 单例实例
     */
    private static volatile ThreadFormatter instance;

    /**
     * 私有构造方法，防止外部实例化
     */
    private ThreadFormatter() {
    }

    /**
     * 获取单例实例
     * 使用双重检查锁定模式确保线程安全和性能
     *
     * @return SMNStackTraceFormatter 单例实例
     */
    public static ThreadFormatter getInstance() {
        if (instance == null) {
            synchronized (ThreadFormatter.class) {
                if (instance == null) {
                    instance = new ThreadFormatter();
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
