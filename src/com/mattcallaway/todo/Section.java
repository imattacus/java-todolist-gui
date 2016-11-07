package com.mattcallaway.todo;

import org.sormula.annotation.Column;

public class Section {
	@Column(name="id",primaryKey=true, identity=true)
	int sectionid;

	String name;
	
	public Section(){
		
	}
	
	public Section(String name) {
		this.name = name;
	}
	
	public int getSectionid() {
		return sectionid;
	}
	
	public void setSectionid(int id) {
		this.sectionid = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}
