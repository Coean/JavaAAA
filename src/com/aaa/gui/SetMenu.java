package com.aaa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.aaa.clientpc.GetLogRecord;
import com.aaa.model.GetLogRecordResult;
import com.aaa.util.CryptoTools;
import com.aaa.util.ExecuteCommond;
import com.aaa.util.GetFileInfo;
import com.aaa.util.MD5;
import com.aaa.util.SystemInfo;

public class SetMenu extends JMenu
{
	JMenuItem setParamter;
	JMenuItem setMac;
	JMenuItem setIp;
	JMenuItem wifiItem;
	JMenuItem closewifi;
	JMenuItem setwifi;
	JMenuItem getlog;
	JMenuItem getinfo;
	Client client;

	public SetMenu(String label, Client client)
	{
		super(label);
		this.client = client;
		this.getlog = new JMenuItem("查询登陆日志");
		this.getinfo = new JMenuItem("更新版本参数");
		this.setParamter = new JMenuItem("手动设置参数");
		MenuItemActionListener menuItemActionListener = new MenuItemActionListener();
		this.getlog.addActionListener(menuItemActionListener);
		this.getinfo.addActionListener(menuItemActionListener);
		this.setParamter.addActionListener(menuItemActionListener);
		add(this.getlog);
		add(this.getinfo);
		add(this.setParamter);
	}

	public void setVersion(String ErrInfo, String ClientVersion)
	{
		Preferences preferences = Preferences.userRoot().node("com.aaa.client");
		preferences.put("errinfo", ErrInfo);
		preferences.put("clientversion", ClientVersion);
	}

	public void getVersion()
	{
		Preferences preferences = Preferences.userRoot().node("com.aaa.client");
		String errinfo = preferences.get("errinfo", "");
		String clientversion = preferences.get("clientversion", "");
		if ((!errinfo.equals("")) && (!clientversion.equals("")))
		{
			Client.ErrInfo = errinfo;
			Client.ClientVersion = clientversion;
		}
	}

	private class MenuItemActionListener
	implements ActionListener
	{
		private MenuItemActionListener()
		{
		}

		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("手动设置参数"))
			{
				String ClientVersion = JOptionPane.showInputDialog(SetMenu.this.client, "输入版本号:");
				String ErrInfo = JOptionPane.showInputDialog(SetMenu.this.client, "输入ErrInfo:");
				if ((ClientVersion != null) && (!ClientVersion.equals("")))
				{
					if ((ErrInfo != null) && (!ErrInfo.equals("")))
					{
						SetMenu.this.setVersion(ErrInfo, ClientVersion);
					}
				}
			}else if (e.getActionCommand().equals("查询登陆日志")) {
				String uid = JOptionPane.showInputDialog(SetMenu.this.client,"请输入学号");
				String pw = JOptionPane.showInputDialog(SetMenu.this.client,"请输入密码");
				String keyMD5 = MD5.GetMD5Code(uid);
			    String key = keyMD5.substring(0, 8);
			    byte[] desKey = key.getBytes();
			    byte[] desIV = desKey;
			    try {
			    	CryptoTools cryptoTools = new CryptoTools(desKey, desIV);
					pw = cryptoTools.encode(pw);
					//System.out.println(pw);
					GetLogRecord getlog = new GetLogRecord();
					String message = "     用户名                 登录时间       登录方式    注销时间          注销方式       在线秒数    IP地址      MAC地址       计算机名             系统版本\n";
					List<GetLogRecordResult> list = getlog.loginlog(uid, pw);
					for (int i = 0; i < list.size(); i++) {
						message+=list.get(i).getUserid()+" "+list.get(i).getLogintime()+" "+list.get(i).getLoginmethod()+" "+list.get(i).getLogouttime()+" "+list.get(i).getLogoutmethod()+"      "+list.get(i).getOnlinetime()+"     "+list.get(i).getIp()+" "+list.get(i).getMac()+" "+list.get(i).getComputername()+" "+list.get(i).getOsversion()+"\n";
						if (i >= 9) {
							break;
						}
					}
					JOptionPane.showMessageDialog(SetMenu.this.client, message);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if (e.getActionCommand().equals("更新版本参数")) {
				String filepath = null;
				//文件选择器
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("NSUAAA客户端(NSUAAAC.exe)", "exe","EXE");
				chooser.setFileFilter(fileNameExtensionFilter);
				chooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
				chooser.setApproveButtonText("确定");
				chooser.setDialogTitle("请选择最新版本AAA客户端...");
				if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(chooser)) {
					filepath = chooser.getSelectedFile().getPath();
				}
				if (! (filepath == null)) {
					File file = new File(filepath);
					GetFileInfo fileinfo = new GetFileInfo();
					String errinfo = null;
					String fileversion = null;
					try {
						errinfo = fileinfo.getMd5ByFile(file);
						fileversion = fileinfo.getFileVersion(file);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (errinfo != null && fileversion != null) {
						//System.out.println("文件版本："+ fileversion + "\n" + "ErrInfo：" + errinfo);
						SetMenu.this.setVersion(errinfo, fileversion);
					}
				}
			}
		}
	}
}