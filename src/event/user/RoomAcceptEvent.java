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

package event.user;

import network.*;
import game.*;
import gameinterface.*;

public class RoomAcceptEvent implements event.Event<User>
{

    private Room room;

    public RoomAcceptEvent(Room r)
    {
        this.room = r;
    }

    public void handle(User user)
    {
        user.setRoom(this.room);
        user.toClient(new event.client.ChatEvent("Room", "private", "Accepted to " + room.getRoomName()));
    }

}
