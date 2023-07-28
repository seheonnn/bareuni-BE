package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.HospitalReq;
import com.umc.BareuniBE.dto.HospitalRes;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    // 스크랩 추가
    @PostMapping("/{hospitalIdx}/scrap")
    public BaseResponse<HospitalRes.HospitalScrapCreateRes> createScrap(
            @PathVariable Long hospitalIdx,
            @RequestBody HospitalReq.HospitalScrapCreateReq request
    ) throws BaseException {
        return new BaseResponse<>(hospitalService.createScrap(request, hospitalIdx));
    }
}
