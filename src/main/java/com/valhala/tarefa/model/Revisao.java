package com.valhala.tarefa.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import com.valhala.tarefa.revision.listeners.RevisaoListener;

/**
 * Classe que representa a tabela Revisao que guarda os detalhes de uma revisão.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 02/03/2014
 */
@Entity
@RevisionEntity(RevisaoListener.class)
@NamedQueries({
	@NamedQuery(name=Revisao.NAMEDQUERY_BUSCAR_TODOS, query="select r from Revisao r")
})
public class Revisao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	public static final String NAMEDQUERY_BUSCAR_TODOS = "buscarTodasRevisoes";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@RevisionNumber
	private Long codigoRevisao;
	
	@RevisionTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	private String autor;
	
	public Revisao() {
	}

	public Long getCodigoRevisao() {
		return codigoRevisao;
	}

	public void setCodigoRevisao(Long codigoRevisao) {
		this.codigoRevisao = codigoRevisao;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
} // fim da classe Revisao