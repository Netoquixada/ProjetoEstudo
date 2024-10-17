package br.com.controlpro.util;

import java.util.Date;

public class CalcDias {

	public static void main(String[] args) {
		int i1 = -10;
        //Pega o valor absoluto de i1 isto é o módulo do mesmo
        int i2 = Math.abs(i1);
        System.out.println(i1);
        System.out.println(i2);
	}

	public static long calDias(Date inicio, Date previsaoTermino) {
		long dias = 0L;
		// 1 hora para compensar hor�rio de ver�o
		if(previsaoTermino != null){
			dias = (previsaoTermino.getTime() - inicio.getTime()) + 3600000;
		}
		return dias / 86400000L;
	}
}
