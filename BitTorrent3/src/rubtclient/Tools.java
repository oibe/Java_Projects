/*
 * Brian Flowers, Onwukike Ibe, Julian Sotolongo
 */

package rubtclient;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;

public class Tools {
	
	public  void printBitField(TorrentInfo tor, byte[] bitField){
		for(int i = 0; i < tor.piece_hashes.length;i++){
		/*	if(Peer.examineBitField(bitField, i)==false){
				System.out.print(i +":");
				System.out.println(0);
				
			}
			else
			{
				System.out.print(i +":");
				System.out.println(1);
			}*/
			
		}
	}
	public static void fileSHA(TorrentInfo info,ArrayList<byte[]> list){
		for(int i = 0; i < list.size();i++){
			byte[] shapiece = Tools.SHA1(list.get(i));
			Tools.verifySHA1(info.piece_hashes[i],shapiece);
		}
	}
	/**
	 * Compares a given Hash to another to confirm they are the same 
	 * @param obj -Original Hash
	 * @param copy -The second hash, it is compared to the original
	 */
	public static boolean verifySHA1(ByteBuffer obj, byte[] copy){
		byte[] original = obj.array();
		if(original.length != copy.length){
			//System.out.println("lengths not equal =(");
		}
		int length = original.length;
		//printByteArray(original);
		//printByteArray(copy);
		for(int i = 0; i < length; i++){
			if(original[i]!=copy[i])
			{
				//System.out.println("SHA1 HASHES BAD KEEP TRYING");
				//System.out.println("false");
				return false;
			}
			else
			{
				//System.out.println("SUCCESS");
			}
		}
		//System.out.println("true");
		
		
		return true;
	}
	
	/***
	 * Prints out an array of bytes
	 * @param array -The byte[] to be printed
	 */
	public static void printByteArray(byte[] array)
	{
		int length = array.length;
		//System.out.print("length "+length + "\n");
		for(int i = 0;i < length;i++)
		{
			//System.out.print((int)array[i]+", ");
		}
	//System.out.println();
		return;
	} 	

	/***
	 *  Converts a bytebuffer into a String
	 * @param incoming -The byte buffer to be converted
	 * @return -The String equivalent of given ByteBuffer
	 */
	public static String byteBufferToString(ByteBuffer incoming)
	{
		int length = incoming.capacity();
		String result = "";
		for(int i = 0; i < length; i++)
		{
			result+=(char)incoming.get();
			
		}
		return result;
	}

	/***
	 * Computes the SHA1 hash of given byte[]
	 * @param piece -The byte[] to have its hash computed
	 * @return -The hash for given byte[] in a byte[]
	 */
	public static byte[] SHA1(byte[] piece)
    {
        MessageDigest md=null;
        try
        {
            md = MessageDigest.getInstance("SHA-1");
            
            md.update(piece);
        }
        catch(NoSuchAlgorithmException e)
        {System.err.println("SHA1 hash not compiled correctly..."); return null;}
        byte[] sha1hash = md.digest();
        return sha1hash;
    }

	/***
	 * Converts an int into a byte array
	 * @param i-the int to be converted
	 * @return -A byte[] representing the given int
	 */
	public static byte[] intToBytes(int i)
	{
		byte[] array = new byte[4];
		for(int z = 0; z < 4;z++)
			{
				array[z]= (byte)((i >> (3-z)*8)& 0xFF);
			}
		return array;
	}
	
	/***
	 * Converts a byte[] into a int 
	 * @param array -The byte[] to be converted
	 * @return -An int representing the given byte[]
	 */
	public static int bytesToInt(byte[] array){
		/*int i = 0;
		for(int z = 0; z < 4;z++)
		{
			i += (int)((array[z] << (3-z)*8)& 0xFF);
		}
		return i;*/
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.put(array);
		bb.rewind();
		int unsignedByteVal= bb.getInt();
		return unsignedByteVal;
	}
	
