import java.awt.Graphics;

public class DrawingPanel {

	public DrawingPanel(int i, int j) {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		DrawingPanel panel = new DrawingPanel(200,100);
		Graphics g = panel.getGraphics;
		g.draw3DRect(10, 20, 10, 20, 30);
	}

}
