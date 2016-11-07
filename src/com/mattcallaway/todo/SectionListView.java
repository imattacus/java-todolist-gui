package com.mattcallaway.todo;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import org.sormula.SormulaException;

public class SectionListView extends JComboBox<Section> implements Observer {
	TodoModel model;
	private Section dummySection;
	
	public SectionListView(TodoModel model) throws SormulaException {
		super();
		this.model = model;
		dummySection = new Section("All Sections");
		
		this.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() instanceof SectionListView) {
					JComboBox<?> c = (JComboBox<?>) e.getSource();
					if(c.getSelectedItem() != null && (e.getStateChange() == ItemEvent.SELECTED && ((Section)c.getSelectedItem()).getSectionid() != model.getCurrentSectionid() )) {
						//if the selected item is not null and the event is a newly selected item
						System.out.println("Section changed");
						Section selectedSection = (Section) c.getSelectedItem();
						try {
							model.setCurrentSectionId(selectedSection.getSectionid());
						} catch (SormulaException e1) {
							System.err.println("Section List View Item Listener");
							e1.printStackTrace();
						}
					}
				}
			}
		
		});	
		populateSectionList();
	}
	
	private void populateSectionList() throws SormulaException {
		for (Section s : model.getSections()) {
			this.addItem(s);
		}
		validate();
		repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (model.sectionsHaveChanged()) {
			ComboBoxModel<Section> savedModel = this.getModel();
			this.removeAllItems();
			try {
				populateSectionList();
			} catch (SormulaException e) {
				//Restore items back into combo box
				System.out.println("Restored combobox model");
				this.setModel(savedModel);
				e.printStackTrace();
			}
		}
	}
}
