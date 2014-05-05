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

public class ServerStandaloneTest extends Thread
{

    public static void main(String[] args)
    {
        System.out.println("----- Server Start -----");
        GameServer server = new GameServer(3, 8887);
        System.console().readLine("Press enter to kill server");
        server.push(new event.server.ShutdownEvent("killin you"));
        System.out.println("----- Tests complete -----");
    }

}
