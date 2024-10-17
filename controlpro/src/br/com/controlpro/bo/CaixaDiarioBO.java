package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.CaixaDiarioDao;
import br.com.controlpro.entity.CaixaDiario;
import br.com.controlpro.entity.consultas.Vendas;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.QuantidadeEnvioInvalidaException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class CaixaDiarioBO {

	public static CaixaDiarioBO getInstance() {
		return new CaixaDiarioBO();
	}

	public void save(CaixaDiario CaixaDiario) throws ViolacaoChaveHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		validation(CaixaDiario);
		GenericDao.save(CaixaDiario);
	}

	public void update(CaixaDiario CaixaDiario) throws ViolacaoChaveHibernateException, ObjetoNaoEncontradoHibernateException,
			ValidacaoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		GenericDao.update(CaixaDiario);
	}

	public CaixaDiario find(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(CaixaDiario.class, id);
	}
	
	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumberFormatException {
		return CaixaDiarioDao.getInstance().geradorDeProtocolo();
	}

	public List<CaixaDiario> caixaDiariosComplete(String CaixaDiarioName) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CaixaDiarioDao.getInstance().caixaDiarios(CaixaDiarioName);
	}

	public List<CaixaDiario> list() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CaixaDiarioDao.getInstance().caixaDiarios();
	}

	public StringBuilder dadosFiltro() {
		return CaixaDiarioDao.getInstance().getDadosFiltro();
	}

	public List<CaixaDiario> caixaDiarioListReport(CaixaDiario caixaDiario) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CaixaDiarioDao.getInstance().caixaDiarioListReport(caixaDiario);
	}

	public void validation(CaixaDiario caixaDiario) {

	}
	
	public void validarNumeroPedido(String numero)
			throws QuantidadeEnvioInvalidaException {

		try {
			List<Vendas> lista = VendasBO.getInstance().vendaPorSequencia(numero);
			if(lista.size() == 0) {
				throw new QuantidadeEnvioInvalidaException(
						"Numero de pedido inválido!!");
			}
			
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

}