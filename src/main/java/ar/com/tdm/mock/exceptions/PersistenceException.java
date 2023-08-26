package ar.com.tdm.mock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author dgunset
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersistenceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
    
    private int code;
    private  HttpStatus httpStatus = HttpStatus.NOT_FOUND;
 
    public PersistenceException(String message) {
        super(message);
    }
    
    public PersistenceException(String message, int code) {
        super(message);
        setCode(code);
    }

	public PersistenceException(HttpStatus httpStatus, String message) {
		super(message);
		setHttpStatus(httpStatus);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
