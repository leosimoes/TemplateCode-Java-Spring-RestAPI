package com.project.restapi.records;

public record AssociatedPayload(String name) {
    public AssociatedPayload() {
        this(null);
    }
}