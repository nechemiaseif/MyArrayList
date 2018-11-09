package edu.touro.mco264;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MyArrayListTest {

    private MyArrayList<String> array;

    @Before
    public void setUp() {
        array = new MyArrayList<>();
    }

    @Test
    public void ctor_Int_TestIllegalCapacityThrowsException() {
        try {
            MyArrayList<String> impossibleArray = new MyArrayList<>(-1);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Capacity cannot be negative. "
                    + "Capacity was: [-1]"));
        }
    }

    
    @Test
    public void iteratorTestEnhancedForLoopIteratesThroughArray() {
        array.add("A"); array.add("B"); array.add("C");
        Iterator iterator = array.iterator();
        String testString = "";
        
        for(String element: array) {
            testString += element;
        }
        
        assertEquals("ABC", testString);
    }
    
    @Test
    public void iteratorTestEnhancedForLoopFullSyntax() {
        array.add("A"); array.add("B"); array.add("C");
        Iterator iterator;
        String testString = "";

        for(iterator = array.iterator(); iterator.hasNext();) {
            testString += iterator.next();
        }
        
        assertEquals("ABC", testString);
    }

    @Test
    public void iterator_hasNextTestHasMoreElementsReturnsTrue() {
        array.add("A"); array.add("B");
        Iterator iterator = array.iterator();
        iterator.next();

        boolean actual = iterator.hasNext();
    
        assertTrue(actual);
    }   
    
    @Test
    public void iterator_hasNextTestNoMoreElementsReturnsFalse() {
        array.add("A");
        Iterator iterator = array.iterator();
        iterator.next();

        boolean actual = iterator.hasNext();

        assertFalse(actual);
    }
    
    @Test
    public void iterator_nextTestCalledTwiceReturnsBothElements() {
        array.add("A"); array.add("B");
        Iterator iterator = array.iterator();
        
        Object firstElementReturned = iterator.next();
        Object secondElementReturned = iterator.next();

        assertEquals("A", firstElementReturned);
        assertEquals("B", secondElementReturned);
    }
    
    @Test
    public void iterator_nextTestEmptyArrayThrowsException() {
        Iterator iterator = array.iterator();
        
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
        Iterator iterator = array.iterator();
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
        Iterator iterator = array.iterator();
        
        iterator.next();
        iterator.remove();
        
        assertEquals(1, array.size());
    }
    
    @Test
    public void iterator_removeTestCalledTwiceWithNextInBetween() {
        array.add("A"); array.add("B");
        Iterator iterator = array.iterator();
        
        iterator.next();
        iterator.remove();
        
        iterator.next();
        iterator.remove();
        
        assertEquals(0, array.size());
    }
    
    @Test
    public void iterator_removeTestNextNotCalledThrowsException() {       
        Iterator iterator = array.iterator();
        
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
        Iterator iterator = array.iterator();
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
    

    @Test
    public void containsAllTestMyArrayHasAllElementsInCollection() {
        array.add("A"); array.add("B"); array.add("C");

        ArrayList<String> testElements
                = new ArrayList<>(Arrays.asList("A", "B"));

        boolean containsElements = array.containsAll(testElements);

        assertTrue(containsElements);
    }

    @Test
    public void containsAllTestMyArrayHasSomeElementsInCollection() {
        array.add("A"); array.add("B");
        ArrayList<String> testElements
                = new ArrayList<>(Arrays.asList("A", "B", "C"));

        boolean containsElements = array.containsAll(testElements);

        assertFalse(containsElements);
    }

    @Test
    public void containsAllTestMyArrayHasNoElementsInCollection() {
        array.add("A"); array.add("B");
        ArrayList<String> testElements
                = new ArrayList<>(Arrays.asList("C", "D"));

        boolean containsElements = array.containsAll(testElements);

        assertFalse(containsElements);
    }

    @Test
    public void containsAllTestCollectionContainsDuplicateElements() {
        array.add("A"); array.add("B");
        ArrayList<String> testElements
                = new ArrayList<>(Arrays.asList("A", "A", "B", "B"));

        boolean containsElements = array.containsAll(testElements);

        assertTrue(containsElements);
    }
    
    @Test
    public void containsAllTestContainsNullCollectionThrowsException() {
        ArrayList<String> testElements = null;

        try {
            array.containsAll(testElements);
            fail();
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is("Cannot pass null "
                    + "reference as an argument"));
        }
    }

    @Test
    public void addAll_Collection_TestAddsElementsBeyondInitialCapacity(){
        MyArrayList<String> arrayInitialCapacity3
                = new MyArrayList<>(3);
        ArrayList<String> elementsToAdd
                = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));

        boolean elementsAdded
                = arrayInitialCapacity3.addAll(elementsToAdd);
        arrayInitialCapacity3.add("Z");

        assertTrue(elementsAdded);
        assertEquals("A", arrayInitialCapacity3.get(0));
        assertEquals("E", arrayInitialCapacity3.get(4));
        assertEquals("Z", arrayInitialCapacity3.get(5));
    }

    @Test
    public void addAll_Collection_TestAddsEmptyCollection() {
        ArrayList<String> emptyArray = new ArrayList<>();

        boolean elementsAdded = array.addAll(emptyArray);

        assertFalse(elementsAdded);
        assertEquals(0, array.size());
    }

    @Test
    public void addAll_Collection_TestAddsNullCollectionThrowsException(){
        ArrayList<String> elementsToAdd = null;

        try {
            array.addAll(elementsToAdd);
            fail();
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is("Cannot pass null "
                    + "reference as an argument"));
        }
    }

    @Test
    public void addAll_IntCollection_TestCollectionAppended() {
        array.add("A");
        ArrayList<String> elementsToAdd
                = new ArrayList<>(Arrays.asList("B", "C", "D"));

        boolean elementsAdded = array.addAll(1, elementsToAdd);

        assertTrue(elementsAdded);
        assertEquals("A", array.get(0));
        assertEquals("D", array.get(3));
    }

    @Test
    public void addAll_IntCollection_TestCollectionInserted() {
        array.add("A"); array.add("B");
        ArrayList<String> elementsToAdd
                = new ArrayList<>(Arrays.asList("X", "Y", "Z"));

        boolean elementsAdded = array.addAll(1, elementsToAdd);

        assertTrue(elementsAdded);
        assertEquals("A", array.get(0));
        assertEquals("Y", array.get(2));
        assertEquals("B", array.get(4));
    }

    @Test
    public void addAll_IntCollection_TestAddsEmptyCollection() {
        ArrayList<String> emptyArray = new ArrayList<>();

        boolean elementsAdded = array.addAll(0, emptyArray);

        assertFalse(elementsAdded);
        assertEquals(0, array.size());
    }

    @Test
    public void addAll_IntCollection_TestAddsNullThrowsException(){
        ArrayList<String> elementsToAdd = null;

        try {
            array.addAll(0, elementsToAdd);
            fail();
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is("Cannot pass null "
                    + "reference as an argument"));
        }
    }

    @Test
    public void removeAllTestMyArrayContainsAllElementsInCollection() {
        array.add("A"); array.add("B");
        ArrayList<String> elementsToRemove
                = new ArrayList<>(Arrays.asList("A", "B"));

        boolean actuallyRemoved = array.removeAll(elementsToRemove);

        assertTrue(actuallyRemoved);
        assertEquals(0, array.size());
    }

    @Test
    public void removeAllTestMyArrayContainsSomeElementsInCollection() {
        array.add("A"); array.add("B");
        ArrayList<String> elementsToRemove
                = new ArrayList<>(Arrays.asList("A", "B", "C"));

        boolean actuallyRemoved = array.removeAll(elementsToRemove);

        assertTrue(actuallyRemoved);
        assertEquals(0, array.size());
    }

    @Test
    public void removeAllTestMyArrayContainsNoElementsInCollection() {
        array.add("A"); array.add("B");
        ArrayList<String> elementsToRemove
                = new ArrayList<>(Arrays.asList("C", "D", "E"));

        boolean actuallyRemoved = array.removeAll(elementsToRemove);

        assertFalse(actuallyRemoved);
        assertEquals(2, array.size());
    }

    @Test
    public void removeAllTestMyArrayContainsDuplicateElements() {
        array.add("A");  array.add("A");
        ArrayList<String> elementsToRemove
                = new ArrayList<>(Arrays.asList("A"));

        boolean actuallyRemoved = array.removeAll(elementsToRemove);

        assertTrue(actuallyRemoved);
        assertEquals(0, array.size());
    }

    @Test
    public void removeAllTestRemovesEmptyCollection() {
        array.add("A"); array.add("A");
        ArrayList<String> elementsToRemove = new ArrayList<>();

        boolean actuallyRemoved = array.removeAll(elementsToRemove);

        assertFalse(actuallyRemoved);
        assertEquals(2, array.size());
    }
    
    @Test
    public void removeAllTestRemovesCollectionWithNullElement() {
        array.add(null); array.add("A");
        ArrayList<String> elementsToRemove
                = new ArrayList<>(Arrays.asList("A", null));

        boolean actuallyRemoved = array.removeAll(elementsToRemove);

        assertTrue(actuallyRemoved);
        assertEquals(0, array.size());
    }

    @Test
    public void removeAllTestRemovesNullCollectionThrowsException() {
        ArrayList<String> elementsToRemove = null;

        try {
            array.removeAll(elementsToRemove);
            fail();
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is("Cannot pass null "
                    + "reference as an argument"));
        }
    }

    @Test
    public void retainAllTestMyArrayContainsAllElementsInCollection() {
        array.add("A"); array.add("B");
        ArrayList<String> elementsToRetain
                = new ArrayList<>(Arrays.asList("A", "B"));

        boolean arrayChanged = array.retainAll(elementsToRetain);

        assertFalse(arrayChanged);
        assertEquals(2, array.size());
    }

    @Test
    public void retainAllTestCollectionContainsSomeElementsInMyArray() {
        array.add("A"); array.add("B"); array.add("C");
        ArrayList<String> elementsToRetain
                = new ArrayList<>(Arrays.asList("A", "C"));

        boolean arrayChanged = array.retainAll(elementsToRetain);

        assertTrue(arrayChanged);
        assertEquals(2, array.size());
        assertEquals("C", array.get(1));
    }

    @Test
    public void retainAllTestMyArrayContainsNoElementsInCollection() {
        array.add("A"); array.add("B");
        ArrayList<String> elementsToRetain
                = new ArrayList<>(Arrays.asList("C", "D"));

        boolean arrayChanged = array.retainAll(elementsToRetain);

        assertTrue(arrayChanged);
        assertEquals(0, array.size());
    }

    @Test
    public void retainAllTestMyArrayContainsDuplicateElements() {
        array.add("A"); array.add("A");
        ArrayList<String> elementsToRetain
                = new ArrayList<>(Arrays.asList("B"));

        boolean arrayChanged = array.retainAll(elementsToRetain);

        assertTrue(arrayChanged);
        assertEquals(0, array.size());
    }

    @Test
    public void retainAllTestRetainsEmptyCollection() {
        array.add("A");  array.add("A");
        ArrayList<String> elementsToRetain = new ArrayList<>();

        boolean arrayChanged = array.retainAll(elementsToRetain);

        assertTrue(arrayChanged);
        assertEquals(0, array.size());
    }

    @Test
    public void retainAllTestRetainsNullCollectionThrowsException() {
        ArrayList<String> elementsToRetain = null;

        try {
            array.retainAll(elementsToRetain);
            fail();
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is("Cannot pass null "
                    + "reference as an argument"));
        }
    }
    
    @Test
    public void indexOfTestPassesNullObjectFindsFirstNullInArray() {
        array.add("A"); array.add(null);
        
        int index = array.indexOf(null);

        assertEquals(1, index); 
    }
}
