package server;

import java.io.IOException;

public class GameServerStarter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			GameServerTCP test = new GameServerTCP(args[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
