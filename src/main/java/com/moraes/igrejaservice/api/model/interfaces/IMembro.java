package com.moraes.igrejaservice.api.model.interfaces;

import java.util.Date;

import com.moraes.igrejaservice.api.model.Endereco;
import com.moraes.igrejaservice.api.model.enumeration.SituacaoMembroEnum;
import com.moraes.igrejaservice.api.model.enumeration.TipoMembroEnum;

/**
 * 
 * @author suleiman-am
 *
 */
public interface IMembro{

	Long getId();
	
	String getNome();
	
	String getCpf();
	
	String getTelefone();
	
	String getEmail();
	
	Boolean getSexo(); //true == M | false == F
	
	SituacaoMembroEnum getSituacao();

	TipoMembroEnum getTipo();
	
	Date getDataNascimento();
	
	Date getDataInclusao();
	
	Endereco getEndereco();
}
