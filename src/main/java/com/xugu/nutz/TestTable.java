package com.xugu.nutz;

import org.nutz.dao.entity.annotation.*;

@Table("t_test")     
public class TestTable {
	@Id   
	private int id;

	@Column    
	private String name;
	@Column   
	private String code;

	public TestTable()
	{}
	public TestTable(int id)
	{
		this.id=id;
		this.name = "xugu batch name "+id;
		this.code = " xugu batch code "+id;
	}
	
	public int getId() {
	
		return id;
	}
	
	public void setId(int id) {
	
		this.id = id;
	}
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}
	
	public String getCode() {
	
		return code;
	}
	
	public void setCode(String code) {
	
		this.code = code;
	}

}
