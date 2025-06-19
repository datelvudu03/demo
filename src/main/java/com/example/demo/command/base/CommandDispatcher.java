package com.example.demo.command.base;

import com.example.demo.command.base.type.Command;
import com.example.demo.command.base.type.CommandWithResult;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommandDispatcher {

    private final Map<Class<? extends Command>, MethodInvoker> handlerMap = new ConcurrentHashMap<>();
    private final  Set<String> processedCommandIds = ConcurrentHashMap.newKeySet();

    public <R> R dispatch(Command command) {
        Class<?> commandClass = command.getClass();
        //
        if (commandClass.isAnnotationPresent(Idempotent.class)) {
            String commandId = extractCommandId(command);
            if (commandId == null || commandId.isBlank()) {
                throw new IllegalArgumentException("Idempotent command must provide a non-empty getCommandId()");
            }

            boolean isNew = processedCommandIds.add(commandId);
            if (!isNew) {
                throw new IllegalStateException("Duplicate command detected: " + commandId);
            }

            try {
                return invokeHandler(command);
            } catch (Exception e) {
                processedCommandIds.remove(commandId); // allow retry
                throw e;
            }
        }

        return invokeHandler(command);
    }

    public void registerHandler(Class<? extends Command> commandType, Object bean, Method method) {
        if (handlerMap.containsKey(commandType)) {
            Method existing = handlerMap.get(commandType).method;
            throw new IllegalStateException(
                    "Duplicate handler for command: " + commandType.getName() +
                            "\n - Existing: " + existing.getDeclaringClass().getName() + "#" + existing.getName() +
                            "\n - Duplicate: " + bean.getClass().getName() + "#" + method.getName()
            );
        }

        method.setAccessible(true);
        handlerMap.put(commandType, new MethodInvoker(bean, method));
    }

    private <R> R invokeHandler(Command command) {
        MethodInvoker invoker = handlerMap.get(command.getClass());
        if (invoker == null) {
            throw new RuntimeException("No handler found for: " + command.getClass().getName());
        }

        try {
            Object result = invoker.method.invoke(invoker.bean, command);
            if (command instanceof CommandWithResult<?>) {
                return (R) result;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke handler", e);
        }
    }

    private String extractCommandId(Command command) {
        try {
            Method method = command.getClass().getMethod("getCommandId");
            Object result = method.invoke(command);
            return (result instanceof String str) ? str : null;
        } catch (Exception e) {
            throw new IllegalStateException("Idempotent command must have a public getCommandId() method", e);
        }
    }

    private record MethodInvoker(Object bean, Method method) {}
}

