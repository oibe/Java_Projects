package rubtclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class ProtoPeer implements Runnable{

	 TorrentMessages message=new TorrentMessages();
	 String ipAddress;
	 int port;
	 TorrentInfo torFile;
	 DataInputStream dis;
	 DataOutputStream dos;
	 String mypeerID;
	 static Database db;
	 byte[] bitField=null;
	 boolean done = true;
	 Socket peerSocket=new Socket();
	 Thread t=new Thread(this,"ProtoPeer");
	 int started=0;
	 int downloaded=0;
	 int uploaded=0;
	 boolean mychoking=true;
	 boolean theirchoking=true;
	 boolean myinterest=false;
	 byte[] known;
	 boolean theirinterest=false;
	 int currentpiece;
	 byte[] piecetemp;
	 int noffset=-1;
	 boolean setchoke=false;
	 boolean setunchoke=false;
	 int section;
	 Date beginrate;
		int retry=0;
	ProtoPeer(TorrentInfo info,String theirip, int theirport,String peerID,Database d)
	{
		mypeerID=peerID;
		torFile=info;
		db=d;
		ipAddress=theirip;
		port=theirport;
		try {
			peerSocket=new Socket(InetAddress.getByName(theirip),theirport);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	started=1;	
	}
	ProtoPeer(TorrentInfo info,Socket connection,String peerID,Database d)
	{
		torFile=info;
		peerSocket=connection;
		db=d;
		mypeerID=peerID;
		started=0;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		piecetemp=new byte[db.piecelength];
		section=(int) Math.ceil(db.piecelength/16384);
		known=new byte[db.piecenumber];
		handshake(started);
		beginrate=Calendar.getInstance().getTime();
		while (db.suspend==false)
		{
			if (setchoke==true)choke();
			if (setunchoke==true)unchoke();
			byte[] four=new byte[4];
			byte[] index=new byte[1];
			try {
				if (dis.available()>3)
				{				
					dis.read(four);
				}
				else{Output();}
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if ((four[0]==0)&&(four[1]==0)&&(four[2]==0)&&(four[3]==0))
			{
				//Output();
				//Keep Alive!
			}
			else
			{
				try {
					dis.read(index);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				switch(index[0])
				{
					case 0:theirchoking=true;/*System.out.println("OTHER "+choice);/*choke*/ break;
					case 1:theirchoking=false;Output();/*System.out.println("OTHER "+choice);/*unchoke*/ break;
					case 2:theirinterest=true;unchoke();/* System.out.println("OTHER "+choice);/*interested*/break;
					case 3:theirinterest=false; /*System.out.println("OTHER "+choice);/*not interested*/break;
					case 4:updateKnown(); System.out.println("Received Have");/*have*/break;
					case 5:fieldAnalysis(four);System.out.println("Bitfield Received!");/*bitfield*/break;
					case 6: if(mychoking==false)requestReader();else flush(12);/*request*/break;
					case 7:pieceRetrieval(four);/*piece*/break;
					default:System.out.println("Received This odd thing!"+four[0]+four[1]+four[2]+four[3]);
				}
			}		
		}
		
	}
	
	public int downloadrate()
	{
		Date endrate=Calendar.getInstance().getTime();
		if((endrate.getTime()-beginrate.getTime()) == 0) return 0;
		return (int) (downloaded/(endrate.getTime()-beginrate.getTime()));
	}
	
	public int uploadrate()
	{
		Date endrate=Calendar.getInstance().getTime();
		if((endrate.getTime()-beginrate.getTime()) == 0) return 0;
		return (int) (uploaded/(endrate.getTime()-beginrate.getTime()));
	}
	
	private boolean flush(int length)
	{
		byte[] toss=new byte[length];
		try {
		while (dis.available()<length)
		{
			
		}
			dis.read(toss);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private void handshake(int initiated){
		//Connecting to a peer and shaking hands
		if (initiated==1) //Which means We initiated the Connection/Handshake
		{
			byte[] tembuf = new byte[1];
			byte[] array = message.handshake(torFile,mypeerID);
			try {
				dos = new DataOutputStream(peerSocket.getOutputStream());
			} catch (IOException e1) {
				if (ipAddress.equals("localhost"))
				{
					System.out.print("Dummy Server Shutdown!");
					return;
				}
				System.err.println("Error: Could not get output stream from peer");
				return;
			}
			try {	
				dos.write(array);
				dos.flush();
			} catch (IOException e1) {
				System.err.println("Error: Encountered difficulty with peer communication");
				return;
			}	
			try {
				dis = new DataInputStream(peerSocket.getInputStream());
			} catch (IOException e1) {
				System.err.println("Error: Could not get input stream from peer");
			}
			byte[] peerarray = new byte[1024];
			int u = 0;
			while(u < 68 ){
				try {
					dis.read(tembuf);
					peerarray[u]= tembuf[0];
				} catch (IOException e) {
					e.printStackTrace();
				}
				u++;
				}
				try {
					dos.write(db.bitfield);
					dos.flush();
					dos.write(message.interested());
					dos.flush();
					myinterest=true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		else
		{
			byte[] tempbuf = new byte[1];
			try {dos = new DataOutputStream(peerSocket.getOutputStream());} catch (IOException e1) 
			{System.err.println("Error: Could not get output stream");}	
			//Create input stream
			DataInputStream uploadinputstream = null;
			try {dis = new DataInputStream(peerSocket.getInputStream());} catch (IOException e1) {System.err.println("Error: Could not get input stream from peer");}
			byte[] peerarray = new byte[68];
			int x = 0;
			//Read handshake from peer
			//System.out.println("Starting to receive Handshake~");
			while(x < 68 ){try {dis.read(tempbuf);peerarray[x]= tempbuf[0];}catch (IOException e) {System.err.println("Error: Could not read handshake from peer");}x++;}
			//System.out.println("PEERARRRAY ");
			//write handshake to peer
			byte[] handshake = message.handshake(torFile,mypeerID);
			try {dos.write(handshake);dos.flush();dos.write(db.bitfield);dos.flush();}catch (IOException e) {System.err.println("Error: Could not write handshake to peer");return;}
		}

	}
	private void choke()
	{
		try {
			dos.write(message.choke());
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mychoking=true;
		setchoke=false;
	}
	private void unchoke()
	{
		System.out.println("Looks like we got someone's attention!");
		mychoking=false;
		try {
			dos.write(message.unchoke());
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		beginrate=Calendar.getInstance().getTime();
		uploaded=0;
		downloaded=0;
		setunchoke=false;
	}
	public boolean equals(ProtoPeer beta)
	{
		if (peerSocket.equals(beta.peerSocket))
		{return true;}
		return false;
	}
	public boolean updateKnown()
	{
		byte[] piece=new byte[4];
		try {
			dis.readFully(piece);
		} catch (IOException e) {
			System.out.println("Byte non existant");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int number=Tools.bytesToInt(piece);
		known[number]=1;
		db.UpdateHaves(number);
		return true;
	}
	private int pieceRetrieval(byte[] four)
	{
		int size=Tools.bytesToInt(four);
		byte[] number=new byte[4];
		byte[] offset=new byte[4];

		try {
			dis.read(number);
			dis.read(offset);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int index=Tools.bytesToInt(number);
		byte[] piece=new byte[size-9];
		int thispiecelength=db.piecelength;
		if (index==db.piecenumber-1)
			{thispiecelength=db.filelength%db.piecelength;}
		
		try {
			while (dis.available()<size-9)
			{
				
			}
			dis.read(piece);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int thisoffset=Tools.bytesToInt(offset);
		if (noffset!=thisoffset)
			{
			return 0;}
		for (int i=0;i<piece.length;i++)
		{
			piecetemp[noffset+i]=piece[i];
		}
		noffset+=piece.length;
		done=true;
		if (noffset==thispiecelength)
		{
			noffset=-1;
			if (index==db.piecenumber-1)
			{
				byte[] finalpiece=new byte[thispiecelength];
				for (int i=0;i<thispiecelength;i++)
				{
					finalpiece[i]=piecetemp[i];
				}
				piecetemp=finalpiece;
			}
			if((Tools.verifySHA1(torFile.piece_hashes[index],Tools.SHA1(piecetemp))==true)&&(db.status[index]!=1))
			{
				Piece current=new Piece(piecetemp);
				db.UpdateFile(current, index);
				System.out.println("Successfully Downloaded "+index);
				piecetemp=new byte[db.piecelength];
				downloaded+=piecetemp.length;
			}
			else
			{
				System.out.println("Failed Hash Check... Piece"+currentpiece);
				if (db.status[index]!=1)db.DownloadFail(index);
			}
		}
		 Output();
		return downloaded;
	}
	private boolean fieldAnalysis(byte[] four)
	{
		int fieldsize = Tools.bytesToInt(four) -1;
		byte[] field = new byte[fieldsize];
		try {
			dis.readFully(field);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Couldn't Read the Bitfield");
			e.printStackTrace();
		}
		bitField=field;
		for(int i=0;i<(db.piecenumber);i++)
		{
			if (examineBitField(bitField,i))
			{
				known[i]=1;
			}
			else
			{
				known[i]=0;
			}
		}
		
		return true;
	}
	private boolean examineBitField(byte[] bitField, int index){
		byte[] field = new byte[4];
		field[3] = (byte) (bitField[index/8] & (0x80 >> index%8));
		
		if(Tools.bytesToInt(field)==0){
			return false;
		}
		else
		{
			return true;
		}
}

	//Upload section--------------------------------------------------------------------------
	
	
	private void Output()
	{
		if ((theirchoking==false)&&(myinterest==true))
		{
			downloader();
		}
		KnownSync(known);
		//if (bitField!=null){
		/*else{
			try {
				dos.write(message.interested());
				dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}
	private boolean requestReader()
	{
		try {
			while (dis.available()<12)
			{
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] request=new byte[12];
		try {
			dis.read(request);
		} catch (IOException e) {
			System.out.println("Could not retrieve requestblock");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] temp={request[0],request[1],request[2],request[3]}; //Sets temp to Index
		int index=Tools.bytesToInt(temp); //System.out.println("Index is "+index);
		temp[0]=request[4];temp[1]=request[5];temp[2]=request[6];temp[3]=request[7]; //Sets temp to Begin
		int begin=Tools.bytesToInt(temp);//System.out.print(" begin is "+begin);
		temp[0]=request[8];temp[1]=request[9];temp[2]=request[10];temp[3]=request[11]; //Sets temp to Begin
		int length=Tools.bytesToInt(temp);//System.out.print("Length is "+length);
		if (length+begin>db.piecelength){System.out.println("Bad PEER YO"); choke(); return false;}
		if (begin<0){System.out.println("Bad PEER YO"); choke();return false;}
		if (length<=0){System.out.println("Bad PEER YO"); choke(); return false;}
		if ((index<0)||(index>db.piecenumber-1)){System.out.println("Bad PEER YO"); choke(); return false;}
		if ((db.status[index]!=1)){System.out.println("Requesting Impossible Piece Dawg!");return false;}
		try {
			dos.write(db.PieceRequest(index,begin,length));
			dos.flush();
		} catch (IOException e) {
			System.out.println("Bad Upload...");
			// TODO Auto-generated catch block
			e.printStackTrace();
			uploaded+=length;
			return false;
		}
		return true;
	}
	private void KnownSync(byte[] previous)
	{
		int[] inter=db.status;
		byte[] current=previous;
		for(int i=0;i<db.piecenumber;i++)
		{
			if ((inter[i]==1)&&(current[i]==0))
			{
				current[i]=-1;
			}
			
		}
		known=current;
		haveupdate();
	}
	private boolean downloader()
	{
		PieceNode temp=db.piecenodehaves.head;
		int allowed=-1;
		int thispiecelength=db.piecelength;
		if(currentpiece==db.piecenumber-1)thispiecelength=db.filelength%db.piecelength;
		int requestsize=16384;
		if (noffset<0)
		{
			
			while (temp!=null)
			{
					if ((known[temp.piecenumber]==1)&&(db.status[temp.piecenumber]==0))
					{
						allowed=temp.piecenumber;
						if (db.PieceCheckout(allowed))
						{
							noffset=0;
							if (noffset+requestsize>thispiecelength)
							{requestsize=thispiecelength%requestsize;}
								try {
									dos.write(message.pieceRequestArray(allowed,noffset,requestsize));
								} catch (IOException e) {
									System.out.println("Fucked Up  Requesting  Piece endings");
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								currentpiece=allowed;
								done=false;
								retry=0;
								return true;
						}
					}
					temp=temp.next;
			}
		}
		else
		{
			if ((done==false)) //If piece has not arrived yet~
			{	

				if(((retry!=0)&&(retry%1000000==0)))
				{
					System.out.println("Dead beat lost piece"+currentpiece);
					db.DownloadFail(currentpiece);
					try {
						dos.write(message.uninterested());
						dos.flush();
						noffset=-1;
					} catch(SocketException e)
					{
						e.printStackTrace();
					}catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if ((retry!=0)&&(retry%100000==0)) 
				{
				
					if (noffset+requestsize>thispiecelength)
					{requestsize=thispiecelength%requestsize;}
					try {
						dos.write(message.pieceRequestArray(currentpiece,noffset,requestsize));
						dos.flush();
					} catch(SocketException e)
					{
						System.out.println("The current piece is"+currentpiece+" the offset is "+noffset+" and the requestsize is"+requestsize);
						e.printStackTrace();
					}catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				retry=retry+1;return false;
			}
			else
			{
				if (noffset+requestsize>thispiecelength)
				{requestsize=thispiecelength%requestsize;}
					try {
						dos.write(message.pieceRequestArray(currentpiece,noffset,requestsize));
						dos.flush();
					} catch (IOException e) {
						System.out.println("Fucked Up  Requesting  Piece endings");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					done=false;
					return true;
			}
		}
		return false;
	}
	private void SendHave(byte index)
	{
		try {
			dos.write(TorrentMessages.have(index));
			dos.flush();
			//System.out.println("Have sent! For "+index+"to port"+port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.out.println("Problem sending have!!! Peer at "+port+" With Piece "+index);
		}
	}
		private void haveupdate()
		{
			for (int i=0;i<db.piecenumber;i++)
			{
		
				if (known[i]==-1)
				{
					SendHave((byte)i);
					known[i]=1;
				}
			}	
		}
}
