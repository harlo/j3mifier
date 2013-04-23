package tests;

import java.io.File;


import org.junit.Assert;
import org.junit.Test;

import framework.FrameworkProperties;
import framework.MediaProcessor;

public class MediaProcessorTests {
	@Test
	public void processInputNoArgsTest() throws Exception {
		MediaProcessor processor = new MediaProcessor();
		String[] args = null;
		try {
			processor.processInput(args);
		}catch (Exception e) {
			return;
		}
		Assert.fail("Didnt detect missing arguments");
	}
	@Test
	public void processInputNullArgsTest() throws Exception {
		MediaProcessor processor = new MediaProcessor();
		String[] args = {null, null};
		try {
			processor.processInput(args);
		}catch (Exception e) {
			Assert.assertNotNull(e.getMessage());
			return;
		}
		Assert.fail("Didnt detect missing arguments");
	}
	@Test
	public void processInputNoArgTest() throws Exception {
		MediaProcessor processor = new MediaProcessor();
		String[] args = {"something", null};
		try {
			processor.processInput(args);
		}catch (Exception e) {
			Assert.assertNotNull(e.getMessage());
			return;
		}
		Assert.fail("Didnt detect missing argument");
	}
	
	@Test
	public void processInputNoArgTest2() throws Exception {
		MediaProcessor processor = new MediaProcessor();
		String[] args = {null, "something"};
		try {
			processor.processInput(args);
		}catch (Exception e) {
			Assert.assertNotNull(e.getMessage());
			return;
		}
		Assert.fail("Didnt detect missing argument");
	}

	@Test
	public void processInputNoFileTest() throws Exception {
		MediaProcessor processor = new MediaProcessor();
        FrameworkProperties config = FrameworkProperties.getInstance();
		String[] args = {"something", config.getOutputFolder()};
		try {
			processor.processInput(args);
		}catch (Exception e) {
			Assert.assertNotNull(e.getMessage());
			return;
		}
		Assert.fail("Didnt detect missing argument");
	}
	@Test
	public void processInputNoFolderTest() throws Exception {
		MediaProcessor processor = new MediaProcessor();
        FrameworkProperties config = FrameworkProperties.getInstance();
		String[] args = {config.getTestImage(), "stuff"};
		try {
			processor.processInput(args);
		}catch (Exception e) {
			Assert.assertNotNull(e.getMessage());
			return;
		}
		Assert.fail("Didnt detect missing argument");
	}
	@Test
	public void processInputWrongFileTypeTest() throws Exception {
		MediaProcessor processor = new MediaProcessor();
        FrameworkProperties config = FrameworkProperties.getInstance();
        File file = File.createTempFile("name", ".blah");
		String[] args = {file.getAbsolutePath(), config.getOutputFolder()};
		try {
			processor.processInput(args);
		}catch (Exception e) {
			Assert.assertNotNull(e.getMessage());
			return;
		}
		Assert.fail("Didnt detect missing argument");
	}
	@Test
	public void processInputTest() throws Exception {
		MediaProcessor processor = new MediaProcessor();
        FrameworkProperties config = FrameworkProperties.getInstance();
		String[] args = {config.getTestImage(), config.getOutputFolder()};
		try {
			processor.processInput(args);
		}catch (Exception e) {
			Assert.fail("Failed on correct input");
		}
		
	}
}
