package GameOfLife;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class LifeGame extends JFrame implements MouseMotionListener{
	private final World world;
	//JButton button = new JButton ("button");
	static JMenu location=new JMenu();
	public LifeGame(int rows,int columns)
	{
		world=new World(rows, columns);
		world.setBackground(Color.LIGHT_GRAY);
		new Thread(world).start();
		add(world);
	}
	public static void main(String[]args)
	{
		LifeGame frame=new LifeGame(90, 90);
		
		frame.addMouseMotionListener(frame);
		JMenuBar menu=new JMenuBar();
		frame.setJMenuBar(menu);
		
		
		JMenu options =new JMenu("设置");
		menu.add(options);
		JMenu changeSpeed=new JMenu("设置速度");
		menu.add(changeSpeed);
		JMenu other = new JMenu("其他");
		menu.add(other);
		
		JMenuItem start=options.add("开始");
		start.addActionListener(frame.new StartActionListener());
		JMenuItem random=options.add("随机排列");
		random.addActionListener(frame.new RandomActionListener());

		JMenuItem stop=options.add("停止");
		stop.addActionListener(frame.new StopActionListener());
		JMenuItem pause=options.add("暂停");
		pause.addActionListener(frame.new PauseActionListener());
		JMenuItem doityourself=options.add("添加");
		doityourself.addActionListener(frame.new DIYActionListener());
		JMenuItem clean=options.add("消除");
		clean.addActionListener(frame.new CleanActionListener());
		JMenuItem cleanAll=options.add("清空");
		cleanAll.addActionListener(frame.new CleanAllActionListener());
		JMenuItem slow=changeSpeed.add("慢速");
		slow.addActionListener(frame.new SlowActionListener());
		JMenuItem fast=changeSpeed.add("快速");
		fast.addActionListener(frame.new FastActionListener());
		JMenuItem hyper=changeSpeed.add("极速");
		hyper.addActionListener(frame.new HyperActionListener());
		
		JMenuItem help=other.add("帮助");
		help.addActionListener(frame.new HelpActionListener());
		
		JMenuItem about=other.add("关于");
		about.addActionListener(frame.new AboutActionListener());
		//about.addActionListener(frame.new AboutActionListener());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(815,820);
		//frame.setSize(1207,859);
		//JButton button = new JButton ("button");
		//frame.button.setBounds(108,100,100,100);
		//frame.add(frame.button,BorderLayout.EAST);
		frame.setTitle("生命游戏");
		int windowWidth = frame.getWidth(); //获得窗口宽
		int windowHeight = frame.getHeight(); //获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
		Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
		int screenWidth = screenSize.width; //获取屏幕的宽
		int screenHeight = screenSize.height; //获取屏幕的高
		frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
		frame.setVisible(true);
		frame.setResizable(false);
	}

	class RandomActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			world.diy=false;
			world.clean=false;
			world.setBackground(Color.LIGHT_GRAY);
			//world.setStop();
			world.setRandom();
		}
	}
	class StartActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//world.begintime=System.currentTimeMillis();
			world.setBackground(Color.WHITE);
			world.diy=false;
			world.clean=false;
			world.setShape();
		}
	}
	class StopActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//world.time=0;
			world.setBackground(Color.LIGHT_GRAY);
			world.diy=false;
			world.clean=false;
			world.setStop();
		}
	}
	class PauseActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			world.setBackground(Color.LIGHT_GRAY);
			world.diy=false;
			world.clean=false;
			world.setPause();
		}
	}
	class SlowActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			world.changeSpeedSlow();
		}
	}
	class FastActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			world.changeSpeedFast();
		}
	}
	class HyperActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			world.changeSpeedHyper();
		}
	}
	class HelpActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			JOptionPane.showMessageDialog(null, "这是生命游戏!!!\n生命游戏是英国数学家约翰·何顿·康威在1970年发明的细胞自动机\n"+
		"1．如果一个细胞周围有3个细胞为生，则该细胞为生;\n"+"2． 如果一个细胞周围有2个细胞为生，则该细胞的生死状态保持不变;\n"+"3． 在其它情况下，该细胞为死。");
		}
	}
	class AboutActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			JOptionPane.showMessageDialog(null, "游戏作者：abc");
		}
	}
	class CleanActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			world.setPause();
			world.clean=true;
			world.diy=false;
			world.setBackground(Color.orange);
		}
	}
	class CleanAllActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			world.setPause();
			for(int i=0;i<90;i++)
				for(int j=0;j<90;j++)
					World.pauseshape[i][j]=0;
			world.clean=true;
			world.diy=false;
			world.setBackground(Color.LIGHT_GRAY);
		}
	}
	class DIYActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			world.setPause();
			world.diy=true;
			world.clean=false;
			world.setBackground(Color.cyan);
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(world.diy){
		int x=e.getX();
		int y=e.getY();
		//button.setText("x:"+x+"y:"+y);
		World.pauseshape[(y-50)/10][x/10]=1;
		world.setDiy();
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(world.clean){
		int x=e.getX();
		int y=e.getY();
		//button.setText("x:"+x+"y:"+y);
		World.pauseshape[(y-50)/10][x/10]=0;
		world.setDiy();
		}
	}
}

