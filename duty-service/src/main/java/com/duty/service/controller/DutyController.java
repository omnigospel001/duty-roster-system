package com.duty.service.controller;

import com.duty.service.request.DutyRequest;
import com.duty.service.request.DutyRequestForMultiJobs;
import com.duty.service.service.DutyService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/duty")
@RequiredArgsConstructor
public class DutyController {

    private final DutyService dutyService;

    @PostMapping("/schedule")
    public void scheduleOneDuty(@RequestBody @Valid DutyRequest dutyRequest) throws MessagingException, UnsupportedEncodingException {
        dutyService.scheduleOneDutyJobOnly(dutyRequest);
    }

    @PostMapping("/schedule/multiple")
    public void scheduleMultipleDuties(@RequestBody @Valid DutyRequestForMultiJobs requestForMultiJobs) throws MessagingException, UnsupportedEncodingException {
        dutyService.scheduleMultipleDutyJobs(requestForMultiJobs);
    }

    @GetMapping("/stop")
    public ResponseEntity<String> stopDuty() {
        dutyService.stopDuty();
        return ResponseEntity.ok().body("Duty is stopped.");
    }

    @GetMapping("/status")
    public ResponseEntity<String> isDutyRunning() {
        return ResponseEntity.ok().body(dutyService.isDutyRunning() ? "Duty is active." : "No active duty");
    }

    @GetMapping("/cancel/name/{dutyName}")
    public ResponseEntity<String> cancelDutyReminderByDutyName(@PathVariable String dutyName) {
        dutyService.cancelDutyReminderByDutyName(dutyName);
        return ResponseEntity.ok().body("Duty is stopped.");
    }

    @GetMapping("/status/name/{dutyName}")
    public ResponseEntity<String> isDutyWithNameRunning(@PathVariable String dutyName) {
        return ResponseEntity.ok().body(dutyService.isDutyWithNameRunning(dutyName) ? "Duty is active." : "No active duty");
    }
}
