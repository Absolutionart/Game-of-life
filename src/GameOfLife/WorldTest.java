package GameOfLife;

import static org.junit.Assert.*;

import java.awt.Graphics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GameOfLife.World.CellStatus;

public class WorldTest {

	private static World world =new World(5, 5);
	private CellStatus[][] currentGeneration=new CellStatus[5][5];
	private CellStatus[][] nextGeneration=new CellStatus[5][5];
	@Before
	public void setUp() throws Exception {
		for(int i=0;i<5;i++)
			for(int j=0;j<5;j++)
				currentGeneration[i][j]=CellStatus.Dead;
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0;i<5;i++)
			for(int j=0;j<5;j++)
				nextGeneration[i][j]=CellStatus.Dead;
	}

	@Test
	public void testTransfrom() {
		CellStatus[][] generation=new CellStatus[5][5];
		int[][] pauseshape=new int [5][5];
		generation[1][1]= CellStatus.Active;
		generation[1][2]= CellStatus.Dead;
		generation[3][2]= CellStatus.Active;
		generation[4][3]=CellStatus.Dead;
		world.transfrom(generation,pauseshape);
		if(pauseshape[1][1]==0||pauseshape[1][2]==1||
				pauseshape[3][2]==0||pauseshape[4][3]==1)
			fail("failure");
	}


	@Test
	public void testUpdateNumber() {
		for(int j=1;j<5;j++)
			currentGeneration[1][j]=CellStatus.Active;
		world.setcurrentGeneration(currentGeneration);
		world.AddNum(currentGeneration);
		world.updateNumber();
		assertEquals(world.getNum(),4);
		for(int j=1;j<5;j++)
			currentGeneration[4][j]=CellStatus.Active;
		world.setcurrentGeneration(currentGeneration);
		world.AddNum(currentGeneration);
		world.updateNumber();
		assertEquals(world.getNum(),8);
	}

	@Test
	public void testChangeSpeedSlow() {
		world.changeSpeedSlow();//慢速测试
		assertEquals(world.getSpeed(),8);
	}

	@Test
	public void testChangeSpeedFast() {
		world.changeSpeedFast();//快速
		assertEquals(world.getSpeed(),3);
	}

	@Test
	public void testChangeSpeedHyper() {
		world.changeSpeedHyper();//极速
		assertEquals(world.getSpeed(),1);
	}


	@Test
	public void testSetShape() {
		int[][] shape=new int [5][5];
		for(int j=0;j<5;j++)
			shape[1][j]=1;
		world.setShape(shape);
		currentGeneration=world.getCurrentGeneration();
		for(int i=0;i<5;i++)
			for(int j=0;j<5;j++)
			{
				if(i!=1&&currentGeneration[i][j]==CellStatus.Active)
					fail("failure");
			}
	}

	@Test
	public void testSetRandom() {
		int[][] shape=new int [5][5];
		int x=0,y=0;
		for(int k=0;k<5;k++)
		{
			world.setRandom();
			shape=world.getpause();
			for(int i=0;i<5;i++)
				for(int j=0;j<5;j++)
				{
					if(shape[i][j]==1)x++;
					else y++;
				}
			if(Math.abs(x-y)>=25)
				fail("failure");
		}
	}

	@Test
	public void testSetZero() {	
	int[][] shape=new int [5][5];
		world.setZero();
		shape=world.getZero();
		for(int i=0;i<5;i++)
			for(int j=0;j<5;j++)
			{
				if(shape[i][j]==1)
					fail("failure");
			}
   }

	@Test
	public void testisVaildCell()
	{
		if(!world.isVaildCell(0,0)||world.isVaildCell(1,5))
			fail("failure");
	}
	
	@Test
	public void testEvolve1() {
		/**
		 **/
		currentGeneration[0][0]=CellStatus.Active;
		currentGeneration[0][1]=CellStatus.Active;
		currentGeneration[1][1]=CellStatus.Active;
		currentGeneration[1][0]=CellStatus.Active;
		world.setcurrentGeneration(currentGeneration);
		world.allevoloe();
		nextGeneration=world.getnextGeneration();
		if(nextGeneration[0][0]==CellStatus.Dead ||nextGeneration[0][1]==CellStatus.Dead ||
				nextGeneration[1][1]==CellStatus.Dead ||nextGeneration[1][0]==CellStatus.Dead )
		{
			fail("failure");
		}
		
	}
	@Test
	public void testEvolve2() {
		currentGeneration[1][1]=CellStatus.Active;
		currentGeneration[1][0]=CellStatus.Active;
		currentGeneration[1][2]=CellStatus.Active;
		world.setcurrentGeneration(currentGeneration);
		world.allevoloe();
		nextGeneration=world.getnextGeneration();
		for(int i=0;i<5;i++)
			for(int j=0;j<3;j++)
				if(nextGeneration[i][j]==CellStatus.Active&&j!=1)
				{
					fail("failure");
				}
		
	}
	@Test
	public void testEvolve3() {
		currentGeneration[0][2]=CellStatus.Active;
		currentGeneration[0][1]=CellStatus.Active;
		currentGeneration[2][0]=CellStatus.Active;
		currentGeneration[1][0]=CellStatus.Active;
		currentGeneration[2][3]=CellStatus.Active;
		currentGeneration[1][3]=CellStatus.Active;
		currentGeneration[3][1]=CellStatus.Active;
		currentGeneration[3][2]=CellStatus.Active;
		world.setcurrentGeneration(currentGeneration);
		world.allevoloe();
		nextGeneration=world.getnextGeneration();
		for(int i=0;i<5;i++)
			for(int j=0;j<5;j++)
			{
				if(currentGeneration[i][j]!=nextGeneration[i][j])
					fail("failure");
			}
		
	}

}
