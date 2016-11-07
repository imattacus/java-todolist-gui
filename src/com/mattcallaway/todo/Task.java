package com.mattcallaway.todo;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.sormula.annotation.Column;
import org.sormula.annotation.Where;

/**
 * Represents a task
 * @author mattcallaway
 *
 */
@Where(name="section", fieldNames="sectionid")
@Where(name="completed", fieldNames="completed")
public class Task {
	@Column(primaryKey=true, identity=true)
	int id;
	
	@Column(name="descr")
	String description;
	
	Boolean completed;
	
	int sectionid;
	
	/**
	 * Empty constructor required for Sormula
	 */
	public Task() {
		
	}
	
	/**
	 * Create a task
	 * @param description the description of the task
	 * @param sectionid the id of the section this task should belong to
	 */
	public Task(String description, int sectionid) {
		this.description = description;
		this.sectionid = sectionid;
		this.completed = false;
	}
	
	/**
	 * Get the id of this task
	 * @return the task id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set the id of this task
	 * @param id the id to set this tasks id to
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * get the description of this task
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description of this task (to be used for updating, not yet implemented)
	 * @param description the description to set this tasks description to
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get whether or not this task is completed
	 * @return true if task completed, false if not
	 */
	public Boolean getCompleted() {
		return completed;
	}

	/**
	 * set whether or not this task is completed
	 * @param completed value to set this tasks completeness to
	 */
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	/**
	 * Get id of the section this task belongs to
	 * @return id of section
	 */
	public int getSectionid() {
		return sectionid;
	}

	/**
	 * Set the section id of this task
	 * @param sectionid section id to set this tasks sectionid to
	 */
	public void setSectionid(int sectionid) {
		this.sectionid = sectionid;
	}
}
