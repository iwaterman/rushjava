package com.apress.springrecipes.wire;

public class Roommate {
	private Person person;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {

		return person.toString();
	}

}
