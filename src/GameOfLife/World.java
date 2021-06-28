package GameOfLife;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.*;

public class World extends JPanel implements Runnable{
	private final int rows;
	private final int columns;
	JLabel  record;
	boolean diy=false;
	boolean clean=false;
	private int speed=8;
	private int lnum=0;
	private static int shape[][]=new int [90][90];
	private static int zero[][]=new int [90][90];
	static  int pauseshape[][]=new int [90][90];
	private final CellStatus[][] generation1;
	private final CellStatus[][] generation2;
	private CellStatus[][] currentGeneration;
	private CellStatus[][] nextGeneration;
	private volatile boolean isChanging = false;
	public World(int rows, int columns)
	{
		
		this.rows=rows;
		this.columns=columns;
		record = new JLabel();
		add(record);
		generation1=new CellStatus[rows][columns];
		//addMouseMotionListener(this);
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				generation1[i][j]=CellStatus.Dead;
			}
		}
		generation2=new CellStatus[rows][columns];
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				generation2[i][j]=CellStatus.Dead;
			}
		}
		currentGeneration=generation1;
		nextGeneration=generation2;
	}
	public void transfrom(CellStatus[][] generation, int pauseshape[][])
	{
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				if(generation[i][j]==CellStatus.Active)
				{
					pauseshape[i][j]=1;
				}
				else if(generation[i][j]==CellStatus.Dead)
				{
					pauseshape[i][j]=0;
				}
			}
		}
	}
	public void run()
	{
//		begintime=System.currentTimeMillis();
		while(true)
		{
			synchronized(this)
			{
				while(isChanging)
				{
					
					try
					{
						this.wait();
					}catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
//				repaint();
				sleep(speed);
				for(int i=0;i<rows;i++)
				{
					for(int j=0;j<columns;j++)
					{
						evolve(i,j);
					}
				}
				CellStatus[][]temp=null;
				temp=currentGeneration;
				currentGeneration=nextGeneration;
				nextGeneration=temp;
				for(int i=0;i<rows;i++)
				{
					for(int j=0;j<columns;j++)
					{
						nextGeneration[i][j]=CellStatus.Dead;	
					}
				}
				//transfrom(currentGeneration,shape);
				transfrom(currentGeneration,pauseshape);
				repaint();
				//endtime=System.currentTimeMillis();
				updateNumber();
			}
		}
	}
	
	public void setcurrentGeneration(CellStatus[][] a)
	{
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
			{
				currentGeneration[i][j]=a[i][j];
			}
	}
	
	public void updateNumber()
	{
		String s = " 存活数量:" + lnum ;
		record.setText(s);
	}
	
	public int getNum()
	{
		return lnum;
	}
	
	public void changeSpeedSlow()
	{
		speed=8;
	}
	public void changeSpeedFast()
	{
		speed=3;
	}
	public void changeSpeedHyper()
	{
		speed=1;
	}
	
	
	public void AddNum(CellStatus[][] a)
	{
		lnum=0;
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				if(a[i][j]==CellStatus.Active)
					lnum++;
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		lnum=0;
		super.paintComponent(g);
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				if(currentGeneration[i][j]==CellStatus.Active)
				{
					g.fillRect(j*10, i*10, 10, 10);
					lnum++;
				}
				else
				{
					g.drawRect(j*10, i*10, 10, 10);
				}
			}
		}
	}

	public void setShape()
	{
		setShape(shape);
	}
	public void setRandom()
	{
		Random a=new Random();
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				shape[i][j]=Math.abs(a.nextInt(2));
				pauseshape[i][j]=shape[i][j];
			}
		}
		setShapetemp(shape);
	}
	public int [][]getpause()
	{
		return pauseshape;
	}
	public void setZero()
	{
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				zero[i][j]=0;
			}
		}
	}
	
	public int [][]getZero()
	{
		return zero;
	}
	
	public void setStop()
	{
		setZero();		
		shape=zero;
		setShape(shape);
		pauseshape=shape;
	}
	
	public void setPause()
	{
		shape=pauseshape;
		setShapetemp(pauseshape);
	}
	
	public void setDiy()
	{
		shape=pauseshape;
		setShapetemp(shape);
	}
	private void setShapetemp(int [][]shape)
	{
		isChanging=true;
		int arrowsRows=shape.length;
		int arrowsColumns=shape[0].length;
		int minimumRows=(arrowsRows<rows)?arrowsRows:rows;
		int minimunColumns=(arrowsColumns<columns)?arrowsColumns:columns;
		synchronized(this)
		{
			for(int i=0;i<rows;i++)
			{
				for(int j=0;j<columns;j++)
				{
					currentGeneration[i][j]=CellStatus.Dead;
				}
			}
			for(int i=0;i<minimumRows;i++)
			{
				for(int j=0;j<minimunColumns;j++)
				{
					if(shape[i][j]==1)
					{
						currentGeneration[i][j]=CellStatus.Active;
					}
				}
			}
			//transfrom(currentGeneration,pauseshape);
			repaint();
			updateNumber();
//			isChanging=true;
//			this.notifyAll();
		}
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	
	void setShape(int [][]shape)
	{
		isChanging=true;
		int arrowsRows=shape.length;
		int arrowsColumns=shape[0].length;
		int minimumRows=(arrowsRows<rows)?arrowsRows:rows;
		int minimunColumns=(arrowsColumns<columns)?arrowsColumns:columns;
		synchronized(this)
		{
			for(int i=0;i<rows;i++)
			{
				for(int j=0;j<columns;j++)
				{
					currentGeneration[i][j]=CellStatus.Dead;
				}
			}
			for(int i=0;i<minimumRows;i++)
			{
				for(int j=0;j<minimunColumns;j++)
				{
					if(shape[i][j]==1)
					{
						currentGeneration[i][j]=CellStatus.Active;
					}
				}
			}
			isChanging=false;
			this.notifyAll();
		}
		
	}
	
	public void allevoloe()
	{
		for(int i=0;i<5;i++)
			for(int j=0;j<5;j++)
					evolve(i,j);
	}
	
	public CellStatus[][] getnextGeneration()
	{
		return nextGeneration;
	}
	
	public CellStatus[][] getCurrentGeneration()
	{
		return currentGeneration;
	}
	
	public void evolve(int x,int y)
	{
		int activeSurroundingCell=0;
		if(isVaildCell(x-1,y-1)&&(currentGeneration[x-1][y-1]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x,y-1)&&(currentGeneration[x][y-1]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x+1,y-1)&&(currentGeneration[x+1][y-1]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x+1,y)&&(currentGeneration[x+1][y]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x+1,y+1)&&(currentGeneration[x+1][y+1]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x,y+1)&&(currentGeneration[x][y+1]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x-1,y+1)&&(currentGeneration[x-1][y+1]==CellStatus.Active))
			activeSurroundingCell++;
		if(isVaildCell(x-1,y)&&(currentGeneration[x-1][y]==CellStatus.Active))
			activeSurroundingCell++;
		//
		if(activeSurroundingCell==3)
		{
			nextGeneration[x][y]=CellStatus.Active;
		}
		else if(activeSurroundingCell==2)
		{
			nextGeneration[x][y]=currentGeneration[x][y];
		}
		else
		{
			nextGeneration[x][y]=CellStatus.Dead;
		}
	}
	boolean isVaildCell(int x,int y)
	{
		if((x>=0)&&(x<rows)&&(y>=0)&&(y<columns))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private void sleep(int x)
	{
		try {
			Thread.sleep(80*x);
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
	}
	static enum CellStatus
	{
		Active,
		Dead;
	}

	
}

