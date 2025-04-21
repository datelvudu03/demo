package com.example.demo.cmd.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StartCmd {
    private String location;
}
