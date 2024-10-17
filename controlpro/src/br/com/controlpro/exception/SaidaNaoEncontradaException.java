package br.com.controlpro.exception;

import java.io.Serializable;

public class SaidaNaoEncontradaException extends Exception implements Serializable {
	 
	private static final long serialVersionUID = -8922605396441669876L;

	public SaidaNaoEncontradaException(String msg){
		super(msg);
	}

}
