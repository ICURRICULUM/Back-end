package icurriculum.global.response.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "로그인 인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    /**
     * Take
     */
    TAKE_HAS_ABNORMAL_COURSE(HttpStatus.INTERNAL_SERVER_ERROR, "TAKE501",
        "Take에는 Course 또는 CustomCourse 중 하나만 채워져 있어야 한다"),

    /**
     * MemberMajor
     */
    MEMBER_MAJOR_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBERMAJOR401", "회원의 전공이 한개도 존재하지 않습니다."),
    MEMBER_MAIN_MAJOR_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBERMAJOR402", "회원의 주전공이 존재하지 않습니다."),

    /**
     * Curriculum
     */
    CURRICULUM_NOT_FOUND(HttpStatus.NOT_FOUND, "CURRICULUM401", "해당학과의 졸업요건을 지원하지 않습니다."),

    CURRICULUM_NOT_VALID_CATEGORY(HttpStatus.INTERNAL_SERVER_ERROR, "CURRICULUM0501",
        "CurriculumCodesJson 에는 전공필수, 전공선택, 교양필수만 존재합니다."),

    /**
     * graduation
     */
    CATEGORY_IS_NOT_VALID(HttpStatus.BAD_REQUEST, "CATEGORYERROR401", "사용자가 수정한 과목영역이 올바르지 않습니다."),
    PROCESSOR_FIND_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "GRADUATION501",
        "졸업요건을 검사하기 위한 내부 로직에 에러가 발생했습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}