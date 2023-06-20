package edu.iastate.cs228.hw3;

import edu.iastate.cs228.hw3.StoutList;

import org.junit.Before;

import org.junit.Test;

import java.util.Arrays;

import java.util.ListIterator;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class StoutListTest2 {

    private StoutList<Integer> testList;

    private ListIterator<Integer> testListIterator;

    

    @Before

    public void setUp() {

        testList = new StoutList<Integer>();

        testList.add(1);

        testList.add(2);

        testList.add(3);

        testListIterator = testList.listIterator();

    }

    @Test

    public void testHasNext() {

        assertTrue(testListIterator.hasNext());

        testListIterator.next();

        assertTrue(testListIterator.hasNext());

        testListIterator.next();

        assertTrue(testListIterator.hasNext());

        testListIterator.next();

        assertFalse(testListIterator.hasNext());

    }

    @Test

    public void testNext() {

        assertEquals(new Integer(1), testListIterator.next());

        assertEquals(new Integer(2), testListIterator.next());

        assertEquals(new Integer(3), testListIterator.next());

    }

    @Test

    public void testHasPrevious() {

        assertFalse(testListIterator.hasPrevious());

        testListIterator.next();

        assertTrue(testListIterator.hasPrevious());

        testListIterator.next();

        assertTrue(testListIterator.hasPrevious());

        testListIterator.next();

        assertTrue(testListIterator.hasPrevious());

        testListIterator.previous();

        assertTrue(testListIterator.hasPrevious());

        testListIterator.previous();

        assertTrue(testListIterator.hasPrevious());

        testListIterator.previous();

        assertFalse(testListIterator.hasPrevious());

    }

    @Test

    public void testPrevious() {

        testListIterator.next();

        testListIterator.next();

        testListIterator.next();

        assertEquals(new Integer(3), testListIterator.previous());

        assertEquals(new Integer(2), testListIterator.previous());

        assertEquals(new Integer(1), testListIterator.previous());

    }

    @Test

    public void testNextIndex() {

        assertEquals(0, testListIterator.nextIndex());

        testListIterator.next();

        assertEquals(1, testListIterator.nextIndex());

        testListIterator.next();

        assertEquals(2, testListIterator.nextIndex());

        testListIterator.next();

        assertEquals(3, testListIterator.nextIndex());

    }

    @Test

    public void testPreviousIndex() {

        assertEquals(-1, testListIterator.previousIndex());

        testListIterator.next();

        assertEquals(0, testListIterator.previousIndex());

        testListIterator.next();

        assertEquals(1, testListIterator.previousIndex());

        testListIterator.next();

        assertEquals(2, testListIterator.previousIndex());

    }

    @Test

    public void testRemove() {

        testListIterator.next();

        testListIterator.remove();

        assertEquals(Arrays.asList(2, 3), testList);

    }

    @Test

    public void testSet() {

        testListIterator.next();

        testListIterator.set(4);

        assertEquals(Arrays.asList(4, 2, 3), testList);

    }

    @Test

    public void testAdd() {

        testListIterator.next();

        testListIterator.add(4);

        assertEquals(Arrays.asList(1, 4, 2, 3), testList);

    }

    

    @Test

    public void testHasNext_EmptyList() {

        StoutList<Integer> emptyList = new StoutList<Integer>();

        ListIterator<Integer> emptyListIterator = emptyList.listIterator();

        assertFalse(emptyListIterator.hasNext());

    }

    @Test

    public void testHasNext_EndOfList() {

        while(testListIterator.hasNext()) {

            testListIterator.next();

        }

        assertFalse(testListIterator.hasNext());

    }

    @Test

    public void testHasPrevious_BeginningOfList() {

        assertFalse(testListIterator.hasPrevious());

    }

    @Test

    public void testPrevious_EndOfList() {

        while(testListIterator.hasNext()) {

            testListIterator.next();

        }

        assertEquals(new Integer(3), testListIterator.previous());

    }

    @Test

    public void testNextIndex_EndOfList() {

        while(testListIterator.hasNext()) {

            testListIterator.next();

        }

        assertEquals(3, testListIterator.nextIndex());

    }

    @Test

    public void testPreviousIndex_BeginningOfList() {

        assertFalse(testListIterator.hasPrevious());

        assertEquals(-1, testListIterator.previousIndex());

    }

    @Test(expected=NoSuchElementException.class)

    public void testNext_EmptyList() {

        StoutList<Integer> emptyList = new StoutList<Integer>();

        ListIterator<Integer> emptyListIterator = emptyList.listIterator();

        emptyListIterator.next();

    }

    @Test(expected=NoSuchElementException.class)

    public void testPrevious_BeginningOfList() {

        assertFalse(testListIterator.hasPrevious());

        testListIterator.previous();

    }

    @Test(expected=IllegalStateException.class)

    public void testRemove_ConsecutiveCalls() {

        testListIterator.next();

        testListIterator.remove();

        testListIterator.remove();

    }

//    @Test(expected=IllegalStateException.class)
//
//    public void testSet_ConsecutiveCalls() {
//
//        testListIterator.next();
//
//        testListIterator.set(4);
//
//        testListIterator.set(5);
//
//    }

//    @Test(expected=IllegalStateException.class)
//
//    public void testAdd_ConsecutiveCalls() {
//
//        testListIterator.next();
//
//        testListIterator.add(4);
//
//        testListIterator.add(5);
//
//    }

}
