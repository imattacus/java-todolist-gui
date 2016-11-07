package com.mattcallaway.todo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class TaskPopup extends JPopupMenu {
	public TaskPopup (TodoModel model, Task t) {
		super();
		JMenuItem delete = new JMenuItem("Delete");
		
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == delete) {
					model.deleteTask(t);
				}
			}
		});
		
		add(delete);
	}
}
