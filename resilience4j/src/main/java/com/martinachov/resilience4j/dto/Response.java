package com.martinachov.resilience4j.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private String data;
    private int code;
}
