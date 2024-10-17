package br.com.controlpro.util;

import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class DownloadArquivoUtil {

	public static DownloadArquivoUtil getInstance() {
		return new DownloadArquivoUtil();
	}

	private DownloadArquivoUtil() {

	}

	public static String caminhoWebInf = FacesContext.getCurrentInstance().getExternalContext()
			.getRealPath("/WEB-INF/arquivos");

	// Download de Arquivo
	public StreamedContent downloadArquivo(byte[] arquivoData, StreamedContent arquivo, String nomeDoArquivo,  String tipoArquivo) {

		try {

			FileUtils.writeByteArrayToFile(new File(caminhoWebInf + nomeDoArquivo), arquivoData);

			InputStream stream = new FileInputStream(caminhoWebInf + nomeDoArquivo);
			arquivo = new DefaultStreamedContent(stream, "arquivo/" + tipoArquivo , nomeDoArquivo + "." + tipoArquivo);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NullPointerException npe){
			addWarnMessage("", "Arquivo não anexado!");
		}

		return arquivo;
	}
	
}
