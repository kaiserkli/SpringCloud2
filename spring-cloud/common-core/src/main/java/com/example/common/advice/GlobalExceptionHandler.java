package com.example.common.advice;

import com.example.common.exceptions.BusinessException;
import com.example.common.web.response.ResponseEntity;
import com.example.common.web.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常捕获处理方法.
     * @param e 异常
     * @return Object
     */
    @ExceptionHandler(value = Exception.class)
    public Object defaultErrorHandler(Exception e) {
        log.error(e.getMessage(), e);

        if (e instanceof BusinessException) { // 自定义异常处理
            BusinessException exception = (BusinessException) e;
            return ResponseEntity.factory(exception.getCode(), exception.getMessage());
        } else if (e instanceof NoHandlerFoundException) { // 路径不存在异常处理
            return ResponseEntity.factory(ResponseMessage.INVOKE_FAILURE, "地址不存在");
        } else if (e instanceof ConstraintViolationException) { //方法参数验证异常处理
            ConstraintViolationException exception = (ConstraintViolationException) e;

            Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

            if (constraintViolations.size() == 0) {
                return ResponseEntity.factory(ResponseMessage.INVOKE_FAILURE, "参数验证错误");
            }

            ConstraintViolation<?> constraintViolation = null;
            for (Iterator<ConstraintViolation<?>> it = constraintViolations.iterator(); it.hasNext();) {
                constraintViolation = it.next();
                continue;
            }

            return ResponseEntity.factory(ResponseMessage.INVOKE_FAILURE, constraintViolation.getMessage());
        }else if (e instanceof MethodArgumentNotValidException) { //属性绑定异常处理
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            return getFieldErrors(exception.getBindingResult());
        } else if (e instanceof BindException) { //属性绑定异常处理
            BindException exception = (BindException) e;
            return getFieldErrors(exception.getBindingResult());
        } else { // 其他异常处理
            return ResponseEntity.factory(ResponseMessage.INVOKE_FAILURE, "未知错误");
        }
    }

    public ResponseEntity getFieldErrors(BindingResult bindingResult) {

        if (bindingResult.getFieldErrors().size() > 0) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            return ResponseEntity.factory(ResponseMessage.INVOKE_FAILURE, fieldError.getDefaultMessage());
        } else {
            return ResponseEntity.factory(ResponseMessage.INVOKE_FAILURE);
        }
    }
}
