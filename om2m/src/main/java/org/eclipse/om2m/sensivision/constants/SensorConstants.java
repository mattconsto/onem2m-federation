package org.eclipse.om2m.sensivision.constants;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;

import org.eclipse.om2m.commons.constants.Constants;

public class SensorConstants {


	private SensorConstants(){}
	
	public static final String POA = "sensivision";
	public static final String DESC = "DESCRIPTOR";
	public static final String AE_NAME = "mn-pi";
	// TODO detect camera and detect board.
	public static final String[] SENSORS = {"accelerometer", "camera", "compass", "cpu", "disk", "gyroscope", "humidity", "memory", "null", "orientation", "one", "pressure", "process", "quality", "rand", "temperature", "time", "uptime", "wifi", "zero"};
	public static final String QUERY_STRING_OP = "op";
	public static final String QUERY_STRING_SENSOR_ID = "sensorid";
	public static final String CSE_ID = "/" + Constants.CSE_ID;
	public static final String CSE_PREFIX = CSE_ID + "/" + Constants.CSE_NAME;
	public static final String REMOTE_CSE_ID = "/" + Constants.REMOTE_CSE_ID;
	public static final String REMOTE_CSE_PREFIX = REMOTE_CSE_ID + "/" + Constants.REMOTE_CSE_NAME;
	public static final String REMOTE_CONTAINER_SUFFIX = "_DATA";
	public static String MAC_ADDRESS = "unknown";
	
	public static final BigInteger REMOTE_CONTAINER_SIZE = BigInteger.valueOf(10);
	
	
}
