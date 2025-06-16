package com.example.demo.aop;

import com.example.demo.command.base.type.Command;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CommandHandlerAspect {

    @Around("@annotation(com.example.demo.command.base.HandlesCommand)")
    public Object aroundCommandHandler(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().toShortString();

        // Find the first argument that implements Command
        Object command = null;
        for (Object arg : pjp.getArgs()) {
            if (arg instanceof Command) {
                command = arg;
                break;
            }
        }

        // If no Command argument is found, throw an exception
        if (command == null) {
            throw new IllegalStateException("Handler method " + methodName +
                    " must have at least one argument implementing Command.");
        }

        log.info("Executing command handler: {} with command: {}", methodName, command);

        long start = System.currentTimeMillis();
        try {
            Object result = pjp.proceed(); // 🟢 Proceed to original method
            long elapsed = System.currentTimeMillis() - start;

            log.info("Handler {} completed in {} ms", methodName, elapsed);
            return result;
        } catch (Throwable ex) {
            log.error("Handler {} failed with error: {}", methodName, ex.getMessage(), ex);
            throw ex;
        }
    }
}
