package task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import task.input.Input;
import task.input.InputProvider;
import task.output.MatchResultsProcessor;
import task.output.Output;
import task.process.Orchestrator;

import java.io.IOException;
import java.util.*;

/**
 * Application main entry class.
 */
public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);
  private static final int DEFAULT_NUMBER_THREADS = 32;

  /**
   * Application main entry.
   */
  public static void main(String[] args) {
    try {
      Properties settings = loadSettings();

      // input
      InputProvider inputProvider = new InputProvider(settings);
      Input input = inputProvider.getInput();

      // process
      Orchestrator orchestrator = new Orchestrator(getNumberOfThreads(settings));
      Output output = orchestrator.run(input);

      // output
      MatchResultsProcessor resultsProcessor = new MatchResultsProcessor();
      resultsProcessor.process(output);
    }
    catch(IOException ex) {
      logger.error(ex);
      ex.printStackTrace();
    }
  }

  /**
   * Loads and returns a Properties object.
   * @return Application seeings Properties object.
   * @throws IOException if an error occurred when reading from the input stream.
   */
  private static Properties loadSettings() throws IOException {
    Properties settings = new Properties();
    settings.load(Main.class.getResourceAsStream("/settings.properties"));
    return settings;
  }


  /**
   * Parse the number of threads to use to process the tasks
   * @param settings Application settings
   * @return Parsed value or default value if the settings value cannot be parsed
   */
  private static int getNumberOfThreads(Properties settings) {
    try {
      return Integer.parseInt(settings.getProperty("number.of.threads"));
    } catch (NumberFormatException ex) {
      return DEFAULT_NUMBER_THREADS;
    }
  }
}

