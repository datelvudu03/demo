package com.example.demo.command.base.type;

public interface IdempotentCommandWithResult extends CommandWithResult{
    String getCommandId();
}
