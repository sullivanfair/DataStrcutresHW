package edu.iastate.cs228.hw3;

/**
 * @author Sullivan Fair
 */

import java.util.AbstractSequentialList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  @Override
  public int size()
  {
    return size;
  }
  
  @Override
  public boolean add(E item)
  {
	if(item == null)
	{
		throw new NullPointerException();
	}
	
    if(size == 0)
    {
    	Node newNode = new Node();
    	newNode.addItem(item);
    	head.next = newNode;
    	newNode.previous = head;
    	newNode.next = tail;
    	tail.previous = newNode;
    }
    else
    {
    	if(tail.previous.count < nodeSize)
    	{
    		tail.previous.addItem(item);
    	}
    	else
    	{
    		Node newNode = new Node();
    		newNode.addItem(item);
    		Node temp = tail.previous;
    		temp.next = newNode;
    		newNode.previous = temp;
    		newNode.next = tail;
    		tail.previous = newNode;
    	}
    }
    
    size++;
    return true;
  }

  @Override
  public void add(int pos, E item)
  {
    if(pos > size || pos < 0)
    {
    	throw new IndexOutOfBoundsException();
    }
    
    //add case 1
    if(head.next == tail)
    {
    	add(item);
    }
    
    NodeInfo nodeInfo = findNodeInfo(pos);
    Node temp = nodeInfo.node;
    int offset = nodeInfo.offset;
    
    //add case 2
    if(offset == 0)
    {
    	if(temp.previous.count < nodeSize && temp.previous != head)
    	{
    		temp.previous.addItem(item);
    		size++;
    		return;
    	}
    	else if(temp == tail)
    	{
    		add(item);
    		size++;
    		return;
    	}
    }
    
    //add case 3
    if(temp.count < nodeSize)
    {
    	temp.addItem(offset, item);
    }
    //add case 4
    else
    {
    	Node newNext = new Node();
    	int midPoint = nodeSize / 2;
    	int count = 0;
    	
    	while(count < midPoint)
    	{
    		newNext.addItem(temp.data[midPoint]);
    		temp.removeItem(midPoint);
    		count++;
    	}
    	
    	Node oldNext = temp.next;
    	temp.next = newNext;
    	newNext.previous = temp;
    	newNext.next = oldNext;
    	oldNext.previous = newNext;
    	
    	if(offset <= midPoint) 
    	{
    		temp.addItem(offset, item);
    	}
    	else if(offset > midPoint)
    	{
    		newNext.addItem((offset - midPoint), item);
    	}
    }
    
    size++;
  }

  @Override
  public E remove(int pos)
  {
    if(pos < 0 || pos > size)
    {
    	throw new IndexOutOfBoundsException();
    }
    
    NodeInfo nodeInfo = findNodeInfo(pos);
    Node temp = nodeInfo.node;
    int offset = nodeInfo.offset;
    E nodeVal = temp.data[offset];
    int midPoint = nodeSize / 2;
    
    if(temp.next == tail && temp.count == 1)
    {
    	Node prev = temp.previous;
    	prev.next = temp.next;
    	temp.next.previous = prev;
    	temp = null;
    }
    else if(temp.next == tail || temp.count > midPoint)
    {
    	temp.removeItem(offset);
    }
    else
    {
    	temp.removeItem(offset);
    	Node nxt = temp.next;
    	
    	if(nxt.count > midPoint)
    	{
    		temp.addItem(nxt.data[0]);
    		nxt.removeItem(0);
    	}
    	else if(nxt.count <= midPoint)
    	{
    		for(int i = 0; i < nxt.count; i++)
    		{
    			temp.addItem(nxt.data[i]);
    		}
    		
    		temp.next = nxt.next;
    		nxt.next.previous = temp;
    		nxt = null;
    	}
    }
    
    size--;
    return nodeVal;
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
	  E[] sortList = (E[]) new Comparable[size];
	  
	  int index = 0;
	  Node temp = head.next;
	  
	  while(temp != tail)
	  {
		  for(int i = 0; i < temp.count; i++)
		  {
			  sortList[index] = temp.data[i];
			  index++;
		  }
		  
		  temp = temp.next;
	  }
	  
	  head.next = tail;
	  tail.previous = head;
	  insertionSort(sortList, new ElementComparator());
	  size = 0;
	  
	  for(int i = 0; i < sortList.length; i++)
	  {
		  add(sortList[i]);
	  }
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
	  E[] revSortList = (E[]) new Comparable[size];
	  int index = 0;
	  Node temp = head.next;
	  
	  while(temp != tail)
	  {
		  for(int i = 0; i < temp.count; i++)
		  {
			  revSortList[index] = temp.data[i];
			  index++;
		  }
		  
		  temp = temp.next;
	  }
	  
	  head.next = tail;
	  tail.previous = head;
	  bubbleSort(revSortList);
	  size = 0;
	  
	  for(int i = 0; i < revSortList.length; i++) 
	  {
		  add(revSortList[i]);
	  }
  }
  
  @Override
  public Iterator<E> iterator()
  {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
    return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
  
  /**
   * 
   *  Used for gaining information of a node
   *
   */
  private class NodeInfo 
  {
	public Node node;
	public int offset;

	public NodeInfo(Node node, int offset) 
	{
		this.node = node;
		this.offset = offset;
	}
  }
 
  /**
   * 
   * @param pos location of item that needs info
   * @return NodeInfo from point in list
   */
  private NodeInfo findNodeInfo(int pos) 
  {
	Node temp = head.next;
	int curPos = 0;
	while (temp != tail) 
	{
		if (curPos + temp.count <= pos) 
		{
			curPos += temp.count;
			temp = temp.next;
			continue;
		}

		NodeInfo nodeInfo = new NodeInfo(temp, pos - curPos);
		return nodeInfo;

	}
	
	return null;
  }
  
  private class StoutListIterator implements ListIterator<E>
  {
	private static final int BEHIND = -1;
	private static final int AHEAD = 1;
	private static final int NONE = 0;
	private int index;
	private E[] data;
	private int direction;
	private boolean atStart = false;
	  
    /**
     * Default constructor 
     */
    public StoutListIterator()
    {
    	this(0); 
    }

    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos)
    {
    	if(pos < 0 || pos > size)
    	{
    		throw new IndexOutOfBoundsException();
    	}
   
    	index = pos;
    	direction = NONE;
    	getData();
    }

    @Override
    public boolean hasNext()
    {
    	if(index >= size)
    	{
    		return false;
    	}
    	else 
    	{
    		return true;
    	}
    }
    
    @Override
    public boolean hasPrevious()
    {
    	if(index <= 0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }

    @Override
    public E next()
    {
    	if(!hasNext())
    	{
    		throw new NoSuchElementException();
    	}
    	
    	direction = BEHIND;
    	return data[index++];
    }
    
    @Override
    public E previous()
    {
    	if(!hasPrevious())
    	{
    		throw new NoSuchElementException();
    	}
    	
    	index--;
    	direction = AHEAD;
    	return data[index];
    }
    
    @Override
    public int nextIndex()
    {
    	return index;
    }

    @Override
    public int previousIndex()
    {
    	return index - 1;
    }
    
    @Override
    public void add(E item)
    {
    	if(item == null)
    	{
    		throw new NullPointerException();
    	}
    	
    	StoutList.this.add(index, item);
    	index++;
    	getData();
    	direction = NONE;
    }
    
    @Override
    public void remove()
    {
    	if(direction == NONE)
    	{
    		throw new IllegalStateException();
    	}
    	if(direction == AHEAD)
    	{
    		StoutList.this.remove(index);
    		getData();
    		index++;
    		
    		if(index != 0)
    		{
    			index--;
    		}
    		else
    		{
    			index = 0;
    		}
    	}
    	else
    	{
    		StoutList.this.remove(index - 1);
    		getData();
    		index--;
    	}
    	
    	direction = NONE;
    }
    
    @Override
    public void set(E item)
    {	
    	if(direction == NONE)
    	{
    		throw new IllegalStateException();
    	}
    	else if(direction == AHEAD)
    	{	
    		if(index <= 0)
    		{
    			index = 1;
    			atStart = true;
    		}
    		
    		NodeInfo nodeInfo = findNodeInfo(index - 1);
    		nodeInfo.node.data[nodeInfo.offset] = item;  		
    		data[index - 1] = item;
    		
    		if(atStart)
    		{
    			index = 0;
    			atStart = false;
    		}
    	}
    	else
    	{
    		NodeInfo nodeInfo = findNodeInfo(index - 1);
    		nodeInfo.node.data[nodeInfo.offset] = item;
    		data[index - 1] = item;
    	}
    }
    
    private void getData()
    {
    	data = (E[]) new Comparable[size];
    	int index = 0;
    	Node temp = head.next;
    	
    	while(temp != tail)
    	{
    		for(int i = 0; i < temp.count; i++)
    		{
    			data[index] = temp.data[i];
    			index++;
    		}
    		
    		temp = temp.next;
    	}
    }
  }
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  for(int i = 1; i < arr.length; i++)
	  {
		  E key = arr[i];
		  int j = i - 1;
		  
		  while(j >= 0 && comp.compare(arr[j], key) > 0)
		  {
			  arr[j + 1] = arr[j];
			  j--;
		  }
		  
		  arr[j + 1] = key;
	  }
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
	  int len = arr.length;
	  
	  for(int i = 0; i < len - 1; i++)
	  {
		  for(int j = 0; j < (len - i - 1); j++)
		  {
			  if(arr[j].compareTo(arr[j + 1]) < 0)
			  {
				  E temp = arr[j];
				  arr[j] = arr[j + 1];
				  arr[j + 1] = temp;
			  }
		  }
	  }
  }
  
  private class ElementComparator<E extends Comparable<E>> implements Comparator<E>
  {
	  @Override
	  public int compare(E lhs, E rhs)
	  {
		  return lhs.compareTo(rhs);
	  }
  }
}