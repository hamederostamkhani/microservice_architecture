package com.javagym.microservice_four.repository;


import com.javagym.microservice_four.model.DateRangeFilter;

public class SplunkQuery {

    public static String getFlowInfo(DateRangeFilter rangeFilter) {
        return "search index=\"correlate_index\" " + rangeFilter.filterDate() + " | tojson flowInfo";
    }
}
