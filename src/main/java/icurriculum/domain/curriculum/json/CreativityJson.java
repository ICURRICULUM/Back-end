package icurriculum.domain.curriculum.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

/**
 * 창의
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class CreativityJson {

    @JsonProperty("과목_지정여부")
    private Boolean isCodeConfirmed;

    @JsonProperty("지정과목")
    private Set<String> confirmedCodes;

    @JsonProperty("요구학점")
    private Integer requiredCredit;

}

/**
 * {
 *   "과목_지정여부": true,
 *   "지정과목": ["CRE4302"],
 *   "요구학점": 3
 * }
 */

