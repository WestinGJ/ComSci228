package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
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
  public boolean add(E item){
	  Node tempNode;
		// Checks NullPointerException()
		if (item == null) {
			throw new NullPointerException();
		}
		// Checks if list is empty and creates a new node
		if (head.next == tail) {
			tempNode = new Node();
			head.next = tempNode;
			tail.previous = tempNode;
			tempNode.next = tail;
			tempNode.previous = head;
			tempNode.addItem(item);
		}
		// Checks is the current node is open and fills it
		else if (tail.previous.count < nodeSize) {
			tail.previous.addItem(item);
		}
		// Else creates a new node and insert it in the first spot
		else {
			tempNode = new Node();
			tempNode.previous = tail.previous;
			tempNode.next = tail;
			tempNode.previous.next = tempNode;
			tail.previous = tempNode;
			tempNode.addItem(item);
		}
		// Increases the size of the list
		size++;
		return true;
  }

  @Override
  public void add(int pos, E item)
  {
	  Node curNode;
		Node tempNode;
		int offset;
		int nodeIndex;
		//Checks IndexOutOfBoundsException()
		if (pos < 0) {
			throw new IndexOutOfBoundsException();
		}
		//Checks NullPointerException()
		if (item == null) {
			throw new NullPointerException();
		}
		//Checks if the index is at the end of the list, creates new node and inserts item to first spot
		if (pos == size) {
			add(item);
		} 
		//Else...
		else {
			offset = findOffset(pos);
			nodeIndex = findNodeIndex(pos);
			curNode = head;
			for (int i = 0; i < nodeIndex; i++) {
				curNode = curNode.next;
			}
			//Checks if offset = 0 and previous node isn't full, inserts item into node
			if (offset == 0 && curNode.previous.count < nodeSize && nodeIndex != 1) {

				curNode.previous.addItem(item);
			}
			//Checks if node isn't full at pos, inserts item at designated offset
			else if (curNode.count < nodeSize) {
				curNode.addItem(offset, item);
			}
			//Checks if there isn't space in the current node, adds new node and inserts second half of current
			// node to the new node
			else {
				tempNode = new Node();
				tempNode.next = curNode.next;
				tempNode.previous = curNode;
				tempNode.previous.next = tempNode;
				tempNode.next.previous = tempNode;
				for (int i = 0; i < nodeSize / 2; i++) {
					tempNode.addItem(curNode.data[nodeSize / 2]);
					curNode.removeItem(nodeSize / 2);
				}
				if (offset <= nodeSize / 2) {
					curNode.addItem(offset, item);
				} else {
					tempNode.addItem((offset - (nodeSize / 2)), item);
				}
			}
			//Increases size of the list
			size++;
		}

  }

  @Override
  public E remove(int pos)
  {
	  NodeInfo nodeInfo = find(pos);
		Node tempNode;
		E item;
		if (pos >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if (nodeInfo.node.count == 1) {
			item = nodeInfo.node.data[nodeInfo.offset];
			tempNode = nodeInfo.node.previous;
			nodeInfo.node = nodeInfo.node.next;
			tempNode.next = nodeInfo.node;
			nodeInfo.node.previous = tempNode;
			return item;
		}
		if ((nodeInfo.node.next == tail && nodeInfo.node.previous == head) || (nodeInfo.node.count > (nodeSize / 2))) {
			item = nodeInfo.node.data[nodeInfo.offset];
			nodeInfo.node.removeItem(nodeInfo.offset);
			size--;
			return item;
		}
		if (nodeInfo.node.count <= (nodeSize / 2)) {
			Node node = nodeInfo.node.next;
			if (node == null || node == tail) {
				item = nodeInfo.node.data[nodeInfo.offset];
				nodeInfo.node.removeItem(nodeInfo.offset);
				size--;
				return item;
			}
			if (node.count > (nodeSize / 2)) {
				item = nodeInfo.node.data[nodeInfo.offset];
				nodeInfo.node.removeItem(nodeInfo.offset);
				nodeInfo.node.addItem(node.data[0]);
				node.removeItem(0);
				size--;
				return item;
			}
			if (node.count <= (nodeSize / 2)) {
				item = nodeInfo.node.data[nodeInfo.offset];
				nodeInfo.node.removeItem(nodeInfo.offset);
				size--;
				while (node.count > 0) {
					nodeInfo.node.addItem(node.data[0]);
					node.removeItem(0);
				}
				node = node.next;
				nodeInfo.node.next = node;
				node.previous = nodeInfo.node;
				return item;
			}
		}
		return null;
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
	  Comparator<E> comp = new Comparator<E>() {
			@Override
			public int compare(E o1, E o2) {
				return o1.compareTo(o2);
			}
		};
		Node curNode;
		Node tempNode;
		E[] arr = (E[]) new Comparable[size()];
		for (int i = 0; i < size(); i++) {
			arr[i] = get(i);
		}
		for (int k = 0; k < size(); k++) {
			remove(0);
		}
		insertionSort(arr, comp);
		head.next = tail;
		tail.next = head;
		curNode = new Node();
		curNode.previous = head;
		curNode.next = tail;
		size = 0;
		for (int j = 0; j < arr.length; j++) {
			if (curNode.count == nodeSize) {
				tempNode = new Node();
				tempNode.previous = tempNode.previous.next;
				tempNode.next = tail;
			}
			add(arr[j]);
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
	  E[] arr = (E[]) new Comparable[size()];
		Node curNode;
		Node tempNode;
		int sizeToUse = size();
		for (int i = 0; i < sizeToUse; i++) {
			arr[i] = this.get(i);
		}
		for (int j = 0; j < sizeToUse; j++) {
			remove(0);
		}
		bubbleSort(arr);
		curNode = new Node();
		curNode.previous = head;
		curNode.next = tail;
		size = 0;
		for (int k = 0; k < arr.length; k++) {
			if (curNode.count == nodeSize) {
				tempNode = new Node();
				tempNode.previous = tempNode.previous.next;
				tempNode.next = tail;
			}
			add(arr[k]);
		}
  }
  
  @Override
	public Iterator<E> iterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
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
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
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
      //useful for debugging 
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
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
 
  private class StoutListIterator implements ListIterator<E>
  {
	// constants you possibly use ...   
	  
	// instance variables ... 
	  private Node curNode;
		private int index;
		private int prevIndex;
		private int offset;
		private int prevOffset;
		private Node removeCurNode;
		private int removeOffset;
    /**
     * Default constructor 
     */
    public StoutListIterator()
    {
    	curNode = head.next;
		index = 0;
		offset = 0;
		prevIndex = -1;
		prevOffset = -1;

    }

    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos)
    {
    	NodeInfo nodeInfo;
		Node tempNode;
		nodeInfo = find(pos);
		this.curNode = nodeInfo.node;
		this.offset = nodeInfo.offset;
		index = pos;
		prevIndex = index - 1;
		if (offset > 0) {
			prevOffset = offset - 1;
		} else {
			if (curNode.previous != head) {
				tempNode = curNode.previous;
				prevOffset = tempNode.count - 1;
			}
		}
    }

    @Override
    public boolean hasNext()
    {
    	Node tempNode;
		if (offset < curNode.count && curNode.data[offset] != null) {
			return true;
		} else if (curNode.next != null || curNode != tail) {
			tempNode = curNode.next;
			if (tempNode.count > 0) {
				return true;
			}
		}
		return false;
    }

    @Override
    public E next()
    {
    	if (!hasNext()) {
			throw new NoSuchElementException();
		}
		if (index > size()) {
//if there is a bug in code
			throw new RuntimeException();
		}
		if (curNode.count <= offset) {
			curNode = curNode.next;
			removeCurNode = curNode;
			offset = 0;
			prevOffset = 0;
			removeOffset = 0;
			prevIndex = index;
			index++;
			return curNode.data[offset++];
		}
		if (curNode.count > offset) {
			removeCurNode = curNode;
			prevIndex = index;
			index++;
			prevOffset = offset;
			removeOffset = offset;
			return curNode.data[offset++];
		}
		return null;
    }

    @Override
    public void remove()
    {
    	if (prevIndex >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if (removeCurNode == null) {
			throw new IllegalStateException();

		}
		Node tempNode;
		if (removeCurNode.count == 1) {
			tempNode = removeCurNode.previous;
			removeCurNode = removeCurNode.next;
			tempNode.next = removeCurNode;
			removeCurNode.previous = tempNode;
			if (removeOffset <= offset) {
				offset--;
				prevOffset--;
				index--;
				prevIndex--;
			}
			size--;
			removeCurNode = null;
			return;
		}
		if ((removeCurNode.next == tail && removeCurNode.previous == head)
				|| (removeCurNode.count > (nodeSize / 2))) {
			removeCurNode.removeItem(removeOffset);
			if (removeOffset < offset && offset > 0) {
				offset--;
				prevOffset--;
				index--;
				prevIndex--;
			}
			size--;
			removeCurNode = null;
			return;
		}
		if (removeCurNode.count <= (nodeSize / 2)) {
			tempNode = removeCurNode.next;
			if (tempNode == null || tempNode == tail) {
				removeCurNode.removeItem(removeOffset);
				if (removeOffset < offset && offset > 0) {
					offset--;
					prevOffset--;
					index--;
					prevIndex--;
				}
				size--;
				removeCurNode = null;
				return;
			}
			if (tempNode.count > (nodeSize / 2)) {
				removeCurNode.removeItem(removeOffset);
				removeCurNode.addItem(tempNode.data[0]);
				tempNode.removeItem(0);
				if (removeOffset < offset && offset > 0) {
					offset--;
					prevOffset--;
					index--;
					prevIndex--;
				}
				size--;
				removeCurNode = null;
				return;
			}
			if (tempNode.count <= (nodeSize / 2)) {
				removeCurNode.removeItem(removeOffset);
				while (tempNode.count > 0) {
					removeCurNode.addItem(tempNode.data[0]);
					tempNode.removeItem(0);
				}
				tempNode = tempNode.next;
				removeCurNode.next = tempNode;
				tempNode.previous = removeCurNode;
				if (removeOffset < offset && offset > 0) {
					offset--;
					prevOffset--;
					index--;
					prevIndex--;
				}
				size--;
				removeCurNode = null;
				return;
			}
		}
    }

	@Override
	public boolean hasPrevious() {
		if (prevOffset < 0) {
			return false;
		}
		if (prevOffset < curNode.count && curNode.data[prevOffset] != null) {
			return true;
		} else if (curNode.previous != null || curNode != tail) {
			Node tempNode = curNode.previous;
			if (tempNode.count > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public E previous() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		}
		if (prevIndex < 0 || prevIndex > size()) {
			throw new RuntimeException();
		}
		if (prevOffset < 0 || prevOffset > offset) {
			prevIndex--;
			index--;
			curNode = curNode.previous;
			prevOffset = curNode.count - 1;
			offset = curNode.count - 1;
			removeCurNode = curNode;
			removeOffset = prevOffset;
			return curNode.data[prevOffset--];
		}
		if (prevOffset <= offset) {
			prevIndex--;
			index--;
			offset--;
			removeCurNode = curNode;
			removeOffset = prevOffset;
			return curNode.data[prevOffset--];
		}
		return null;
	}

	@Override
	public int nextIndex() {
		return index;
	}

	@Override
	public int previousIndex() {
		return prevIndex;
	}

	@Override
	public void set(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (removeCurNode == null) {
			throw new IllegalStateException();
		}
		removeCurNode.removeItem(removeOffset);
		removeCurNode.addItem(removeOffset, e);
		
	}

	@Override
	public void add(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (index > size()) {
			throw new IllegalArgumentException();
		}
		if (size() == 0) {
			Node curNode = head;
			Node tempNode = new Node();
			curNode.next = tempNode;
			tempNode.previous = curNode;
			tempNode.next = tail;
			tail.previous = tempNode;
			tempNode.addItem(index, e);
			prevIndex = index;
			index++;
			prevOffset = offset;
			offset++;
			size++;
			removeCurNode = null;
			return;
		}
		NodeInfo nodeInfo = find(index);
		if (nodeInfo.offset == 0) {
			if (nodeInfo.node.previous.count != nodeSize && nodeInfo.node.previous != head) {
				nodeInfo.node = nodeInfo.node.previous;
				nodeInfo.node.addItem(e);
				prevIndex = index;
				index++;
				offset = nodeInfo.node.count;
				prevOffset = offset - 1;
				curNode = nodeInfo.node;
				size++;
				removeCurNode = null;
				return;
			}
			if (nodeInfo.node == tail && nodeInfo.node.previous.count == nodeSize) {
				Node tempNode = new Node();
				Node lastNode = nodeInfo.node.previous;
				tempNode.previous = lastNode;
				tempNode.next = nodeInfo.node;
				nodeInfo.node.previous = tempNode;
				lastNode.next = tempNode;
				tempNode.addItem(e);
				prevIndex = index;
				index++;
				offset = nodeInfo.node.count;
				prevOffset = offset - 1;
				curNode = tempNode;
				size++;
				removeCurNode = null;
				return;
			}
		}
		if (nodeInfo.node.count < nodeSize) {
			nodeInfo.node.addItem(nodeInfo.offset, e);
			prevIndex = index;
			index++;
			prevOffset = offset;
			offset++;
			size++;
			removeCurNode = null;
			return;
		}
		if (nodeInfo.node.count >= nodeSize) {
			Node tempNode = new Node();
			Node nextNode = nodeInfo.node.next;
			tempNode.next = nextNode;
			nodeInfo.node.next = tempNode;
			tempNode.previous = nodeInfo.node;
			nextNode.previous = tempNode;
			while (tempNode.count != (nodeSize / 2)) {
				tempNode.addItem(nodeInfo.node.data[nodeSize / 2]);
				nodeInfo.node.removeItem(nodeSize / 2);
			}
			if (nodeInfo.offset <= (nodeSize / 2)) {
				nodeInfo.node.addItem(nodeInfo.offset, e);
				prevIndex = index;
				index++;
				prevOffset = nodeInfo.offset;
				offset = nodeInfo.offset + 1;
				curNode = nodeInfo.node;
				size++;
				removeCurNode = null;
				return;
			}
			if (nodeInfo.offset > (nodeSize / 2)) {
				tempNode.addItem(nodeInfo.offset - (nodeSize / 2), e);
				prevIndex = index;
				index++;
				prevOffset = nodeInfo.offset - (nodeSize / 2);
				offset = nodeInfo.offset - (nodeSize / 2) + 1;
				curNode = tempNode;
				size++;
				removeCurNode = null;
				return;
			}
		}
		
	}
    
    // Other methods you may want to add or override that could possibly facilitate 
    // other operations, for instance, addition, access to the previous element, etc.
    // 
    // ...
    // 
	
  }
 
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  if (arr == null || comp == null) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < arr.length; i++) {
			E tempItem = arr[i];
			int j = i - 1;
			while (j >= 0 && comp.compare(tempItem, arr[j]) < 0) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = tempItem;
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
	  int n = arr.length;
		boolean swapped;
		do {
			swapped = false;
			for (int i = 0; i < n - 1; i++) {
				if (arr[i].compareTo(arr[i + 1]) < 0) {

					// Swap arr[i] and arr[i+1]
					E temp = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = temp;
					swapped = true;
				}
			}
			n--;
		} while (swapped);
  }
  private int findOffset(int pos) {
		int itemCount = 0;
		Node curNode = head;
		// Finds and sets the node
		while (itemCount <= pos) {
			curNode = curNode.next;
			itemCount += curNode.count;
		}
		// Finds and sets the offset
		itemCount -= curNode.count;
		int offset = 0;
		while ((itemCount + offset) < pos) {
			offset++;
		}
		return offset;
	}

	private int findNodeIndex(int pos) {
		int itemCount = 0;
		int nodeCount = 0;
		Node curNode = head;
		// Finds and sets the node
		while (itemCount <= pos) {
			curNode = curNode.next;
			itemCount += curNode.count;
			nodeCount++;
		}
		return nodeCount;
	}

	private class NodeInfo {
		public Node node;
		public int offset;

		public NodeInfo(Node node, int offset) {
			this.node = node;
			this.offset = offset;
		}

	}

	private NodeInfo find(int pos) {
		if (pos == size()) {
			return new NodeInfo(tail, 0);
		}
		Node curNode = head.next;
		int nextCount = curNode.count;
		int previouse = 0;
		while ((pos >= nextCount && curNode.next != null)) {
			previouse += curNode.count;
			curNode = curNode.next;

			nextCount += curNode.count;
		}
		return new NodeInfo(curNode, pos - previouse);
	}
 
}