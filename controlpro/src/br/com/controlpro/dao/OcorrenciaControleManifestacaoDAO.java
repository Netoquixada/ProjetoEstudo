package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ControleManifestacao;
import br.com.controlpro.entity.OcorrenciaControleManifestacao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class OcorrenciaControleManifestacaoDAO {

	private OcorrenciaControleManifestacaoDAO() {
	}

	public static OcorrenciaControleManifestacaoDAO getInstance() {
		return new OcorrenciaControleManifestacaoDAO();
	}

	public List<OcorrenciaControleManifestacao> ocorrenciaPorControleManifestacao(ControleManifestacao ordem)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (ordem != null) {
			restrictions.add(Restrictions.eq("controleManifestacao", ordem));
		}

		return GenericDao.findAllByAttributesRestrictions(OcorrenciaControleManifestacao.class,
				null, true, restrictions);
	}
}