	/**
	 * Converts a bitset to a byte[]
	 * @param bits byteset to be converted
	 * @return byte][ representation of the bytes
	 */
	public static byte[] BitstoByteArray(BitSet bits) 
	{ 
		byte[] bytes = new byte[(bits.length()/8)+1]; 
		for (int i=0; i<bits.length(); i++) 
		{ if (bits.get(i)) 
			{ 
				bytes[bytes.length-i/8-1] |= 1<<(i%8); 
			} 
		} 
		return bytes; 
	} 
	
	/***
	 * Converts unsigned an unsigned byte into an int
	 * @param b-The given byte
	 * @return -An int representing the given byte
	 */
	public static int unsignedByteToInt(byte b) {
		    return (int) b & 0xFF;
		    }
	
	/**
	 * Identifies whether the Char in question is an alpha-numeric character
	 * 
	 * @param ch -Char for confirmation
	 * @return -True if ch is alphanumeric Character, False otherwise
	 */
	public static boolean isStringChar(char ch) {
	    if (ch >= 'a' && ch <= 'z')
	    {
	    	return true;
	    }
	    else if (ch >= 'A' && ch <= 'Z')
	    {
	      return true;
	    }
	    else if (ch >= '0' && ch <= '9')
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
	    
	  }

	/**
	 * Converts given hash into a String
	 * @param hash -Hash to be converted
	 * @return -String representing given hash
	 */
	public static String hashToString(ByteBuffer hash)
	{
		String escapedString="";
		
		String num;
		
		for(int i = 0;i < hash.capacity();i++)
		{
			byte c=hash.get(i);
			if(isStringChar((char)c))
			{
				escapedString= escapedString +((char) c);
				
			}
			else
			{
				num =Integer.toHexString(unsignedByteToInt(c));
				if(num.length() == 1)
				{
					num = "0" + num;
					//System.out.println("single digit " +num);
				}
				num = "%"+num;
				escapedString = escapedString + num;
			}
			
		}
		
		//System.out.println(escapedString);
		return escapedString;
	}

	/***
	 * Converts a byte Array into a String using chars as an intermediary
	 * 
	 * @param incoming -byte array to be converted
	 * @return -A string conversion of the original byte array
	 */
	public static String byteArrayToString(byte[] incoming)
	{
		int length = incoming.length;
		String result = "";
		for(int i = 0; i < length; i++)
		{
			result+=(char)incoming[i];
			
		}
		return result;
	}
	
	/***
	 * Combines two byte[]s
	 * @param one -First byte[]
	 * @param two Second byte[], it is appended to the end of the first one
	 * @return -A byte[] containing the information from both byte[]s
	 */
	public static byte[] combineArray(byte[] one, byte[] two)
	{
		byte[] array = new byte[one.length + two.length];
		int i;
		for(i = 0;i <one.length;i++){
			array[i]= one[i];
		}
		for(int j = 0;j < two.length;j++ ){
			array[i]=two[j];i++;
		}
		return array;
	}
	
	/***
	 * Prints byte array into Hexidecimal
	 * @param array-Byte[] to be printed
	 */
	public static void modPrintByteArray(byte[] array)
	{
		//int length = array.length;
		for(int i = 0;i < 100;i++)
		{
			System.out.print(Integer.toHexString((int)array[i])+", ");
		}
	System.out.println();
		return;
	} 
	
	public static int countUnchoked(ArrayList<ProtoPeer> peers){
		int unchoked = 0;
		for(int x=0; x<peers.size(); x++){
			if(!peers.get(x).mychoking) unchoked++;
		}
		return unchoked;
	}

	public static boolean addPeer(ProtoPeer peer, ArrayList<ProtoPeer> peers){
		for(int x=0; x<peers.size(); x++){
			if(peers.get(x).equals(peer)) return false;
		}
		peers.add(peer);
		return true;
	}
}
