package server;

import java.util.Random;

import sage.ai.behaviortrees.BTAction;
import sage.ai.behaviortrees.BTStatus;

public class turn extends BTAction {
	GameServerTCP server;
	NPC NPC;
	public turn(NPC n){
		NPC = n;
	}

	@Override
	protected BTStatus update(float time) {

		Random rand = new Random();
		int r = rand.nextInt(360);
		NPC.rotate(r);
				
		return BTStatus.BH_SUCCESS;
	}
	

}
