package com.mattcallaway.todo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Popup (context) menu opened when right clicking on a task
 * @author mattcallaway
 *
 */
public class TaskPopup extends JPopupMenu {
	/**
	 * Create TaskPopup menu
	 * @param model the todolist model
	 * @param t the task associated with this popup menu
	 */
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
