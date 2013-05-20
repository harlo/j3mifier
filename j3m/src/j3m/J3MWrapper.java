package j3m;

import java.io.File;
import java.util.Arrays;

import util.StreamDump;
import util.Util;
import framework.FrameworkProperties;



public class J3MWrapper {
	public void extractMetaData(File inputFile, File outputFile ) throws J3MException{
		FrameworkProperties config = FrameworkProperties.getInstance();
		String command = Util.replaceFileMarkers(config.getJ3MGetMetadata(), inputFile.getAbsolutePath(), outputFile.getAbsolutePath() );
		try {
			
			ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList(command.split(" ")));
			processBuilder.redirectErrorStream(true);
			Process p = processBuilder.start();
			new Thread(new StreamDump(p.getInputStream(), outputFile), "output stream").start();
			p.waitFor(); 
				
		} catch (Exception e) {
			throw new J3MException("Could not run the j3mParser command: " + command, e);
		} 

	}
}
