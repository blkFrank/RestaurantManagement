package com.pxltk.prenotazioni.exception;

/**
 * 
 * @author Antonio Magnocavallo
 * @company PIXELTEK S.R.L.
 * @email amagnocavallo@pxltk.com
 *
 */
public enum ErrorCode {

	_500("500", "Errore Generico di sistema");

	private String code;
	private String description;

	private ErrorCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
