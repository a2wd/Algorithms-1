import java.util.Iterator;

public class Subset {

  private static void usage() {
    StdOut.println("Usage: Subset n\nSubset takes a list of values via StdIn and an integer n");
    StdOut.println("Subset prints n of the values (where 0 <= n <= length)");
  }

   public static void main(String[] args) {
     if (args.length < 1) {
    	 usage();
    	 return;
     }

    int i;
    try {
    	i = Integer.parseInt(args[0]);
    }
    catch (NumberFormatException e) {
    	usage();
    	return;
    }

     if (i < 0) {
    	 usage();
    	 return;
     }

     RandomizedQueue<String> rq = new RandomizedQueue<String>();
     while (!StdIn.isEmpty())
    	 rq.enqueue(StdIn.readString());

     if (i > rq.size()) {
    	 usage();
    	 return;
     }

     Iterator<String> it = rq.iterator();
     int index = 0;

     while (it.hasNext() && index++ < i)
    	 StdOut.println(it.next());
   }
}
