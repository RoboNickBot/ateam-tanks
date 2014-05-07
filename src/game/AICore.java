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

package game;

import java.awt.Color;
import java.util.ArrayList;
import java.lang.*;

public class AICore extends OldPlayer
{
	private ArrayList<Obstacle> objects;
	private SimpleTank opponent;
	private OrderQueue orders;
	private int frames;
	public AICore(ArrayList<Obstacle> objects, ArrayList<SimpleTank> tanks, SimpleTank enemy) {
		super ("Computer", tanks, Color.white);
		this.objects = objects;
		this.opponent = enemy;
		this.orders = new OrderQueue();
		this.frames = 0;
	}
	// Gives orders to tank
	public ArrayList<OrderQueue> giveOrders(int frameLimit) {
		
		this.orders = new OrderQueue(frameLimit, this.ownedTanks.get(0).uid());
		this.frames = frameLimit;

		Vector3D orig = this.ownedTanks.get(0).getPosition();
		Obstacle obst= this.checkIfCol(orig);
		Vector3D opp = this.opponent.getPosition();
		if (obst != null) {
			System.out.println("Collision");
			double angle = this.getAngle(obst.getPosition(), orig);
			Vector3D vec = new Vector3D(new Vector3D(18, new Direction(angle)), orig);
			this.move(orig, vec);
			this.shoot(vec, opp);
		}
		else {
			Obstacle o = this.pathClear(orig, opp);
			Vector3D newPos = null;
			Vector3D shootAt = null;
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
				if (d2 > d1-30) {
					newPos = this.getPos(orig, opp, o);
					shootAt = opp;
				}
				// Else find position to defend
				else {
					newPos = this.getPos2(orig, opp, o);
					shootAt = this.getPos(opp, orig, o);
				}
				// Get orders to move to new position
				this.move(orig, newPos);

			}
			// Once done moving attack with remaining frames
			if (this.frames >= 9) {
				this.shoot(newPos, opp);
			}
			// return orders
		}
        ArrayList<OrderQueue> output = new ArrayList<OrderQueue>();
		output.add(this.orders.clone());
		return output;
	}
	
	public Obstacle checkIfCol(Vector3D v) {
		double cObst = 10000;
		Obstacle o = null;
		for (Obstacle temp : this.objects) {
			double dist = this.getDist(v, temp.getPosition() );
			if (dist < cObst) {
				cObst = dist;
				o = temp;
			}
		}
		if (o != null) {
			double angle = getAngle(v, o.getPosition());
			Vector3D v2 = new Vector3D(v, new Vector3D ( 10, new Direction(angle)));
			Obstacle o2 = this.isLegit(v2);
			if (o2 != null) {
				return o2;
			}
			else {
				return null;
			}
		}
		return null;
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
	public Obstacle pathClear(Vector3D v1, Vector3D v2) {
		double speed = this.ownedTanks.get(0).getSpeed();
		// Get number of frames to reach v2 from v1
		int frames = this.moveFrames(v1, v2, speed);
		Obstacle o = null;
		while (frames > 0) {
			double angle = this.getAngle(v1, v2);
			v1 = new Vector3D(v1, new Vector3D ( speed, new Direction(angle)));
			o = this.isLegit(v1);
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
	public Vector3D getPos(Vector3D v1, Vector3D v2, Obstacle o) {
		long startTime = System.nanoTime();
		// Get diff between angles from object
		double angle1 = this.getAngle(v1, o.getPosition());
		double angle2 = this.getAngle(v2, o.getPosition());
		double diff = angle1 - angle2;	
		double newAngle = 0;
		if (diff < 0) {
			diff = -diff;
			if (diff > 180) {
				diff = 360-diff;
				newAngle = angle2 + (diff/2);
			}
			else {
				newAngle = angle2 - (diff/2);
			}
		}
		else {
			if (diff > 180) {
				diff = 360-diff;
				newAngle = angle1 + (diff/2);
			}
			else {
				newAngle = angle1 - (diff/2);
			}
		}
		newAngle = newAngle % 360;
		if (newAngle < 0) {
			newAngle = 360 + newAngle;
		}
		boolean b = false;
		int i = 2;
		Vector3D temp = null;
		// find position that is clear of obstacles
		while (b == false) {
			long endTime = System.nanoTime();
			long duration = endTime - startTime;
			double sec = (double) duration / 1000000000.0;
			System.out.println("time = " + sec);
			if (sec > 2.5) {
				b = true;
			}
			// Get new position
			System.out.println("i = " + i);
			temp = new Vector3D(new Vector3D(i*8+30, new Direction(newAngle)), o.getPosition());
			if (temp.getX() > 200 || temp.getX() < -200 || temp.getY() > 200 || temp.getY() < -200) {
				newAngle += 180;
				newAngle = newAngle % 360;
				i = 2;
			}
			else {
				// Check if path is clear from tank to newPos
				o = this.pathClear(temp, v1);
				// If no obstacle in way b == true
				if (o != null) {
					i += 1;
				}
				else {
					b = true;
				}
			}
		}
		return temp;
	}
	// Get defense position to move
	public Vector3D getPos2(Vector3D v1, Vector3D v2, Obstacle o) {
		long startTime = System.nanoTime();
		Vector3D opp = this.getPos(v2, v1, o);
		double angle = this.getAngle(o.getPosition(), v1);
		boolean b = false;
		int i = 2;
		Vector3D temp = null;
		while (b == false) {
			long endTime = System.nanoTime();
			long duration = endTime - startTime;
			double sec = (double) duration / 1000000000.0;
			if (sec > 2.5) {
				b = true;
			}
			System.out.println("i = " + i);
			System.out.println("2");
			// Get new position
			temp = new Vector3D(new Vector3D(i*10, new Direction(angle)), o.getPosition());
			System.out.println("(" + temp.getX() + ", " + temp.getY() + ", " + temp.getZ() + ")");
			// Check if path is clear from tank to newPos
					
			Obstacle o2 = this.pathClear(temp, opp);
			// If no obstacle in way b == true
			if (o2 == null) {
				b = true;
			}
			i += 1;
		}
		return temp;
	}
	// Check if Vector3D collides with an obstacle
	public Obstacle isLegit(Vector3D v) {
		SimpleTank plTank = this.ownedTanks.get(0);
		Vector3D oldPos = plTank.getPosition();
		plTank.setPosition(v);
		for (Obstacle o : this.objects) {
			boolean b = plTank.checkCollision(o);
			if (b == true) {
				plTank.setPosition(oldPos);
				return o;
			}
		}
		plTank.setPosition(oldPos);
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