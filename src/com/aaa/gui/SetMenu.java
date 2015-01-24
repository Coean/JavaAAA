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
		this.getlog = new JMenuItem("��ѯ��½��־");
		this.getinfo = new JMenuItem("���°汾����");
		this.setParamter = new JMenuItem("�ֶ����ò���");
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
			if (e.getActionCommand().equals("�ֶ����ò���"))
			{
				String ClientVersion = JOptionPane.showInputDialog(SetMenu.this.client, "����汾��:");
				String ErrInfo = JOptionPane.showInputDialog(SetMenu.this.client, "����ErrInfo:");
				if ((ClientVersion != null) && (!ClientVersion.equals("")))
				{
					if ((ErrInfo != null) && (!ErrInfo.equals("")))
					{
						SetMenu.this.setVersion(ErrInfo, ClientVersion);
					}
				}
			}else if (e.getActionCommand().equals("��ѯ��½��־")) {
				String uid = JOptionPane.showInputDialog(SetMenu.this.client,"������ѧ��");
				String pw = JOptionPane.showInputDialog(SetMenu.this.client,"����������");
				String keyMD5 = MD5.GetMD5Code(uid);
			    String key = keyMD5.substring(0, 8);
			    byte[] desKey = key.getBytes();
			    byte[] desIV = desKey;
			    try {
			    	CryptoTools cryptoTools = new CryptoTools(desKey, desIV);
					pw = cryptoTools.encode(pw);
					//System.out.println(pw);
					GetLogRecord getlog = new GetLogRecord();
					String message = "     �û���                 ��¼ʱ��       ��¼��ʽ    ע��ʱ��          ע����ʽ       ��������    IP��ַ      MAC��ַ       �������             ϵͳ�汾\n";
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
			}else if (e.getActionCommand().equals("���°汾����")) {
				String filepath = null;
				//�ļ�ѡ����
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("NSUAAA�ͻ���(NSUAAAC.exe)", "exe","EXE");
				chooser.setFileFilter(fileNameExtensionFilter);
				chooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
				chooser.setApproveButtonText("ȷ��");
				chooser.setDialogTitle("��ѡ�����°汾AAA�ͻ���...");
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
						//System.out.println("�ļ��汾��"+ fileversion + "\n" + "ErrInfo��" + errinfo);
						SetMenu.this.setVersion(errinfo, fileversion);
					}
				}
			}
		}
	}
}