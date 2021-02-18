package structures;

public interface CSVSerializable {
	
	/**
    * @brief Convert this object to CSV string
    */
	public String toCSV();
	
	/**
    * @brief Load this object from the CSV string at a certan offset.
    *
    * @param str CSV string representing this object.
    * @param offset Offset into the string.
    * @return New offset into the string.
    */
	public int fromCSV(String str, int offset);
	
}

