package src;

import java.util.*;
import java.io.*;

public class Engine {

	Outfile out;
	public static long startID = 76561197968613153l; //Royalaid
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        Engine engine = new Engine();
        engine.startEngine(startID);
	}
	
	
	//Constructor
	public Engine(){}
	
	//Starts the program using a given user ID
	public void startEngine(long startID){
		
		LinkedList<SteamUser> queue = new LinkedList<SteamUser>();
		HashMap<Long, SteamUser> scrapedUsers = new HashMap<Long, SteamUser>(); 
		SteamGetter getter = new SteamGetter();
		
		try {
			queue.add(new SteamUser(startID)); //Add first user
			out = new Outfile();
			while(!queue.isEmpty()){
				
				//gets a single individuals Profile info
				SteamUser curUser = queue.removeFirst();
				if(!scrapedUsers.containsKey(curUser.getID())){
					
					curUser = getter.getPlayerSummary(curUser);
					out.writeProfileOf(curUser);
					
					ArrayList<SteamUser> friends = getter.userFriendData(curUser);
					for(SteamUser freind: friends){
						queue.add(freind);
						curUser.addFriend(freind);
						scrapedUsers.put(curUser.getID(), curUser);
					}
					out.writeFriendsOf(curUser);
					out.flush();
					
					scrapedUsers.put(curUser.getID(), curUser);
					System.out.println(curUser.getID() + " scraped");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
