package ru.tsc.almadapter.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class ErrorMessage {

    private String userMessage;
    private String detail;

}