package tests;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.junit.Test;
import org.junit.Assert;

import framework.FrameworkProperties;

public class PropertiesTest {
	
/**
 * makes sure all the necessary resource are provided in the conf file and are accsessoble
 * @throws Exception
 */
	@Test
	public void initTest() throws Exception {
		FrameworkProperties config;
		try {
			config = FrameworkProperties.getInstance();
		} catch (Exception e) {
			Assert.fail("could not load properties file");
			throw e;
		}
		//file formats
		try {				
			Assert.assertTrue(config.getImageInputTypes().contains("jpg"));
		} catch (Exception e) {
			Assert.fail("could not process image formats");
		}
		try {				
			Assert.assertTrue(config.getVideoInputTypes().contains("mkv"));
		} catch (Exception e) {
			Assert.fail("could not process video formats");
		}
		//keywords containers and excluded words
		try {				
			Assert.assertTrue(config.getImageKeywordContainers().size()>0);
		} catch (Exception e) {
			Assert.fail("could not process keywords containers");
		}
		try {				
			Assert.assertTrue(config.getImageKeywordExclussions().size()>0);
		} catch (Exception e) {
			Assert.fail("could not process keyword exclussions");
		}
		Assert.assertNotNull("No j3m metadata a command", config.getJ3MGetMetadata());
		
		
		Assert.assertNotNull("No image metadata file type", config.getImageMetadataFileExt());
		Assert.assertNotNull("No image keywords file type", config.getImageKeywordsFileExt());
		Assert.assertNotNull("No video metadata file type", config.getVideoMetadataFileExt());
		Assert.assertNotNull("No video metadata file type", config.getVideoStillFileExt());
		Assert.assertNotNull("No video conversion file type", config.getVideoConvertedFormat());
		
		Assert.assertNotNull("No ffmpeg@theora command", config.getffmpeg2Theora());
		
		
		Assert.assertNotNull("No thumb file type", config.getThumbFileExt());
		Assert.assertTrue("No thumb file width", 0 < config.getThumbWidth());
		Assert.assertTrue("No thumb file height", 0 < config.getThumbHeight());
		
		Assert.assertTrue("No small image width", 0 < config.getImageSmallWidth());
		Assert.assertTrue("No small image height",0 <  config.getImageSmallHeight());
		Assert.assertTrue("No med image width",0 <  config.getImageMedWidth());
		Assert.assertTrue("No med image height",0 <  config.getImageMedHeight());
		Assert.assertTrue("No large image width",0 <  config.getImageLargeWidth());
		Assert.assertTrue("No large image height",0 <  config.getImageLargeHeight());
		
		Assert.assertNotNull("No small video width", config.getVideoSmallWidth());
		Assert.assertNotNull("No small video height", config.getVideoSmallHeight());
		Assert.assertNotNull("No med video width", config.getVideoMedWidth());
		Assert.assertNotNull("No med video height", config.getVideoMedHeight());
		Assert.assertNotNull("No large video width", config.getVideoLargeWidth());
		Assert.assertNotNull("No large video height", config.getVideoLargeHeight());
		
		//check ffmpeg is working
		ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList(config.getFfmpegVersion().split(" ")));
		processBuilder.redirectErrorStream(true);
		Process p = processBuilder.start(); 
		p.waitFor(); 
		BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
		String line=reader.readLine(); 
		if (!line.contains("ffmpeg version")) 
		{ 
			Assert.fail("could not run ffmpeg from command line");
		} 

	}
	
	/**
	 * tests optional resources
	 * @throws Exception
	 */
		@Test
		public void optTest() throws Exception {
			FrameworkProperties config;
			try {
				config = FrameworkProperties.getInstance();
				
			
				File file = new File(config.getTestImage());
				if (!file.exists()){
				    Assert.fail("test image file does not exist");
				}
				if (!file.isFile()){
				    Assert.fail("test image file doe not exist");
				}
				
				file = new File(config.getTestVideo());
				if (!file.exists()){
				    Assert.fail("test video file does not exist");
				}
				if (!file.isFile()){
				    Assert.fail("test video file doe not exist");
				}
				
				
				file = new File(config.getOutputFolder());
				if (!file.exists()){
				    Assert.fail("output folder does not exist");
				}
				if (!file.isDirectory()){
				    Assert.fail("output folder aint a folder");
				}
				file = new File(config.getTestJ3M());
				if (!file.exists()){
				    Assert.fail("tesst j3m file does not exist");
				}
				if (!file.isFile()){
				    Assert.fail("tesst j3m file does not exist");
				}
				
			
			} catch (Exception e) {
				Assert.fail("could not load properties file");
			}
		}
}
