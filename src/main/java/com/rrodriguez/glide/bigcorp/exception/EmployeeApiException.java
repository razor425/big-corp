package com.rrodriguez.glide.bigcorp.exception;

public class EmployeeApiException extends RuntimeException {

    private static final long serialVersionUID = 7538136458884672311L;

    public EmployeeApiException(Throwable cause) {
        super(cause);
    }


    public EmployeeApiException(String message, Throwable cause) {
        super(message, cause);
    }


}
