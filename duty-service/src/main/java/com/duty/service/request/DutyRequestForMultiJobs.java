package com.duty.service.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DutyRequestForMultiJobs {

    @NotBlank(message = "Duty cannot be blank")
    private String duty;

    @NotBlank(message = "Duty is required")
    private String dutyName;

    private int second;
    private int minute;
    private int hour;

    private int day;
    private int month;

    //
    @NotNull(message = "userId is required")
    private Integer userId;
}
