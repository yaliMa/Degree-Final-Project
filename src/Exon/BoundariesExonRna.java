package Exon;

import org.json.JSONException;
import org.json.JSONObject;

import Boundaries.Boundaries;


/**
 * 
 * @author Yael Mathov
 */
public class BoundariesExonRna extends Boundaries{
	
	/*************************** Constructors ***************************/

	public BoundariesExonRna(int _start, int _end) {
		super(_start, _end);
	}
	
	//------------------------------------------------------------------//
	// Copy Constructor
	public BoundariesExonRna(BoundariesExonRna _other) {
		super(_other);
	}
	
	/****************************** JSON *********************************
	 * This method parse the ExonRna object to JSON object.
	 * variable parsing: (m_start, m_end)
	 * @throws JSONException 
	 */
	public JSONObject boundariesToJson() throws JSONException
	{
		JSONObject json = new JSONObject();
		
		json.put("start", new Integer(this.m_start));
		json.put("end", new Integer(this.m_end));
		
		return json;
	}
	
	
	/***************************** toString *****************************/
	@Override
	public String toString() 
	{
		StringBuilder builder = new StringBuilder();
		builder.append("RNA Boundary ( ");
		builder.append(this.m_start);
		builder.append(" - ");
		builder.append(this.m_end);
		builder.append(")");
		return builder.toString();
	}

}
