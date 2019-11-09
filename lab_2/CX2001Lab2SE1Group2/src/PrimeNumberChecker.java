import java.util.ArrayList;

/**
 * Not the best class for checking prime
 * Works by checking if a target number has a prime number which is smaller than it as a factor
 * Stores all previously found prime numbers in a static (class) array
 * 
 * @author Jason
 *
 */
public class PrimeNumberChecker
{
	public static ArrayList<Integer> primeArray = new ArrayList<Integer>();

	// place first value in primeArray: 2
	public static void init()
	{
		primeArray.clear();
		primeArray.add(2);
	}
	
	// place first values in primeArray in the range 2 - upperBound
	public static void init(int upperBound)
	{
		init();

		for (int number = 3; number <= upperBound; number += 2) // after 2, no more even primes
		{
			checkAndAddPrime(number);
		}
	}

	/**
	 * This function assumes all prime numbers that come before the target number
	 * is already in prime array
	 * 
	 * @param targetNumber
	 * @return boolean value of whether targetNumber is a prime number
	 */
	public static boolean checkAndAddPrime(int targetNumber)
	{
		if(primeArray.isEmpty())
		{
			init(targetNumber-1);
		}
		
		boolean isPrime = true; // assume number is prime number

		for (int primeNumber : primeArray) // check against existing prime numbers
		{
			// checks if number has a factor that is NOT itself (learnt this the hard way)
			if (targetNumber % primeNumber == 0 && targetNumber != primeNumber) 
			{
				isPrime = false;
				break;
			}
		}

		if (isPrime)
		{
			primeArray.add(targetNumber);
		}

		return isPrime;
	}
}