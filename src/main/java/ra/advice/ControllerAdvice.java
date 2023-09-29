package ra.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.exception.ProductExceptionValidate;

import javax.validation.UnexpectedTypeException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleExceptionProductPost(BindException e){
        Map<String,String> map = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()
             ) {
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductExceptionValidate.class)
    public ResponseEntity<?> handleDuplicateProduct(ProductExceptionValidate e){
        Map<String,String> map =new HashMap<>();
        map.put("name",e.getMessage());
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }
}
