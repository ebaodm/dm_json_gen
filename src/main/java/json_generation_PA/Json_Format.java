package json_generation_PA;

public class Json_Format {
	public static void printJson(String jsonStr){
        System.out.println(formatJson(jsonStr));
    }
    
    /**
     * ��ʽ��
     * @param jsonStr
     * @return
     * @author   lizhgb(copy on internet)
     * @Date   2015-10-14 ����1:17:35
     * @Date modification
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
 
        return sb.toString();
    }
    /**
     * add space
     * @param sb
     * @param indent
     * @author   lizhgb(copy on internet)
     * @Date   2015-10-14 ����10:38:04
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}