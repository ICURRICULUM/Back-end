package icurriculum.domain.take.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TakeRequest {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class TakeCreateDTO{
        String code;
        String name;
        String category;
        String majorType;
        Double grade;
        Integer credit;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class TakeUpdateDTO{
        Long takeId;
        String code;
        String name;
        String category;
        String majorType;
        Integer credit;
        Double grade;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class TakeCreateListDTO{
        List<TakeCreateDTO> takeCreateDTOList;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class TakeDeleteDTO{
        Long takeId;
    }
}
