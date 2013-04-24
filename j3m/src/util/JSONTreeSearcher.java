package util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JSONTreeSearcher {
	
	private File json;
	private String[] path;
	private List<String> results;
	private List<JSONBranch> queue;

	public JSONTreeSearcher(File json, String[] path) {
		super();
		this.json = json;
		this.path = path;
		results = new ArrayList<String>();
		queue = new ArrayList<JSONBranch>();
	}
	
	public List<String> performSearch () throws IOException{
		JsonParser parser = new JsonParser();
		JsonElement metadata = parser.parse(new FileReader(json));
		
		queue.add(new JSONBranch(metadata,path));
		processQueue();
		return results;
	}
	
	private void processQueue(){
		while(queue.size() > 0 ){
			JSONBranch branch = queue.remove(0);
			JsonElement element = branch.getHead();
			String[] currentPath = branch.getPath();
			boolean keepLooking = true;
			int i = 0;
			for (i = 0; i < currentPath.length && keepLooking; i++) {
				if (element.isJsonObject()){
					element = element.getAsJsonObject().get(currentPath[i]);
				}else if(element.isJsonArray()){
					//sigh
					JsonArray array = element.getAsJsonArray();
					String[] subPath = Arrays.copyOfRange(currentPath, i, currentPath.length);
					for (int j = 0; j < array.size(); j++) {
						queue.add(new JSONBranch(array.get(j),subPath));
					}
					//bail out
					keepLooking = false;
				}else {
					//bail out
					keepLooking = false;
				}
			}
			if (i == currentPath.length && keepLooking){
				results.add(element.getAsString());
			}
		}
		
	}
	
	private class JSONBranch {
		private String[] path;
		private JsonElement head;
		public JSONBranch(JsonElement head, String[] path) {
			super();
			this.path = path;
			this.head = head;
		}
		public String[] getPath() {
			return path;
		}
		public JsonElement getHead() {
			return head;
		}
		
	}
	
}
