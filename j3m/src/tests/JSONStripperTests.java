package tests;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import util.*;

import framework.FrameworkProperties;

public class JSONStripperTests {
	FrameworkProperties config = FrameworkProperties.getInstance();
	TestProperties testConfig = TestProperties.getInstance();

	@Test
	public void testCleanFile() throws Exception {
		File inFile = new File(testConfig.getDirtyJ3M());
		File outFile = new File(testConfig.getOutputFolder() + Util.getBaseFileName(inFile) + "_clean.json");
		JSONStripper stripper = new JSONStripper(inFile,outFile);
		long timestamp = System.currentTimeMillis();
		stripper.cleanFile();
		Assert.assertTrue("Output file not created", outFile.exists());
		Assert.assertTrue("Output file timestamp doesnt reflect operation", timestamp < outFile.lastModified());

	}
}
