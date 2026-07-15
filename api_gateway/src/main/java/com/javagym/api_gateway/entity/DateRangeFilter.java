package com.javagym.api_gateway.entity;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class DateRangeFilter {
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/(0[1-9]|[12][0-9]|3[01])\\/\\d{4}:([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
            message = "From Date Format Is : MM/DD/YYYY:hh:mm:ss")
    private String fromDate;
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/(0[1-9]|[12][0-9]|3[01])\\/\\d{4}:([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
            message = "To Date Format Is : MM/DD/YYYY:hh:mm:ss")
    private String toDate;
}