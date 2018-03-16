package org.eclipse.om2m.sensivision.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.sensivision.RequestSender;

/**
 * Runs processes
 * @author Matthew Consterdine
 */
public class ProcessRunner extends Thread { 
	protected String sensorId;
	protected String[] commands;
	protected Boolean stop = false;
	
	Process process; 
	String remoteServer;
	
	/**
	 * Creates the process runner
	 * @param commands A list of commands
	 */
	public ProcessRunner(String[] commands, String sensorId, String targetId) {
		this.commands = commands;
		this.remoteServer = targetId;
		this.sensorId = sensorId;
	}
	
	@Override
	public void run(){
		try {
			this.exec();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Empty a buffer into a string
	 * @param reader The buffer reader
	 * @return The combined string
	 * @throws IOException If an error occurs while reading the buffer
	 */
	protected static String emptyBuffer(BufferedReader reader) throws IOException {
		StringBuilder builder = new StringBuilder();
		while(reader.ready()) builder.append(reader.readLine()).append("\n");
		return builder.toString();
	}

	public void stopProcess(){
		stop = true;
	}
	
	/**
	 * Execute the process, saving the results
	 * @return Itself
	 * @throws IOException If an error occurs while executing the script, or the return value is non-zero
	 */
	public void exec() throws IOException {
		// TODO check if this is safe against injection attacks with user input
		ProcessBuilder builder = new ProcessBuilder(this.commands);
		builder.redirectErrorStream(true);
		process = builder.start();
		InputStream is = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		String line = null;
		while (((line = reader.readLine()) != null)) {
			saveData(line);
			if (stop) break;
		}
	}
	
	private void saveData(String data){
		ContentInstance cin = new ContentInstance();
		cin.setContent(ObixUtil.getRep(this.sensorId, System.currentTimeMillis() / 1000L, data));
		cin.setContentInfo(MimeMediaType.OBIX + ":" + MimeMediaType.ENCOD_PLAIN);
		RequestSender.createContentInstance(this.remoteServer, cin);
	}
}