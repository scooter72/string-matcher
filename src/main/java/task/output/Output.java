package task.output;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * task.output.Output data struct.
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Output {
  @Getter(AccessLevel.PUBLIC)
  Map<String, Map<Integer, List<Integer>>> matchResults;

  @Getter(AccessLevel.PROTECTED)
  long duration;
}
