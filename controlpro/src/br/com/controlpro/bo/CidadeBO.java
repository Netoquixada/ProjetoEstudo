package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.CidadeDao;
import br.com.controlpro.entity.consultas.Cidade;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class CidadeBO {

	public static CidadeBO getInstance() {
			return new CidadeBO();
	}

	public List<Cidade> cidadesComplete(String CidadeName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CidadeDao.getInstance().cidades(CidadeName);
	}

	public List<Cidade> cidadesPorEstado(String uf)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CidadeDao.getInstance().cidadesPorEstado(uf);
	}
	public List<Cidade> cidadesDoCeara()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CidadeDao.getInstance().cidadesDoCeara();
	}
	
	public List<String> cidadesVenda(){
		return CidadeDao.getInstance().cidadesVenda();
	}

	public List<Cidade> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CidadeDao.getInstance().cidades();
	}
	

	public void validation(Cidade cidade) {

	}

}