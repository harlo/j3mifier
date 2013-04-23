package framework;

import java.io.File;


public class MediaProcessor {
	
	public static void main(String[] args){
		try {
			MediaProcessor processor = new MediaProcessor();
			processor.processInput(args);
		} catch (Exception e) {
			System.err.println("Error occured: \n" + e.getMessage());
			System.out.println("Please provide arguments in the format: <source file> <output folder>");
			System.exit(1);
		}
	}

	public void processInput(String[] args) throws Exception{
		String sourceFile = args[0];
		String destinationFolder = args[1];
		
		//null pointer exceptions will be caught in the main catch all
		//input validation:
		File source;
		File destination;
		try {
			source = new File(sourceFile);
			destination = new File(destinationFolder);
		} catch (NullPointerException e) {
			throw new Exception("Empty argument", e);
		}
		if (!source.exists()) {
			throw new Exception("Source '" + sourceFile  + "' does not exist");
		}
		if (!source.isFile()) {
			throw new Exception("Source '" + sourceFile  + "' is not a file");	
		}
		if (!destination.exists()) {
			throw new Exception("Destination '" + destinationFolder  + "' does not exist");
		}
		if (!destination.isDirectory()) {
			throw new Exception("Destination '" + destinationFolder  + "' is not a directory");
		}
		
		//figure out what it is and do the actual work
		FrameworkProperties config = FrameworkProperties.getInstance();
		String fileType;
		try {
			fileType = Util.getFileExtenssion(sourceFile).toLowerCase();
		} catch (Exception e) {
			throw new Exception("Source '" + sourceFile  + "' is not in one of accepted formats : " + config.getImageInputTypesString() + " or " + config.getVideoInputTypesString(), e );
		}
		if (config.getImageInputTypes().contains(fileType)) {
			processImage(source,destination);
		}else if (config.getVideoInputTypes().contains(fileType)) {
			processVideo(source,destination);
		}else {
			throw new Exception("Source '" + sourceFile  + "' is not in one of accepted formats : " + config.getImageInputTypesString() + " or " + config.getVideoInputTypesString() );
		}

	}

	private void processVideo(File sourceFile, File outputFolder) throws Exception {
		VideoProcessor video = new VideoProcessor(sourceFile, outputFolder);
		try {
			video.extractMetadata();
			video.toOriginalResolution(true);
			video.createStillAndThumbnail();
			
			video.toLowResolution(false);
			video.toMediumResolution(false);
			video.toHighResolution(false);

		} catch (Exception e) {
			throw new Exception ("Error durring video processing: " + e.getLocalizedMessage(), e);
		}
	}

	private void processImage(File sourceFile, File outputFolder) throws Exception{
		ImageProcessor image = new ImageProcessor(sourceFile, outputFolder);
		try {
			image.extractMetadataAndKeywords();
			image.toOriginalResolution(true);
			image.createThumbnail();
			
			image.toLowResolution(false);
			image.totMediumResolution(false);
			image.toHighResolution(false);

		} catch (Exception e) {
			throw new Exception ("Error durring image processing: " + e.getLocalizedMessage(), e);
		}
		
	}
}
