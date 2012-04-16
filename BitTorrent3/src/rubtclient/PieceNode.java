package rubtclient;

public class PieceNode{
	int piecenumber;
	int haves;
	PieceNode next;
	
	/*
	 * Create a PieceNode with a given piecenumber
	 * and number of peers who have this piece
	 */
	public PieceNode(int number, int have){
		piecenumber = number;
		haves = have;
		next = null;
	}
	
	/*
	 * Set haves to int have
	 */
	public void setHaves(int have){
		haves = have;
	}
	
	/* 0 if equal
	 * 1 if first is greater
	 * -1 if second is greater
	 */
	public int compareTo(PieceNode second){
		if(this.haves > second.haves) return 1;
		else if(this.haves < second.haves) return -1;
		else return 0;
	}
}