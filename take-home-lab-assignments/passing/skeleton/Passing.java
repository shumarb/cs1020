
/**
*	Name		:
*	Matric No.	:
*/

import java.util.*;

public class Passing {
    
    private void run() {
        
    }
	public static void main(String[] args) {
        Passing passingObj = new Passing();
        passingObj.run();
    }
}

class Players {
    String _name;
    int _received;
    
    //constructors
    public Players (String name, int received) {
        _name = name;
        _received = received;
    }
    
    public int getReceived() {
        return _received;
    }
}



class LinkedList<E> {
	protected ListNode head = null;
    protected ListNode tail = null;
    protected ListNode current = null;
    protected int num_nodes=0;

	public void addAfter(E item, E after) {
        ListNode<E> newNode = new ListNode<E>();
        if (after == null) {
            tail = newNode;
            head = newNode;
            tail.setNext(head);
            num_nodes++;
        }
        else {
            tail = newNode;
            tail.setNext(head);
            num_nodes++;
        }
    }
    
    public ListNode<E> getHead() {
        return this.head;
    }
    
    public ListNode<E> getCurrent() {
        return this.current;
    }

	public int findIndex(E item) {
		return 0;
	}
    
    public ListNode<E> getLast() {
        return this.tail;
    }
    
    public ListNode<E> getPrev(ListNode<E> ln) {
        current = head;
        while (current)
    }
        
    
}

class ListNode<E> {
	protected E element;
	protected ListNode<E> next;

	/* constructors */
	public ListNode(E item) {
		element = item;
		next = null;
	}

	public ListNode(E item, ListNode<E> n) {
		element = item;
		next = n;
	}

	/* get the next ListNode */
	public ListNode<E> getNext() {
		return this.next;
	}

	/* get the element of the ListNode */
	public E getElement() {
		return this.element;
	}

	public void setNext(ListNode<E> item) {
		this.next = item;
	}

	public void setElement(E item) {
		this.element = item;
	}
}
