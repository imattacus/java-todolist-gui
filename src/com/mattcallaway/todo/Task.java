package com.mattcallaway.todo;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.sormula.annotation.Column;
import org.sormula.annotation.Where;

@Where(name="section", fieldNames="sectionid")
@Where(name="completed", fieldNames="completed")
public class Task {
	@Column(primaryKey=true, identity=true)
	int id;
	
	@Column(name="descr")
	String description;
	
	@Column(name="datetimecreated")
	Timestamp created;
	
	@Column(name="datecompleted")
	Timestamp datecompleted;
	
	Boolean completed;
	
	int sectionid;
	
	public Task() {
		
	}
	
	public Task(String description, int sectionid) {
		this.description = description;
		this.sectionid = sectionid;
		this.created = new Timestamp(System.currentTimeMillis());
		this.datecompleted = null;
		this.completed = false;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getDatecompleted() {
		return datecompleted;
	}

	public void setDatecompleted(Timestamp datecompleted) {
		this.datecompleted = datecompleted;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public int getSectionid() {
		return sectionid;
	}

	public void setSectionid(int sectionid) {
		this.sectionid = sectionid;
	}
}
