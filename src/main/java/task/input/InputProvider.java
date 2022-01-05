package task.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class encapsulates inputs to the application.
 */
public class InputProvider {
  private static final Logger logger = LogManager.getLogger(InputProvider.class);
  private final String url;
  private final Set<String> queries;
  private final Map<Integer, String> lines = new HashMap<>();

  /**
   * Constructs an InputProvider instance using the settings argument.
   * @param settings Application settings.
   */
  public InputProvider(Properties settings) {
    if (Objects.isNull(settings)) {
      throw new IllegalArgumentException("settings");
    }

    url = settings.getProperty("url");
    queries = Arrays
        .stream(settings.getProperty("queries").split(","))
        .collect(Collectors.toSet());
  }

  /**
   * Returns an task.input.Input data struct.
   *
   * @return task.input.Input data struct.
   * @exception IOException if an error occurred when reading from the input stream.
   */
  public Input getInput() throws IOException {
    if (lines.size() > 0) {
      return new Input(lines, queries);
    }

    long start = System.currentTimeMillis();
    URL url = new URL(this.url);

    logger.debug(
        "Reading input from URL '{}', this might take a few seconds depending on your connection.",
        url);

    lines.putAll(readLines(url.openStream()));

    logger.debug("{} lines have bean read from url: '{}'. ({} seconds)",
        lines.size(),
        url,
        (System.currentTimeMillis()- start)/1000);

    return new Input(lines, queries);
  }

  /**
   * Reads all lines from the provided inputStream argument.
   * @param inputStream The input stream to read from.
   * @return Map of line index to line text.
   * @throws IOException if an error occurred when reading from the input stream.
   */
  private Map<Integer, String> readLines(InputStream inputStream) throws IOException {
    Map<Integer, String> lines = new HashMap<>();
    String line;
    int lineIndex = 0;

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      while ((line = reader.readLine()) != null) {
        lines.put(lineIndex++, line);
      }
    }

    return lines;
  }
}
