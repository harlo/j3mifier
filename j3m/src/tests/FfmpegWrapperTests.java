package tests;

import java.io.File;


import org.junit.Assert;
import org.junit.Test;

import util.Util;

import ffmpeg.FfmpegException;
import ffmpeg.FfmpegWrapper;
import framework.FrameworkProperties;

public class FfmpegWrapperTests {

	@Test
	public void extractMetadataTest () throws Exception {
		FfmpegWrapper wrapper = new FfmpegWrapper();
		FrameworkProperties config = FrameworkProperties.getInstance();
		
		File inFile = new File(config.getTestVideo());
		String outputFile = config.getOutputFolder() + Util.getBaseFileName(inFile.getName()) + "." + config.getVideoMetadataFileExt();
		long timestamp = System.currentTimeMillis();
		File output = new File(outputFile);
		wrapper.extractMetadata(inFile, output);
		if (!output.exists()){
		    Assert.fail("Extracted metadata file " + output.getPath() + " does not exist");
		}
		Assert.assertTrue("Metadata file timestamp doesnt reflect operation", timestamp < output.lastModified());
	}
	
	@Test
	public void convertToOgvTest () throws Exception {
		FfmpegWrapper wrapper = new FfmpegWrapper();
		FrameworkProperties config = FrameworkProperties.getInstance();
		
		File inFile = new File(config.getTestVideo());
		try {
			wrapper.convertToOgv(inFile);
			Assert.fail("Didn't throw exception on non mp4 input file");
		}catch (FfmpegException e) {
			// supposed to happen
		}
		
	}
}
