package Boundaries;

import org.json.JSONException;
import org.json.JSONObject;

public class CDS extends Boundaries{
	
	/********************************************************************/
	/*************************** Constructors ***************************/
	/********************************************************************/
	public CDS(int _start, int _end) {
		super(_start, _end);
	}
	
	// copy constructor 
	public CDS(CDS _other) {
		super(_other);
	}
	
	/**************** JSON ******************
	 * @throws JSONException ****************/

	public JSONObject CDSToJson() throws JSONException
	{
		JSONObject json = new JSONObject();
		
		json.put("start", new Integer(this.m_start));
		json.put("end", new Integer(this.m_end));
		
		return json;
	}
	
	/************** toString **************/
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\tCDS [ ");
		builder.append(m_start);
		builder.append(" - ");
		builder.append(m_end);
		builder.append(" ]\n");
		return builder.toString();
	}
}