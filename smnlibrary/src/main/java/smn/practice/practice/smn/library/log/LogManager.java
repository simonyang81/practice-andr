package smn.practice.practice.smn.library.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogManager {

    private LogConfig config;

    private List<LogPrinter> printers = new ArrayList<>();

    private static volatile LogManager instance;
    
    private LogManager(LogConfig config, LogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }
    
    public static LogManager getInstance() {
        return instance;
    }
    
    public static void init(LogConfig config, LogPrinter... printers) {

        if (instance == null) {
            synchronized (LogManager.class) {
                if (instance == null) {
                    instance = new LogManager(config, printers);
                }
            }
        }

    }

    public LogConfig getConfig() {
        return config;
    }

    public List<LogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(LogPrinter printer) {
        printers.add(printer);
    }

    public void removePrinter(LogPrinter printer) {
        if (printers != null) {
            printers.remove(printer);
        }
    }


}
