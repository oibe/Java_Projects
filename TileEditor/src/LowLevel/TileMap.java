package LowLevel;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;



public class TileMap {
	private final static String confFile = "Conf" + File.separator
	+ "TileMapConf";
	private static Dimension size;

	HashMap<String,Chunk> cache;
		
	private Chunk[][] chunks;	
	
	static {
		if (!new File(confFile).exists()) {
			createConf();
		}

		loadConf();
	}
	
	public TileMap(int width, int height){
		cache = new HashMap<String, Chunk>();
		chunks = new Chunk[width][height];
		for (int x = 0; x < width; x++)
			for(int y = 0; y < height; y++){
				chunks[x][y] = new Chunk(x, y);
				chunks[x][y].writeChunk();
			}
	}
	
	public Chunk getChunk(int x, int y){
		if(cache.containsKey((x + "." + y)))
			return cache.get(x + "."+ y);
		cache.put(x+"."+y,new Chunk(x,y));
		return new Chunk(x,y);
	}
	
	private static void loadConf() {
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(new File(confFile));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		size = new Dimension();
		try {
			size.width = doc.getRootElement().getAttribute("SizeX").getIntValue();
			size.height = doc.getRootElement().getAttribute("SizeY").getIntValue();
		} catch (DataConversionException e) {
			e.printStackTrace();
		}
	}

	private static void createConf() {
		Element root = new Element("TileMapConf");
		root.setAttribute("SizeX", "4");
		root.setAttribute("SizeY", "4");

		Document doc = new Document(root);

		XMLOutputter out = new XMLOutputter();
		FileOutputStream fo = null;

		try {
			fo = new FileOutputStream(new File(confFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			out.output(doc, fo);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new TileMap(4, 4);
	}
}
