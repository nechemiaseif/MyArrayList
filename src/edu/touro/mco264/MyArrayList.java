package edu.touro.mco264;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements List<T> {

    private T[] backingStore;
    private int insertionPoint;

    public MyArrayList() {
        this(5);
    }

    public MyArrayList(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException
                    (String.format("Capacity cannot be negative. "
                     + "Capacity was: [%d]", capacity));
        backingStore = (T[]) new Object[5];
    }
    
    @Override
    public void add(int index, T element) {
        checkWithinValidRange(index, size());
        ensureCapacity(1);

        System.arraycopy(backingStore, index, backingStore, index + 1,
                         size() - index);

        backingStore[index] = element;

        insertionPoint++;
    }

    @Override
    public boolean add(T element) {
        ensureCapacity(1);
        backingStore[insertionPoint++] = element;
        return true;
    }
    
    @Override
    public boolean addAll(Collection<? extends T> collection) {
        return addAll(insertionPoint, collection);
    }   

    @Override
    public boolean addAll(int index,
            Collection<? extends T> collection) {
        
        invalidateIfNull(collection);
        checkWithinValidRange(index, size());
        ensureCapacity(collection.size());
        
        Object[] collectionAsArray = collection.toArray();
        
        int elementsMoved = size() - index;
        if (elementsMoved > 0) {
            System.arraycopy(backingStore, index, 
                             backingStore, index + collection.size(),
                             elementsMoved);
        }
        System.arraycopy(collectionAsArray, 0,
                         backingStore, index,
                         collection.size());
        
        insertionPoint+=collection.size();

        return !collection.isEmpty();
    }

    private void ensureCapacity(int incomingElements) {
        if (insertionPoint + incomingElements > backingStore.length) {
            T temp[]
                    = (T[]) new Object[backingStore.length * 2 
                                 + incomingElements];
            System.arraycopy(backingStore, 0, temp, 0, 
                             backingStore.length);
            backingStore = temp;
        }
    }
    
    @Override
    public T remove(int index) {
        checkWithinValidRange(index, size() - 1);

        T originalValue = backingStore[index];

        quickRemove(index);

        return originalValue;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index >= 0) {
            quickRemove(index);
            return true;
        }
        return false;
    }

    private void quickRemove(int index) {
        int elementsToShift = size() - index - 1;

        if (elementsToShift > 0) {
            System.arraycopy(backingStore, index + 1, backingStore,
                             index, elementsToShift);
        }
        backingStore[--insertionPoint] = null;
    }
    
    @Override
    public boolean removeAll(Collection<?> collection) { 
        boolean containedInCollection = true;    
        
        return removeOnCondition(collection, containedInCollection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean containedInCollection = true;      
        
        return removeOnCondition(collection, !containedInCollection);
    }
    
    private boolean removeOnCondition(Collection<?> collection, 
                                            boolean condition){        
        invalidateIfNull(collection);
        
        int originalSize = size();
        
        for (int index = 0; index < size(); index++) {
            if (collection.contains(backingStore[index]) == condition) {
                remove(backingStore[index--]);
            }
        }
        return size() != originalSize;
    }
    
    @Override
    public T get(int index) {
        checkWithinValidRange(index, size() - 1);
        return backingStore[index];
    }

    @Override
    public T set(int index, T element) {
        checkWithinValidRange(index, size() - 1);

        T originalValue = backingStore[index];

        backingStore[index] = element;

        return originalValue;
    }

    @Override
    public void clear() {
        for (int index = 0; index < size(); index++) {
            backingStore[index] = null;
        }
        insertionPoint = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int index = 0; index < size(); index++) {
            if (o == null ? get(index) == null : o.equals(get(index))) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int index = size() - 1; index >= 0; index--) {
            if (backingStore[index].equals(o)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        invalidateIfNull(collection);

        for (Object element : collection) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int size() {
        return insertionPoint;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private void checkWithinValidRange(int index, int maximum) {
        if (index < 0 || index > maximum) {
            throw new IndexOutOfBoundsException(
                    String.format("index = [%d]; maximum is [%d]",
                            index, maximum));
        }
    }
    
    private void invalidateIfNull(Object o) {
        if (o == null) {
            throw new NullPointerException("Cannot pass null "
                    + "reference as an argument");
        }
    }
    
    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }
    
    private class MyArrayListIterator implements Iterator<T> {
       
        int boundaryBeforeElement;
        boolean nextHasJustBeenCalled;

        public MyArrayListIterator() {
            boundaryBeforeElement = 0;
            nextHasJustBeenCalled = false;
        }

        @Override
        public boolean hasNext() {
            return boundaryBeforeElement != size();
        }

        @Override
        public T next() {
            
            if (boundaryBeforeElement >= size()){
                throw new NoSuchElementException("The iteration "
                        + "has no more elements");
            } 
            nextHasJustBeenCalled = true;
            return backingStore[boundaryBeforeElement++];
        }
        
        @Override
        public void remove() {
         
            if (!nextHasJustBeenCalled) {
                throw new IllegalStateException("Next has not been "
                        + "called directly before call to remove");
            }   
   
            MyArrayList.this.remove(--boundaryBeforeElement);
            nextHasJustBeenCalled = false;       
        }
    }
    
    @Override
    public Object[] toArray() {
        T[] backingStoreCopy = (T[]) new Object[size()]; 
        
        System.arraycopy(backingStore, 0, backingStoreCopy, 0, size()); 
        
        return backingStoreCopy;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        invalidateIfNull(array);
        
        if (array.length < size()) {
            array = (T[]) Array.newInstance
                (array.getClass().getComponentType(), size());
        }

        System.arraycopy(backingStore, 0, array, 0, size());
        
        if(array.length > size()) {
            array[size()] = null;
        }
        
        return array;
    }
    
    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}