package org.enigma.zooticket.service;

import org.enigma.zooticket.model.response.ReportResponse;

import java.util.List;

public interface ReportService {
    List<ReportResponse> getReportByMonth(Integer year, Integer month);

    List<ReportResponse> getReportByDay(Integer year, Integer month, Integer day);
}
