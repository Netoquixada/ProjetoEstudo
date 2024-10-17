package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.constants.TipoFolhaPagamento;
import br.com.controlpro.dao.FolhaPagamentoDao;
import br.com.controlpro.entity.FolhaPagamento;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class FolhaPagamentoBO {

	public static FolhaPagamentoBO getInstance() {
		return new FolhaPagamentoBO();
	}

	public void save(FolhaPagamento FolhaPagamento) throws ViolacaoChaveHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		validation(FolhaPagamento);
		GenericDao.save(FolhaPagamento);
	}

	public void update(FolhaPagamento FolhaPagamento)
			throws ViolacaoChaveHibernateException, ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		validation(FolhaPagamento);
		GenericDao.update(FolhaPagamento);
	}

	public FolhaPagamento find(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(FolhaPagamento.class, id);
	}

	public LazyEntityDataModel<FolhaPagamento> folhaPagamentosLazy(FolhaPagamento FolhaPagamento) {
		return FolhaPagamentoDao.getInstance().folhaPagamentosLazy(FolhaPagamento);
	}

	public List<FolhaPagamento> folhaPagamentosComplete(String FolhaPagamentoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return FolhaPagamentoDao.getInstance().folhaPagamentos(FolhaPagamentoName);
	}

	public List<FolhaPagamento> list() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return FolhaPagamentoDao.getInstance().folhaPagamentos();
	}

	public String geradorDeProtocolo() throws SessaoNaoEncontradaParaEntidadeFornecidaException, NumberFormatException {
		return FolhaPagamentoDao.getInstance().geradorDeProtocolo();
	}

	public StringBuilder dadosFiltro() {
		return FolhaPagamentoDao.getInstance().getDadosFiltro();
	}

	public List<FolhaPagamento> folhaPagamentoListReport(FolhaPagamento folhaPagamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return FolhaPagamentoDao.getInstance().folhaPagamentoListReport(folhaPagamento);
	}

	public void validation(FolhaPagamento folhaPagamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		FolhaPagamento c = findByFolha(folhaPagamento);
		if (c != null) {
			if (!c.equals(folhaPagamento) && c.getReferencia().equals(folhaPagamento.getReferencia())
					&& c.getTipoFolhaPagamento() == TipoFolhaPagamento.MENSAL) {
				throw new ObjetoExistenteException(
						"Já existe uma Folha de Pagamento MENSAL para a referência: '" + c.getReferencia() + "!");
			} else if (!c.equals(folhaPagamento) && c.getReferencia().equals(folhaPagamento.getReferencia())
					&& c.getTipoFolhaPagamento() == TipoFolhaPagamento.QUINZENA1
					&& (folhaPagamento.getTipoFolhaPagamento() == TipoFolhaPagamento.QUINZENA1
							|| folhaPagamento.getTipoFolhaPagamento() == TipoFolhaPagamento.MENSAL)) {
				throw new ObjetoExistenteException("Você precisa selecionar a Quinzena 2 para a referência: '"
						+ c.getReferencia() + ", pois já existe uma folha cadastrada com o tipo: "
						+ c.getTipoFolhaPagamento() + "!");
			} else if (!c.equals(folhaPagamento) && c.getReferencia().equals(folhaPagamento.getReferencia())
					&& c.getTipoFolhaPagamento() == TipoFolhaPagamento.QUINZENA2
					&& folhaPagamento.getTipoFolhaPagamento() == TipoFolhaPagamento.MENSAL) {
				throw new ObjetoExistenteException(
						"Já existe uma Folha de Pagamento MENSAL para a referência: '" + c.getReferencia() + "!");
			}
		}

		if (c == null) {
			if (folhaPagamento.getTipoFolhaPagamento() == TipoFolhaPagamento.QUINZENA2) {
				throw new ObjetoExistenteException(
						"Folha de Pagamento da Quinzena 1 não foi encontrado para a referência: '"
								+ folhaPagamento.getReferencia() + "!");
			}
		}
	}

	public FolhaPagamento findByFolha(FolhaPagamento c) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findByAttribute(FolhaPagamento.class, "referencia", c.getReferencia());
	}

}