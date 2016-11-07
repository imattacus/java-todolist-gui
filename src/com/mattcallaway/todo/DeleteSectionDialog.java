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

import org.sormula.SormulaException;

/**
 * Dialog box to delete a section
 * @author mattcallaway
 *
 */
public class DeleteSectionDialog extends JDialog {
	/**
	 * Create a DeleteSectionDialog
	 * @param frame the parent frame this Dialog box belongs to
	 * @param model the todo list model
	 */
	public DeleteSectionDialog (JFrame frame, TodoModel model) {
		super(frame, "Delete Section", ModalityType.DOCUMENT_MODAL);
		JPanel panel = new JPanel(new GridBagLayout());
		
		JComboBox<Section> sections = new JComboBox<Section>();
		try {
			for (Section s : model.getSections()) {
				sections.addItem(s);
			}
		} catch (SormulaException e) {
			e.printStackTrace();
		}
		
		JButton delete = new JButton("Delete");
		JButton cancel = new JButton("Cancel");
		
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == delete) {
					if (sections.getSelectedItem() != null) {
						model.deleteSection((Section) sections.getSelectedItem());
						dispose();
					}
				}
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();		
			}
		});
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		panel.add(sections, c);
		
		c.gridy = 1;
		c.gridwidth = 1;
		c.weighty = 0.5;
		panel.add(delete, c);
		
		c.gridx = 1;
		panel.add(cancel,c);
		
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}
}
