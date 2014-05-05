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

package event.client;

import network.*;
import game.*;
import gameinterface.*;

public class GameStartEvent implements event.Event<GameClient>
{

    private SpriteList initList;
    private int ID;

    public GameStartEvent(SpriteList ss, int ID)
    {
        this.initList = ss;
        this.ID = ID;
    }

    public void handle(GameClient client)
    {
        System.out.println(client.getPlayerName() + ": start sprites received");
        client.toGW(new event.gw.GetOrdersEvent(this.initList, this.ID, client.getPlayerName()));
    }

}
