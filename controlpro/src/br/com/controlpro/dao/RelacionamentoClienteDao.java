package br.com.controlpro.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.com.controlpro.entity.RelacionamentoCliente;
import br.hibernateutil.core.SuperHibernate;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class RelacionamentoClienteDao {

	private RelacionamentoClienteDao() {
	}

	public static RelacionamentoClienteDao getInstance() {
		return new RelacionamentoClienteDao();
	}

	public List<RelacionamentoCliente> relacionamentoClienteLista() {
		Session session = null;

		String sql = "select \r\n" + 
				"	Cli_For.Codigo,\r\n" + 
				"	Cli_For.Nome,\r\n" + 
				"	Cli_For.Cidade,\r\n" + 
				"	Cli_For.Bairro,\r\n" + 
				"	Cli_For.Endereco,\r\n" + 
				"	Cli_For.Fone_1,\r\n" + 
				"	sum(Recebe_Dinheiro + Recebe_Cartao_Credito + Recebe_Cartao_Debito + Recebe_Banco_Valor) as Valor,\r\n" + 
				"	case \r\n" + 
				"		when (select sum(valor_resgate) from CONTROL_RESGATE where cliente_Codigo = Saidas.Cliente) is null\r\n" + 
				"		then 0\r\n" + 
				"		else (select sum(valor_resgate) from CONTROL_RESGATE where cliente_Codigo = Saidas.Cliente)\r\n" + 
				"	end as Resgatado,\r\n" + 
				"	case \r\n" + 
				"		when sum(Recebe_Dinheiro + Recebe_Cartao_Credito + Recebe_Cartao_Debito + Recebe_Banco_Valor) - \r\n" + 
				"		(select sum(valor_resgate) from CONTROL_RESGATE where cliente_Codigo = Saidas.Cliente) is null\r\n" + 
				"		then sum(Recebe_Dinheiro + Recebe_Cartao_Credito + Recebe_Cartao_Debito + Recebe_Banco_Valor)\r\n" + 
				"		else sum(Recebe_Dinheiro + Recebe_Cartao_Credito + Recebe_Cartao_Debito + Recebe_Banco_Valor) - \r\n" + 
				"		(select sum(valor_resgate) from CONTROL_RESGATE where cliente_Codigo = Saidas.Cliente)\r\n" + 
				"	end as Saldo,\r\n" + 
				"	(select top 1 Data from Saidas where Cliente = Cli_For.Codigo order by Data desc) as Ultima_Compra\r\n" + 
				" from Cli_For\r\n" + 
				"	inner join Saidas\r\n" + 
				"		on Saidas.Cliente = Cli_For.Codigo\r\n" + 
				" where \r\n" + 
				"	Saidas.Efetivada_Financ = 1 \r\n" + 
				" group by \r\n" + 
				"	Saidas.Cliente,\r\n" + 
				"	Cli_For.Codigo,\r\n" + 
				"	Cli_For.Nome,\r\n" + 
				"	Cli_For.Cidade,\r\n" + 
				"	Cli_For.Fone_1,\r\n" + 
				"	Cli_For.Bairro,\r\n" + 
				"	Cli_For.endereco\r\n" + 
				"" + 
				"";
		
		List<RelacionamentoCliente> lista = new ArrayList<>();

		try {
			session = SuperHibernate.getHibernateInstance().getSession(Session.class);
			SQLQuery query = session.createSQLQuery(sql);
//			query.setParameter(0, filter.getDataInicioFilter());
//			query.setParameter(1, filter.getDataFimFilter());

			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

			@SuppressWarnings("rawtypes")
			List data = query.list();
			for (Object o : data) {
				@SuppressWarnings("rawtypes")
				Map map = (Map) o;
				lista.add(new RelacionamentoCliente(
						(Integer) new BigDecimal(map.get("Codigo").toString()).intValue(),
						(String) map.get("Nome"), 
						(String) map.get("Cidade"), 
						(String) map.get("Bairro"), 
						(String) map.get("Endereco"), 
						(String) map.get("Fone_1"), 
						new BigDecimal(map.get("Valor").toString()),
						new BigDecimal(getResgatado(map.get("Resgatado").toString())),
						new BigDecimal(getResgatado(map.get("Saldo").toString())),
						(Date) (map.get("Ultima_Compra"))
						));
			}

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return lista;
	}
	
	public String getResgatado(String parametro) {
		if(parametro == null) {
			return "0";
		}else {
			return parametro;
		}
	}
	
	
	
	
	
}
