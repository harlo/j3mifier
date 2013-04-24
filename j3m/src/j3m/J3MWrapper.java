package j3m;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import util.Util;

import framework.FrameworkProperties;



public class J3MWrapper {
	public void extractMetaData(File inputFile, String outputFile ) throws J3MException{
		FrameworkProperties config = FrameworkProperties.getInstance();
		String command = Util.replaceFileMarkers(config.getJ3MGetMetadata(), inputFile.getAbsolutePath(), outputFile );
		try {
				//figure out the file name
				Process p=Runtime.getRuntime().exec(command); 
				p.waitFor(); 
				
				BufferedReader reader=new BufferedReader(new InputStreamReader(p.getErrorStream())); 
				String line = reader.readLine();

	            while(line != null) {
	                System.err.println(line);
	                line = reader.readLine();
	            }
	            
	            reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
			    line = reader.readLine();
			    
			    File metadata = new File(outputFile);
				FileWriter writer = new FileWriter(metadata);
				BufferedWriter out = new BufferedWriter(writer);
				try {
					while (line != null) {
						out.write(line);
						line = reader.readLine();
					}
				} catch (Exception e) {
					throw new J3MException("Could create metadata file: " + outputFile, e);
				}finally{
					out.close();
				}
				
			} catch (Exception e) {
				throw new J3MException("Could not run the j3mParser command: " + command, e);
			} 

	}
}
