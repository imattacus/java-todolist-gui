package com.mattcallaway.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.sormula.Database;
import org.sormula.SormulaException;
import org.sormula.Table;

public class TodoController {
	
	Connection connection = null;
	
	public TodoController() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();
		stmt.execute("PRAGMA FOREIGN_KEYS=ON;");
	}
	
	public void close() throws SQLException {
		connection.close();
	}
	
	public void newTask(String description, int sectionid) throws SormulaException {
		Database database = new Database(connection);
		Table<Task> taskTable = database.getTable(Task.class);
		taskTable.insert(new Task(description, sectionid));
	}
	
	public void newSection (String name) throws SormulaException {
		Database database = new Database(connection);
		Table<Section> sectionTable = database.getTable(Section.class);
		sectionTable.insert(new Section(name));
	}
	
	public List<Section> getAllSections() throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Section> sectionTable = database.getTable(Section.class);
			return sectionTable.selectAll();
		} 
	}
	
	public void deleteTask(int taskid) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			taskTable.delete(taskTable.select(taskid));
		}
	}
	
	public void deleteSection(int sectionid) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Section> sectionTable = database.getTable(Section.class);
			sectionTable.delete(sectionTable.select(sectionid));
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
	
	public List<Task> getTasks(int sectionid, boolean showcomplete) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			if (showcomplete) return taskTable.selectAllCustom("WHERE sectionid = ?", sectionid);
			return taskTable.selectAllCustom("WHERE sectionid = ? AND completed=0", sectionid);	
		}
	}

	public List<Task> getAllTasks(boolean showcomplete) throws SormulaException {
		try (Database database = new Database(connection)) {
			Table<Task> taskTable = database.getTable(Task.class);
			if (showcomplete) return taskTable.selectAll();
			return taskTable.selectAllCustom("WHERE completed=0");
		} 
	}
	
}
