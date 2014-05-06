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

package test;

import game.*;
import network.*;
import event.*;
import gameinterface.*;

import java.io.Console;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.BorderLayout;

public class RealWindowTest extends Thread
{

    public static void main(String args[])
    {
        System.out.println("----- Client Start -----");
        Console in = System.console();
        // get a name
        String username = in.readLine("Enter your name: ");
        // get server hostname
        String hostname = in.readLine("Server hostname: ");
        // c or j
        String mode = in.readLine("[C]reate or [J]oin?: ");
        // get room name
        String roomname = in.readLine("Name of game-room: ");
        // set up the demopanel and its enclosing frame
        DemoPanel ui=new DemoPanel();
        JFrame frame=new JFrame("ateam-tanks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(new BorderLayout());
        frame.add(ui,BorderLayout.CENTER);
        frame.setVisible(true);
        // init and start game client
        GameClient c1 = new GameClient(username, 8887);
        // create RealWindow
        RealWindow win = new RealWindow(c1, ui);
        // start server communications
        try {
            sleep(1000);
            System.out.println("Connecting to server..");
            c1.push(new event.client.JoinServerEvent(hostname));
            sleep(1000);
            if(mode.equals("C") || mode.equals("c"))
            {
                c1.push(new event.client.FwdUserEvent(new event.user.CreateRoomEvent(roomname, mkList())));
                in.readLine("Press Enter to start the game");
                c1.push(new event.client.FwdUserEvent(new event.user.StartGameEvent()));
            }
            else if(mode.equals("J") || mode.equals("j"))
            {
                c1.push(new event.client.FwdUserEvent(new event.user.JoinRoomEvent(roomname)));
            }
        } catch (InterruptedException e) {}
    }

    public static SpriteList mkList()
    {
        SpriteList init = new SpriteList();
        // player 1 sprites
        init.add(new SimpleTank(new Vector3D(-150, 20, 10), new Direction(0, 0), 1, 1));
        init.add(new SimpleTank(new Vector3D(-150, -20, 10), new Direction(0, 0), 1, 2));
        // player 2 sprites
        init.add(new SimpleTank(new Vector3D(150, 20, 10), new Direction(0, 0), 2, 1));
        init.add(new SimpleTank(new Vector3D(150, -20, 10), new Direction(0, 0), 2, 2));
        // player 3 sprites
        init.add(new SimpleTank(new Vector3D(20, -150, 10), new Direction(0, 0), 3, 1));
        init.add(new SimpleTank(new Vector3D(-20, -150, 10), new Direction(0, 0), 3, 2));
        // player 4 sprites
        init.add(new SimpleTank(new Vector3D(20, 150, 10), new Direction(0, 0), 4, 1));
        init.add(new SimpleTank(new Vector3D(-20, 150, 10), new Direction(0, 0), 4, 2));
        // center obstacle
        init.add(new Obstacle(new Vector3D(0, 0, 0), new Direction(0, 0), 27));
        // outer ring 1
        init.add(new Obstacle(new Vector3D(120, 120, 0), new Direction(0, 0), 17));
        init.add(new Obstacle(new Vector3D(120, -120, 0), new Direction(0, 0), 17));
        init.add(new Obstacle(new Vector3D(-120, 120, 0), new Direction(0, 0), 17));
        init.add(new Obstacle(new Vector3D(-120, -120, 0), new Direction(0, 0), 17));
        // outer ring 2
        init.add(new Obstacle(new Vector3D(-88, 40, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(-88, -40, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(88, 40, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(88, -40, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(40, 88, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(-40, 88, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(40, -88, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(-40, -88, 0), new Direction(0, 0), 15));
        // settle
        init.settle();
        return init;
    }

}
