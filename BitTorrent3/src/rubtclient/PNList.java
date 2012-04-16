package rubtclient;

public class PNList{

	PieceNode head;
	
	/**
	 * Creates an empty PNList with head set to null
	 */
	public PNList(){
		head = null;
	}
	
	/**
	 * Adds a PieceNode to the end of the PNList
	 * @param addme PieceNode to be added to the list
	 */
	public void addToRear(PieceNode addme){
		if (head == null) head = addme;
		else {
			PieceNode temp = head;
			while (temp.next != null){
				temp = temp.next;
			}
			temp.next = addme;
		}
	}
	
	/**
	 * Deletes a PieceNode from the PNList
	 * @param piecenumber Indicates which node to delete
	 * @return True upon successful deletion
	 */
	public boolean deleteNode(int piecenumber){
		PieceNode previous = null;
		PieceNode current = head;
		while(current != null){
			if (current.piecenumber == piecenumber) {
	            if (previous == null) {
	                head = current.next;
	                return true;
	            }
	            else {
	                previous.next = current.next;
	                return true;
	            }
	        }
	        else {
	            previous = current;
	        }
	        current = current.next;
		}
		return false;
	}
	
	/**
	 * Sorts a PNList in order of decreasing rarity
	 * @return A sorted PNList in order of decreasing rarity
	 */
	public PNList sortPNList(){
		this.head = merge_sort(this.head);
		return this;
	}
	
	private PieceNode merge_sort (PieceNode piece1) {
		if (piece1 == null || piece1.next == null)
	           return piece1;
		PieceNode list2 = split (piece1);
		piece1 = merge_sort (piece1);
		list2 = merge_sort (list2);
		return merge (piece1, list2);
	}

	private PieceNode split (PieceNode piece1) {
		if (piece1 == null || piece1.next == null) return null;
		PieceNode list2 = piece1.next;
		piece1.next = list2.next;
		list2.next = split (list2.next);
		return list2;
	}

	private PieceNode merge (PieceNode piece1, PieceNode piece2) {
		if (piece1 == null) return piece2;
		if (piece2 == null) return piece1;
		if (piece1.compareTo(piece2) == -1) {
			piece1.next = merge (piece1.next, piece2);
			return piece1;
		}
		else {
			piece2.next = merge (piece1, piece2.next);
			return piece2;
		}
	}
	
}