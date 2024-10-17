package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.RowEditEvent;

import com.mysql.jdbc.Util;

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.GradeOrdemBO;
import br.com.controlpro.bo.HistoricoCorteBO;
import br.com.controlpro.bo.ItemEnfestoCorteBO;
import br.com.controlpro.bo.MateriaPrimaBO;
import br.com.controlpro.bo.OrdemCorteBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.SituacaoOrdem;
import br.com.controlpro.entity.GradeOrdem;
import br.com.controlpro.entity.HistoricoCorte;
import br.com.controlpro.entity.ItemEnfestoCorte;
import br.com.controlpro.entity.MateriaPrima;
import br.com.controlpro.entity.OrdemCorte;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.BuscaNoWebContent;
import br.com.controlpro.util.CalcDias;
import br.com.controlpro.util.DataUtil;
import br.com.controlpro.util.ManagedBeanHelper;
import br.com.controlpro.util.RecuperarObjetoViaRequisicao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@ViewScoped
public class OrdemCorteBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarOrdemCorte.xhtml";
	private final String LIST = "/private/lista/listarOrdemCorte.xhtml?faces-redirect=true";

	private OrdemCorte ordemCorte;
	private OrdemCorte ordemCorteFilter;
	private List<OrdemCorte> ordemCortes;

	private GradeOrdem gradeOrdem;
	GradeOrdem gradeOrdemTemp;
	private List<GradeOrdem> gradeOrdemList;
	private List<GradeOrdem> gradeOrdemListAux;

	private ItemEnfestoCorte itemEnfestoCorte;
	private List<ItemEnfestoCorte> itemEnfestoCorteList;
	private List<ItemEnfestoCorte> itemEnfestoCorteListAux;

	private List<HistoricoCorte> historicoList;
	private Usuario usuarioLogado;

	@PostConstruct
	public void init() {
		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		ordemCorte = new OrdemCorte();
		ordemCorteFilter = new OrdemCorte();
		try {
			ordemCortes = OrdemCorteBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		gradeOrdem = new GradeOrdem();
		gradeOrdemTemp = new GradeOrdem();
		gradeOrdemList = new ArrayList<GradeOrdem>();
		gradeOrdemListAux = new ArrayList<GradeOrdem>();

		itemEnfestoCorte = new ItemEnfestoCorte();
		itemEnfestoCorteList = new ArrayList<>();
		itemEnfestoCorteListAux = new ArrayList<>();

		historicoList = new ArrayList<HistoricoCorte>();

		OrdemCorte aux = RecuperarObjetoViaRequisicao.getObjeto(OrdemCorte.class, "id");
		ordemCorte = aux != null ? aux : new OrdemCorte();

		// CASO SEJA EDIÇÃO DE DADOS
		if (ordemCorte.getId() != null) {
			try {
				gradeOrdemList = GradeOrdemBO.getInstance().gradeOrdemPorOrdemCorte(ordemCorte);
				historicoList = HistoricoCorteBO.getInstance().historicoCorte(ordemCorte);
				itemEnfestoCorteList = ItemEnfestoCorteBO.getInstance().itemEnfestoCortePorOrdemCorte(ordemCorte);
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
		}
	}

	public String save() {
		try {
			String protocolo = formataProtocolo(ordemCorte);
			ordemCorte.setProtocolo(protocolo);
			ordemCorte.setSituacao(SituacaoOrdem.ANDAMENTO);
			ordemCorte.setGrades(gradeOrdemList);
			OrdemCorteBO.getInstance().save(ordemCorte);

			for (int i = 0; i < gradeOrdemList.size(); i++) {
				gradeOrdemList.get(i).setOrdemCorte(ordemCorte);
			}

			if (!gradeOrdemList.isEmpty()) {
				GradeOrdemBO.getInstance().mergeAll(gradeOrdemList);
			}

			for (int i = 0; i < itemEnfestoCorteList.size(); i++) {
				itemEnfestoCorteList.get(i).setOrdemCorte(ordemCorte);

				itemEnfestoCorteList.get(i).getMateriaPrima().setSaldo(itemEnfestoCorteList.get(i).getMateriaPrima()
						.getSaldo().subtract(itemEnfestoCorteList.get(i).getTotal()));
				MateriaPrimaBO.getInstance().update(itemEnfestoCorteList.get(i).getMateriaPrima());
			}

			if (!itemEnfestoCorteList.isEmpty()) {
				ItemEnfestoCorteBO.getInstance().mergeAll(itemEnfestoCorteList);
			}

			addInfoMessage("Cadastrado com sucesso!", "Protocolo: " + ordemCorte.getProtocolo() + " - "
					+ ordemCorte.getSituacao() + " - " + DataUtil.formatarData(ordemCorte.getDataCadastro()));
			ordemCorte = new OrdemCorte();
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario", "erro.salvar.entidade.campo.existente");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ListaVaziaException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
		return CAD_EDIT;
	}

	public String update() {
		try {
			ordemCorte.setGrades(gradeOrdemList);
			OrdemCorteBO.getInstance().update(ordemCorte);

			for (int i = 0; i < gradeOrdemList.size(); i++) {
				gradeOrdemList.get(i).setOrdemCorte(ordemCorte);
			}

			if (!gradeOrdemList.isEmpty()) {
				GradeOrdemBO.getInstance().mergeAll(gradeOrdemList);
			}

			if (!gradeOrdemListAux.isEmpty()) {
				GradeOrdemBO.getInstance().deleteAll(gradeOrdemListAux);
			}

			for (int i = 0; i < itemEnfestoCorteList.size(); i++) {
				itemEnfestoCorteList.get(i).setOrdemCorte(ordemCorte);

				MateriaPrima materia = itemEnfestoCorteList.get(i).getMateriaPrima();

				if (itemEnfestoCorteList.get(i).getDebitarSaldo() == true) {
					// Debitando o saldo da materia prima
					materia.setSaldo(materia.getSaldo().subtract(itemEnfestoCorteList.get(i).getTotal()));
					MateriaPrimaBO.getInstance().update(materia);
				}

			}

			if (!itemEnfestoCorteList.isEmpty()) {
				ItemEnfestoCorteBO.getInstance().mergeAll(itemEnfestoCorteList);
			}

			// Atualizando o Saldo (DECREMENTO)
			for (int i = 0; i < itemEnfestoCorteListAux.size(); i++) {
				itemEnfestoCorteListAux.get(i).getMateriaPrima().setSaldo(itemEnfestoCorteListAux.get(i)
						.getMateriaPrima().getSaldo().add(itemEnfestoCorteListAux.get(i).getTotal()));
				MateriaPrimaBO.getInstance().update(itemEnfestoCorteListAux.get(i).getMateriaPrima());
			}

			if (!itemEnfestoCorteListAux.isEmpty()) {
				ItemEnfestoCorteBO.getInstance().deleteAll(itemEnfestoCorteListAux);
			}
			addInfoMessage("Editado com sucesso!", "");
		} catch (ViolacaoChaveHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ListaVaziaException e) {
			e.printStackTrace();
		} catch (IntegridadeObjetoHibernateException e) {
			e.printStackTrace();
		}
		ordemCorte = new OrdemCorte();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public Long getTotal() {
		Long acumulador = 0L;
		for (GradeOrdem item : gradeOrdemList) {
			acumulador = acumulador + item.getQuantidade();
		}
		return acumulador;
	}

	public Loja[] getLojas() {
		return Loja.values();
	}

	public Long getTotalPorOrdem(OrdemCorte ordem) {
		Long acumulador = 0L;
		try {
			gradeOrdemList = GradeOrdemBO.getInstance().gradeOrdemPorOrdemCorte(ordem);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}

		for (GradeOrdem item : gradeOrdemList) {
			acumulador = acumulador + item.getQuantidade();
		}
		return acumulador;
	}

	public void finalizarOrdem() {

		try {
			ordemCorte.setSituacao(SituacaoOrdem.FINALIZADA);
			OrdemCorteBO.getInstance().update(ordemCorte);
			addInfoMessage("Finalizada com sucesso!", "");
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		}
	}

	public void cancelarOrdem() {

		try {
			ordemCorte.setSituacao(SituacaoOrdem.CANCELADA);
			ordemCorte.setStatus(false);
			ordemCorte.setDataCancelamento(new Date());

			OrdemCorteBO.getInstance().update(ordemCorte);

			for (int i = 0; i < gradeOrdemList.size(); i++) {
				gradeOrdemList.get(i).setOrdemCorte(ordemCorte);
				gradeOrdemList.get(i).setStatus(false);
				;
			}

			if (!gradeOrdemList.isEmpty()) {
				GradeOrdemBO.getInstance().mergeAll(gradeOrdemList);
			}

			addInfoMessage("Ordem de Corte: " + ordemCorte.getProtocolo() + " cancelada com sucesso!", "");
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		} catch (ListaVaziaException e) {
			e.printStackTrace();
		}
	}

	public String gerarPDFOrdemCorte() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<OrdemCorte> ordensCorte = new ArrayList<OrdemCorte>();

		ordensCorte.add(ordemCorte);

		carregarListaGradeOrdem();

		mapa.put("logo", BuscaNoWebContent.buscaArquivo("/resources/imagens/logo-diva.jpg"));
		mapa.put("ordem-corte-itens", gradeOrdemList);
		mapa.put("ordem-corte-historico", historicoList);
		mapa.put("ordem-corte-enfesto", itemEnfestoCorteList);

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, ordensCorte, "ordem-corte",
				"Ordem de corte - " + ordemCorte.getProtocolo(), true);
		return null;
	}

	public void gerarPDF(ActionEvent ev) {
		try {
			Map<String, Object> mapa = new HashMap<String, Object>();

			List<OrdemCorte> ordens = OrdemCorteBO.getInstance().ordemCorteListReport(ordemCorteFilter);

			for (int i = 0; i < ordens.size(); i++) {
				ordens.get(i).setQuantidadeEnviado(getTotalPorOrdem(ordens.get(i)));
			}

			ordemCorteFilter = new OrdemCorte();
			mapa.put("logo", BuscaNoWebContent.buscaArquivo("/resources/imagens/logo-diva.jpg"));
			mapa.put("filtro", OrdemCorteBO.getInstance().dadosFiltro());

			GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, ordens, "ordem-corte-lista", "Arquivo", true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public boolean isAtrasado(OrdemCorte ordem) {
		if (ordem.getPrevisaoTermino().before(new Date()) && ordem.getSituacao().getDescricao() == "Andamento") {
			return true;
		} else {
			return false;
		}
	}

	public SituacaoOrdem[] getSituacoes() {
		return SituacaoOrdem.values();
	}

	public void clearGradeOrdem() {
		gradeOrdem = new GradeOrdem();
	}

	public void clearItemEnfesto() {
		itemEnfestoCorte = new ItemEnfestoCorte();
	}

	public String list() {
		try {
			ordemCortes = OrdemCorteBO.getInstance().ordemCorteListReport(ordemCorteFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String goToList() {
		ordemCorte = new OrdemCorte();
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		ordemCorte = new OrdemCorte();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (ordemCorte.getStatus()) {
				ordemCorte.setStatus(false);
				OrdemCorteBO.getInstance().update(ordemCorte);
			} else {
				ordemCorte.setStatus(true);
				OrdemCorteBO.getInstance().update(ordemCorte);
			}
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro!", "Erro: " + e.getMessage());
			e.printStackTrace();
		}
		String status = ordemCorte.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O ordemCorte está " + status);
		return null;
	}

	public String removerGradeOrdem() {
		try {
			for (int i = 0; i < gradeOrdemList.size(); i++) {
				if (gradeOrdemList.get(i) == gradeOrdem) {
					gradeOrdemListAux.add(gradeOrdemList.get(i));
					gradeOrdemList.remove(i);
					addWarnMessage("Grade removida com sucesso!", "");
					break;
				}
			}
			gradeOrdem = new GradeOrdem();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	public String addGradeOrdem() {
		try {
			gradeOrdemList.add(gradeOrdem);
			addInfoMessage("Grade adicionada com sucesso!", "");
			gradeOrdem = new GradeOrdem();
		} catch (Exception e) {
			addWarnMessage("Atenção", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String updateGradeOrdem() {
		try {
			for (int i = 0; i < gradeOrdemList.size(); i++) {
				if (gradeOrdemList.get(i) == gradeOrdem) {
					gradeOrdemList.set(i, gradeOrdem);
					addInfoMessage("Grade atualizada com sucesso!", "");
					break;
				}
			}
			gradeOrdem = new GradeOrdem();
		} catch (Exception e) {
			addErrorMessage("Erro!", e.getMessage());
		}
		return null;
	}

	public String removerItemEnfesto() {
		try {
			for (int i = 0; i < itemEnfestoCorteList.size(); i++) {
				if (itemEnfestoCorteList.get(i) == itemEnfestoCorte) {
					itemEnfestoCorteListAux.add(itemEnfestoCorteList.get(i));
					itemEnfestoCorteList.remove(i);
					addWarnMessage("Enfesto removido com sucesso!", "");
					break;
				}
			}
			itemEnfestoCorte = new ItemEnfestoCorte();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	public String addItemEnfesto() {
		try {
			itemEnfestoCorteList.add(itemEnfestoCorte);
			addInfoMessage("Item Enfesto adicionado com sucesso!", "");
			itemEnfestoCorte = new ItemEnfestoCorte();
		} catch (Exception e) {
			addWarnMessage("Atenção", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String updateItemEnfesto() {
		try {
			for (int i = 0; i < itemEnfestoCorteList.size(); i++) {
				if (itemEnfestoCorteList.get(i) == itemEnfestoCorte) {
					itemEnfestoCorteList.set(i, itemEnfestoCorte);
					addInfoMessage("Item Enfesto atualizado com sucesso!", "");
					break;
				}
			}
			itemEnfestoCorte = new ItemEnfestoCorte();
		} catch (Exception e) {
			addErrorMessage("Erro!", e.getMessage());
		}
		return null;
	}

	public void atualizarQtdDias() {
		ordemCorte.setQtdDias((int) CalcDias.calDias(ordemCorte.getDataCadastro(), ordemCorte.getPrevisaoTermino()));
	}

	public String formataProtocolo(OrdemCorte ordemCorte)
			throws NumberFormatException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		String res = "OC" + OrdemCorteBO.getInstance().geradorDeProtocolo() + "-"
				+ new SimpleDateFormat("MM/yyyy").format(ordemCorte.getDataCadastro());
		return res;
	}

	public void carregarListaGradeOrdem() {
		try {
			gradeOrdemList = GradeOrdemBO.getInstance().gradeOrdemPorOrdemCorte(ordemCorte);
			historicoList = HistoricoCorteBO.getInstance().historicoCorte(ordemCorte);
			itemEnfestoCorteList = ItemEnfestoCorteBO.getInstance().itemEnfestoCortePorOrdemCorte(ordemCorte);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public String onRowEdit(RowEditEvent event) {

		String enderecoRetorno = "";
		boolean finalizarOrdem = true;

		gradeOrdemTemp = ((GradeOrdem) event.getObject());
		try {
			if (gradeOrdemTemp.getProntasAux() > gradeOrdemTemp.getFaltaAux()
					&& gradeOrdemTemp.getJustificativaAux().isEmpty()) {
				// addWarnMessage("Atenção!",
				// "Falta apenas: " + gradeOrdemTemp.getFaltaAux() + " peças a serem
				// cortadas.");
				addWarnMessage("Atenção!",
						"Quantidade informada é supeior ao que falta. Por favor, informe uma justificativa.");
			} else {
				gradeOrdemTemp.setProntas(gradeOrdemTemp.getProntas() + gradeOrdemTemp.getProntasAux());
				GradeOrdemBO.getInstance().update(gradeOrdemTemp);
				addInfoMessage("Sucesso!", "Cortador: " + gradeOrdemTemp.getCortador().getNome());

				carregarListaGradeOrdem();

				for (GradeOrdem gradeOrdem : gradeOrdemList) {
					System.out.println("Entrei no for");
					if (gradeOrdem.getQuantidade() != gradeOrdem.getProntas()) {
						System.out.println("Entrei no if");
						finalizarOrdem = false;
						enderecoRetorno = "/private/lista/listarOrdemCorte.xhtml=faces-redirect=true";
					}
				}

				if (finalizarOrdem) {
					ordemCorte.setSituacao(SituacaoOrdem.FINALIZADA);
					OrdemCorteBO.getInstance().update(ordemCorte);
				}

				insertHistoricoCorte(gradeOrdemTemp);
			}
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		}
		gradeOrdemTemp = new GradeOrdem();
		AfterRedirect.manterMensagem();
		return enderecoRetorno;
	}

	private void insertHistoricoCorte(GradeOrdem gradeAux) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {

		HistoricoCorte historico = new HistoricoCorte();
		historico.setUsuario(usuarioLogado);
		historico.setCortador(gradeAux.getCortador());
		historico.setGrade(gradeAux.getGrade());
		historico.setProduto(gradeAux.getProduto());
		historico.setQtdRecebido(gradeAux.getProntasAux());
		historico.setDataCorte(gradeAux.getDataCorteAux());
		historico.setJustificativa(gradeAux.getJustificativaAux());
		historico.setOrdemCorte(ordemCorte);

		HistoricoCorteBO.getInstance().save(historico);
	}

	public OrdemCorte getOrdemCorte() {
		return ordemCorte;
	}

	public void setOrdemCorte(OrdemCorte ordemCorte) {
		this.ordemCorte = ordemCorte;
	}

	public OrdemCorte getOrdemCorteFilter() {
		return ordemCorteFilter;
	}

	public void setOrdemCorteFilter(OrdemCorte ordemCorteFilter) {
		this.ordemCorteFilter = ordemCorteFilter;
	}

	public List<OrdemCorte> getOrdemCortes() {
		return ordemCortes;
	}

	public void setOrdemCortes(List<OrdemCorte> ordemCortes) {
		this.ordemCortes = ordemCortes;
	}

	public GradeOrdem getGradeOrdem() {
		return gradeOrdem;
	}

	public void setGradeOrdem(GradeOrdem gradeOrdem) {
		this.gradeOrdem = gradeOrdem;
	}

	public List<GradeOrdem> getGradeOrdemList() {
		return gradeOrdemList;
	}

	public void setGradeOrdemList(List<GradeOrdem> gradeOrdemList) {
		this.gradeOrdemList = gradeOrdemList;
	}

	public List<GradeOrdem> getGradeOrdemListAux() {
		return gradeOrdemListAux;
	}

	public void setGradeOrdemListAux(List<GradeOrdem> gradeOrdemListAux) {
		this.gradeOrdemListAux = gradeOrdemListAux;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<HistoricoCorte> getHistoricoList() {
		return historicoList;
	}

	public void setHistoricoList(List<HistoricoCorte> historicoList) {
		this.historicoList = historicoList;
	}

	public ItemEnfestoCorte getItemEnfestoCorte() {
		return itemEnfestoCorte;
	}

	public void setItemEnfestoCorte(ItemEnfestoCorte itemEnfestoCorte) {
		this.itemEnfestoCorte = itemEnfestoCorte;
	}

	public List<ItemEnfestoCorte> getItemEnfestoCorteList() {
		return itemEnfestoCorteList;
	}

	public void setItemEnfestoCorteList(List<ItemEnfestoCorte> itemEnfestoCorteList) {
		this.itemEnfestoCorteList = itemEnfestoCorteList;
	}

	public List<ItemEnfestoCorte> getItemEnfestoCorteListAux() {
		return itemEnfestoCorteListAux;
	}

	public void setItemEnfestoCorteListAux(List<ItemEnfestoCorte> itemEnfestoCorteListAux) {
		this.itemEnfestoCorteListAux = itemEnfestoCorteListAux;
	}

}
