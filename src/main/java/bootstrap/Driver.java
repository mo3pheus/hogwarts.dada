package bootstrap;

import org.apache.log4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Driver {
    public static Logger logger = LoggerFactory.getLogger(Driver.class);

    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig(args);
        configureLogging(applicationConfig.isDebugLogEnabled(),
                applicationConfig.getLogFileName());
        logger.info("This is the Defense Against the Dark Arts class");

        try {
            List<String> lines = Files.readAllLines(Paths.get(applicationConfig.getInputFileName()));
            Map<String, Integer> histogram = new HashMap<>();
            for (String line : lines) {
                if (line.contains("Invalid") || line.contains("wrong")) {
                    String[] words = line.split(" ");
                    for (int i = 0; i < words.length; i++) {
                        if (words[i].contains(".")) {
                            int count = (histogram.containsKey(words[i])) ? (histogram.get(words[i]) + 1) : 1;
                            histogram.put(words[i], count);
                        }
                    }
                }
            }

            for (String ip : histogram.keySet()) {
                logger.info(ip + ":" + histogram.get(ip));
            }
        } catch (IOException e) {
            logger.error("Could not read input file");
        }
    }

    public static String configureLogging(boolean debug, String logFileName) {
        FileAppender fa = new FileAppender();

        if (!debug) {
            fa.setThreshold(Level.toLevel(Priority.INFO_INT));
        } else {
            fa.setThreshold(Level.toLevel(Priority.DEBUG_INT));
        }

        fa.setFile(logFileName);
        fa.setLayout(new EnhancedPatternLayout("%-6d [%t] %-5p %c - %m%n"));
        fa.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(fa);
        return fa.getFile();
    }

    public static void configureConsoleLogging(boolean debug) {
        ConsoleAppender ca = new ConsoleAppender();
        if (!debug) {
            ca.setThreshold(Level.toLevel(Priority.INFO_INT));
        } else {
            ca.setThreshold(Level.toLevel(Priority.DEBUG_INT));
        }
        ca.setLayout(new EnhancedPatternLayout("%-6d [%25.35t] %-5p %40.80c - %m%n"));
        ca.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(ca);
    }

    public static Properties getProjectProperties(String propertiesFilePath) throws IOException {
        logger.info("Properties file specified at location = " + propertiesFilePath);
        FileInputStream projFile = new FileInputStream(propertiesFilePath);
        Properties properties = new Properties();
        properties.load(projFile);
        return properties;
    }
}
