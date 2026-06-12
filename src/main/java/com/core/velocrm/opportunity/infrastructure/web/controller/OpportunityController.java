package com.core.velocrm.opportunity.infrastructure.web.controller;

import com.core.velocrm.opportunity.application.port.in.MoveOpportunityUseCase;
import com.core.velocrm.opportunity.infrastructure.dto.OpportunityResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/opportunities")
@RequiredArgsConstructor
public class OpportunityController {

    private final CreateOpportunityUseCase createOpportunityUseCase;
    private final MoveOpportunityUseCase moveOpportunityUseCase;
    @GetMapping("/health")
    @Operation(summary = "Health check")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/Create")
    @Operation(summary = "Criar nova oportunidade")
    public ResponseEntity<OpportunityResponse> createOpportunity(
            @RequestHeader("X-Account-Id") String accountId,
            @Valid @RequestBody CreateOpportunityRequest request
    ) {
        var response = createOpportunityUseCase.execute(
                new CreateOpportunityUseCase.CreateOpportunityCommand(
                        accountId,
                        request.title(),
                        request.description(),
                        request.amount(),
                        request.customAttributes()
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
