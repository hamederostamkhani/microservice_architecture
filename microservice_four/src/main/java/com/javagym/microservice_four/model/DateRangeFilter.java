package com.javagym.microservice_four.model;

import lombok.Data;

@Data
public class DateRangeFilter {
    private String fromDate;
    private String toDate;

    public String filterDate() {
        if (this.fromDate != null) {
            if (this.toDate != null)
                return "earliest=\"" + this.fromDate + "\" latest=\"" + this.toDate + "\" ";
            else
                return "earliest=\"" + this.fromDate + "\" ";
        } else {
            if (this.toDate != null)
                return "latest=\"" + this.toDate + "\" ";
            else
                return "";
        }
    }
}
