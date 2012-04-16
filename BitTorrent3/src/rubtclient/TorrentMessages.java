/*
 * Brian Flowers, Onwukike Ibe, Julian Sotolongo
 */

package rubtclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;

public class TorrentMessages {

	public TorrentMessages()
	{}
	
	/***
	 * 
	 * @return -A keepalive message
	 */
	public byte[] keepalive()
	{
	 	byte[] alive={0,0,0,0};
		return alive;
	}
	/**
	 * 
	 * @return -The choke message
	 */
	public byte[] choke()
	{
	 	byte[] block={0,0,0,1,0};
		return block;
	}
	/**
	 * 
	 * @return-An unchoke message
	 */
	public byte[] unchoke()
	{
	 	byte[] heimlich={0,0,0,1,1};
		return heimlich;
	}
	/**
	 * 
	 * @return -The interested message
	 */
	public byte[] interested()
	{
	 	byte[] want={0,0,0,1,2};
		return want;
	}
	/**
	 * 
	 * @return -The uninterested message
	 */
	public byte[] uninterested()
	{
	 	byte[] donotwant={0,0,0,1,3};
		return donotwant;
	}
	/**
	 * 
	 * @return -The Have message
	 */
	public static byte[] have(byte index)
	{
	 	byte[] mine={0,0,0,5,4,0,0,0,index};
		return mine;
	}
	
	public byte[] bitfield(int[] status,int piecenumber)
	{
		int length=((int) Math.ceil(piecenumber/8))+5;
		byte[] bf = new byte[length];
		BitSet have=new BitSet();
		bf[0] = 0;
		bf[1] = 0;
		bf[2] = 0;
		bf[3] = (byte)(length-5);
		bf[4]=5;
		for (int i=0;i<piecenumber;i++)
		{
			if (status[i+1]==1)
			{
				have.set(i,true);
			}
			else
			{have.set(i,false);}		
		}
		byte[] end=Tools.BitstoByteArray(have);
		for (int i=0;i<(Math.ceil(length-5));i++)
		{
			//System.out.print("Entry "+i+"is "+end[i]);
		}
		for (int i=0;i<(Math.ceil(length-5));i++)
		{
			bf[5+i]=end[i];
		}
		//This needs to initialize the beginning of the bitfield properly	
		//We don't want to send a piece that is currently downloading
		return bf;
	}
	public byte[] piece(byte[] request, byte[] piece){
		byte[] sendpiece = new byte[1028];
		for(int x=0; x<1028; x++){
			sendpiece[x]=0;
		}
		int reqindex = request[5];
		int reqbegin = request[6];
		int reqlength = request[7];
		
		return sendpiece;
	}
	
	/**
	 * Creates a handshake for peers
	 * @param info -The torrentinfo object, contains the SHA1 hash
	 * @param id -The user's peer ID
	 * @return -The handshake message
	 */
	public byte[] handshake(TorrentInfo info, String id)
	{
		
		byte dec = 19;
		String proto = "BitTorrent protocol";
		byte zero = 0;
		//String hash = hashToString(info.info_hash);
		int malloc =(1+proto.length()+8 + info.info_hash.capacity() + id.length());
		//System.out.println("malloc "+ malloc);
		byte array[] = new byte[malloc];
		//byte array[] = new byte[1024];
		array[0]= dec;
		int i;
		//System.out.println("proto length " + proto.length());
		for(i =0; i < proto.length();i++)
		{
			array[i+1]=(byte) proto.charAt(i);
			//System.out.println("col "+array[i+1]);
		}
		i++;
		for(int j = 0;j < 8;j++)
		{
			array[i]=zero;
			i++;
		}
		for(int j = 0;j < 20;j++)
		{
			array[i]= info.info_hash.get(j);
			//System.out.println("info hash = "+info.info_hash.get(j));
			i++;
		}
	
		for(int j=0;j < 20;j++)
		{
			array[i]= (byte)id.charAt(j);
			i++;
		}
		
		//printByteArray(array);
		return array;
	}
	
