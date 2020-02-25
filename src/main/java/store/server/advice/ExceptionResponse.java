package store.server.advice;

import lombok.Getter;

@Getter
class ExceptionResponse {

    private String message;

    private Class<? extends Exception> exceptionClass;

    ExceptionResponse(Exception ex) {
        this.message = ex.getMessage();
        this.exceptionClass = ex.getClass();
    }

}
