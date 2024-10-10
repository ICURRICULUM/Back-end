package icurriculum.data;

import static icurriculum.domain.department.DepartmentName.컴퓨터공학과;
import static icurriculum.domain.member.RoleType.ROLE_USER;
import static icurriculum.domain.membermajor.MajorType.주전공;
import static icurriculum.domain.take.Category.교양선택;
import static icurriculum.domain.take.Category.교양필수;
import static icurriculum.domain.take.Category.전공선택;
import static icurriculum.domain.take.Category.전공필수;
import static icurriculum.domain.take.Category.핵심교양1;
import static icurriculum.domain.take.Category.핵심교양3;
import static icurriculum.domain.take.Category.핵심교양4;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.repository.CourseRepository;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.Core;
import icurriculum.domain.curriculum.data.Creativity;
import icurriculum.domain.curriculum.data.GeneralRequired;
import icurriculum.domain.curriculum.data.MajorRequired;
import icurriculum.domain.curriculum.data.MajorSelect;
import icurriculum.domain.curriculum.data.RequiredCredit;
import icurriculum.domain.curriculum.data.SwAi;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.domain.department.Department;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.department.repository.DepartmentRepository;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.RoleType;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
import icurriculum.domain.take.CustomCourse;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.repository.TakeRepository;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestDataInitializer {

    public static final String MEMBER_NAME = "홍길동";
    public static final Integer JOIN_YEAR = 19;
    public static final RoleType ROLE = ROLE_USER;

    public static final DepartmentName DEPARTMENT_NAME_컴공 = 컴퓨터공학과;

    public static final MajorType MAIN_MAJOR_TYPE = 주전공;

    private Member member;
    private List<Course> courseList;

    private Department department;

    private List<Take> takeList;

    private List<MemberMajor> memberMajorList;

    private Curriculum curriculum;


    private MemberRepository memberRepository;
    private CourseRepository courseRepository;

    private DepartmentRepository departmentRepository;

    private TakeRepository takeRepository;

    private MemberMajorRepository memberMajorRepository;

    private CurriculumRepository curriculumRepository;

    public TestDataInitializer() {
    }

    public TestDataInitializer(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public TestDataInitializer(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public TestDataInitializer(CurriculumRepository curriculumRepository) {
        this.curriculumRepository = curriculumRepository;
    }

    public TestDataInitializer(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public TestDataInitializer(
        MemberRepository memberRepository,
        CourseRepository courseRepository,
        TakeRepository takeRepository
    ) {
        this.memberRepository = memberRepository;
        this.courseRepository = courseRepository;
        this.takeRepository = takeRepository;
    }

    public TestDataInitializer(
        MemberRepository memberRepository,
        DepartmentRepository departmentRepository,
        MemberMajorRepository memberMajorRepository
    ) {
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
        this.memberMajorRepository = memberMajorRepository;
    }


    public void init() {
        // Test member 데이터 추가
        member = getMemberData();
        memberRepository.save(member);

        // Test Course 데이터 추가
        courseList = getCourseDataList();
        courseRepository.saveAll(courseList);

        // Test Curriculum 데이터 추가
        curriculumRepository.save(getCurriculumData());

        // Test Take 데이터 추가
        takeList = getTakeDataList();
        takeRepository.saveAll(takeList);


    }

    public Member initMemberData() {
        // Test member 데이터 추가
        member = getMemberData();
        memberRepository.save(member);
        return member;
    }

    public List<Course> initCourseData() {
        // Test Course 데이터 추가
        courseList = getCourseDataList();
        courseRepository.saveAll(courseList);
        return courseList;
    }

    public Department initDepartmentData() {
        // Test Department 데이터 추가
        department = getDepartmentData();
        departmentRepository.save(department);
        return department;
    }

    public List<Take> initTakeData() {
        // Test Take 데이터 추가
        takeList = getTakeDataList();
        takeRepository.saveAll(takeList);
        return takeList;
    }

    public List<MemberMajor> initMemberMajorData() {
        memberMajorList = getMemberMajorDataList();
        memberMajorRepository.saveAll(memberMajorList);
        return memberMajorList;
    }

    public Curriculum initCurriculumData() {
        curriculum = getCurriculumData();
        curriculumRepository.save(curriculum);
        return curriculum;
    }


    public Member getMemberData() {
        return Member.builder()
            .name(MEMBER_NAME)
            .joinYear(JOIN_YEAR)
            .role(ROLE)
            .build();
    }

    public List<MemberMajor> getMemberMajorDataList() {
        return Arrays.asList(
            MemberMajor.builder().member(member).majorType(MajorType.주전공).department(department)
                .build()
        );
    }

    public Course getCourseData() {
        return Course.builder().code("GEB1112").name("크로스오버 1 : 인간의 탐색").credit(2).build();
    }

    public List<Course> getCourseDataList() {
        return Arrays.asList(
            Course.builder().code("GEB1112").name("크로스오버 1 : 인간의 탐색").credit(2).build(),
            Course.builder().code("GEB1114").name("크로스오버 3 : 사회의 탐색").credit(2).build(),
            Course.builder().code("GEB1115").name("프로네시스 세미나 Ⅰ : 가치형성과 진로탐색").credit(1).build(),
            Course.builder().code("GEB1124").name("이공계열 글쓰기와 토론").credit(3).build(),
            Course.builder().code("GEB1131").name("생활한문").credit(1).build(),
            Course.builder().code("GEB1107").name("의사소통 영어").credit(3).build(),
            Course.builder().code("GEB1108").name("의사소통 영어: 중급").credit(3).build(),
            Course.builder().code("GEB1109").name("의사소통 영어: 고급").credit(3).build(),
            Course.builder().code("GEB1201").name("실용영어 L/S").credit(3).build(),
            Course.builder().code("GEB1202").name("실용영어 R/W").credit(3).build(),
            Course.builder().code("GEB1203").name("고급대학영어").credit(3).build(),
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

    public List<Take> getTakeDataList() {
        return Arrays.asList(
            // 2019학년도 1학기
            Take.builder().category(전공필수).takenYear("19").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("CSE1101").get()).member(member).build(),
            Take.builder().category(교양필수).takenYear("19").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("GEB1114").get()).member(member).build(),
            Take.builder().category(교양필수).takenYear("19").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("GEB1115").get()).member(member).build(),
            Take.builder().category(교양필수).takenYear("19").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("GEB1124").get()).member(member).build(),
            Take.builder().category(교양필수).takenYear("19").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("GEB1131").get()).member(member).build(),
            Take.builder().category(교양선택).takenYear("19").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("GEG1123").get()).member(member).build(),
            Take.builder().category(교양선택).takenYear("19").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("MTH1010").get()).member(member).build(),

            // 2019학년도 2학기
            Take.builder().category(교양필수).takenYear("19").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("ACE1204").get()).member(member).build(),
            Take.builder().category(전공필수).takenYear("19").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("CSE1102").get()).member(member).build(),
            Take.builder().category(전공필수).takenYear("19").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("CSE1103").get()).member(member).build(),
            Take.builder().category(교양필수).takenYear("19").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("GEB1108").get()).member(member).build(),
            Take.builder().category(교양필수).takenYear("19").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("GEB1112").get()).member(member).build(),
            Take.builder().category(핵심교양1).takenYear("19").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("GEC1027").get()).member(member).build(),
            Take.builder().category(교양필수).takenYear("19").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("MTH1001").get()).member(member).build(),

            // 2021학년도 2학기
            Take.builder().category(핵심교양4).takenYear("19").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("GED4009").get()).member(member).build(),

            // 2021학년도 동계학기
            Take.builder().category(교양필수).takenYear("19").takenSemester("동계").majorType(주전공)
                .course(courseRepository.findByCode("MTH1002").get()).member(member).build(),
            Take.builder().category(교양필수).takenYear("19").takenSemester("동계").majorType(주전공)
                .course(courseRepository.findByCode("PHY1001").get()).member(member).build(),
            Take.builder().category(교양필수).takenYear("19").takenSemester("동계").majorType(주전공)
                .course(courseRepository.findByCode("PHY1002").get()).member(member).build(),

            // 2022학년도 1학기
            Take.builder().category(교양필수).takenYear("22").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("ACE2104").get()).member(member).build(),
            Take.builder().category(전공선택).takenYear("22").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("CSE2104").get()).member(member).build(),
            Take.builder().category(전공선택).takenYear("22").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("CSE2107").get()).member(member).build(),
            Take.builder().category(전공필수).takenYear("22").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("CSE2112").get()).member(member).build(),
            Take.builder().category(교양선택).takenYear("22").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("GEE3029").get()).member(member).build(),
            Take.builder().category(교양필수).takenYear("22").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("PHY1003").get()).member(member).build(),

            // 2022학년도 하계학기
            Take.builder().category(교양필수).takenYear("22").takenSemester("하계").majorType(주전공)
                .course(courseRepository.findByCode("ACE2101").get()).member(member).build(),

            // 2022학년도 2학기
            Take.builder().category(교양필수).takenYear("22").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("ACE1312").get()).member(member).build(),
            Take.builder().category(전공필수).takenYear("22").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("CSE2101").get()).member(member).build(),
            Take.builder().category(전공선택).takenYear("22").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("CSE2113").get()).member(member).build(),
            Take.builder().category(전공선택).takenYear("22").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("CSE3209").get()).member(member).build(),
            Take.builder().category(핵심교양3).takenYear("22").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("GED3018").get()).member(member).build(),
            Take.builder().category(교양필수).takenYear("22").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("PHY1004").get()).member(member).build(),

            // 2023학년도 1학기
            Take.builder().category(전공선택).takenYear("23").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("CSE3203").get()).member(member).build(),
            Take.builder().category(전공선택).takenYear("23").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("CSE3206").get()).member(member).build(),
            Take.builder().category(전공선택).takenYear("23").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("CSE3207").get()).member(member).build(),
            Take.builder().category(전공선택).takenYear("23").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("CSE4204").get()).member(member).build(),
            Take.builder().category(교양선택).takenYear("23").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("GED6005").get()).member(member).build(),
            Take.builder().category(교양선택).takenYear("23").takenSemester("1").majorType(주전공)
                .course(courseRepository.findByCode("GEE4011").get()).member(member).build(),

            // 2023학년도 2학기
            Take.builder().category(전공선택).takenYear("23").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("CSE3212").get()).member(member).build(),
            Take.builder().category(전공선택).takenYear("23").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("CSE3309").get()).member(member).build(),
            Take.builder().category(전공선택).takenYear("23").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("CSE3313").get()).member(member).build(),
            Take.builder().category(전공선택).takenYear("23").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("CSE4301").get()).member(member).build(),
            Take.builder().category(교양선택).takenYear("23").takenSemester("2").majorType(주전공)
                .course(courseRepository.findByCode("GEE5017").get()).member(member).build(),

            // 2023학년도 동계학기
            Take.builder().category(교양선택).takenYear("23").takenSemester("동계").majorType(주전공)
                .customCourse(
                    CustomCourse.builder().name("현장실습 6").credit(6).build()
                ).member(member).build(),

            // 2024학년도 1학기
            Take.builder().category(전공선택).takenYear("24").takenSemester("1").majorType(주전공)
                .customCourse(
                    CustomCourse.builder().name("현장실습 18").credit(18).build()
                ).member(member).build()
        );
    }

    public Department getDepartmentData() {
        return Department.builder()
            .name(DEPARTMENT_NAME_컴공)
            .build();
    }

    public Curriculum getCurriculumData() {
        CurriculumDecider decider = CurriculumDecider.builder()
            .majorType(MAIN_MAJOR_TYPE)
            .joinYear(JOIN_YEAR)
            .departmentName(DEPARTMENT_NAME_컴공)
            .build();

        return Curriculum.builder()
            .decider(decider)
            .core(getCoreData())
            .swAi(getSwAiData())
            .creativity(getCreativityData())
            .requiredCredit(getRequiredCreditData())
            .majorSelect(getMajorSelectData())
            .majorRequired(getMajorRequiredData())
            .generalRequired(getGeneralRequiredData())
            .alternativeCourse(getAlternativeCourseData())
            .build();
    }

    public Core getCoreData() {
        return Core.builder()
            .isAreaFixed(false)
            .requiredCredit(9)
            .build();
    }

    public SwAi getSwAiData() {
        return SwAi.builder()
            .requiredCredit(0)
            .build();
    }

    public Creativity getCreativityData() {
        return Creativity.builder()
            .requiredCredit(0)
            .build();
    }

    public RequiredCredit getRequiredCreditData() {
        return RequiredCredit.builder()
            .totalNeedCredit(130)
            .singleNeedCredit(65)
            .secondNeedCredit(39)
            .minorNeedCredit(21)
            .build();
    }

    public GeneralRequired getGeneralRequiredData() {
        return GeneralRequired.builder()
            .codeSet(
                Set.of(
                    "GEB1112", "GEB1114", "GEB1115", "GEB1124", "GEB1131", "GEB1107",
                    "GEB1201", "ACE1204", "ACE1312", "ACE2101",
                    "ACE2104", "ACE2106", "MTH1001", "MTH1002", "PHY1001", "PHY1002", "PHY1003",
                    "PHY1004"
                )
            )
            .build();
    }

    public MajorSelect getMajorSelectData() {
        return MajorSelect.builder()
            .codeSet(
                Set.of(
                    "ICE4029", "IEN3204", "CSE1105", "CSE2103", "CSE2104", "CSE2105", "CSE2107",
                    "CSE3101", "CSE3201", "CSE3203", "CSE3206", "CSE3308", "CSE3309", "CSE4201",
                    "CSE4202", "CSE4204", "CSE4308", "CSE3102", "CSE3207", "CSE4302", "CSE4305",
                    "CSE4314", "CSE3202", "CSE3205", "CSE3302", "CSE3303", "CSE3304", "CSE3307",
                    "CSE4312", "CSE3204", "CSE4301", "CSE4303", "CSE4304", "CSE4307"
                )
            )
            .build();
    }

    public MajorRequired getMajorRequiredData() {
        return MajorRequired.builder()
            .codeSet(
                Set.of("CSE1101", "CSE1102", "CSE1103", "CSE2101", "CSE2112", "CSE4205")
            )
            .build();
    }

    public AlternativeCourse getAlternativeCourseData() {
        Map<String, Set<String>> alternativeCourseMap = new HashMap<>();
        alternativeCourseMap.put("CSE2013", Set.of("CSE3209"));
        alternativeCourseMap.put("CSE3209", Set.of("CSE2013"));

        return AlternativeCourse.builder()
            .alternativeCourseCodeMap(alternativeCourseMap)
            .build();
    }

}
