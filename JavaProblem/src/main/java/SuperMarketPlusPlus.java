import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class SuperMarketPlusPlus.java {

	private static List<Item> items = null;

	private final static String AGED_BRIE = "Aged Brie";
	private final static String SULFURAS = "Sulfuras";
	private final static String BACKSTAGE_PASSES = "Backstage Passes" ;
	private final static String ORGANIC_BANANAS = "Organic Bananas";
	private final static int CHANGE_FACTOR_ONE = 1;
	private final static int CHANGE_FACTOR_TWO = 2;
	private final static int CHANGE_FACTOR_THREE = 3;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Start reading the Item details from itemsDetails.properties
		System.out.println("Welcome to our SUPER MARKET ");
		System.out.println("====================================================================");
		System.out.println("We have started reading the Item details....");
		readItemsDetails();
		// Assumption : Code operation of daily schedule is already in place. 
		// Below for loop is only for testing for 10 night cycles and can be commented out if required.
		for(int i=0;i < 10; i++)
			updateQuality();
	}




	/**
	 * updateQuality this method does the major task of updating the Item Details
	 * as per the requirement specifications:
	 * 
	 * Assumptions :
	 * 1. It is assumed that increment of an item's Quality would max reach 50 even though there is 
	 * 				a scope for it to be incremented more than 50, except for Sulfuras.
	 * 2. It is assumed that decrement of an item's Quality would minimum reach 0 even though there is 
	 * 				a scope for it to be decremented less than 0.
	 * 
	 */
	public static void updateQuality()
	{
		String tempName = null;
		int tempSellIn = 0;
		int index=0;
		System.out.println("Started Modifying the Item Details:");
		System.out.println("====================================================================");

		for(Item itemIterator : items) {
			System.out.println("Item Name: " + itemIterator.getName());
			System.out.println("Before Modification: SellIn="
					+	itemIterator.getSellIn() + " Quality="
					+	itemIterator.getQuality() );
			tempName = itemIterator.getName();
			tempSellIn = itemIterator.getSellIn();
			if(AGED_BRIE.equalsIgnoreCase(tempName))
			{
				//Updating Quality of AGED BRIE
				increaseQuality(CHANGE_FACTOR_ONE, itemIterator);
			}
			else if(BACKSTAGE_PASSES.equalsIgnoreCase(tempName))
			{
				//Updating Quality of BACKSTAGE PASSES Based on Special behaviour

				if ( tempSellIn > 10)
				{
					increaseQuality(CHANGE_FACTOR_ONE, itemIterator);

				}
				else if ( tempSellIn > 5 && tempSellIn <= 10)
				{
					increaseQuality(CHANGE_FACTOR_TWO, itemIterator);
				}
				else if (tempSellIn > 0 && tempSellIn <= 5)
				{
					increaseQuality(CHANGE_FACTOR_THREE, itemIterator);
				}
				else if (tempSellIn <= 0){
					itemIterator.setQuality(0);
				}


			}
			else if (ORGANIC_BANANAS.equalsIgnoreCase(tempName))
			{
				//Updating Quality of ORGANIC_BANANAS Based on Special behaviour
				decreaseQuality(CHANGE_FACTOR_TWO, itemIterator);
			}
			else if(!SULFURAS.equalsIgnoreCase(tempName))
			{
				//Updating Quality of all the other Items apart from SULFURAS, 

				decreaseQuality(CHANGE_FACTOR_ONE, itemIterator); 
			}

			if (!SULFURAS.equalsIgnoreCase(tempName))
			{
				//Updating SellIn of all the Items apart from SULFURAS
				itemIterator.setSellIn(tempSellIn-1);
			}
			
			/*
			 * Handling the Quality after the SellIn days reaches less than 0
			 * 
			 */
			if (itemIterator.getSellIn() < 0 && !AGED_BRIE.equalsIgnoreCase(tempName) 
					&& !BACKSTAGE_PASSES.equalsIgnoreCase(tempName) && !ORGANIC_BANANAS.equalsIgnoreCase(tempName) 
					&& !SULFURAS.equalsIgnoreCase(tempName))
			{
				decreaseQuality(CHANGE_FACTOR_ONE, itemIterator);   		
			}
		
			// Assumed  that Banana's would degrade twice the rate after the SellIn reaches less than 0
			else if(itemIterator.getSellIn() < 0 && ORGANIC_BANANAS.equalsIgnoreCase(tempName)) 
			{
				decreaseQuality(CHANGE_FACTOR_TWO, itemIterator);  
			}
			items.set(index, itemIterator);
			System.out.println("After Modification : SellIn="
					+	itemIterator.getSellIn() + " Quality="
					+	itemIterator.getQuality() );
			System.out.println("====================================================================");

			index++;
		}
		System.out.println("All the necessary details of the Items have been modified Successfully ");

		System.out.println();

		//  	System.out.println(itemIterator.getName() + " " +itemIterator.getSellIn() + " " + 	itemIterator.getQuality() );
	}

	
	/**
	 * This method would start reading the itemsDetails.properties file.
	 * Only the Validated Item details would be stored and Invalid Item Details would be discarded.
	 * An intimate is given to the User to Correct the details as per the Correct format.
	 */
	private static void readItemsDetails() {
		try {
			items = new ArrayList<Item>();
			FileReader fr = new FileReader("itemsDetails.properties");
			BufferedReader br = new BufferedReader(fr);
			String stringRead = br.readLine();
			if (stringRead == null){
				System.out.println("ItemsList.properties file is Empty. Please entry valid Item details");
				System.out.println("The expected format of Item Details is : <itemName>,<SellIndays>,<Quality>");
				
			}
			
			while( stringRead != null )
			{
				StringTokenizer st = new StringTokenizer(stringRead, ",");
				// Validation for the correct format of the Item Details
				if (st.countTokens() !=3 ){
					System.out.println("Received Item details as : " +stringRead);
					System.out.println("The expected format of Item Details is : <itemName>,<SellIndays>,<Quality>");
					System.out.println("Please re-enter the Item details in correct format. Skipping the entry " +stringRead);
					stringRead = br.readLine();
					continue;
				}
				String name = st.nextToken( );
				int sellIn = Integer.parseInt(st.nextToken( ).trim());  
				int quality = Integer.parseInt(st.nextToken( ).trim());
				
				// Validation for the Quality of the Item
				if(itemDetailsValidated(name,quality)) {
					items.add( new Item(name, sellIn, quality));
					System.out.println("Adding the Item Details =====> " + name + "," + sellIn + ","  +quality);
					// read the next line
					stringRead = br.readLine();
				}
				else {
					System.out.println("Quality " + quality +" of the item " + name + " is not a valid option ");
					System.out.println("Skipping the entry : " +stringRead);
					stringRead = br.readLine();
					continue;
				}
			}
			System.out.println("====================================================================");
			System.out.println("Total Number of Items added : " +items.size());
			System.out.println("====================================================================");
			br.close( );
		}

		catch(IOException ioe){
			System.out.println("Exception");
		}
	}


	/**
	 * itemDetailsValidated validates for the maximum and minimum allowed Quality of an Item
	 * 
	 * @param name
	 * @param quality
	 * @return
	 */
	private static boolean itemDetailsValidated(String name,  int quality) {
		// TODO Auto-generated method stub
		if (!SULFURAS.equalsIgnoreCase(name)){
			// Quality of all the Items, except SULFURAS Should be between 0 and 50 
			return quality <=50 &&  quality>=0 ;
		}
		else {
			// Quality of SULFURAS should be between 0 and 80
			return quality <=80 &&  quality>=0  ;

		}
	}






	/**
	 * @param changeFactor
	 * @param itemBehaviour
	 * @param itemIterator
	 */
	public static void decreaseQuality(int changeFactor,
			Item itemIterator) {
		itemIterator.setQuality(new ItemBehaviour().decreaseQuality(itemIterator.getQuality(),changeFactor));
	}



	/**
	 * @param changeFactor
	 * @param itemBehaviour
	 * @param itemIterator
	 */
	public static void increaseQuality(int changeFactor,
			Item itemIterator) {
		itemIterator.setQuality(new ItemBehaviour().increaseQuality(itemIterator.getQuality(),changeFactor));
	}	



}
