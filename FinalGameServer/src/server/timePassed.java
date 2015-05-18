package server;

import sage.ai.behaviortrees.BTCondition;

public class timePassed extends BTCondition {
	long lastUpdateTime;

	public timePassed(boolean toNegate) {
		super(toNegate);
		lastUpdateTime = System.nanoTime();
	}


	protected boolean check() {
		// TODO Auto-generated method stub
		long curTime = System.nanoTime();
		float elapsedTime = (curTime - lastUpdateTime) / 1000000;
		if(elapsedTime > 2000){
			//System.out.println("timepassed");
			lastUpdateTime = curTime;
			return true;
		}
		else
			return false;
	}
	

}
