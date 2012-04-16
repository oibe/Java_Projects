package rubtclient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
//import java.util.List;
import java.util.Random;

public class NuClient {
	
	static TorrentMessages message=new TorrentMessages();
	static Database db;
	static PortListener catcher;
	
	public static void main(String[] args){
		/* Set up */
		//int u = 0;
		//byte[] tembuf = new byte[1];
		System.out.println("Welcome to BitTorrent V1.0");
		System.out.println("Made by Brian Flowers, Onwukike Ibe, & Julian Sotolongo");
		String torrentpath = "";
		String savepath = "";
		/* Check for valid arguments */
		if(args.length != 2){
			System.err.println("Error: Invalid arguments");
			return;
		}
		
		/* Set the path for the torrent's location */
		try{
			torrentpath = args[0];
			savepath = args[1];
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.err.println("Error: Please enter a filepath and savepath as arguments");
			return;
		}
		
		System.out.println();
		System.out.println("File will be saved to " + savepath);
		
		File torrentfile = new File(torrentpath);
		byte[] filebytes = new byte[(int)torrentfile.length()];
		
		/* Create a FileInputStream for the torrent file */
		FileInputStream finputstream;
		try{
			finputstream = new FileInputStream(torrentfile);
		}
		catch(FileNotFoundException e){
			System.err.println("Error: Please enter a valid torrent location");
			return;
		}
		
		/* Create a DataInputStream for the torrent file */
		DataInputStream dinputstream = new DataInputStream(finputstream);
		int bytecounter = 0;
		try {
			/* Copy all file bytes into byte array */
			while(dinputstream.available() != 0){
				filebytes[bytecounter] = dinputstream.readByte();
				bytecounter++;
			}
		}
		catch (IOException e) {
			System.err.println("Error: Invalid file contents");
			return;
		}
		
		/* Create a TorrentInfo object to hold all torrent information */
		TorrentInfo torrentinfo = null;
		try {
			torrentinfo = new TorrentInfo(filebytes);
		}
		catch (BencodingException e) {
			System.err.println("Error: Encountered trouble with Bencoding");
			return;
		}
		
		/* Tries to load an existing Database, Otherwise a new one is made. */
		if(resume()==false)
		{
			System.out.println("Number if pieces is "+ Math.ceil(torrentinfo.file_length/torrentinfo.piece_length));
		db=new Database((int)Math.ceil(torrentinfo.file_length/torrentinfo.piece_length)+1,torrentinfo,savepath);
		System.out.println("Resume failed. -_-");
		}
		else 
		{
			db.load(torrentinfo);
			System.out.println("Resume was coool");
		}
		Controller master=new Controller(db); //Creates the Control Module Thread.
		master.t.start();
		System.out.println("Database Instantiated");
		/* Generate a peer ID of the format 'I_PITY_THE_FOOLL' + 'random number' */
		String mypeerID = "I_PITY_THE_FOOLL";
		Random generator = new Random();
		int random = generator.nextInt(7000)+1000;
		mypeerID += random;
		catcher=new PortListener(torrentinfo,mypeerID,db);  //Begins the Port Listener
		/* Now, we go to the Tracker Phase */
		trackercommunication(torrentinfo,mypeerID);
		System.out.println("Leaving Tracker State");
		
		/* Then we do our seeding Requirements */
		seeding();
	
		/* After that We just close everything down.
		We need to tell the port Listener it's alright to die... */
		master.kill=true;
		close(mypeerID);
		System.out.println("Shut off the things Still on if any.");
		System.exit(0);
	}
	
	public static boolean resume()
	{
		/* Database loading */
		File f=new File("datatest.tmp");
		if (!f.exists())	//If database file does not exist, then False is returned.
		{
			return false;
		}
		FileInputStream fis;
		/* Else wise, the database is loaded into object db */
		try {
			fis = new FileInputStream(f);
		ObjectInputStream ois=new ObjectInputStream(fis);
		db=(Database) ois.readObject();
		ois.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found...  -_-");
			return false;
		} catch(IOException e)
		{
			System.err.println ("IOException!");
			return false;
		} catch (ClassNotFoundException e) {
			System.err.println ("ClassNotFOUND!~");
			return false;
		}
		return true;
	}
	
