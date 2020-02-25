package store.server.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import store.server.category.exception.CategoryNotFoundException;
import store.server.category.exception.InvalidCategoryInfoException;
import store.server.item.exception.InvalidItemInfoException;
import store.server.item.exception.ItemNotFoundException;

@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCategoryInfoException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCategoryInfoException(InvalidCategoryInfoException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleItemNotFoundException(ItemNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidItemInfoException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidItemInfoException(InvalidItemInfoException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }

}
