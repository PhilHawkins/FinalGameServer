package server;

import graphicslib3D.Point3D;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

import sage.networking.server.GameConnectionServer;
import sage.networking.server.IClientInfo;

public class GameServerTCP extends GameConnectionServer<UUID> {
	private NPCController NPCCont;

	public GameServerTCP(String localPort)
			throws IOException {
		super(Integer.parseInt(localPort), ProtocolType.TCP);
		// TODO Auto-generated constructor stub
	}
	
	public void setNPCController(NPCController cont){
		NPCCont = cont;
	}
	
	 public void acceptClient(IClientInfo ci, Object o) // override
	 { 
		 String message = (String)o;
		 String[] messageTokens = message.split(",");
		 if(messageTokens.length > 0)
		 { 
		 	if(messageTokens[0].compareTo("join") == 0) // received “join”
		 	{ // format: join,localid
				 UUID clientID = UUID.fromString(messageTokens[1]);
				 addClient(ci, clientID);
				 sendJoinedMessage(clientID, true);
		 	}
		 }
	 }
	 
	 public void sendJoinedMessage(UUID clientID, boolean success)
	 { // format: join, success or join, failure
		  try
		  { 
			  String message = new String("join,");
			  if(success) 
			  {
				  message += "success";
				  sendNPCDetails(clientID);
			  }
			  else 
				  message += "failure";
			  sendPacket(message, clientID);
		  }
		  catch (IOException e) 
		  { 
			  e.printStackTrace();
		  } 
	  }
	 
	 

	public void processPacket(Object o, InetAddress senderIP, int sndPort)
	 {
		 String message = (String) o;
		 String[] msgTokens = message.split(",");
		 if(msgTokens.length > 0)
		 { 
			 if(msgTokens[0].compareTo("bye") == 0) // receive “bye”
			 { // format: bye,localid
				 UUID clientID = UUID.fromString(msgTokens[1]);
				 sendByeMessages(clientID);
				 removeClient(clientID);
			 }
			 if(msgTokens[0].compareTo("create") == 0) // receive “create”
			 { // format: create,localid,x,y,z
				 UUID clientID = UUID.fromString(msgTokens[1]);
				 String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
				 String planet = msgTokens[5];
				 sendCreateMessages(clientID, pos, planet);
				 sendWantsDetailsMessages(clientID);
			 }
			 if(msgTokens[0].compareTo("dsfr") == 0) // receive “details for”
			 { // etc….. }
				 UUID clientID = UUID.fromString(msgTokens[1]);
				 UUID remID = UUID.fromString(msgTokens[2]);
				 String[] pos = {msgTokens[3], msgTokens[4], msgTokens[5]};
				 String planet = msgTokens[6];
				 sendDetailsMessage(clientID, remID, pos, planet);
			 }
			 if(msgTokens[0].compareTo("move") == 0) // receive “move”
			 { // etc….. }
				 UUID clientID = UUID.fromString(msgTokens[1]);
				 String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
				 sendMoveMessages(clientID, pos);
			 } 
		 
		 }
	 }
	 
	 public void sendCreateMessages(UUID clientID, String[] position, String planet)
	 { // format: create, remoteId, x, y, z
		  try
		  { 
			  String message = new String("create," + clientID.toString());
			  message += "," + position[0];
			  message += "," + position[1];
			  message += "," + position[2];
			  message += "," + planet;
			  forwardPacketToAll(message, clientID);
		  }
		  catch (IOException e) { 
			  e.printStackTrace();
		  } 
	  }
	 
	 public void sendDetailsMessage(UUID clientID, UUID remoteId, String[] position, String planet)
	 { // etc….. 
		 String message = new String("dsfr," + clientID.toString());
		  message += "," + position[0];
		  message += "," + position[1];
		  message += "," + position[2];
		  message += "," + planet;
		  try {
			sendPacket(message, remoteId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void sendWantsDetailsMessages(UUID clientID)
	 { // etc….. 
		 String message = new String("wsds," + clientID.toString());
		 try {
			forwardPacketToAll(message, clientID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void sendMoveMessages(UUID clientID, String[] pos)
	 { // etc….. }
		 String message = new String("move," + clientID.toString());
			message += "," + pos[0]+"," + pos[1] + "," + pos[2];
			try {
				forwardPacketToAll(message, clientID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }

	 public void sendByeMessages(UUID clientID)
	 { // etc….. 
		try {
            String msg = new String("bye," + clientID.toString());
            forwardPacketToAll(msg, clientID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		removeClient(clientID);
	 }

	public void sendNPCinfo(Point3D first, Point3D second) {
		Point3D firstNPCPoint = NPCCont.getFirstPos();
		Point3D secondNPCPoint = NPCCont.getSecondPos();
		
		 String message = new String("mnpc,");
		 message += firstNPCPoint.getX() + "," + firstNPCPoint.getY() + "," + firstNPCPoint.getZ();
		 message += "," + secondNPCPoint.getX() + "," + secondNPCPoint.getY() + "," + secondNPCPoint.getZ();
		 
		 try{
			 sendPacketToAll(message);
		 }
		 catch(IOException e){
			 e.printStackTrace();
		 }
		
	}
	
	private void sendNPCDetails(UUID clientID) {
		Point3D firstNPCPoint = NPCCont.getFirstPos();
		Point3D secondNPCPoint = NPCCont.getSecondPos();
		
		 String message = new String("npcds,");
		 message += firstNPCPoint.getX() + "," + firstNPCPoint.getY() + "," + firstNPCPoint.getZ();
		 message += "," + secondNPCPoint.getX() + "," + secondNPCPoint.getY() + "," + secondNPCPoint.getZ();
		 
		 try{
			 sendPacket(message, clientID);
		 }
		 catch(IOException e){
			 e.printStackTrace();
		 }
	}


}
