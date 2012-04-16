
package rubtclient;

import java.io.Serializable;

public class Piece implements Serializable{
	byte[] classArray;
	public Piece(byte[] array){
		classArray = array;
	}
}