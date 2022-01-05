package task.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class encapsulates the process of the results.
 */
public class MatchResultsProcessor {
  private static final Logger logger = LogManager.getLogger(MatchResultsProcessor.class);

  /**
   * Process the output argument.
   * @param output Process output
   */
  public void process(Output output) {
    logger.debug("Process completed. ({} seconds)", output.getDuration());
    printResults(getSortedMatchResults(output.getMatchResults()));
  }

  /**
   * Return sorted by number of match results in descending order.
   * @param matchResults The results to sort.
   * @return Sorted by number of match results in descending order.
   */
  private static List<MatchResult> getSortedMatchResults(Map<String, Map<Integer, List<Integer>>> matchResults) {
    List<MatchResult> results = new ArrayList<>();
    matchResults.forEach((name, indices) -> results.add(new MatchResult(name, indices)));

    return results.stream()
        .sorted((x, y) -> Integer.compare(y.getResults().size(), x.getResults().size()))
        .collect(Collectors.toList());
  }

  private static void printResults(List<MatchResult> matchResults) {
    printResultsTable(matchResults);
    printDetailedResults(matchResults);
  }

  private static void printResultsTable(List<MatchResult> sortedResults) {
    String leftAlignFormat = "| %-15s | %-6d |%n";
    System.out.format("+-----------------+--------+%n");
    System.out.format("| Name            | Result |%n");
    System.out.format("+-----------------+--------+%n");
    sortedResults.forEach(entry -> System.out.format(leftAlignFormat, entry.getText(), entry.getResults().size()));
    System.out.format("+-----------------+--------+%n");
    System.out.println();
  }

  private static void printDetailedResults(List<MatchResult> sortedResults) {
    StringBuilder builder = new StringBuilder();
    sortedResults.forEach(entry -> {
      builder.delete(0, builder.length());
      builder.append(entry.getText()).append("--> (").append(entry.getResults().size()).append(" results) --> ");
      entry.getResults().forEach(
          (line, indices) ->  builder
              .append("[line=")
              .append(line)
              .append(", indices=") // a name can appear multiple times in a line
              .append(indices.size() > 1 ? indices : indices.get(0)).append("], ")
      );
      logger.debug(builder);
      builder.delete(builder.length() - 2, builder.length());
    });
  }
}
