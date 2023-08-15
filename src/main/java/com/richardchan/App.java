package com.richardchan;

import java.io.File;

import com.richardchan.csv.CSVReader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        CSVReader reader = new CSVReader(new File("src/main/resources/scandi.csv"));
        
        while (reader.isIterable()) {
        	String[] columns = reader.next();
        	//printStringArray(columns);
        }
        
    }
    
    private static void printStringArray(String[] items) {
    	for (int i = 0; i < items.length; i++) {
    		System.out.print(items[i] + "|");
    	}
    	System.out.println("");
    }
}
