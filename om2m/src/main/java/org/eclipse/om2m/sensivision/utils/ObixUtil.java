package org.eclipse.om2m.sensivision.utils;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.obix.Contract;
import org.eclipse.om2m.commons.obix.Obj;
import org.eclipse.om2m.commons.obix.Op;
import org.eclipse.om2m.commons.obix.Str;
import org.eclipse.om2m.commons.obix.Uri;
import org.eclipse.om2m.commons.obix.Int;
import org.eclipse.om2m.commons.obix.io.ObixEncoder;
import org.eclipse.om2m.sensivision.constants.Operations;


public class ObixUtil {
	
	public static String getDescriptorRep(String cseId, String appId) {
		String TYPE = "sensor";
		String LOCATION = "pi";
		String prefix = cseId+"/"+ Constants.CSE_NAME + "/" + appId;
		// oBIX
		Obj obj = new Obj();
		obj.add(new Str("type", TYPE));
		obj.add(new Str("location", LOCATION));
		obj.add(new Str("appId",appId));
		
		// get sensor value
		Op opStateDirect = new Op();
		opStateDirect.setName("getState");
		opStateDirect.setHref(new Uri(prefix + "?op="+ Operations.GET+"&sensorid=" + appId));
		opStateDirect.setIs(new Contract("execute"));
		opStateDirect.setIn(new Contract("obix:Nil"));
		opStateDirect.setOut(new Contract("obix:Nil"));
		obj.add(opStateDirect);
		
		// get sensor value
		Op opStartStream = new Op();
		opStartStream.setName("Start Stream");
		opStartStream.setHref(new Uri(prefix + "?op="+ Operations.START_STREAM +"&sensorid=" + appId));
		opStartStream.setIs(new Contract("execute"));
		opStartStream.setIn(new Contract("obix:Nil"));
		opStartStream.setOut(new Contract("obix:Nil"));
		obj.add(opStartStream);
		
		// get sensor value
		Op opEndStream = new Op();
		opEndStream.setName("End Stream");
		opEndStream.setHref(new Uri(prefix + "?op="+ Operations.END_STREAM +"&sensorid=" + appId));
		opEndStream.setIs(new Contract("execute"));
		opEndStream.setIn(new Contract("obix:Nil"));
		opEndStream.setOut(new Contract("obix:Nil"));
		obj.add(opEndStream);

		return ObixEncoder.toString(obj);
	}
	
	public static String getValueRep(String appId, String value) {
		Obj obj = new Obj();
		obj.add(new Str("type", "sensor"));
		obj.add(new Str("location", "pi"));
		obj.add(new Str("appId", appId));
		obj.add(new Str("value",value));
		return ObixEncoder.toString(obj);
	}

	public static String getRep(String sensorId, Long timestamp, String data) {
		Obj obj = new Obj();
		obj.add(new Str("type", "sensor"));
		obj.add(new Str("sensorId", sensorId));
		obj.add(new Str("timestamp", timestamp.toString()));
		obj.add(new Str("value", data));
		return ObixEncoder.toString(obj);
	}

}
