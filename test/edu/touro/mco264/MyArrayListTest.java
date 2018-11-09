package edu.touro.mco264;

import org.junit.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class MyArrayListTest {

    private MyArrayList<String> array;
    private Iterator iterator;

    @Before
    public void setUp() {
        array = new MyArrayList<>();
        iterator = array.iterator();
    }
    
    @Test
    public void iteratorTestEnhancedForLoopIteratesThroughArray() {
        array.add("A"); array.add("B"); array.add("C");
        String testString = "";
        
        for(String element: array) {
            testString += element;
        }
        
        assertEquals("ABC", testString);
    }
    
    @Test
    public void iteratorTestEnhancedForLoopFullSyntax() {
        array.add("A"); array.add("B"); array.add("C");
        String testString = "";

        for(iterator = array.iterator(); iterator.hasNext();) {
            testString += iterator.next();
        }
        
        assertEquals("ABC", testString);
    }

    @Test
    public void iterator_hasNextTestHasMoreElementsReturnsTrue() {
        array.add("A"); array.add("B");
        iterator.next();

        boolean actual = iterator.hasNext();
    
        assertTrue(actual);
    }   
    
    @Test
    public void iterator_hasNextTestNoMoreElementsReturnsFalse() {
        array.add("A");
        iterator.next();

        boolean actual = iterator.hasNext();

        assertFalse(actual);
    }
    
    @Test
    public void iterator_nextTestCalledTwiceReturnsBothElements() {
        array.add("A"); array.add("B");

        Object firstElementReturned = iterator.next();
        Object secondElementReturned = iterator.next();

        assertEquals("A", firstElementReturned);
        assertEquals("B", secondElementReturned);
    }
    
    @Test
    public void iterator_nextTestEmptyArrayThrowsException() {
        try {
            iterator.next();
            fail();
        } catch (NoSuchElementException e) {
            assertThat(e.getMessage(), is("The iteration "
                        + "has no more elements"));
        }
    }
    
    @Test
    public void iterator_nextTestNoMoreElementsThrowsException() {
        array.add("A"); array.add("B");       
        iterator.next(); iterator.next();        
        
        try {
            iterator.next();
            fail();
        } catch (NoSuchElementException e) {
            assertThat(e.getMessage(), is("The iteration "
                        + "has no more elements"));
        }
    }
    
    @Test
    public void iterator_removeTestRemovesElementSizeShrinks() {
        array.add("A"); array.add("B");
        
        iterator.next();
        iterator.remove();
        
        assertEquals(1, array.size());
    }
    
    @Test
    public void iterator_removeTestCalledTwiceWithNextInBetween() {
        array.add("A"); array.add("B");
        
        iterator.next();
        iterator.remove();
        
        iterator.next();
        iterator.remove();
        
        assertEquals(0, array.size());
    }
    
    @Test
    public void iterator_removeTestNextNotCalledThrowsException() {       
        try {
            iterator.remove();
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is("Next has not been "
                        + "called directly before call to remove"));
        }
    }
    
    @Test
    public void iterator_removeTestCalledConsecutivelyThrowsException() {       
        array.add("A");
        iterator.next();
        iterator.remove();
        
        try {
            iterator.remove();
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is("Next has not been "
                        + "called directly before call to remove"));
        }
    }
}
