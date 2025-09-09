package com.duty.service.service;

import com.duty.service.notification.NotificationProducer;
import com.duty.service.request.DutyRequest;
import com.duty.service.request.DutyRequestForMultiJobs;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class DutyService {

    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduleJob;

    private final EmailService emailService;
    private final NotificationProducer producer;

    // Keep track of all scheduled jobs (DUTIES)
    private final Map<String, ScheduledFuture<?>> scheduledJobs = new ConcurrentHashMap<>();

    public void scheduleOneDutyJobOnly(DutyRequest dutyRequest) throws MessagingException, UnsupportedEncodingException {

        String cronExpression = String.format("%d %d %d %d %d ?",
                dutyRequest.getSecond(),
                dutyRequest.getMinute(),
                dutyRequest.getHour(),
                dutyRequest.getDay(),
                dutyRequest.getMonth()
                );

        System.out.println("Checking cronExpression: "+ cronExpression);

        if (scheduleJob != null && !scheduleJob.isCancelled()) {
            //return a DTO response that there's a job running, cancel it before scheduling another one
            scheduleJob.cancel(false);
        }

        scheduleJob = taskScheduler.schedule(() -> {
            try {
                emailService.sendDutyReminderEmail(dutyRequest);
            } catch (Exception e) {
                e.printStackTrace();
                // optionally log properly instead of print
            }
        }, new CronTrigger(cronExpression));

    }

    public void scheduleMultipleDutyJobs(DutyRequestForMultiJobs requestForMultiJobs) throws MessagingException, UnsupportedEncodingException {

        String cronExpression = String.format("%d %d %d %d %d ?",
                requestForMultiJobs.getSecond(),
                requestForMultiJobs.getMinute(),
                requestForMultiJobs.getHour(),
                requestForMultiJobs.getDay(),
                requestForMultiJobs.getMonth()
        );

        String jobsDutyName = requestForMultiJobs.getDutyName();

        if (scheduledJobs.containsKey(jobsDutyName)) {
            //I can return a message or throw an Exception (Tell the User that this Duty name already exist)
            return;
        }

        System.out.println("Checking cronExpression: "+ cronExpression);

        ScheduledFuture<?> future = taskScheduler.schedule(
                emailService.sendDutyReminderEmailForMultipleJobs(requestForMultiJobs),
                new CronTrigger(cronExpression)
        );

        scheduledJobs.put(jobsDutyName, future);
    }


    public void stopDuty() {
        if (scheduleJob != null) {
            scheduleJob.cancel(true);
            System.out.println("Duty stopped");
            scheduleJob = null;
        } else {
            System.out.println("No duty to stop.");
        }
    }


    public boolean isDutyRunning() {
        return scheduleJob != null && !scheduleJob.isCancelled();
    }

    public void cancelDutyReminderByDutyName(String dutyName) {

        if (!scheduledJobs.containsKey(dutyName)) {
            //I can return a message or throw an Exception (Tell the User that no such Duty name exist)
            return;
        }

        ScheduledFuture<?> future = scheduledJobs.remove(dutyName);
        if (future != null) {
            future.cancel(true);
        }
    }

    public boolean isDutyWithNameRunning(String dutyName) {

        if (!scheduledJobs.containsKey(dutyName)) {
            //I can return a message or throw an Exception (Tell the User that no such Duty name exist)
            return false;
        }

        ScheduledFuture<?> future = scheduledJobs.get(dutyName);
        return future != null && !future.isCancelled();
    }
}
