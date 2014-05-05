/**
 * Copyright 2014 A-Team Games
 *
 * This file is part of ateam-tanks.
 *
 *    ateam-tanks is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    ateam-tanks is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with ateam-tanks.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * A player class for a simple AI
 */

import java.awt.Color;
import java.util.ArrayList;
import java.lang.*;

public class AIPlayer3 extends Player
{
	private ArrayList<Obstacle> objects;
	private SimpleTank opponent;
	private OrderQueue orders;
	private int frames;
	public AIPlayer3(ArrayList<Obstacle> objects, ArrayList<SimpleTank> tanks, SimpleTank enemy, Color c) {
		super ("Computer", tanks, c);
		this.objects = objects;
		this.opponent = enemy;
		this.orders = new OrderQueue();
		this.frames = 0;
	}
	// Gives orders to tank
	public void giveOrders(int frameLimit) {
		this.orders = new OrderQueue();
		this.frames = frameLimit;
		
		Vector3D orig = this.ownedTanks.get(0).getPosition();
		Vector3D opp = this.opponent.getPosition();
		Obstacle o = this.pathClear(orig, opp, 1);
		Vector3D newPos = null;
		// If nothing is in the way shoot 
		if (o == null) {
			this.shoot(orig, opp);
		}
		// Else find position to move to
		else {
			Vector3D ov = o.getPosition();
			double d1 = this.getDist(orig, ov);
			double d2 = this.getDist(opp, ov);
			// If opponent is farther from obstacle than AI find position to attack
			if (d2 > d1) {
				newPos = this.getPos(o);
			}
			// Else find position to defend
			else {
				newPos = this.getPos(o);
			}
			// Get orders to move to new position
			this.move(orig, newPos);
			
		}
		// Once done moving attack with remaining frames
		if (this.frames >= 3) {
			this.shoot(newPos, opp);
		}
		// Give tanks orders
		this.ownedTanks.get(0).giveOrders(this.orders);
		return;
	}
	
