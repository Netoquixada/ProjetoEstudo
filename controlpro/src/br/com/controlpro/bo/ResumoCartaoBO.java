package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ResumoCartaoDAO;
import br.com.controlpro.entity.consultas.ResumoCartao;
import br.com.controlpro.entity.consultas.ResumoCartaoTotal;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ResumoCartaoBO {

	private ResumoCartaoBO() {
	}

	public static ResumoCartaoBO getInstance() {
		return new ResumoCartaoBO();
	}

	public List<ResumoCartao> resumoCartao(ResumoCartao filter)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ResumoCartaoDAO.getInstance().resumoCartao(filter);
	}
	public List<ResumoCartaoTotal> resumoCartaoTotal(ResumoCartao filter)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ResumoCartaoDAO.getInstance().resumoCartaoTotal(filter);
	}

}
