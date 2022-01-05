package task.input;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * task.input.TaskInput data struct.
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TaskInput {
  @Getter(AccessLevel.PUBLIC)
  private final int id;

  @Getter(AccessLevel.PUBLIC)
  private final String query;

  @Getter(AccessLevel.PUBLIC)
  private final String text;
}
