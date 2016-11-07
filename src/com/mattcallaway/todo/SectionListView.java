package com.mattcallaway.todo;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import org.sormula.SormulaException;

/**
 * Combobox containing sections from the model with logic to update combobox when the list of sections changes in the model
 * @author mattcallaway
 *
 */
public class SectionListView extends JComboBox<Section> implements Observer {
	TodoModel model;
	
	/**
	 * Create SectionListView 
	 * @param model the todo list model
	 * @throws SormulaException
	 */
	public SectionListView(TodoModel model) throws SormulaException {
		super();
		this.model = model;
		
		//Listens for selecting items in combobox and updates model to change tasklist accordingly
		this.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getSource() instanceof SectionListView) {
					JComboBox<?> c = (JComboBox<?>) e.getSource();
					if(c.getSelectedItem() != null && (e.getStateChange() == ItemEvent.SELECTED && ((Section)c.getSelectedItem()).getSectionid() != model.getCurrentSectionid() )) {
						//if the selected item is not null and the event is a newly selected item
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
	
	/**
	 * Fills the section combobox with sections from the model
	 * @throws SormulaException
	 */
	private void populateSectionList() throws SormulaException {
		for (Section s : model.getSections()) {
			this.addItem(s);
		}
		validate();
		repaint();
	}

	@Override
	/**
	 * Update the SectionListView when changes in model are observed
	 */
	public void update(Observable o, Object arg) {
		//Checks to see if the changes in model are relevant to the sections
		if (model.sectionsHaveChanged()) {
			//Save current combobox model
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
