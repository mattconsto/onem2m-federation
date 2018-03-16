package org.eclipse.om2m.sensivision.utils;

import java.util.HashMap;
import java.util.Map;

public class ProcessManager {

	Map<String, ProcessRunner> processes;
	
	public ProcessManager(){
		processes = new HashMap<String, ProcessRunner>();
	}
	
	
	public Boolean startProcess(ProcessRunner p){
		if (!processes.containsKey(p.sensorId)){
			processes.put(p.sensorId, p);
			p.start();
			return true;
		}
		return false;
	}
	
	//processes.removeIf(item -> item.sensorId == sensorId);
	
	public Boolean endProcess(String sensorid){
		try {
			ProcessRunner p = processes.get(sensorid);
			p.stopProcess();
			processes.remove(sensorid);
			return true;
		} catch (NullPointerException e){
			return false;
		}
	}
}
