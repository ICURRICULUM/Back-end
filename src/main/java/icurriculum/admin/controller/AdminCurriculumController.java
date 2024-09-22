package icurriculum.admin.controller;

import icurriculum.admin.service.AdminCurriculumService;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.department.Department;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.department.repository.DepartmentRepository;
import icurriculum.domain.membermajor.MajorType;
import java.util.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/curriculum")
@RequiredArgsConstructor
public class AdminCurriculumController {
    private final AdminCurriculumService adminCurriculumService;
    private final DepartmentRepository departmentRepository;
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
}
