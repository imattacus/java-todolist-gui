package com.mattcallaway.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.sormula.SormulaException;


public class TodoModel extends Observable {
	TodoController cont;
	List<Task> tasksInView;
	int currentSectionId;
	boolean showComplete = false;
	boolean showAllSections = true;
	boolean tasksChanged;
	boolean sectionsChanged;
	
	
	public TodoModel(TodoController controller) throws SormulaException {
		super();
		cont = controller;
		tasksInView = new ArrayList<Task>();
		updateTaskList();
	}
	
	public void setCurrentSectionId(int id) throws SormulaException {
		currentSectionId = id;
		showAllSections = false;
		updateTaskList();
	}
	
	public void showAllSections(boolean b) throws SormulaException {
		showAllSections = b;
		updateTaskList();
	}
	
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
	
	public List<Task> getTasksInView() {
		tasksChanged = false;
		return tasksInView;
	}
	
	public List<Section> getSections() throws SormulaException {
		sectionsChanged = false;
		return cont.getAllSections();
	}
	
	public boolean newTask(String description, int sectionid) {
		try {
			cont.newTask(description, sectionid);
			updateTaskList();
			return true;
		} catch (SormulaException e) {
			return false;
		}
	}
	
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
	
	public int getCurrentSectionid() {
		return currentSectionId;
	}
	
	public boolean getShowAllSections() {
		return showAllSections;
	}
	
	public void markTask(Task t) {
		try {
			cont.markTask(t.getId());
			updateTaskList();
		} catch (SormulaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	public Section getSection(int id) {
		return null;
	}
	
	public boolean tasksHaveChanged() {
		return tasksChanged;
	}
	
	public boolean sectionsHaveChanged() {
		return sectionsChanged;
	}
	
}
