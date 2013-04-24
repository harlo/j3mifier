package tests;

import java.io.File;
import junit.framework.Assert;
import org.junit.Test;

import util.Util;

import framework.FrameworkProperties;

public class UtilTests {
	@Test
	public void replaceFileMarkersTest() {
		String in = "-y -dump_attachment:t:0 <outfile> -i <infile>";
		String out = Util.replaceFileMarkers(in,"c://test/in.file","c://test/out.file");
		Assert.assertEquals(out, "-y -dump_attachment:t:0 c://test/out.file -i c://test/in.file");
	}
	
	@Test
	public void getBaseFileNameTest() throws Exception {
		String file = "c://test/in.file";
		String out = Util.getBaseFileName(file);
		Assert.assertEquals(out, "in");
	}
	@Test
	public void getFileExtenssionTest() {
		String file = "c://test/in.file";
		String out = Util.getFileExtenssion(file);
		Assert.assertEquals(out, "file");
	}
	
	@Test
	public void resizeImageTest() throws Exception{
		FrameworkProperties config = FrameworkProperties.getInstance();
		
		File outFile = new File(config.getOutputFolder()+ "test." + Util.getFileExtenssion(config.getTestImage()));
		long timestamp = System.currentTimeMillis();
		Util.resizeImage(new File(config.getTestImage()), outFile, 100, 100);
		if (!outFile.exists()){
		    Assert.fail("Resized image file " + outFile.getPath() + " does not exist");
		}
		Assert.assertTrue("Resized image file was not updated", timestamp < outFile.lastModified());

	}

}
