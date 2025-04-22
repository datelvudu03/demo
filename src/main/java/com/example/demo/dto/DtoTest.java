package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class DtoTest implements Serializable {
    private String value;
}