	/* Goes through to make sure all the threads are closed */
	public static void close(String peerid)
	{
		System.out.println("Starting peer check~!");
		int close=0;
		/* Now We wait until the download peers are finished
		This is only needed if the file is not finished however */
		if (db.finished==0)
		{
			while (close==0)
			{
				close=1;
				for (int i=0;i<catcher.mypeers.size();i++)
				{
					if(catcher.mypeers.get(i).t.isAlive())
					{
						close=0;
					}
				}
			}
		}
		
		/* Then we check the upload peers */
		System.out.println("saying goodbyeee");
		/* At this point we tell the tracker we're leaving */
		@SuppressWarnings("unused")
		URL trackerurl=null;
		String getmessage=message.getRequestString(db.infohash,db.filelength,db.announceurl,peerid,"stopped",db.totalupload,db.totaldownload,db.totaldownload-db.totalupload);
		try {
			trackerurl = new URL(getmessage);
		} catch (MalformedURLException e1) {
		System.err.println("Error: Invalid URL");
		return;
		}
	
			/* NOW, we save the current database */
			FileOutputStream fos;
			try {
					fos = new FileOutputStream("datatest.tmp");
					ObjectOutputStream oos=new ObjectOutputStream(fos);
					oos.writeObject(db);
					System.out.println("clone written");
					oos.close();
					fos.flush();
			} catch (FileNotFoundException e) {
				System.err.println("File not found...  -_-");
				return;
			} catch(IOException e)
			{
				System.err.println ("IOException!");
				return;
			}
		/* Then we just kinda stop */	
	}

	public static boolean updatepeers(LinkedList<ProtoPeer> newpeers)
	{
		if (catcher.mypeers.isEmpty())
		{
			while (!newpeers.isEmpty())
			{
				System.out.println("adding "+newpeers.get(0).ipAddress+" "+newpeers.get(0).port);
				catcher.mypeers.add(newpeers.pop());
				catcher.mypeers.get(catcher.mypeers.size()-1).t.start();
			}
			return true;
		}
		for (int i=0;i<catcher.mypeers.size();i++)
		{
			if (!catcher.mypeers.get(i).t.isAlive())
			{
				catcher.mypeers.remove(i);
				
			}
		}
		while ((!newpeers.isEmpty())&&(catcher.mypeers.size()<10))
				{
					if 
					(!catcher.mypeers.contains(newpeers.get(0)))
					{
					
						catcher.mypeers.add(newpeers.pop());
						catcher.mypeers.get(catcher.mypeers.size()-1).t.start();
					
						System.out.println("removing dead "+catcher.mypeers.get(catcher.mypeers.size()-1).ipAddress+" "+catcher.mypeers.get(catcher.mypeers.size()-1).port);
					}
					else {newpeers.pop();}
			
				}
		return true;
	}
				
