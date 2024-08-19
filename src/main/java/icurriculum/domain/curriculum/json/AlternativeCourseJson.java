package icurriculum.domain.curriculum.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

/**
 * 대체 과목
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class AlternativeCourseJson {

    private Map<String, Set<String>> alternativeCourseMap;

}
