package org.rothmayer.AmbiForPC;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class AmbiForPC {

	private JFrame frmAmbiforpc;
	DatagramSocket clientSocket;
	private JTextField textFieldIPAddress;
	private JTextField textFieldPort;
	private JComboBox comboBoxDisplay;
	public static TestManager tManager;
	public Display dis;
	public PixelSender task;
	private JButton btnStart;
	public JTextField textFieldInSet;
	public static boolean test = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AmbiForPC window = new AmbiForPC();
					window.frmAmbiforpc.setVisible(true);
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SocketException 
	 */
	public AmbiForPC() throws SocketException {
		task = new PixelSender(this);
		clientSocket = new DatagramSocket();
		initialize();
		//test3();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		iniSystemTray();
		
		frmAmbiforpc = new JFrame();
		frmAmbiforpc.setIconImage(Toolkit.getDefaultToolkit().getImage(AmbiForPC.class.getResource("/images/icon.png")));
		frmAmbiforpc.setTitle("AmbiForPC");
		frmAmbiforpc.setBounds(100, 100, 610, 600);
		frmAmbiforpc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmAmbiforpc.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	try {
					task.stopTimer();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
            	try {
					task.blackScreen();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
            }

        });
		

		frmAmbiforpc.addWindowStateListener(new WindowStateListener() {
			   public void windowStateChanged(WindowEvent arg0) {
				      frame__windowStateChanged(arg0);
				   }
				});
		
		JLabel lblDisplay = new JLabel("Display:");
		
		comboBoxDisplay = new JComboBox();
		
		
		JLabel lblIpaddress = new JLabel("IP-Address:");
		
		textFieldIPAddress = new JTextField();
		textFieldIPAddress.setText("192.168.128.29");
		textFieldIPAddress.setColumns(1);
		
		JLabel lblPort = new JLabel("Port:");
		
		textFieldPort = new JTextField();
		textFieldPort.setText("40022");
		textFieldPort.setColumns(1);
		
		JLabel lblInset = new JLabel("Inset:");
		
		textFieldInSet = new JTextField();
		textFieldInSet.setEditable(true);
		textFieldInSet.setText("100");
		textFieldInSet.setColumns(10);
		
		JSeparator separator = new JSeparator();
		
		JLabel lblRotation = new JLabel("Rotation:");
		
		ButtonGroup rotationGruppe = new ButtonGroup();
		
		JRadioButton rdbtnClockwise = new JRadioButton("Clockwise");
		
		JRadioButton rdbtnCounterclockwise = new JRadioButton("Counterclockwise");
		
		rotationGruppe.add(rdbtnCounterclockwise);
		rotationGruppe.add(rdbtnClockwise);
		
		JLabel lblStartposition = new JLabel("Startposition:");
		
		ButtonGroup postionGruppe = new ButtonGroup();
		
		JRadioButton rdbtnTopLeft = new JRadioButton("Top Left");
		
		JRadioButton rdbtnTopRight = new JRadioButton("Top Right");
		
		JRadioButton rdbtnBottomLeft = new JRadioButton("Bottom Left");
		
		JRadioButton rdbtnBottomRight = new JRadioButton("Bottom Right");
		
		postionGruppe.add(rdbtnTopLeft);
		postionGruppe.add(rdbtnTopRight);
		postionGruppe.add(rdbtnBottomLeft);
		postionGruppe.add(rdbtnBottomRight);
		
		btnStart = new JButton("Start");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//long time = System.currentTimeMillis();
				//test3(dis.getData(50, 28, 16));
				//System.out.println("Fertig " + (System.currentTimeMillis() - time));
				if(btnStart.getText().equalsIgnoreCase("Start")){
					task.startTask();
					btnStart.setText("Stop");
					textFieldInSet.setEditable(false);
				}else{
					task.stopTimer();
					btnStart.setText("Start");
					textFieldInSet.setEditable(true);
				}
			}
		});
		
		JButton btnSystemtray = new JButton("Systemtray");
		
		JLabel lblConsole = new JLabel("Console:");
		
		JTextArea txtrConsoleOutput = new JTextArea();
		
		
		PrintStream con=new PrintStream(new TextAreaOutputStream(txtrConsoleOutput));
		//System.setOut(con);
		//System.setErr(con);
		
		readDisplays();
		
		JButton btnTest = new JButton("Test");
		btnTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(test){
					System.out.println("Set Test False");
					test = false;
					tManager.clear();
				}else{
					System.out.println("Set Test True");
					test = true;
				}
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frmAmbiforpc.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtrConsoleOutput, GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblRotation)
							.addGap(11)
							.addComponent(rdbtnClockwise)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnCounterclockwise)
							.addPreferredGap(ComponentPlacement.RELATED, 218, Short.MAX_VALUE)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblIpaddress)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldIPAddress, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblInset)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldInSet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(9)
							.addComponent(lblPort)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldPort, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addGap(0, 178, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnStart)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSystemtray)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnTest))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDisplay)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBoxDisplay, 0, 486, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblStartposition)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnTopLeft)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnTopRight)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnBottomLeft)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnBottomRight))
						.addComponent(lblConsole))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDisplay)
						.addComponent(comboBoxDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIpaddress)
						.addComponent(textFieldIPAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPort)
						.addComponent(textFieldPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInset)
						.addComponent(textFieldInSet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(rdbtnClockwise)
							.addComponent(rdbtnCounterclockwise)
							.addComponent(lblRotation)))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStartposition)
						.addComponent(rdbtnTopLeft)
						.addComponent(rdbtnTopRight)
						.addComponent(rdbtnBottomLeft)
						.addComponent(rdbtnBottomRight))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStart)
						.addComponent(btnSystemtray)
						.addComponent(btnTest))
					.addGap(18)
					.addComponent(lblConsole)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtrConsoleOutput, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
					.addContainerGap())
		);
		frmAmbiforpc.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmAmbiforpc.setJMenuBar(menuBar);
		
		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);
	}
	
	private void readDisplays(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		int i = 1;
		for(GraphicsDevice curGs : gs)
		{
			Display dis2 = new Display(i, curGs);
			comboBoxDisplay.addItem(dis2);
			if(i == 2){
				dis = dis2;

				//test3(dis.getData(100, 28, 16));
				
			}
			i++;
		 }
	}
	
	
	
	public void test3(byte[] data){
		String host = "192.168.128.29";
		//String host = "255.255.255.255";
	    int port = 40022;
		
		
		try {
			InetAddress IPAddress = InetAddress.getByName(host);
			
			/*byte[] sendData  = new byte[264];
			for(int i = 0 ; i < 264; i++){
		    	  sendData[i] = (byte) Integer.parseInt(textFieldIPAddress.getText());
		      } 
			
			byte[] sendData2  = new byte[264];
			for(int i = 0 ; i < 88; i+=3){
		    	  sendData2[i] = (byte) 255;
		    	  sendData2[i+1] = (byte) 0;
		    	  sendData2[i+2] = (byte) 0;
		      } */
			
		       
		    DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
		      
		    clientSocket.send(sendPacket); 
			
		      
		      
		      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
	
	public void frame__windowStateChanged(WindowEvent e){
		   // minimized
		   if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED){
			   frmAmbiforpc.setVisible(false);
		   }
		   // maximized
		   else if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH){
			   frmAmbiforpc.setVisible(true);
		   }
		}
	
	public void iniSystemTray(){
		//checking for support
	    if(!SystemTray.isSupported()){
	        System.out.println("System tray is not supported !!! ");
	        return ;
	    }
	    //get the systemTray of the system
	    SystemTray systemTray = SystemTray.getSystemTray();
	    //get default toolkit
	    //Toolkit toolkit = Toolkit.getDefaultToolkit();
	    //get image 
	    //Toolkit.getDefaultToolkit().getImage("src/resources/busylogo.jpg");
	    Image image = Toolkit.getDefaultToolkit().getImage("src/images/icon.png");

	    //popupmenu
	    PopupMenu trayPopupMenu = new PopupMenu();

	    //1t menuitem for popupmenu
	    MenuItem action = new MenuItem("Action");
	    trayPopupMenu.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            JOptionPane.showMessageDialog(null, "Action Clicked");
	            if(frmAmbiforpc.isVisible()){
	            	frmAmbiforpc.setVisible(false);
	            }else{
	            	frmAmbiforpc.setVisible(true);
	            }
	        }
	    });     
	    //trayPopupMenu.add(action);

	    //2nd menuitem of popupmenu
	    MenuItem close = new MenuItem("Close");
	    close.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.exit(0);             
	        }
	    });
	    //trayPopupMenu.add(close);

	    //setting tray icon
	    TrayIcon trayIcon = new TrayIcon(image, "AmbiForPC", trayPopupMenu);
	    //adjust to default size as per system recommendation 
	    trayIcon.setImageAutoSize(true);
	    trayIcon.addMouseListener(new MouseListener() {
	    	
	    	@Override
	        public void mouseClicked(MouseEvent ev) {
	        	if(frmAmbiforpc.isVisible()){
	            	frmAmbiforpc.setVisible(false);
	            }else{
	            	frmAmbiforpc.setVisible(true);
	            }
	        }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	    }); 

	    try{
	        systemTray.add(trayIcon);
	    }catch(AWTException awtException){
	        awtException.printStackTrace();
	    }
	}

	public static TestManager gettManager() {
		return tManager;
	}

	public static void settManager(TestManager tManager) {
		AmbiForPC.tManager = tManager;
	}
}

