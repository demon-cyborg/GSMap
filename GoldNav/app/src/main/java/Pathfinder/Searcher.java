package Pathfinder;

import android.content.res.AssetManager;

import java.util.*;
import java.io.*;

public class Searcher {
	private HashMap<String,HashSet<String>> rtv_f1, rtv_f2, rtv_f3;
    private String[] rooms;

	public Searcher(AssetManager assets) throws IOException{
		rtv_f1 = createRTV(assets.open("RHB_F1_RTN.txt"), rtv_f1);
		rtv_f2 = createRTV(assets.open("RHB_F2_RTN.txt"), rtv_f2);
		rtv_f3 = createRTV(assets.open("RHB_F3_RTN.txt"), rtv_f3);
        createArray();
	}

	private HashMap<String,HashSet<String>> createRTV(InputStream fileInput, HashMap<String,HashSet<String>> roomToVertex) throws IOException {
		roomToVertex = new HashMap<String,HashSet<String>>();
		Scanner file = new Scanner(fileInput);

		while(file.hasNextLine()) {
			String temp = file.nextLine();
			String[] roomInfo = temp.split("\t");
			roomToVertex.put(roomInfo[0], new HashSet<String>());
			String[] roomName = roomInfo[1].split(":");
			for(String room : roomName) {
				roomToVertex.get(roomInfo[0]).add(room.trim());
			}
		}
		return roomToVertex;
	}

	private void createArray() {
		TreeSet<String> temp = new TreeSet<String>();
		for(HashSet roomSet: rtv_f1.values()) {
            temp.addAll(roomSet);
		}
		for(HashSet roomSet: rtv_f2.values()) {
            temp.addAll(roomSet);
		}
		for(HashSet roomSet: rtv_f3.values()) {
            temp.addAll(roomSet);
		}
		rooms = temp.toArray(new String[temp.size()]);
	}

    public String[] getRooms() {
        return rooms;
    }
	
	public ArrayList<String> findVertex(String location) { // searches building to find the vertices associated with the locations
		ArrayList<String> vertexLabel = new ArrayList<String>(); // Used a list as locations can be accessed to multiple vertex
		for(Map.Entry<String, HashSet<String>> entry: rtv_f1.entrySet()) {	// Goes through all entries on the first floor
			Iterator<String> roomIt = entry.getValue().iterator();			// Iterates through each location name
			while(roomIt.hasNext()) {
				String room = roomIt.next();
				if(room.contains(location)) {								// If the location is correct
					vertexLabel.add("F1_"+entry.getKey());						// Adds the vertexLabel to the ArrayList
					break;
				}
			}
		}
		
		for(Map.Entry<String, HashSet<String>> entry: rtv_f2.entrySet()) {	// Goes through all entries on the second floor
			Iterator<String> roomIt = entry.getValue().iterator();			// Iterates through each location name
			while(roomIt.hasNext()) {
				String room = roomIt.next();
				if(room.contains(location)) {								// If the location is correct
					vertexLabel.add("F2_"+entry.getKey());						// Adds the vertexLabel to the ArrayList
					break;
				}
			}
		}
		
		for(Map.Entry<String, HashSet<String>> entry: rtv_f3.entrySet()) {	// Goes through all entries on the second floor
			Iterator<String> roomIt = entry.getValue().iterator();			// Iterates through each location name
			while(roomIt.hasNext()) {
				String room = roomIt.next();
				if(room.contains(location)) {								// If the location is correct
					vertexLabel.add("F3_"+entry.getKey());						// Adds the vertexLabel to the ArrayList
					break;
				}
			}
		}
		return vertexLabel;
	}
}