	// Spray shoot at opponent
	public void shoot(Vector3D v1, Vector3D v2) {
		double angle = this.getAngle(v1, v2);
		int t = 0;
		int l = 1;
		double handling = this.ownedTanks.get(0).getHandling();
		while (this.frames >= 3) {
			this.orders.add(new ShootOrder(angle));
			this.frames -= 3;
			int y = t % 2;
			if (y == 0) {
				angle -= l*handling/2;
			}
			else {
				angle += l*handling/2;
			}
			t += 1;
			l += 1;
		}
		return;
	}
	//  Moves from v1 to v2
	public void move(Vector3D v1, Vector3D v2) {
		double angle = this.getAngle(v1, v2);
		// Turn to face v2 from v1
		this.turn(angle);
		// Make MoveOrder to move to v2 from v1
		if (this.frames > 0) {
			double speed = this.ownedTanks.get(0).getSpeed();
			double dist = this.getDist(v1, v2);
			double fr2 =  dist/speed;
					
			int fr = (int) fr2;
			if (this.frames <  fr) {
				fr = this.frames;
			}
			this.frames -= fr;
			this.orders.add(new MoveOrder(fr, 1));
			return;
		}
		else {
			return;
		}
	}
	// Find if an obstacle is in the way
	public Obstacle pathClear(Vector3D v1, Vector3D v2, int x) {
		double speed = this.ownedTanks.get(0).getSpeed();
		// Get number of frames to reach v2 from v1
		int frames = this.moveFrames(v1, v2, speed);
		Obstacle o = null;
		while (frames > 0) {
			double angle = this.getAngle(v1, v2);
			v1 = new Vector3D(v1, new Vector3D ( speed, new Direction(angle)));
			// If x == 0 check if tank will collide
			if (x == 0) {
				o = this.isLegit(v1);
			}
			// Else check if bullets will collide
			else {
				o = this.isLegit2(v1);
			}
			if (o == null) {
				frames -= 1;
			}
			else {
				return o;
			}
		}
		// If no collision through path then return null
		return null;
	}
	// Number of move frames 
	public int moveFrames(Vector3D v1, Vector3D v2, double speed) {
		double dist = this.getDist(v1, v2);
		double frames = dist/speed;
		return (int) frames;
	}
	// Turn order to face direction for tank to move
	public void turn(double angle) {
		SimpleTank temp = this.ownedTanks.get(0);
		double orig = temp.getDirection().getTheta();
		// Get difference between original angle and tank angle
		double diff = orig - angle;
		diff = diff % 360;
		int dir = -1;
		// If diff > 180 turn opposite direction
		if (diff > 180) {
			dir = 1;
			diff = 360-diff;
		}
		else {
			// If diff < 0 make diff the opposite
			if (diff < 0) {
				dir = 1;
				diff = -diff;
				// if opposite > 180 turn opposite direction
				if (diff > 180) {
					dir = -1;
					diff = 360-diff;
				}
			}
		}
		// Get number of turn frames
		double handling = this.ownedTanks.get(0).getHandling();
		double fr2 = diff/handling;
		int fr = (int) fr2;
		// If frames < turn frames, turn rest of frames
		if (this.frames <  fr) {
			fr = this.frames;
		}
		this.frames -= fr;
		// Make TurnOrder
		this.orders.add(new TurnOrder(fr, dir));
	}
	// Get attack position to move
	public Vector3D getPos(Obstacle o) {
		Vector3D oppPos = this.opponent.getPosition();
		Vector3D pos = this.ownedTanks.get(0).getPosition();
		// Get diff between angles from object
		double angle1 = this.getAngle(pos, o.getPosition());
		double angle2 = this.getAngle(oppPos, o.getPosition());
		double diff = angle1 - angle2;	
		double newAngle = 0;
		// Get middle between both angles
		if (diff < -180 || diff > 180) {
			newAngle = angle1 + (diff/2);
		}
		else {
			newAngle = angle1 - (diff/2);
		}
		if (newAngle < 0) {
			newAngle = newAngle + 360;
		}
		else if (newAngle > 360) {
			newAngle = newAngle - 360;
		}
		boolean b = false;
		int i = 2;
		Vector3D temp = null;
		// find position that is clear of obstacles
		while (b == false) {
			// Get new position
			temp = new Vector3D(new Vector3D(o.hitboxRadius + i*this.ownedTanks.get(0).hitboxRadius, new Direction(newAngle)), o.getPosition());
			// Check if path is clear from tank to newPos
			Obstacle o2 = this.pathClear(temp, this.ownedTanks.get(0).getPosition(), 0);
			// If nno obstacle in way b == true
			if (o2 == null) {
				b = true;
			}
			i += 1;
		}
		return temp;
	}
	// Get defense position to move
	public Vector3D getPos2(Obstacle o) {
		return new Vector3D (0, 0, 0);
	}
	// Check if Vector3D collides with an obstacle
	public Obstacle isLegit(Vector3D v) {
		SimpleTank plTank = this.ownedTanks.get(0);
		double hitBox = plTank.hitboxRadius;
		for (Obstacle o : this.objects) {
			double dist = this.getDist(v, o.getPosition());
			if (dist <= hitBox + o.hitboxRadius) {
				return o;
			}
		}
		return null;
	}
	// Check if bullet will collide with an obstacle
	public Obstacle isLegit2(Vector3D v) {
		SimpleTank plTank = this.ownedTanks.get(0);
		double hitBox = 1;
		for (Obstacle o : this.objects) {
			double dist = this.getDist(v, o.getPosition());
			if (dist <= hitBox + o.hitboxRadius) {
				return o;
			}
		}
		return null;
	}
	// Distance between two Vector3Ds
	public double getDist(Vector3D v1, Vector3D v2) {
		double deltaX = v2.getX() - v1.getX();
		double deltaY = v2.getY() - v1.getY();
		double temp = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);
		return Math.sqrt(temp);
	}
	// Any object added to map needs to be added
	public void addObject(Obstacle o) {
		this.objects.add(o);
	}
	public void remObject(Obstacle o) {
		this.objects.remove(o);
	}
	// Returns the angle between both tanks
	public double getAngle(Vector3D v1, Vector3D v2) {
		double deltaX = v2.getX() - v1.getX();
		double deltaY = v2.getY() - v1.getY();
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
		if (angle < 0) {
			angle += 360;
		}
		return angle;
	}
	//Print the position of a Vector3D
	public void printPos(Vector3D v) {
		System.out.println("Pos = (" + v.getX() + "," + v.getY() + ")");
	}
}
	
	
	