/**
 * holds data to be used for hashing and searching
 * key - serialNumber
 * value - name
 * 
 * @author Annie
 *
 */
public class Product
{
	private String serialNumber; // key for the Product
	private String name;

	public Product(String serialNumber, String name)
	{
		this.serialNumber = serialNumber;
		this.name = name;

	}

	public String getSerialNumber()
	{
		return serialNumber;
	}
	
	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{

		return "Product(serialNum: " + serialNumber + " , name: " + name + ")";

	}

}