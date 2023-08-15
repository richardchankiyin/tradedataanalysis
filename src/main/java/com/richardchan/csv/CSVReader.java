package com.richardchan.csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CSVReader {

	private String content;
	private int currentpos = -1;
	private static final char NEWLINE='\n';
	private static final String DELIMITER=",";
	private int contentlength = -1;
	private boolean isIterable = false;
	public CSVReader(String content) {
		this.content = content;
		this.contentlength = this.content.length();
		this.isIterable = true;
	}
	
	public CSVReader(File csvfile) throws IOException{
		Path p = Path.of(csvfile.getAbsolutePath());
		this.content = Files.readString(p);
		this.contentlength = this.content.length();
		this.isIterable = true;
	}
	
	public boolean isIterable() {
		return this.isIterable;
	}

	public String[] next() {
		if (!isIterable) {
			throw new IllegalStateException("not iterable");
		}
		
		int nextnewline = content.indexOf(NEWLINE,currentpos+1);
		int endchar = -1;
		if (nextnewline == -1)  {
			endchar = this.contentlength - 1;
			isIterable = false;
			String result[] = this.content.substring(currentpos + 1, endchar + 1).split(DELIMITER);
			currentpos = -1;
			return result;
		} else {
			endchar = nextnewline - 1;
			if (nextnewline == this.contentlength - 1) {
				isIterable = false;
			}
			String result[] = this.content.substring(currentpos + 1, endchar + 1).split(DELIMITER);
			if (!isIterable) {
				currentpos = -1;
			} else {
				currentpos = nextnewline;
			}
			return result;
		}
	}
	
}
