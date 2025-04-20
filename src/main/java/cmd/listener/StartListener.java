package cmd.listener;

import cmd.event.StartCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartListener {

    @EventListener
    void startListen(StartCmd startCmd){

    }
}
