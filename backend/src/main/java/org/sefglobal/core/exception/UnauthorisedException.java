package org.sefglobal.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorisedException extends APIException {

    public UnauthorisedException() {
        super();
    }

    public UnauthorisedException(String msg) {
        super(msg);
    }

    public UnauthorisedException(String msg, Throwable e) {
        super(msg, e);
    }
}
