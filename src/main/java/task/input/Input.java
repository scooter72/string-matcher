package task.input;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

/**
 * Task input data struct.
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Input {
  @Getter(AccessLevel.PUBLIC)
  private Map<Integer, String> input;

  @Getter(AccessLevel.PUBLIC)
  private Set<String> queries;
}
