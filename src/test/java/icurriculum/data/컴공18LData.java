package icurriculum.data;

import static icurriculum.domain.department.DepartmentName.컴퓨터공학과;
import static icurriculum.domain.member.RoleType.ROLE_USER;
import static icurriculum.domain.membermajor.MajorType.주전공;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class 컴공18LData {

    private static final String MEMBER_NAME = "홍길동";
    private static final Integer JOIN_YEAR = 18;
    private static final RoleType ROLE = ROLE_USER;

    private static final Long testMemberId = 1L;

    private MemberRepository memberRepository;
    private DepartmentRepository departmentRepository;
    private MemberMajorRepository memberMajorRepository;

    private CourseRepository courseRepository;
    private TakeRepository takeRepository;
    private CurriculumRepository curriculumRepository;

    public 컴공18LData(MemberRepository memberRepository,
        DepartmentRepository departmentRepository, MemberMajorRepository memberMajorRepository,
        CourseRepository courseRepository, TakeRepository takeRepository,
        CurriculumRepository curriculumRepository) {
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
        this.memberMajorRepository = memberMajorRepository;
        this.courseRepository = courseRepository;
        this.takeRepository = takeRepository;
        this.curriculumRepository = curriculumRepository;
    }

    /*
     * 기본 데이터 추가 method
     */
    @PostConstruct
    public void init() {
        Department department = getDepartmentData();
        departmentRepository.save(department);

        Member member = getMemberData();
        memberRepository.save(member);

        MemberMajor memberMajor = MemberMajor.builder()
            .majorType(주전공)
            .department(department)
            .member(member)
            .build();
        memberMajorRepository.save(memberMajor);

        List<Course> courses = getCoursesData();
        courseRepository.saveAll(courses);

        List<Take> takes = getTakesData(member);
        takeRepository.saveAll(takes);

        Curriculum curriculum = getCurriculumData(memberMajor);
        curriculumRepository.save(curriculum);
    }

    public Department getDepartmentData() {
        return Department.builder()
            .name(컴퓨터공학과)
            .build();
    }

    public Member getMemberData() {
        return Member.builder()
            .name("홍길동")
            .joinYear(JOIN_YEAR)
            .role(ROLE_USER)
            .build();
    }

    public List<Course> getCoursesData() {
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

            // 추가 데이터
            Course.builder().code("GEG1123").name("기초물리학").credit(3).build(),
            Course.builder().code("MTH1010").name("기초수학").credit(3).build(),

            Course.builder().code("GEC1027").name("축제와 인간사회").credit(3).build(),

            Course.builder().code("GED4009").name("동북아와 한일관계").credit(3).build(),

            Course.builder().code("GEE3029").name("고급대학영어").credit(3).build(),

            Course.builder().code("GED3018").name("그래픽 디자인 이야기").credit(3).build(),

            Course.builder().code("GEE5017").name("품질의 차원").credit(3).build(),

            Course.builder().code("GED6005").name("지능정보서비스와 소프트웨어").credit(3).build(),
            Course.builder().code("GEE4011").name("세상을 바꾸는 스타트업 이야기").credit(3).build(),

            Course.builder().code("GEC2001").name("부와 빈곤의 글로벌 지도").credit(3).build(),
            Course.builder().code("GEG2033").name("대학기초영어").credit(3).build(),
            Course.builder().code("GED3002").name("영화로보는문학세계").credit(3).build(),
            Course.builder().code("GEE6008").name("스키").credit(1).build(),

            Course.builder().code("GED1005").name("인간-AI-기계").credit(3).build(),
            Course.builder().code("GEE2024").name("미디어와 콘텐츠").credit(3).build(),
            Course.builder().code("GEE3027").name("실용영어 L/S").credit(3).build(),
            Course.builder().code("GED5001").name("환경과 인간").credit(3).build(),
            Course.builder().code("GED5007").name("첨단 바이오테크놀로지의 이해").credit(3).build(),

            Course.builder().code("CHM1021").name("화학 1").credit(3).build(),
            Course.builder().code("GEC2004").name("국제관계학개론").credit(3).build(),

            Course.builder().code("ACE2106").name("정수론입문").credit(3).build(),
            Course.builder().code("GEC4011").name("희곡의 이해").credit(3).build(),
            Course.builder().code("GEE4007").name("발명과 특허").credit(2).build(),
            Course.builder().code("GED1009").name("마음을 움직이는 리더쉽").credit(3).build(),
            Course.builder().code("GED5002").name("생활 속 바이오이야기").credit(3).build()
        );
    }

    public List<Take> getTakesData(Member member) {
        return Arrays.asList(
            // 1학년 1학기
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("GEB1115").get())
                .member(member).build(), // 프로네시스 세미나 I: 가치형성과 진로탐색
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("GEB1124").get())
                .member(member).build(), // 이공계열 글쓰기와 토론
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("GEB1131").get())
                .member(member).build(), // 생활한문
            Take.builder().category(Category.교양선택).majorType(주전공)
                .course(courseRepository.findByCode("GEG1123").get())
                .member(member).build(), // 기초물리학
            Take.builder().category(Category.교양선택).majorType(주전공)
                .course(courseRepository.findByCode("MTH1010").get())
                .member(member).build(), // 기초수학

            // 1학년 2학기
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("ACE1204").get())
                .member(member).build(), // 생명과학
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("GEB1108").get())
                .member(member).build(), // 의사소통 영어: 중급
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("GEB1112").get())
                .member(member).build(), // 크로스오버 1: 인간의 탐색
            Take.builder().category(Category.핵심교양2).majorType(주전공)
                .course(courseRepository.findByCode("GEC2004").get())
                .member(member).build(), // 국제관계학개론
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("PHY1001").get())
                .member(member).build(), // 물리학 1

            // 2학년 1학기
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("ACE1312").get())
                .member(member).build(), // 이산수학
            Take.builder().category(Category.교양선택).majorType(주전공)
                .course(courseRepository.findByCode("ACE2106").get())
                .member(member).build(), // 정수론입문
            Take.builder().category(Category.전공필수).majorType(주전공)
                .course(courseRepository.findByCode("CSE1102").get())
                .member(member).build(), // 컴퓨터공학입문 및 실습
            Take.builder().category(Category.전공선택).majorType(주전공)
                .course(courseRepository.findByCode("CSE2104").get())
                .member(member).build(), // 인터넷 프로그래밍
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("PHY1002").get())
                .member(member).build(), // 물리학 2
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("PHY1003").get())
                .member(member).build(), // 물리학실험 1

            // 2학년 2학기
            Take.builder().category(Category.전공필수).majorType(주전공)
                .course(courseRepository.findByCode("CSE2101").get())
                .member(member).build(), // 논리회로
            Take.builder().category(Category.전공선택).majorType(주전공)
                .course(courseRepository.findByCode("CSE2105").get())
                .member(member).build(), // 컴퓨터기반선형대수
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("GEB1114").get())
                .member(member).build(), // 크로스오버 3: 사회의 탐색
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("GEB1203").get())
                .member(member).build(), // 고급대학영어
            Take.builder().category(Category.핵심교양4).majorType(주전공)
                .course(courseRepository.findByCode("GEC4011").get())
                .member(member).build(), // 희곡의 이해
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("MTH1001").get())
                .member(member).build(), // 일반수학 1 (재수강)
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("PHY1004").get())
                .member(member).build(), // 물리학실험 2

            // 2학년 동계학기
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("MTH1002").get())
                .member(member).build(), // 일반수학 2

            // 3학년 1학기
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("ACE2104").get())
                .member(member).build(), // 통계학
            Take.builder().category(Category.전공필수).majorType(주전공)
                .course(courseRepository.findByCode("CSE1101").get())
                .member(member).build(), // 객체지향프로그래밍 1

            Take.builder().category(Category.교양선택).majorType(주전공)
                .course(courseRepository.findByCode("CHM1021").get())
                .member(member).build(), // 화학 1

            Take.builder().category(Category.전공선택).majorType(주전공)
                .course(courseRepository.findByCode("CSE2107").get())
                .member(member).build(), // 자바
            Take.builder().category(Category.전공필수).majorType(주전공)
                .course(courseRepository.findByCode("CSE2112").get())
                .member(member).build(), // 자료구조

            Take.builder().category(Category.전공필수).majorType(주전공)
                .course(courseRepository.findByCode("CSE1103").get())
                .member(member).build(), // 객체지향 2

            // 3학년 2학기
            Take.builder().category(Category.전공선택).majorType(주전공)
                .course(courseRepository.findByCode("CSE3203").get())
                .member(member).build(), // 컴퓨터구조론
            Take.builder().category(Category.전공선택).majorType(주전공)
                .course(courseRepository.findByCode("CSE3206").get())
                .member(member).build(), // 오퍼레이팅시스템
            Take.builder().category(Category.전공선택).majorType(주전공)
                .course(courseRepository.findByCode("CSE3207").get())
                .member(member).build(), // 데이터베이스
            Take.builder().category(Category.전공선택).majorType(주전공)
                .course(courseRepository.findByCode("CSE3212").get())
                .member(member).build(), // 컴퓨터 네트워크
            Take.builder().category(Category.전공선택).majorType(주전공)
                .course(courseRepository.findByCode("CSE3308").get())
                .member(member).build(), // 시스템분석
            Take.builder().category(Category.교양선택).majorType(주전공)
                .course(courseRepository.findByCode("GEE4007").get())
                .member(member).build(), // 발명과 특허

            Take.builder().category(Category.전공선택).majorType(주전공)
                .course(courseRepository.findByCode("CSE3309").get())
                .member(member).build(), // 문제해결기법
            Take.builder().category(Category.전공선택).majorType(주전공)
                .course(courseRepository.findByCode("CSE3313").get())
                .member(member).build(), // 리눅스
            Take.builder().category(Category.전공선택).majorType(주전공)
                .course(courseRepository.findByCode("CSE4204").get())
                .member(member).build(), // 알고리즘
            Take.builder().category(Category.핵심교양1).majorType(주전공)
                .course(courseRepository.findByCode("GED1009").get())
                .member(member).build(), // 마움리
            Take.builder().category(Category.핵심교양5).majorType(주전공)
                .course(courseRepository.findByCode("GED5002").get())
                .member(member).build(), // 생바오

            // 4학년 1학기
            Take.builder().category(Category.전공선택).majorType(주전공)
                .customCourse(
                    CustomCourse.builder()
                        .name("IPP 현장실습")
                        .code("CUSTOM")
                        .credit(12)
                        .build()
                )
                .member(member).build(), // IPP

            // 4학년 2학기
            Take.builder().category(Category.교양필수).majorType(주전공)
                .course(courseRepository.findByCode("ACE2101").get())
                .member(member).build(), // 공업수학1
            Take.builder().category(Category.전공필수).majorType(주전공)
                .course(courseRepository.findByCode("CSE4205").get())
                .member(member).build() // 종설
        );
    }


    public Curriculum getCurriculumData(MemberMajor memberMajor) {
        CurriculumDecider decider = CurriculumDecider.builder()
            .majorType(memberMajor.getMajorType())
            .departmentName(memberMajor.getDepartment().getName())
            .joinYear(memberMajor.getMember().getJoinYear())
            .build();

        return Curriculum.builder()
            .decider(decider)
            .core(getCoreJsonData())
            .swAi(getSwAiJsonData())
            .creativity(getCreativityJsonData())
            .requiredCredit(getRequirementCreditJsonData())
            .majorRequired(getTestMajorRequiredData())
            .majorSelect(getTestMajorSelectData())
            .generalRequired(getTestGeneralRequiredData())
            .alternativeCourse(getAlternativeCourseJsonData())
            .build();
    }

    public Core getCoreJsonData() {
        return Core.builder()
            .isAreaFixed(false)
            .requiredCredit(9)
            .requiredAreaSet(Set.of())
            .build();
    }

    public SwAi getSwAiJsonData() {
        return SwAi.builder()
            .requiredCredit(0)
            .approvedCodeSet(Set.of())
            .build();
    }

    public Creativity getCreativityJsonData() {
        return Creativity.builder()
            .requiredCredit(0)
            .approvedCodeSet(Set.of())
            .build();
    }

    public RequiredCredit getRequirementCreditJsonData() {
        return RequiredCredit.builder()
            .totalNeedCredit(130)
            .singleNeedCredit(65)
            .secondNeedCredit(39)
            .minorNeedCredit(21)
            .build();
    }

    public MajorRequired getTestMajorRequiredData() {
        return MajorRequired.builder()
            .codeSet(
                Set.of("CSE1101", "CSE1102", "CSE1103", "CSE2101", "CSE2112", "CSE4205")
            )
            .build();
    }

    public MajorSelect getTestMajorSelectData() {
        return MajorSelect.builder()
            .codeSet(
                Set.of(
                    "ICE4029", "IEN3204", "CSE1105", "CSE2103", "CSE2104", "CSE2105", "CSE2107",
                    "CSE3101", "CSE3201", "CSE3203", "CSE3206", "CSE3308", "CSE3309", "CSE4201",
                    "CSE4202", "CSE4204", "CSE4308", "CSE3102", "CSE3207", "CSE4302", "CSE4305",
                    "CSE4314", "CSE3202", "CSE3205", "CSE3302", "CSE3303", "CSE3304", "CSE3307",
                    "CSE4312", "CSE3204", "CSE4301", "CSE4303", "CSE4304", "CSE4307",

                    // 추가
                    "CSE2113", "CSE3209", "CSE3313"

                    /*
                     * [컴퓨터공학과 19 커리큘럼을 크롤러로 가져오면 존재하지 않는 것으로 확인되는 과목]
                     * {code=CSE3313, name=리눅스 프로그래밍, credit=3}
                     * {code=CSE2113, name=오픈소스SW개론, credit=3}
                     * {code=CSE3209, name=시스템 프로그래밍, credit=3}
                     */
                )
            )
            .build();
    }

    public GeneralRequired getTestGeneralRequiredData() {
        return GeneralRequired.builder()
            .codeSet(
                Set.of(
                    "GEB1112", "GEB1114", "GEB1115", "GEB1124", "GEB1131", "GEB1107", "GEB1108",
                    "GEB1109",
                    "GEB1201", "GEB1202", "GEB1203", "ACE1204", "ACE1312", "ACE2101",
                    "ACE2104", "MTH1001", "MTH1002", "PHY1001", "PHY1002", "PHY1003",
                    "PHY1004"

                    // 정수론 제외시킴
                    //,"ACE2106"
                )
            )
            .build();
    }

    public AlternativeCourse getAlternativeCourseJsonData() {
        Map<String, Set<String>> alternativeCourseMap = new HashMap<>();
        alternativeCourseMap.put("CSE2013", Set.of("CSE3209"));
        alternativeCourseMap.put("CSE3209", Set.of("CSE2013"));

        // 고대영
        alternativeCourseMap.put("GEE3029", Set.of("GEB1203"));

        // 실용영어 L/S
        alternativeCourseMap.put("GEE3027", Set.of("GEB1201"));

        // 컴퓨터 네트워크 학수번호가 바뀜
        alternativeCourseMap.put("CSE3212", Set.of("CSE4202"));

        // 컴퓨터공학입문 -> 컴퓨터공학입문 및 실습대체 가능
        alternativeCourseMap.put("CSE1112", Set.of("CSE1102"));

        return AlternativeCourse.builder()
            .alternativeCourseCodeMap(alternativeCourseMap)
            .build();
    }


}
