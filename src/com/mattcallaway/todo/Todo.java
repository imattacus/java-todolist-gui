package com.mattcallaway.todo;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.sormula.SormulaException;

public class Todo {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, SormulaException{
		TodoController todo = new TodoController();
		TodoComponent todocomp = new TodoComponent(todo);
		
		JFrame frame = new JFrame("Java To Do List");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(todocomp);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

}
