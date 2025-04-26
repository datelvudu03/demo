package com.example.demo.cmd.event;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BaseCmd<T> implements Serializable {
    private T value;
}
