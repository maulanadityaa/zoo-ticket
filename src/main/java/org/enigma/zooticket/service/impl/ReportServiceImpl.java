package org.enigma.zooticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.model.response.ReportResponse;
import org.enigma.zooticket.model.response.TransactionDetailResponse;
import org.enigma.zooticket.model.response.TransactionResponse;
import org.enigma.zooticket.service.ReportService;
import org.enigma.zooticket.service.TicketService;
import org.enigma.zooticket.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final TransactionService transactionService;
    private final TicketService ticketService;

    @Override
    public List<ReportResponse> getReportByMonth(Integer month, Integer year) {
        List<TransactionResponse> transactionResponses = transactionService.getAllTransactions();
        List<TransactionResponse> monthlyReport = transactionResponses.stream().filter(transactionResponse -> transactionResponse.getTransactionDate().getMonthValue() == month && transactionResponse.getTransactionDate().getYear() == year).toList();

        if (!monthlyReport.isEmpty()) {
            return toReportResponses(monthlyReport);
        } else {
            return null;
        }
    }

    @Override
    public List<ReportResponse> getReportByDay(Integer year, Integer month, Integer day) {
        List<TransactionResponse> transactionResponses = transactionService.getAllTransactions();
        List<TransactionResponse> dailyReport = transactionResponses.stream().filter(transactionResponse -> transactionResponse.getTransactionDate().getDayOfMonth() == day && transactionResponse.getTransactionDate().getMonthValue() == month && transactionResponse.getTransactionDate().getYear() == year).toList();

        if (!dailyReport.isEmpty()) {
            return toReportResponses(dailyReport);
        }
        return null;
    }

    private List<ReportResponse> toReportResponses(List<TransactionResponse> dailyReport) {
        return dailyReport.stream().map(monthly -> {
            ReportResponse reportResponse = new ReportResponse();
            reportResponse.setCustomerId(monthly.getCustomerResponse().getId());
            reportResponse.setTransDate(monthly.getTransactionDate());
            reportResponse.setTotalTicket(monthly.getTransactionDetails().stream().mapToInt(TransactionDetailResponse::getQuantity).sum());
            reportResponse.setTotalPrice(monthly.getTotalPrice());

            return reportResponse;
        }).toList();
    }
}
