package tests;

import j3m.J3MWrapper;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import framework.FrameworkProperties;
import framework.Util;

public class J3MWrapperTests {

	@Test
	public void extractMetaDataTest () throws Exception{
		J3MWrapper wrapper = new J3MWrapper();
		FrameworkProperties config = FrameworkProperties.getInstance();
		
		File inFile = new File(config.getTestImage());
		String output = config.getOutputFolder() + Util.getBaseFileName(inFile.getName()) + "." + config.getImageMetadataFileExt();
		long timestamp = System.currentTimeMillis();
		wrapper.extractMetaData(inFile, output);
		File outputFile = new File(output);
		if (!outputFile.exists()){
		    Assert.fail("Extracted metadata file " + outputFile.getPath() + " does not exist");
		}
		Assert.assertTrue("Metadata file timestamp doesnt reflect operation", timestamp < outputFile.lastModified());
	}
}
