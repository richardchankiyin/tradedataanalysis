package com.richardchan.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class CSVReaderTest 
{

    @Test
    public void testWithStringContent()
    {
        final String content = "XXX,123.5,123.6,20150405\nYYY,133.5,133.6,20150403\nZZZ,134.5,134.6,20150404";
        
        CSVReader r = new CSVReader(content);
        assertTrue(r.isIterable());
        String[] line1 = r.next();
        assertTrue(4 == line1.length);
        assertEquals("XXX", line1[0]);
        assertEquals("123.5", line1[1]);
        assertEquals("123.6", line1[2]);
        assertEquals("20150405", line1[3]);
        
        assertTrue(r.isIterable());
        String[] line2 = r.next();
        assertTrue(4 == line2.length);
        assertEquals("YYY", line2[0]);
        assertEquals("133.5", line2[1]);
        assertEquals("133.6", line2[2]);
        assertEquals("20150403", line2[3]);
        
        assertTrue(r.isIterable());
        String[] line3 = r.next();
        assertTrue(4 == line3.length);
        assertEquals("ZZZ", line3[0]);
        assertEquals("134.5", line3[1]);
        assertEquals("134.6", line3[2]);
        assertEquals("20150404", line3[3]);
        
        assertFalse(r.isIterable());
    }
    
    @Test
    public void testWithStringContent2()
    {
        final String content = "XXX,123.5,123.6,20150405\nYYY,133.5,133.6,20150403\nZZZ,134.5,134.6,20150404\n";
        
        CSVReader r = new CSVReader(content);
        assertTrue(r.isIterable());
        String[] line1 = r.next();
        assertTrue(4 == line1.length);
        assertEquals("XXX", line1[0]);
        assertEquals("123.5", line1[1]);
        assertEquals("123.6", line1[2]);
        assertEquals("20150405", line1[3]);
        
        assertTrue(r.isIterable());
        String[] line2 = r.next();
        assertTrue(4 == line2.length);
        assertEquals("YYY", line2[0]);
        assertEquals("133.5", line2[1]);
        assertEquals("133.6", line2[2]);
        assertEquals("20150403", line2[3]);
        
        assertTrue(r.isIterable());
        String[] line3 = r.next();
        assertTrue(4 == line3.length);
        assertEquals("ZZZ", line3[0]);
        assertEquals("134.5", line3[1]);
        assertEquals("134.6", line3[2]);
        assertEquals("20150404", line3[3]);
        
        assertFalse(r.isIterable());
    }
    
    @SuppressWarnings("resource")
	@Test
    public void testWithFileContent() throws IOException {
    	
    	String filePath = "src/test/resources/CSVReaderTest_testWithFileContent.csv";
        
        CSVReader r = new CSVReader(new File(filePath));
        assertTrue(r.isIterable());
        String[] line1 = r.next();
        assertTrue(4 == line1.length);
        assertEquals("XXX", line1[0]);
        assertEquals("123.5", line1[1]);
        assertEquals("123.6", line1[2]);
        assertEquals("20150405", line1[3]);
        
        assertTrue(r.isIterable());
        String[] line2 = r.next();
        assertTrue(4 == line2.length);
        assertEquals("YYY", line2[0]);
        assertEquals("133.5", line2[1]);
        assertEquals("133.6", line2[2]);
        assertEquals("20150403", line2[3]);
        
        assertTrue(r.isIterable());
        String[] line3 = r.next();
        assertTrue(4 == line3.length);
        assertEquals("ZZZ", line3[0]);
        assertEquals("134.5", line3[1]);
        assertEquals("134.6", line3[2]);
        assertEquals("20150404", line3[3]);
        
        assertFalse(r.isIterable());
    }
}
