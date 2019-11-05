package com.moraes.igrejaservice.api.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.moraes.igrejaservice.api.model.Endereco;
import com.moraes.igrejaservice.api.model.Membro;
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
public class MembroDto implements Serializable, IMembro {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;
	
	private String cpf;
	
	private String telefone;
	
	private String email;
	
	private Boolean sexo; //true == M | false == F
	
	private SituacaoMembroEnum situacao;

	private TipoMembroEnum tipo;
	
	private Date dataNascimento;
	
	private Date dataInclusao;
	
	private Endereco endereco;
	
	public MembroDto(Membro obj) {
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
		MembroDto other = (MembroDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
