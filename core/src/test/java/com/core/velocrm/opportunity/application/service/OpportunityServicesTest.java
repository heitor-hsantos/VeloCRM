package com.core.velocrm.opportunity.application.service;

import com.core.velocrm.opportunity.application.port.out.EventPublisherPort;
import com.core.velocrm.opportunity.application.port.out.OpportunityRepositoryPort;
import com.core.velocrm.opportunity.domain.event.OpportunityMovedEvent;
import com.core.velocrm.opportunity.domain.exception.OpportunityNotFoundException;
import com.core.velocrm.opportunity.domain.model.Opportunity;
import com.core.velocrm.opportunity.domain.model.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpportunityServicesTest {

    @Mock
    private OpportunityRepositoryPort repositoryPort;

    @Mock
    private EventPublisherPort eventPublisherPort;

    private CreateOpportunityService createService;
    private FindOpportunityByIdService findByIdService;
    private FindAllOpportunitiesService findAllService;
    private UpdateOpportunityService updateService;
    private DeleteOpportunityService deleteService;
    private MoveOpportunityService moveService;

    @BeforeEach
    void setUp() {
        createService = new CreateOpportunityService(repositoryPort);
        findByIdService = new FindOpportunityByIdService(repositoryPort);
        findAllService = new FindAllOpportunitiesService(repositoryPort);
        updateService = new UpdateOpportunityService(repositoryPort);
        deleteService = new DeleteOpportunityService(repositoryPort);
        moveService = new MoveOpportunityService(repositoryPort, eventPublisherPort);
    }

    @Test
    void shouldCreateOpportunity() {
        Opportunity opportunity = new Opportunity(
                UUID.randomUUID(),
                "Title",
                "Description",
                BigDecimal.TEN,
                Stage.PROSPECTING,
                java.util.Map.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(repositoryPort.save(any(Opportunity.class))).thenReturn(opportunity);

        Opportunity created = createService.createOpportunity(opportunity);

        assertNotNull(created);
        assertEquals(opportunity.getTitle(), created.getTitle());
        verify(repositoryPort, times(1)).save(opportunity);
    }

    @Test
    void shouldFindOpportunityById() {
        UUID id = UUID.randomUUID();
        Opportunity opportunity = new Opportunity(
                id,
                "Title",
                "Description",
                BigDecimal.TEN,
                Stage.PROSPECTING,
                java.util.Map.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(repositoryPort.findById(id)).thenReturn(Optional.of(opportunity));

        Optional<Opportunity> found = findByIdService.findById(id);

        assertTrue(found.isPresent());
        assertEquals(id, found.get().getId());
    }

    @Test
    void shouldFindAllOpportunities() {
        Opportunity opportunity = new Opportunity(
                UUID.randomUUID(),
                "Title",
                "Description",
                BigDecimal.TEN,
                Stage.PROSPECTING,
                java.util.Map.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(repositoryPort.findAll()).thenReturn(List.of(opportunity));

        List<Opportunity> list = findAllService.findAll();

        assertEquals(1, list.size());
        assertEquals(opportunity.getTitle(), list.get(0).getTitle());
    }

    @Test
    void shouldUpdateOpportunity() {
        UUID id = UUID.randomUUID();
        Opportunity existing = new Opportunity(
                id,
                "Old Title",
                "Old Description",
                BigDecimal.ONE,
                Stage.PROSPECTING,
                java.util.Map.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Opportunity updates = new Opportunity(
                null,
                "New Title",
                "New Description",
                BigDecimal.TEN,
                Stage.PROPOSAL,
                java.util.Map.of("updated", true),
                null,
                null
        );

        when(repositoryPort.findById(id)).thenReturn(Optional.of(existing));
        when(repositoryPort.save(any(Opportunity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Opportunity updated = updateService.updateOpportunity(id, updates);

        assertNotNull(updated);
        assertEquals(id, updated.getId());
        assertEquals("New Title", updated.getTitle());
        assertEquals("New Description", updated.getDescription());
        assertEquals(BigDecimal.TEN, updated.getAmount());
        assertEquals(Stage.PROPOSAL, updated.getStage());
        assertEquals(java.util.Map.of("updated", true), updated.getCustomAttributes());
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentOpportunity() {
        UUID id = UUID.randomUUID();
        Opportunity updates = new Opportunity(
                null,
                "New Title",
                "New Description",
                BigDecimal.TEN,
                Stage.PROPOSAL,
                java.util.Map.of(),
                null,
                null
        );

        when(repositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThrows(OpportunityNotFoundException.class, () -> updateService.updateOpportunity(id, updates));
    }

    @Test
    void shouldDeleteOpportunity() {
        UUID id = UUID.randomUUID();
        Opportunity existing = new Opportunity(
                id,
                "Title",
                "Description",
                BigDecimal.TEN,
                Stage.PROSPECTING,
                java.util.Map.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(repositoryPort.findById(id)).thenReturn(Optional.of(existing));

        deleteService.deleteOpportunity(id);

        verify(repositoryPort, times(1)).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeletingNonExistentOpportunity() {
        UUID id = UUID.randomUUID();
        when(repositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThrows(OpportunityNotFoundException.class, () -> deleteService.deleteOpportunity(id));
    }

    @Test
    void shouldMoveOpportunity() {
        UUID id = UUID.randomUUID();
        Opportunity existing = new Opportunity(
                id,
                "Title",
                "Description",
                BigDecimal.TEN,
                Stage.PROSPECTING,
                java.util.Map.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(repositoryPort.findById(id)).thenReturn(Optional.of(existing));
        when(repositoryPort.save(any(Opportunity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Opportunity moved = moveService.moveOpportunity(id, Stage.PROPOSAL);

        assertNotNull(moved);
        assertEquals(Stage.PROPOSAL, moved.getStage());
        verify(repositoryPort, times(1)).save(existing);
        verify(eventPublisherPort, times(1)).publish(any(OpportunityMovedEvent.class));
    }

    @Test
    void shouldThrowWhenMovingNonExistentOpportunity() {
        UUID id = UUID.randomUUID();
        when(repositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThrows(OpportunityNotFoundException.class, () -> moveService.moveOpportunity(id, Stage.PROPOSAL));
    }
}
