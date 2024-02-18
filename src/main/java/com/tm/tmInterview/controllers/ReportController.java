package com.tm.tmInterview.controllers;

import com.tm.tmInterview.models.ReportRecord;
import com.tm.tmInterview.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/words")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/report")
    public List<ReportRecord> generateReport(@RequestBody String requestBody) {
        return reportService.generateReport(requestBody);
    }

    @PostMapping("/matchSearch")
    public Set<ReportRecord> search(@RequestBody String requestBody,
                                    @RequestParam("match") String[] matchWords) {
        return reportService.matchSearch(requestBody, matchWords);
    }

    @PostMapping("/regexSearch")
    public Set<ReportRecord> regexSearch(@RequestBody String requestBody,
                                         @RequestParam("regex") String[] regexWords) {
        return reportService.regexSearch(requestBody, regexWords);
    }
}
