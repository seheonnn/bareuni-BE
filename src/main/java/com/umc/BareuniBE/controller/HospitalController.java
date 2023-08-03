package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.HospitalReq;
import com.umc.BareuniBE.dto.HospitalRes;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    // 홈 - 후기가 좋은 치과 목록 조회
    @GetMapping("/best")
    public BaseResponse<List<HospitalRes.HospitalListRes>> getBestHospitalList() throws BaseException {
        return new BaseResponse<>(hospitalService.getBestHospitalList());
    }

    // 치과정보 탭 - 추천 치과 목록 조회
    @GetMapping("/recommend/{area}")
    public BaseResponse<List<HospitalRes.HospitalListRes>> getRecommendHospitalList(
            @PathVariable("area") String areaStr
    ) throws BaseException {
        String[] areaList = areaStr.split(",");

        return new BaseResponse<>(hospitalService.getRecommendHospitalList(areaList));
    }

    // 스크랩 추가
    @PostMapping("/{hospitalIdx}/scrap")
    public BaseResponse<HospitalRes.HospitalScrapCreateRes> createScrap (
            @PathVariable Long hospitalIdx,
            @RequestBody HospitalReq.HospitalScrapCreateReq request
    ) throws BaseException {
        return new BaseResponse<>(hospitalService.createScrap(request, hospitalIdx));
    }
}
