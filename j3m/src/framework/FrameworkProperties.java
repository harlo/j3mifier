package framework;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class FrameworkProperties {


	private Properties properties;
	private static FrameworkProperties instance;
	
	private void setProperties (Properties properties){
		this.properties = properties;
	}
	
	public String getOutputFolder() {
		return properties.getProperty("output_folder");
	}
	public String getTestImage() {
		return properties.getProperty("test_image");
	}
	public String getTestVideo() {
		return properties.getProperty("test_video");
	}
	public String getTestJ3M() {
		return properties.getProperty("test_j3m");
	}
	public String getJ3MGetMetadata() {
		return properties.getProperty("j3m_get_metadata");
	}
	public String getFfmpegVersion() {
		return properties.getProperty("ffmpeg_version");
	}
	public String getFfmpegGetAttachment() {
		return properties.getProperty("ffmpeg_get_attachement");
	}
	public List<String> getVideoInputTypes() {
		return Arrays.asList(properties.getProperty("video_input_types").split(","));
	}
	public List<String> getImageInputTypes() {
		return Arrays.asList(properties.getProperty("image_input_types").split(","));
	}
	public List<String> getImageKeywordContainers() {
		return Arrays.asList(properties.getProperty("image_keyword_container_elements").split(","));
	}
	public List<String> getImageKeywordExclussions() {
		return Arrays.asList(properties.getProperty("image_keywords_excluded_words").split(","));
	}
	public String getVideoInputTypesString() {
		return properties.getProperty("video_input_types");
	}
	public String getImageInputTypesString() {
		return properties.getProperty("image_input_types");
	}
	public String getImageMetadataFileExt() {
		return properties.getProperty("image_metadata_file_ext");
	}
	public String getImageKeywordsFileExt() {
		return properties.getProperty("image_keywords_file_ext");
	}
	public String getVideoMetadataFileExt() {
		return properties.getProperty("video_metadata_file_ext");
	}
	public String getVideoStillFileExt() {
		return properties.getProperty("video_still_file_ext");
	}
	public String getVideoConvertedFormat() {
		return properties.getProperty("video_converted_format");
	}
	public String getThumbFileExt() {
		return properties.getProperty("image_thumb_file_ext");
	}
	public int getThumbHeight() {
		return Integer.parseInt(properties.getProperty("image_thumb_height"));
	}
	public int getThumbWidth() {
		return Integer.parseInt(properties.getProperty("image_thumb_width"));
	}
	public int getImageSmallHeight() {
		return Integer.parseInt(properties.getProperty("image_small_height"));
	}
	public int getImageSmallWidth() {
		return Integer.parseInt(properties.getProperty("image_small_width"));
	}
	public int getImageMedHeight() {
		return Integer.parseInt(properties.getProperty("image_med_height"));
	}
	public int getImageMedWidth() {
		return Integer.parseInt(properties.getProperty("image_med_width"));
	}
	public int getImageLargeHeight() {
		return Integer.parseInt(properties.getProperty("image_large_height"));
	}
	public int getImageLargeWidth() {
		return Integer.parseInt(properties.getProperty("image_large_width"));
	}
	public String getVideoSmallHeight() {
		return properties.getProperty("video_small_height");
	}
	public String getVideoSmallWidth() {
		return properties.getProperty("video_small_width");
	}
	public String getVideoMedHeight() {
		return properties.getProperty("video_med_height");
	}
	public String getVideoMedWidth() {
		return properties.getProperty("video_med_width");
	}
	public String getVideoLargeHeight() {
		return properties.getProperty("video_large_height");
	}
	public String getVideoLargeWidth() {
		return properties.getProperty("video_large_width");
	}
	public String getffmpegChangeFormat() {
		return properties.getProperty("ffmpeg_change_format");
	}
	public String getffmpegChangeResolution() {
		return properties.getProperty("ffmpeg_change_resolution");
	}
	public String getffmpegCreateStill() {
		return properties.getProperty("ffmpeg_create_still");
	}

	protected FrameworkProperties() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public static FrameworkProperties getInstance() {
	   if(instance == null) {
		  
		  instance = new FrameworkProperties(); 
	
		  Properties _properties = new Properties();
	   	  try {
			_properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties"));
			} catch (Exception e) {
				throw new RuntimeException("Could not load properties file", e);
			}
	   	  instance.setProperties(_properties);
	   }
	   return instance;
	}
	
}
