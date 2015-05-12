package server;

import java.io.IOException;

import sage.ai.behaviortrees.BTCompositeType;
import sage.ai.behaviortrees.BehaviorTree;

public class GameServerStarter {
	private long startTime;
	private long lastUpdateTime;
	 private NPCController npcCtrl;
	 GameServerTCP server;

	 public GameServerStarter(String port) // constructor
	 { 
		 startTime = System.nanoTime();
		 lastUpdateTime = startTime;
		 
	 // 	start networking TCP server (as before)
		 try {
			 server = new GameServerTCP(port);
		 } catch (IOException e) {
			 e.printStackTrace();
		 }

		 npcCtrl = new NPCController(server);
		 
	 // 	start NPC control loop
		 npcCtrl.setupNPCs();
		 
		 server.setNPCController(npcCtrl);
		 
		 npcCtrl.startNPCControl();
		 //npcLoop();
	 }
	 
	 


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameServerStarter gs = new GameServerStarter(args[0]);
	}

}
