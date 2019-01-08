package com.moons.paramcheck.aspect;

import com.moons.paramcheck.annotation.ParameterValid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ParamValidSupport {
    private static final Logger logger = LoggerFactory.getLogger(ParamValidSupport.class);

    private static final String PARAM_TYPE_ERROR = "param type error";
    private static final String INT_PARAM_ERROR = "Invalid interva";
    private static final int INT_PARAM_TYPE_MAX_SIZE = 2;
    private static final int INT_PARAM_SIZE_SUBSCRIPT_MIN = 0;
    private static final int INT_PARAM_SIZE_SUBSCRIPT_MAX = 0;

    private static final int STRING_SIZE = 2;
    private static final char STRING_TYPE_END = '}';
    private static final char STRING_TYPE_BEGIN = '{';
    private static final char STRING_EMPTY_DOUBLE_CHARACTER = '"';
    private static final char STRING_EMPTY_SINGLE_CHARACTER = '\'';

    private static ParamValidSupport mInstance;

    private ParamValidSupport() {
    }

    public static ParamValidSupport get() {
        if (mInstance == null) {
            synchronized (ParamValidSupport.class) {
                if (mInstance == null) {
                    mInstance = new ParamValidSupport();
                }
            }
        }
        return mInstance;
    }

    /**
     * 校验
     */
    public List<String> validate(String className, String methodName,
                                 Class<?> annotationClass, Object[] args)
            throws NotFoundException, NoSuchMethodException, ClassNotFoundException {

        if (StringUtils.isBlank(className)) {
            return null;
        }
        if (StringUtils.isBlank(methodName)) {
            return null;
        }
        if (annotationClass == null) {
            return null;
        }

        ClassPool pool = ClassPool.getDefault();
        CtClass ct = pool.get(className);
        CtMethod ctMethod = ct.getDeclaredMethod(methodName);
        Object[][] parameterAnnotations = ctMethod.getParameterAnnotations();

        List<String> errorLists = new ArrayList<>();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            Object[] parameterAnnotation = parameterAnnotations[i];
            Object param = args[i];
            for (Object object : parameterAnnotation) {
                Annotation annotation = (Annotation) object;
                Class<? extends Annotation> aClass = annotation.annotationType();
                if (aClass.equals(annotationClass)) {
                    boolean isEmpty = ((ParameterValid) object).isEmpty();
                    if (isEmpty) {
                        ParameterValid parameterValid = (ParameterValid) object;
                        String errorMsg = parameterValid.msg();
                        if (Integer.class.isAssignableFrom(param.getClass())){
                            int paramInt = (int) param;
                            if (parameterValid.isMin() && paramInt < parameterValid.min()) {
                                errorLists.add(errorMsg);
                            }
                            if (parameterValid.isMax() && paramInt < parameterValid.max()) {
                                errorLists.add(errorMsg);
                            }
                            if (parameterValid.isSection()) {
                                int[] section = parameterValid.section();
                                if (section.length != INT_PARAM_TYPE_MAX_SIZE) {
                                    logger.error(INT_PARAM_ERROR);
                                    throw new ParameterValidException(INT_PARAM_ERROR);
                                }
                                if (!(paramInt > section[INT_PARAM_SIZE_SUBSCRIPT_MIN] && paramInt < section[INT_PARAM_SIZE_SUBSCRIPT_MAX])) {
                                    errorLists.add(errorMsg);
                                } else if (!(paramInt > section[INT_PARAM_SIZE_SUBSCRIPT_MAX] && paramInt < section[INT_PARAM_SIZE_SUBSCRIPT_MIN])) {
                                    errorLists.add(errorMsg);
                                }
                            }
                        }

                        if (String.class.isAssignableFrom(param.getClass())){
                            String paramStr = (String) param;
                            if (parameterValid.isNull()) {
                                if (StringUtils.isEmpty(paramStr)) {
                                    errorLists.add(errorMsg);
                                }
                            } else {
                                if (parameterValid.isBlank()) {
                                    if (StringUtils.isBlank(paramStr)) {
                                        errorLists.add(errorMsg);
                                    } else {
                                        int length = paramStr.length();
                                        char begin = paramStr.charAt(0);
                                        char end = paramStr.charAt(length - 1);
                                        if (STRING_TYPE_BEGIN == begin &&
                                                STRING_TYPE_END == end) {
                                            errorLists.add(errorMsg);
                                        }
                                        if (length == STRING_SIZE && STRING_EMPTY_DOUBLE_CHARACTER == begin
                                                && STRING_EMPTY_DOUBLE_CHARACTER == end) {
                                            errorLists.add(errorMsg);
                                        }
                                        if (length == STRING_SIZE && STRING_EMPTY_SINGLE_CHARACTER == begin
                                                && STRING_EMPTY_SINGLE_CHARACTER == end) {
                                            errorLists.add(errorMsg);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (errorLists.size() != 0) {
            return errorLists;
        }
        return null;
    }
}


