package restaurant;
/**
 * Use this class to test your Menu method. 
 * This class takes in two arguments:
 * - args[0] is the menu input file
 * - args[1] is the output file
 * 
 * This class:
 * - Reads the input and output file names from args
 * - Instantiates a new RUHungry object
 * - Calls the menu() method 
 * - Sets standard output to the output and prints the restaurant
 *   to that file
 * 
 * To run: java -cp bin restaurant.Menu menu.in menu.out stock.in stock.out order3.in donate3.in
 * To run transactions: java -cp bin restaurant.Menu menu.in menu.out stock.in stock.out transaction1.in
 * 
 * 
 */
public class Menu {
    public static void main(String[] args) {

	// 1. Read input files
	// Option to hardcode these values if you don't want to use the command line arguments
	   
        String inputFile = args[0];
        String outputFile = args[1];
	String stockFile = args[2];
        String stockOutput = args[3];
        //String transactions = args[4];
        String order = args[4];
        //String donateInput = args[5];
        //String restock = args[5];

        // 2. Instantiate an RUHungry object
        RUHungry rh = new RUHungry();

	// 3. Call the menu() method to read the menu
        rh.menu(inputFile);
        rh.createStockHashTable(stockFile);
        rh.updatePriceAndProfit();
        
        //StdIn.setFile(transactions);
        //int numberOfTransactions = Integer.parseInt(StdIn.readLine());
        //rh.order("House Salad", 16);
         /*0 
        for(int i = 0; i < numberOfTransactions; i++){
                String type = StdIn.readString();
                StdIn.readChar();
                int amount = StdIn.readInt();
                StdIn.readChar();
                String item = StdIn.readLine();
                if(type.equals("order")){
                        rh.order(item, amount);
                }
                else if(type.equals("donation")){
                        rh.donation(item, amount);
                }
                else if(type.equals("restock")){
                        rh.restock(item, amount);
                }
        } 
        */
        
         
        StdIn.setFile(order);
        int totalOrders = Integer.parseInt(StdIn.readLine());
       
        for(int i = 0; i < totalOrders; i++){
                int amount = StdIn.readInt();
                StdIn.readChar();
                String item = StdIn.readLine();
                rh.order(item, amount);
        }
        
        /* 
        StdIn.setFile(donateInput);
        int totalDonates = Integer.parseInt(StdIn.readLine());

        for(int x = 0; x < totalDonates; x++){
                int amountDonated = StdIn.readInt();
                StdIn.readChar();
                String itemDonated = StdIn.readLine();
                rh.donation(itemDonated, amountDonated);
        }
        */
         /* 
        StdIn.setFile(restock);
        int totalRestocks = Integer.parseInt(StdIn.readLine());

        for(int z = 0; z < totalRestocks; z++){
                int amountRestocked = StdIn.readInt();
                StdIn.readChar();
                String itemRestocked = StdIn.readLine();
                rh.restock(itemRestocked, amountRestocked);
        }
        */
        
        //rh.order("Classic Chicken Sandwich", 27);
	// 4. Set output file
	// Option to remove this line if you want to print directly to the screen
        
        StdOut.setFile(outputFile);
        StdOut.setFile(stockOutput);
        
	// 5. Print restaurant
        rh.printRestaurant();
        
    }
}