	/**
	 * The HTTP Get request for a tracker
	 * @param info -The torrentinfo, contains the object's SHA1 hash 
	 * @param id -The user's peer ID
	 * @return
	 */
	public String getRequestString(TorrentInfo info, String id)
	{
		String rocky = info.announce_url.toString();
		String rockythesequel = "?port=56969";//<your peer's port"
		String rockythesequel2 = "&peer_id="+id;
		String rockythesequel3 = "&event=started&info_hash=".concat(Tools.hashToString(info.info_hash));
		String rockythesequel4 = "&uploaded=0&left=".concat(Integer.toString(info.file_length));//file_length or file_size
		String rockybalboa = "&downloaded=0";
		String epicmarathon = rocky +rockythesequel +rockythesequel2+ rockythesequel3 +rockythesequel4 +rockybalboa;
		
		//STUFF HERE
		return epicmarathon;
	}
	public String getRequestString(TorrentInfo info, String id,String event, int uploaded,int downloaded,int left)
	{
		String rocky = info.announce_url.toString();
		String rockythesequel = "?port=56969";//<your peer's port"
		String rockythesequel2 = "&peer_id="+id;
		String rockythesequel3;
		if (!event.equals(""))
		{
			rockythesequel3 = "d&info_hash=".concat(Tools.hashToString(info.info_hash));
		}
		else
		{
			rockythesequel3 = "&event="+event+"d&info_hash=".concat(Tools.hashToString(info.info_hash));
		}
		
		String rockythesequel4 = "&uploaded="+uploaded+"&left="+left+"".concat(Integer.toString(info.file_length));//file_length or file_size
		String rockybalboa = "&downloaded="+downloaded;
		String epicmarathon = rocky +rockythesequel +rockythesequel2+ rockythesequel3 +rockythesequel4 +rockybalboa;
		
		//STUFF HERE
		return epicmarathon;
	}
	public String getRequestString(ByteBuffer infohash, int filelength, URL announceurl, String id,String event, int uploaded,int downloaded,int left)
	{
		String rocky = announceurl.toString();
		String rockythesequel = "?port=56969";//<your peer's port"
		String rockythesequel2 = "&peer_id="+id;
		String rockythesequel3;
		if (!event.equals(""))
		{
			rockythesequel3 = "d&info_hash=".concat(Tools.hashToString(infohash));
		}
		else
		{
			rockythesequel3 = "&event="+event+"d&info_hash=".concat(Tools.hashToString(infohash));
		}
		
		String rockythesequel4 = "&uploaded="+uploaded+"&left="+left+"".concat(Integer.toString(filelength));//file_length or file_size
		String rockybalboa = "&downloaded="+downloaded;
		String epicmarathon = rocky +rockythesequel +rockythesequel2+ rockythesequel3 +rockythesequel4 +rockybalboa;
		
		//STUFF HERE
		return epicmarathon;
	}
	/***
	 * 
	 * @param index -Index location for the requested piece
	 * @param offset -byte offset within the piece
	 * @param length -The length of the piece being requested
	 * @return
	 */
	public byte[] pieceRequestArray(int index,int offset, int length)
	{
		byte[] indexarray = Tools.intToBytes(index);
		byte[] lengtharray = Tools.intToBytes(length);
		byte[] offsetarray = Tools.intToBytes(offset);
		//indexarray = intToByteArray
		byte[] array = new byte[17];
		array[0]= 0;
		array[1]= 0;
		array[2]= 0;
		array[3]= 13;
		array[4]= 6;
		int i = 5;
		for(int j = 0; j < 4;j++)
		{
			array[i]=indexarray[j];
			i++;
		}
		for(int j = 0; j < 4;j++)
		{
			array[i]=offsetarray[j];
			i++;
		}
		for(int j = 0; j < 4;j++)
		{
			array[i]=lengtharray[j];
			i++;
		}
		//printByteArray(array);
		return array;
	}
	public  byte[] getPiece( int pieceIndex, double pieceLength,DataInputStream peerinputstream, DataOutputStream peeroutputstream){
		//	System.out.println("piece length "+pieceLength);
			
			int runXTimes = (int)Math.ceil(pieceLength/16384);
			//System.out.println("runXtimes "+runXTimes);
			ArrayList<Byte> piece = new ArrayList<Byte>();
			ArrayList<Byte> junk = new ArrayList<Byte>();
			byte[] buffer = new byte[1];
			byte tempByte;
			boolean check = true;
			int c;
			int pieceReq = 16384;
			for(int i = 0; i < runXTimes;i++){
				if(pieceLength%16384 != 0){if(i == (runXTimes-1)){pieceReq = (int)pieceLength%16384;}}
				try {
					
					peeroutputstream.write(pieceRequestArray(pieceIndex,(i*16384),pieceReq));
					peeroutputstream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				byte[] ka = new byte[4];
			  
				while(check == true){
				c = 0;
				while ( c < 4) {
					    try {
							peerinputstream.read(buffer);
						} catch (IOException e) {
							e.printStackTrace();
						}
				            	ka[c]=buffer[0];
				                c++;
				    }
				int thing =Tools.bytesToInt(ka);
				//System.out.println("thing " +thing);
					if(thing!=pieceReq+9){
					
						check = true;
					}
					else
					{
						break;
					//check = false;
					}
				}
			    c=0;
			   try{
			    while ( c < 9) {
			    peerinputstream.read(buffer);
		            	tempByte=buffer[0];
		                junk.add(tempByte);c++;
		            }
			
			    	c= 0;
		            while (c < pieceReq) {
		            	
		            	peerinputstream.read(buffer);
		            	tempByte=buffer[0];
		                piece.add(tempByte);c++;
		            }
			   }catch (IOException e){
				   e.printStackTrace();
			   }
			}
			byte[] retarray = new byte[piece.size()];
			for(int i = 0; i < piece.size();i++){
				//System.out.print(piece.get(i));
				retarray[i]= piece.get(i);
			}
			//System.out.println();
			return retarray;
		}
}

