package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/***
 * Outfile manages all the output file formats. Needs to be readable
 * by SASS, Excel, and the program itself.
 * 
 * @author summersb
 *
 */
public class Outfile extends Thread{
	private BufferedWriter bw;
	private FileWriter fw;
	private File file;
	private Calendar now;
	
	
	/**
	 * Constructor for the Outfile class. Creates file if needed otherwise
	 * appends data to end of current file.
	 * 
	 * @throws IOException
	 */
	public Outfile() throws IOException {
		now = Calendar.getInstance();
//		File dir = new File (Global.ASSETSPWD);//set the directory address
		file = new File( 			
				Global.AEOLIPILE+
				"_"+now.get(Calendar.YEAR)+
				"_"+(now.get(Calendar.MONTH)+1)+
				"_"+now.get(Calendar.DAY_OF_MONTH)+".txt");
		
		
		
		if (!file.exists()) {
			file.setWritable(true);
			file.setReadable(true);
			file.createNewFile();
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
		}
		else if(file.exists()) 
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.append('\n' + "------------------" + '\n');
		
		
		
		//Denote stop and restart point
		
	}
	
	
	/**
	 * Writes friends to the end of the file.
	 * Form is:
	 * "ID:Friend1,Friend2,Friend3,...,friendN\\n"
	 * @param friend
	 * @throws IOException
	 */
	public void writeFriendsOf(SteamUser friend) throws IOException{
		bw.append(Long.toString(friend.getID()));
		bw.append(':');
		bw.append(friend.getFriendsString());
		
	}
	
	
	/**
	 * Writes friends profile to the end of the file.
	 * Form is:
	 * "ID:PersonalName,profileurl,avatar(url),avatarmedium(url),
	 * avatarfull(url),personastate"
	 * @param profile
	 * @throws IOException
	 */
	public void writeProfileOf(SteamUser profile) throws IOException{
		bw.append(profile.toSASProfile());
	}
	
	
	public void flush() throws IOException{
		bw.flush();
	}
}
