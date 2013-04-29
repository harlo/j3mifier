package tests;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import util.Util;
import framework.FrameworkProperties;
import framework.VideoProcessor;

public class VideoProcessorTests {
	
	private VideoProcessor videoProcessor;
	
	
	public void setUP() {
		FrameworkProperties config = FrameworkProperties.getInstance();
		videoProcessor = new VideoProcessor(new File(config.getTestVideo()), new File(config.getOutputFolder()));
	}
	
	@Test
	public void runTests()throws Exception {
		setUP();
		toOriginalResolutionTest();
		createStillAndThumbnailTest();
		toHighResolutionTest();
		toMediumResolutionTest();
		toLowResolutionTest();
	}
	
	public void toOriginalResolutionTest() throws Exception{
		FrameworkProperties config = FrameworkProperties.getInstance();
		String outFile = config.getOutputFolder()+ Util.getBaseFileName(config.getTestVideo()) + "." + config.getVideoConvertedFormat() ;
		long timestamp = System.currentTimeMillis();
		videoProcessor.toOriginalResolution(true);
		File outputFile = new File(outFile);
		if (!outputFile.exists()){
		    Assert.fail("converted video file " + outputFile.getPath() + " does not exist");
		}
		Assert.assertTrue("converted video file timestamp doesnt reflect operation", timestamp < outputFile.lastModified());
	}
	
	
	public void createStillAndThumbnailTest() throws Exception {
		FrameworkProperties config = FrameworkProperties.getInstance();
		File stillFile =  new File(config.getOutputFolder(),  Util.getBaseFileName(config.getTestVideo()) + "." + config.getVideoStillFileExt());
		File thumbFile =  new File(config.getOutputFolder(),  "thumb_" + Util.getBaseFileName(config.getTestVideo()) + "." + config.getThumbFileExt());
		long timestamp = System.currentTimeMillis();
		videoProcessor.createStillAndThumbnail();
		if (!stillFile.exists()){
		    Assert.fail("Still video file " + stillFile.getPath() + " does not exist");
		}
		Assert.assertTrue("Still video file timestamp doesnt reflect operation", timestamp < stillFile.lastModified());
		if (!thumbFile.exists()){
		    Assert.fail("Thumb video file " + thumbFile.getPath() + " does not exist");
		}
		Assert.assertTrue("Thumb video file timestamp doesnt reflect operation", timestamp < thumbFile.lastModified());
	}
	
	public void toHighResolutionTest() throws Exception {
		FrameworkProperties config = FrameworkProperties.getInstance();
		String outFile = config.getOutputFolder()+ "high_" + Util.getBaseFileName(config.getTestVideo()) + "." + config.getVideoConvertedFormat();
		long timestamp = System.currentTimeMillis();
		videoProcessor.toHighResolution(true);
		File outputFile = new File(outFile);
		if (!outputFile.exists()){
		    Assert.fail("High res video file " + outputFile.getPath() + " does not exist");
		}
		Assert.assertTrue("High res video file timestamp doesnt reflect operation", timestamp < outputFile.lastModified());
	}
	

	public void toMediumResolutionTest() throws Exception {
		FrameworkProperties config = FrameworkProperties.getInstance();
		String outFile = config.getOutputFolder()+ "med_" + Util.getBaseFileName(config.getTestVideo()) + "." + config.getVideoConvertedFormat();
		long timestamp = System.currentTimeMillis();
		videoProcessor.toMediumResolution(false);
		File outputFile = new File(outFile);
		if (!outputFile.exists()){
		    Assert.fail("Medium res video file " + outputFile.getPath() + " does not exist");
		}
		Assert.assertTrue("Medium res video file timestamp doesnt reflect operation", timestamp < outputFile.lastModified());
	}
	

	public void toLowResolutionTest() throws Exception {
		FrameworkProperties config = FrameworkProperties.getInstance();
		String outFile = config.getOutputFolder()+ "low_" + Util.getBaseFileName(config.getTestVideo()) + "." + config.getVideoConvertedFormat();
		long timestamp = System.currentTimeMillis();
		videoProcessor.toLowResolution(false);
		File outputFile = new File(outFile);
		if (!outputFile.exists()){
		    Assert.fail("Low res video file " + outputFile.getPath() + " does not exist");
		}
		Assert.assertTrue("Low res video file timestamp doesnt reflect operation", timestamp < outputFile.lastModified());
	}	
}
