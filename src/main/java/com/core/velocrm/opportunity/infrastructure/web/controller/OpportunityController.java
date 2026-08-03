package com.core.velocrm.opportunity.infrastructure.web.controller;

import com.core.velocrm.opportunity.application.port.in.*;
import com.core.velocrm.opportunity.domain.exception.OpportunityNotFoundException;
import com.core.velocrm.opportunity.infrastructure.dto.MoveOpportunityRequest;
import com.core.velocrm.opportunity.infrastructure.dto.OpportunityRequest;
import com.core.velocrm.opportunity.infrastructure.dto.OpportunityResponse;
import com.core.velocrm.opportunity.infrastructure.persistence.mapper.OpportunityMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/opportunities")
@RequiredArgsConstructor
public class OpportunityController {

    private final CreateOpportunityUseCase createOpportunityUseCase;
    private final MoveOpportunityUseCase moveOpportunityUseCase;
    private final FindOpportunityByIdUseCase findOpportunityByIdUseCase;
    private final FindAllOpportunitiesUseCase findAllOpportunitiesUseCase;
    private final UpdateOpportunityUseCase updateOpportunityUseCase;
    private final DeleteOpportunityUseCase deleteOpportunityUseCase;
    private final OpportunityMapper mapper;

    @GetMapping("/health")
    @Operation(summary = "Health check")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping
    @Operation(summary = "Criar nova oportunidade")
    public ResponseEntity<OpportunityResponse> createOpportunity(
            @Valid @RequestBody OpportunityRequest request
    ) {
        var domain = mapper.toDomainFromRequest(request);
        var createdDomain = createOpportunityUseCase.createOpportunity(domain);
        var response = mapper.toResponse(createdDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar oportunidade por ID")
    public ResponseEntity<OpportunityResponse> getOpportunityById(@PathVariable UUID id) {
        return findOpportunityByIdUseCase.findById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new OpportunityNotFoundException(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas as oportunidades")
    public ResponseEntity<List<OpportunityResponse>> getAllOpportunities() {
        var list = findAllOpportunitiesUseCase.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar oportunidade")
    public ResponseEntity<OpportunityResponse> updateOpportunity(
            @PathVariable UUID id,
            @Valid @RequestBody OpportunityRequest request
    ) {
        var domainUpdates = mapper.toDomainFromRequest(request);
        var updatedDomain = updateOpportunityUseCase.updateOpportunity(id, domainUpdates);
        var response = mapper.toResponse(updatedDomain);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/move")
    @Operation(summary = "Mover oportunidade para outro estágio")
    public ResponseEntity<OpportunityResponse> moveOpportunity(
            @PathVariable UUID id,
            @Valid @RequestBody MoveOpportunityRequest request
    ) {
        var updatedDomain = moveOpportunityUseCase.moveOpportunity(id, request.stage());
        var response = mapper.toResponse(updatedDomain);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar oportunidade")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable UUID id) {
        deleteOpportunityUseCase.deleteOpportunity(id);
        return ResponseEntity.noContent().build();
    }

}
