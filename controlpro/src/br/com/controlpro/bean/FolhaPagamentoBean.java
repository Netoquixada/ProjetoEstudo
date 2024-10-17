package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.FolhaPagamentoBO;
import br.com.controlpro.bo.ItemFolhaPagamentoBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.TipoFolhaPagamento;
import br.com.controlpro.entity.FolhaPagamento;
import br.com.controlpro.entity.ItemFolhaPagamento;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.BuscaNoWebContent;
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
public class FolhaPagamentoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarFolhaPagamento.xhtml";
	private final String LIST = "/private/lista/listarFolhaPagamento.xhtml?faces-redirect=true";

	private FolhaPagamento folhaPagamento;
	private FolhaPagamento folhaPagamentoFilter;
	private List<FolhaPagamento> folhaPagamentos;

	private ItemFolhaPagamento itemFolhaPagamento;
	ItemFolhaPagamento itemFolhaPagamentoTemp;
	private List<ItemFolhaPagamento> itemFolhaPagamentoList;
	private List<ItemFolhaPagamento> itemFolhaPagamentoListAux;

	private Usuario usuarioLogado;

	@PostConstruct
	public void init() {
		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		folhaPagamento = new FolhaPagamento();
		folhaPagamentoFilter = new FolhaPagamento();
		try {
			folhaPagamentos = FolhaPagamentoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		itemFolhaPagamento = new ItemFolhaPagamento();
		itemFolhaPagamentoTemp = new ItemFolhaPagamento();
		itemFolhaPagamentoList = new ArrayList<ItemFolhaPagamento>();
		itemFolhaPagamentoListAux = new ArrayList<ItemFolhaPagamento>();

		FolhaPagamento aux = RecuperarObjetoViaRequisicao.getObjeto(FolhaPagamento.class, "id");
		folhaPagamento = aux != null ? aux : new FolhaPagamento();

		// CASO SEJA EDIÇÃO DE DADOS
		if (folhaPagamento.getId() != null) {
			try {
				itemFolhaPagamentoList = ItemFolhaPagamentoBO.getInstance()
						.itemFolhaPagamentoPorFolhaPagamento(folhaPagamento);
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
		}
	}

	public String save() {
		try {
			String protocolo = formataProtocolo(folhaPagamento);
			folhaPagamento.setProtocolo(protocolo);
			folhaPagamento.setItensFolhaPagamento(itemFolhaPagamentoList);
			FolhaPagamentoBO.getInstance().save(folhaPagamento);

			for (int i = 0; i < itemFolhaPagamentoList.size(); i++) {
				itemFolhaPagamentoList.get(i).setFolhaPagamento(folhaPagamento);
			}

			if (!itemFolhaPagamentoList.isEmpty()) {
				ItemFolhaPagamentoBO.getInstance().mergeAll(itemFolhaPagamentoList);
			}

			addInfoMessage("Cadastrado com sucesso!", "Protocolo: " + folhaPagamento.getProtocolo() + " - "
					+ folhaPagamento.getReferencia() + " - " + DataUtil.formatarData(folhaPagamento.getDataCadastro()));
			folhaPagamento = new FolhaPagamento();
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
			folhaPagamento.setItensFolhaPagamento(itemFolhaPagamentoList);
			FolhaPagamentoBO.getInstance().update(folhaPagamento);

			for (int i = 0; i < itemFolhaPagamentoList.size(); i++) {
				itemFolhaPagamentoList.get(i).setFolhaPagamento(folhaPagamento);
			}

			if (!itemFolhaPagamentoList.isEmpty()) {
				ItemFolhaPagamentoBO.getInstance().mergeAll(itemFolhaPagamentoList);
			}

			if (!itemFolhaPagamentoListAux.isEmpty()) {
				ItemFolhaPagamentoBO.getInstance().deleteAll(itemFolhaPagamentoListAux);
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
		folhaPagamento = new FolhaPagamento();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	// public Long getTotal() {
	// Long acumulador = 0L;
	// for (ItemFolhaPagamento item : itemFolhaPagamentoList) {
	// acumulador = acumulador + item.getQuantidade();
	// }
	// return acumulador;
	// }

	public Loja[] getLojas() {
		return Loja.values();
	}

	public TipoFolhaPagamento[] getTiposFolhaPagamento() {
		return TipoFolhaPagamento.values();
	}

	public BigDecimal getSalarioReceberTotal() {
		BigDecimal salarioReceberTotal = new BigDecimal(0);

		for (ItemFolhaPagamento item : itemFolhaPagamentoList) {
			salarioReceberTotal = salarioReceberTotal.add(item.getSalarioReceber());
		}
		return salarioReceberTotal;
	}

	public String gerarPDFFolhaPagamento() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<FolhaPagamento> folha = new ArrayList<FolhaPagamento>();

		folha.add(folhaPagamento);

		carregarListaItemFolhaPagamento();

		// mapa.put("logo",
		// BuscaNoWebContent.buscaArquivo("/resources/imagens/logo-diva.jpg"));
		mapa.put("folha-pagamento-itens", itemFolhaPagamentoList);
		mapa.put("total", getSalarioReceberTotal());

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, folha, "folha-pagamento",
				"Folha de Pagamento - " + folhaPagamento.getProtocolo(), true);
		return null;
	}

	public void gerarPDF(ActionEvent ev) {
		try {
			Map<String, Object> mapa = new HashMap<String, Object>();

			List<FolhaPagamento> folhas = FolhaPagamentoBO.getInstance().folhaPagamentoListReport(folhaPagamentoFilter);

			folhaPagamentoFilter = new FolhaPagamento();

			GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, folhas, "folha-pagamento-lista",
					"Relatório de Folhas de Pagamento", true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public void clearItemFolhaPagamento() {
		itemFolhaPagamento = new ItemFolhaPagamento();
	}

	public String list() {
		try {
			folhaPagamentos = FolhaPagamentoBO.getInstance().folhaPagamentoListReport(folhaPagamentoFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String goToList() {
		folhaPagamento = new FolhaPagamento();
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		folhaPagamento = new FolhaPagamento();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (folhaPagamento.getStatus()) {
				folhaPagamento.setStatus(false);
				FolhaPagamentoBO.getInstance().update(folhaPagamento);
			} else {
				folhaPagamento.setStatus(true);
				FolhaPagamentoBO.getInstance().update(folhaPagamento);
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
		String status = folhaPagamento.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O folhaPagamento está " + status);
		return null;
	}

	public String removerItemFolhaPagamento() {
		try {
			for (int i = 0; i < itemFolhaPagamentoList.size(); i++) {
				if (itemFolhaPagamentoList.get(i) == itemFolhaPagamento) {
					itemFolhaPagamentoListAux.add(itemFolhaPagamentoList.get(i));
					itemFolhaPagamentoList.remove(i);
					addWarnMessage("Registro removido com sucesso!", "");
					break;
				}
			}
			itemFolhaPagamento = new ItemFolhaPagamento();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	public String atualizandoValores() {
		try {

			for (int i = 0; i < itemFolhaPagamentoList.size(); i++) {
				if (folhaPagamento.getTipoFolhaPagamento() == TipoFolhaPagamento.MENSAL) {
					itemFolhaPagamentoList.get(i)
							.setValor(itemFolhaPagamentoList.get(i).getFuncionario().getSalarioTotal());
					itemFolhaPagamentoList.get(i)
							.setSalarioReceber(itemFolhaPagamentoList.get(i).getValor()
									.add(itemFolhaPagamentoList.get(i).getHoraExtra())
									.add(itemFolhaPagamentoList.get(i).getFuncionario().getSalarioFamilia())
									.subtract(itemFolhaPagamentoList.get(i).getFuncionario().getInss())
									.subtract(itemFolhaPagamentoList.get(i).getOutrosDescontos()));
				} else if (folhaPagamento.getTipoFolhaPagamento() == TipoFolhaPagamento.QUINZENA1) {
					itemFolhaPagamentoList.get(i).setValor(
							itemFolhaPagamentoList.get(i).getFuncionario().getSalarioTotal().divide(new BigDecimal(2)));
					itemFolhaPagamentoList.get(i)
							.setSalarioReceber(itemFolhaPagamentoList.get(i).getValor()
									.add(itemFolhaPagamentoList.get(i).getHoraExtra())
									.subtract(itemFolhaPagamentoList.get(i).getOutrosDescontos()));
				} else if (folhaPagamento.getTipoFolhaPagamento() == TipoFolhaPagamento.QUINZENA2) {
					itemFolhaPagamentoList.get(i).setValor(
							itemFolhaPagamentoList.get(i).getFuncionario().getSalarioTotal().divide(new BigDecimal(2)));
					itemFolhaPagamentoList.get(i)
							.setSalarioReceber(itemFolhaPagamentoList.get(i).getValor()
									.add(itemFolhaPagamentoList.get(i).getHoraExtra())
									.add(itemFolhaPagamentoList.get(i).getFuncionario().getSalarioFamilia())
									.subtract(itemFolhaPagamentoList.get(i).getFuncionario().getInss())
									.subtract(itemFolhaPagamentoList.get(i).getOutrosDescontos()));
				}

			}

		} catch (Exception e) {
			addWarnMessage("Atenção", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String addItemFolhaPagamento() {
		try {
			if (folhaPagamento.getTipoFolhaPagamento() == TipoFolhaPagamento.MENSAL) {
				itemFolhaPagamento.setValor(itemFolhaPagamento.getFuncionario().getSalarioTotal());
				itemFolhaPagamento
						.setSalarioReceber(itemFolhaPagamento.getValor().add(itemFolhaPagamento.getHoraExtra())
								.add(itemFolhaPagamento.getFuncionario().getSalarioFamilia())
								.subtract(itemFolhaPagamento.getFuncionario().getInss())
								.subtract(itemFolhaPagamento.getOutrosDescontos()));
			} else if (this.getFolhaPagamento().getTipoFolhaPagamento() == TipoFolhaPagamento.QUINZENA1) {
				itemFolhaPagamento
						.setValor(itemFolhaPagamento.getFuncionario().getSalarioTotal().divide(new BigDecimal(2)));
				itemFolhaPagamento.setSalarioReceber(itemFolhaPagamento.getValor()
						.add(itemFolhaPagamento.getHoraExtra()).subtract(itemFolhaPagamento.getOutrosDescontos()));
			} else if (this.getFolhaPagamento().getTipoFolhaPagamento() == TipoFolhaPagamento.QUINZENA2) {
				itemFolhaPagamento
						.setValor(itemFolhaPagamento.getFuncionario().getSalarioTotal().divide(new BigDecimal(2)));
				itemFolhaPagamento
						.setSalarioReceber(itemFolhaPagamento.getValor().add(itemFolhaPagamento.getHoraExtra())
								.add(itemFolhaPagamento.getFuncionario().getSalarioFamilia())
								.subtract(itemFolhaPagamento.getFuncionario().getInss())
								.subtract(itemFolhaPagamento.getOutrosDescontos()));
			}

			itemFolhaPagamento.setSalarioFamilia(itemFolhaPagamento.getFuncionario().getSalarioFamilia());
			itemFolhaPagamento.setInss(itemFolhaPagamento.getFuncionario().getInss());
			itemFolhaPagamentoList.add(itemFolhaPagamento);
			addInfoMessage("Registro adicionado com sucesso!", "");
			itemFolhaPagamento = new ItemFolhaPagamento();
		} catch (Exception e) {
			addWarnMessage("Atenção", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String updateItemFolhaPagamento() {
		try {
			for (int i = 0; i < itemFolhaPagamentoList.size(); i++) {
				if (itemFolhaPagamentoList.get(i) == itemFolhaPagamento) {
					if (folhaPagamento.getTipoFolhaPagamento() == TipoFolhaPagamento.MENSAL) {
						itemFolhaPagamento.setValor(itemFolhaPagamento.getFuncionario().getSalarioTotal());
						itemFolhaPagamento.setSalarioReceber(itemFolhaPagamento.getValor()
								.add(itemFolhaPagamento.getHoraExtra()).add(itemFolhaPagamento.getSalarioFamilia())
								.subtract(itemFolhaPagamento.getFuncionario().getInss())
								.subtract(itemFolhaPagamento.getOutrosDescontos()));
					} else if (this.getFolhaPagamento().getTipoFolhaPagamento() == TipoFolhaPagamento.QUINZENA1) {
						itemFolhaPagamento.setValor(
								itemFolhaPagamento.getFuncionario().getSalarioTotal().divide(new BigDecimal(2)));
						itemFolhaPagamento
								.setSalarioReceber(itemFolhaPagamento.getValor().add(itemFolhaPagamento.getHoraExtra())
										.subtract(itemFolhaPagamento.getOutrosDescontos()));
					} else if (this.getFolhaPagamento().getTipoFolhaPagamento() == TipoFolhaPagamento.QUINZENA2) {
						itemFolhaPagamento.setValor(
								itemFolhaPagamento.getFuncionario().getSalarioTotal().divide(new BigDecimal(2)));
						itemFolhaPagamento.setSalarioReceber(itemFolhaPagamento.getValor()
								.add(itemFolhaPagamento.getHoraExtra()).add(itemFolhaPagamento.getSalarioFamilia())
								.subtract(itemFolhaPagamento.getFuncionario().getInss())
								.subtract(itemFolhaPagamento.getOutrosDescontos()));
					}

					itemFolhaPagamento.setSalarioFamilia(itemFolhaPagamento.getFuncionario().getSalarioFamilia());
					itemFolhaPagamento.setInss(itemFolhaPagamento.getFuncionario().getInss());
					itemFolhaPagamentoList.set(i, itemFolhaPagamento);
					addInfoMessage("Registro atualizado com sucesso!", "");
					break;
				}
			}
			itemFolhaPagamento = new ItemFolhaPagamento();
		} catch (Exception e) {
			addErrorMessage("Erro!", e.getMessage());
		}
		return null;
	}

	public String formataProtocolo(FolhaPagamento folhaPagamento)
			throws NumberFormatException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		String res = "FP" + FolhaPagamentoBO.getInstance().geradorDeProtocolo() + "-"
				+ new SimpleDateFormat("MM/yyyy").format(folhaPagamento.getDataCadastro());
		return res;
	}

	public void carregarListaItemFolhaPagamento() {
		try {
			itemFolhaPagamentoList = ItemFolhaPagamentoBO.getInstance()
					.itemFolhaPagamentoPorFolhaPagamento(folhaPagamento);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public FolhaPagamento getFolhaPagamento() {
		return folhaPagamento;
	}

	public void setFolhaPagamento(FolhaPagamento folhaPagamento) {
		this.folhaPagamento = folhaPagamento;
	}

	public FolhaPagamento getFolhaPagamentoFilter() {
		return folhaPagamentoFilter;
	}

	public void setFolhaPagamentoFilter(FolhaPagamento folhaPagamentoFilter) {
		this.folhaPagamentoFilter = folhaPagamentoFilter;
	}

	public List<FolhaPagamento> getFolhaPagamentos() {
		return folhaPagamentos;
	}

	public void setFolhaPagamentos(List<FolhaPagamento> folhaPagamentos) {
		this.folhaPagamentos = folhaPagamentos;
	}

	public ItemFolhaPagamento getItemFolhaPagamento() {
		return itemFolhaPagamento;
	}

	public void setItemFolhaPagamento(ItemFolhaPagamento itemFolhaPagamento) {
		this.itemFolhaPagamento = itemFolhaPagamento;
	}

	public List<ItemFolhaPagamento> getItemFolhaPagamentoList() {
		return itemFolhaPagamentoList;
	}

	public void setItemFolhaPagamentoList(List<ItemFolhaPagamento> itemFolhaPagamentoList) {
		this.itemFolhaPagamentoList = itemFolhaPagamentoList;
	}

	public List<ItemFolhaPagamento> getItemFolhaPagamentoListAux() {
		return itemFolhaPagamentoListAux;
	}

	public void setItemFolhaPagamentoListAux(List<ItemFolhaPagamento> itemFolhaPagamentoListAux) {
		this.itemFolhaPagamentoListAux = itemFolhaPagamentoListAux;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}