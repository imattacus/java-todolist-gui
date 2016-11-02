package com.mattcallaway.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.sormula.Database;
import org.sormula.SormulaException;
import org.sormula.Table;

public class TodoModel {
	
	Connection connection = null;
	
	public TodoModel() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();
		stmt.execute("PRAGMA FOREIGN_KEYS=ON;");
	}
	
	public void close() throws SQLException {
		connection.close();
	}
	
	public void newTask(String description, int sectionid) {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			taskTable.insert(new Task(description, sectionid));
			System.out.println("Task: " + description + " created in " + sectionid);
		} catch (SormulaException e) {
			System.err.println("Exception in newTask");
			e.printStackTrace();
		}
	}
	
	public List<Task> getAllTasks() throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			return taskTable.selectAll();
		} 
	}
	
	public void newSection (String name) {
		try (Database database = new Database(connection)) {
			Table<Section> sectionTable = database.getTable(Section.class);
			sectionTable.insert(new Section(name));
			System.out.println("Section: " + name + " created");
		} catch (SormulaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Section> getAllSections() throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Section> sectionTable = database.getTable(Section.class);
			return sectionTable.selectAll();
		} 
	}
	
	public List<Task> getTasksBySection(int sectionid) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			return taskTable.selectAllWhere("sectionid", sectionid);
		}
	}
	
	public void deleteTask(int taskid) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			taskTable.delete(taskTable.select(taskid));
			System.out.println("Task: " + taskid + " deleted!");
		}
	}
	
	public void deleteSection(int sectionid) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Section> sectionTable = database.getTable(Section.class);
			sectionTable.delete(sectionTable.select(sectionid));
			System.out.println("Section: " + sectionid + " deleted!");
		}
	}
	
	public void markTask(int taskid) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			Task task = taskTable.select(taskid);
			task.setCompleted(!task.getCompleted());
			taskTable.update(task);
			System.out.println("Task: " + taskid + " is completed: " + task.getCompleted());
		}
	}
	
	public List<Task> getTasksByCompleted(Boolean complete) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			return taskTable.selectAllWhere("completed", complete);
		}
	}
	
	
}