	@SuppressWarnings("unchecked")
	public static void trackercommunication(TorrentInfo torinfo,String mypeerID)
	{
		TorrentInfo torrentinfo=torinfo;
		boolean started=true; /* Is it our first time through the loop? 1 if Yes. */
		@SuppressWarnings("unused")
		boolean sdone=false; /* Did you start Finished? */
		if (db.finished==1)
		{
			sdone=true;
		}
		String getmessage;
		URL trackerurl;
		double timer = 0;
		do /* Download Phase. Goes until the file is downloaded, or suspended */
		{
			if (started==true){  /* First time running through the loop, sends started event */ 
				getmessage=message.getRequestString(torrentinfo,mypeerID,"started",db.totalupload,db.totaldownload,(torrentinfo.file_length-db.totaldownload));
			}
			else{
				getmessage=message.getRequestString(torrentinfo,mypeerID,"",db.totalupload,db.totaldownload,(torrentinfo.file_length-db.totaldownload));
			}
			trackerurl=null;
			try {
					trackerurl = new URL(getmessage);
			} catch (MalformedURLException e1) {
				System.err.println("Error: Invalid URL");
				return;
			}
			URLConnection trackerconnection= null;
			try {
				trackerconnection = trackerurl.openConnection();
			} catch (IOException e1) {
				System.err.println("Error: Could not open connection to tracker");
				return;
			}
			
			java.io.InputStream in = null;
			try {
				in = trackerconnection.getInputStream();
			} catch (IOException e3) {
				System.err.println("Error: Could not get input stream from tracker connection");
				return;
			}
			ByteArrayOutputStream boss = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int bytesSoFar= 0;
			try
			{
				while((-1 != (bytesSoFar = in.read(buffer)))){
					boss.write(buffer,0,bytesSoFar);
					//s += String.copyValueOf(buffer);
				}
			in.close();
			}
			catch(IOException e2)
			{
				System.err.println("Error: Unfortunate byte-age difficulties");
				return;
			}

			/* Reading data from the dictionary given by the Tracker */
			HashMap<ByteBuffer,Object> resp_dict=null;
		
			try {
				resp_dict = (HashMap<ByteBuffer,Object>) Bencoder2.decode(boss.toByteArray());
			} catch (BencodingException e1) {
				System.err.println("Error: Could not properly decode Bencoding");
				return;
			}
			//ToolKit.print(resp_dict);
			int interval =(Integer) resp_dict.get(ByteBuffer.wrap("interval".getBytes()));
		
			if(started){
				/* Update peers here */
				ArrayList<HashMap<ByteBuffer,Object>> peer_list = (ArrayList<HashMap<ByteBuffer,Object>>)resp_dict.get(ByteBuffer.wrap("peers".getBytes())); // KEY_PEERS=“peers”
				int counter = 0;
				String dip="";
				int dport=0;
				LinkedList<ProtoPeer> uppeer=new LinkedList<ProtoPeer>();
				for(HashMap<ByteBuffer,Object> peer_info : peer_list) {
					int port = ((Integer) peer_info.get(ByteBuffer.wrap("port".getBytes()))).intValue(); //KEY_PORT=“port”
					String ip = new String(((ByteBuffer) (peer_info.get(ByteBuffer.wrap("ip".getBytes())))).array());
					counter++;
					if (ip.equals("128.6.157.250"))
						{dip=ip;dport=port;}
						ProtoPeer temp=new ProtoPeer(torrentinfo, dip,dport, mypeerID, db);
						uppeer.add(temp);
					}
					updatepeers(uppeer);
			}
			
			/* Choke Peer with Lowest Download Rate (Every 30 seconds) */
			int lowestrate = 999;
			int lowestindex = -1;
			for(int x=0; x<catcher.mypeers.size(); x++){
				int dlrate = catcher.mypeers.get(x).downloadrate();
				if(dlrate < lowestrate){
					lowestindex = x;
					lowestrate = dlrate;
				}
			}
			if(!catcher.mypeers.isEmpty() && lowestindex != -1 && !catcher.mypeers.get(lowestindex).mychoking){
				catcher.mypeers.get(lowestindex).setchoke=true;
			}
		
			/* Unchoke Random Peer (Every 30 seconds) */
			int selection = (int)(Math.random()*catcher.mypeers.size());
			if(!catcher.mypeers.isEmpty() && catcher.mypeers.get(selection).mychoking && Tools.countUnchoked(catcher.mypeers)<10){ 
				catcher.mypeers.get(selection).setunchoke=true;
			}
		
			/* Sleep for 30 seconds in half second intervals */
			for(int x = 0; x<60; x++){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.err.println("Thread Interrupted");
					return;
				}
				timer += .5;
				if(timer >= interval){
					timer = 0;
					/* Update peers here */
					ArrayList<HashMap<ByteBuffer,Object>> peer_list = (ArrayList<HashMap<ByteBuffer,Object>>)resp_dict.get(ByteBuffer.wrap("peers".getBytes())); // KEY_PEERS=“peers”
					int counter = 0;
					String dip="";
					int dport=0;
					LinkedList<ProtoPeer> uppeer=new LinkedList<ProtoPeer>();
					for(HashMap<ByteBuffer,Object> peer_info : peer_list) {
						int port = ((Integer) peer_info.get(ByteBuffer.wrap("port".getBytes()))).intValue(); //KEY_PORT=“port”
						String ip = new String(((ByteBuffer) (peer_info.get(ByteBuffer.wrap("ip".getBytes())))).array());
						counter++;
						if (ip.equals("128.6.157.250"))
							{dip=ip;dport=port;}
							ProtoPeer temp=new ProtoPeer(torrentinfo, dip,dport, mypeerID, db);
							uppeer.add(temp);
						}
						updatepeers(uppeer);	
					}
				if (db.suspend==true) break;
			}
			if (db.suspend==true) break;
		}while ((db.finished==0)&&(db.suspend==false));
		if((sdone=false)&&(db.finished==1))  //If Finished, and the file did not START finished.
		{
			getmessage=message.getRequestString(torrentinfo,mypeerID,"completed",db.totalupload,torrentinfo.file_length,0);
			try {
				trackerurl = new URL(getmessage);
			} catch (MalformedURLException e1) {
				//System.out.println("All done downloading~");
				System.err.println("Error: Invalid URL");
				return;
			}
		}
	}
	
	public static void seeding()
	{
		/* Waits for the upload to exceed or equal 40%, or the User directs to quit */
		while(true)
		{
			/* Choke Peer with Lowest Upload Rate (Every 30 seconds) */
    		int lowestrate = 999;
    		int lowestindex = -1;
    		for(int x=0; x<catcher.mypeers.size(); x++){
    			int dlrate = catcher.mypeers.get(x).uploadrate();
    			if(dlrate < lowestrate){
    				lowestindex = x;
    				lowestrate = dlrate;
    			}
    		}
    		if(!catcher.mypeers.isEmpty() && lowestindex != -1 && !catcher.mypeers.get(lowestindex).mychoking){
    			catcher.mypeers.get(lowestindex).setchoke=true;
    		}
    		
    		/* Unchoke Random Peer (Every 30 seconds) */
    		int selection = (int)(Math.random()*catcher.mypeers.size());
    		if(!catcher.mypeers.isEmpty() && catcher.mypeers.get(selection).mychoking && Tools.countUnchoked(catcher.mypeers)<10){
    			catcher.mypeers.get(selection).setunchoke=true;
    		}
    		
    		/* Sleep for 30 seconds in half second intervals */
			for(int x = 0; x<60; x++){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.err.println("Thread Interrupted");
					return;
				}
				if (db.suspend==true) break;
			}
			if (db.suspend==true) break;
		}
		System.out.println("Leaving Seed State");
	}
}

