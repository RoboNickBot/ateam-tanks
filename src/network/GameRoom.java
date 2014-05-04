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

import java.util.concurrent.*;
import java.util.*;
import event.*;

public class GameRoom extends Room
{

    private SpriteList sprites;
    private boolean isGameRunning;
    private int maxPlayers;
    private String creator;
    private Map<String,User> users;
    private Map<String,Player> players;

    public GameRoom(GameServer s, String name, User c, SpriteList initList)
    {
        super(s, name);
        this.users = new HashMap<String,User>();
        this.players = new HashMap<String,Player>();
        this.sprites = initList.clone();
        this.creator = c.getPlayerName();
        this.isGameRunning = false;
        this.maxPlayers = this.sprites.playerCount();
        if(this.maxPlayers < 1)
        {
            c.push(new event.user.FwdClientEvent(new event.client.ChatEvent("Game", "private", "You cannot make a game that takes no players")));
            this.killingYou();
        }
        else
        {
            addUser(c);
        }
    }

    public void addUser(User user)
    {
        if(this.players.size() < this.maxPlayers && this.isGameRunning == false)
        {
            this.users.put(user.getPlayerName(), user);
            user.push(new event.user.RoomAcceptEvent(this));
        }
        else
        {
            user.push(new event.user.FwdClientEvent(new event.client.ChatEvent("room", "private", "You can't join that game")));
        }
    }

    public void removeUser(User user)
    {
        this.users.remove(user.getPlayerName());
        if(user.getPlayerName().equals(this.creator))
        {
            toUsers(new event.user.PartRoomEvent("Creator left, game closed"));
            this.killingYou();
        }
        else if(this.isGameRunning)
        {
            //replace the human player with a dummy or AI
            int id = this.players.get(user.getPlayerName()).ID();
            this.players.remove(user.getPlayerName());
            this.players.put(user.getPlayerName(), new Player(id, user.getPlayerName()));
        }
    }

    public void toUsers(Event<User> ev)
    {
        for(String un : this.users.keySet())
        {
            this.users.get(un).push(ev);
        }
    }

    public void announce(String message)
    {
        this.toUsers(new event.user.FwdClientEvent(new event.client.ChatEvent("Game", "room", message)));
    }

    public User getUser(String username)
    {
        return users.get(username);
    }

    public boolean isCreator(String name)
    {
        return name.equals(this.creator);
    }

    public boolean isGameRunning()
    {
        return this.isGameRunning;
    }

    public void startGame()
    {
        int playerCount = 0;
        for (String uname : this.users.keySet())
        {
            playerCount ++;
            this.players.put(uname, new RemotePlayer(playerCount, uname));
        }
        while (playerCount < this.maxPlayers)
        {
            playerCount ++;
            String botName = "bot " + playerCount;
            this.players.put(botName, new Player(playerCount, botName));
        }
        for (String uname : this.users.keySet())
        {
            this.users.get(uname).push(new event.user.FwdClientEvent(new event.client.GameStartEvent(this.sprites.clone(), this.players.get(uname).ID())));
        }
        this.isGameRunning = true;
    }

    public void stepGame()
    {

        for (String uname : this.players.keySet())
        {
            this.users.get(uname).push(new event.user.FwdClientEvent(new event.client.SpritesEvent(SpriteType.PLAY, this.sprites.clone(), this.players.get(uname).ID())));
        }
        this.sprites.runTurn();
        clearDeadPlayers();
        if (this.players.size() > 1)
        {
            for (String uname : this.players.keySet())
            {
                this.users.get(uname).push(new event.user.FwdClientEvent(new event.client.SpritesEvent(SpriteType.ORDER, this.sprites.clone(), this.players.get(uname).ID())));
            }
        }
        else if (this.players.size() == 1)
        {
            for (String uname : this.users.keySet())
            {
                String winner = this.players.keySet().iterator().next();
                this.users.get(uname).push(new event.user.FwdClientEvent(new event.client.GameOverEvent(winner)));
                this.isGameRunning = false;
            }
        }
        else
        {
            for (String uname : this.users.keySet())
            {
                this.users.get(uname).push(new event.user.FwdClientEvent(new event.client.GameOverEvent("Nobody")));
                this.isGameRunning = false;
            }
        }
    }

    public boolean depositOrders(String playerName, ArrayList<OrderQueue> os)
    {
        this.players.get(playerName).setOrders(os);
        boolean ready = true;
        for (String pname : this.players.keySet())
        {
            if (!(ready && this.players.get(pname).areOrdersSet()))
            {
                ready = false;
            }
        }
        return ready;
    }

    private void applyOrders()
    {
        for (Sprite sprite : this.sprites.getSprites())
        {
            // all are blanked first in case they dont get a new queue
            sprite.giveOrders(new OrderQueue());
        }
        for (String pname : this.players.keySet())
        {
            for (OrderQueue q : this.players.get(pname).getOrders())
            {
                for (Sprite sprite : this.sprites.getSprites())
                {
                    if (sprite.uid().equals(q.uid()))
                    {
                        sprite.giveOrders(q);
                    }
                }
            }
            this.players.get(pname).clearOrders();
        }
    }

    private void clearDeadPlayers()
    {
        for (String pname : this.players.keySet())
        {
            boolean dead = true;
            for (Sprite s : this.sprites.getSprites())
            {
                if (s.getPlayerID() == this.players.get(pname).ID())
                {
                    dead = false;
                }
            }
            if (dead)
            {
                System.out.println(pname + " has been destroyed.");
                this.users.get(pname).push(new event.user.FwdClientEvent(new event.client.ChatEvent(this.name, "room", "You are dead")));
                this.players.remove(pname);
            }
        }
    }
}
