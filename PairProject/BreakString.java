import java.util.ArrayList;

public class BreakString {
	public static ArrayList<String> breakText(String string) {
		    ArrayList <String> ret = new ArrayList<String>();
		    String newLine = "";
		    char character = ' ';
		    String cut = "";
		    int cutAdd = 0;
		    char cutChar = ' ';
		    int read = -1;
		    for (int i = 0; i < string.length(); i++){
		        read++;
		        character = string.charAt(i);
		        if(read >= 26 && i > 1){
		            if (character == ' ' || character == ','){
		                newLine += character;
		                ret.add(newLine);
		                newLine = "";
		            }
		            else{
		                cutChar = character;
		                while(cutChar != ' ' && i > 0){
		                    i--;
		                    cutChar = string.charAt(i);
		                    newLine = newLine.substring(0, newLine.length() - 1);
		                }
		                ret.add(newLine);
		                newLine = "";
		            } 

		            read = 0;
		        }

		        else if (i == (string.length() - 1)){
		            newLine += character;
		            ret.add(newLine);
		        }		        else{
		            newLine += character;
		        }
		    }
		    return ret;
	}
}
