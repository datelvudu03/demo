package com.example.demo.command.base;

import com.example.demo.command.base.type.Command;
import com.example.demo.command.base.type.CommandWithResult;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommandDispatcher implements ApplicationContextAware {

    private final Map<Class<? extends Command>, MethodInvoker> handlerMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        for (Object bean : context.getBeansWithAnnotation(CommandHandlerClass.class).values()) {
            Class<?> actualClass = AopUtils.getTargetClass(bean);

            for (Method method : actualClass.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(HandlesCommand.class)) continue;

                Class<?>[] params = method.getParameterTypes();

                // Find all parameters that implement Command
                List<Class<?>> commandParams = Arrays.stream(params)
                        .filter(Command.class::isAssignableFrom)
                        .toList();

                // Enforce exactly one Command param
                Class<? extends Command> commandType = getCommandType(method, commandParams, actualClass);

                // Prevent duplicate handler registration
                if (handlerMap.containsKey(commandType)) {
                    Method existing = handlerMap.get(commandType).method;
                    throw new IllegalStateException(
                            "⚠Duplicate handler for command: " + commandType.getName() +
                                    "\n - Existing: " + existing.getDeclaringClass().getName() + "#" + existing.getName() +
                                    "\n - Duplicate: " + actualClass.getName() + "#" + method.getName()
                    );
                }

                method.setAccessible(true);
                handlerMap.put(commandType, new MethodInvoker(bean, method));
            }
        }
    }

    private static Class<? extends Command> getCommandType(Method method, List<Class<?>> commandParams, Class<?> actualClass) {
        if (commandParams.size() != 1) {
            throw new IllegalStateException(
                    "Method " + method.getName() + " in " + actualClass.getName() +
                            " must have exactly one parameter implementing Command. Found: " + commandParams.size()
            );
        }

        // Safe cast after type check
        Class<?> paramType = commandParams.get(0);
        if (!Command.class.isAssignableFrom(paramType)) {
            throw new IllegalStateException("Invalid command parameter type: " + paramType.getName());
        }

        @SuppressWarnings("unchecked")
        Class<? extends Command> commandType = (Class<? extends Command>) paramType;
        return commandType;
    }

    public <R> R dispatch(Command command) {
        MethodInvoker invoker = handlerMap.get(command.getClass());
        if (invoker == null) {
            throw new RuntimeException("No handler found for: " + command.getClass().getName());
        }

        try {
            Object result = invoker.method.invoke(invoker.bean, command);

            if (command instanceof CommandWithResult<?>) {
                return (R) result;
            }

            return null; // for void commands
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke handler", e);
        }
    }

    private record MethodInvoker(Object bean, Method method) {
    }
}