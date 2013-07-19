package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;


public class Util {

	public static String replaceFileMarkers(String command, String inputFile, String outputFile) {
		return command.replace("<infile>", inputFile).replace("<outfile>", outputFile);
	}
	
	public static String replaceMarkers(String command, String prefix, List<String> replacements) {
		int count = 1;
		for (String replacement : replacements){
			command = command.replace(prefix + count, replacement);
			count++;
		}
		return command;
	}
	
	public static String getBaseFileName(File file) {
		return file.getName().substring(0, file.getName().indexOf("."));
	}
	
	public static String getBaseFileName(String file) {
		File inFile = new File (file);
		return inFile.getName().substring(0, inFile.getName().indexOf("."));
	}
	
	public static String getFileExtenssion(File file){
		String fileName = file.getName();
		if (fileName.contains(".")){
			return fileName.substring(1 + fileName.lastIndexOf("."));
		}else {
			return null;
		}
	}
	public static String getFileExtenssion(String fileName){
		if (fileName.contains(".")){
			return fileName.substring(1 + fileName.lastIndexOf("."));
		}else {
			return null;
		}
	}
	
	public static String replaceWidthHeight(String command, String width, String height){
		return command.replace("<width>", ""+width).replace("<height>", ""+height);
	}
	
	/**
	 * Uses the imgscalr-java-image-scaling-library
	 * http://www.thebuzzmedia.com/software/imgscalr-java-image-scaling-library/
	 * @param sourceFile
	 * @param outFile
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public static void resizeImage(File sourceFile, File outFile, int width, int height) throws Exception{
		BufferedImage image = ImageIO.read(sourceFile);
		BufferedImage result = Scalr.resize(image,width, height);
		String fileType = Util.getFileExtenssion(outFile);
		ImageIO.write(result, fileType, outFile);
	}
	
	public static void unGzip(File sourceFile, File destinationFile) throws FileNotFoundException, IOException {
		  GZIPInputStream gin = new GZIPInputStream(new FileInputStream(sourceFile));
		  FileOutputStream fos = new FileOutputStream(destinationFile);
		  IOUtils.copyLarge(gin,fos);
		  gin.close();
		  fos.close();
	}

	public static String saltAndHash(String data, String salt) throws NoSuchAlgorithmException{
		String payload = data + salt;
		MessageDigest digester = MessageDigest.getInstance("SHA-1"); //MD5 or SHA-1
		digester.update(payload.getBytes());
		byte[] messageDigest = digester.digest();
		return Hex.encodeHexString(messageDigest);
	}
	
	public static String decodeBase64(String encoded) throws UnsupportedEncodingException{
		byte[] decoded = Base64.decodeBase64(encoded);
		return new String(decoded, "UTF-8");
	}
	
	public static void decodeBase64File(File input, File output)throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(input);
		Base64InputStream b64is = new Base64InputStream(fis);
		FileOutputStream fos = new FileOutputStream(output);
		IOUtils.copyLarge(b64is,fos);
		b64is.close();
		fos.close();

	}
	
}
