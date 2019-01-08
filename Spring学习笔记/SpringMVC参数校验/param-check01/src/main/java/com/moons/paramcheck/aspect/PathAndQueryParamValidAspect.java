package com.moons.paramcheck.aspect;

import com.moons.paramcheck.annotation.ParameterValid;
import com.moons.paramcheck.annotation.PathAndQueryParamValid;
import com.moons.paramcheck.requestParam.ResponseVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.lang.*;

import java.util.List;

@Aspect
@Component
public class PathAndQueryParamValidAspect {

    private static final Logger log = LoggerFactory.getLogger(PathAndQueryParamValidAspect.class);

    @Before("@annotation(paramValid)")
    public void paramValid(JoinPoint joinPoint, PathAndQueryParamValid paramValid) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] param = joinPoint.getArgs();
        try {
            List<String> errorLists = ParamValidSupport.get().validate(className, methodName,
                    ParameterValid.class, param);
            if (errorLists != null) {
                AdvanceResponseSupport.advanceResponse(
                        new ResponseVO(HttpStatus.BAD_REQUEST.value(), "parameter empty", errorLists));
            }
        } catch ( NoSuchMethodException | ClassNotFoundException e) {
            log.error("e-name：" + e.getClass().getName() + "： message：" + e.getMessage());
        }
    }
}


