package ffmpeg;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import util.Util;

import framework.FrameworkProperties;


public class FfmpegWrapper {


	public void createStill(File inputFile, String outputFile) throws FfmpegException {
		FrameworkProperties config = FrameworkProperties.getInstance();
		runCommand(inputFile,outputFile,config.getffmpegCreateStill());
	}

	public void extractMetadata(File inputFile, String outputFile) throws FfmpegException {
		FrameworkProperties config = FrameworkProperties.getInstance();
		runCommand(inputFile,outputFile,config.getFfmpegGetAttachment());
	}


	public void changeResolution(File inputFile, String outputFile, int width, int height) throws FfmpegException {
		FrameworkProperties config = FrameworkProperties.getInstance();
		runCommand(inputFile,outputFile,config.getffmpegChangeResolution());
	}

	public void changeFormat(File inputFile, String outputFile) throws FfmpegException  {
		FrameworkProperties config = FrameworkProperties.getInstance();
		runCommand(inputFile,outputFile,config.getffmpegChangeFormat());
	}


	private int runCommand(File inputFile, String outputFile, String command) throws FfmpegException {
		try {
			//figure out the file name
			Process p=Runtime.getRuntime().exec(Util.replaceFileMarkers(command, inputFile.getAbsolutePath(), outputFile )); 
			p.waitFor(); 
			
			BufferedReader reader=new BufferedReader(new InputStreamReader(p.getErrorStream())); 
			String line = reader.readLine();

            while(line != null) {
                System.err.println(line);
                line = reader.readLine();
            }
            
            reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
		    line = reader.readLine();

            while(line != null) {
                System.out.println(line);
                line = reader.readLine();
            }

            int exitValue = p.exitValue();
            return exitValue;
			
		} catch (Exception e) {
			throw new FfmpegException("Could not run the ffmpeg command: " + command, e);
		} 
		
	}

}
