package org.eclipse.om2m.sensivision.constants;

import org.eclipse.om2m.commons.exceptions.BadRequestException;


public enum Operations {
	
	GET("get"),
	START_STREAM("ss"),
	END_STREAM("es"),
	FREQUENCY(1), 
	STREAM_60_READINGS_TEST("s_60fps_t");
	
	private String value;
	private Integer freqValue;
	
	private Operations(final String value){
		this.value = value;
	}
	
	private Operations(final Integer value){
		this.freqValue = value;
	}
	
	public void setValue(final String value){
		this.value = value;
	}
	
	
	public String toString() {
		return value;
	}
	
	public String getValue(){
		return value;
	}
	
	public Integer getFreq(){
		return this.freqValue;
	}
	
	
	/**
	 * Return the operation from the string
	 * @param operation
	 * @return
	 */
	public static Operations getOperationFromString(String operation){
		for(Operations op : values()){
			if(op.getValue().equals(operation)){
				return op;
			}
		}
		throw new BadRequestException("Unknown Operation");
	}
	
	

}
