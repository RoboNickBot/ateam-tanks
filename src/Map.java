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

/*
 *
 *
 *
 *
 */
 
import java.io.*;
import java.util.ArrayList;


//public class Map extends Sprite{
	
	//return 0;
//}
	class FileRead{

public static void main(String args[])
{
	/**
	 * reading a file for input
	 */
	try{
// Open the file that is the first 
// command line parameter
	FileInputStream fstream = new FileInputStream("e:\\School\\Spring 2014\\Software Engineering\\ateam-tanks\\src\\test.txt");
// Get the object of DataInputStream
	DataInputStream in = new DataInputStream(fstream);
	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	String strLine;
//Read File Line By Line
	while ((strLine = br.readLine()) != null)   {
// Print the content on the console
	String[] ar = strLine.split(",");
	System.out.println (strLine);
}
//Close the input stream
	in.close();
}catch (Exception e){//Catch exception if any
	System.err.println("Error: " + e.getMessage());
}
}
	
	}
