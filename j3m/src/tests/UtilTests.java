package tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPOutputStream;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.junit.Test;

import util.Util;
import framework.FrameworkProperties;

public class UtilTests {
	FrameworkProperties config = FrameworkProperties.getInstance();
	TestProperties testConfig = TestProperties.getInstance();
	
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
		
		File outFile = new File(testConfig.getOutputFolder()+ "test." + Util.getFileExtenssion(testConfig.getTestImage()));
		long timestamp = System.currentTimeMillis();
		Util.resizeImage(new File(testConfig.getTestImage()), outFile, 100, 100);
		if (!outFile.exists()){
		    Assert.fail("Resized image file " + outFile.getPath() + " does not exist");
		}
		Assert.assertTrue("Resized image file was not updated", timestamp < outFile.lastModified());

	}
	
	@Test
	public void replaceWidthHeightTest(){
		String in = "ffmpeg.exe -i <infile> -filter:v scale=<width>:<height> -acodec copy <outfile>";
		String out = Util.replaceWidthHeight( in,  "700",  "trunc(ow/a/2)*2");
		Assert.assertEquals(out, "ffmpeg.exe -i <infile> -filter:v scale=700:trunc(ow/a/2)*2 -acodec copy <outfile>");
	}

	@Test 
	public void unGzipTest()throws Exception {
		File test = File.createTempFile(String.valueOf(System.currentTimeMillis()), ".zip");
		GZIPOutputStream zip = new GZIPOutputStream(new FileOutputStream(test));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(zip, "UTF-8"));
		String testString = "This will be zipped up, tralalalalala";
		writer.write(testString);
		writer.close();
		
		File testOut = File.createTempFile(String.valueOf(System.currentTimeMillis()), ".test");
		Util.unGzip(test, testOut);
		BufferedReader reader = new BufferedReader(new FileReader(testOut));
		Assert.assertEquals("Unzipped file contents doesnt match input", testString, reader.readLine());
	}
	
	@Test
	public void decodeBase64FileTest() throws Exception {
		File test = File.createTempFile(String.valueOf(System.currentTimeMillis()), ".b64");
		Base64OutputStream out = new Base64OutputStream(new FileOutputStream(test));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
		String testString = "This will be base 64 encoded, tralalalalala";
		writer.write(testString);
		writer.close();
		
		File testOut = File.createTempFile(String.valueOf(System.currentTimeMillis()), ".unb64");
		Util.decodeBase64File(test, testOut);
		BufferedReader reader = new BufferedReader(new FileReader(testOut));
		Assert.assertEquals("Base64 decoded file contents doesnt match input", testString, reader.readLine());
	}
}
