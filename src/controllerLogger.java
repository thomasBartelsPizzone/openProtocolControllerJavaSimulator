import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class controllerLogger {
    // protected static final Logger logger = LogManager.getLogger();
    protected static final Logger logger = LogManager.getLogger(controllerLogger.class);

    static void controllerLog(String inFile, String inLog, String logType) {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
////        ctx.
        ctx.reconfigure();
//        ctx.setName("data");

        System.out.println("the inputs were: \n" + inFile + "\n" + inLog);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
//        String logTimeStamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx").withZone(ZoneId.of("GMT")));
        String logTimeStamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));

        String logFileName = inFile + "_" + logTimeStamp + ".log";
        System.setProperty("log.folder", inFile);

        // Path absLogFilePath = Paths.get(logFolder + joinFolder + logFileName);
        System.setProperty("log.filename", logFileName);
        //
        try {
            if (inLog != null && !"null".equals(inLog) && !"".equals(inLog)) {
                System.out.println("the log type was: " + logType);
                logger.log(Level.getLevel(logType), inLog);
            }
        } catch (Throwable r) {
            System.out.println("Exception in logging?\n: " + r.getMessage());
        }
    }
}