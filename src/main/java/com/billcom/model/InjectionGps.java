package com.billcom.model;

public class InjectionGps {

	private Cagnotte cagnotte;

	public Cagnotte getCagnotte() {
		return cagnotte;
	}

	public void setCagnotte(Cagnotte cagnotte) {
		this.cagnotte = cagnotte;
	}

	public InjectionGps(Cagnotte cagnotte) {
		super();
		this.cagnotte = cagnotte;
	}

	public InjectionGps() {
		super();
	}
}
