import java.util.ArrayList;
import java.util.List;


public class SuperMarketPlusPlus {

	private static List<Item> items = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
        System.out.println("Welcome to our SUPER MARKET ");
	System.out.println("====================================================================");
	System.out.println("We have started reading the Item details....");
	readItemsDetails();

        updateQuality();
}


	
    public static void updateQuality()
    {
        for (int i = 0; i < items.size(); i++)
        {
            if ((!"Aged Brie".equals(items.get(i).getName())) && !"Backstage Passes".equals(items.get(i).getName())) 
            {
                if (items.get(i).getQuality() > 0)
                {
                    if (!"Sulfuras".equals(items.get(i).getName()))
                    {
                        items.get(i).setQuality(items.get(i).getQuality() - 1);
                    }
                }
            }
            else
            {
                if (items.get(i).getQuality() < 50)
                {
                    items.get(i).setQuality(items.get(i).getQuality() + 1);

                    if ("Backstage Passes".equals(items.get(i).getName()))
                    {
                        if (items.get(i).getSellIn() < 11)
                        {
                            if (items.get(i).getQuality() < 50)
                            {
                                items.get(i).setQuality(items.get(i).getQuality() + 1);
                            }
                        }

                        if (items.get(i).getSellIn() < 6)
                        {
                            if (items.get(i).getQuality() < 50)
                            {
                                items.get(i).setQuality(items.get(i).getQuality() + 1);
                            }
                        }
                    }
                }
            }

            if (!"Sulfuras".equals(items.get(i).getName()))
            {
                items.get(i).setSellIn(items.get(i).getSellIn() - 1);
            }

            if (items.get(i).getSellIn() < 0)
            {
                if (!"Aged Brie".equals(items.get(i).getName()))
                {
                    if (!"Backstage Passes".equals(items.get(i).getName()))
                    {
                        if (items.get(i).getQuality() > 0)
                        {
                            if (!"Sulfuras".equals(items.get(i).getName()))
                            {
                                items.get(i).setQuality(items.get(i).getQuality() - 1);
                            }
                        }
                    }
                    else
                    {
                        items.get(i).setQuality(items.get(i).getQuality() - items.get(i).getQuality());
                    }
                }
                else
                {
                    if (items.get(i).getQuality() < 50)
                    {
                        items.get(i).setQuality(items.get(i).getQuality() + 1);
                    }
                }
            }
        }
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



}
