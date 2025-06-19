package com.example.demo.command.base;

import com.example.demo.command.base.type.Command;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Component
public class CommandHandlerRegistrar implements ApplicationListener<ContextRefreshedEvent> {

    private final ApplicationContext context;
    private final CommandDispatcher dispatcher;

    public CommandHandlerRegistrar(ApplicationContext context, CommandDispatcher dispatcher) {
        this.context = context;
        this.dispatcher = dispatcher;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (Object bean : context.getBeansWithAnnotation(CommandHandlerClass.class).values()) {
            Class<?> actualClass = AopUtils.getTargetClass(bean);

            for (Method method : actualClass.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(HandlesCommand.class)) continue;

                List<Class<?>> commandParams = Arrays.stream(method.getParameterTypes())
                        .filter(Command.class::isAssignableFrom)
                        .toList();

                if (commandParams.size() != 1) {
                    throw new IllegalStateException(
                            "Handler method must have exactly one Command parameter: " +
                                    actualClass.getName() + "#" + method.getName()
                    );
                }

                Class<?> paramType = commandParams.get(0);
                if (!Command.class.isAssignableFrom(paramType)) {
                    throw new IllegalStateException("Invalid parameter type: " + paramType.getName());
                }

                @SuppressWarnings("unchecked")
                Class<? extends Command> commandType = (Class<? extends Command>) paramType;

                dispatcher.registerHandler(commandType, bean, method);
            }
        }
    }
}

