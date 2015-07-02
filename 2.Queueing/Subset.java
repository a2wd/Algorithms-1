import java.util.Iterator;

public class Subset {
	private static Integer ParseInt(String s) {
		try {
			return Integer.parseInt(s);
		}
		catch(NumberFormatException e) {
			return null;
		}
	}

	private static void Usage() {
		StdOut.println("Usage: Subset n\nSubset takes a list of values via StdIn and an integer n");
		StdOut.println("Subset prints n of the values (where 0 <= n <= length)");
	}

   public static void main(String[] args) {
		 if(args.length < 1) {
			 Usage();
			 return;
		 }

		 Integer i = ParseInt(args[0]);
		 if(i == null || i < 0) {
			 Usage();
			 return;
		 }

		 RandomizedQueue<String> rq = new RandomizedQueue<String>();
		 while(!StdIn.isEmpty())
			 rq.enqueue(StdIn.readString());

		 if(i > rq.size()) {
			 Usage();
			 return;
		 }

		 Iterator<String> it = rq.iterator();
		 int index = 0;

		 while(it.hasNext() && index++ < i)
			 StdOut.println(it.next());
	 }
}
