package Exon;

import Boundaries.Boundaries;
import Boundaries.CDS;

enum ExonPhase{
	PHASE0,
	PHASE1,
	PHASE2,
	OUT_OF_CDS
}

/**
 * 
 * @author Yael Mathov
 */
public class BoundariesExonProtein extends Boundaries
{
	public ExonPhase startPhase;
	public ExonPhase endPhase;


	/*************************** Constructors ***************************/
	/**
	 * Get the exon's accusal boundaries (in the RNA) and 
	 * calculate the range in the protein that was encoded 
	 * by this exon. Also calculate each boundary's phase.	 */
	public BoundariesExonProtein(int _start, int _end, CDS _cds) {
		super(_start, _end);
		updateRange(_cds);
	}
	
	//------------------------------------------------------------------//
	/**
	 * Receive the boundaries "in the protein" and determine the phase. */
	public BoundariesExonProtein(int _start, int _end) {
		super(_start, _end);
		this.startPhase = determinePhase(this.m_start);
		this.endPhase = determinePhase(this.m_end);
	}
	
	//------------------------------------------------------------------//
	// Default Contractor	
	public BoundariesExonProtein() {
		super();
	}
	
	//------------------------------------------------------------------//
	// Copy Constructor 
	public BoundariesExonProtein(BoundariesExonProtein _other) {
		super(_other);
		this.startPhase = _other.startPhase;
		this.endPhase = _other.endPhase;
	}
	
	
	
	
	/************************** Private Methods *************************/

	/**
	 * Receive exon boundary in the protein and determines 
	 * the exon's phase. 
	 * @param _bound
	 * @return
	 */
	private ExonPhase determinePhase(int _bound)
	{
		switch(_bound % 3)
		{
		case 0:
			return ExonPhase.PHASE0;
		case 1:
			return ExonPhase.PHASE1;
		default:
			return ExonPhase.PHASE2;
		}
	}
	
	//------------------------------------------------------------------//

	
	/**
	 * Determines if the boundaries of the exon are in the protein's
	 * CDS. If both boundaries are out of the CDS the boundaries phase will both marked
	 * as "out of CDS". If only one of the boundaries is out of the protein's CDS, only
	 * it will be marked had "out of CDS" and the other will be calculate normally.
	 * If both boundaries in the CDS, both phases will be calculated in the method
	 * "determinePhase" and the boundaries will be updated from RNA boundaries to 
	 * the exon boundaries in the protein.     
	 * @param _cds
	 */
	private void updateRange(CDS _cds)
	{
		// the boundary is out of the protein's CDS
		if((this.m_end < _cds.m_start) || (this.m_start > _cds.m_end))
		{
			this.m_start = -1;
			this.m_end = -1;
			this.m_length = -1;
			this.startPhase = ExonPhase.OUT_OF_CDS;
			this.endPhase = ExonPhase.OUT_OF_CDS;
		}
		else
		{ 
			this.startPhase = determinePhase((this.m_start - _cds.m_start));
			this.endPhase = determinePhase((this.m_end - _cds.m_start));
			
			// the start of the boundary is out of the protein's CDS.
			if(this.m_start < _cds.m_start)
			{
				this.m_start = _cds.m_start;
				this.startPhase = ExonPhase.OUT_OF_CDS;
			}
			// the end of the boundary is out of the protein's CDS.
			if(this.m_end > _cds.m_end)
			{
				this.m_end = _cds.m_end;
				this.endPhase = ExonPhase.OUT_OF_CDS;
			}
			
			// the boundaries are in the rang of the protein's CDS
			this.m_start = (int) (Math.floor(((this.m_start - _cds.m_start) / 3)) + 1);
			this.m_end = (int) (Math.floor(((this.m_end - _cds.m_start) / 3)) + 1);
			this.m_length = this.m_end - this.m_start;
		}	
	}
	
	
	
	

	/***************************** toString *****************************/
	
	// TODO: Do I need the full object? Right now using the parent's "clean" toString.
	
	//@Override
	/*public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\tProtein Boundary [ ");
		builder.append(m_start);
		builder.append(" - ");
		builder.append(m_end);
		builder.append(" ] { ");
		builder.append(m_length);
		builder.append(" } \t< ");
		builder.append(startPhase);
		builder.append(" - ");
		builder.append(endPhase);
		builder.append(" >");
		return builder.toString();
	}*/
	
}



