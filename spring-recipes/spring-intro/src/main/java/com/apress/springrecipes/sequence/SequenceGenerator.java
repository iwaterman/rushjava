package com.apress.springrecipes.sequence;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SequenceGenerator {
	private String prefix;
	private String suffix;
	private int initial;
	private int counter;

	private List<Object> suffixesList;
	private Set<Object> suffixesSet;
	private Map<Object, Object> suffixesMap;

	private Properties suffixesProperties;

	public SequenceGenerator() {
	}

	public SequenceGenerator(String prefix, String suffix, int initial) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.initial = initial;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setInitial(int initial) {
		this.initial = initial;
	}

	public void setSuffixesList(List<Object> suffixesList) {
		this.suffixesList = suffixesList;
	}

	public void setSuffixesMap(Map<Object, Object> suffixesMap) {
		this.suffixesMap = suffixesMap;
	}

	public void setSuffixesSet(Set<Object> suffixesSet) {
		this.suffixesSet = suffixesSet;
	}

	public void setSuffixesProperties(Properties suffixesProperties) {
		this.suffixesProperties = suffixesProperties;
	}

	public synchronized String getSequence() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(prefix);
		buffer.append("\n");

		buffer.append(initial + counter++);
		buffer.append("\n");

		buffer.append(suffix);
		buffer.append("\n");

		for (Object suffix : suffixesList) {
			buffer.append("-");
			buffer.append(suffix);
		}
		buffer.append("\n");
		for (Object suffix : suffixesSet) {
			buffer.append("-");
			buffer.append(suffix);
		}
		buffer.append("\n");
		for (Map.Entry entry : suffixesMap.entrySet()) {
			buffer.append("-");
			buffer.append(entry.getKey());
			buffer.append("@");
			buffer.append(entry.getValue());
		}

		return buffer.toString();
	}
}
