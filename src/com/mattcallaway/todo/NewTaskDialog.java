package com.mattcallaway.todo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sormula.SormulaException;

/**
 * Dialog window for creating a new task
 * @author mattcallaway
 *
 */
public class NewTaskDialog extends JDialog {

	/**
	 * Create a new NewTaskDialog
	 * @param frame the parent frame of this dialog
	 * @param model the todo list model
	 */
	public NewTaskDialog(JFrame frame, TodoModel model) {
		super(frame, "New Task", ModalityType.DOCUMENT_MODAL);
		JPanel panel = new JPanel();
		
		JTextField input = new JTextField("Description");
		JComboBox<Section> sections = new JComboBox<Section>();
		
		//Populate section combobox with sections
		try {
			for (Section s : model.getSections()) {
				sections.addItem(s);
			}
		} catch (SormulaException e) {
			e.printStackTrace();
		}
		
		JButton create = new JButton("Create");
		JButton cancel = new JButton("Cancel");
		
		//Action listener for create button, creates the task in the model
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == create) {
					if (input.getText() != "") { //Text input field cannot be null, very crude validation
						if (model.newTask(input.getText(), ((Section) sections.getSelectedItem()).getSectionid())) {
							getNewTaskDialog().dispose();
						} else {
							input.setText("There was an internal error creating this task, sorry!");
						}
					} else {
						input.setText("This field cannot be empty!");
					}
				}
			}
		});
		
		//Cancel button action listener closes dialog
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getNewTaskDialog().dispose();		
			}
		});
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		panel.add(input,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weighty = 0.5;
		panel.add(sections,c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		panel.add(create,c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.5;
		panel.add(cancel,c);
		
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Returns the new task dialog for use action listener
	 * @return this
	 */
	private NewTaskDialog getNewTaskDialog() {
		return this;
	}
}
