package com.moraes.igrejaservice.api.model.enumeration;

import lombok.Getter;

@Getter
public enum TipoMembroEnum {
	MEMBRO("Membro"),
	PASTOR_DIRIGENTE("Pastor(a) Dirigente"),
	TESOUREIRO("Tesoureir(a)");
	
	private String descricao;
	
	private TipoMembroEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public static TipoMembroEnum get(String status) {
		TipoMembroEnum retorno = TipoMembroEnum.valueOf(status != null ? status.trim().toUpperCase() : "MEMBRO");
		return retorno != null ? retorno : TipoMembroEnum.MEMBRO;
	}
}
