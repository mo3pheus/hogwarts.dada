package bootstrap;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class ApplicationConfig {
    private transient Namespace namespace;
    private String[] args;
    private String inputFileName;
    private String outputFileName;
    private String logFileName;
    private boolean debugLogEnabled;

    public ApplicationConfig(String[] args) {
        this.args = args;
        this.namespace = buildNamespace(args);
        this.inputFileName = namespace.getString("input.file.name");
        this.logFileName = namespace.getString("log.file.name");
        this.outputFileName = namespace.getString("output.file.name");
        this.debugLogEnabled = namespace.getBoolean("debug.log.enabled");
    }

    public String[] getArgs() {
        return args;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public boolean isDebugLogEnabled() {
        return debugLogEnabled;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public Namespace buildNamespace(String[] args) {
        Namespace namespace = null;
        ArgumentParser argumentParser = ArgumentParsers.newFor("intrusion-detection").build().defaultHelp(true)
                .description("Intrusion detection for hogwarts castles on aws");
        argumentParser.addArgument("--input.file.name").type(String.class).setDefault("auth.log")
                .help("Name of the input auth file you wish to analyze");
        argumentParser.addArgument("--output.file.name").type(String.class).setDefault("authHistogram.log")
                .help("Name of the output histogram file produced upon analyzing");
        argumentParser.addArgument("--debug.log.enabled").type(Boolean.class).setDefault(false)
                .help("boolean to determine if debug logging is enabled.");
        argumentParser.addArgument("--log.file.name").type(String.class).setDefault("executionLogs/dada.log")
                .help("Name of the log file.");

        try {
            namespace = argumentParser.parseArgs(args);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }

        return namespace;
    }
}
