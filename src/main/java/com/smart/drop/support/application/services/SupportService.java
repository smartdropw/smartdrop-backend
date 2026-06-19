package com.smart.drop.support.application.services;

import com.smart.drop.support.domain.model.entities.IncidentReport;
import com.smart.drop.support.domain.model.entities.SupportTicket;
import com.smart.drop.support.domain.model.repositories.NotificationRepository;
import com.smart.drop.support.domain.model.ports.TicketManagementPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupportService {

    private final NotificationRepository notificationRepository;
    private final TicketManagementPort ticketManagementPort;

    public SupportService(NotificationRepository notificationRepository, TicketManagementPort ticketManagementPort) {
        this.notificationRepository = notificationRepository;
        this.ticketManagementPort = ticketManagementPort;
    }

    public SupportTicket createSupportTicket(Integer userId, String description) {
        SupportTicket supportTicket = SupportTicket.create(userId, description);
        SupportTicket saved = notificationRepository.saveSupportTicket(supportTicket);
        ticketManagementPort.createTicket("Support ticket", description, "MEDIUM");
        return saved;
    }

    @Transactional(readOnly = true)
    public Optional<SupportTicket> getSupportTicketById(Integer ticketId) {
        return notificationRepository.findSupportTicketById(ticketId);
    }

    @Transactional(readOnly = true)
    public List<SupportTicket> getSupportTicketsByUserId(Integer userId) {
        return notificationRepository.findSupportTicketsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<SupportTicket> getAllSupportTickets() {
        return notificationRepository.findAllSupportTickets();
    }

    public SupportTicket updateSupportTicketStatus(Integer ticketId, String status) {
        SupportTicket current = notificationRepository.findSupportTicketById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Support ticket not found: " + ticketId));
        SupportTicket updated = current.withStatus(status);
        return notificationRepository.saveSupportTicket(updated);
    }

    public IncidentReport createIncidentReport(Integer adminId, String title, String description, String status) {
        IncidentReport incidentReport = IncidentReport.create(adminId, title, description, status);
        return notificationRepository.saveIncidentReport(incidentReport);
    }

    @Transactional(readOnly = true)
    public Optional<IncidentReport> getIncidentReportById(Integer incidentId) {
        return notificationRepository.findIncidentReportById(incidentId);
    }

    @Transactional(readOnly = true)
    public List<IncidentReport> getIncidentReportsByAdminId(Integer adminId) {
        return notificationRepository.findIncidentReportsByAdminId(adminId);
    }

    @Transactional(readOnly = true)
    public List<IncidentReport> getAllIncidentReports() {
        return notificationRepository.findAllIncidentReports();
    }
}

