package com.example.demo.command.handler;

import com.example.demo.command.base.CommandHandlerClass;
import com.example.demo.command.base.HandlesCommand;
import com.example.demo.command.cmd.CreateTicketCommand;
import org.springframework.stereotype.Component;

@CommandHandlerClass
@Component
public class TicketCommandHandler {
    @HandlesCommand()
    public void handleCreateUser(CreateTicketCommand cmd){

    }

}
