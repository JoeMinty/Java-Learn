package com.moons.paramcheck.aspect;

import com.google.gson.Gson;
import com.moons.paramcheck.annotation.ParamValid;
import com.moons.paramcheck.requestParam.ResponseVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class ParamValidAspect {

    private static final Logger log = LoggerFactory.getLogger(ParamValidAspect.class);

    @Before("@annotation(paramValid)")
    public void paramValid(JoinPoint point, ParamValid paramValid) {
        Object[] paramObj = point.getArgs();
        if (paramObj.length > 0) {
            if (paramObj[1] instanceof BindingResult) {
                BindingResult result = (BindingResult) paramObj[1];
                ResponseVO errorMap = this.validRequestParams(result);
                if (errorMap != null) {
                    ServletRequestAttributes res = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    HttpServletResponse response = res.getResponse();
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatus(HttpStatus.BAD_REQUEST.value());

                    OutputStream output = null;
                    try {
                        output = response.getOutputStream();
                        //errorMap.setCode(null);
                        String error = new Gson().toJson(errorMap);
                        log.info("aop 检测到参数不规范" + error);
                        output.write(error.getBytes("UTF-8"));
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    } finally {
                        try {
                            if (output != null) {
                                output.close();
                            }
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * 校验
     */
    private ResponseVO validRequestParams(BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            List<String> lists = new ArrayList<>();
            for (ObjectError objectError : allErrors) {
                lists.add(objectError.getDefaultMessage());
            }
            return new ResponseVO(HttpStatus.BAD_REQUEST.value(), "parameter empty", lists);
        }
        return null;
    }
}

