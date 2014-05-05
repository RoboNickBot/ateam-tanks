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

public class Comp3 extends Player
{
	private ArrayList<Obstacle> objects;
	private SimpleTank opponent;
	private OrderQueue orders;
	private int frames;
	public Comp3(ArrayList<Obstacle> objects, ArrayList<SimpleTank> tanks, SimpleTank enemy, Color c) {
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
		Vector3D movePos = this.getPos();
		this.printPos(movePos);
		Vector3D finPos = this.move(movePos);
		this.printPos(finPos);
		if (this.frames >= 3) {
			this.shoot(finPos);
		}
		this.ownedTanks.get(0).giveOrders(this.orders);
		return;
	}
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
	public Vector3D move(Vector3D v) {
		this.printPos(v);
		Vector3D pos = this.ownedTanks.get(0).getPosition();
		Obstacle o = this.pathClear(this.ownedTanks.get(0).getPosition(), v);
		Vector3D newPos = null;
		int i = 2;
		if (o == null) {
			this.travel(pos, v);
			return v;
		}
		else {
			newPos = this.alterPos(v, o, this.getAngle(pos, v), i);
			i += 2;
			this.move(newPos);
		}
		return newPos;
	}
	public Obstacle pathClear(Vector3D v1, Vector3D v2) {
		double speed = this.ownedTanks.get(0).getSpeed();
		int frames = this.moveFrames(v1, v2, speed);
		while (frames > 0) {
			double angle = this.getAngle(v1, v2);
			v1 = new Vector3D(v1, new Vector3D ( speed, new Direction(angle)));
			Obstacle o = this.isLegit(v1);
			if (o == null) {
				frames -= 1;
			}
			else {
				return o;
			}
		}
		return null;
	}
	public int moveFrames(Vector3D v1, Vector3D v2, double speed) {
		double dist = this.getDist(v1, v2);
		double frames = dist/speed;
		return (int) frames;
	}
	public void travel(Vector3D v1, Vector3D v2) {
		double angle = this.getAngle(v1, v2);
		this.turn(angle);
		if (this.frames > 0) {
			double speed = this.ownedTanks.get(0).getSpeed();
			double dist = this.getDist(v1, v2);
			double fr2 =  dist/speed;
			System.out.println("Dist = " + dist);
			
			int fr = (int) fr2;
			System.out.println("Frames = " + fr);
			if (this.frames <  fr) {
				System.out.println("A");
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
	public void turn(double angle) {
		SimpleTank temp = this.ownedTanks.get(0);
		double orig = temp.getDirection().getTheta();
		double diff = orig - angle;
		System.out.println(diff);
		int dir = -1;
		if (diff > 180) {
			dir = 1;
			diff = 360-diff;
		}
		else {
			if (diff < 0) {
				System.out.println("1");
				dir = 1;
				diff = -diff;
				if (diff > 180) {
					dir = -1;
					diff = 360-diff;
				}
			}
		}
		System.out.println(diff);
		double handling = this.ownedTanks.get(0).getHandling();
		double fr2 = diff/handling;
		int fr = (int) fr2;
		if (this.frames <  fr) {
			fr = this.frames;
		}
		this.frames -= fr;
		this.orders.add(new TurnOrder(fr, dir));
	}
	public Vector3D getPos() {
		Vector3D oppPos = this.opponent.getPosition();
		double angle = this.getAngle(this.ownedTanks.get(0).getPosition(), oppPos);
		boolean b = false;
		double dist =100;
		Vector3D newPos = null;
		Vector3D m = new Vector3D(dist, new Direction(angle-180));
		while (b == false) {
			newPos = new Vector3D(m ,oppPos);
			Obstacle o = this.isLegit(newPos);
			if (o == null) {
				b = true;
			}
			else {
				m = this.alterPos(newPos, o, angle, 2);
			}
		}
		return newPos;
	}
	public Vector3D alterPos(Vector3D v, Obstacle o, double a, int i) {
		Vector3D obstPos = o.getPosition();
		double angle = this.getAngle(v, obstPos);
		if (angle > 180) {
			a = a + 90;
			if (a > 360) {
				a -= 360;
			}
		}
		else {
			a = a-90;
			if (a < 0) {
				a += 360;
			}
		}
		Vector3D temp = new Vector3D(new Vector3D(o.hitboxRadius + i*this.ownedTanks.get(0).hitboxRadius, new Direction(a)), obstPos);
		double angle2 = this.getAngle(temp, this.opponent.getPosition());
		double dist = this.getDist(temp, this.opponent.getPosition());
		return new Vector3D(dist, new Direction(angle2-180));
	}
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
		System.out.println(angle);
		return angle;
	}
	public void printPos(Vector3D v) {
		System.out.println("Pos = (" + v.getX() + "," + v.getY() + ")");
	}
}
	
	
	