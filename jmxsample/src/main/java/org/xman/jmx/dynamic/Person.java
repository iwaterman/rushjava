package org.xman.jmx.dynamic;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 被管理的Bean
 * 
 * @since 2010-9-13 下午02:35:34
 */
public class Person {
    private String name;
    private int age;
    private String gender;
    private int sendCount = 0;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getAge() {
	return age;
    }

    public void setAge(int age) {
	this.age = age;
    }

    public String getGender() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

    public void printName() {
	System.out.println(this.name);
    }

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }

    public void say(String message) {
	System.out.println(name + "说:" + message);
    }
}