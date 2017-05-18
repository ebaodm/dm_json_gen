package json_generation_PA;

public class ColumnConvert {
	/* Column Convert to Upper first letter
     * @param rootcol
     * @return 
     * @author   Wing.Xu
     * @Date creation  9/13/2016 4:14:35 PM
     * @Date modification  
     */
	public static String FirstUpperCase(String colname)
    {
    	StringBuilder attribute = new StringBuilder();
    	String colnamearr[] = colname.split("_");
    	for (int i = 0 ; i < colnamearr.length ;i++){  	
        	char[] namearray = colnamearr[i].toLowerCase().toCharArray();
        	namearray[0] -= 32;
        	attribute.append(String.valueOf(namearray)); 
        } 
    	return attribute.toString();
    }
}
