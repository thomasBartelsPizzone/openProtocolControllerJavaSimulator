import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class hostLogger {
    // protected static final Logger logger = LogManager.getLogger();
    protected static final Logger logger = LogManager.getLogger(hostLogger.class);
//    protected static final Logger errorLogger = LogManager.getLogger(hostLogger.class);
//    protected static final Logger traceLogger = LogManager.getLogger(hostLogger.class);
//    protected static final Logger logger2 = LogManager.
    // protected static final Logger logger2 = Logger.getLogger(JavaClass.class.getName());
    // protected static final Logger logger2 = Logger.class.getName();
    // protected static final Logger logger3 = LogManager.getLogger().set

    static void hostLog(String inFile, String inLog, String logType) {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
////        ctx.
        ctx.reconfigure();
//        ctx.setName("data");

        System.out.println("the inputs were: \n" + inFile + "\n" + inLog);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
//        String logTimeStamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSxxx").withZone(ZoneId.of("GMT")));
        String logTimeStamp = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));

        String logFileName = inFile + "_" + logTimeStamp + ".log";
//        String logFileName = inFile + "_" + logTimeStamp;

//        File logFolder = new File(System.getProperty("user.dir") + "/hostLogs" + "/"+inFile);
//        // File logFolder = new File("../hostLogs");
//        if (!logFolder.exists()) {
//            logFolder.mkdir();
//        }
//        String joinFolder = "\\";
//        String logFolder = "hostLogs" + joinFolder + inFile;

//        MDC.put("folder", "logs");  // Set folder dynamically in MDC
//        System.setProperty("log.folder", logFolder);
//        System.setProperty("log.folder", "/hostLogs/error");
//        System.setProperty("folder", "/hostLogs/error");
        System.setProperty("log.folder", inFile);

        // Path absLogFilePath = Paths.get(logFolder + joinFolder + logFileName);
//        String absLogFilePath = logFolder + joinFolder + logFileName;
//        System.setProperty("logFilePath", absLogFilePath);

//        System.setProperty("log.filename", logFileName);
//        System.setProperty("logFileName", "thFileName.log");
//        System.setProperty("filename", logFileName);
        System.setProperty("log.filename", logFileName);
        //
        try {
//            if ("info".equals(logType)) {
//                logger.info(inLog);
//            } else if ("debug".equals(logType)) {
//                logger.debug(inLog);
//            } else if ("error".equals(logType)) {
//                logger.error(inLog);
//            } else if ("trace".equals(logType)) {
//                logger.trace(inLog);
//            } else if("warn".equals(logType)) {
//                logger.warn(inLog);
//            } else if("fatal".equals(logType)) {
//                logger.fatal(inLog);
//            } else {
//                logger.log(Level.getLevel(logType), inLog);
//            }
//            logger.isEnabled(Level.ALL);
            if (inLog != null && !"null".equals(inLog) && !"".equals(inLog)) {
                System.out.println("the log type was: " + logType);
                logger.log(Level.getLevel(logType), inLog);
            } // else {
            //    logger.error(inLog + "some error");
            //}
//            logger
//            LogManager.shutdown();
        } catch (Throwable r) {
            System.out.println("Exception in logging?\n: " + r.getMessage());
        }
    }
}