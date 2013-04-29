package tests;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import util.Util;

import framework.FrameworkProperties;
import framework.ImageProcessor;

public class ImageProcessorTest {
	

	@Test
	public void parseKeyWordsTest() throws Exception {
		FrameworkProperties config = FrameworkProperties.getInstance();
		ImageProcessor imageProcessor = new ImageProcessor(new File(config.getTestImage()), new File(config.getOutputFolder()));
		File metadata = new File(config.getTestJ3M());
		File outFile = new File(config.getOutputFolder()+ "testKeywords.json");
		long timestamp = System.currentTimeMillis();
		imageProcessor.parseKeyWords(metadata, outFile);
		if (!outFile.exists()){
		    Assert.fail("Extracted key words file " + outFile.getPath() + " does not exist");
		}
		Assert.assertTrue("keyword file timestamp doesnt reflect operation", timestamp < outFile.lastModified());

	}

	@Test
	public void createThumbnailTest() throws Exception {
		FrameworkProperties config = FrameworkProperties.getInstance();
		ImageProcessor imageProcessor = new ImageProcessor(new File(config.getTestImage()), new File(config.getOutputFolder()));
		String outFile = config.getOutputFolder()+ "thumb_" + Util.getBaseFileName(config.getTestImage()) + "." + config.getThumbFileExt();
		long timestamp = System.currentTimeMillis();
		imageProcessor.createThumbnail();
		File outputFile = new File(outFile);
		if (!outputFile.exists()){
		    Assert.fail("Thumbnail image file " + outputFile.getPath() + " does not exist");
		}
		Assert.assertTrue("Thumbnail image file timestamp doesnt reflect operation", timestamp < outputFile.lastModified());
	}
	
	@Test
	public void toLowResolutionTest() throws Exception {
		FrameworkProperties config = FrameworkProperties.getInstance();
		ImageProcessor imageProcessor = new ImageProcessor(new File(config.getTestImage()), new File(config.getOutputFolder()));
		String outFile = config.getOutputFolder()+ "low_" + Util.getBaseFileName(config.getTestImage()) + "." + config.getThumbFileExt();
		long timestamp = System.currentTimeMillis();
		imageProcessor.toLowResolution(false);
		File outputFile = new File(outFile);
		if (!outputFile.exists()){
		    Assert.fail("Low res image file " + outputFile.getPath() + " does not exist");
		}
		Assert.assertTrue("Low res image file timestamp doesnt reflect operation", timestamp < outputFile.lastModified());
	}
	
	@Test
	public void toMediumResolutionTest() throws Exception {
		FrameworkProperties config = FrameworkProperties.getInstance();
		ImageProcessor imageProcessor = new ImageProcessor(new File(config.getTestImage()), new File(config.getOutputFolder()));
		String outFile = config.getOutputFolder()+ "med_" + Util.getBaseFileName(config.getTestImage()) + "." + config.getThumbFileExt();
		long timestamp = System.currentTimeMillis();
		imageProcessor.toMediumResolution(false);
		File outputFile = new File(outFile);
		if (!outputFile.exists()){
		    Assert.fail("Medium res image file " + outputFile.getPath() + " does not exist");
		}
		Assert.assertTrue("Medium res image file timestamp doesnt reflect operation", timestamp < outputFile.lastModified());
	}
	
	@Test
	public void toHighResolutionTest() throws Exception {
		FrameworkProperties config = FrameworkProperties.getInstance();
		ImageProcessor imageProcessor = new ImageProcessor(new File(config.getTestImage()), new File(config.getOutputFolder()));
		String outFile = config.getOutputFolder()+ "high_" + Util.getBaseFileName(config.getTestImage()) + "." + config.getThumbFileExt();
		long timestamp = System.currentTimeMillis();
		imageProcessor.toHighResolution(false);
		File outputFile = new File(outFile);
		if (!outputFile.exists()){
		    Assert.fail("High res file " + outputFile.getPath() + " does not exist");
		}
		Assert.assertTrue("Thumbnail image file timestamp doesnt reflect operation", timestamp < outputFile.lastModified());
	}
	
	@Test
	public void toOriginalResolutionTest() throws Exception {
		FrameworkProperties config = FrameworkProperties.getInstance();
		ImageProcessor imageProcessor = new ImageProcessor(new File(config.getTestImage()), new File(config.getOutputFolder()));
		String outFile = config.getOutputFolder()+ Util.getBaseFileName(config.getTestImage()) + "." + config.getThumbFileExt();
		long timestamp = System.currentTimeMillis();
		imageProcessor.toOriginalResolution(true);
		File outputFile = new File(outFile);
		if (!outputFile.exists()){
		    Assert.fail("Original res image file " + outputFile.getPath() + " does not exist");
		}
		Assert.assertTrue("Original res image file timestamp doesnt reflect operation", timestamp < outputFile.lastModified());
	}
	
	
	
	
	
	
	
	
}
