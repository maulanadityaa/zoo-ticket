package org.enigma.zooticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.model.exception.ApplicationException;
import org.enigma.zooticket.model.response.ReportResponse;
import org.enigma.zooticket.model.response.TransactionDetailResponse;
import org.enigma.zooticket.model.response.TransactionResponse;
import org.enigma.zooticket.service.ReportService;
import org.enigma.zooticket.service.TicketService;
import org.enigma.zooticket.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final TransactionService transactionService;

    @Override
    public List<ReportResponse> getReportByMonth(Integer month, Integer year) {
        List<TransactionResponse> transactionResponses = transactionService.getAllTransactions();
        List<TransactionResponse> monthlyReport = transactionResponses.stream().filter(transactionResponse -> transactionResponse.getTransactionDate().getMonthValue() == month && transactionResponse.getTransactionDate().getYear() == year).toList();

        if (!monthlyReport.isEmpty()) {
            return toReportResponses(monthlyReport);
        } else {
            throw new ApplicationException("Transaction not found", "Theres no transaction on this date", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<ReportResponse> getReportByDay(Integer year, Integer month, Integer day) {
        List<TransactionResponse> transactionResponses = transactionService.getAllTransactions();
        List<TransactionResponse> dailyReport = transactionResponses.stream().filter(transactionResponse -> transactionResponse.getTransactionDate().getDayOfMonth() == day && transactionResponse.getTransactionDate().getMonthValue() == month && transactionResponse.getTransactionDate().getYear() == year).toList();

        if (!dailyReport.isEmpty()) {
            return toReportResponses(dailyReport);
        }
        throw new ApplicationException("Transaction not found", "Theres no transaction on this date", HttpStatus.NOT_FOUND);
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
