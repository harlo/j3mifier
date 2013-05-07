package tests;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import util.JSONStripper;

import framework.FrameworkProperties;

public class JSONStripperTests {

	@Test
	public void testCleanFile() throws Exception {
		FrameworkProperties config = FrameworkProperties.getInstance();
		File inFile = new File(config.getTestJ3M());
		File outFile = new File(config.getTestJ3M());
		JSONStripper stripper = new JSONStripper(inFile,outFile);
		long timestamp = System.currentTimeMillis();
		stripper.cleanFile();
		Assert.assertTrue("Output file not created", outFile.exists());
		Assert.assertTrue("Output file timestamp doesnt reflect operation", timestamp < outFile.lastModified());

	}
}
