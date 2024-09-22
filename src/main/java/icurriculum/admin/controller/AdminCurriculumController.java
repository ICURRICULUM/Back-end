package icurriculum.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/curriculum")
@RequiredArgsConstructor
public class AdminCurriculumController {
    @GetMapping()
    public String showCurriculumPage(){return "admin-curriculum";}
}
