package br.com.controlpro.util;

import java.util.ArrayList;
import java.util.List;

import br.com.controlpro.bo.MateriaPrimaBO;
import br.com.controlpro.entity.MateriaPrima;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class Teste {

	public static void main(String[] args) {
//		System.out.println(
//		new SimpleDateFormat("MM/yyyy")
//		.format(new Date()));

//		Integer  numero =3;
//		System.out.println(StringUtils.leftPad(String.valueOf(numero), 5, "0"));

//		String nome = "Andrey";
//		System.out.println(nome.);

		// System.out.println("Andrey".charAt(0));

		System.out.println(100 % 6);

		// System.out.println(MovimentacaoBancariaDAO.getInstance().movimentacaoBancaria(new
		// MovimentacaoBancaria()));

//		Integer quantidade = 411;
//		Integer prontas = 230;
//		
//		
//		if(!quantidade.equals(prontas)){
//			System.out.println("São diferentes");
//		}else{
//			System.out.println("São iguais");
//		}
//			
//		try {
//			List<Usuario> lista = new ArrayList<Usuario>();
//
//			lista = UsuarioBO.getInstance().list();
//			
//			System.out.println(lista);
//		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
//			e.printStackTrace();
//		}
		try {
			List<MateriaPrima> lista = new ArrayList<MateriaPrima>();
			
			lista = MateriaPrimaBO.getInstance().list();
			
			System.out.println(lista);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}

	}
}
