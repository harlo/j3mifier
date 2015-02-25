package info.j3m.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

public class StreamDump implements Runnable {
    private InputStream stream;
    private File outFile = null;
    
    public StreamDump(InputStream stream, File outFile) {
		super();
		this.stream = stream;
		this.outFile = outFile;
	}


	public StreamDump(InputStream input) {
        this.stream = input;
    }
    
    
    private void writeToFile() throws Exception{
    	FileWriter writer = new FileWriter(outFile);
		BufferedWriter out = new BufferedWriter(writer);
		int c;
        try {
			while ((c = stream.read()) != -1) {
			    out.write(c);
			}
		}finally {
			out.close();
		}
    }
    
    private void writeToStandardOut() throws Exception{
    	int c;
        while ((c = stream.read()) != -1) {
            System.out.write(c);
        }
    }
    
    public void run() {
        try {
            if (outFile != null) {
            	writeToFile();
            }else {
            	writeToStandardOut();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
