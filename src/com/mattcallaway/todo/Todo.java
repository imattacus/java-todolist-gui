package com.mattcallaway.todo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import org.sormula.SormulaException;

public class Todo {

	static Boolean running = true;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println("Welcome to JDoList! - type help for a list of commands");
		
		TodoModel todo = new TodoModel();
		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			while(running) {
				String[] input = user.readLine().split(" ");
				switch (input[0]) {
					case "newtask":
						if (input.length == 3) {
							todo.newTask(input[1], Integer.parseInt(input[2]));
							break;
						}
						System.err.println("Incorrect usage of newtask, expects <description> <section>");
						break;
					case "newsection":
						if (input.length == 2) {
							todo.newSection(input[1]);
							break;
						}
						System.err.println("Incorrect usage of newsection, expects <name>");
						break;
					case "deletetask":
						if (input.length == 2) {
							todo.deleteTask(Integer.parseInt(input[1]));
							break;
						}
						System.err.println("Incorrect usage of deletetask, expects <id>");
						break;
					case "deletesection":
						if (input.length == 2) {
							todo.deleteSection(Integer.parseInt(input[1]));
							break;
						}
						System.err.println("Incorrect usage of deletesection, expects <id>");
						break;
					case "showtasks":
						if (input.length == 2) {
							System.out.printf("%-10s %-12s %-10s %-10s\n", "ID:", "Description:", "Completed:", "Section:");
							for (Task t : todo.getTasksBySection(Integer.parseInt(input[1]))) {
								System.out.printf("%-10s %-12s %-10s %-10s\n", t.getId(), t.getDescription(), t.getCompleted(), t.getSectionid());
							}
							break;
						}
						System.err.println("Incorrect usage of showtasks, expects <id>");
						break;
					case "showalltasks":
						if (input.length == 1) {
							System.out.printf("%-10s %-12s %-10s %-10s\n", "ID:", "Description:", "Completed:", "Section:");
							for (Task t : todo.getAllTasks()) {
								System.out.printf("%-10s %-12s %-10s %-10s\n", t.getId(), t.getDescription(), t.getCompleted(), t.getSectionid());
							}
							break;
						}
						System.err.println("Incorrect usage of showalltasks, expects no arguments.");
						break;
					case "marktask":
						if (input.length == 2) {
							todo.markTask(Integer.parseInt(input[1]));
							break;
						}
						System.err.println("Incorrect usage of marktask, expects <id>");
						break;
					case "showcompleted":
						if (input.length == 1) {
							System.out.printf("%-10s %-12s %-10s %-10s\n", "ID:", "Description:", "Completed:", "Section:");
							for (Task t : todo.getTasksByCompleted(true)) {
								System.out.printf("%-10s %-12s %-10s %-10s\n", t.getId(), t.getDescription(), t.getCompleted(), t.getSectionid());
							}
							break;
						}
						System.err.println("Incorrect usage of showcompleted, expects no arguments.");
						break;
					default:
						System.err.println("Incorrect usage of syntax! Type help for a list of commands.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SormulaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
