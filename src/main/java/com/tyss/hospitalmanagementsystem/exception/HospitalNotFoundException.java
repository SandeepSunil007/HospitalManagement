package com.tyss.hospitalmanagementsystem.exception;

@SuppressWarnings("serial")
public class HospitalNotFoundException extends RuntimeException {
	public HospitalNotFoundException(String message){
		super(message);
	}
}
