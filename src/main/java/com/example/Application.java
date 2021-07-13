package com.example;

import com.example.api.ApiLogger;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/**
 * Driver code to start RESTful API.
 */
@SpringBootApplication
public class Application {
    // The port this app runs on.
    @Value("${server.port}")
    public static String PORT;

    public static void main(String... args) {
        Properties prop = new Properties();
        try {
            prop.load(Application.class.getClassLoader().getResourceAsStream("application.properties"));
            PORT = prop.getProperty("server.port");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Options options = new Options();
        Option portOption = new Option("p", "port", true,
                "(optional) choose to run on a desired port - default is " + PORT);
        portOption.setRequired(false);
        options.addOption(portOption);

        Option logOption = new Option("l", false, "(optional) include logging information during execution");
        logOption.setRequired(false);
        options.addOption(logOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
            return;
        }

        String port = cmd.getOptionValue("port", PORT);
        ApiLogger.LOG_API_CALLS = Arrays.asList(cmd.getArgs()).contains("l");
        SpringApplication application = new SpringApplication(Application.class);

        try {
            // Just to ensure the port is a valid port.
            int ignored = Integer.parseInt(port);
            PORT = port;
            application.setDefaultProperties(Collections.singletonMap("server.port", port));
        } catch (NumberFormatException ignored) {
            System.err.println("error: invalid port \"" + port + "\" - must be a number");
            formatter.printHelp("utility-name", options);
            System.exit(1);
            return;
        }
        ApiLogger.welcome();
        ApiLogger.start();
        application.run(args);
    }
}
