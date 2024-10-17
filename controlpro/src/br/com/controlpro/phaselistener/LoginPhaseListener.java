package br.com.controlpro.phaselistener;


import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.UsuarioBO;
import br.com.controlpro.util.ManagedBeanHelper;
import br.com.controlpro.util.Mensagens;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;


public class LoginPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 8403402696846718508L;

	/**
	 * Verifica se o usuario esta logado no sistema.
	 * 
	 * @param event
	 *            {@link PhaseEvent}
	 * @see PhaseListener#beforePhase(PhaseEvent)
	 */
	public void afterPhase(PhaseEvent event) {

		if (event.getFacesContext().getViewRoot().getViewId()
				.startsWith("/public/index.xhtml.")) {
			HttpSession httpSession = (HttpSession) event.getFacesContext()
					.getExternalContext().getSession(false);
			Mensagens.addErrorMessage("Atenção!",
					"O acesso foi expirado por conta de inatividade no sistema!");

			if (httpSession != null) {
				httpSession.invalidate();
			}
			navigateToView(event.getFacesContext(), "/public/index.xhtml");

		} else if (!event.getFacesContext().getViewRoot().getViewId()
				.contains("/public")
				&& ManagedBeanHelper.recuperaBean("escopoSessaoBean",
						EscopoSessaoBean.class).getUsuarioLogado() == null) {
			
			try {
				if(UsuarioBO.getInstance().list().isEmpty()){
					UsuarioBO.getInstance().criarPrimeiroUsuario();	
				}
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
			
			
			navigateToView(event.getFacesContext(), "/public/login.xhtml");
		
		}
	}

	/**
	 * Navega para um view.
	 * 
	 * @param context
	 *            Faces Context atrelado a requisizição atual.
	 * @param view
	 *            outcome para qual se deseja navegar
	 */
	private void navigateToView(FacesContext context, String view) {
		context.getApplication().getNavigationHandler()
				.handleNavigation(context, null, view);
	}

	/**
	 * Metodo da interface PhaseListener. Nao utilizado.
	 * 
	 * @param event
	 *            {@link PhaseEvent}
	 * @see PhaseListener#beforePhase(PhaseEvent)
	 */
	public void beforePhase(PhaseEvent event) {
	}

	/**
	 * A que fase queremos "escutar". No caso {@link PhaseId#RESTORE_VIEW}.
	 * 
	 * @return {@link PhaseId#RESTORE_VIEW}
	 * @see PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
