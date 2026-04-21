/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cw_csarchitecture.errorExceptions;

import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author dervishdenaj
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable exception) {
        LOGGER.severe("Unexpected error: " + exception.getMessage());
        ErrorResponse errorMessage = new ErrorResponse(
                "An unexpected internal server error occurred.",
                500
        );
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage).build();
    }
}
