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

import java.util.concurrent.*;
import java.io.*;
import java.net.*;

public class User extends Thread implements DropBox<UserEvent>
{

    private String name;
    private BlockingQueue<UserEvent> events;
    private DropBox<ClientEvent> client;
    private DropBox<ServerEvent> server;
    private DropBox<GameEvent> room;

    public User(GameServer s, Socket c)
    {
        this.name = "un-initialized";
        this.server = s;
        this.client = new NetCore<UserEvent,ClientEvent>(c, this);
        this.events = new LinkedBlockingDeque<UserEvent>();
        this.client.push(new RequestInitInfoClientEvent());
        this.room = s.getLobby();
        this.start();
    }

    public void push(UserEvent ev)
    {
        try {
            this.events.put(ev);
        } catch (InterruptedException e) {}
    }

    public void run()
    {
        while(true)
        {
            try {
                this.events.take().handle(this);
            } catch (InterruptedException e) {}
        }
    }

    public void toClient(ClientEvent ev)
    {
        this.client.push(ev);
    }

    public void toRoom(GameEvent ev)
    {
        this.room.push(ev);
    }

    public void toServer(ServerEvent ev)
    {
        this.server.push(ev);
    }

    public void setPlayerName(String name)
    {
        this.name = name;
    }

    public String getPlayerName()
    {
        return this.name;
    }

}