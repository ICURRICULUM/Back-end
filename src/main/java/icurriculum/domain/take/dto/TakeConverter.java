package icurriculum.domain.take.dto;

import icurriculum.domain.course.Course;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.CustomCourse;
import icurriculum.domain.take.Take;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TakeConverter {

    public static TakeResponse.TakeListDTO toTakeList(List<Take> takes) {
        List<TakeResponse.TakeDTO> takeListDTOs = takes.stream()
            .map(take -> {
                Course effectiveCourse = take.getEffectiveCourse();
                return TakeResponse.TakeDTO.builder()
                    .takeId(take.getId())
                    .name(effectiveCourse.getName())
                    .credit(effectiveCourse.getCredit())
                    .code(effectiveCourse.getCode())
                    .category(take.getCategory().toString())
                    .majorType(take.getMajorType().toString())
                    .build();
            })
            .collect(Collectors.toList());

        return TakeResponse.TakeListDTO.builder()
            .takeList(takeListDTOs)
            .build();
    }

    public static Take toCustomTakeEntity(Member member,TakeRequest.TakeCreateDTO takeCreateDTO){
        return Take.builder()
            .category(Category.valueOf(takeCreateDTO.getCategory()))
            .majorType(MajorType.valueOf(takeCreateDTO.getMajorType()))
            .member(member)
            .course(null)
            .customCourse(CustomCourse.builder()
                .code(takeCreateDTO.getCode())
                .name(takeCreateDTO.getName())
                .credit(takeCreateDTO.getCredit())
                .build())
            .build();
    }

    public static Take toTakeEntity(Member member,TakeRequest.TakeCreateDTO takeCreateDTO){
        return Take.builder()
            .category(Category.valueOf(takeCreateDTO.getCategory()))
            .majorType(MajorType.valueOf(takeCreateDTO.getMajorType()))
            .member(member)
            .course(Course.builder()
                .code(takeCreateDTO.getCode())
                .name(takeCreateDTO.getName())
                .credit(takeCreateDTO.getCredit())
                .build()
            )
            .customCourse(null)
            .build();
    }





}
