package LowLevel;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Set;

import javax.swing.ImageIcon;


public class TileDictionary {
	private static HashMap<String, Image> dict;
	
	public static Image get(String name){
		return dict.get(name);
	}
	
	public static Set<String> keySet()
	{
		return dict.keySet();
	}
	
	static{
		dict = new HashMap<String, Image>();
		addEntry("dirt.jpg");
		addEntry("grass.jpg");
		addEntry("ground.jpg");
		addEntry("mud.jpg");
		addEntry("road.jpg");
		addEntry("rock.jpg");
		addEntry("wood.jpg");
	}
	
	private static void addEntry(String fileName){
		if(dict.get(fileName) == null)
			dict.put(fileName, new ImageIcon("Sprites" + File.separator + fileName).getImage());
	}
	
}
