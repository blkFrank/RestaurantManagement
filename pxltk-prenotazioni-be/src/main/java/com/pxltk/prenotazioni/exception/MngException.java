package com.pxltk.prenotazioni.exception;

/**
 * 
 * @author Antonio Magnocavallo
 * @company PIXELTEK S.R.L.
 * @email amagnocavallo@pxltk.com
 *
 */
public class MngException extends Exception {

	private static final long serialVersionUID = 1579926409115510446L;

	private String code;
	private String message;

	public MngException() {
		super();
	}

	public MngException(ErrorCode errorCode) {
		super();
		this.code = errorCode.getCode();
		this.message = errorCode.getDescription();
	}

	public MngException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
