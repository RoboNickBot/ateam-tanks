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
 * A general order
 * 
 * Every order lasts a certain number of frames, and simply defines how the tank
 * will behave for that many frames of the game (frames are the basic unit of
 * game progression -- they represent one pass of the main game loop
 */
public abstract class Order {
	protected int frames;

	/**
	 * @param frames
	 *            have to be greater than 0
	 */
	public Order(int frames) {
		assert (frames >= 0) : "frames less than 0.";
		this.frames = frames;
	}

	public void exec(SimpleTank tank) {
		this.execSpecific(tank);
		frames--;
	}

	protected abstract void execSpecific(SimpleTank tank);

	public int getFrames() {
		return frames;
	}

	public abstract Order clone(); 
				
		
	

}
