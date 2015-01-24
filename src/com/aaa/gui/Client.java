package com.aaa.gui;

import com.aaa.clientpc.KeepSession;
import com.aaa.clientpc.Login;
import com.aaa.clientpc.Logout;
import com.aaa.model.KeepSessionRequestInfo;
import com.aaa.model.KeepSessionResult;
import com.aaa.model.LoginRequestInfo;
import com.aaa.model.LoginResult;
import com.aaa.model.LogoutRequestInfo;
import com.aaa.model.LogoutResult;
import com.aaa.util.CryptoTools;
import com.aaa.util.MD5;
import com.aaa.util.SystemInfo;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;

public class Client extends JFrame
{
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JMenuBar jMenuBar1;
	private JScrollPane jScrollPane1;
	private JButton loginButton;
	private JButton logoutButton;
	private JTextArea textArea;
	private JTextField username;
	private JPasswordField userpw;
	private SetMenu setMenu = new SetMenu("设置", this);
	public String UserIP;
	public String MAC;
	public String ComputerName;
	public String UserID;
	public String UserPW;
	public String IsAutoLogin = "false";
	public String Token = "";
	public boolean isLogin = true;
	public static String ClientVersion = "";
	final String OSVersion = "Microsoft Windows NT 6.1.7601 Service Pack 1";
	public static String ErrInfo = "";

	Map<String, String> loginResult = new HashMap();
	private SystemTray systemTray;
	private TrayIcon trayIcon;
	private Timer timer;
	private static Image image;
	private MyPopupMenu myPopupMenu = new MyPopupMenu(this);

