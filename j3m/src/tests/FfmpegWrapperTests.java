package tests;

import java.io.File;


import org.junit.Assert;
import org.junit.Test;

import util.Util;

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
		wrapper.extractMetadata(inFile, outputFile);
		File output = new File(outputFile);
		if (!output.exists()){
		    Assert.fail("Extracted metadata file " + output.getPath() + " does not exist");
		}
		Assert.assertTrue("Metadata file timestamp doesnt reflect operation", timestamp < output.lastModified());
	}
}
