import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] itemQueue;
  private int size;

  public RandomizedQueue() {               // construct an empty randomized queue
  	itemQueue = (Item[]) new Object[2];
  }

  public boolean isEmpty() {               // is the queue empty?
  	return size == 0;
  }

  public int size() {                      // return the number of items on the queue
  	return size;
  }

  public void enqueue(Item item) {         // add the item
  	if (item == null)
  		throw new NullPointerException("Attempt to add a null item");

  	if (size > (itemQueue.length) / 2) {
  		Item[] temp = (Item[]) new Object[itemQueue.length * 2];
  		for (int i = 0; i < size; i++) {
  			temp[i] = itemQueue[i];
  		}
  		itemQueue = temp;
  	}

  	itemQueue[size++] = item;
  }

  public Item dequeue() {                  // remove and return a random item
  	if (size == 0)
  		throw new NoSuchElementException("Attempt to remove item from empty randomised queue");

  	int r = StdRandom.uniform(size);
  	Item returnItem = itemQueue[r];
  	itemQueue[r] = itemQueue[size - 1];
		itemQueue[size - 1] = null;
  	size--;

  	if(size < (itemQueue.length / 4)) {
  		Item[] temp = (Item[]) new Object[itemQueue.length / 2];
  		for (int i = 0; i < size; i++) {
  			temp[i] = itemQueue[i];
  		}
  		itemQueue = temp;
  	}


  	return returnItem;
  }

  public Item sample() {                   // return (but do not remove) a random item
  	if (size == 0)
  		throw new NoSuchElementException("Attempt to sample item from empty randomised queue");

  	return itemQueue[StdRandom.uniform(size)];
  }

  public Iterator<Item> iterator() {       // return an independent iterator over items in random order
  	return new RandomizedQueueIterator(itemQueue, size);
  }

  private class RandomizedQueueIterator implements Iterator<Item> {

  	private int index;
  	private int size;
  	private Item[] rq;

  	public RandomizedQueueIterator(Item[] irq, int  s) {
  		index = 0;
  		size = s;
  		rq = (Item[]) new Object[s];

  		for (int i  = 0; i < s; i++)
  			rq[i] = irq[i];

  		for (int i = 0; i < s; i++) {
  			int r = i + StdRandom.uniform(s - i);
  			Item temp = rq[i];
  			rq[i] = rq[r];
  			rq[r] = temp;
  		}

  	}

  	public boolean hasNext() {
  		return index < size;
  	}

  	public Item next() {
  		if (index >= size)
  			throw new NoSuchElementException("Attempt to iterate to next element in exhausted deque");

  		return rq[index++];
  	}

  	public void remove() {
  		throw new UnsupportedOperationException("Remove not supported for Deque");
  	}
  }

  public static void main(String[] args) { // unit testing
  	RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();

  	StdOut.println("Filling our bag with ints from 1-20");

  	for (int i = 0; i < 20; i++)
  		r.enqueue(i);

  	StdOut.println("Filled bag with 20 integers");

  	for (int i = 0; i < 25; i++)
  		StdOut.print(r.sample() + " ");

  	StdOut.println("\nNow, to empty bag");

  	for (int i = 0; i < 20; i++)
  		StdOut.print(r.dequeue() + " ");

  	StdOut.println("\nAnd with an iterator");

  	for (int i = 0; i < 20; i++)
  		r.enqueue(i);

  	for (int i : r)
  		StdOut.print(i + " ");

  	StdOut.println("\nand again:");

  	for (int i : r)
  		StdOut.print(i + " ");

  	StdOut.println("\nBye!");
  }
}
