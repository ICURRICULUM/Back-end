package icurriculum.admin.controller;

import icurriculum.admin.service.AdminCurriculumService;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.domain.department.Department;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.department.repository.DepartmentRepository;
import icurriculum.domain.membermajor.MajorType;
import java.util.Currency;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/curriculum")
@RequiredArgsConstructor
public class AdminCurriculumController {
    private final AdminCurriculumService adminCurriculumService;
    private final DepartmentRepository departmentRepository;
    private final CurriculumRepository curriculumRepository;
    @GetMapping()
    public String showCurriculumPage(){return "admin-curriculum";}

    @GetMapping("/load")
    @ResponseBody
    public Curriculum loadCurriculum(@RequestParam MajorType majorType,
                                     @RequestParam DepartmentName departmentName,
                                     @RequestParam Integer joinYear) {
        // Department 객체를 데이터베이스에서 조회
        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new RuntimeException("학과가 존재하지 않음"));

        CurriculumDecider curriculumDecider = new CurriculumDecider(majorType, department, joinYear);


        return adminCurriculumService.getCurriculumByDecider(curriculumDecider);
    }

    @PostMapping("/save")
    @ResponseBody
    public String saveCurriculum(@RequestParam MajorType majorType,
                                 @RequestParam DepartmentName departmentName,
                                 @RequestParam Integer joinYear,
                                 @RequestBody Curriculum curriculumData) {
        // Department 객체를 데이터베이스에서 조회
        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new RuntimeException("학과가 존재하지 않음"));

        CurriculumDecider curriculumDecider = new CurriculumDecider(majorType, department, joinYear);

        // 기존 Curriculum이 있는지 확인
        Optional<Curriculum> existingCurriculumOptional = curriculumRepository.findByDecider(curriculumDecider);

        if (existingCurriculumOptional.isPresent()) {
            // 이미 존재하는 경우, 덮어쓰기 요청을 반환
            return "duplicate";
        }

        // 새로운 Curriculum 저장
        Curriculum newCurriculum = Curriculum.builder()
                .decider(curriculumDecider)
                .coreJson(curriculumData.getCoreJson())
                .swAiJson(curriculumData.getSwAiJson())
                .creativityJson(curriculumData.getCreativityJson())
                .requiredCreditJson(curriculumData.getRequiredCreditJson())
                .curriculumCodesJson(curriculumData.getCurriculumCodesJson())
                .alternativeCourseJson(curriculumData.getAlternativeCourseJson())
                .build();

        curriculumRepository.save(newCurriculum);
        return "새로운 커리큘럼 등록 성공!";
    }
    @PostMapping("/overwrite")
    @ResponseBody

    public String overwriteCurriculum(@RequestParam MajorType majorType,
                                      @RequestParam DepartmentName departmentName,
                                      @RequestParam Integer joinYear,
                                      @RequestBody Curriculum curriculumData) {

        // Department 객체를 데이터베이스에서 조회
        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new RuntimeException("학과가 존재하지 않음"));

        CurriculumDecider curriculumDecider = new CurriculumDecider(majorType, department, joinYear);

        // 기존 Curriculum을 가져와서 덮어쓰기
        Curriculum existingCurriculum = curriculumRepository.findByDecider(curriculumDecider)
                .orElseThrow(() -> new RuntimeException("기존 커리큘럼을 찾을 수 없습니다."));


        adminCurriculumService.modifyCurriculum(curriculumData, existingCurriculum);


        return "성공적으로 커리큘럼 수정!";
    }
}
