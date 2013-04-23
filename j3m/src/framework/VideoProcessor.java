package framework;

import java.io.File;

import ffmpeg.FfmpegException;
import ffmpeg.FfmpegWrapper;

public class VideoProcessor {
	
	File sourceFile; 
	File outputFolder;
	
	public VideoProcessor(File sourceFile, File outputFolder) {
		super();
		this.sourceFile = sourceFile;
		this.outputFolder = outputFolder;
	}
	
	protected VideoProcessor() {
		super();
	}
	
	public void extractMetadata() throws FfmpegException{
		String outFile = outputFolder.getAbsolutePath() +
						Util.getBaseFileName(sourceFile.getName()) + "." + 
						FrameworkProperties.getInstance().getVideoMetadataFileExt();
		FfmpegWrapper ffmpeg = new FfmpegWrapper();
		ffmpeg.extractMetadata(sourceFile, outFile);
		
	}
	public void createStillAndThumbnail() throws FfmpegException, Exception{
		String stillFile = outputFolder.getAbsolutePath() +
		Util.getBaseFileName(sourceFile.getName()) + "." + 
		FrameworkProperties.getInstance().getVideoStillFileExt();
		FfmpegWrapper ffmpeg = new FfmpegWrapper();
		try {
			ffmpeg.createStill(sourceFile, stillFile);
		} catch (Exception e) {
			throw new FfmpegException("Still file " + stillFile + " could not be created", e);
		}
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

	public void toLowResolution(boolean updateSource)throws Exception{
		String outFile = outputFolder.getAbsolutePath() +
		"low_" + Util.getBaseFileName(sourceFile.getName()) + "." + 
		Util.getFileExtenssion(sourceFile.getName());
		try {
			FfmpegWrapper ffmpeg = new FfmpegWrapper();
			ffmpeg.changeResolution(sourceFile, outFile, 
					FrameworkProperties.getInstance().getVideoSmallWidth(), 
					FrameworkProperties.getInstance().getVideoSmallHeight());
			if (updateSource) {
				sourceFile = new File(outFile);
			}
		} catch (Exception e) {
			throw new Exception("Low res video file " + outFile + " could not be created", e);
		}
		
	}
	public void toMediumResolution(boolean updateSource)throws Exception{
		String outFile = outputFolder.getAbsolutePath() +
		"med_" + Util.getBaseFileName(sourceFile.getName()) + "." +
		Util.getFileExtenssion(sourceFile.getName());
		try {
			FfmpegWrapper ffmpeg = new FfmpegWrapper();
			ffmpeg.changeResolution(sourceFile, outFile, 
					FrameworkProperties.getInstance().getVideoMedWidth(), 
					FrameworkProperties.getInstance().getVideoMedHeight());
			if (updateSource) {
				sourceFile = new File(outFile);
			}
		} catch (Exception e) {
			throw new Exception("Medium res video file " + outFile + " could not be created", e);
		}
		
	}
	public void toHighResolution(boolean updateSource) throws Exception{
		String outFile = outputFolder.getAbsolutePath() +
		"high_" + Util.getBaseFileName(sourceFile.getName()) + "." + 
		Util.getFileExtenssion(sourceFile.getName());
		try {
			FfmpegWrapper ffmpeg = new FfmpegWrapper();
			ffmpeg.changeResolution(sourceFile, outFile, 
					FrameworkProperties.getInstance().getVideoLargeWidth(), 
					FrameworkProperties.getInstance().getVideoLargeHeight());
			if (updateSource) {
				sourceFile = new File(outFile);
			}
		} catch (Exception e) {
			throw new Exception("High res video file " + outFile + " could not be created", e);
		}
	}
	//TODO what other format other than mp4?
	public void toOriginalResolution(boolean updateSource) throws Exception{
		String outFile = outputFolder.getAbsolutePath() +
		Util.getBaseFileName(sourceFile.getName()) + ".mp4";
		//Util.getFileExtenssion(sourceFile.getName());
		try {
			FfmpegWrapper ffmpeg = new FfmpegWrapper();
			ffmpeg.changeResolution(sourceFile, outFile, 
					FrameworkProperties.getInstance().getVideoLargeWidth(), 
					FrameworkProperties.getInstance().getVideoLargeHeight());
			if (updateSource) {
				sourceFile = new File(outFile);
			}
		} catch (Exception e) {
			throw new Exception("Reformatted video file " + outFile + " could not be created", e);
		}
	}
	public void setSourceFile(File sourceFile){
		this.sourceFile = sourceFile;
	}
	public void setOutputFolder(File outputFolder){
		this.outputFolder = outputFolder;
	}

}
