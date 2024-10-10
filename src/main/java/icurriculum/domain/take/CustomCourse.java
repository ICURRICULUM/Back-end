package icurriculum.domain.take;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class CustomCourse {

    @Column(name = "custom_code")
    private final String code = "CUSTOM";

    @Column(name = "custom_name")
    private String name;

    @Column(name = "custom_credit")
    private Integer credit;

    @Builder
    private CustomCourse(String name, Integer credit) {
        this.name = name;
        this.credit = credit;
    }
}