package LowLevel;

import java.awt.Dimension;
import java.awt.Image;
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

public class Chunk {
	private static Dimension size;
	private final static String confFile = "Conf" + File.separator
			+ "ChunkConf.xml";

	private char[][] map;
	private Dimension chunkCoords;
	private HashMap<Character, String> tileSpriteDict;
	private HashMap<String, Character> tileCharDict;

	static {
		if (!new File(confFile).exists()) {
			createConf();
		}

		loadConf();
	}

	public static Dimension getSizeByTiles(){ return (Dimension)size.clone(); }
	
	public String getFileName() {
		return "Chunks" + File.separator + "chunk." + chunkCoords.width + "."
				+ chunkCoords.height + ".xml";
	}

	public Chunk(int x, int y) {
		chunkCoords = new Dimension(x, y);
		map = new char[size.width][size.height];
		if (!new File(getFileName()).exists()) {

			tileSpriteDict = new HashMap<Character, String>();
			tileSpriteDict.put((char) 0, "grass.jpg");
			tileCharDict = new HashMap<String, Character>();
			tileCharDict.put("grass.jpg",(char) 0);
		}else{
			readChunk();
		}
	}
	
	public void setTexture(int x, int y, String tex){
		char texChar;
		if( tileCharDict.get(tex) != null){
			texChar = tileCharDict.get(tex);
		}else
		{
			texChar = (char)tileSpriteDict.size();
			tileCharDict.put(tex, texChar);
			tileSpriteDict.put(texChar, tex);
		}
	}

	public int numTextures() {
		return tileSpriteDict.keySet().size();
	}

	public void readChunk() {
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(new File(getFileName()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		chunkCoords = new Dimension();
		tileSpriteDict = new HashMap<Character, String>();
		tileCharDict = new HashMap<String, Character>();
		try {
			chunkCoords.width = doc.getRootElement().getAttribute("X")
					.getIntValue();
			chunkCoords.height = doc.getRootElement().getAttribute("Y")
					.getIntValue();

			for (Object e : doc.getRootElement().getChild("TileTextures")
					.getChildren("Texture")) {
				tileSpriteDict.put((char) ((Element) e).getAttribute(
						"Character").getIntValue(), ((Element) e).getAttribute(
						"Name").getValue());
				tileCharDict.put(((Element) e).getAttribute(
						"Name").getValue(), (char) ((Element) e).getAttribute(
						"Character").getIntValue());
			}

			for (Object c : doc.getRootElement().getChild("Map").getChildren("C")) {
				for (Object r : ((Element) c).getChildren("R")) {
					map[((Element)c).getAttribute("id").getIntValue()][((Element)r).getAttribute("id").getIntValue()] = (char) Integer.parseInt(((Element)r).getText());
				}

			}
		} catch (DataConversionException e) {
			e.printStackTrace();
		}

	}

	public void writeChunk() {
		Element root = new Element("chunk");
		root.setAttribute("X", "" + chunkCoords.width);
		root.setAttribute("Y", "" + chunkCoords.height);

		Element element = new Element("TileTextures");
		for (Character c : tileSpriteDict.keySet()) {
			Element texture = new Element("Texture");
			texture.setAttribute("Character", "" + (int) c.charValue());
			texture.setAttribute("Name", tileSpriteDict.get(c));
			element.addContent(texture);
		}
		root.addContent(element);

		element = new Element("Map");
		for (int x = 0; x < size.width; x++) {
			Element column = new Element("C");
			column.setAttribute("id", "" + x);
			for (int y = 0; y < size.height; y++) {
				Element row = new Element("R");
				row.setAttribute("id", "" + y);
				row.setText("" + (int) map[x][y]);
				column.addContent(row);
			}
			element.addContent(column);
		}
		root.addContent(element);

		Document doc = new Document(root);

		XMLOutputter out = new XMLOutputter();
		FileOutputStream fo = null;

		try {
			fo = new FileOutputStream(new File(getFileName()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			out.output(doc, fo);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Image getTile(int x, int y) {
		return TileDictionary.get(tileSpriteDict.get(map[x][y]));
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
			size.width = doc.getRootElement().getAttribute("SizeX")
					.getIntValue();
			size.height = doc.getRootElement().getAttribute("SizeY")
					.getIntValue();
		} catch (DataConversionException e) {
			e.printStackTrace();
		}
	}

	private static void createConf() {
		Element root = new Element("ChunkConf");
		root.setAttribute("SizeX", "32");
		root.setAttribute("SizeY", "32");

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

	public static void main(String[] args) {
		

	}

}
