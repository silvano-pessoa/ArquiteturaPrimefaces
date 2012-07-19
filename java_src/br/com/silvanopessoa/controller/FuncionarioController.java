package br.com.silvanopessoa.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.primefaces.event.data.SortEvent;
import org.springframework.stereotype.Controller;

import br.com.silvanopessoa.annotation.FlexViewScope;
import br.com.silvanopessoa.exception.java.ApplicationMessageException;
import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.model.dto.FuncionarioDTO;
import br.com.silvanopessoa.model.entity.Accesslist;
import br.com.silvanopessoa.model.entity.Funcionario;
import br.com.silvanopessoa.service.IAccesslistService;
import br.com.silvanopessoa.service.IFuncionarioService;
import br.com.silvanopessoa.service.ISessionService;
import br.com.silvanopessoa.util.FacesUtils;
import br.com.silvanopessoa.util.SecurityUtils;

@FlexViewScope
@Controller("funcionarioController")
public class FuncionarioController implements Serializable{

	/**************************************************************/
	/************************* ATRIBUTOS **************************/
	/**************************************************************/

	/** SERIAL VERSION UID */
	private static final long serialVersionUID = 1511819049943440048L;

	/********* SERVICE *********/
	@Resource
	private IFuncionarioService service;

	@Resource
	private ISessionService serviceSession;
	
	@Resource
	private IAccesslistService serviceAccess;
	
	/*********** VIEW **********/
	private FuncionarioDTO funcionarioSearch;
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	private List<Funcionario> funcionariosCmb;
	private Funcionario funcionarioSel;
	private Funcionario funcionarioSenha;
	private List<Accesslist> accesslists;
	
	private boolean disableDepartamentoSearch;
	private boolean disableDepartamento;
	private boolean disableSite;
	private int numSeq;
	
	/**************************************************************/
	/*********************** INICIALIZACAO ************************/
	/**************************************************************/
	
	/**
	 * 
	 */
	@PostConstruct
	public void init(){
			this.funcionarioSearch 		= new FuncionarioDTO();
			this.funcionario 				= new Funcionario();
			this.funcionarios 				= this.search();
			this.funcionarioSel 			= new Funcionario();	
			this.disableDepartamento		= true;
			this.disableDepartamentoSearch	= true;
			this.disableSite				= true;
			this.numSeq						= 1;
			
			Integer idFun	= serviceSession.getFuncionarioIdSession();
			
			try {
				this.funcionarioSenha			= service.findById(idFun);
			} catch (ApplicationMessageException e) {
				FacesUtils.error(e.getMessage());
			} 
	}
	
	/**************************************************************/
	/********************* REQUEST/RESPONSE ***********************/
	/***************************************************************/
	

	public List<Funcionario> search()  {
		try {
			Funcionario	searchFunc	= new Funcionario();
			
			searchFunc.setNome(this.funcionarioSearch.getNome());
			searchFunc.setAtivo(this.funcionarioSearch.getAtivo());
			
			this.funcionarios= service.find(searchFunc);
		}catch (ApplicationMessageException e) {
			FacesUtils.error(e.getMessage());
		}
		return this.funcionarios;
	}
	
	public void save(){
		try {
			this.funcionario.setNome(this.funcionario.getNome());
			this.service.save(this.funcionario);
			this.init();
			
			FacesUtils.infoI18n("app.generic.successSave");
		} catch (BusinessMessageException e) {
			FacesUtils.info(e.getMessage());
		}catch (ApplicationMessageException e) {
			FacesUtils.error(e.getMessage());
		}
	}
	
	public void delete(){
		try{
			this.service.delete(this.funcionario);
			this.init();
			
		}catch (BusinessMessageException e) {
			FacesUtils.info(e.getMessage());
		}catch (ApplicationMessageException e) {
			FacesUtils.error(e.getMessage());
		}
	}

	public void insert() {
		this.funcionario	= new Funcionario();
		this.funcionarioSel = new Funcionario();
		this.numSeq			= 1;
		FacesUtils.clearForm();
	}	

