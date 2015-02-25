package info.j3m.util;



import java.nio.charset.Charset;
import java.security.MessageDigest;

import org.bouncycastle.util.encoders.Hex;

public class StreamToHash extends StreamTo {
    private String hashFunction;
    private String result;
    
    public StreamToHash(String hashFunction) {
		super();
		/* 	SHA-1 MD2 MD5 */
		this.hashFunction = hashFunction;
	}
    
    public void run() {
    	try {
    		MessageDigest digester;

    		digester = MessageDigest.getInstance(hashFunction); //MD5 or SHA-1
    		byte[] bytes = new byte[BYTE_READ_SIZE];
    		int byteCount;
    		while ((byteCount = stream.read(bytes)) > 0) {
    			digester.update(bytes, 0, byteCount);
    		}

    		byte[] messageDigest = digester.digest();
   
    		result = new String(Hex.encode(messageDigest), Charset.forName("UTF-8"));
        
    	} catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public String getResult()  {
    	return result;
    }
}
