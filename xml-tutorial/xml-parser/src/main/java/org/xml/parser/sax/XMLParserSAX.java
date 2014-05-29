package org.xml.parser.sax;

import java.io.File;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.parser.Employee;

public class XMLParserSAX {

	public static void main(String[] args) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			MyHandler handler = new MyHandler();
			saxParser.parse(new File("src/main/resources/sax-employee.xml"), handler);
			// Get Employees list
			List<Employee> empList = handler.getEmpList();
			// print employee information
			for (Employee emp : empList) {
				System.out.println(emp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}