import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
 * hash table implemented using closed-address hashing
 * 
 * structure (assuming even distribution): ===== | 0 |->[0]->[1]->...->[n/h] | 1
 * |->[0]->[1]->...->[n/h] | 2 |->[0]->[1]->...->[n/h] . . . | h
 * |->[0]->[1]->...->[n/h] =====
 * 
 * @author Annie, Stephen, Jason, Lewis, Kenrick
 *
 */
public class HashTable
{

	private ArrayList<LinkedList<String>> hashTable;

	private int hashTableSize;
	private int numOfKeysStored;

	/**
	 * Constructor allocates memory for hash table (closed-address hashing). Memory
	 * is allocated according to hash table size.
	 * 
	 * @param hashTableSize
	 */
	public HashTable(int hashTableSize, int numOfKeysStored)
	{
		hashTable = new ArrayList<LinkedList<String>>();
		this.hashTableSize = hashTableSize;
		this.numOfKeysStored = numOfKeysStored;

		for (int slot = 0; slot < hashTableSize; ++slot)
		{
			// no hash codes stored in a newly-created hash table, so every linked lists are
			// empty
			LinkedList<String> emptyLinkedList = new LinkedList<String>();
			hashTable.add(emptyLinkedList);
		}
	}

	public int getNumOfKeysStored()
	{
		return numOfKeysStored;
	}

	/**
	 * @param productArray
	 *            - an ArrayList that stores all product data
	 * @param serialNumList
	 *            - stores a list of random serial numbers to be searched in later
	 * @return Returns number of records imported (used to calculate load factor)
	 * @throws IOException
	 */
	public void storeKeys(ArrayList<Product> productArray, ArrayList<String> serialNumList, int maxNumOfKeysToStore)
			throws IOException
	{
		// Do iteration of compute and store value for each String element
		// int numOfHashCodeStored = 0;
		ArrayList<String> WholeList = new ArrayList<String>();

		Random rng = new Random(); // rng - random number generator

		for (int numOfKeysStored = 0; numOfKeysStored <= maxNumOfKeysToStore; ++numOfKeysStored)
		{
			Product product = productArray.get(numOfKeysStored);
			// System.out.println(product);
			String serialNum = product.getSerialNumber();
			int hashCode = computeHash(serialNum);

			WholeList.add(RandomNumber.generateRandomStr());

			// take the linked list stored in the slot for this particular hash code
			LinkedList<String> linkedListOfKeys = hashTable.get(hashCode);

			// chain the product the end of the linked list
			linkedListOfKeys.add(product.getSerialNumber());

			// put the linked list back into its slot in the hash table
			hashTable.set(hashCode, linkedListOfKeys);

		}

		for (int i = 0; i < 100; i++)
		{
			int randNumWithinRange = rng.nextInt(maxNumOfKeysToStore);
			serialNumList.add(WholeList.get(randNumWithinRange));
		}
	}

	/**
	 * Data - product Key - serial number Function that computes hash code of a
	 * product's serial number using the folding method
	 *
	 * @param serialNumber
	 * @return hash code
	 */
	private int computeHash(String serialNumber)
	{
		int keyValue = 0; // a (hopefully) unique sum of serialNumber's characters' ASCII values

		for (int index = 0; index < serialNumber.length(); ++index)
		{
			// The ASCII value of character
			int charValue = Character.getNumericValue(serialNumber.charAt(index));

			// multiply charValue with its before adding them up to get a unique keyValue
			keyValue += (int) (charValue * (index + 1)); // index+1, otherwise value of index0 is lost
		}
		return keyValue % hashTableSize;
	}

