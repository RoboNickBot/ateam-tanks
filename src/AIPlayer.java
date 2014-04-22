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

public class AIPlayer extends Player
{
	private ArrayList<Obstacle> objects;
	private SimpleTank opponent;
	private OrderQueue orders;
	private int frames;
	public AIPlayer(ArrayList<Obstacle> objects, ArrayList<SimpleTank> tanks, SimpleTank enemy, Color c) {
		super ("Computer", tanks, c);
		this.objects = objects;
		this.opponent = enemy;
		this.orders = new OrderQueue();
		this.frames = 0;
	}
	// Gives orders to game
	public void giveOrders(int frameLimit) {
		this.orders = new OrderQueue();
		this.frames = frameLimit;
		this.printPos(this.ownedTanks.get(0).getPosition());
		Vector3D movePos = this.getPos();
		this.printPos(movePos);
		Vector3D finPos = this.move(this.ownedTanks.get(0).getPosition() , movePos);
		this.printPos(finPos);
		if (this.frames >= 3) {
			this.shoot(finPos);
		}
		this.ownedTanks.get(0).giveOrders(this.orders);
		return;
	}
	// Spray shoot at opponent
	public void shoot(Vector3D v) {
		double angle = this.getAngle(v, this.opponent.getPosition());
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
	public Vector3D move(Vector3D v1, Vector3D v2) {
		this.printPos(v2);
		Obstacle o = this.pathClear(v1, v2, 1);
		Vector3D newPos = null;
		// If no obstacle in way
		if (o == null) {
			this.travel(v1, v2);
			return v2;
		}
		// Alter move around obstacle in the way of path
		else {
			System.out.println("move alter move");
			newPos = this.alterMov(o, this.ownedTanks.get(0), 1);
			this.move(v1, newPos);
			Obstacle o2 = this.pathClear(newPos, v2, 0);
			if (o2 != null) {
				this.travel(newPos, v2);
			}
		}
		return newPos;
	}
	// Alter move around obstacle in the way
	public Vector3D alterMov(Obstacle o, SimpleTank s, int x) {
		double angle1 = this.getAngle(this.ownedTanks.get(0).getPosition(), o.getPosition());
		double angle2 = this.getAngle(this.opponent.getPosition(), o.getPosition());
		double diff = angle1 - angle2;
		System.out.println(diff + " = " + angle1 + " - " + angle2);
		System.out.println("Diff angle = " + diff);
		double newAngle = 0;
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
		System.out.println("alterMov angle = " + newAngle);
		boolean b = false;
		int i = 2;
		Vector3D temp = null;
		while (b == false) {
			System.out.println("i = " + i);
			temp = new Vector3D(new Vector3D(o.hitboxRadius + i*s.hitboxRadius, new Direction(newAngle)), o.getPosition());
			this.printPos(temp);
			Obstacle o2 = this.pathClear(temp, s.getPosition(), x);
			if (o2 == null) {
				b = true;
			}
			i += 1;
		}
		System.out.println("New Pos");
		this.printPos(temp);
		return temp;
	}
	// Find if an obstacle is in the way
	public Obstacle pathClear(Vector3D v1, Vector3D v2, int x) {
		double speed = this.ownedTanks.get(0).getSpeed();
		int frames = this.moveFrames(v1, v2, speed);
		while (frames > 0) {
			double angle = this.getAngle(v1, v2);
			v1 = new Vector3D(v1, new Vector3D ( speed, new Direction(angle)));
			Obstacle o = this.isLegit(v1, x);
			if (o == null) {
				frames -= 1;
			}
			else {
				return o;
			}
		}
		return null;
	}
	// Number of move frames 
	public int moveFrames(Vector3D v1, Vector3D v2, double speed) {
		double dist = this.getDist(v1, v2);
		double frames = dist/speed;
		return (int) frames;
	}
	// Moves tank from v1 to v2
	public void travel(Vector3D v1, Vector3D v2) {
		double angle = this.getAngle(v1, v2);
		System.out.println("travel angle = " + angle);
		this.turn(angle);
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
	// Turn order to face direction for tank to move
	public void turn(double angle) {
		SimpleTank temp = this.ownedTanks.get(0);
		double orig = temp.getDirection().getTheta();
		orig = orig % 360;
		double diff = orig - angle;
		System.out.println("Diff angle1 = " + diff + " = " + orig + " + " + angle);
		int dir = -1;
		if (diff > 180) {
			dir = 1;
			diff = 360-diff;
		}
		else {
			if (diff < 0) {
				dir = 1;
				diff = -diff;
				if (diff > 180) {
					dir = -1;
					diff = 360-diff;
				}
			}
		}
		System.out.println("Diff angle2 = " + diff);
		double handling = this.ownedTanks.get(0).getHandling();
		double fr2 = diff/handling;
		int fr = (int) fr2;
		System.out.println("turn frames = " + fr);
		if (this.frames <  fr) {
			fr = this.frames;
		}
		this.frames -= fr;
		this.orders.add(new TurnOrder(fr, dir));
	}
	// Get position to move to
	public Vector3D getPos() {
		Vector3D oppPos = this.opponent.getPosition();
		double angle = this.getAngle(this.ownedTanks.get(0).getPosition(), oppPos);
		boolean b = false;
		double dist =100;
		
		Vector3D m = new Vector3D(dist, new Direction(angle-180));
		Vector3D newPos = new Vector3D(m ,oppPos);
		while (b == false) {
			Obstacle o = this.pathClear(oppPos, newPos, 0);
			if (o == null) {
				b = true;
			}
			else {
				System.out.println("getPos alterMov");
				newPos = this.alterMov(o, this.opponent, 0);
			}
		}
		return newPos;
	}
	// Check if Vector3D collides with an obstacle
	public Obstacle isLegit(Vector3D v, int x) {
		SimpleTank plTank = this.ownedTanks.get(0);
		double hitBox = 1;
		if (x == 1) {
			hitBox = plTank.hitboxRadius;
		}
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
	
	
	