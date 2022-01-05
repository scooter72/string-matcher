package task;

import org.junit.jupiter.api.Test;
import task.input.Input;
import task.output.Output;
import task.process.Orchestrator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OrchestratorTest {
  @Test
  void runNoMatch() {
    Orchestrator orchestrator = new Orchestrator(1);
    Map<Integer, String> input = new HashMap<>();
    Set<String> queries = new HashSet<>();

    input.put(0, "The quick brown fox jumps over the lazy dog");
    queries.add("foo");

    Output output = orchestrator.run(new Input(input, queries));
    assertTrue(output.getMatchResults().get("foo").isEmpty());
  }

  @Test
  void runSingleMatch() {
    Orchestrator orchestrator = new Orchestrator(1);
    Map<Integer, String> input = new HashMap<>();
    Set<String> queries = new HashSet<>();

    input.put(0, "The quick brown fox jumps over the lazy dog");
    queries.add("quick");

    Output output = orchestrator.run(new Input(input, queries));
    assertEquals(1, output.getMatchResults().get("quick").size());
    assertEquals(4, output.getMatchResults().get("quick").get(/* line */ 0).get(/* indices */ 0));
  }

  @Test
  void runMultiQuery() {
    Orchestrator orchestrator = new Orchestrator(1);
    Map<Integer, String> input = new HashMap<>();
    Set<String> queries = new HashSet<>();

    input.put(0, "The quick brown fox jumps over the lazy dog");
    queries.add("quick");
    queries.add("fox");

    Output output = orchestrator.run(new Input(input, queries));
    assertEquals(1, output.getMatchResults().get("quick").size());
    assertEquals(4, output.getMatchResults().get("quick").get(/* line */ 0).get(/* indices */ 0));
    assertEquals(1, output.getMatchResults().get("fox").size());
    assertEquals(16, output.getMatchResults().get("fox").get(/* line */ 0).get(/* indices */ 0));
  }

  @Test
  void runMultiMatchSingleLine() {
    Orchestrator orchestrator = new Orchestrator(1);
    Map<Integer, String> input = new HashMap<>();
    Set<String> queries = new HashSet<>();

    input.put(0, "The quick brown fox jumps over the lazy dog");
    queries.add("The");

    Output output = orchestrator.run(new Input(input, queries));
    assertEquals(1, output.getMatchResults().get("The").size());
  }

  @Test
  void runMultiLinesMatch() {
    Orchestrator orchestrator = new Orchestrator(1);
    Map<Integer, String> input = new HashMap<>();
    Set<String> queries = new HashSet<>();

    input.put(0, "The dog quick brown fox jumps over the lazy dog");
    queries.add("dog");

    Output output = orchestrator.run(new Input(input, queries));
    List<Integer> queryIndicesInFirstLine = output.getMatchResults().get("dog").get(/* line */ 0);

    // 2 matches are expected in the single line
    assertEquals(2, queryIndicesInFirstLine.size());
    assertEquals(4, queryIndicesInFirstLine.get(/* indices */ 0));
    assertEquals(44, queryIndicesInFirstLine.get(/* indices */ 1));
  }
}