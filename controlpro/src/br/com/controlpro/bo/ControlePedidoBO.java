package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ControlePedidoDao;
import br.com.controlpro.entity.ControlePedido;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ControlePedidoBO {

	public static ControlePedidoBO getInstance() {
		return new ControlePedidoBO();
	}

	public void save(ControlePedido ControlePedido) throws ViolacaoChaveHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		validation(ControlePedido);
		GenericDao.save(ControlePedido);
	}

	public void update(ControlePedido ControlePedido)
			throws ViolacaoChaveHibernateException, ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		GenericDao.update(ControlePedido);
	}

	public ControlePedido find(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ControlePedido.class, id);
	}

	public LazyEntityDataModel<ControlePedido> controlePedidoLazy(ControlePedido ControlePedido) {
		return ControlePedidoDao.getInstance().controlePedidoLazy(ControlePedido);
	}

	public List<ControlePedido> controlePedidoComplete(String ControlePedidoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ControlePedidoDao.getInstance().controlesPedidos(ControlePedidoName);
	}

	public String geradorDeProtocolo() throws SessaoNaoEncontradaParaEntidadeFornecidaException, NumberFormatException {
		return ControlePedidoDao.getInstance().geradorDeProtocolo();
	}
	public String geradorDeProtocoloGlamix() throws SessaoNaoEncontradaParaEntidadeFornecidaException, NumberFormatException {
		return ControlePedidoDao.getInstance().geradorDeProtocoloGlamix();
	}

	public List<ControlePedido> list() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ControlePedidoDao.getInstance().controlesPedidos();
	}

	public StringBuilder dadosFiltro() {
		return ControlePedidoDao.getInstance().getDadosFiltro();
	}

	public List<ControlePedido> controlePedidoListReport(ControlePedido conferencia, boolean glamix)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ControlePedidoDao.getInstance().controlePedidoListReport(conferencia,glamix);
	}

	public void validation(ControlePedido controle)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		ControlePedido c = findByVenda(controle);
		if (c != null) {
			if (!c.equals(controle) && c.getSequenciaVenda().equals(controle.getSequenciaVenda())) {
				throw new ObjetoExistenteException("Já existe um controle de pedido cadastrado para essa referência: '"
						+ controle.getSequenciaVenda() + "!");
			}
		}
	}

	public ControlePedido findByVenda(ControlePedido c) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findByAttribute(ControlePedido.class, "sequenciaVenda", c.getSequenciaVenda());
	}

}