	/**
	 * Search for a key within the hash table by: 
	 * 1. hashing it to get a corresponding hash code 
	 * 2. using the hash code to find the correct slot in the hash table 
	 * 3. iterating through the linked list stored in that slot 
	 * 4. comparing target serial number against the stored serial numbers
	 * 
	 * Has an additional feature of printing out: 
	 * 1. whether the target is found and 
	 * 2. the time taken to search 
	 * which can be used to increase search time in case computer is too fast
	 * 
	 * @param targetSerialNumber
	 *            - serial number of the product to search for
	 * @param print
	 *            - controls whether or not to print 1. if the target is found and
	 *            2. time taken to search
	 * @return data time taken to search for the product, regardless of whether product
	 *         is found
	 */
	public boolean searchProduct(String targetSerialNum, boolean print, long[] data)
	{
		data[1] = 0;

		long startTime = System.nanoTime();

		if (print)
			System.out.println("Start time:" + startTime);

		boolean targetFound = false;

		int targetHashCode = computeHash(targetSerialNum);

		// serial numbers with the same hash codes are chained in the same linked list
		LinkedList<String> linkedListOfKeys = hashTable.get(targetHashCode);

		for (String key : linkedListOfKeys)
		{
			++data[1];

			if (targetSerialNum.equals(key))
			{
				targetFound = true;
				break;
			}
		}
		long endTime = System.nanoTime();

		if (print)
			System.out.println("End time:" + endTime);

		data[0] = endTime - startTime;
//		if (data[0] == 0)
//		{
//			System.out.println(startTime);
//			System.out.println(endTime);
//			System.out.println("true");
//		}
		// DecimalFormat df = new DecimalFormat("#.##########");
		if (print)
		{
			System.out.println("Product: " + targetSerialNum + " is " + (targetFound ? "" : "not ")
					+ "found, exec. time = " + data[0] + " nanoseconds");
		}
		// System.out.println(timeTaken);
		return targetFound;
	}

	/**
	 * This is a method to do multiple testings on each size of hashtable and
	 * compute the average time used.
	 * 
	 * @param prod
	 *            serial number of prodect to serach
	 * @param times
	 *            Amount of tests to be done
	 * @return
	 */
	/*
	 * public long avgSearch(String prod, long times) { long sum = 0; for (int i =
	 * 0; i < times; i++) { sum += searchProduct(prod, false); } long avgTime = sum
	 * / times; System.out.println("The average time taken to search is: " + avgTime
	 * / 1000.0 + " nanoseconds"); return avgTime; }
	 */

	/**
	 * store the individual times used for each testing. Each testing uses a
	 * different serial number randomly picked from serialNumList
	 * 
	 * @param serialNumList
	 *            - stores list of random serial numbers
	 * @param times
	 *            - number of iterations the search is repeated
	 * @param data[][]
	 *            - stores time taken to search and number of key comparisons
	 */
	public void avgSearchArray(ArrayList<String> serialNumList, boolean searchSuccess, long data[][])
	{
		String serialNumber;
		Random rng = new Random();

		if(searchSuccess)
		{
			for (int i = 0; i < numOfKeysStored; i++)
			{
				// System.out.println("Num of products: " + numOfKeysStored);
				// try
				// {
				serialNumber = serialNumList.get(rng.nextInt(numOfKeysStored));
				searchProduct(serialNumber, true, data[i]);
				// }
				// catch (Exception e)
				// {
				// e.getMessage();
				// }
			}
		}
		else
		{
			for (int i = 0; i < numOfKeysStored; i++)
			{
				serialNumber = RandomNumber.generateRandomStr();
				searchProduct(serialNumber, true, data[i]);
			}
		}

	}

	/**
	 * A function to print out the hash table by: 1. accessing the linked list in
	 * each slot of the hash table 2. iterating through the nodes in each linked
	 * list 3. printing out the Product stored in each node
	 */
	public void print()
	{
		for (int slot = 0; slot < hashTableSize; ++slot)
		{

			LinkedList<String> linkedListOfKeys = hashTable.get(slot);
			System.out.print("Index " + slot + ": [");

			ListIterator<String> iterator = linkedListOfKeys.listIterator();

			while (iterator.hasNext())
			{
				System.out.print(iterator.next().toString());
			}

			System.out.print(" ]\n");

		}
	}

	/**
	 * This is a method to count the number of products stored
	 */
	public void printNumOfKeysStored()
	{
		int total = 0;

		for (int slot = 0; slot < hashTableSize; ++slot)
		{

			LinkedList<String> linkedListOfKeys = hashTable.get(slot);
			System.out.println("Key: " + slot + " size: " + linkedListOfKeys.size());

			total += linkedListOfKeys.size();
		}
		System.out.println("Total number of keys stored in hash table: " + total);
	}

}
