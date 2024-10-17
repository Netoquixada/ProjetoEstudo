package br.com.controlpro.bo;

import java.math.BigDecimal;

import br.com.controlpro.entity.GradeOrdem;
import br.com.controlpro.entity.consultas.Produto;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;


public class QueryUtilBO {

	public static QueryUtilBO getInstance() {
		return new QueryUtilBO();
	}
	
	public BigDecimal quantidadeCortadasPorProduto(Produto produto) {
		BigDecimal result =new BigDecimal(0.0);
		try {
			result = GenericDao.findEntityNotMappedBySQLQuery(BigDecimal.class,
					GradeOrdem.class,
					"select sum(prontas) from CONTROL_GRADE_ORDEM where status like 'T' and produto_id like '"
							+ produto.getId()+"'");
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		if (result == null)
			return new BigDecimal(0.0);
		return result;
	}
	public Integer quantidadeEnviadasPorProduto(Produto produto) {
		Integer result = 0;
		try {
			result = GenericDao.findEntityNotMappedBySQLQuery(Integer.class,
					GradeOrdem.class,
					"select sum(quantidade) from CONTROL_ITEM_PRODUCAO where status like 'T' and produto_id like '"
							+ produto.getId()+"'");
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		if (result == null)
			return 0;
		return result;
	}
	public Integer quantidadeRecebidasPorProduto(Produto produto) {
		Integer result = 0;
		try {
			result = GenericDao.findEntityNotMappedBySQLQuery(Integer.class,
					GradeOrdem.class,
					"select sum(prontas) from CONTROL_ITEM_PRODUCAO where  status like 'T' and produto_id like '"
							+ produto.getId()+"'");
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		if (result == null)
			return 0;
		return result;
	}

}