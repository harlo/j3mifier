package ffmpeg;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import util.Util;

import framework.FrameworkProperties;


public class FfmpegWrapper {


	public void createStill(File inputFile, File outputFile) throws FfmpegException {
		FrameworkProperties config = FrameworkProperties.getInstance();
		runCommand(inputFile,outputFile,config.getffmpegCreateStill());
	}

	public void extractMetadata(File inputFile, File outputFile) throws FfmpegException {
		FrameworkProperties config = FrameworkProperties.getInstance();
		runCommand(inputFile,outputFile,config.getFfmpegGetAttachment());
	}


	public void changeResolution(File inputFile, File outputFile, String width, String height) throws FfmpegException {
		FrameworkProperties config = FrameworkProperties.getInstance();
		runCommand(inputFile,outputFile,Util.replaceWidthHeight(config.getffmpegChangeResolution(), width, height));
	}

	public void changeFormat(File inputFile, File outputFile) throws FfmpegException  {
		FrameworkProperties config = FrameworkProperties.getInstance();
		runCommand(inputFile,outputFile,config.getffmpegChangeFormat());
	}


	private int runCommand(File inputFile, File outputFile, String command) throws FfmpegException {
		try {
			//figure out the file name
			command = Util.replaceFileMarkers(command, inputFile.getAbsolutePath(), outputFile.getAbsolutePath());
			ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList(command.split(" ")));
			processBuilder.redirectErrorStream(true);
			Process p = processBuilder.start();
			new Thread(new StreamDump(p.getInputStream()), "output stream").start();
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

	class StreamDump implements Runnable {
	    private InputStream stream;
	    StreamDump(InputStream input) {
	        this.stream = input;
	    }
	    public void run() {
	        try {
	            int c;
	            while ((c = stream.read()) != -1) {
	                System.out.write(c);
	            }
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	    }
	}
}
