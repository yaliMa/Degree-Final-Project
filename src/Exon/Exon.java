package Exon;

import org.json.JSONException;
import org.json.JSONObject;

import Boundaries.CDS;


/**
 * Each exon is represented by to boundaries objects: ExonRna and ExonProtein
 * After receiving the real boundaries (ExonRna) and the CDS boundaries, I can 
 * easily calculate the ExonProtein boundaries and phases.
 * The class also contain two integers that contain values for statistics.
 * 
 * @author Yael Mathov
 *
 */
public class Exon implements Comparable<Exon>{
	public BoundariesExonRna 		m_rnaBoundary;
	public BoundariesExonProtein 	m_proteinBoundary;
	
	/* The following variables contains statistics values or -123 */
	public int 						m_signedDistenceFromIrregularStructure;
	public int 						m_unsignedDistenceFromIrregularStructure;
	
	
	/*************************** Constructors ***************************/
	public Exon(int _start, int _end, CDS _cds)
	{
		this.m_rnaBoundary = new BoundariesExonRna(_start, _end);
		this.m_proteinBoundary = new BoundariesExonProtein(_start, _end, _cds);
		
		this.m_signedDistenceFromIrregularStructure = -123;
		this.m_unsignedDistenceFromIrregularStructure = -123;
	}

	//------------------------------------------------------------------//
	/**
	 * This constructor create a "dummy" exon - an exon with only 
	 * ExonProtein. Creating the dummy exon helps in calculating statistics
	 * and comparing between two exons.
	 */
	public Exon(int _start, int _end)
	{
		this.m_rnaBoundary = new BoundariesExonRna(-1, -1);
		this.m_proteinBoundary = new BoundariesExonProtein(_start, _end);
		this.m_signedDistenceFromIrregularStructure = -123;
		this.m_unsignedDistenceFromIrregularStructure = -123;
	}
	
	//------------------------------------------------------------------//
	// Copy Constructor
	public Exon(Exon _other)
	{
		this.m_rnaBoundary = new BoundariesExonRna(_other.m_rnaBoundary);
		this.m_proteinBoundary = new BoundariesExonProtein(_other.m_proteinBoundary);
		this.m_signedDistenceFromIrregularStructure = _other.m_signedDistenceFromIrregularStructure;
		this.m_unsignedDistenceFromIrregularStructure = _other.m_unsignedDistenceFromIrregularStructure;
	}
	
	/****************************** JSON *********************************
	 * This method parse the Exon object to JSON object.
	 * Only the m_rnaBoundary is parsed because m_proteinBoundary can
	 * be easily calculated.
	 * variable parsing: (m_rnaBoundary)
	 * @throws JSONException 
	 */
	public JSONObject exonToJson() throws JSONException
	{
		JSONObject root = new JSONObject();
		
		root.put("RnaBoundary", this.m_rnaBoundary.boundariesToJson());
		return root;
	}
	
	
	/***************************** toString *****************************/
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Exon \tProtein Boundary: ");
		builder.append(this.m_proteinBoundary.toString());
		builder.append(",    \tunsign distence: { ");
		builder.append(this.m_unsignedDistenceFromIrregularStructure);
		builder.append(" }\n");
		return builder.toString();
	}
	
	//------------------------------------------------------------------//
	// toString for debug
	
	/*public String toString() 
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Exon: [ ");
		builder.append(this.m_rnaBoundary.toString());
		builder.append("\t\t");
		builder.append(this.m_proteinBoundary.toString());
		builder.append("\n\t\t unsign distence: ");
		builder.append(this.m_unsignedDistenceFromIrregularStructure);
		builder.append("\n\t\t sign distence: ");
		builder.append(this.m_signedDistenceFromIrregularStructure);
		builder.append(" ]\n");
		return builder.toString();
	}*/
	
	
	/**************************** compareTo *****************************/
	@Override
	public int compareTo(Exon o) {
		return this.m_proteinBoundary.m_start > o.m_proteinBoundary.m_start ? +1 : this.m_proteinBoundary.m_start < o.m_proteinBoundary.m_start ? -1 : 0;
	}

}
