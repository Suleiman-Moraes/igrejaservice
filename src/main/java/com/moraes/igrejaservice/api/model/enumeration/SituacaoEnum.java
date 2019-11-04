package com.moraes.igrejaservice.api.model.enumeration;

import lombok.Getter;

@Getter
public enum SituacaoEnum {
	ATIVO("Ativo"),
	INATIVO("Inativo");
	
	private String descricao;
	
	private SituacaoEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public static SituacaoEnum get(String status) {
		SituacaoEnum retorno = SituacaoEnum.valueOf(status != null ? status.trim().toUpperCase() : "ATIVO");
		return retorno != null ? retorno : SituacaoEnum.ATIVO;
	}
}
