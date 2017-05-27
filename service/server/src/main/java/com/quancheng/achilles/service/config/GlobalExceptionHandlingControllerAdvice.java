package com.quancheng.achilles.service.config;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * The exceptions below could be raised by any controller and they would be handled here, if not handled in the
 * controller already. TODO: you may need customize for json response
 */
@ControllerAdvice()
public class GlobalExceptionHandlingControllerAdvice {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandlingControllerAdvice.class);

    public GlobalExceptionHandlingControllerAdvice(){
    }

    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */
    /*
     * . . . . . . . . . . . . . EXCEPTION HANDLERS . . . . . . . . . . . . . .
     */
    /*
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     */

    /**
     * Convert a predefined exception to an HTTP Status code and specify the name of a specific view that will be used
     * to display the error.
     * 
     * @return Exception view.
     */
    @ExceptionHandler({ SQLException.class })
    public String databaseError(Exception exception) {
        // Nothing to do. Return value 'databaseError' used as logical view name
        // of an error page, passed to view-resolver(s) in usual way.
        logger.error("Request raised " + exception.getClass().getSimpleName());
        return "databaseError";
    }

    /**
     * Demonstrates how to take total control - setup a model, add useful information and return the "support" view
     * name. This method explicitly creates and returns
     * 
     * @param req Current HTTP request.
     * @param exception The exception thrown - always {@link SupportInfoException}.
     * @return The model and view used by the DispatcherServlet to generate output.
     * @throws Exception
     */
    @ExceptionHandler(Throwable.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception) throws Exception {

        // Rethrow annotated exceptions or they will be processed here instead.
        if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) throw exception;

        logger.error("Request: " + req.getRequestURI() + " raised " + exception);
        exception.printStackTrace();

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("timestamp", new Date().toString());
        mav.addObject("status", 500);

        mav.setViewName("500");
        return mav;
    }
}
