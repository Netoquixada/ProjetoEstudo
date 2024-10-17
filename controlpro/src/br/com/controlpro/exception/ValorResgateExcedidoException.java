package br.com.controlpro.exception;

import java.io.Serializable;

public class ValorResgateExcedidoException extends Exception implements Serializable {
	 
	private static final long serialVersionUID = -8922605396441669876L;

	public ValorResgateExcedidoException(String msg){
		super(msg);
	}

}
