package com.moraes.igrejaservice.api.model.enumeration;

import lombok.Getter;

@Getter
public enum SituacaoMembroEnum {
	ATIVO("Ativo"),
	INATIVO("Inativo");
	
	private String descricao;
	
	private SituacaoMembroEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public static SituacaoMembroEnum get(String status) {
		SituacaoMembroEnum retorno = SituacaoMembroEnum.valueOf(status != null ? status.trim().toUpperCase() : "ATIVO");
		return retorno != null ? retorno : SituacaoMembroEnum.ATIVO;
	}
}
