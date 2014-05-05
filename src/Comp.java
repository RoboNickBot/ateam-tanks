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

public class Comp extends Player
{
	private ArrayList<Obstacle> objects;
	private SimpleTank opponent;
	private int moveFrames;
	private double theta;
	public Comp(ArrayList<Obstacle> objects, ArrayList<SimpleTank> tanks, SimpleTank enemy, Color c) {
		super ("Computer", tanks, c);
		this.objects = objects;
		this.opponent = enemy;
	}
	// Gives orders to game
	public void giveOrders(int frameLimit) {
		OrderQueue orders = this.getOrders(this.opponent, 100);
		Sprite q = this.checkCollision();
		while (q != null) {
			System.out.println("null2");
			Vector3D v = this.opponent.getPosition();
			Vector3D pos = this.newPos(q, this.opponent);
			ArrayList<SimpleTank> tanks = new ArrayList<SimpleTank>();
			SimpleTank temp = new SimpleTank(new SpriteList(), tanks, pos, new Direction(), this.opponent.getSpeed(), this.opponent.getHandling(), this.opponent.getColor());
			orders = this.getOrders(temp, 100);
			q = this.checkCollision();
		}
		this.ownedTanks.get(0).giveOrders(orders);
		return;
	}
	public OrderQueue getOrders(SimpleTank s, int frameLimit) {
		int random = (int) (Math.random()*6 + 1);
		System.out.println(random);
		OrderQueue orders = new OrderQueue();
		TurnOrder turn = this.turn(s, random);
		int f1 = turn.getFrames();
		int dir = turn.getDirection();
		System.out.println("Turn order: (" + f1 + " Frames, " + dir+ " Direction)");
		if (f1 > frameLimit) {
			orders.add(new TurnOrder(frameLimit, dir));
			return orders;
		}
		else {
			orders.add(turn);
			frameLimit = frameLimit - f1;
		}
		MoveOrder move = this.move(s);
		int f2 = move.getFrames();
		this.moveFrames = f2;
		System.out.println("Move order: (" + f2 + " Frames, " + move.getDirection() + " Direction)");
		if (f2 > frameLimit) {
			orders.add(new MoveOrder(frameLimit, 1));
			return orders;
		}
		else {
			orders.add(move);
			frameLimit = frameLimit - f2;
		}
		double theta = this.ownedTanks.get(0).getDirection().getTheta();
		this.theta = theta;
		double handling = this.ownedTanks.get(0).getHandling();
		
		if (dir == 1) {
			theta += (f1+random*2)*handling;
		}
		else {
			theta += (f1+random*2)*handling;
		}
		int dif = 100 - frameLimit;
		int t = 0;
		if (dif < 10) {
			while (frameLimit >= 3) {
				orders.add(new ShootOrder(theta));
				int y = t % 2;
				if (y == 0) {
					theta -= 1*handling;
				}
				else {
					theta += 1*handling;
				}
				t += 1;
				frameLimit -= 3;
			}
		}
		else {
			int l = 1;
			while (frameLimit >= 3) {
				orders.add(new ShootOrder(theta));
				frameLimit -= 3;
				int y = t % 2;
				if (y == 0) {
					theta -= l*handling;
				}
				else {
					theta += l*handling;
				}
				t += 1;
				l += 1;
			}
		}
		return orders;
	}
	// Any object added to map needs to be added
	public void addObject(Obstacle o) {
		this.objects.add(o);
	}
	public void remObject(Obstacle o) {
		this.objects.remove(o);
	}
	// Turns the tank toward enemy tank
	public TurnOrder turn(SimpleTank s, int i) {
		SimpleTank s2 = this.ownedTanks.get(0);
		double angle = this.diffAngle(s2, s);
		int dir = -1;
		if (angle == 0) {
			return new TurnOrder(0, dir);
		}
		else if (angle < 0) {
			dir = 1;
			angle = -angle;
		}
		double handling = this.ownedTanks.get(0).getHandling();
		double frames = angle/handling;
		return new TurnOrder((int)frames + i, dir);
	}
	// Moves the tank toward the enemy
	public MoveOrder move(SimpleTank s) {
		double dist = this.distance(s) - 100;
		int dir = 1;
		if (dist == 0) {
			return new MoveOrder(0, dir);
		}
		else if (dist < 0) {
			dir = -1;
			dist = - dist;
		}
		double speed = this.ownedTanks.get(0).getSpeed();
		double frames = dist/speed;
		return new MoveOrder((int)frames, dir);
	}
	// Returns the angle between both tanks
	public double diffAngle(Sprite s1, Sprite s2) {
		Vector3D p = s1.getPosition();
		Vector3D e = s2.getPosition();
		double deltaX = e.getX() - p.getX();
		double deltaY = e.getY() - p.getY();
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
		if (angle < 0) {
			angle += 360;
		}
		System.out.println(angle);
		Direction temp = s1.getDirection();
		double diff = temp.getTheta() - angle;
		System.out.println(temp.getTheta());
		System.out.println(diff);
		return diff;
	}
	// Finds the distance from the enemy
	public double distance(SimpleTank s) {
		SimpleTank player = this.ownedTanks.get(0);
		Vector3D p = player.getPosition();
		Vector3D e = s.getPosition();
		double deltaX = e.getX() - p.getX();
		double deltaY = e.getY() - p.getY();
		double temp = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);
		return Math.sqrt(temp);
	}
	public Sprite checkCollision() {
		SimpleTank temp =this.ownedTanks.get(0);
		Vector3D origP = temp.getPosition();
		System.out.println(objects.size());
		int f = this.moveFrames;
		while (f >= 0) {
			Vector3D newP = new Vector3D(origP, new Vector3D ( temp.getSpeed(), new Direction(this.theta)));
			int u = 0;
			for (Sprite s : objects) {
				System.out.println("obj = " + u);
				if (newP.distance(s.getPosition()) < temp.hitboxRadius + s.hitboxRadius) {
					System.out.println("newP");
					return s;
				}
				u += 1;
			}
			f -= 1;
			
		}
		return null;
	}
	public Vector3D newPos(Sprite o, SimpleTank s) {
		double diff = this.diffAngle(new Obstacle (o.sprites, o.getPosition(), new Direction(0), o.hitboxRadius), s);
		double diff2 = this.diffAngle(new Obstacle (o.sprites, o.getPosition(), new Direction(0), o.hitboxRadius), this.ownedTanks.get(0));
		double d = 90;
		if (diff2 < 0) {
			d = -d;
		}
		return new Vector3D(o.getPosition(), new Vector3D (o.hitboxRadius + 25, new Direction(diff + d)));
	}
}
	
	
	