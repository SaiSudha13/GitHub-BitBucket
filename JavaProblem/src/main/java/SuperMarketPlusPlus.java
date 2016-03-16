import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


public class SuperMarketPlusPlus {

	private static List<Item> items = null;
	
	private final static String AGED_BRIE = "Aged Brie";
	private final static String SULFURAS = "Sulfuras";
	private final static String BACKSTAGE_PASSES = "Backstage Passes" ;
	private final static String ORGANIC_BANANAS = "Organic Bananas";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		readItemsDetails();
		
  //    System.out.println("Starting Supermarket Plus Plus" +items.get(0));
	for(int i=0;i < 10; i++)
       updateQuality();
}



	private static void readItemsDetails() {
		try {
		  items = new ArrayList<Item>();
	      FileReader fr = new FileReader("ItemsList.properties");
	      BufferedReader br = new BufferedReader(fr);
	      String stringRead = br.readLine();

	      while( stringRead != null )
	      {
	        StringTokenizer st = new StringTokenizer(stringRead, ",");
	        String name = st.nextToken( );
	        int sellIn = Integer.parseInt(st.nextToken( ));  
	        int quality = Integer.parseInt(st.nextToken( ));
	        items.add( new Item(name, sellIn, quality));
	        System.out.println(name + " " + sellIn + " "  +quality);
	        // read the next line
	        stringRead = br.readLine();
	      }
	      br.close( );
	    }
	
	    catch(IOException ioe){
	    	System.out.println("Exception");
	    	}
	}


	
    public static void updateQuality()
    {
    	String tempName = null;
    	int tempSellIn = 0;
    	int tempQuality = 0;
    	int index=0;
    	ItemBehaviour itemBehaviour = new ItemBehaviour();
    //	Iterator<Item> itemIterator = items.iterator();
    	for(Item itemIterator : items) {
    	//while(itemIterator.hasNext()){
    		tempName = itemIterator.getName();
    		tempQuality = itemIterator.getQuality();
    		tempSellIn = itemIterator.getSellIn();
    		if(AGED_BRIE.equalsIgnoreCase(tempName))
    		{
    			itemIterator.setQuality(itemBehaviour.increaseQuality(itemIterator.getQuality(),1));
    		}
    		else if(BACKSTAGE_PASSES.equalsIgnoreCase(tempName))
    		{
    			if ( tempSellIn > 10)
                {
    				itemIterator.setQuality(itemBehaviour.increaseQuality(itemIterator.getQuality(),1));
            
                }
            	else if ( tempSellIn > 5 && tempSellIn <= 10)
                {
            		itemIterator.setQuality(itemBehaviour.increaseQuality(itemIterator.getQuality(),2));
}
                else if (tempSellIn >= 0 && tempSellIn <= 5)
                {
                	itemIterator.setQuality(itemBehaviour.increaseQuality(itemIterator.getQuality(),3));
                }
                else 
                {
                	itemIterator.setQuality(0);
                }
    			
     			}
    		else if (ORGANIC_BANANAS.equalsIgnoreCase(tempName))
    		{
    			itemIterator.setQuality(itemBehaviour.decreaseQuality(itemIterator.getQuality(),2));
    			System.out.println(tempName + "-" +itemIterator.getQuality());
    		}
    		else if(!SULFURAS.equalsIgnoreCase(tempName))
    		{
    			itemIterator.setQuality(itemBehaviour.decreaseQuality(itemIterator.getQuality(),1)); 
       		}
    		 
    		if (!SULFURAS.equalsIgnoreCase(tempName))
            {
    			itemIterator.setSellIn(tempSellIn-1);
            }
    		if (itemIterator.getSellIn() < 0 && !AGED_BRIE.equalsIgnoreCase(tempName) 
    				&& !BACKSTAGE_PASSES.equalsIgnoreCase(tempName) && !ORGANIC_BANANAS.equalsIgnoreCase(tempName) 
    				&& !SULFURAS.equalsIgnoreCase(tempName))
    		{
    			itemIterator.setQuality(itemBehaviour.decreaseQuality(itemIterator.getQuality(),1));   		
    			System.out.println("if@@" +tempName + "-" +itemIterator.getQuality());
        		
        		
    		}
    		else if(itemIterator.getSellIn() < 0 && ORGANIC_BANANAS.equalsIgnoreCase(tempName)) 
    		{
    			itemIterator.setQuality(itemBehaviour.decreaseQuality(itemIterator.getQuality(),2));  
    			System.out.println(tempName + "-" +itemIterator.getQuality());
        		
    		}
    		//+itemIterator.getSellIn() + " " + 	itemIterator.getQuality() );
    		System.out.println("Before setting " + index  +"-" + itemIterator.getName() + " "
    			+	itemIterator.getSellIn() + " "
    			+	itemIterator.getQuality() );
    		
    		items.set(index, itemIterator);
    	//	System.out.println(items.);
    		index++;
    		}
    	System.out.println();
    	
  //  	System.out.println(itemIterator.getName() + " " +itemIterator.getSellIn() + " " + 	itemIterator.getQuality() );
    }	
    		
    		
    	
}
