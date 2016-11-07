package com.mattcallaway.todo;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.sormula.SormulaException;

/**
 * Control Bar component comprising of Add task button and show completed tasks checkbox
 * @author mattcallaway
 *
 */
public class ControlBarView extends JPanel {
	TodoModel model;
	
	/**
	 * Create ControlBarView for specified model
	 * @param model the todo list model to use
	 */
	public ControlBarView(TodoModel model) {
		this.model = model;
		
		JButton add = new JButton("Add Task");
		JCheckBox showcomplete = new JCheckBox();
		showcomplete.setText("Show Completed Tasks");
		
		//Show complete checkbox action listener toggles whether model is showing completed tasks
		showcomplete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == showcomplete) {
					try {
						model.toggleShowComplete();
					} catch (SormulaException e1) {
						showcomplete.setSelected(false);
					}
				}
			}
		});
		
		//Add button action listener opens New task Dialog to allow user to create a new task
		add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == add) {
					NewTaskDialog ntd = new NewTaskDialog((JFrame) SwingUtilities.windowForComponent(getControlBarView()), model);
				}
			}	
		});
		
		
		this.add(add);
		this.add(showcomplete);
	}
	
	/**
	 * Returns the Control Bar View for use in action listeners and for creating dialog where parent is required
	 * @return this
	 */
	private ControlBarView getControlBarView() {
		return this;
	}
	
}
