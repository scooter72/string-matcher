package task.process;

import task.input.Input;
import task.input.TaskInput;
import task.output.Output;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class orchestras the parallel processing and aggregates the results.
 */
public class Orchestrator {
  private final int numberOfThreads;

  /**
   * COnstruct an instance of Orchestrator with the numberOfThreads argument.
   * @param numberOfThreads numberOfThreads this Orchestrator will use to process the tasks.
   */
  public Orchestrator( int numberOfThreads) {
    this.numberOfThreads = numberOfThreads;
  }

  /**
   * Process the input in parallel waits for all of the threads to complete and returns the aggregation
   * of the worker results.
   * @param input Process input data struct.
   * @return Aggregations of match results.
   */
  public Output run(Input input) {
    long start = System.currentTimeMillis();
    Set<String> queries = input.getQueries();
    Map<Integer, String> lines = input.getInput();
    ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
    LinkedList<TaskInput> tasksQueue = new LinkedList<>();
    Map<String, Map<Integer, List<Integer>>> matchResults = new HashMap<>();

    queries.forEach(query -> {
      // init map query entry to empty match results
      matchResults.put(query, new HashMap<>());

      // build a task queue
      // each task contains: [line index, name, line of text]
      lines.keySet()
        .forEach(
          index -> tasksQueue.add(new TaskInput(index, query, lines.get(index)))
        );
    });

    for (int i = 0; i < numberOfThreads; i++) {
      Worker matcher = new Worker();
      Runnable worker = () -> {
        TaskInput taskInput;

        // Eagerly poll tasks from the queue
        while (tasksQueue.peek() != null) {
          synchronized (tasksQueue) {
            taskInput = tasksQueue.poll();
          }

          if (taskInput != null) {
            TaskResult taskResult = matcher.findMatches(taskInput);

            if (taskResult.getIndices().size() > 0) {
               matchResults.get(taskResult.getQuery()).put(taskResult.getIndex(), taskResult.getIndices());
            }
          }
        }
      };
      executor.execute(worker);
    }

    executor.shutdown();

    while (!executor.isTerminated()) {
      // Wait until all threads are finish
    }

    return new Output(matchResults, (System.currentTimeMillis()-start)/1000);
  }
}
