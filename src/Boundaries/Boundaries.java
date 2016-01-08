package Boundaries;

/**
 * 
 * @author Yael Mathov
 */
public class Boundaries {
	
	public int m_start;
	public int m_end;
	public int m_length;
	
	
	/*************************** Constructors ***************************/
	public Boundaries(int _start, int _end) {
		this.m_start = _start;
		this.m_end = _end;
		this.m_length = _end - _start;
	}
	
	//------------------------------------------------------------------//
	// Default Contractor	
	public Boundaries() {
		this.m_start = -1;
		this.m_end = -1;
		this.m_length = -1;
	}
	
	//------------------------------------------------------------------//
	// Copy Constructor 
	public Boundaries(Boundaries _other) {
		this.m_start = _other.m_start;
		this.m_end = _other.m_end;
		this.m_length = _other.m_length;
	}
	
	
	/***************************** toString *****************************/
	
	// [ m_start - m_end]
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ ");
		builder.append(m_start);
		builder.append(" - ");
		builder.append(m_end);
		builder.append(" ]\n");
		return builder.toString();
	}

}
