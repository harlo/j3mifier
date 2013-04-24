package tests;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

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
			
			
			Assert.assertNotNull("No thumb file type", config.getThumbFileExt());
			Assert.assertTrue("No thumb file width", 0 < config.getThumbWidth());
			Assert.assertTrue("No thumb file height", 0 < config.getThumbHeight());
			
			Assert.assertTrue("No small image width", 0 < config.getImageSmallWidth());
			Assert.assertTrue("No small image height",0 <  config.getImageSmallHeight());
			Assert.assertTrue("No med image width",0 <  config.getImageMedWidth());
			Assert.assertTrue("No med image height",0 <  config.getImageMedHeight());
			Assert.assertTrue("No large image width",0 <  config.getImageLargeWidth());
			Assert.assertTrue("No large image height",0 <  config.getImageLargeHeight());
			
			Assert.assertTrue("No small video width",0 <  config.getVideoSmallWidth());
			Assert.assertTrue("No small video height",0 <  config.getVideoSmallHeight());
			Assert.assertTrue("No med video width",0 <  config.getVideoMedWidth());
			Assert.assertTrue("No med video height",0 <  config.getVideoMedHeight());
			Assert.assertTrue("No large video width",0 <  config.getVideoLargeWidth());
			Assert.assertTrue("No large video height",0 <  config.getVideoLargeHeight());
			
			//check ffmpeg is working
			Process p=Runtime.getRuntime().exec(config.getFfmpegVersion()); 
			p.waitFor(); 
			BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
			String line=reader.readLine(); 
			if (!line.contains("ffmpeg version")) 
			{ 
				Assert.fail("could not run ffmpeg from command line");
			} 
		
		
		} catch (Exception e) {
			Assert.fail("could not load properties file");
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
