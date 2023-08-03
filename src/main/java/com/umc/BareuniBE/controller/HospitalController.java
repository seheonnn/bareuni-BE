package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.HospitalReq;
import com.umc.BareuniBE.dto.HospitalRes;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.service.HospitalService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    // 홈 - 후기가 좋은 치과 목록 조회
    @ApiOperation(value = "홈 - 후기가 좋은 치과 목록 조회", notes = "ex) http://localhost:8080/hospital/best\n\n")
    @GetMapping("/best")
    public BaseResponse<List<HospitalRes.HospitalSummaryListRes>> getBestHospitalList() throws BaseException {
        return new BaseResponse<>(hospitalService.getBestHospitalList());
    }

    // 치과정보 탭 - 추천 치과 목록 조회
    @ApiOperation(value = "치과정보 탭 - 추천 치과 목록 조회", notes = "ex) http://localhost:8080/hospital/recommend/서림동,청담동")
    @GetMapping("/recommend/{area}")
    public BaseResponse<List<HospitalRes.HospitalSummaryListRes>> getRecommendHospitalList(
            @PathVariable("area") String areaStr
    ) throws BaseException {
        String[] areaList = areaStr.split(",");

        return new BaseResponse<>(hospitalService.getRecommendHospitalList(areaList));
    }

    // 스크랩 추가
    @ApiOperation(value = "스크랩 추가", notes = "ex) http://localhost:8080/hospital/1/scrap\n\n" +
            "{\n\n" +
            "\"userIdx\":1\n\n" +
            "}")
    @PostMapping("/{hospitalIdx}/scrap")
    public BaseResponse<HospitalRes.HospitalScrapCreateRes> createScrap (
            @PathVariable Long hospitalIdx,
            @RequestBody HospitalReq.HospitalScrapCreateReq request
    ) throws BaseException {
        return new BaseResponse<>(hospitalService.createScrap(request, hospitalIdx));
    }
}
