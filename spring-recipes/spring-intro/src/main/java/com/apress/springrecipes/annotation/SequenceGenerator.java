package com.apress.springrecipes.annotation;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class SequenceGenerator {

    private List<Integer> suffixes;
    private int initial;
    private int counter;

    private PrefixGenerator prefixGenerator;

    public void setSuffixes(List<Integer> suffixes) {
        this.suffixes = suffixes;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }
    
    public void setCounter(int counter) {
    	this.counter = counter;
    }

    @Autowired(required = false)
    public void setPrefixGenerator(PrefixGenerator prefixGenerator){
    	this.prefixGenerator = prefixGenerator;
    }
    
    public synchronized String getSequence() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(prefixGenerator.getPrefix());
        buffer.append(initial + counter++);
        DecimalFormat formatter = new DecimalFormat("0000");
        for (int suffix : suffixes) {
            buffer.append("-");
            buffer.append(formatter.format(suffix));
        }
        return buffer.toString();
    }
}
