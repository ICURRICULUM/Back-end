package icurriculum.domain.take.controller;


import icurriculum.domain.member.Member;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.dto.TakeConverter;
import icurriculum.domain.take.dto.TakeRequest;
import icurriculum.domain.take.dto.TakeResponse.TakeListDTO;
import icurriculum.domain.take.service.TakeService;
import icurriculum.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/take")
public class TakeController {

    private final TakeService takeService;

    @GetMapping("/")
    public ApiResponse<TakeListDTO> getTake(Member member) {

        List<Take> takeList = takeService.getTakeListByMember(member);
        TakeListDTO takes = TakeConverter.toTakeList(takeList);

        return ApiResponse.onSuccess(takes);
    }


    @PostMapping("/create")
    public ApiResponse<TakeListDTO> createTake(
        Member member,
        @RequestBody TakeRequest.TakeCreateListDTO takeCreateListDTO) {

        TakeListDTO takes = takeService.createTakeListByMember(member,
            takeCreateListDTO);

        return ApiResponse.onSuccess(takes);
    }


    @PostMapping("/update")
    public ApiResponse<TakeListDTO> updateTake(
        Member member,
        @RequestBody TakeRequest.TakeUpdateDTO takeUpdateDTO) {

        TakeListDTO takes = takeService.upadateTakeByMember(member, takeUpdateDTO);

        return ApiResponse.onSuccess(takes);
    }

    @PostMapping("/delete")
    public ApiResponse<TakeListDTO> deleteTake(
        Member member,
        @RequestBody TakeRequest.TakeDeleteDTO takeDeleteDTO) {

        TakeListDTO takes = takeService.deleteTakeByMember(member, takeDeleteDTO);

        return ApiResponse.onSuccess(takes);
    }
}
