package org.eclipse.om2m.sensivision.controller;

import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.sensivision.RequestSender;
import org.eclipse.om2m.sensivision.constants.SensorConstants;
import org.eclipse.om2m.sensivision.utils.ObixUtil;

public class LifeCycleManager {
	
	private static Log LOGGER = LogFactory.getLog(LifeCycleManager.class);
	
	
	public static void start(){
		// createAEonRemoteServer();
		for(String sensor: SensorConstants.SENSORS){
			createResource(sensor, SensorConstants.POA);
			createContainerOnRemoteServer(sensor);
		}
	}
	
	public static void stop(){}

	private static void createResource(String appId, String poa) {
			
			// Create the Application resource
			Container container = new Container();
			container.getLabels().add("sensor");
			container.setMaxNrOfInstances(BigInteger.valueOf(0));
			
			AE ae = new AE();
			ae.setRequestReachability(true);
			ae.getPointOfAccess().add(poa);
			ae.setAppID(appId);
			ae.setName(appId);
			
			ResponsePrimitive response = RequestSender.createAE(ae);
			
			// Add bool for (if AE was created in the IN) for if condition?
			if(response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
				
				container = new Container();
				container.setMaxNrOfInstances(BigInteger.valueOf(10));
				// Create DESCRIPTOR container sub-resource
				container.setName(SensorConstants.DESC);
				LOGGER.info(RequestSender.createContainer(response.getLocation(), container));
				
				String content;
				// Create DESCRIPTION contentInstance on the DESCRIPTOR container resource
				content = ObixUtil.getDescriptorRep(SensorConstants.CSE_ID, appId);
				ContentInstance contentInstance = new ContentInstance();
				contentInstance.setContent(content);
				contentInstance.setContentInfo(MimeMediaType.OBIX);
				RequestSender.createContentInstance(
						SensorConstants.CSE_PREFIX + "/" + appId + "/" + SensorConstants.DESC, contentInstance);
			}
		}
	
	private static void createContainerOnRemoteServer(String sensorName){		
		Container container = new Container();
		container.setMaxNrOfInstances(SensorConstants.REMOTE_CONTAINER_SIZE);
		container.setName(sensorName + SensorConstants.REMOTE_CONTAINER_SUFFIX);
		LOGGER.info(RequestSender.createContainer("~/" + Constants.REMOTE_CSE_ID + "/" + Constants.REMOTE_CSE_NAME + 
			"/" + Constants.CSE_NAME, container));
	}
	
}
