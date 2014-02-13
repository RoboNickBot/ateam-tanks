/**
 * A new core test for the Game/Player structure,
 * as well as the dummy ui. This test is acting in
 * place of the main gui menu that will eventually
 * be written to launch the games.
 */

import java.util.ArrayList;

public class GameTest
{
    public static void main ( String args[] )
    {
        System.out.println ( "/// GameTest Starting ///\n" );

        int framesPerTurn = 10;
        int turnLimit = 3;
        int mapsize = 200;

        DummyUI ui = new DummyUI ();

        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        ArrayList<SimpleTank> p1tanks = new ArrayList<SimpleTank>();
        ArrayList<SimpleTank> p2tanks = new ArrayList<SimpleTank>();
        SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( 20, 20, 0 ), new Direction ( 60 ), 5, 5, Color.red );
        SimpleTank t2 = new SimpleTank ( sprites, p2tanks, new Vector3D ( 50, 20, 0 ), new Direction ( 90 ), 10, 5, Color.blue );
        Obstacle o1 = new Obstacle ( sprites, new Vector3D ( 100, 40, 0 ), new Direction ( 0 ), new HitBox ( 5, 5, 5 ) );

        sprites.add ( t1 );
        sprites.add ( t2 );
        sprites.add ( o1 );
        p1tanks.add ( t1 );
        p2tanks.add ( t2 );

        ArrayList<Player> players = new ArrayList<Player>();
        players.add ( new HumanPlayer ( ui, "player1", p1tanks, Color.red ) );
        players.add ( new HumanPlayer ( ui, "player2", p2tanks, Color.blue ) );

        Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );

        System.out.println ( "//  Game running now  //\n" );

        int result = game.run ();

        System.out.println ( "/// Game finished with code " + result + " ///\n" );
    }
}
