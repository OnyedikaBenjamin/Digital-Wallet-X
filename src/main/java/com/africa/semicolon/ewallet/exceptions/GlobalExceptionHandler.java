package com.africa.semicolon.ewallet.exceptions;

import com.africa.semicolon.ewallet.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler (GenericHandlerException.class)
    public ResponseEntity<ApiResponse> globalHandlerException(GenericHandlerException genericHandlerException,
                                                              HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(genericHandlerException.getMessage())
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.CONFLICT.value())
                .isSuccessful(false)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
