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

public class ChatEvent implements event.Event<GameClient>
{

    String sender;
    String status;
    String message;

    public ChatEvent(String s, String p, String m)
    {
        this.sender = s;
        this.status = p;
        this.message = m;
    }

    public void handle(GameClient client)
    {
        System.out.println(client.getPlayerName() + " received from " + sender + " " + status + ": " + message);
        client.toCW(new event.cw.ChatEvent(sender, status, message));
    }

}
