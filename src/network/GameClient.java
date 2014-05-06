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

package network;

import game.*;
import gameinterface.*;

import event.Event;

public class GameClient extends ConcreteDropBox<GameClient>
{

    private String name;
    private DropBox<User> outBox;
    private DropBox<GWindow> gameWindow;
    private int port;
    private boolean connected;

    public GameClient(String name, int port)
    {
        this.connected = false;
        this.name = name;
        this.port = port;
        this.outBox = new FakeBox<User>();
        this.gameWindow = new FakeWindow(this);
        this.start();
    }

    public void setWin(DropBox<GWindow> win)
    {
        this.gameWindow.killingYou();
        this.gameWindow = win;
    }

    public void toUser(Event<User> ev)
    {
        this.outBox.push(ev);
    }

    public void toGW(Event<GWindow> ev)
    {
        this.gameWindow.push(ev);
    }

    public String getPlayerName()
    {
        return this.name;
    }

    public void shutdown()
    {
        System.out.println(this.getPlayerName() + " is shutting down.");
        this.toUser(new event.user.PartEvent("Client shutdown"));
        this.gameWindow.killingYou();
        this.killingYou();
        try {
            sleep(100);
        } catch (InterruptedException e) {}
        this.outBox.killingYou();
        //System.out.println(this.name + " Client has killed its netcore");
    }

    public void connect(String hostname)
    {
        this.connected = true;
        this.outBox = new NetWorker<GameClient,User>().connect(this, hostname, this.port);
    }

    public void disconnect(String reason)
    {
        if(this.connected)
        {
            this.connected = false;
            System.out.println(this.name + " leaving server: " + reason);
            this.toUser(new event.user.PartEvent(reason));
            this.outBox.killingYou();
            this.outBox = new NetWorker<GameClient,User>().disconnect();
        }
    }

}
