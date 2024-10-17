package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ControleManifestacaoDAO;
import br.com.controlpro.entity.ControleManifestacao;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.QuantidadeEnvioInvalidaException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ControleManifestacaoBO {

	public static ControleManifestacaoBO getInstance() {
		return new ControleManifestacaoBO();
	}

	public void save(ControleManifestacao controleManifestacao)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(controleManifestacao);
		GenericDao.save(controleManifestacao);
	}

	public void update(ControleManifestacao controleManifestacao)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(controleManifestacao);
	}

	public ControleManifestacao find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ControleManifestacao.class, id);
	}

	public LazyEntityDataModel<ControleManifestacao> controleManifestacaoLazy(
			ControleManifestacao controleManifestacao) {
		return ControleManifestacaoDAO.getInstance().controleManifestacaosLazy(controleManifestacao);
	}

	public List<ControleManifestacao> controleManifestacaoComplete(String controleManifestacaoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ControleManifestacaoDAO.getInstance().controleManifestacaos(controleManifestacaoName);
	}

	public List<ControleManifestacao> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ControleManifestacaoDAO.getInstance().controleManifestacaos();
	}

	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumberFormatException {
		return ControleManifestacaoDAO.getInstance().geradorDeProtocolo();
	}
	public String geradorDeProtocoloOcorrencia()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumberFormatException {
		return ControleManifestacaoDAO.getInstance().geradorDeProtocoloOcorrencia();
	}

	public StringBuilder dadosFiltro() {
		return ControleManifestacaoDAO.getInstance().getDadosFiltro();
	}

	public List<ControleManifestacao> controleManifestacaoListReport(ControleManifestacao controleManifestacao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ControleManifestacaoDAO.getInstance().controleManifestacaoListReport(
				controleManifestacao);
	}

	public void validation(ControleManifestacao ordemCorte) {

	}
	
	public void validarQuantidadeEnvio(Integer qtdEnvio, Integer cortadas)
			throws QuantidadeEnvioInvalidaException {
	}

}