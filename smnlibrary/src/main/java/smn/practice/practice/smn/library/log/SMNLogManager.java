package smn.practice.practice.smn.library.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SMNLogManager {

    private SMNLogConfig config;

    private List<SMNLogPrinter> printers = new ArrayList<>();

    private static volatile SMNLogManager instance;
    
    private SMNLogManager(SMNLogConfig config, SMNLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }
    
    public static SMNLogManager getInstance() {
        return instance;
    }
    
    public static void init(SMNLogConfig config, SMNLogPrinter... printers) {

        if (instance == null) {
            synchronized (SMNLogManager.class) {
                if (instance == null) {
                    instance = new SMNLogManager(config, printers);
                }
            }
        }

    }

    public SMNLogConfig getConfig() {
        return config;
    }

    public List<SMNLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(SMNLogPrinter printer) {
        printers.add(printer);
    }

    public void removePrinter(SMNLogPrinter printer) {
        if (printers != null) {
            printers.remove(printer);
        }
    }


}
