package icurriculum.domain.graduation.processor;

import icurriculum.DataInitializer;
import icurriculum.domain.course.Course;
import icurriculum.domain.course.repository.CourseRepository;
import icurriculum.domain.course.service.CourseService;
import icurriculum.domain.curriculum.json.CurriculumCodesJson;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.domain.department.Department;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.graduation.processor.dto.ProcessorDto;
import icurriculum.domain.graduation.processor.strategy.generalrequirement.GeneralRequirement;
import icurriculum.domain.graduation.processor.strategy.generalrequirement.strategy.GeneralRequirementStrategy;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
import icurriculum.domain.membermajor.util.MemberMajorUtils;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.repository.TakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static icurriculum.domain.take.util.TakeUtils.getTakesByCategory;

@SpringBootTest
@Transactional
class GeneralRequirementProcessorTest {

    @Autowired
    DataInitializer dataInitializer;
    @Autowired
    TakeRepository takeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberMajorRepository memberMajorRepository;
    @Autowired
    CurriculumRepository curriculumRepository;
    @Autowired
    CourseService courseService;
    @Autowired
    CourseRepository courseRepository;


    @Autowired
    Map<DepartmentName, GeneralRequirementStrategy> generalRequirementStrategyMap;

    GeneralRequirementProcessor generalRequirementProcessor;
    CurriculumCodesJson curriculumCodesJson;
    Department department;
    List<Take> takes;

    @BeforeEach
    void beforeEach() {
        generalRequirementProcessor = new GeneralRequirementProcessor(generalRequirementStrategyMap);

        Long testMemberId = dataInitializer.getTestMemberId();
        Member testMember = memberRepository.findById(testMemberId).get();
        List<MemberMajor> memberMajors = memberMajorRepository.findByMember(testMember);
        MemberMajor mainMemberMajor = MemberMajorUtils.getMainMemberMajor(memberMajors);
        department = mainMemberMajor.getDepartment();
        List<Take> allTakes = takeRepository.findByMemberAndDepartment(testMember, department);
        takes = getTakesByCategory(allTakes, Category.교양필수);

        curriculumCodesJson = dataInitializer.getTestCurriculumCodesJson();
    }


    @Test
    @DisplayName("교양필수 처리")
    public void execute() throws Exception {
        // given
        Set<String> codes = curriculumCodesJson.findByCategory(Category.교양필수);
        List<Course> generalRequirementCourses = courseService.getCoursesByCodesAndDepartment(codes, department);
        GeneralRequirement generalRequirement = new GeneralRequirement(generalRequirementCourses, DepartmentName.컴퓨터공학과);

        // when
        ProcessorDto.GeneralRequirementDto execute = generalRequirementProcessor.execute(generalRequirement, takes);
        // then
        System.out.println("GeneralRequirementDto = " + execute);
    }

}