package icurriculum.domain.graduation.dto;

import icurriculum.domain.take.Category;

import java.util.*;

public class GraduationResponse {
    private int 전체_이수_학점;
    private int 전체_기준_학점;

    private int 주전공필수_이수학점;
    private int 주전공필수_기준학점;

    private int 주전공선택_이수학점;
    private int 주전공선택_기준학점;

    private int 교양필수_이수학점;
    private int 교양필수_기준학점;

    /**
     * 임시 : 과목코드값을 기준으로 안들은 과목의 정보를 제공해줘야 함.
     */
    private final List<String> 교양필수_미이수과목_코드 = new ArrayList<>();

    private boolean 핵심교양_조건충족;
    private final List<Category> 핵심교양_미이수영역 = new ArrayList<>();

    private boolean SWAI_조건충족;

    private boolean 창의_조건충족;

    public void 창의_영역_입력(int 이수학점, int 기준학점) {
        add_전체이수학점(이수학점);
        창의_조건충족 = 이수학점 >= 기준학점;
    }

    public void SWAI_영역_입력(int 이수학점, int 기준학점) {
        add_전체이수학점(이수학점);
        SWAI_조건충족 = 이수학점 >= 기준학점;
    }

    public void 핵심교양_영역_입력_영역상관없을때(int 이수학점, int 기준학점) {
        add_전체이수학점(이수학점);
        핵심교양_조건충족 = 이수학점 >= 기준학점;
    }

    public void 핵심교양_영역_입력_영역상관있을때(int 이수학점, List<Category> 미이수영역) {
        add_전체이수학점(이수학점);
        if (미이수영역.isEmpty()) {
            핵심교양_조건충족 = true;
            return;
        }
        핵심교양_조건충족 = false;
        핵심교양_미이수영역.addAll(미이수영역);
    }

    public void 교양필수_영역_입력(int 이수학점, int 기준학점, List<String> 미이수과목) {
        add_전체이수학점(이수학점);
        교양필수_이수학점 += 이수학점;
        교양필수_기준학점 += 기준학점;
        교양필수_미이수과목_코드.addAll(미이수과목);
    }

    private void add_전체이수학점(int credit){
        전체_이수_학점 += credit;
    }

}
