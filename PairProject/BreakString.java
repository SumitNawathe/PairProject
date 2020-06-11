import java.util.ArrayList;

public class BreakString {
	public static ArrayList<String> breakText(String string) {
		ArrayList<String> ret=new ArrayList<String>();
		char[] text=string.toCharArray();
		while (string.length()>28) {
			int i;
			for (i=28;i>0;i--) {
				i--;
				if (text[i]==' ') {
					break;
				}
			}
			String add=string.substring(0,i+1);
			ret.add(add);
			string=string.substring(i+1);
			text=string.toCharArray();
		}
		ret.add(string);
		return ret;
	}
}
