package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.OrdemProducaoDao;
import br.com.controlpro.entity.OrdemProducao;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.QuantidadeEnvioInvalidaException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class OrdemProducaoBO {

	public static OrdemProducaoBO getInstance() {
		return new OrdemProducaoBO();
	}

	public void save(OrdemProducao ordemProducao)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(ordemProducao);
		GenericDao.save(ordemProducao);
	}

	public void update(OrdemProducao ordemProducao)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(ordemProducao);
	}

	public OrdemProducao find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(OrdemProducao.class, id);
	}

	public LazyEntityDataModel<OrdemProducao> ordemProducaoLazy(
			OrdemProducao ordemProducao) {
		return OrdemProducaoDao.getInstance().ordemProducaosLazy(ordemProducao);
	}

	public List<OrdemProducao> ordemProducaoComplete(String ordemProducaoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return OrdemProducaoDao.getInstance().ordemProducaos(ordemProducaoName);
	}

	public List<OrdemProducao> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return OrdemProducaoDao.getInstance().ordemProducaos();
	}

	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumberFormatException {
		return OrdemProducaoDao.getInstance().geradorDeProtocolo();
	}
	public String geradorDeProtocoloOcorrencia()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumberFormatException {
		return OrdemProducaoDao.getInstance().geradorDeProtocoloOcorrencia();
	}

	public StringBuilder dadosFiltro() {
		return OrdemProducaoDao.getInstance().getDadosFiltro();
	}

	public List<OrdemProducao> ordemProducaoListReport(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return OrdemProducaoDao.getInstance().ordemProducaoListReport(
				ordemProducao);
	}

	public void validation(OrdemProducao ordemCorte) {

	}
	
	public void validarQuantidadeEnvio(Integer qtdEnvio, Integer cortadas)
			throws QuantidadeEnvioInvalidaException {

		if (qtdEnvio > cortadas) {
			throw new QuantidadeEnvioInvalidaException(
					"Quantidade de envio não pode ser maior que a quantidade de peças cortadas!");
		}
	}

}