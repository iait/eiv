package iait.eiv.error;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

   @Override
   public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, 
           HttpHeaders headers, HttpStatus status, WebRequest request) {
       String errorMsg = "JSON malformado en la petición";
       ApiError error = new ApiError(HttpStatus.BAD_REQUEST, errorMsg, ex);
       return new ResponseEntity<>(error, error.getStatus());
   }

   @ExceptionHandler(EntityNotFoundException.class)
   public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
       String errorMsg = "Recurso " + ex.getResource() + " no encontrado";
       ApiError error = new ApiError(HttpStatus.NOT_FOUND, errorMsg, ex);
       return new ResponseEntity<>(error, error.getStatus());
   }

   @ExceptionHandler(InvalidInputException.class)
   public ResponseEntity<Object> handleEntityNotFound(InvalidInputException ex) {
       String errorMsg = ex.getDescription();
       ApiError error = new ApiError(HttpStatus.BAD_REQUEST, errorMsg, ex);
       return new ResponseEntity<>(error, error.getStatus());
   }

}