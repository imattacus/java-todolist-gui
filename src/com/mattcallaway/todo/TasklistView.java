package com.mattcallaway.todo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * TaskListView represents the list of tasks displayed in the GUI, updates to show most recent tasklist in model
 * @author mattcallaway
 *
 */
public class TasklistView extends JPanel implements Observer {
	TodoModel model;
	List<Task> tasks;
	
	/**
	 * create TaskListView
	 * @param model the todolist model
	 */
	public TasklistView(TodoModel model) {
		super(new GridBagLayout());
		this.model = model;
		
		tasks = model.getTasksInView();
		
		
		populateTaskList();
	}
	
	/**
	 * Fills the tasklist with tasks from the model
	 */
	private void populateTaskList() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = gbc.NORTH;
		this.add(new JPanel(), gbc);
		for (Task t : tasks) {
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            this.add(new TaskView(t, model), gbc, 0);
		}
		
		revalidate();
		repaint();
	}

	@Override
	/**
	 * Update when model changes to show most recent tasklist
	 */
	public void update(Observable o, Object arg) {
		//Checks if the update is relevant to the tasks
		if (model.tasksHaveChanged()) {
			tasks = model.getTasksInView();
			this.removeAll();
			populateTaskList();
		}
	}
}
