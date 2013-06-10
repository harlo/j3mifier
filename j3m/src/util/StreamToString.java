package util;

import java.util.Scanner;

public class StreamToString extends StreamTo {
    private String result;
    
    
    public StreamToString(String pattern) {
		super();
		this.pattern = pattern;
	}
    
    //TODO this is kinda slow, maybe take out the scanner and do a simpler search
    public void run() {
    	Scanner scanner = new Scanner(stream).useDelimiter(pattern);
    	scanner.findWithinHorizon(pattern, 0);
    	result = scanner.match().group(2);
    }
    
    public String getResult()  {
    	return result;
    }
}
