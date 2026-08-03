package com.core.velocrm.opportunity.infrastructure.web.controller;

import com.core.velocrm.opportunity.application.port.in.*;
import com.core.velocrm.opportunity.domain.model.Opportunity;
import com.core.velocrm.opportunity.domain.model.Stage;
import com.core.velocrm.opportunity.infrastructure.dto.MoveOpportunityRequest;
import com.core.velocrm.opportunity.infrastructure.dto.OpportunityRequest;
import com.core.velocrm.opportunity.infrastructure.dto.OpportunityResponse;
import com.core.velocrm.opportunity.infrastructure.persistence.mapper.OpportunityMapper;
import com.core.velocrm.opportunity.infrastructure.web.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OpportunityControllerIntegrationTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CreateOpportunityUseCase createOpportunityUseCase;

    @Mock
    private MoveOpportunityUseCase moveOpportunityUseCase;

    @Mock
    private FindOpportunityByIdUseCase findOpportunityByIdUseCase;

    @Mock
    private FindAllOpportunitiesUseCase findAllOpportunitiesUseCase;

    @Mock
    private UpdateOpportunityUseCase updateOpportunityUseCase;

    @Mock
    private DeleteOpportunityUseCase deleteOpportunityUseCase;

    @Mock
    private OpportunityMapper mapper;

    @InjectMocks
    private OpportunityController opportunityController;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(opportunityController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void shouldCreateOpportunity() throws Exception {
        OpportunityRequest request = new OpportunityRequest(
                "New Title",
                "Description",
                BigDecimal.TEN,
                Stage.PROSPECTING,
                java.util.Map.of()
        );

        Opportunity domain = new Opportunity(
                UUID.randomUUID(),
                "New Title",
                "Description",
                BigDecimal.TEN,
                Stage.PROSPECTING,
                java.util.Map.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        OpportunityResponse response = new OpportunityResponse(
                domain.getId(),
                domain.getTitle(),
                domain.getDescription(),
                domain.getAmount(),
                domain.getStage(),
                domain.getCustomAttributes(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );

        when(mapper.toDomainFromRequest(any(OpportunityRequest.class))).thenReturn(domain);
        when(createOpportunityUseCase.createOpportunity(any(Opportunity.class))).thenReturn(domain);
        when(mapper.toResponse(any(Opportunity.class))).thenReturn(response);

        mockMvc.perform(post("/api/opportunities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.amount").value(10));
    }

    @Test
    void shouldGetOpportunityById() throws Exception {
        UUID id = UUID.randomUUID();
        Opportunity domain = new Opportunity(
                id,
                "Title",
                "Description",
                BigDecimal.TEN,
                Stage.PROSPECTING,
                java.util.Map.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        OpportunityResponse response = new OpportunityResponse(
                id,
                "Title",
                "Description",
                BigDecimal.TEN,
                Stage.PROSPECTING,
                java.util.Map.of(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );

        when(findOpportunityByIdUseCase.findById(id)).thenReturn(Optional.of(domain));
        when(mapper.toResponse(domain)).thenReturn(response);

        mockMvc.perform(get("/api/opportunities/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    void shouldReturn404WhenOpportunityNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(findOpportunityByIdUseCase.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/opportunities/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Opportunity with ID " + id + " was not found."));
    }

    @Test
    void shouldGetAllOpportunities() throws Exception {
        Opportunity domain = new Opportunity(
                UUID.randomUUID(),
                "Title",
                "Description",
                BigDecimal.TEN,
                Stage.PROSPECTING,
                java.util.Map.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        OpportunityResponse response = new OpportunityResponse(
                domain.getId(),
                domain.getTitle(),
                domain.getDescription(),
                domain.getAmount(),
                domain.getStage(),
                domain.getCustomAttributes(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );

        when(findAllOpportunitiesUseCase.findAll()).thenReturn(List.of(domain));
        when(mapper.toResponse(domain)).thenReturn(response);

        mockMvc.perform(get("/api/opportunities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title"));
    }

    @Test
    void shouldUpdateOpportunity() throws Exception {
        UUID id = UUID.randomUUID();
        OpportunityRequest request = new OpportunityRequest(
                "Updated Title",
                "Updated Description",
                BigDecimal.TEN,
                Stage.PROPOSAL,
                java.util.Map.of()
        );

        Opportunity domainUpdates = new Opportunity(
                null,
                "Updated Title",
                "Updated Description",
                BigDecimal.TEN,
                Stage.PROPOSAL,
                java.util.Map.of(),
                null,
                null
        );

        Opportunity updatedDomain = new Opportunity(
                id,
                "Updated Title",
                "Updated Description",
                BigDecimal.TEN,
                Stage.PROPOSAL,
                java.util.Map.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        OpportunityResponse response = new OpportunityResponse(
                id,
                "Updated Title",
                "Updated Description",
                BigDecimal.TEN,
                Stage.PROPOSAL,
                java.util.Map.of(),
                updatedDomain.getCreatedAt(),
                updatedDomain.getUpdatedAt()
        );

        when(mapper.toDomainFromRequest(any(OpportunityRequest.class))).thenReturn(domainUpdates);
        when(updateOpportunityUseCase.updateOpportunity(any(UUID.class), any(Opportunity.class))).thenReturn(updatedDomain);
        when(mapper.toResponse(any(Opportunity.class))).thenReturn(response);

        mockMvc.perform(put("/api/opportunities/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.stage").value("PROPOSAL"));
    }

    @Test
    void shouldDeleteOpportunity() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(deleteOpportunityUseCase).deleteOpportunity(id);

        mockMvc.perform(delete("/api/opportunities/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn400WhenCreateRequestIsInvalid() throws Exception {
        OpportunityRequest request = new OpportunityRequest(
                "",
                "Description",
                null,
                Stage.PROSPECTING,
                java.util.Map.of()
        );

        mockMvc.perform(post("/api/opportunities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.title").value("Title is required"))
                .andExpect(jsonPath("$.errors.amount").value("Amount is required"));
    }

    @Test
    void shouldMoveOpportunity() throws Exception {
        UUID id = UUID.randomUUID();
        MoveOpportunityRequest request = new MoveOpportunityRequest(Stage.PROPOSAL);

        Opportunity updatedDomain = new Opportunity(
                id,
                "Title",
                "Description",
                BigDecimal.TEN,
                Stage.PROPOSAL,
                java.util.Map.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        OpportunityResponse response = new OpportunityResponse(
                id,
                "Title",
                "Description",
                BigDecimal.TEN,
                Stage.PROPOSAL,
                java.util.Map.of(),
                updatedDomain.getCreatedAt(),
                updatedDomain.getUpdatedAt()
        );

        when(moveOpportunityUseCase.moveOpportunity(id, Stage.PROPOSAL)).thenReturn(updatedDomain);
        when(mapper.toResponse(updatedDomain)).thenReturn(response);

        mockMvc.perform(patch("/api/opportunities/{id}/move", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stage").value("PROPOSAL"));
    }
}
