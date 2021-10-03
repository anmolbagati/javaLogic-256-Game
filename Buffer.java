
/**

 @author (Anmol Bagati)

 **/
 

import java.util.ArrayList;
/* Getter setter statements used in the startgame class at each round stage.
   Buffer class with two variables declared with data types*/
public class Buffer {

	private ArrayList<Multiple> list;
	private int maxElements;
	
	public ArrayList<Multiple> getList() {
		return list;
	}
	public void setList(ArrayList<Multiple> list) {
		this.list = list;
	}
	public int getMaxElements() {
		return maxElements;
	}
	public void setMaxElements(int maxElements) {
		this.maxElements = maxElements;
	}
	/* Constructor for initiating values */
	public Buffer()
	{
		maxElements = 5;
		list = new ArrayList<>();
	}
}
