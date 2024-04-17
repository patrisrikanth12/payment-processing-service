package com.cpt.payments.constants;

import lombok.Getter;

public enum ProviderHandlerEnum {
	TRUSTLY(1, "TRUSTLY"),
	STRIPE(2, "STRIPE");

	@Getter
	private Integer providerId;
	@Getter
	private String providerName;

	private ProviderHandlerEnum(Integer providerId, String providerName) {
		this.providerId = providerId;
		this.providerName = providerName;
	}

	public static ProviderHandlerEnum getProviderEnum(String providerName) {
		for (ProviderHandlerEnum e : ProviderHandlerEnum.values()) {
			if (providerName.equals(e.providerName))
				return e;
		}
		return null;
	}
	
	public static ProviderHandlerEnum getProviderEnumById(int id) {
		for (ProviderHandlerEnum e : ProviderHandlerEnum.values()) {
			if (id == e.providerId)
				return e;
		}
		return null;
	}
}
