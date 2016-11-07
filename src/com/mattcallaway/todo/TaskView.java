package com.mattcallaway.todo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class TaskView extends JPanel{
	Task task;
	TodoModel model;
	
	public TaskView(Task task, TodoModel model) {
		super();
		this.task = task;
		this.model = model;
		
		JCheckBox check = new JCheckBox();
		JLabel taskLabel = new JLabel();
		JLabel sectionLabel = new JLabel();
		
		this.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
		
		check.setSelected(task.getCompleted());
		taskLabel.setText(task.getDescription());
		
		check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == check) {
					model.markTask(task);
				}
			}
		});
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup(e);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup(e);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		
		});
		
		add(check);
		add(taskLabel);
	}
	
	private void popup(MouseEvent e) {
		TaskPopup tp = new TaskPopup(model, task);
		tp.show(this, e.getX(), e.getY());
	}
	
}
