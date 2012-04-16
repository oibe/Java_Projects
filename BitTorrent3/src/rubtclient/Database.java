package rubtclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Database  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<Thread> thread_list;
	ArrayList<ProtoPeer> peer_list;
	Piece[] download; // will store the total download in progress
	int[] status; // 0=not downloaded, 1=downloaded, -1=downloading
	transient TorrentInfo torrentinfo; // contains information about the torrent
	int finished = 0;
	int totaldownload=0;
	int totalupload=0;
	transient ByteBuffer infohash;
	byte[] temphash;
	URL announceurl;
	int filelength;
	byte[] bitfield;
	int piecelength;
	int piecenumber;
	transient Object lock = new Object();
	String savepath;
	transient PNList piecenodehaves;
	public boolean suspend=false;

	/**
	 * Creates a new Database object
	 * @param status_length The length the status array should be
	 * @param tf The torrentinfo object
 	 * @param savpath The savepath for the file
 	 */
	public Database( int status_length, TorrentInfo tf,String savpath){
		torrentinfo = tf;
		//System.out.println(torrentinfo.file_length);
		announceurl=tf.announce_url;
		double dec=Math.ceil((double)((tf.file_length)/(tf.piece_length))/8);
		piecenumber= tf.piece_hashes.length;
		
		bitfield=new byte[(int)(dec+5)];
		bitfield[3]=(byte) (dec+1);
		bitfield[4]=5;
		//System.out.println("bitfield size is "+ (piecenumber/8));
		filelength=tf.file_length;
		piecelength=tf.piece_length;
		infohash=tf.info_hash;
		download = new Piece[status_length];
		status = new int[status_length];
		thread_list = new ArrayList<Thread>();
		peer_list = new ArrayList<ProtoPeer>();
		savepath=savpath;
		for(int x=0; x<status_length; x++){
			status[x] = 0;
		}
		piecenodehaves = new PNList();
		createEmptyPNList(status_length);
	}
 
 	/**
 	 * Updates the bitfield at the given index
 	 * @param index the location to be updated
 	 */
	public void updateOurBitField(int index)
	{
		//System.out.println("Piece is "+index);
		bitfield[(index/8)+5] |=(byte) (0x80>>index%8);
		return;
	}
 
	/**
	 * Updates the download array of the piece_number with the given
	 * byte array piece
	 * @param piece the piece to be placed in the file
	 * @param piece_number the number of the piece
	 * @return True if successful
	 */
	public  boolean UpdateFile(Piece piece, int piece_number){
		download[piece_number]=piece;
		status[piece_number]=1;
		totaldownload+=piece.classArray.length;
		int count=0;
		updateOurBitField(piece_number);
		piecenodehaves.deleteNode(piece_number);
		for(int i = 0; i < status.length;i++){
			if(status[i]==1){
				count++;
			}
			else if ((piece_number==75)||(piece_number==76))
			{
				System.out.println("Still missing piece "+i+" when updating "+piece_number);
			}
//			System.out.println(i+"["+status[i]+"]");
		}
		//System.out.println("\tNUMBER OF TIMES IN UPDATE FILE");
		if(count == status.length){
			System.out.println("Setting Finished flag~");
			finished = 1;
			createFile(download,savepath);
		}
		return true;
	}
	
	/**
	 * Increments the indicated piece's have in the piecenodehaves list
	 * @param piece The piece to be updated
	 * @return true if successful
	 */
	public boolean UpdateHaves(int piece){
		PieceNode temp = piecenodehaves.head;
		while(temp != null){
			if (temp.piecenumber == piece){
				temp.haves++;
				piecenodehaves.sortPNList();
				return true;
			}
			temp = temp.next;
		}
		return false;
	}
	
	/* 
	 * Allows a thread to check out a piece for download
	 * Synchronized so that only one thread may access
	 * this method at a time - avoid race conditions
	 */
	public synchronized boolean PieceCheckout(int request){
		synchronized(lock){
		if(status[request]==1 || status[request]==-1){
			return false;
		}
		else{
			status[request]=-1;
			return true;
		}
		}
	}
	
	public byte[] PieceRequest(int index, int begin, int length){
		byte piece[] = new byte[length+13];
		int z = 0;
		byte[] four = Tools.intToBytes(9+length);
		//System.out.println("prefix "+Tools.bytesToInt(four));
		for(int i = 0;i < 4;i++){
			
			piece[i]=four[z];z++;
			
		}
		
		piece[4]=7;
		four = Tools.intToBytes(index);
		//System.out.println("index "+Tools.bytesToInt(four));
		z=0;
		for(int i = 5;i < 9;i++){
			piece[i]=four[z];z++;
		}
		four = Tools.intToBytes(begin);
		//System.out.println("begin "+Tools.bytesToInt(four));
		z=0;
		for(int i = 9;i < 13;i++){
			piece[i]=four[z];z++;
			
		}
		//System.out.print("Piece Request start");
		z=0;
		for(int i = 13;i < length+13;i++){
			piece[i] = download[index].classArray[begin+z];
			z++;
		}
	//	System.out.print("Piece Request end");
		totalupload+=length;
		return piece;
	}
	public void load(TorrentInfo info)
	{
		torrentinfo=info;
		infohash=info.info_hash;
	}
	
	/**
	 * Called when a piece download fails
	 * @param piece Will set this piece's status back to 0
	 */
	public void DownloadFail(int piece){
		status[piece]=0;
		return;
	}
	
	/**
	 * 
	 * @param piecelist
	 * @param savepath
	 */
	public void createFile(Piece[] piecelist,String savepath)
	{
		//savepath+="\thing.gif";
		File f=new File(savepath);
		FileOutputStream fop = null;
	    try {
			fop=new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			System.err.println("Error: File not found");
			return;
		}
		try {
		for(int i= 0; i< (status.length-1);i++){
			fop.write(piecelist[i].classArray);
		}
		fop.close();
		} catch (IOException e) {
			System.err.println("Error: Could not write to file");
			return;
		}
		
	}

	private void createEmptyPNList(int length){
		for(int x=0; x<length; x++){
			PieceNode addme = new PieceNode(x, 0);
			piecenodehaves.addToRear(addme);
		}
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{ 	 
		 //ByteBuffer to byte[]
		 byte[] bytes = new byte[infohash.capacity()];
		 for(int x = 0; x<bytes.length; x++){
			 bytes[x] = infohash.get(x);
		 }
		 temphash=bytes;
		 
		 out.defaultWriteObject();
	}
	
	private void readObject(java.io.ObjectInputStream in)
     throws IOException, ClassNotFoundException
 	{
		//System.out.println("Uses my special read... Oh! and suspend="+suspend);
		in.defaultReadObject();
		// Re-initialize the PNList to empty
		suspend=false; //Reset Suspension
		for (int i=0;i<piecenumber+1;i++) //All pieces in the middle of being downloaded are discarded
		{
			//System.out.println("Piece reset");
			if(status[i]==-1)
			{
				status[i]=0; 
			}
		}
		piecenodehaves = new PNList();
		createEmptyPNList(status.length);
	 	//byte[] to ByteBuffer
		byte[] bytes = new byte[temphash.length];
		infohash = ByteBuffer.wrap(bytes);
		lock=new Object();
	}
 }
