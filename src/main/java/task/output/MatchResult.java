package task.output;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * Match result data struct.
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchResult {
  @Getter(AccessLevel.PROTECTED)
  private String query;

  @Getter(AccessLevel.PROTECTED)
  private Map<Integer, List<Integer>> indexToIndices;
}
