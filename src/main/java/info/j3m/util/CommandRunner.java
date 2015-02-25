package info.j3m.util;

import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
public class CommandRunner {

	public int runCommand(String command, StreamTo processor) throws Exception {
        return runCommand(command,processor,null);
	}
	
	public int runCommand(String command, StreamTo processor, InputStream input) throws Exception {
		ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList(command.split(" ")));
		processBuilder.redirectErrorStream(true);
		Process p = processBuilder.start();
		processor.setStream(p.getInputStream());
		Thread processorThread = new Thread(processor, "output stream");
		processorThread.start();
		
		if(input != null){
			  IOUtils.copyLarge(input,p.getOutputStream());
			  input.close();
			  p.getOutputStream().close();
		}
		p.waitFor(); 
		processorThread.join(100000);

        int exitValue = p.exitValue();
        return exitValue;
	}
	
}