	public List<String> searchNome(String query) {	
		List<String> suggestions = new ArrayList<String>();
		
		try {
			List<Funcionario> funcionarios = this.service.findListByNome(query);
			
			if( funcionarios != null ) {
				for( Funcionario funcionario : funcionarios ) {
					suggestions.add( funcionario.getNome());
				}
			}

		} catch (Exception e) {
			FacesUtils.error(e.getMessage());
		}
		
		return suggestions;
	}
	
	public void selecionarFunc(){
		try {
			this.funcionario	= service.findById(getFuncionarioSel().getFunId());
		} catch (ApplicationMessageException e) {
			FacesUtils.error(e.getMessage());
		}	
		FacesUtils.clearForm();
	}


	public void saveSenha(){
		try {
			Integer idFun	= serviceSession.getFuncionarioIdSession();
			
			try {
				Funcionario oldFunc		= service.findById(idFun);
				
				if( !SecurityUtils.md5(this.funcionarioSenha.getSenha()).equals(oldFunc.getSenha()) ){
					FacesUtils.errorI18n("app.entity.funcionario.error.invalidPassword");
					return;
				}

				if( !this.funcionarioSenha.getValidNovaSenha().equals(this.funcionarioSenha.getNovaSenha()) ){
					FacesUtils.errorI18n("app.entity.funcionario.error.invalidNewPassword");
					return;
				}
				
			} catch (ApplicationMessageException e) {
				FacesUtils.error(e.getMessage());
			}
			
			this.service.save(this.funcionarioSenha);
			this.init();
			
			FacesUtils.infoI18n("app.generic.successSave");
		} catch (BusinessMessageException e) {
			FacesUtils.info(e.getMessage());
		}catch (ApplicationMessageException e) {
			FacesUtils.error(e.getMessage());
		}
	}
	
	public void onSort(SortEvent event) {
		
	}
	
	/**************************************************************/
	/************************** GET/SET ***************************/
	/**************************************************************/
	
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public FuncionarioDTO getFuncionarioSearch() {
		return funcionarioSearch;
	}

	public void setFuncionarioSearch(FuncionarioDTO funcionarioSearch) {
		this.funcionarioSearch = funcionarioSearch;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Funcionario getFuncionarioSel() {
		return funcionarioSel;
	}

	public void setFuncionarioSel(Funcionario funcionarioSel) {
		this.funcionarioSel = funcionarioSel;
	}

	public boolean isDisableDepartamento() {
		return disableDepartamento;
	}

	public void setDisableDepartamento(boolean disableDepartamento) {
		this.disableDepartamento = disableDepartamento;
	}


	public boolean isDisableDepartamentoSearch() {
		return disableDepartamentoSearch;
	}

	public void setDisableDepartamentoSearch(boolean disableDepartamentoSearch) {
		this.disableDepartamentoSearch = disableDepartamentoSearch;
	}

	public boolean isDisableSite() {
		return disableSite;
	}

	public void setDisableSite(boolean disableSite) {
		this.disableSite = disableSite;
	}


	public List<Funcionario> getFuncionariosCmb() {
		try {
			funcionariosCmb	= service.findAll();
		} catch (ApplicationMessageException e) {
			FacesUtils.error(e.getMessage());
		}
		
		return funcionariosCmb;
	}

	public void setFuncionariosCmb(List<Funcionario> funcionariosCmb) {
		this.funcionariosCmb = funcionariosCmb;
	}

	public int getNumSeq() {
		return numSeq;
	}

	public void setNumSeq(int numSeq) {
		this.numSeq = numSeq;
	}

	public Funcionario getFuncionarioSenha() {
		return funcionarioSenha;
	}

	public void setFuncionarioSenha(Funcionario funcionarioSenha) {
		this.funcionarioSenha = funcionarioSenha;
	}
	
	public List<Accesslist> getAccesslists() {
		try {
			accesslists	= serviceAccess.findAll();
		} catch (ApplicationMessageException e) {
			FacesUtils.error(e.getMessage());
		}
		
		return accesslists;
	}

	public void setAccesslists(List<Accesslist> accesslists) {
		this.accesslists = accesslists;
	}
	
}
