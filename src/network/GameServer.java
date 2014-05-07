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

import java.util.*;
import event.Event;

/* The GameServer is intended to handle connections and disconnections and
 * create and start games, all in the form of responses to "events"
 */
public class GameServer extends ConcreteDropBox<GameServer>
{
    
    private Map<String,User> users;
    private Map<String,Room> rooms;
    private int userCapacity;
    private CollectServer coll;

    public GameServer(int userCapacity, int port)
    {
        this.users = new HashMap<String,User>();
        this.userCapacity = userCapacity;
        this.rooms = new HashMap<String,Room>();
        this.rooms.put("Lobby", new Room(this, "Lobby"));
        this.coll = new CollectServer(this, port);
        this.start();
    }

    public DropBox<Room> getLobby()
    {
        return this.rooms.get("Lobby");
    }

    public Set<String> getUserNames()
    {
        return this.users.keySet();
    }

    public void shutdown()
    {
        this.shutdown("OP shutdown");
    }

    public void shutdown(String reason)
    {
        coll.killingYou();
        announce("Server is going down: " + reason);
        try {
            sleep(700);
        } catch (InterruptedException e) {}
        for (String uname : this.users.keySet())
        {
            this.users.get(uname).push(new event.user.PartEvent("Server shutdown"));
        }
        try {
            sleep(700);
        } catch (InterruptedException e) {}
        this.rooms.get("Lobby").killingYou();
        this.killingYou();
    }


    public void announce(String announcement)
    {
        for(String uname : this.users.keySet())
        {
            this.users.get(uname).push(new event.user.FwdClientEvent(new event.client.ChatEvent("Server", "public", announcement)));
        }
    }

    public void infoUpdate()
    {
        for(String uname : this.users.keySet())
        {
            this.users.get(uname).push(new event.user.FwdClientEvent(new event.client.ServerInfoEvent(serverInfo())));
        }
    }

    public String serverInfo()
    {
        String info = "== SERVER INFO ==\n\nGames:\n";
        if(this.rooms.size() == 1)
        {
            info = info + "  (none)\n";
        }
        else
        {
            for (String gname : this.rooms.keySet())
            {
                if(!gname.equals("Lobby"))
                {
                    if(this.rooms.get(gname).isGameRunning())
                    {
                        info = info + "  " + gname + " [Running]\n";
                    }
                    else if(this.rooms.get(gname).isGameFull())
                    {
                        info = info + "  " + gname + "  [Full]\n";
                    }
                    else
                    {
                        info = info + "  " + gname + "  [Open] " + this.rooms.get(gname).roomLeft() + "\n";
                    }
                }
            }
        }
        info = info + "\n";
        info = info + "Users:\n";
        if(this.users.size() < 1)
        {
            info = info + "  (none)\n";
        }
        else
        {
            for (String uname : this.users.keySet())
            {
                info = info + "  " + uname + "\n";
            }
        }
        return info;
    }

    public void removeUser(String name, String reason)
    {
        // if a user gets rejected before actually joining, they stiil
        // send the server a part event, so we must check if they are
        // really in users before we try to do anything
        if(this.users.get(name) != null)
        {
            this.users.get(name).push(new event.user.PartEvent(reason));
            this.users.remove(name);
            System.out.println("SERVER REMOVED " + name);
            infoUpdate();
        }
    }

    public Room getRoom(String name)
    {
        return this.rooms.get(name);
    }

    public void toUser(String name, Event<User> ev)
    {
        this.users.get(name).push(ev);
    }

    public boolean addRoom(User creator, String name, SpriteList initList)
    {
        if(this.rooms.containsKey(name))
        {
            creator.push(new event.user.FwdClientEvent(new event.client.ChatEvent("Server", "private", "A game with that name already exists")));
            infoUpdate();
            return false;
        }
        else
        {
            this.rooms.put(name, new GameRoom(this, name, creator, initList));
            infoUpdate();
            return true;
        }
    }

    public boolean removeRoom(String name)
    {
        if(this.rooms.containsKey(name))
        {
            this.rooms.remove(name);
            System.out.println("Game \"" + name + "\" closed");
        }
        System.out.println("remove room");
        infoUpdate();
        return true;
    }

    public boolean addUser(User u)
    {
        if(this.users.containsKey(u.getPlayerName()))
        {
            u.push(new event.user.PartEvent("Username already in use"));
            infoUpdate();
            return false;
        }
        else if(this.users.size() == this.userCapacity)
        {
            u.push(new event.user.PartEvent("Server is full"));
            infoUpdate();
            return false;
        }
        else
        {
            this.users.put(u.getPlayerName(), u);
            System.out.println(u.getPlayerName() + " has joined!!");
            u.push(new event.user.ServerAcceptEvent());
            infoUpdate();
            return true;
        }
    }

    public int getUserCapacity()
    {
        return this.userCapacity;
    }

}
