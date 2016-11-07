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

public class ControlBarView extends JPanel {
	TodoModel model;
	
	public ControlBarView(TodoModel model) {
		this.model = model;
		
		JButton add = new JButton("Add Task");
		JCheckBox showcomplete = new JCheckBox();
		showcomplete.setText("Show Completed Tasks");
		
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
	
	private ControlBarView getControlBarView() {
		return this;
	}
	
}
