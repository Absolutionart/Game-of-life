package GameOfLife;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import javax.swing.event.MenuDragMouseEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LifeGameTest {
	
	private static LifeGame lifegame=new LifeGame(90, 90);
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMain() {
		String[] args = null;
		lifegame.main(args);
	}

	@Test
	public void testMouseDragged() {
		MouseEvent e = null ;
		lifegame.mouseDragged(e);
	}

	@Test
	public void testMouseMoved() {
		MouseEvent e = null;
		lifegame.mouseDragged(e);
	}

}
