package server;

import sage.ai.behaviortrees.BTCompositeType;
import sage.ai.behaviortrees.BTSequence;
import sage.ai.behaviortrees.BehaviorTree;
import graphicslib3D.Point3D;

public class NPCController {
	GameServerTCP server;
	private NPC[] NPCList = new NPC[2];
	 BehaviorTree bt = new BehaviorTree(BTCompositeType.SELECTOR);
	 long startTime, lastUpdateTime;
	 
	 public NPCController(GameServerTCP s){
		 server = s;
	 }
	 
	 public void startNPCControl(){
		startTime = System.nanoTime(); 
		lastUpdateTime = startTime;
		setupNPCs();
		setupBehaviorTree();
		npcLoop();
	 }
	 

	 public void npcLoop() // NPC control loop
	 { 
		 while (true)
		 { 
			 long frameStartTime = System.nanoTime();
			 float elapMilSecs = (frameStartTime-lastUpdateTime)/(1000000.0f);
			 if (elapMilSecs >= 50.0f)
			 { 
				 lastUpdateTime = frameStartTime;
				 updateNPCs();
				 server.sendNPCinfo(getFirstPos(), getSecondPos());
				 bt.update(elapMilSecs);
			 }
		 	Thread.yield();
		 } 
	 }
	
	private void setupBehaviorTree() {
		bt.insertAtRoot(new BTSequence(10));
		bt.insertAtRoot(new BTSequence(20));
		bt.insert(10, new timePassed(false));
		bt.insert(10, new turn(NPCList[0]));
		bt.insert(10, new turn(NPCList[1]));
	}

	public void updateNPCs()
	{
		for(int i = 0; i < NPCList.length; i++){
			NPCList[i].updateLocation();
		}
	}
	
	public void setupNPCs(){
		NPC firstNPC = new NPC(0);
		Point3D loc = new Point3D(5, 5, 15);
		firstNPC.setLocation(loc);
		NPCList[0] = firstNPC;
		
		NPC secondNPC = new NPC(1);
		Point3D loc2 = new Point3D(15, 5, 1);
		secondNPC.setLocation(loc2);
		NPCList[1] = secondNPC;
		
	}
	
	public Point3D getFirstPos(){
		return NPCList[0].getPoint();
	}
	
	public Point3D getSecondPos(){
		return NPCList[1].getPoint();
	}

}
