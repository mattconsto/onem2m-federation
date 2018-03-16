package org.eclipse.om2m.sensivision;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.sensivision.constants.Operations;
import org.eclipse.om2m.sensivision.constants.SensorConstants;
import org.eclipse.om2m.sensivision.controller.Controller;
import org.eclipse.om2m.sensivision.utils.ObixUtil;

public class Router implements InterworkingService {

	private static Log LOGGER = LogFactory.getLog(Router.class);

	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		if(request.getQueryStrings().containsKey(SensorConstants.QUERY_STRING_OP)){
			String operation = request.getQueryStrings().get(SensorConstants.QUERY_STRING_OP).get(0);
			Operations op = Operations.getOperationFromString(operation);
			String sensorId= null;
			if(request.getQueryStrings().containsKey(SensorConstants.QUERY_STRING_SENSOR_ID)){
				sensorId = request.getQueryStrings().get(SensorConstants.QUERY_STRING_SENSOR_ID).get(0);
			}
			LOGGER.info("Received request in Sample IPE: " + SensorConstants.QUERY_STRING_OP + "=" + operation + " ; " + SensorConstants.QUERY_STRING_SENSOR_ID + "=" + sensorId);
			switch(op){
			case GET:
				response.setContent(ObixUtil.getValueRep(sensorId, String.valueOf(Controller.getValue(sensorId))));
				request.setReturnContentType(MimeMediaType.OBIX);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case START_STREAM:
				double freq = 0.1;
				response.setContent(ObixUtil.getValueRep(sensorId, String.valueOf(Controller.startStream(sensorId, freq))));
				request.setReturnContentType(MimeMediaType.OBIX);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case END_STREAM:
				response.setContent(ObixUtil.getValueRep(sensorId, String.valueOf(Controller.endStream(sensorId))));
				request.setReturnContentType(MimeMediaType.OBIX);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
//			case STREAM_60_READINGS_TEST:
//				Integer freq1 = 60;
//				response.setContent(String.valueOf(Controller.startStream(sensorId, freq1)));
//				request.setReturnContentType(MimeMediaType.OBIX);
//				response.setResponseStatusCode(ResponseStatusCode.OK);
//				break;
			default:
				throw new BadRequestException();
			}
		}
		if(response.getResponseStatusCode() == null){
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
		}
		return response;
	}

	@Override
	public String getAPOCPath() {
		return SensorConstants.POA;
	}
	
}
