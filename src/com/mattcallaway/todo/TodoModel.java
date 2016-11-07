package com.mattcallaway.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.sormula.SormulaException;

/**
 * Model for the todo list
 * @author mattcallaway
 *
 */
public class TodoModel extends Observable {
	TodoController cont;
	List<Task> tasksInView;
	int currentSectionId;
	boolean showComplete = false;
	boolean showAllSections = true;
	boolean tasksChanged;
	boolean sectionsChanged;
	
	/**
	 * Create instance of TodoModel
	 * @param controller The controller object of the todo list
	 * @throws SormulaException can be thrown while populating the list of tasks
	 */
	public TodoModel(TodoController controller) throws SormulaException {
		super();
		cont = controller;
		tasksInView = new ArrayList<Task>();
		updateTaskList();
	}
	
	/**
	 * Sets the current section id in the model to this value
	 * @param id the sectionid that the model should be set to 
	 * @throws SormulaException can be thrown while populating the list of tasks
	 */
	public void setCurrentSectionId(int id) throws SormulaException {
		currentSectionId = id;
		showAllSections = false;
		updateTaskList();
	}
	
	/**
	 * Sets the model to show or not show tasks from all sections in the tasklist
	 * @param b true if should show tasks from all sections, false if not
	 * @throws SormulaException can be thrown while populating the list of tasks
	 */
	public void showAllSections(boolean b) throws SormulaException {
		showAllSections = b;
		updateTaskList();
	}
	
	/**
	 * Toggle whether or not the model should show completed tasks as well as incomplete tasks in the tasklist
	 * @throws SormulaException can be thrown while populating the list of tasks
	 */
	public void toggleShowComplete() throws SormulaException {
		showComplete = !showComplete;
		updateTaskList();
	}
	
	private void updateTaskList() throws SormulaException {
		if (showAllSections) {
			tasksInView = cont.getAllTasks(showComplete);
		}
		else {
			tasksInView = cont.getTasks(currentSectionId, showComplete);
		}
		tasksChanged = true;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Get the current list of tasks from the model to be displayed
	 * @return the list of tasks
	 */
	public List<Task> getTasksInView() {
		tasksChanged = false;
		return tasksInView;
	}
	
	/**
	 * Get the current list of sections from the model to be displayed
	 * @return the list of sections
	 * @throws SormulaException can be thrown while retrieving from the controller
	 */
	public List<Section> getSections() throws SormulaException {
		sectionsChanged = false;
		return cont.getAllSections();
	}
	
	/**
	 * Create a new task
	 * @param description the string description of the task
	 * @param sectionid the sectionid this task should belong to
	 * @return True if this operation is completed successfully, false if otherwise
	 */
	public boolean newTask(String description, int sectionid) {
		try {
			cont.newTask(description, sectionid);
			updateTaskList();
			return true;
		} catch (SormulaException e) {
			return false;
		}
	}
	
	/**
	 * Create a new section
	 * @param name the name of the section to be created
	 */
	public void newSection(String name) {
		try {
			cont.newSection(name);
			sectionsChanged = true;
			System.out.println("Created new section: " + name);
			setChanged();
			notifyObservers();
		} catch (SormulaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the current section ID according to the model
	 * @return integer representing the section id
	 */
	public int getCurrentSectionid() {
		return currentSectionId;
	}
	
	/**
	 * Get the boolean value of whether or not the model is showing tasks from all sections
	 * @return true if model is showing from all sections, false if not
	 */
	public boolean getShowAllSections() {
		return showAllSections;
	}
	
	/**
	 * Mark a task, toggling its completeness 
	 * @param t the task to mark
	 */
	public void markTask(Task t) {
		try {
			cont.markTask(t.getId());
			updateTaskList();
		} catch (SormulaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete a section
	 * @param section the section to be deleted
	 */
	public void deleteSection(Section section) {
		try {
			System.out.println("Deleting section");
			cont.deleteSection(section.getSectionid());
			sectionsChanged = true;
			setChanged();
			notifyObservers();
		} catch (SormulaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete a task
	 * @param t the task to be deleted
	 */
	public void deleteTask(Task t) {
		try {
			cont.deleteTask(t.getId());
			updateTaskList();
		} catch (SormulaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Get a section by id
	 * @param id the id of the required section
	 * @return the section with specified ID
	 */
	public Section getSection(int id) {
		return null;
	}
	
	/**
	 * Return whether or not the task list has changed in the model since last update
	 * @return true if the tasks have changed
	 */
	public boolean tasksHaveChanged() {
		return tasksChanged;
	}
	
	/**
	 * Return whether or not the sections have changed in the model since last udpate
	 * @return true if the sections have changed
	 */
	public boolean sectionsHaveChanged() {
		return sectionsChanged;
	}
	
}
