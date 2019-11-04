package com.moraes.igrejaservice.api.model.enumeration;

import lombok.Getter;

@Getter
public enum PermissaoEnum {
	ROLE_MEMBRO_COMUM("Membro Comum"),
	ROLE_TESOUREIRO("Tesoureiro(a)");
	
	private String descricao;
	
	private PermissaoEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public static PermissaoEnum get(String status) {
		PermissaoEnum retorno = PermissaoEnum.valueOf(status != null ? status.trim().toUpperCase() : "ROLE_MEMBRO_COMUM");
		return retorno != null ? retorno : PermissaoEnum.ROLE_MEMBRO_COMUM;
	}
}
