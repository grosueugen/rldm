package com.gt.rldm.hw6;

public enum PatronResult {

	FIGHT("FIGHT"),
	NO_FIGHT("NO FIGHT"),
	I_DO_NOT_KNOW("I DON'T KNOW");
	
	private String s;
	
	private PatronResult(String s) {
		this.s = s;
	}
	
	@Override
	public String toString() {
		return s;
	}
	
}
