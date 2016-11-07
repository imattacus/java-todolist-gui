package com.mattcallaway.todo;

import org.sormula.annotation.Column;

/**
 * Represents a section, a collection of tasks
 * @author mattcallaway
 *
 */
public class Section {
	@Column(name="id",primaryKey=true, identity=true)
	int sectionid;

	String name;
	
	/**
	 * Empty constructor required for Sormula
	 */
	public Section(){
		
	}
	
	/**
	 * Creates a section
	 * @param name Name of the section
	 */
	public Section(String name) {
		this.name = name;
	}
	
	/**
	 * Get id of this section
	 * @return the section id
	 */
	public int getSectionid() {
		return sectionid;
	}
	
	/**
	 * Set the id of this section
	 * @param id the id to set this section's id to
	 */
	public void setSectionid(int id) {
		this.sectionid = id;
	}
	
	/**
	 * Get the name of this section
	 * @return the name of the section
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of this section
	 * @param name the name to set this sections name to
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * The name of the section
	 */
	public String toString() {
		return name;
	}
}
