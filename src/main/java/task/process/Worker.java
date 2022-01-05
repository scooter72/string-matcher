package task.process;

import task.input.TaskInput;

import java.util.*;

/**
 * This class represents the basic execution unit that scans text to find matches.
 */
public class Worker {
  /**
   * Scans the text in the taskInput.TaskInput argument and tries to find
   * matches of the items in the queries argument.
   *
   * @param taskInput The input to process.
   * @return Result of the query input and its indices in the text input.
   */
  TaskResult findMatches(TaskInput taskInput) {
    List<Integer> indices = new ArrayList<>();
    String query = taskInput.getQuery();
    String text = taskInput.getText();
    int index = text.indexOf(query);


    while (index  > -1 && index < text.length()) {
      // a name could appear more than once in a line
      indices.add(index);
      index = text.indexOf(query, index + query.length());
    }

    return new TaskResult(taskInput.getId(), query, indices);
  }
}
