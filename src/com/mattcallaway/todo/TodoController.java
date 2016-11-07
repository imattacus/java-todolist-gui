package com.mattcallaway.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.sormula.Database;
import org.sormula.SormulaException;
import org.sormula.Table;

/**
 * TodoController interfaces with the database using Sormula
 * @author mattcallaway
 *
 */
public class TodoController {
	
	Connection connection = null;
	
	/**
	 * Create TodoController 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public TodoController() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();
		stmt.execute("PRAGMA FOREIGN_KEYS=ON;");
	}
	
	/**
	 * Close connection
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		connection.close();
	}
	
	/**
	 * Create a new task in the database
	 * @param description description of the task
	 * @param sectionid id of the section this task should belong to
	 * @throws SormulaException
	 */
	public void newTask(String description, int sectionid) throws SormulaException {
		Database database = new Database(connection);
		Table<Task> taskTable = database.getTable(Task.class);
		taskTable.insert(new Task(description, sectionid));
	}
	
	/**
	 * Create a new section in the database
	 * @param name name of the new section
	 * @throws SormulaException
	 */
	public void newSection (String name) throws SormulaException {
		Database database = new Database(connection);
		Table<Section> sectionTable = database.getTable(Section.class);
		sectionTable.insert(new Section(name));
	}
	
	/**
	 * Gets all sections from the database
	 * @return List of all sections
	 * @throws SormulaException
	 */
	public List<Section> getAllSections() throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Section> sectionTable = database.getTable(Section.class);
			return sectionTable.selectAll();
		} 
	}
	
	/**
	 * Delete a task in the database
	 * @param taskid the id of the task to be deleted
	 * @throws SormulaException
	 */
	public void deleteTask(int taskid) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			taskTable.delete(taskTable.select(taskid));
		}
	}
	
	/**
	 * Delete a section in the database
	 * @param sectionid the id of the section to be deleted
	 * @throws SormulaException
	 */
	public void deleteSection(int sectionid) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Section> sectionTable = database.getTable(Section.class);
			sectionTable.delete(sectionTable.select(sectionid));
		}
	}
	
	/**
	 * Toggle completeness of a task in the database
	 * @param taskid the id of the task
	 * @throws SormulaException
	 */
	public void markTask(int taskid) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			Task task = taskTable.select(taskid);
			task.setCompleted(!task.getCompleted());
			taskTable.update(task);
			System.out.println("Task: " + taskid + " is completed: " + task.getCompleted());
		}
	}
	
	/**
	 * Get tasks from the database
	 * @param sectionid id of the section to get tasks from
	 * @param showcomplete whether or not to include completed tasks
	 * @return List of tasks
	 * @throws SormulaException
	 */
	public List<Task> getTasks(int sectionid, boolean showcomplete) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			if (showcomplete) return taskTable.selectAllCustom("WHERE sectionid = ?", sectionid);
			return taskTable.selectAllCustom("WHERE sectionid = ? AND completed=0", sectionid);	
		}
	}

	/**
	 * Get tasks from all sections from the database
	 * @param showcomplete whether or not to include completed tasks
	 * @return List of tasks
	 * @throws SormulaException
	 */
	public List<Task> getAllTasks(boolean showcomplete) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			if (showcomplete) return taskTable.selectAll();
			return taskTable.selectAllCustom("WHERE completed=0");
		} 
	}
	
}
