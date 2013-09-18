package razor.lib.comms.model;


public class TimeZone  {
	
	private int id;
	private long gmtOffset;
	private String abbr;
	private String name;
	
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param gmtOffset the gmtOffset to set
	 */
	public void setGmtOffset(long gmtOffset) {
		this.gmtOffset = gmtOffset;
	}
	/**
	 * @return the gmtOffset
	 */
	public long getGmtOffset() {
		return gmtOffset;
	}
	/**
	 * @param abbr the abbr to set
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * @return the abbr
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
