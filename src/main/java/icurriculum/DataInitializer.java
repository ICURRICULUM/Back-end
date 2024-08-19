package icurriculum;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.alternative.AlternativeCourse;
import icurriculum.domain.course.repository.CourseRepository;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.json.*;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.domain.department.Department;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.department.repository.DepartmentRepository;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.RoleType;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.CustomCourse;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.repository.TakeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

import static icurriculum.domain.membermajor.MajorType.주전공;
import static icurriculum.domain.take.Category.*;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private static final Long testMemberId = 1L;

    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final MemberMajorRepository memberMajorRepository;

    private final CourseRepository courseRepository;
    private final TakeRepository takeRepository;
    private final CurriculumRepository curriculumRepository;

    /**
     * 기본 데이터 추가
     */
    @PostConstruct
    public void init() {
        Department department = getTestDepartmentOnlyData();
        departmentRepository.save(department);

        Member member = getTestMemberOnlyData();
        memberRepository.save(member);

        MemberMajor memberMajor = getTestMemberMajorOnlyData(member, department);
        memberMajorRepository.save(memberMajor);

        List<Course> courses = getTestCoursesOnlyData(department);
        courseRepository.saveAll(courses);

        List<Take> takes = getTakesOnlyData(member);
        takeRepository.saveAll(takes);

        Curriculum curriculum = getTestCurriculumData(memberMajor);
        curriculumRepository.save(curriculum);
    }

    public Long getTestMemberId() {
        return testMemberId;
    }

    public Department getTestDepartmentOnlyData() {
        return Department.builder()
                .name(DepartmentName.컴퓨터공학과)
                .build();
    }

    public Member getTestMemberOnlyData() {
        return Member.builder()
                .name("이승철")
                .joinYear(19)
                .role(RoleType.ROLE_USER)
                .build();
    }

    public MemberMajor getTestMemberMajorOnlyData(Member member, Department department) {
        return MemberMajor.builder()
                .majorType(주전공)
                .department(department)
                .member(member)
                .build();
    }

    public List<Course> getTestCoursesOnlyData(Department department) {
        Course 의영 = Course.builder().code("GEB1107").name("의사소통 영어").credit(3).build();
        Course 의영중급 = Course.builder().code("GEB1108").name("의사소통 영어: 중급").credit(3).build();
        Course 의영고급 = Course.builder().code("GEB1109").name("의사소통 영어: 고급").credit(3).build();

        AlternativeCourse 의영대체 = new AlternativeCourse(의영, 의영중급, department);
        AlternativeCourse 의영중급대체 = new AlternativeCourse(의영, 의영고급, department);
        AlternativeCourse 의영고급대체 = new AlternativeCourse(의영중급, 의영, department);

        List<AlternativeCourse> alternativeCourses = Arrays.asList(의영대체);
        List<AlternativeCourse> alternativeCourses2 = Arrays.asList(의영중급대체);
        List<AlternativeCourse> alternativeCourses3 = Arrays.asList(의영고급대체);

        의영.setAlternativeCourses(alternativeCourses);
        의영중급.setAlternativeCourses(alternativeCourses2);
        의영고급.setAlternativeCourses(alternativeCourses3);


        Course LS = Course.builder().code("GEB1201").name("실용영어 L/S").credit(3).build();
        Course RW = Course.builder().code("GEB1202").name("실용영어 R/W").credit(3).build();
        Course 고대영 = Course.builder().code("GEB1203").name("고급대학영어").credit(3).build();

        AlternativeCourse alternativeCourse7 = new AlternativeCourse(LS, RW, department);
        AlternativeCourse alternativeCourse8 = new AlternativeCourse(LS, 고대영, department);
        AlternativeCourse alternativeCourse9 = new AlternativeCourse(RW, LS, department);
        AlternativeCourse alternativeCourse10 = new AlternativeCourse(RW, 고대영, department);
        AlternativeCourse alternativeCourse11 = new AlternativeCourse(고대영, LS, department);
        AlternativeCourse alternativeCourse12 = new AlternativeCourse(고대영, RW, department);
        List<AlternativeCourse> alternativeCourses4 = Arrays.asList(alternativeCourse7, alternativeCourse8);
        List<AlternativeCourse> alternativeCourses5 = Arrays.asList(alternativeCourse9, alternativeCourse10);
        List<AlternativeCourse> alternativeCourses6 = Arrays.asList(alternativeCourse11, alternativeCourse12);
        LS.setAlternativeCourses(alternativeCourses4);
        RW.setAlternativeCourses(alternativeCourses5);
        고대영.setAlternativeCourses(alternativeCourses6);

        return Arrays.asList(
                Course.builder().code("GEB1112").name("크로스오버 1 : 인간의 탐색").credit(2).build(),
                Course.builder().code("GEB1114").name("크로스오버 3 : 사회의 탐색").credit(2).build(),
                Course.builder().code("GEB1115").name("프로네시스 세미나 Ⅰ : 가치형성과 진로탐색").credit(1).build(),
                Course.builder().code("GEB1124").name("이공계열 글쓰기와 토론").credit(3).build(),
                Course.builder().code("GEB1131").name("생활한문").credit(1).build(),
                의영,
                의영중급,
                의영고급,
                LS,
                RW,
                고대영,
                Course.builder().code("ACE1204").name("생명과학").credit(4).build(),
                Course.builder().code("ACE1312").name("이산수학").credit(3).build(),
                Course.builder().code("ACE2101").name("공업수학 1").credit(3).build(),
                Course.builder().code("ACE2104").name("통계학").credit(3).build(),
                Course.builder().code("MTH1001").name("일반수학 1").credit(3).build(),
                Course.builder().code("MTH1002").name("일반수학 2").credit(3).build(),
                Course.builder().code("PHY1001").name("물리학 1").credit(3).build(),
                Course.builder().code("PHY1002").name("물리학 2").credit(3).build(),
                Course.builder().code("PHY1003").name("물리학실험 1").credit(1).build(),
                Course.builder().code("PHY1004").name("물리학실험 2").credit(1).build(),
                Course.builder().code("CSE1101").name("객체지향프로그래밍 1").credit(3).build(),
                Course.builder().code("CSE1102").name("컴퓨터공학입문 및 실습").credit(3).build(),
                Course.builder().code("CSE1103").name("객체지향프로그래밍 2").credit(3).build(),
                Course.builder().code("CSE2101").name("논리회로").credit(3).build(),
                Course.builder().code("CSE2112").name("자료구조").credit(4).build(),
                Course.builder().code("CSE4205").name("컴퓨터공학 종합설계").credit(3).build(),
                Course.builder().code("ICE4029").name("모바일응용소프트웨어설계").credit(4).build(),
                Course.builder().code("IEN3204").name("정보검색론").credit(3).build(),
                Course.builder().code("CSE1105").name("창의적컴퓨터공학설계").credit(3).build(),
                Course.builder().code("CSE2103").name("어셈블리어").credit(3).build(),
                Course.builder().code("CSE2104").name("인터넷 프로그래밍").credit(3).build(),
                Course.builder().code("CSE2105").name("컴퓨터기반선형대수").credit(3).build(),
                Course.builder().code("CSE2107").name("자바기반응용프로그래밍").credit(3).build(),
                Course.builder().code("CSE3101").name("수치프로그래밍").credit(3).build(),
                Course.builder().code("CSE3201").name("시스템 프로그래밍").credit(3).build(),
                Course.builder().code("CSE3203").name("컴퓨터구조론").credit(3).build(),
                Course.builder().code("CSE3206").name("오퍼레이팅시스템").credit(3).build(),
                Course.builder().code("CSE3305").name("시스템분석").credit(3).build(),
                Course.builder().code("CSE3306").name("문제해결기법").credit(3).build(),
                Course.builder().code("CSE4201").name("소프트웨어공학").credit(3).build(),
                Course.builder().code("CSE4202").name("컴퓨터 네트워크").credit(3).build(),
                Course.builder().code("CSE4204").name("알고리즘").credit(3).build(),
                Course.builder().code("CSE4308").name("컴퓨터보안").credit(3).build(),
                Course.builder().code("CSE3102").name("컴퓨터응용확률").credit(3).build(),
                Course.builder().code("CSE3207").name("데이터베이스").credit(3).build(),
                Course.builder().code("CSE4302").name("인공지능").credit(3).build(),
                Course.builder().code("CSE4305").name("생물의료정보학개론").credit(3).build(),
                Course.builder().code("CSE4311").name("데이터베이스 응용").credit(3).build(),
                Course.builder().code("CSE3202").name("프로그래밍언어론").credit(3).build(),
                Course.builder().code("CSE3205").name("오토마타 및 지능 컴퓨팅").credit(3).build(),
                Course.builder().code("CSE3302").name("마이크로프로세서응용").credit(3).build(),
                Course.builder().code("CSE3303").name("유닉스 프로그래밍").credit(3).build(),
                Course.builder().code("CSE3304").name("임베디드소프트웨어").credit(3).build(),
                Course.builder().code("CSE3307").name("무선통신 및 네트워킹").credit(3).build(),
                Course.builder().code("CSE4312").name("컴파일러").credit(3).build(),
                Course.builder().code("CSE3204").name("컴퓨터그래픽스").credit(3).build(),
                Course.builder().code("CSE4301").name("전자상거래").credit(3).build(),
                Course.builder().code("CSE4303").name("게임프로그래밍").credit(3).build(),
                Course.builder().code("CSE4304").name("영상처리 및 이해").credit(3).build(),
                Course.builder().code("CSE4307").name("멀티미디어 컴퓨팅").credit(3).build(),
                Course.builder().code("CSE3308").name("시스템분석").credit(3).build(),
                Course.builder().code("CSE3309").name("문제해결기법").credit(4).build(),
                Course.builder().code("CSE4314").name("데이터마이닝").credit(3).build(),
                Course.builder().code("CSE3209").name("시스템 프로그래밍").credit(3).build(),
                Course.builder().code("CSE3208").name("오토마타 및 지능 컴퓨팅").credit(3).build(),
                Course.builder().code("GEB1116").name("프로네시스 세미나").credit(2).build(),
                Course.builder().code("GEB1126").name("문제해결을 위한 글쓰기").credit(3).build(),
                Course.builder().code("GEB1143").name("미래사회와 소프트웨어-IT계열").credit(3).build(),
                Course.builder().code("CSE1112").name("컴퓨터공학입문").credit(2).build(),
                Course.builder().code("CSE2113").name("오픈소스SW개론").credit(3).build(),
                Course.builder().code("CSE3212").name("컴퓨터 네트워크").credit(3).build(),
                Course.builder().code("CSE3313").name("리눅스 프로그래밍").credit(3).build(),
                Course.builder().code("CSE3315").name("바이오빅데이터").credit(3).build(),
                Course.builder().code("CSE4315").name("기계학습").credit(3).build(),
                Course.builder().code("CSE4313").name("컴퓨터보안").credit(3).build(),
                Course.builder().code("GEB1151").name("커리어 디자인 2").credit(1).build(),
                Course.builder().code("CSE1312").name("이산구조").credit(3).build(),
                Course.builder().code("CSE3210").name("오픈소스응용프로그래밍").credit(3).build(),
                Course.builder().code("CSE3211").name("클라우드 컴퓨팅").credit(3).build(),
                Course.builder().code("CSE3104").name("컴퓨터응용확률").credit(3).build(),
                Course.builder().code("CSE4232").name("프로그래밍언어 이론").credit(3).build(),
                Course.builder().code("CSE4566").name("바이오빅데이터").credit(3).build(),

                // 추가 데이터
                Course.builder().code("GEG1123").name("기초물리학").credit(3).build(),
                Course.builder().code("MTH1010").name("기초수학").credit(3).build(),

                Course.builder().code("GEC1027").name("축제와 인간사회").credit(3).build(),

                Course.builder().code("GED4009").name("동북아와 한일관계").credit(3).build(),

                Course.builder().code("GEE3029").name("고급대학영어").credit(3).build(),

                Course.builder().code("GED3018").name("그래픽 디자인 이야기").credit(3).build(),

                Course.builder().code("GEE5017").name("품질의 차원").credit(3).build(),

                Course.builder().code("GED6005").name("지능정보서비스와 소프트웨어").credit(3).build(),
                Course.builder().code("GEE4011").name("세상을 바꾸는 스타트업 이야기").credit(3).build()
        );
    }

    public List<Take> getTakesOnlyData(Member member) {
        return Arrays.asList(
                // 2019학년도 1학기
                Take.builder().category(전공필수).takenYear("2019").takenSemester("1").course(courseRepository.findByCode("CSE1101").get()).member(member).build(),
                Take.builder().category(교양필수).takenYear("2019").takenSemester("1").course(courseRepository.findByCode("GEB1114").get()).member(member).build(),
                Take.builder().category(교양필수).takenYear("2019").takenSemester("1").course(courseRepository.findByCode("GEB1115").get()).member(member).build(),
                Take.builder().category(교양필수).takenYear("2019").takenSemester("1").course(courseRepository.findByCode("GEB1124").get()).member(member).build(),
                Take.builder().category(교양필수).takenYear("2019").takenSemester("1").course(courseRepository.findByCode("GEB1131").get()).member(member).build(),
                Take.builder().category(교양선택).takenYear("2019").takenSemester("1").course(courseRepository.findByCode("GEG1123").get()).member(member).build(),
                Take.builder().category(교양선택).takenYear("2019").takenSemester("1").course(courseRepository.findByCode("MTH1010").get()).member(member).build(),

                // 2019학년도 2학기
                Take.builder().category(교양필수).takenYear("2019").takenSemester("2").course(courseRepository.findByCode("ACE1204").get()).member(member).build(),
                Take.builder().category(전공필수).takenYear("2019").takenSemester("2").course(courseRepository.findByCode("CSE1102").get()).member(member).build(),
                Take.builder().category(전공필수).takenYear("2019").takenSemester("2").course(courseRepository.findByCode("CSE1103").get()).member(member).build(),
                Take.builder().category(교양필수).takenYear("2019").takenSemester("2").course(courseRepository.findByCode("GEB1108").get()).member(member).build(),
                Take.builder().category(교양필수).takenYear("2019").takenSemester("2").course(courseRepository.findByCode("GEB1112").get()).member(member).build(),
                Take.builder().category(핵심교양1).takenYear("2019").takenSemester("2").course(courseRepository.findByCode("GEC1027").get()).member(member).build(),
                Take.builder().category(교양필수).takenYear("2019").takenSemester("2").course(courseRepository.findByCode("MTH1001").get()).member(member).build(),

                // 2021학년도 2학기
                Take.builder().category(핵심교양4).takenYear("2021").takenSemester("2").course(courseRepository.findByCode("GED4009").get()).member(member).build(),

                // 2021학년도 동계학기
                Take.builder().category(교양필수).takenYear("2021").takenSemester("동계").course(courseRepository.findByCode("MTH1002").get()).member(member).build(),
                Take.builder().category(교양필수).takenYear("2021").takenSemester("동계").course(courseRepository.findByCode("PHY1001").get()).member(member).build(),
                Take.builder().category(교양필수).takenYear("2021").takenSemester("동계").course(courseRepository.findByCode("PHY1002").get()).member(member).build(),

                // 2022학년도 1학기
                Take.builder().category(교양필수).takenYear("2022").takenSemester("1").course(courseRepository.findByCode("ACE2104").get()).member(member).build(),
                Take.builder().category(전공선택).takenYear("2022").takenSemester("1").course(courseRepository.findByCode("CSE2104").get()).member(member).build(),
                Take.builder().category(전공선택).takenYear("2022").takenSemester("1").course(courseRepository.findByCode("CSE2107").get()).member(member).build(),
                Take.builder().category(전공필수).takenYear("2022").takenSemester("1").course(courseRepository.findByCode("CSE2112").get()).member(member).build(),
                Take.builder().category(교양선택).takenYear("2022").takenSemester("1").course(courseRepository.findByCode("GEE3029").get()).member(member).build(),
                Take.builder().category(교양필수).takenYear("2022").takenSemester("1").course(courseRepository.findByCode("PHY1003").get()).member(member).build(),

                // 2022학년도 하계학기
                Take.builder().category(교양필수).takenYear("2022").takenSemester("하계").course(courseRepository.findByCode("ACE2101").get()).member(member).build(),

                // 2022학년도 2학기
                Take.builder().category(교양필수).takenYear("2022").takenSemester("2").course(courseRepository.findByCode("ACE1312").get()).member(member).build(),
                Take.builder().category(전공필수).takenYear("2022").takenSemester("2").course(courseRepository.findByCode("CSE2101").get()).member(member).build(),
                Take.builder().category(전공선택).takenYear("2022").takenSemester("2").course(courseRepository.findByCode("CSE2113").get()).member(member).build(),
                Take.builder().category(전공선택).takenYear("2022").takenSemester("2").course(courseRepository.findByCode("CSE3209").get()).member(member).build(),
                Take.builder().category(핵심교양3).takenYear("2022").takenSemester("2").course(courseRepository.findByCode("GED3018").get()).member(member).build(),
                Take.builder().category(교양필수).takenYear("2022").takenSemester("2").course(courseRepository.findByCode("PHY1004").get()).member(member).build(),

                // 2023학년도 1학기
                Take.builder().category(전공선택).takenYear("2023").takenSemester("1").course(courseRepository.findByCode("CSE3203").get()).member(member).build(),
                Take.builder().category(전공선택).takenYear("2023").takenSemester("1").course(courseRepository.findByCode("CSE3206").get()).member(member).build(),
                Take.builder().category(전공선택).takenYear("2023").takenSemester("1").course(courseRepository.findByCode("CSE3207").get()).member(member).build(),
                Take.builder().category(전공선택).takenYear("2023").takenSemester("1").course(courseRepository.findByCode("CSE4204").get()).member(member).build(),
                Take.builder().category(교양선택).takenYear("2023").takenSemester("1").course(courseRepository.findByCode("GED6005").get()).member(member).build(),
                Take.builder().category(교양선택).takenYear("2023").takenSemester("1").course(courseRepository.findByCode("GEE4011").get()).member(member).build(),

                // 2023학년도 2학기
                Take.builder().category(전공선택).takenYear("2023").takenSemester("2").course(courseRepository.findByCode("CSE3212").get()).member(member).build(),
                Take.builder().category(전공선택).takenYear("2023").takenSemester("2").course(courseRepository.findByCode("CSE3309").get()).member(member).build(),
                Take.builder().category(전공선택).takenYear("2023").takenSemester("2").course(courseRepository.findByCode("CSE3313").get()).member(member).build(),
                Take.builder().category(전공선택).takenYear("2023").takenSemester("2").course(courseRepository.findByCode("CSE4301").get()).member(member).build(),
                Take.builder().category(교양선택).takenYear("2023").takenSemester("2").course(courseRepository.findByCode("GEE5017").get()).member(member).build(),


                // 2023학년도 동계학기
                Take.builder().category(교양선택).takenYear("2023").takenSemester("동계").customCourse(new CustomCourse("AAO9017", "현장실습 6", 6)).member(member).build(),

                // 2024학년도 1학기
                Take.builder().category(전공선택).takenYear("2024").takenSemester("1").customCourse(new CustomCourse("CSE9318", "현장실습 18", 18)).member(member).build()
        );
    }

    public Curriculum getTestCurriculumData(MemberMajor memberMajor) {
        CurriculumDecider decider = getTestCurriculumDecider(memberMajor);
        return Curriculum.builder()
                .decider(decider)
                .coreJson(getTestCoreJson())
                .swAiJson(getTestSwAiJson())
                .creativityJson(getTestCreativityJson())
                .requiredCreditJson(getTestRequirementCreditJson())
                .curriculumCodesJson(getTestCurriculumCodesJson())
                .build();
    }

    private CurriculumDecider getTestCurriculumDecider(MemberMajor memberMajor) {
        return CurriculumDecider.createCurriculumDecider(memberMajor);
    }

    public CoreJson getTestCoreJson() {
        return new CoreJson(false, 9, new HashSet<>(), new HashMap<>());
    }

    public SwAiJson getTestSwAiJson() {
        return new SwAiJson(false, new HashSet<>(), 0);
    }

    public CreativityJson getTestCreativityJson() {
        return new CreativityJson(false, new HashSet<>(), 0);
    }

    public RequirementCreditJson getTestRequirementCreditJson() {
        return new RequirementCreditJson(130, 65, 39, 21);
    }

    public CurriculumCodesJson getTestCurriculumCodesJson() {
        Map<Category, Set<String>> codes = new HashMap<>();

        codes.put(전공필수,
                Set.of("CSE1101", "CSE1102", "CSE1103", "CSE2101", "CSE2112", "CSE4205")
        );

        codes.put(전공선택,
                Set.of(
                        "ICE4029", "IEN3204", "CSE1105", "CSE2103", "CSE2104", "CSE2105", "CSE2107",
                        "CSE3101", "CSE3201", "CSE3203", "CSE3206", "CSE3308", "CSE3309", "CSE4201",
                        "CSE4202", "CSE4204", "CSE4308", "CSE3102", "CSE3207", "CSE4302", "CSE4305",
                        "CSE4314", "CSE3202", "CSE3205", "CSE3302", "CSE3303", "CSE3304", "CSE3307",
                        "CSE4312", "CSE3204", "CSE4301", "CSE4303", "CSE4304", "CSE4307"
                ));

        codes.put(교양필수,
                Set.of(
                        "GEB1112", "GEB1114", "GEB1115", "GEB1124", "GEB1131", "GEB1107",
                        "GEB1201", "ACE1204", "ACE1312", "ACE2101",
                        "ACE2104", "ACE2106", "MTH1001", "MTH1002", "PHY1001", "PHY1002", "PHY1003", "PHY1004"
                ));

        return new CurriculumCodesJson(codes);
    }


}