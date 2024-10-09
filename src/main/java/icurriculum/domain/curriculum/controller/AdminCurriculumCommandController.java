package icurriculum.domain.curriculum.controller;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.service.AdminCurriculumService;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/curriculum")
@RequiredArgsConstructor
public class AdminCurriculumCommandController {
    private final AdminCurriculumService adminCurriculumService;
    @GetMapping("/load")
    public ApiResponse<Curriculum> getCurriculum(@RequestParam MajorType majorType,
                                                 @RequestParam DepartmentName departmentName,
                                                 @RequestParam Integer joinYear){
        CurriculumDecider decider = adminCurriculumService.convertToDecider(majorType, departmentName, joinYear);
        return ApiResponse.onSuccess(adminCurriculumService.getCurriculumByDecider(decider));

    }
    @PostMapping ("/save")
    public ApiResponse<String> createCurriculum(@RequestParam MajorType majorType,
                                                    @RequestParam DepartmentName departmentName,
                                                    @RequestParam Integer joinYear,
                                                    @RequestBody Curriculum curriculumData){
        CurriculumDecider decider = adminCurriculumService.convertToDecider(majorType, departmentName, joinYear);
       return ApiResponse.onSuccess(adminCurriculumService.createCurriculum(curriculumData, decider));
    }
    @PostMapping("/update")
    public ApiResponse<String> updateCurriculum(@RequestParam MajorType majorType,
                                                @RequestParam DepartmentName departmentName,
                                                @RequestParam Integer joinYear,
                                                @RequestBody Curriculum curriculumData) {
        CurriculumDecider decider = adminCurriculumService.convertToDecider(majorType, departmentName, joinYear);
        adminCurriculumService.updateCurriculum(curriculumData, decider);
        return ApiResponse.onSuccess("성공적으로 커리큘럼이 업데이트되었습니다.");



    }

    @DeleteMapping("/delete")
    public ApiResponse<String> deleteCurriculum(@RequestParam MajorType majorType,
                                          @RequestParam DepartmentName departmentName,
                                          @RequestParam Integer joinYear){
        CurriculumDecider decider = adminCurriculumService.convertToDecider(majorType, departmentName, joinYear);
        adminCurriculumService.deleteCurriculum(decider);
        return ApiResponse.onSuccess("성공적으로 커리큘럼 삭제");
    }
}