	static
	{
		InputStream in = Client.class.getClassLoader().getResourceAsStream("image/ico.png");
		try {
			image = ImageIO.read(in);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Client()
	{
		initComponents();
		try {
			open();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "读取文件失败!", "读取错误", 1);
		}
	}

	private void initComponents()
	{
		this.jLabel1 = new JLabel();
		this.username = new JTextField();
		this.jLabel2 = new JLabel();
		this.loginButton = new JButton();
		this.logoutButton = new JButton();
		this.jScrollPane1 = new JScrollPane();
		this.textArea = new JTextArea();
		this.userpw = new JPasswordField();
		this.jMenuBar1 = new JMenuBar();
		//设置窗口不能最大化
		this.setTitle("Java版AAA"); 
		this.setResizable(false);
		setDefaultCloseOperation(3);

		this.jLabel1.setText("用户名:");

		this.jLabel2.setText("密码:");
		
		this.loginButton.setText("登陆");
		this.loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Client.this.loginActionPerformed(evt);
			}
		});
		
		this.userpw.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				Client.this.loginActionPerformed(evt);
			}
		});
		
		this.logoutButton.setText("注销");
		this.logoutButton.setEnabled(false);
		this.logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Client.this.logoutActionPerformed(evt);
			}
		});
		this.textArea.setColumns(15);
		this.textArea.setRows(5);
		this.textArea.setEditable(false);
		textArea.setFont(new Font("", 0, 15));
		this.jScrollPane1.setViewportView(this.textArea);

		this.jMenuBar1.add(this.setMenu);


		setJMenuBar(this.jMenuBar1);

		GroupLayout layout = new GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.TRAILING)
										.addComponent(this.jLabel1)
										.addComponent(this.jLabel2))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(
														layout.createParallelGroup(
																GroupLayout.Alignment.LEADING)
																.addComponent(
																		this.username, 
																		-1, 
																		186, 32767)
																		.addGroup(
																				layout.createSequentialGroup()
																				.addComponent(
																						this.loginButton, 
																						-2, 
																						69, 
																						-2)
																						.addPreferredGap(
																								LayoutStyle.ComponentPlacement.RELATED, 
																								51, 
																								32767)
																								.addComponent(
																										this.logoutButton, 
																										-2, 
																										66, 
																										-2))
																										.addComponent(
																												this.userpw, 
																												-1, 
																												186, 32767))
																												.addPreferredGap(
																														LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(this.jScrollPane1, 
																																-2, 
																																300, 
																																-2)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
						.addGap(34, 34, 34)
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(this.jLabel1)
										.addComponent(
												this.username, 
												-2, 
												-1, 
												-2))
												.addGap(18, 18, 18)
												.addGroup(
														layout.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
																.addComponent(this.jLabel2)
																.addComponent(
																		this.userpw, 
																		-2, 
																		-1, 
																		-2))
																		.addGap(26, 26, 26)
																		.addGroup(
																				layout.createParallelGroup(
																						GroupLayout.Alignment.BASELINE)
																						.addComponent(this.loginButton)
																						.addComponent(this.logoutButton))
																						.addContainerGap(102, 32767))
																						.addComponent(this.jScrollPane1, 
																								-1, 249, 
																								32767));

		addWindowListener(new WindowAdapter() {
			public void windowIconified(WindowEvent e) {
				Client.this.setExtendedState(1);
				Client.this.setVisible(false);
				try {
					Client.this.iconified();
				}
				catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		int x = 300;
		int y = 200;
		setLocation(x, y);

		pack();
		try {
			open();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void loginActionPerformed(ActionEvent evt)
	{
		login();
	}

	private void login()
	{
		this.setMenu.getVersion();
		try {
			if ((this.UserIP == null) || (this.MAC == null))
			{
				InetAddress inetAddress = InetAddress.getLocalHost();
				this.UserIP = getIPAddress(inetAddress);
				this.UserIP = ip;
				if (SystemInfo.isWindows()) {
					this.MAC = getMACAddress(inetAddress).toUpperCase();
				}else {
					this.MAC = "112233445566";
				}
				
			}
			this.UserID = this.username.getText().trim();
			this.UserPW = new String(userpw.getPassword()).trim();
			this.ComputerName = SystemInfo.getcomputerName();
			Login login = new Login(new LoginRequestInfo(ErrInfo, this.UserID, this.UserPW, this.UserIP, this.ComputerName, this.MAC, this.IsAutoLogin, ClientVersion, "Microsoft Windows NT 6.1.7601 Service Pack 1"));
			LoginResult loginResult = login.login();
			if (loginResult.getIsLogin().equals("true"))
			{
				save();
				this.loginButton.setEnabled(false);
				this.username.setEnabled(false);
				this.userpw.setEnabled(false);
				this.logoutButton.setEnabled(true);
				this.textArea.setText("\t登陆成功!\n");
				this.Token = loginResult.getToken();
				String name = loginResult.getName();
				String netGroup = loginResult.getNetGroup();
				String userid = loginResult.getUserId();
				String AdminGroup = loginResult.getAdminGroup();
				String expireTime = loginResult.getExpireTime().replaceAll("T", 
						" ");
				String message = "学号：" + userid + "\n" + 
						"姓名：" + name + "\n" + 
						"您的宽带组为：" + netGroup + "\n" + 
						"您的年级组为：" + AdminGroup + "\n" +
						"到期时间：" + expireTime + "\n" +
						"IP：" + UserIP;

				this.textArea.append(message);
				this.timer = new Timer();
				this.timer.schedule(new TimerTask()
				{
					public void run() {
						if (Client.this.isLogin) {
							Client.this.Token = MD5.GetMD5Code(Client.this.Token);
							Client.this.Token = MD5.GetMD5Code(Client.this.Token);
							Client.this.isLogin = false;
						} else {
							Client.this.Token = MD5.GetMD5Code(Client.this.Token);
						}

						KeepSession keepSession = new KeepSession(new KeepSessionRequestInfo(Client.this.UserID, Client.this.UserIP, Client.this.Token, "false"));
						KeepSessionResult keepSessionResult = keepSession.keepsession();
						if (!keepSessionResult.getKeepSessionResult().equals("true"))
						{
							Client.this.textArea.setText("已注销!");
							Client.this.isLogin = true;
							Client.this.loginButton.setEnabled(true);
							Client.this.timer.cancel();
							Client.this.login();
						}
					}
				}
				, 10000L, 20000L);
			}
			else if (loginResult.getIsLogin().equals("false")) {
				if (loginResult.getIsExpire().equals("true"))
				{
					String message = "\t账户有效期已过\n用户名：" + loginResult.getName() + "\n" + 
							"宽带组：" + loginResult.getNetGroup() + "\n" + 
							"到期时间：" + loginResult.getExpireTime().replaceAll("T", " ") + "\n" +loginResult.getNotice();
					this.textArea.setText(message);
				}else if (loginResult.getIsIDPWWrong().equals("true"))
				{
					this.textArea.setText("用户名或密码错误，请检查你的输入。");
					JOptionPane.showMessageDialog(this, "你输入的用户名密码为:\n" + this.UserID + "\n" + this.UserPW + "\n", "密码错误", 1);
				}else if (loginResult.getIsNeedUpdate().equals("true")) {
					JOptionPane.showMessageDialog(this, "官方版本已升级，请下载最新版AAA到本机，\n然后选择设置→更新版本参数");
				}else {
					JOptionPane.showMessageDialog(this, loginResult.getNotice());
				}
			}
			//是否在控制台打印输出
			//System.out.println(this.UserIP + "\n" + this.MAC + "\n" + this.UserID + "\n" + this.UserPW + "\n" + this.ComputerName);
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "获取IP地址与MAC地址时遇到错误!", 
					"获取失败", 0);
		}
	}

	private void logoutActionPerformed(ActionEvent evt)
	{
		this.timer.cancel();
		this.Token = MD5.GetMD5Code(this.Token);
		Logout logout = new Logout(new LogoutRequestInfo(this.UserID, this.UserIP, this.Token));
		LogoutResult logoutResult = logout.logout();
		if (logoutResult.getLogoutResult().equals("true"))
		{
			this.textArea.setText("注销成功!");
		}
		else {
			this.textArea.setText("注销失败!");
		}
		this.isLogin = true;
		this.loginButton.setEnabled(true);
		this.username.setEnabled(true);
		this.userpw.setEnabled(true);
		this.logoutButton.setEnabled(false);
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame.setDefaultLookAndFeelDecorated(true);
					JDialog.setDefaultLookAndFeelDecorated(true);
					UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
					e.printStackTrace();
				}
				new Client().setVisible(true);
			}
		});
	}

	private String getIPAddress(InetAddress ia)
	{
		String ip = "";
		if (SystemInfo.isWindows()) {
			ip = ia.getHostAddress();
		}else {//Linux
			boolean bfindip = false;
			Enumeration<NetworkInterface> allEnumeration;
			try {
				allEnumeration = NetworkInterface.getNetworkInterfaces();
				InetAddress linuxip = null;
				while (allEnumeration.hasMoreElements()) {
					if (bfindip) {
						break;
					}
					NetworkInterface networkInterface = (NetworkInterface) allEnumeration
							.nextElement();
					Enumeration<InetAddress> address = networkInterface.getInetAddresses();
					while (address.hasMoreElements()) {
						linuxip = address.nextElement();
						if (linuxip != null && !linuxip.isLoopbackAddress() && linuxip.getHostAddress().indexOf(":") == -1) {
							ip = linuxip.getHostAddress();
							bfindip = true;
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ip;
	}


	private String getMACAddress(InetAddress ia)
			{
		byte[] mac;
		StringBuffer sb = new StringBuffer();
		try {
			mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			for (int i = 0; i < mac.length; i++)
			{
				String s = Integer.toHexString(mac[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString().toUpperCase();
			}

	private void iconified()
			throws AWTException
			{
		if (SystemTray.isSupported())
		{
			if ((this.systemTray == null) && (this.trayIcon == null))
			{
				this.systemTray = SystemTray.getSystemTray();
				this.trayIcon = new TrayIcon(image);
				this.trayIcon.setPopupMenu(this.myPopupMenu);
				this.trayIcon.addMouseListener(new TrayMouseMonitor());
				this.trayIcon.setToolTip("成都东软学院Java版AAA");
			}
			this.systemTray.add(this.trayIcon);
		}
			}

	private void save()
			throws Exception
			{
		CryptoTools cryptoTools = new CryptoTools("12345678".getBytes(), "87654321".getBytes());
		Preferences preferences = Preferences.userRoot().node("com.aaa.client");
		String uid = cryptoTools.encode(this.UserID);
		String pw = cryptoTools.encode(this.UserPW);
		preferences.put("uid", uid);
		preferences.put("pw", pw);
			}

	private void open()
			throws Exception
			{
		CryptoTools cryptoTools = new CryptoTools("12345678".getBytes(), "87654321".getBytes());
		Preferences preferences = Preferences.userRoot().node("com.aaa.client");
		String uid = preferences.get("uid", "");
		String pw = preferences.get("pw", "");
		if ((!uid.equals("")) && (!pw.equals("")))
		{
			uid = cryptoTools.decode(uid);
			pw = cryptoTools.decode(pw);
			this.username.setText(uid);
			this.userpw.setText(pw);
		}
			}

	private class TrayMouseMonitor extends MouseAdapter
	{
		private TrayMouseMonitor()
		{
		}

		public void mouseClicked(MouseEvent e)
		{
			if (e.getClickCount() == 2)
			{
				Client.this.setExtendedState(0);
				Client.this.setVisible(true);
				Client.this.setLocationRelativeTo(null);
				Client.this.systemTray.remove(Client.this.trayIcon);
			}
		}
	}
}