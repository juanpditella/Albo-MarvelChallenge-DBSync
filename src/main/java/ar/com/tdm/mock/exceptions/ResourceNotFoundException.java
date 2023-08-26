package ar.com.tdm.mock.exceptions;

/**
 * 
 * @author dgunset
 *
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3794450762426287296L;
	
	private int code;
    private int id;
 
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, int code) {
        super(message);
        this.setCode(code);
    }
    
    public ResourceNotFoundException(String message, int code, int id) {
        super(message);
        this.setCode(code);
        this.setId(id);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}