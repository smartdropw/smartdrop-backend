package com.smart.drop.shared.infrastructure.outbox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - Patrón Transactional Outbox: Publicación Segura de Eventos")
class OutboxPublisherServiceTest {

    @Mock
    private OutboxMessageRepository outboxRepository;

    private OutboxPublisherService publisherService;

    @BeforeEach
    void setUp() {
        publisherService = new OutboxPublisherService(outboxRepository);
    }

    @Test
    @DisplayName("Debe persistir el mensaje en la tabla Outbox en estado PENDING")
    void shouldStageEventInPendingStatus() {
        // Arrange
        when(outboxRepository.save(any(OutboxMessage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        OutboxMessage message = publisherService.stageEvent(
                "Tank", "TANK-101", "CriticalLeakDetectedEvent", "{\"dropRate\": 12.5}"
        );

        // Assert
        assertThat(message.getAggregateType()).isEqualTo("Tank");
        assertThat(message.getAggregateId()).isEqualTo("TANK-101");
        assertThat(message.getEventType()).isEqualTo("CriticalLeakDetectedEvent");
        assertThat(message.getStatus()).isEqualTo(OutboxStatus.PENDING);
        verify(outboxRepository, times(1)).save(any(OutboxMessage.class));
    }

    @Test
    @DisplayName("Debe despachar eventos pendientes y cambiar su estado a PUBLISHED con timestamp")
    void shouldDispatchPendingEventsToBrokerAndMarkAsPublished() {
        // Arrange
        OutboxMessage pendingMsg = new OutboxMessage("Tank", "TANK-202", "ThermalExcursionEvent", "{\"temp\": 23.0}");
        when(outboxRepository.findByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING))
                .thenReturn(List.of(pendingMsg));

        // Act
        int dispatched = publisherService.dispatchPendingEvents();

        // Assert
        assertThat(dispatched).isEqualTo(1);
        assertThat(pendingMsg.getStatus()).isEqualTo(OutboxStatus.PUBLISHED);
        assertThat(pendingMsg.getProcessedAt()).isNotNull();
        verify(outboxRepository, times(1)).save(pendingMsg);
    }
}
