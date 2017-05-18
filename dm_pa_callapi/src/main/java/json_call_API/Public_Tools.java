package json_call_API;

public class Public_Tools {
	public static String getExceptionInfo(Exception ex){
		StringBuilder outStr = new StringBuilder();
		StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement s : trace) {
        	outStr.append("\tat ").append(s).append("\r\n");
        }
        return outStr.toString();
	} 
}
