package com.mattcallaway.todo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.sormula.SormulaException;

public class TodoComponent extends JPanel {
	TodoModel model;
	
	@SuppressWarnings("serial")
	public TodoComponent(TodoController controller) throws SormulaException {
		super();
		try {
			model = new TodoModel(controller);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
		
		SectionListView slv = new SectionListView(model);
		TasklistView tlv = new TasklistView(model);
		ControlBarView cbv = new ControlBarView(model);
		
		JButton newSection = new JButton("+");
		newSection.setMargin(new Insets(0,0,0,0));
		newSection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == newSection) {
					String sectionName = JOptionPane.showInputDialog("Section Name", "");
					if (sectionName != null) {
						model.newSection(sectionName);
					}
				}
			}
		});
		
		JButton deleteSection = new JButton ("-");
		deleteSection.setMargin(new Insets(0,0,0,0));
		deleteSection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == deleteSection) {
					DeleteSectionDialog dsd = new DeleteSectionDialog((JFrame) SwingUtilities.windowForComponent(getTodoComponent()), model);
				}
			}
		});
		
		JCheckBox allSections = new JCheckBox();
		allSections.setText("Show all sections");
		allSections.setSelected(model.getShowAllSections());
		allSections.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == allSections) {
					try {
						model.showAllSections(allSections.isSelected());
						slv.setEnabled(!allSections.isSelected());
					} catch (SormulaException e1) {
						allSections.setSelected(false);
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		model.addObserver(tlv);
		model.addObserver(slv);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(newSection, c);
		
		c.gridx = 1;
		add(deleteSection, c);
		
		c.gridx = 2;
		c.gridwidth = 3;
		c.weightx = 0.1;
		add(slv,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 5;
		add(allSections, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 5;
		c.weighty = 0.3;
		c.fill = GridBagConstraints.BOTH;
		add(new JScrollPane(tlv) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(300,300);
			}
		}, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 5;
		add(cbv, c);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(300,400);
	}
	
	private TodoComponent getTodoComponent() {
		return this;
	}
}
