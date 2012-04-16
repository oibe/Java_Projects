package cs113.calendar.util;
/**
 * @author Akhil Gopinath
 */
import java.util.ArrayList;
import cs113.calendar.model.appointment;

public class quicksort {
	private void quickSort(ArrayList<appointment> list, int low0, int high0) {
		int low = low0, high = high0;
		if (low >= high) {
			return;
		} else if (low == high - 1) {
			if (((Comparable)list.get(low)).compareTo(list.get(high)) > 0) {
				appointment temp = list.get(low);
				list.set(low, list.get(high));
				list.set(high, temp);
			}
			return;
		}			
		appointment pivot = list.get((low + high) / 2);
		list.set((low + high) / 2, list.get(high));
		list.set(high, pivot);

		while (low < high) {
			while (((Comparable)list.get(low)).compareTo(pivot) <= 0 && low < high) {
				low++;
			}
			while (((Comparable)list.get(high)).compareTo(pivot) >= 0 && low < high) {
				high--;
			}
			if (low < high) {
				appointment temp = list.get(low);
				list.set(low, list.get(high));
				list.set(high, temp);
			}
		}
		list.set(high0, list.get(high));
		list.set(high, pivot);
		quickSort(list, low0, low - 1);
		quickSort(list, high + 1, high0);
	}
	
	public void sort(ArrayList<appointment> list) {
		quickSort(list, 0, list.size() - 1);
	}
}
