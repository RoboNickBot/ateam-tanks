import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class gamemenu extends JPanel implements ActionListener {

	final JButton instructions = new JButton("How to Play");
	//final JButton restart = new JButton("Restart");
	final JButton up = new JButton("UP");
	final JButton down = new JButton("DOWN");
	final JButton left = new JButton("LEFT");
	final JButton right = new JButton("RIGHT");
	final JButton newgame = new JButton("New Game");
	final JButton shoot = new JButton("Fire");
	final JButton wait = new JButton("Wait");
	final JButton turn = new JButton("Turn");
	
	private GameBoard game = null;

	public ButtonPanel(GameBoard g) {
		game = g;
		add(newgame);
		add(instructions);
		//add(restart);
		add(up);
		add(down);
		add(left);
		add(right);
		add(shoot);
		add(wait);
		add(turn);
		setLayout(new GridLayout(0, 6));
	}

	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == "instructions") {
			JOptionPane.showMessageDialog(null, "Good Luck", "How to Play", 3, null);
		} else if (ae.getSource() == "up") {

		} else if (ae.getSource() == "down") {
			game.updatePosition("down");
		} else if (ae.getSource() == "left") {

		} else if (ae.getSource() == "right") {

		}

	}
}