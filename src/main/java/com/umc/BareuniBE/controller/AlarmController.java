package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.enums.AlarmType;
import com.umc.BareuniBE.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
@RestController
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;

    // 클라이언트에서 구독을 하기 위한 subcribe 메서드
    @GetMapping(value = "/subscribe/{communityIdx}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @PathVariable Long communityIdx
    ) {
        return alarmService.subscribe(communityIdx);
    }

    // 임시로 서버에서 클라이언트로 알림을 주기 위한 sendData 메서드
    @PostMapping("/send-data/{communityIdx}")
    public void sendData(
            AlarmType alarm_type,
            @PathVariable Long communityIdx,
            String content,
            Long userIdx
    ) throws BaseException {
        createAlarm(alarm_type, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Seoul"))), content, communityIdx, userIdx);

        alarmService.notify(communityIdx, "data");
    }

    // 알림 내역 저장
    @PostMapping("/create")
    public void createAlarm(
        AlarmType alarm_type,
        Timestamp created_at,
        String content,
        Long communityIdx,
        Long userIdx
    ) throws BaseException {
        alarmService.createAlarm(alarm_type, created_at, content, communityIdx, userIdx);
    }
}
