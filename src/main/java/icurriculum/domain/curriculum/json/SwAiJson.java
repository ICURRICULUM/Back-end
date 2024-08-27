package icurriculum.domain.curriculum.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

/**
 * SW_AI
 * 지정과목 - 해당과목만 내에서 들어야 한다.
 *  ex. 컴퓨터공학과, 인공지능학과 / 컴공인공지능 제외 학과 2가지 경우로 나눠짐
 *
 * 대체과목 - 해당과목을 들었으면 인정되는 경우이다. 이 상황에서는 해당과목이 다른 영역에 영향을 줄 수 있다
 *  ex. 전기공학과 ("ACE2105", "ACE2103", "ACE1307") // 교양필수
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class SwAiJson {

    @JsonProperty("과목_지정여부")
    private Boolean isCodeConfirmed;

    @JsonProperty("지정과목")
    private Set<String> confirmedCodes;

    @JsonProperty("대체과목")
    private Set<String> alternativeCodes;

    @JsonProperty("요구학점")
    private Integer requiredCredit;

}

/**
 * {
 *   "과목_지정여부": true,
 *   "지정과목": ["CEE1101"],
 *   "대체과목": ["ACE2105", "ACE2103", "ACE1307"],
 *   "요구학점": 3
 * }
 */