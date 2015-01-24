package com.aaa.gui;

import com.aaa.clientpc.ChangePassword;
import com.aaa.model.ChangePasswordRequestInfo;
import com.aaa.model.ChangePasswordResult;

import java.awt.HeadlessException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class MyPopupMenu extends PopupMenu
{
	private MenuItem changePW;
	private MenuItem exit;
	private Client client;

	public MyPopupMenu(Client client)
	{
		this.client = client;
		this.exit = new MenuItem("ע�����˳�");
		this.changePW = new MenuItem("�޸�����");
		this.exit.addActionListener(new PopupMenuActionListener());
		this.changePW.addActionListener(new PopupMenuActionListener());
		
		add(this.changePW);
		add(this.exit);
	}
	private class PopupMenuActionListener implements ActionListener {
		private PopupMenuActionListener() {
		}
		public void actionPerformed(ActionEvent e) { 
			if (e.getActionCommand().equals("�޸�����"))
			{
				String OPW = JOptionPane.showInputDialog(MyPopupMenu.this.client, "�����������:");
				String NPW = JOptionPane.showInputDialog(MyPopupMenu.this.client, "������������:");
				String NPW2 = JOptionPane.showInputDialog(MyPopupMenu.this.client, "��ȷ��������:");
				if ((OPW != null) && (NPW != null) && (NPW2 != null))
				{
					if (NPW.equals(NPW2))
					{
						ChangePassword changePassword = new ChangePassword(new ChangePasswordRequestInfo(MyPopupMenu.this.client.UserID, OPW, NPW));
						try {
							ChangePasswordResult changePasswordResult = changePassword.changepassword();
							if (changePasswordResult.getChangePasswordResult().equals("true"))
							{
								JOptionPane.showMessageDialog(MyPopupMenu.this.client, "�޸�����ɹ���", "�����޸ĳɹ�", 1);
							}
							else
								JOptionPane.showMessageDialog(MyPopupMenu.this.client, "�޸�����ʧ�ܣ�����������Ƿ�������ȷ\n�����룺" + OPW, "�����޸�ʧ��", 1);
						}
						catch (HeadlessException e1)
						{
							e1.printStackTrace();
						}
						catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					else {
						JOptionPane.showMessageDialog(MyPopupMenu.this.client, "������������벻һ�£����������룡", "���벻һ��", 1);
					}
				}
			}else if (e.getActionCommand().equals("ע�����˳�")) {
					System.exit(0);
			}
		}
	}
}