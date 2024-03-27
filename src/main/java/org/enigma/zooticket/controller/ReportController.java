package org.enigma.zooticket.controller;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.constant.AppPath;
import org.enigma.zooticket.model.response.CommonResponse;
import org.enigma.zooticket.model.response.ReportResponse;
import org.enigma.zooticket.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(AppPath.REPORTS)
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping(AppPath.MONTHLY_REPORT)
    public ResponseEntity<?> getMonthlyReport(@RequestParam("month") Integer month, @RequestParam("year") Integer year) {
        List<ReportResponse> reportResponses = reportService.getReportByMonth(month, year);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<List<ReportResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Monthly report retrieved successfully")
                        .data(reportResponses)
                        .build());
    }

    @GetMapping(AppPath.DAILY_REPORT)
    public ResponseEntity<?> getMonthlyReport(@RequestParam("month") Integer month, @RequestParam("year") Integer year, @RequestParam("day") Integer day) {
        List<ReportResponse> reportResponses = reportService.getReportByDay(year, month, day);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<List<ReportResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Monthly report retrieved successfully")
                        .data(reportResponses)
                        .build());
    }
}
