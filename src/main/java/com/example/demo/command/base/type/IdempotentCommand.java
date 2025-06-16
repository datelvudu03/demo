package com.example.demo.command.base.type;

public interface IdempotentCommand extends Command{
    String getCommandId();
}
