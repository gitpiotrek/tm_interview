package com.tm.tmInterview.models;

import lombok.Data;

import java.util.List;

@Data
public class DetailReportRecord {

    private ReportRecord matchWord;

    private List<ReportRecord> regexMatches;
}
