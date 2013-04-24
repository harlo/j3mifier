package util;

import java.awt.image.BufferedImage;
import java.io.File;


import javax.imageio.ImageIO;

import org.imgscalr.Scalr;


public class Util {

	public static String replaceFileMarkers(String command, String inputFile, String outputFile) {
		return command.replace("<infile>", inputFile).replace("<outfile>", outputFile);
	}
	
	public static String getBaseFileName(String file) {
		File inputFile = new File(file);
		String outFile = inputFile.getName().substring(0, inputFile.getName().indexOf("."));
		return outFile;
	}
	
	public static String getFileExtenssion(String fileName){
		if (fileName.contains(".")){
			return fileName.substring(1 + fileName.lastIndexOf("."));
		}else {
			return null;
		}
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
		String fileType = Util.getFileExtenssion(outFile.getName());
		ImageIO.write(result, fileType, outFile);
	}
	

}
