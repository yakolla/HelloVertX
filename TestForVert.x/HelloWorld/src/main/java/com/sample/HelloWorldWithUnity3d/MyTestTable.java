package com.sample.HelloWorldWithUnity3d;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
 
 
 
@Entity
@Table(name = "mytable")
public class MyTestTable {
 
    @Id @GeneratedValue
    private Integer id;
     
    @NotNull @Column(updatable=true)
    private String name;
     
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
     
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
     
     
}