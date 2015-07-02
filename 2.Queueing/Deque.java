import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private class Node {
    Node next;
    Node prev;
    Item i;
  }

  private Node first;
  private Node last;
  private int size;


   public Deque() {                         // construct an empty deque
     first = new Node();
     last = new Node();
     first.next = last;
     last.prev = first;
   }

   public boolean isEmpty() {               // is the deque empty?
     return size == 0;
   }

   public int size() {                      // return the number of items on the deque
     return size;
   }

   public void addFirst(Item item) {        // add the item to the front
  	 if (item == null)
  		 throw new NullPointerException("Attempt to add a null item");

     Node head = new Node();

     head.next = first.next;
     head.prev = first;
     head.i = item;
     
     first.next.prev = head;
     first.next = head;

     size++;
   }
                                             // java.lang.NullPointerException if add null item
                                            
   public void addLast(Item item) {         // add the item to the end
  	 if (item == null)
  		 throw new NullPointerException("Attempt to add a null item");

     Node tail = new Node();

     tail.prev = last.prev;
     tail.next = last;
     tail.i = item;

     last.prev.next = tail;
     last.prev = tail;
     size++;
   }

   public Item removeFirst() {              // remove and return the item from the front
     if (size-- <= 0)
       throw new NoSuchElementException("Attempt to remove item from empty list");

     Item i = first.next.i;
     first.next = first.next.next;
     first.next.prev = first;

     return i;
   }

   public Item removeLast() {               // remove and return the item from the end
     if (size-- <= 0)
       throw new NoSuchElementException("Attempt to remove item from empty list");

     Item i = last.prev.i;
     last.prev = last.prev.prev;
     last.prev.next = last;

     return i;
   }

   public Iterator<Item> iterator() {       // return an iterator over items in order from front to end
     Iterator<Item> it = new Iterator<Item>() {
       private Node index = first;

       public boolean hasNext() {
         return index.next != last;
       }

       public Item next() {
         if (index.next == last)
           throw new NoSuchElementException("Attempt to iterate to next element in exhausted deque");

         index = index.next;
         return index.i;
       }

       public void remove() {
         throw new UnsupportedOperationException("Remove not supported for Deque");
       }
     };
     return it;
   }

   public static void main(String[] args) { // unit testing
     Deque<Integer> d = new Deque<Integer>();
     d.addFirst(3);
     d.addFirst(2);
     d.addFirst(1);
     d.addLast(4);
     d.addLast(5);
     d.addLast(6);

     StdOut.println("About to iterate after: " + d.size + " adds");

     for (int i : d) {
       StdOut.print(i + " ");
     }

     d.removeFirst();
     d.removeLast();
     
     StdOut.println("\nAbout to iterate after two removes, size: : " + d.size);

     for (int i : d) {
       StdOut.print(i + " ");
     }
     StdOut.println();

     //Attempt to throw exception
     //Iterator<Integer> it = d.iterator();
     //for(int i = 0; i < 5; i++) {
     //   it.next();
     //}
     //it.remove();
     
     for (int i = 0; i < 4; i++) {
       d.removeFirst();
     }
     StdOut.println("Back to zero: " + d.size);

     //Attempt to throw exception
     //d.removeFirst();
     //d.removeLast();
   }
}