class PortListener implements Runnable
{
	Thread t=new Thread(this,"PortListener");
	public boolean alive=true;
	ArrayList<ProtoPeer> mypeers = new ArrayList<ProtoPeer>();
	ServerSocket dmz;
	TorrentInfo torrentinfo;
	String peerid;
	Socket robot=new Socket();
	Database db;
	
	PortListener(TorrentInfo info,String id,Database d)
	{
		peerid=id;
		torrentinfo=info;
		db=d;
		t.start();
	}
	
	@Override
	public void run() 
	{
		try{
			dmz=new ServerSocket(56969,20);
			//dmz.setSoTimeout(500);
			dmz.setReuseAddress(true);
			System.out.println("Socket set, yo");
		}
		catch(IOException e)
		{
			System.err.println("Problem with your DMZ holmes.");
			return;
		}
		System.out.println("PortListener Started");
		try {
			while (alive==true)
			{
				if(!dmz.isClosed())
				{
					robot = dmz.accept();
					ProtoPeer temp=new ProtoPeer(torrentinfo, robot, peerid , db);
					if (mypeers.isEmpty())
					{
						temp.t.start();
						Tools.addPeer(temp, mypeers);
					}
					else
					{
						if (!mypeers.contains(temp))
						{
							temp.t.start();
							Tools.addPeer(temp, mypeers);
						}
					}
				}
			}
			//dmz.close();
			if(robot!=null)
			{robot.close();}
			System.out.println("Damn. finished already?");
		} catch (IOException e) {
			System.err.println("Problem with your DMZ holmes. The Sequel");
			e.printStackTrace();
			return;
		}
		
	}
	
	
}
class Controller implements Runnable
{

	String command="";
	Database db;
	boolean kill=false;
	BufferedReader lineOfText;
	Thread t=new Thread(this,"Controller");
	Controller(Database d)
	{
		db=d;
	}
	@Override
	public void run() {
		do 
		{
			try {
				lineOfText = new BufferedReader(new InputStreamReader(System.in));
				command = lineOfText.readLine();
				System.out.println("You typed "+command);
				if (command.equals("kill"))
				{
					System.exit(0);
				}
				else if(command.equals("Upload"))
				{
					System.out.println(db.totalupload);
				}
				else if(command.equals("UploadPercentage"))
				{
					System.out.println((double)db.totalupload/(double)db.filelength);
				}
				else if(command.equals("Downloaded"))
				{
					System.out.println((double)db.totaldownload/(double)db.filelength);
				}
			} catch (IOException e) {
				System.err.println("Something wrong with User Input, Sorry.");
				e.printStackTrace();
			}	
		}while((!command.equals("suspend"))&&(!command.equals("quit")));
		db.suspend=true;
		System.out.println("Suspending now "+db.suspend);
		System.out.println("Shutting Down");
	}
}