package task.process;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


/**
 * TaskResult data struct.
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskResult {
  @Getter(AccessLevel.PROTECTED)
  private final int index;

  @Getter(AccessLevel.PROTECTED)
  private final String query;

  @Getter(AccessLevel.PROTECTED)
  private final List<Integer> indices;
}
