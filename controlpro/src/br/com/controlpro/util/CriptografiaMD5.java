package br.com.controlpro.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptografiaMD5 {

	public static String cifrar(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		MessageDigest algorithm = null;
		byte messageDigest[] = null;
			algorithm = MessageDigest.getInstance("MD5");
			messageDigest = algorithm.digest(senha.getBytes("UTF-8"));
		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}
		String senhaCriptografada = "";
		return senhaCriptografada = hexString.toString();
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(cifrar("Senha@123"));
			System.out.println("admin"+cifrar("admin"));
			System.out.println("123"+cifrar("123"));
			System.out.println("123456"+cifrar("123456"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
