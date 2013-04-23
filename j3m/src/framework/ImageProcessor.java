package framework;

import j3m.J3MException;
import j3m.J3MWrapper;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;



public class ImageProcessor {
	
	File sourceFile; 
	File outputFolder;
	
	public ImageProcessor(File sourceFile, File outputFolder) {
		super();
		this.sourceFile = sourceFile;
		this.outputFolder = outputFolder;
	}
	
	protected ImageProcessor() {
		super();
	}
	
	public void setSourceFile(File sourceFile){
		this.sourceFile = sourceFile;
	}
	public void setOutputFolder(File outputFolder){
		this.outputFolder = outputFolder;
	}
	
	public void extractMetadataAndKeywords() throws J3MException, Exception{
		String outFile = outputFolder.getAbsolutePath() +
		Util.getBaseFileName(sourceFile.getName()) + "." +
		FrameworkProperties.getInstance().getImageMetadataFileExt();
		J3MWrapper j3m = new J3MWrapper();
		File metadata;
		try {
			j3m.extractMetaData(sourceFile, outFile);
			metadata = new File(outFile);
			if (!metadata.exists()){
				throw new Exception("Could not create image metadata file : " + outFile);
			}
		} catch (Exception e) {
			throw new J3MException("Could not extract image metadata file : " + outFile, e);
		}
		String keyWordFile = outputFolder.getAbsolutePath() +
		Util.getBaseFileName(sourceFile.getName()) + "." + 
		FrameworkProperties.getInstance().getImageKeywordsFileExt();
		
		try {
			parseKeyWords(metadata, keyWordFile);
		} catch (Exception e) {
			throw new Exception("Could not create image keyword file : " + outFile, e);
		}
		
	}
	
	//TODO finish this
	public void parseKeyWords(File sourceFile, String outputFile) throws IOException{
		File output = new File(outputFile);
		FileWriter writer = new FileWriter(output);
		BufferedWriter out = new BufferedWriter(writer);
		JsonWriter jsonWriter = new JsonWriter(out);
		JsonParser parser = new JsonParser();
		JsonElement meta = parser.parse(new FileReader(sourceFile));

		try {
			List<String> exclusions = FrameworkProperties.getInstance().getImageKeywordExclussions();
			for (String container : FrameworkProperties.getInstance().getImageKeywordContainers()) {
				String text = container.toString(); //TODO find a json parser
				String[] keywords = text.split(" ");
				for (int i = 0; i < keywords.length; i++) {
					if (!exclusions.contains(keywords[i])) {
						out.write("Hello Java");
					}
				}
			}
		} finally {
			out.close();
		}
	}
	
	public void createThumbnail() throws Exception{
		String thumbFile = outputFolder.getAbsolutePath() +
		"thumb_" + Util.getBaseFileName(sourceFile.getName()) + "." + 
		FrameworkProperties.getInstance().getThumbFileExt();
		try {
			Util.resizeImage(sourceFile.getAbsolutePath(), thumbFile, 
					FrameworkProperties.getInstance().getThumbWidth(), 
					FrameworkProperties.getInstance().getThumbHeight());
		} catch (Exception e) {
			throw new Exception("Thumbnail file " + thumbFile + " could not be created", e);
		}
		
	}
	public void toLowResolution(boolean updateSource) throws Exception{
		String outFile = outputFolder.getAbsolutePath() +
		"low_" + Util.getBaseFileName(sourceFile.getName()) + "." + 
		Util.getFileExtenssion(sourceFile.getName());
		try {
			File out = Util.resizeImage(sourceFile.getAbsolutePath(), outFile, 
					FrameworkProperties.getInstance().getImageSmallWidth(), 
					FrameworkProperties.getInstance().getImageSmallHeight());
			if (updateSource) {
				sourceFile = out;
			}
		} catch (Exception e) {
			throw new Exception("Low res image file " + outFile + " could not be created", e);
		}
	}
	public void totMediumResolution(boolean updateSource)throws Exception{
		String outFile = outputFolder.getAbsolutePath() +
		"med_" + Util.getBaseFileName(sourceFile.getName()) + "." + 
		Util.getFileExtenssion(sourceFile.getName());
		try {
			File out = Util.resizeImage(sourceFile.getAbsolutePath(), outFile, 
					FrameworkProperties.getInstance().getImageMedWidth(), 
					FrameworkProperties.getInstance().getImageMedHeight());
			if (updateSource) {
				sourceFile = out;
			}
		} catch (Exception e) {
			throw new Exception("Medium res image file  " + outFile + " could not be created", e);
		}
		
	}
	public void toHighResolution(boolean updateSource)throws Exception{
		String outFile = outputFolder.getAbsolutePath() +
		"high_" + Util.getBaseFileName(sourceFile.getName()) + "." + 
		Util.getFileExtenssion(sourceFile.getName());
		try {
			File out = Util.resizeImage(sourceFile.getAbsolutePath(), outFile, 
					FrameworkProperties.getInstance().getImageLargeWidth(), 
					FrameworkProperties.getInstance().getImageLargeHeight());
			if (updateSource) {
				sourceFile = out;
			}
		} catch (Exception e) {
			throw new Exception("High res image file " + outFile + " could not be created", e);
		}
		
	}
	public void toOriginalResolution(boolean updateSource)throws Exception{
		String outFile = outputFolder.getAbsolutePath() + sourceFile.getName();
		try {
			BufferedImage image = ImageIO.read(sourceFile);
			String fileType = Util.getFileExtenssion(outFile);
			File out = new File(outFile);
			ImageIO.write(image, fileType, out);
			if (updateSource) {
				sourceFile = out;
			}
		} catch (Exception e) {
			throw new Exception("Image file " + outFile + " could not be created", e);
		}
		
	}

}
