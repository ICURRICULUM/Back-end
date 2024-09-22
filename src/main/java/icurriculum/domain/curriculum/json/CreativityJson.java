package icurriculum.domain.curriculum.json;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 창의
 */

/*
 * {
 *   "지정과목": ["CRE4302", ... ],
 *   "요구학점": 3
 * }
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class CreativityJson {

    @JsonProperty("지정과목")
    private Set<String> confirmedCodes;

    @JsonProperty("요구학점")
    private Integer requiredCredit;

}


