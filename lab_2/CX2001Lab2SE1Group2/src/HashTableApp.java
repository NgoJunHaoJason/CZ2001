
/**
 * 
 * @author Annie, Stephen, Jason
 *
 */
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class HashTableApp
{

	/**
	 * Read in data file to generate an ArrayList of Products Excel file stores
	 * product serial number in 1st column and name in 2nd column
	 * 
	 * @param fileName
	 *            (path of file)
	 * @return an ArrayList of Products
	 * @throws IOException
	 */
	public static ArrayList<Product> readDataFromFile(String fileName) throws IOException
	{
		ArrayList<Product> productArrayList = new ArrayList<Product>();
		File myFile = new File(fileName);
		Scanner fileScanner = new Scanner(myFile); // scans each line in file

		while (fileScanner.hasNextLine())
		{
			String line = fileScanner.nextLine();

			Scanner lineScanner = new Scanner(line); // scans product data in each line

			// product's serial number and name
			String serialNumber, name;

			if (lineScanner.hasNext())
			{
				serialNumber = lineScanner.next(); // serial number is in 1st column

				if (lineScanner.hasNext())
				{
					name = lineScanner.next(); // description is in 2nd column

					/*
					 * quantity of product in inventory is no longer used int num = 0; if
					 * (lineScan.hasNextInt()) num = lineScan.nextInt(); while
					 * (lineScan.hasNextInt()) num = lineScan.nextInt();
					 */

					productArrayList.add(new Product(serialNumber, name/* , num, 0 */));

				}
			}
			lineScanner.close();
		}
		fileScanner.close();

		return productArrayList;

	}

	/**
	 * To arrange data in the array of searching time in ascending order using
	 * bubble sort
	 * 
	 * @param searchTimeArray
	 *            is the array of time taken to search
	 * @param arrayLength
	 * @return sum is sum of all the times stored in the array
	 *
	 */
	public static void bubbleSort(int index, long data[][], int numOfItems)
	{
		long temp;
		for (int i = 0; i < numOfItems; i++)
			for (int j = 0; j < numOfItems - i - 1; j++)
				if (data[j][index] > data[j + 1][index])
				{
					temp = data[j][index];
					data[j][index] = data[j + 1][index];
					data[j + 1][index] = temp;
				}

	}

	/**
	 * mean trimmed 20%
	 * 
	 * @return average after discarding the top and bottom 20% of values
	 */
	public static long meanTrimmed20Percent(int index, long data[][], int numOfItems)
	{
		long sum = 0;

		for (int i = (numOfItems / 5); i < (4 * numOfItems) / 5; i++)
		{
			sum += data[i][index];
			data[i - (numOfItems / 5)][index] = data[i][index];
		}

		return sum;
	}

	public static long sortAndAverage(int index, long data[][], int numOfItems)
	{
		bubbleSort(index, data, numOfItems);

		return meanTrimmed20Percent(index, data, numOfItems);
	}

	/**
	 * function to enter header into the csv files
	 * 
	 * @param writer
	 *            - object writing to file
	 */
	public static void initWriter(StringBuilder writer, int num)
	{
		writer.append("Hashtable size").append(',').append("Load").append(',').append("Average time to search")
				.append(',').append("Average number of comparisons").append(',');

		for (int i = 0; i < (3 * num) / 5; i++)
		{
			writer.append("Instance " + i);
			writer.append(',');
			writer.append(" <- ");
			writer.append(',');
		}
		writer.append('\n');
	}

	/**
	 * function to enter the values into the csv files
	 * 
	 * @param writer
	 *            - object writing into file
	 * @param hashSize
	 *            - Hash Value
	 * @param no
	 *            - total number of serial numbers
	 * @param num
	 *            - total number of values used to calculate average
	 * @param time
	 *            - average time
	 * @param data
	 *            - array of serial numbers
	 */
	public static void fillWriter(StringBuilder writer, int hashSize, int no, long data[][], int numOfItems)
	{
		long sum1, sum2;
		double avgTime1, avgTime2;

		sum1 = sortAndAverage(0, data, numOfItems);
		sum2 = sortAndAverage(1, data, numOfItems);

		avgTime1 = (double) sum1 / numOfItems;
		avgTime2 = (double) sum2 / numOfItems;

		writer.append(Integer.toString(hashSize)).append(',');

		writer.append(Float.toString((float) no / hashSize)).append(',');

		writer.append(Double.toString(avgTime1)).append(',');

		writer.append(Double.toString(avgTime2));

		for (int j = 0; j < (3 * numOfItems) / 5; j++)
		{
			writer.append(',');

			writer.append(Double.toString(data[j][0])).append(',');

			writer.append(Double.toString(data[j][1]));
		}

		writer.append('\n');
	}

	// these variables are declared constants for easy manipulation
	public static final double BASE_LOAD_FACTOR = 20;
	public static final int MIN_HASH_TABLE_SIZE = 20;
	public static final int MAX_HASH_TABLE_SIZE = 300;
	public static final int NUM_OF_TESTINGS = 100;

	/**
	 * print writers and string builders can be placed in arrays and accessed via
	 * indices
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> serialNumList = new ArrayList<String>();

		// the minimum hash table size used is 20
		PrimeNumberChecker.init(MIN_HASH_TABLE_SIZE);

		// read in data which would be placed in hash tables
		ArrayList<Product> productArray = readDataFromFile("Input Data\\products_withNum.csv");

		/**
		 * pwArray - PrintWriter Array 
		 * 0 - successful search in hash table of prime size with load factor of 20 
		 * 1 - successful search in hash table of prime size with load factor of 40 
		 * 2 - successful search in hash table of prime size with load factor of 60
		 * 
		 * 3 - successful search in hash table of non-prime size with load factor of 20
		 * 4 - successful search in hash table of non-prime size with load factor of 40
		 * 5 - successful search in hash table of non-prime size with load factor of 60
		 * 
		 * 6 - unsuccessful search in hash table of prime size with load factor of 20 
		 * 7 - unsuccessful search in hash table of prime size with load factor of 40 
		 * 8 - unsuccessful search in hash table of prime size with load factor of 60
		 * 
		 * 9 - unsuccessful search in hash table of non-prime size with load factor of 20 
		 * 10 - unsuccessful search in hash table of non-prime size with load factor of 40 
		 * 11 - unsuccessful search in hash table of non-prime size with load factor of 60
		 * 
		 * Each PrintWriter writes the above result to their respective files
		 */
		PrintWriter[] pwArray = new PrintWriter[12];
		for (int index = 0; index < pwArray.length; ++index)
		{
			String fileName = "Output Data\\";
			
			fileName += (index+1);

			fileName += ((index < 6) ? ". Successful Search - " : ". Unsuccessful Search - ");

			fileName += (((index % 6) < 3) ? "Prime Size - " : "Non-Prime Size - ");

			switch (index % 3)
			{
				case 0:
					fileName += "Load Factor 20";
					break;

				case 1:
					fileName += "Load Factor 40";
					break;

				case 2:
					fileName += "Load Factor 60";
					break;

				default:
					System.out.println("This should not show up");
			}

			fileName += " Result.csv";

			pwArray[index] = new PrintWriter(new File(fileName));
		}

		/**
		 * sbArray - StringBuilder 
		 * Array 
		 * 0 - for pwArray[0] 
		 * 1 - for pwArray[1] 
		 * . 
		 * . 
		 * . 
		 * 11 - for pwArray[11] each StringBuilder stores content for its respective PrintWriter
		 */
		StringBuilder[] sbArray = new StringBuilder[12];

		for(int index = 0; index < sbArray.length; ++index)
		{
			sbArray[index] = new StringBuilder();
			initWriter(sbArray[index], NUM_OF_TESTINGS);
		}

		
		for(int hashTableSize = MIN_HASH_TABLE_SIZE; hashTableSize <= MAX_HASH_TABLE_SIZE; ++hashTableSize)
		{
			for(int loadFactorRatio = 1; loadFactorRatio <= 3; ++loadFactorRatio)
			{
				double loadFactor = loadFactorRatio * BASE_LOAD_FACTOR;
				int maxNumOfKeysToStore = (int) (loadFactor * hashTableSize);

				HashTable hashTable = new HashTable(hashTableSize, NUM_OF_TESTINGS);
				long successData[][] = new long[hashTable.getNumOfKeysStored()][2];
				long failureData[][] = new long[hashTable.getNumOfKeysStored()][2];
				hashTable.storeKeys(productArray, serialNumList, maxNumOfKeysToStore);

				// hashTable.print();
				// hashTable.printNumOfProdImported();

				/**
				 * 0 - successful search in hash table of prime size with load factor of 20 
				 * 1 - successful search in hash table of prime size with load factor of 40 
				 * 2 - successful search in hash table of prime size with load factor of 60
				 * 
				 * 3 - successful search in hash table of non-prime size with load factor of 20
				 * 4 - successful search in hash table of non-prime size with load factor of 40
				 * 5 - successful search in hash table of non-prime size with load factor of 60
				 * 
				 * 6 - unsuccessful search in hash table of prime size with load factor of 20 
				 * 7 - unsuccessful search in hash table of prime size with load factor of 40 
				 * 8 - unsuccessful search in hash table of prime size with load factor of 60
				 * 
				 * 9 - unsuccessful search in hash table of non-prime size with load factor of 20 
				 * 10 - unsuccessful search in hash table of non-prime size with load factor of 40 
				 * 11 - unsuccessful search in hash table of non-prime size with load factor of 60
				 */
				// sbIndex used for accessing the various StringBuilders
				//int sbIndex = 12; //initialise to 12 1st so if assignment fails, indexOutOfRange
				//int sbIndex = loadFactorRatio - 1; // for this iteration, get corresponding load factor

				//System.out.println("Load factor: " + loadFactor);
				// successful search

				boolean isPrime = PrimeNumberChecker.checkAndAddPrime(hashTableSize); // prime
				
				int sbIndex = loadFactorRatio-1;
				
				//System.out.println("loadFactorRatio: " + loadFactorRatio);
				if(!isPrime)
					sbIndex += 3;
				
//				if(sbIndex == 2)
//					System.out.println("Test");
				
	
				// sbIndex += 3;

				hashTable.avgSearchArray(serialNumList, true, successData); // successful search

				fillWriter(sbArray[sbIndex], hashTableSize, maxNumOfKeysToStore, successData,
						hashTable.getNumOfKeysStored());

				hashTable.avgSearchArray(serialNumList, false, failureData); // unsuccessful search

				//System.out.println("Test 3, load factor: " + loadFactor);
				// unsuccessful search's index is 6 more than corresponding successful search
				// of the same prime/non-prime truth value and load factor value
				fillWriter(sbArray[sbIndex + 6], hashTableSize, maxNumOfKeysToStore, failureData,
						hashTable.getNumOfKeysStored());

				if (hashTableSize % 100 == 0)
					System.out.println("\nCurrent progress: " + hashTableSize / 100 + " (" + 
							loadFactorRatio + "/" + loadFactorRatio + ") completed");
			}

		}

		for (int index = 0; index < pwArray.length; ++index)
		{
			pwArray[index].write(sbArray[index].toString());
			pwArray[index].close();
		}

		System.out.println("~ FINISHED ~");

	}

}
