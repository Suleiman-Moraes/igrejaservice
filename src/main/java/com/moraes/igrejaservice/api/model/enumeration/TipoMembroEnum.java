package com.moraes.igrejaservice.api.model.enumeration;

import lombok.Getter;

@Getter
public enum TipoMembroEnum {
	ATIVO("Ativo"),
	INATIVO("Inativo");
	
	private String descricao;
	
	private TipoMembroEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public static TipoMembroEnum get(String status) {
		TipoMembroEnum retorno = TipoMembroEnum.valueOf(status != null ? status.trim().toUpperCase() : "ATIVO");
		return retorno != null ? retorno : TipoMembroEnum.ATIVO;
	}
}
