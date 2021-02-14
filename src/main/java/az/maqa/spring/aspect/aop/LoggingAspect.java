package az.maqa.spring.aspect.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
    public void logForControllerPackage(){
        log.info("This method executes before all methods inside of controller package");
    }


}
