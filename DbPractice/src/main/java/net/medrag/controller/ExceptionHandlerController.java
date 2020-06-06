package net.medrag.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author Stanislav Tretyakov
 * 12.05.2020
 */
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public String requestHandlingNoHandlerFound() {
        System.out.println("FAILLL");
        return "FAIL";
//        return new ErrorResponse("custom_404", "message for 404 error code");
    }
}
