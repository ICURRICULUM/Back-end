package icurriculum.domain.take;


import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@ToString
public class CustomCourse {

    @Column(name = "custom_code")
    private String code;

    @Column(name = "custom_name")
    private String name;

    @Column(name = "custom_credit")
    private Integer credit;

}