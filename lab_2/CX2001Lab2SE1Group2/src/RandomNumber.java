
import java.util.Random;

/**
 * generates a random serial number
 * 
 * @author Stephen
 *
 */
public class RandomNumber
{

	public static String generateRandomStr()
	{
		char array[] = new char[32];
		Random rand = new Random();
		for (int j = 0; j < 32; j++)
		{
			int i = rand.nextInt();
			if (i % 3 == 0)
				array[j] = (char) ('a' + rand.nextInt(26));
			else if (i % 3 == 1)
				array[j] = (char) ('A' + rand.nextInt(26));
			else
				array[j] = (char) ('1' + rand.nextInt(9));

		}
		return String.valueOf(array);
		
	}

}
