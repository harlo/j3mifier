package framework;

import gpg.GPGException;
import gpg.GPGWrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.JSONTreeSearcher;
import util.Util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class J3mMetadataProcessor extends FileProcessor{
	
	private List<File> fileTrail;
	private String uniquePrefix;

	public J3mMetadataProcessor(File sourceFile, File outputFolder) {
		super(sourceFile, outputFolder);
		fileTrail = new ArrayList<File>();
		uniquePrefix = String.valueOf(System.currentTimeMillis());
	}

	public void processMetadata() throws Exception{
		if (!JSONTreeSearcher.isJSON(getSourceFile())) {
			toJSON();
		}
		File sig = extractSignature();
		if (sig != null) {
			try {
				verifySignature();
			}catch (Exception e) {
				FrameworkProperties.processError("Failed to verify signature for file " + getSourceFile(), e);
			}

		}
		try {
			parseKeyWords();
		}catch (Exception e) {
			FrameworkProperties.processError("Failed to extract keywords " + getSourceFile(), e);
		}
	}
	
	/**
	 * As metadata files are zipped, maybe encrypted, and lastly base 64 encoded, 
	 * this method performs the reverse - base 64 decode, attempt decryption, 
	 * This method performs all neccessary operations in order to get the file into JSON format for further processing
	 * @param sourceFile
	 * @param outputFile
	 * @throws IOException
	 */
	public void toJSON() throws Exception{
		//decode
		File result = new File (getSourceFile().getParent(), uniquePrefix +  getSourceFile().getName() + ".from64");
		fileTrail.add(result);
		try {
			Util.decodeBase64File(getSourceFile(), result);
		} catch (Exception e) {
			FrameworkProperties.processError("Failed to base64 decode file " + getSourceFile(), e);
		}
		
		//check if it's encrypted
		BufferedReader reader = new BufferedReader(new FileReader(result));
		if (reader.readLine().contains("BEGIN PGP MESSAGE")){
			File encrypted = result;
			result = new File (getSourceFile().getParent(), uniquePrefix +  getSourceFile().getName() + ".decrypted");
			fileTrail.add(result);
			try {
				gpgDecrypt(encrypted, result );
			} catch (GPGException e) {
				FrameworkProperties.processError("Failed to decrypt file " + encrypted, e);
			}
		}
		
		//unzip
		try {
			File zipped = result;
			result = new File (getSourceFile().getParent(), uniquePrefix +  getSourceFile().getName() + ".unzipped");
			fileTrail.add(result);
			Util.unGzip(zipped, result);
			setSourceFile(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FrameworkProperties.processError("Failed to ungzip file " + result, e);
		}
	}
	
	
	
	public void gpgDecrypt(File sourceFile, File outputFile) throws GPGException{
		GPGWrapper gpgWrapper = new GPGWrapper();
		gpgWrapper.decrypt(sourceFile, outputFile);
	}
	
	/**
	 * returns TRUE when a signature block is present and is verified, FALSE in all other cases
	 * @param sourceFile
	 * @return
	 * @throws IOException 
	 * @throws GPGException 
	 */
	public void verifySignature() throws GPGException, IOException{
		File sigFile = extractSignature();
	 	GPGWrapper gpgWrapper = new GPGWrapper();

		if (!gpgWrapper.verifySignature(getSourceFile(), sigFile)) {
			throw new GPGException("Could not verify signature " + sigFile.getName() + " for file " + getSourceFile().getName());
		}	
	}
	

	/**
	 * Looks for a signature block as per the path provided in the propeties,
	 * if one is present, extracts it into a separate file and removes any charcater encoding
	 * Extracts the nested j3m data, and overwrites the  @param sourceFile
	 * @param sourceFile
	 * @return the File containing the signature
	 * @throws IOException 
	 */
	public File extractSignature() throws IOException {
		File sigFile = null;
		
		JSONTreeSearcher jsonSearcher = new JSONTreeSearcher(getSourceFile(), FrameworkProperties.getInstance().getSignatureContainer().split("\\."), true);
		if (jsonSearcher.getEndElement()!= null) {
			// ok, we got a signature
			sigFile = new File (getSourceFile().getParent(), getSourceFile().getName() + ".sig");
			FileWriter fw = new FileWriter(sigFile);
			fw.write(jsonSearcher.getEndElement().getAsString());
			fw.close();
			
			//have to get the main j3m content out too
			jsonSearcher = new JSONTreeSearcher(getSourceFile(), FrameworkProperties.getInstance().getJ3mContainer().split("\\."), true);
			jsonSearcher.performSearch();
			if (jsonSearcher.getEndElement() != null) {
				File j3mFile = new File (getSourceFile().getParent(), getSourceFile().getName() +  ".json");
				Gson gson = new Gson(); // Or use new GsonBuilder().create();
				String json = gson.toJson(jsonSearcher.getEndElement());
				fw = new FileWriter(j3mFile);
				fw.write(json);
				fw.write("\n");//TODO move to preperties as an option
				fw.close();
				setSourceFile(j3mFile);
			}else {
				//well, if we can't get the main j3m out, the signature wont verify, for one thing...'
				//TODO somethibng
			}
		}
		return sigFile;
	}

	/**
	 * Uses google's gson lib to parse keywords out of metadata into a separate json file, probably badly
	 * this all seems very iffy and un-java-ry
	 * @param sourceFile
	 * @param outputFile
	 * @throws IOException
	 */
	public void parseKeyWords() throws IOException{
		File keyWordFile = new File (getOutputFolder(),
				"key_words_" + getSourceFileName() + "." + 
				FrameworkProperties.getInstance().getImageKeywordsFileExt());
		
		List<String> exclusions = FrameworkProperties.getInstance().getKeywordExclussions();
		List<String> keywordList = new ArrayList<String>();
		
		for (String container : FrameworkProperties.getInstance().getKeywordContainers()) {
			String[] path = container.split("\\.");
			JSONTreeSearcher jsonSearcher = new JSONTreeSearcher(getSourceFile(), path);
			List<String> values = jsonSearcher.performSearch();
			
			for (String value : values){
				if (value != null) {
					String[] keywords = value.toString().split(" ");
					for (int i = 0; i < keywords.length; i++) {
						if (!exclusions.contains(keywords[i])) {
							keywordList.add(keywords[i]);
						}
					}
				}
			}
		}
		JsonWriter jsonWriter;
		jsonWriter = new JsonWriter(new FileWriter(keyWordFile));
		jsonWriter.beginObject();
		jsonWriter.name("keywords");
		jsonWriter.beginArray();
		for (String keyword : keywordList) {
			jsonWriter.value(keyword);
		}
		jsonWriter.endArray();
		jsonWriter.endObject(); // }
		jsonWriter.close();
	}
}
