package az.maqa.spring.aspect.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging execution of controller and service
 */
@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * @Before advice -- this annotation using on the top of the method. this method execute before the method
     */
    @Before("execution(* az.maqa.spring.aspect.controller.EmployeeController.createEmployee(*))")
    public void logBefore() {
        log.info("This method executed before the createEmployee() method");
    }

    /**
     * @After advice -- this annotation using on the top of the method. this method execute after the method
     */
    @After("execution(* az.maqa.spring.aspect.controller.EmployeeController.createEmployee(*))")
    public void logAfter() {
        log.info("This method executed after the createEmployee() method");
    }

    /**
     * @Pointcut annotation - prevent to copy paste same pointCut multiple times, instead of doing this we can create a new method and then add pointCut expression to that method.
     * We are done. We can use this pointCut expression multiple times , just simply add method name.
     */
    @Pointcut("execution(* az.maqa.spring.aspect.controller.EmployeeController.*(*))")
    public void forControllerPackage() {
    }

    @Before("forControllerPackage()")
    public void logForControllerPackage() {
        log.info("This method executes before all methods inside of controller package");
    }

    /**
     * this pointcut select all getter methods inside of domain package
     */
    @Pointcut("execution(* az.maqa.spring.aspect.domain.*.get*(*))")
    public void getter() {
    }


    /**
     * this pointcut select all setter methods inside of domain package
     */
    @Pointcut("execution(* az.maqa.spring.aspect.domain.*.set*(*))")
    public void setter() {
    }

    /**
     * this pointcut select all methods inside of base package
     */
    @Pointcut("execution(* az.maqa.spring.aspect.*.*.*(*))")
    public void basePackage() {
    }


    /**
     * this is pointcut combining and this methods select all methods inside of base package and without getter and setter methods
     */
    @Before("basePackage() && !(getter() || setter())")
    public void forControllerPackageWithoutGetterAndSetter() {
        log.info("This method Executes before the all base packages' method");
    }

    /**
     * This method executes after the methods throwing error which inside of Employee Controller
     *
     * @param e exception
     */
    @AfterThrowing(pointcut = "execution(* az.maqa.spring.aspect.controller.EmployeeController.*(..))", throwing = "e")
    public void afterThrowingLog(Throwable e) {
        log.info("This method executes after throwing error inside of EmployeeController's any methods");
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(az.maqa.spring.aspect.repository..*)" +
            " || within(az.maqa.spring.aspect.service..*)" +
            " || within(az.maqa.spring.aspect.controller..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            throw e;
        }
    }

}
