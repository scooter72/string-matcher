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

  /**
   * Each entry in this map is a query mapped to its line index to indices of the query in that line.
   */
  @Getter(AccessLevel.PUBLIC) Map<String, Map<Integer, List<Integer>>> matchResults;

  /**
   * The process duration.
   */
  @Getter(AccessLevel.PROTECTED) long duration;
}
