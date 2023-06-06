package com.mytest.myservlet;

public enum AuditState {
	UNAUDIT(1),
	AUDITING(2),
	AUDIT_SUCCESS(3),
	AUDIT_FAIL(4);
	private final int statenum; 
	
	AuditState(int statenum){
		this.statenum = statenum;   
	}
	
	public int getStatenum() {
		return statenum;   
	}

}
