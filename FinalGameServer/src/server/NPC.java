package server;

import sage.scene.shape.Sphere;
import graphicslib3D.Point3D;

public class NPC {
	GameServerTCP server;
	int id;
	Point3D location;
	int rotation = 0;
	
	public NPC(int id){
		this.id = id;
	}
	
	public double getX(){
		return location.getX();
	}
	
	public double getY(){
		return location.getY();
	}
	
	public double getZ(){
		return location.getZ();
	}
	
	public void updateLocation(){
		double locRads = rotation * Math.PI /180;
		double x = Math.sin(locRads);
		double y = Math.cos(locRads);
		Point3D p = new Point3D((Math.sin(locRads)) / 10, 0, (Math.cos(locRads)) / 10);
		location = location.add(p);
	}
	
	public int getID(){
		return id;
	}

	public void setLocation(Point3D loc) {
		location = loc;
	}

	public Point3D getPoint() {
		// TODO Auto-generated method stub
		return location;
	}
	
	public int getRotation(){
		return rotation;
	}
	
	public void rotate(int amt){
		int newRot = rotation + amt;
		if(newRot > 360){
			newRot -= 360;
		}
		else if (newRot < 0){
			newRot += 360;
		}
		rotation = newRot;
	}

}
