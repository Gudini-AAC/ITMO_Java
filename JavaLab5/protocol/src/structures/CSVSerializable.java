package structures;

public interface CSVSerializable {
	
	/**
    * @brief Convert this object to CSV string
    * @return CSV string.
    */
	String toCSV();
	
	/**
    * @brief Load this object from the CSV string at a certan offset.
    * @param str CSV string representing this object.
    * @param offset Offset into the string.
    * @return New offset into the string.
    */
	int fromCSV(String str, int offset);
	
}

