package com.ssafy.ain.idealperson.controller;

import com.ssafy.ain.global.dto.ApiResponse;
import com.ssafy.ain.global.dto.UserPrincipal;
import com.ssafy.ain.idealperson.dto.IdealPersonDTO.*;
import com.ssafy.ain.idealperson.service.IdealPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.ssafy.ain.global.constant.SuccessCode;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://myain.co.kr"})
@RequestMapping("/ideal-people")
public class IdealPersonController {

    private final IdealPersonService idealPersonService;

    @GetMapping("")
    public ApiResponse<?> getAllIdealPerson(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ApiResponse.success(SuccessCode.GET_IDEAL_PEOPLE,
                idealPersonService.getAllIdealPerson(userPrincipal.getUserInfoDTO().getMemberId()));
    }

    @PatchMapping("/ranks")
    public ApiResponse<?> modifyRankingOfIdealPeople(@AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ModifyRankingOfIdealPeopleRequest modifyRankingOfIdealPeopleRequest) {
        idealPersonService.modifyRankingOfIdealPeople(
                userPrincipal.getUserInfoDTO().getMemberId(),
                modifyRankingOfIdealPeopleRequest);
        return ApiResponse.success(SuccessCode.MODIFY_RANKING_OF_IDEAL_PEOPLE);
    }

    @PostMapping("/names")
    public ApiResponse<?> getNameOfIdealPerson(@RequestBody GetNameOfIdealPersonRequest getNameOfIdealPersonRequest) {
        return ApiResponse.success(SuccessCode.GET_NAME_OF_IDEAL_PERSON,
                idealPersonService.getNameOfIdealPerson(getNameOfIdealPersonRequest));
    }

    @PostMapping("")
    public ApiResponse<?> addIdealPerson(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @RequestBody AddIdealPersonRequest addIdealPersonRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://myain.co.kr/fast/chatbots/ideal-people";
        GetIdealPersonThreadIdResponse responseBody = restTemplate.postForObject(
                url, null, GetIdealPersonThreadIdResponse.class);

        // responseBody nullException 처리 필요
        idealPersonService.addIdealPerson(
                userPrincipal.getUserInfoDTO().getMemberId(),
                responseBody.getIdealPersonThreadId(),
                addIdealPersonRequest);
        return ApiResponse.success(SuccessCode.ADD_IDEAL_PERSON);
    }
}