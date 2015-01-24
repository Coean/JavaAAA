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
		this.exit = new MenuItem("注销并退出");
		this.changePW = new MenuItem("修改密码");
		this.exit.addActionListener(new PopupMenuActionListener());
		this.changePW.addActionListener(new PopupMenuActionListener());
		
		add(this.changePW);
		add(this.exit);
	}
	private class PopupMenuActionListener implements ActionListener {
		private PopupMenuActionListener() {
		}
		public void actionPerformed(ActionEvent e) { 
			if (e.getActionCommand().equals("修改密码"))
			{
				String OPW = JOptionPane.showInputDialog(MyPopupMenu.this.client, "请输入旧密码:");
				String NPW = JOptionPane.showInputDialog(MyPopupMenu.this.client, "请输入新密码:");
				String NPW2 = JOptionPane.showInputDialog(MyPopupMenu.this.client, "请确认新密码:");
				if ((OPW != null) && (NPW != null) && (NPW2 != null))
				{
					if (NPW.equals(NPW2))
					{
						ChangePassword changePassword = new ChangePassword(new ChangePasswordRequestInfo(MyPopupMenu.this.client.UserID, OPW, NPW));
						try {
							ChangePasswordResult changePasswordResult = changePassword.changepassword();
							if (changePasswordResult.getChangePasswordResult().equals("true"))
							{
								JOptionPane.showMessageDialog(MyPopupMenu.this.client, "修改密码成功！", "密码修改成功", 1);
							}
							else
								JOptionPane.showMessageDialog(MyPopupMenu.this.client, "修改密码失败，请检查旧密码是否输入正确\n旧密码：" + OPW, "密码修改失败", 1);
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
						JOptionPane.showMessageDialog(MyPopupMenu.this.client, "两次输入的密码不一致，请重新输入！", "密码不一致", 1);
					}
				}
			}else if (e.getActionCommand().equals("注销并退出")) {
					System.exit(0);
			}
		}
	}
}