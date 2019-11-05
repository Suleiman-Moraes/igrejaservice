package com.moraes.igrejaservice.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.moraes.igrejaservice.api.model.dto.MembroDto;
import com.moraes.igrejaservice.api.model.enumeration.SituacaoMembroEnum;
import com.moraes.igrejaservice.api.model.enumeration.TipoMembroEnum;
import com.moraes.igrejaservice.api.model.interfaces.IMembro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author suleiman-am
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "membro")
public class Membro implements Serializable, IMembro {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String cpf;
	
	private String telefone;
	
	private String email;
	
	private Boolean sexo; //true == M | false == F
	
	@Enumerated(EnumType.STRING)
	private SituacaoMembroEnum situacao;

	@Enumerated(EnumType.STRING)
	private TipoMembroEnum tipo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_inclusao")
	private Date dataNascimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_inclusao")
	private Date dataInclusao;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;
	
	public Membro(MembroDto obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.telefone = obj.getTelefone();
		this.email = obj.getEmail();
		this.sexo = obj.getSexo();
		this.situacao = obj.getSituacao();
		this.tipo = obj.getTipo();
		this.dataNascimento = obj.getDataNascimento();
		this.dataInclusao = obj.getDataInclusao();
		this.endereco = obj.getEndereco();
	}
	
	@PrePersist
	public void prePersist() {
		this.dataInclusao = new Date();
		this.situacao = this.situacao != null ? this.situacao : SituacaoMembroEnum.ATIVO;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Membro other = (Membro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
