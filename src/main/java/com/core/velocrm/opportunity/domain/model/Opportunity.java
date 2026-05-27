package com.core.velocrm.opportunity.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Opportunity {

    private final UUID id;
    private final String title;
    private final String description;
    private final BigDecimal amount;
    private Stage stage;
    private final String customAttributes;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Opportunity(UUID id, String title, String description, BigDecimal amount, Stage stage, String customAttributes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.stage = stage;
        this.customAttributes = customAttributes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void moveToStage(Stage newStage) {
        if (this.stage == Stage.WON || this.stage == Stage.LOST) {
            throw new IllegalStateException("Cannot move opportunity from " + this.stage + " stage.");
        }
        this.stage = newStage;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Stage getStage() {
        return stage;
    }

    public String getCustomAttributes() {
        return customAttributes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }



    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

