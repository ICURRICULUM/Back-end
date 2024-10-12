package icurriculum.domain.take.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public abstract class TakeResponse {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TakeDTO{
        private long takeId;
        private String code;
        private String name;
        private String category;
        private int credit;
        private String majorType;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TakeListDTO {
        private List<TakeDTO> takeList;
    }

}
