package org.eclipse.om2m.sensivision.controller;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.sensivision.constants.SensorConstants;
import org.eclipse.om2m.sensivision.utils.ProcessManager;
import org.eclipse.om2m.sensivision.utils.ProcessRunner;

public class Controller {
	
	public static CseService CSE;
	protected static String AE_ID;
	protected static ProcessManager processManager = new ProcessManager();
	
	public static Boolean getValue(String sensorId){
		System.out.println("value");
		endStream(sensorId);
		String file = "/home/pi/hats/python/" + sensorId + ".py";
		String targetId = "~/" + Constants.REMOTE_CSE_ID + "/" + Constants.REMOTE_CSE_NAME + "/" + Constants.CSE_NAME +  "/" + sensorId + "_DATA";
		ProcessRunner p = new ProcessRunner(new String[] {"python3", "-u", file}, sensorId, targetId);
		return processManager.startProcess(p);
	}
	
	public static Boolean startStream(String sensorId, double freq){
		if (freq==1) {
			System.out.printf("Start %s stream at 1 reading per second.%n", sensorId);
		}else{
			System.out.printf("Start %s stream at %f readings per second.%n", sensorId, freq);
		}
		String file = "/home/pi/hats/python/" + sensorId + ".py";
		String targetId = "~/" + Constants.REMOTE_CSE_ID + "/" + Constants.REMOTE_CSE_NAME + "/" + Constants.CSE_NAME + "/" + sensorId + SensorConstants.REMOTE_CONTAINER_SUFFIX;
		ProcessRunner p = new ProcessRunner(new String[] {"python3", "-u", file, new Double(freq).toString()}, sensorId, targetId);
		return processManager.startProcess(p);
	}	
	
	public static Boolean endStream(String sensorId){
		System.out.printf("End %s stream.%n", sensorId);
		return processManager.endProcess(sensorId);
	}
	
	public static Boolean changeFreq(String sensorId, Integer freq){
		endStream(sensorId);
		startStream(sensorId, freq);
		return true;
	}

	public static void setCse(CseService cse){
		CSE = cse;
	}

}
