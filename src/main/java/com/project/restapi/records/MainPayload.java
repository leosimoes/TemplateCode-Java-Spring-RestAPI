package com.project.restapi.records;

public record MainPayload(String name, String description, String associated) {
    public MainPayload() {
        this("", "", "");
    }
}
