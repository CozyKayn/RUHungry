package restaurant;

import java.security.Identity;
import java.util.Hashtable;

import javax.print.attribute.standard.Media;
import javax.sound.midi.Sequencer.SyncMode;

/**
 * RUHungry is a fictitious restaurant. 
 * You will be running RUHungry for a day by seating guests, 
 * taking orders, donation requests and restocking the pantry as necessary.
 * 
 * Compiling and executing:
 * 1. use the run or debug function to run the driver and test your methods 
 * 
 * @author Mary Buist
 * @author Kushi Sharma
*/

public class RUHungry {
    
    /*
     * Instance variables
     */

    // Menu: two parallel arrays. The index in one corresponds to the same index in the other.
    private   String[] categoryVar; // array where containing the name of menu categories (e.g. Appetizer, Dessert).
    private MenuNode[] menuVar;     // array of lists of MenuNodes where each index is a category.
    
    // Stock: hashtable using chaining to resolve collisions.
    private StockNode[] stockVar;  // array of linked lists of StockNodes (use hashfunction to organize Nodes: id % stockVarSize)
    private int stockVarSize;

    // Transactions: orders, donations, restock transactions are recorded 
    private TransactionNode transactionVar; // refers to the first front node in linked list

    // Queue keeps track of parties that left the restaurant
    private Queue<Party> leftQueueVar;  

    // Tables Information - parallel arrays
    // If tableSeats[i] has 3 seats then parties with at most 3 people can sit at tables[i]
    private Party[] tables;      // Parties currently occupying the tables
    private   int[] tableSeats;  // The number of seats at each table

    /*
     * Default constructor
     */
    public RUHungry () {
        categoryVar    = null;
        menuVar        = null;
        stockVar       = null;
        stockVarSize   = 0;
        transactionVar = null;
        leftQueueVar   = null;
        tableSeats     = null;
        tables         = null;
    }

    /*
     * Getter and Setter methods
     */
    public MenuNode[] getMenu() { return menuVar; }
    public String[] getCategoryArray() { return categoryVar;}
    public StockNode[] getStockVar() { return stockVar; } 
    public TransactionNode getFrontTransactionNode() { return transactionVar; } 
    public TransactionNode resetFrontNode() {return transactionVar = null;} // method to reset the transactions for a new day
    public Queue<Party> getLeftQueueVar() { return leftQueueVar; } 
    public Party[] getTables() { return tables; }
    public int[] getTableSeats() { return tableSeats; }

    /*
     * Menu methods
     */

    /**
     * 
     * This method populates the two parallel arrays menuVar and categoryVar.
     * 
     * Each index of menuVar corresponds to the same index in categoryVar (a menu category like Appetizers).
     * If index 0 at categoryVar is Appetizers then menuVar at index 0 contains MenuNodes of appetizer dishes.
     * 
     * 1. read the input file:
     *      a) the first number corresponds to the number of categories (aka length of menuVar and categoryVar)
     *      b) the next line states the name of the category (populate CategoryVar as you read each category name)
     *      c) the next number represents how many dishes are in that category - this will be the size of the linked list in menuVar for this category
     *      d) the next line states the name of the dish
     *      e) the first number in the next line represents how many ingredient IDs there are
     *      f) the next few numbers (all in the 100s) are each the ingredient ID
     * 
     * 2. As you read through the input file:
     *      a) populate the categoryVar array
     *      b) populate menuVar depending on which index (aka which category) you are in
     *          i) make a dish object (with filled parameters -- don't worry about "price" and "profit" in the dish object for right now)
     *          ii) create menuNode and insert at the front of menuVar (NOTE! there will be multiple menuNodes in one index)
     * 
     * @param inputFile - use menu.in file which contains all the dishes
     */

    /*
     * Private method that makes a sort of list/hash thingy for each category that is given
     * 
     */
    public void menu(String inputFile) {

        StdIn.setFile(inputFile); // opens the inputFile to be read
        int numOfCategories = Integer.parseInt(StdIn.readLine());
        categoryVar = new String[numOfCategories];
        MenuNode menu = new MenuNode(null, null);
        if(menuVar == null){
            menuVar = new MenuNode[numOfCategories];
        }

        for(int i = 0; i < numOfCategories; i++){
            categoryVar[i] = StdIn.readLine();
            
            int numOfDishes = Integer.parseInt(StdIn.readLine());
            
            for(int x = 0; x < numOfDishes; x++){
                String nameOfDish123 = StdIn.readLine();
                int numOfIDs = StdIn.readInt();
                StdIn.readChar();
                String[] IDs = StdIn.readLine().split(" ");
                int[] intIDs = new int[IDs.length];


                //for(int e = 0; e < numOfIDs; e++){
                //System.out.println(IDs[e]);
                //}


                for(int z = 0; z < numOfIDs; z++){
                    intIDs[z] = Integer.parseInt(IDs[z]);
                    //System.out.println(intIDs[z]);
                    
                }
                
                Dish[] newDish1 = new Dish[numOfDishes];
                newDish1[x] = new Dish(categoryVar[i], nameOfDish123, intIDs);
                
                menu = new MenuNode(newDish1[x], menuVar[i]);
                menu = menu.getNextMenuNode();
                menuVar[i] = new MenuNode(newDish1[x], menu);
               
                
            }
            
        }
        /*String[] menuText = StdIn.readAllLines();
        String[] category = {menuText[1], menuText[11], menuText[19], menuText[31], menuText[41], menuText[47]};
        int[] numDishesInCategory = {Integer.parseInt(menuText[2]), Integer.parseInt(menuText[12]), Integer.parseInt(menuText[20]), Integer.parseInt(menuText[32]), Integer.parseInt(menuText[42]), Integer.parseInt(menuText[48])};
        
        
        if(menuVar == null){
            menuVar = new MenuNode[category.length];
        }
        //category index 0
        int numDishes0 = numDishesInCategory[0];
        String[] name0 = new String[numDishes0];
        int numOfID0 = 0;
        
        //category index 1
        int numDishes1 = numDishesInCategory[1];
        String[] name1 = new String[numDishes1];
        int numOfID1 = 0;

        //category index 2
        int numDishes2 = numDishesInCategory[2];
        String[] name2 = new String[numDishes2];
        int numOfID2 = 0;

        //category index 3
        int numDishes3 = numDishesInCategory[3];
        String[] name3 = new String[numDishes3];
        int numOfID3 = 0;
        
        //category index 4
        int numDishes4 = numDishesInCategory[4];
        String[] name4 = new String[numDishes4];
        int numOfID4 = 0;

        //category index 5
        int numDishes5 = numDishesInCategory[5];
        String[] name5 = new String[numDishes5];
        int numOfID5 = 0;

        categoryVar = new String[category.length];
        for(int x = 0; x < category.length; x++){
            categoryVar[x] = category[x];
        }

        for(int i = 0; i < menuText.length; i++){
            
            if(menuText[i].equals(category[0])){
                
                for(int i0 = 0; i0 < numDishes0 * 2; i0++){
                    
                    if(i0 % 2 == 0){
                        name0[i0 / 2] = menuText[i + 1 + i0];
                    }
                    else{
                        
                        String[] numID = menuText[i + 2 + i0].split(" ");
                        numOfID0 = Integer.parseInt(numID[0]);
                        
                        //makes the IDlist
                        int[] nameOfID0 = new int[numOfID0];
                        for(int x0 = 0; x0 < numOfID0; x0++){
                            nameOfID0[x0] = Integer.parseInt(numID[x0]);
                            Dish newdish = new Dish(category[0], name0[i0 / 2], nameOfID0);
                        MenuNode menu = new MenuNode(newdish, null);
                        menuVar[0] = menu;
                        }
                        
                    }

                }
            } 
            else if(menuText[i].equals(category[1])){
                
                
                for(int i1 = 0; i1 < numDishes1 * 2; i1++){
                    
                    if(i1 % 2 == 0){
                        name1[i1 / 2] = menuText[i + 1 + i1];
                    }
                    else{
                        String[] numID = menuText[i + 2 + i1].split(" ");
                        numOfID1 = Integer.parseInt(numID[0]);

                        int[] nameOfID1 = new int[numOfID1];
                        for(int x0 = 0; x0 < numOfID1; x0++){
                            nameOfID1[x0] = Integer.parseInt(numID[x0]);
                        }
                        Dish newdish = new Dish(category[1], name1[i1 / 2], nameOfID1);
                        MenuNode menu = new MenuNode(newdish, null);
                        menuVar[1] = menu;
                    }
                }
            }
            else if(menuText[i].equals(category[2])){
                
                
                for(int i2 = 0; i2 < numDishes1 * 2; i2++){
                    
                    if(i2 % 2 == 0){
                        name2[i2 / 2] = menuText[i + 1 + i2];
                    }
                    else{
                        String[] numID = menuText[i + 2 + i2].split(" ");
                        numOfID2 = Integer.parseInt(numID[0]);
                        int[] nameOfID2 = new int[numOfID2];
                        for(int x0 = 0; x0 < numOfID2; x0++){
                            nameOfID2[x0] = Integer.parseInt(numID[x0]);
                        }
                        Dish newdish = new Dish(category[2], name2[i2 / 2], nameOfID2);
                        MenuNode menu = new MenuNode(newdish, null);
                        menuVar[2] = menu;
                    }
                }
            }
            else if(menuText[i].equals(category[3])){
                
                
                for(int i3 = 0; i3 < numDishes3 * 2; i3++){
                    
                    if(i3 % 2 == 0){
                        name3[i3 / 2] = menuText[i + 1 + i3];
                    }
                    else{
                        String[] numID = menuText[i + 2 + i3].split(" ");
                        numOfID3 = Integer.parseInt(numID[0]);
                        int[] nameOfID3 = new int[numOfID3];

                        for(int x0 = 0; x0 < numOfID3; x0++){
                            nameOfID3[x0] = Integer.parseInt(numID[x0]);
                        }
                        Dish newdish = new Dish(category[3], name3[i3 / 2], nameOfID3);
                        MenuNode menu = new MenuNode(newdish, null);
                        menuVar[3] = menu;
                    }
                }
            }
            else if(menuText[i].equals(category[4])){
                
                
                for(int i4 = 0; i4 < numDishes4 * 2; i4++){
                    
                    if(i4 % 2 == 0){
                        name4[i4 / 2] = menuText[i + 1 + i4];
                    }
                    else{
                        String[] numID = menuText[i + 2 + i4].split(" ");
                        numOfID4 = Integer.parseInt(numID[0]);
                        int[] nameOfID4 = new int[numOfID4];

                        for(int x0 = 0; x0 < numOfID4; x0++){
                            nameOfID4[x0] = Integer.parseInt(numID[x0]);
                        }
                        Dish newdish = new Dish(category[4], name4[i4 / 2], nameOfID4);
                        MenuNode menu = new MenuNode(newdish, null);
                        menuVar[4] = menu;
                    }
                }
            }
            else if(menuText[i].equals(category[5])){
                
                
                for(int i5 = 0; i5 < numDishes5 * 2; i5++){
                    
                    if(i5 % 2 == 0){
                        name5[i5 / 2] = menuText[i + 1 +i5];
                    }
                    else{
                        String[] numID = menuText[i + 2 + i5].split(" ");
                        numOfID5 = Integer.parseInt(numID[0]);
                        int[] nameOfID5 = new int[numOfID5];

                        for(int x0 = 0; x0 < numOfID5; x0++){
                            nameOfID5[x0] = Integer.parseInt(numID[x0]);
                        }
                        Dish newdish = new Dish(category[5], name5[i5 / 2], nameOfID5);
                        MenuNode menu = new MenuNode(newdish, null);
                        menuVar[5] = menu;
                    }
                }
            }
        }
*/
	// WRITE YOUR CODE HERE

    }

    /** 
     * Find and return the MenuNode that contains the dish with dishName in the menuVar.
     * 
     *      ** GIVEN METHOD **
     *      ** DO NOT EDIT **
     * 
     * @param dishName - the name of the dish
     * @return the dish object corresponding to searched dish, null if dishName is not found.
     */

    public MenuNode findDish ( String dishName ) {

        MenuNode menuNode = null;

        // Search all categories since we don't know which category dishName is at
        for ( int category = 0; category < menuVar.length; category++ ) {

            MenuNode ptr = menuVar[category]; // set ptr at the front (first menuNode)
            
            while ( ptr != null ) { // while loop that searches the LL of the category to find the itemOrdered
                if ( ptr.getDish().getName().equalsIgnoreCase(dishName) ) {
                    return ptr;
                } else{
                    ptr = ptr.getNextMenuNode();
                }
            }
        }
        return menuNode;
    }

    /**
     * Find integer that corresponds to the index in menuVar and categoryVar arrays that has that category
     *              
     *      ** GIVEN METHOD **
     *      ** DO NOT EDIT **
     *
     * @param category - the category name
     * @return index of category in categoryVar
     */

    public int findCategoryIndex ( String category ) {
        int index = 0;
        for ( int i = 0; i < categoryVar.length; i++ ){
            if ( category.equalsIgnoreCase(categoryVar[i]) ) {
                index = i;
                break;
            }
        }
        return index;
    }

    /*
     * Stockroom methods
     */

    /**
     * PICK UP LINE OF THE METHOD:
     * *can I insert myself into your life? cuz you always help me sort 
     * out my problems and bring stability to my mine*
     * 
     * ***********
     * This method adds a StockNode into the stockVar hashtable.
     * 
     * 1. get the id of the given newNode and use a hash function to get the index at which the
     *    newNode is being inserted.
     * 
     * HASH FUNCTION: id % stockVarSize
     * 
     * 2. insert at the front of the linked list at the specific index
     * 
     * @param newNode - StockNode that needs to be inserted into StockVar
     */

    public void addStockNode ( StockNode newNode ) {
    //stockVar is an StockNode[]
    //stockVarSize = 0;

     /* 1 & 2: Allocate the Node &
        Put in the data*/
    //Node new_node = new Node(new_data);
 
    /* 3. Make next of new Node as head */
    //new_node.next = head;
 
    /* 4. Move the head to point to new Node */
   // head = new_node;
 


    Ingredient ingredient = newNode.getIngredient();
    int ingredientID = ingredient.getID();
    int hashFunction = ingredientID % stockVarSize;
    
    StockNode next = stockVar[hashFunction];
    stockVar[hashFunction] = newNode;
    newNode.setNextStockNode(next);
    
    //stockVar[hashFunction] = newNode;
    //stockVar[hashFunction].setNextStockNode(stockVar[hashFunction].getNextStockNode());
        //StockNode next = new StockNode(ingredient, null);
        /*Ingredient currIngredient = stockVar[hashFunction].getIngredient();
        StockNode currStock = new StockNode(currIngredient, next);
        stockVar[hashFunction].setNextStockNode(currStock);*/
    }
	// WRITE YOUR CODE HERE
    

    /**
     * This method finds an ingredient from StockVar (given the ingredientID)
     * 
     * 1. find the node based upon the ingredient ID (you can go to the specific index using the hash function!)
     *      (a) this is an efficient search as it looks only at the linked list which the key hash to
     * 2. find and return the node
     *  
     * @param ingredientID - the ID of the ingredient
     * @return the StockNode corresponding to the ingredientID, null otherwise
     */
   
    public StockNode findStockNode (int ingredientID) {

        int hash = ingredientID % stockVarSize;
        StockNode nodeBeingChecked = stockVar[hash];
        StockNode ptr = nodeBeingChecked;
        
    while(ptr.getIngredient() != null){
        if(ptr.getIngredient().getID() == ingredientID){
            
            return ptr;
        }
        else{
            ptr = ptr.getNextStockNode();
            
        }
    }
    
    return ptr;
        
	// WRITE YOUR CODE HERE

         // update the return value
    }

    /**
     * This method is to find an ingredient from StockVar (given the ingredient name).
     * 
     *      ** GIVEN METHOD **
     *      ** DO NOT EDIT **
     * 
     * @param ingredientName - the name of the ingredient
     * @return the specific ingredient StockNode, null otherwise
     */

    public StockNode findStockNode (String ingredientName) {
        
        StockNode stockNode = null;
        
        for ( int index = 0; index < stockVar.length; index ++ ){
            
            StockNode ptr = stockVar[index];
            
            while ( ptr != null ){
                if ( ptr.getIngredient().getName().equalsIgnoreCase(ingredientName) ){
                    return ptr;
                } else {  
                    ptr = ptr.getNextStockNode();
                }
            }
        }
        return stockNode;
    }

    /**
     * This method updates the stock amount of an ingredient.
     * 
     * 1. you will be given the ingredientName **OR** the ingredientID:
     *      a) the ingredientName is NOT null: find the ingredient and add the given stock amount to the
     *         current stock amount
     *      b) the ingredientID is NOT -1: find the ingredient and add the given stock amount to the
     *         current stock amount
     * 
     * (FOR FUTURE USE) SOMETIMES THE STOCK AMOUNT TO ADD MAY BE NEGATIVE (TO REMOVE STOCK)
     * 
     * @param ingredientName - the name of the ingredient
     * @param ingredientID - the id of the ingredient
     * @param stockAmountToAdd - the amount to add to the current stock amount
     */
    
    public void updateStock (String ingredientName, int ingredientID, int stockAmountToAdd) {
        StockNode node;
        
        if(ingredientName == null){
            node = findStockNode(ingredientID);
            while(node != null){
                if(node.getIngredient().getID() == ingredientID){
                    break;
                }
                else{
                    node = node.getNextStockNode();
                }
            }
        }
        else{
            if(ingredientID == -1){
                node = findStockNode(ingredientName);
                while(node != null){
                    if(node.getIngredient().getName().equalsIgnoreCase(ingredientName)){
                        break;
                    }
                    else{
                        node = node.getNextStockNode();
                    }
                }
                
            }
            else{
                //System.out.println(false);
                return;
            }
        }
        if(node != null){
            int stockLvl = node.getIngredient().getStockLevel() + stockAmountToAdd;
            node.getIngredient().setStockLevel(stockLvl);
        }     
	// WRITE YOUR CODE HERE

    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a single ‘for’ loop? cuz i only have i’s for you*
     * 
     * ***********
     * This method goes over menuVar to update the price and profit of each dish,
     * using the stockVar hashtable to lookup for ingredient's costs.
     * 
     * 1. For each dish in menuVar, add up the cost for each ingredient (found in stockVar), 
     *    and multiply the total by 1.2 to get the final price.
     *      a) update the price of each dish
     *  HINT! --> you can use the methods you've previously made!
     * 
     * 2. Calculate the profit of each dish by getting the totalPrice of ingredients and subtracting from 
     *    the price of the dish itself.
     * 
     * @return void
     */

    public void updatePriceAndProfit() {
        
        for(int i = 0; i < menuVar.length; i++){
           
            MenuNode category = menuVar[i];
            Dish currDish = category.getDish();
            //System.out.println(currDish);
            //System.out.println(category);
            //for(int y = 0; y < stockIDs.length - 1; y++){
            //System.out.println(stockIDs[y]);
            //}
        while(currDish != null && category != null){

            int[] stockIDs = currDish.getStockID();
            double cost = 0.0;
            for(int x = 0; x < stockIDs.length; x++){
                    
                StockNode stock = findStockNode(stockIDs[x]);
                
                cost =  cost + stock.getIngredient().getCost();
                
                
            }
            
            double price = cost * 1.2;
            double profit = price - cost;
            currDish.setPriceOfDish(price);
            currDish.setProfit(profit);  

            category = category.getNextMenuNode();
            if(category != null){
                currDish = category.getDish(); 
            }
            
        }
    } 
        
        

	// WRITE YOUR CODE HERE
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a decimal? cuz the thought of you 
     * always floats in my head and the two of use would make double*
     * 
     * ************
     * This method initializes and populates stockVar which is a hashtable where each index contains a 
     * linked list with StockNodes.
     * 
     * 1. set and read the inputFile (stock.in):
     *      a) first integer (on line 1) is the size of StockVar *** update stockVarSize AND create the stockVar array ***
     *      b) first integer of next line represents the ingredientID
     *          i) example: 101 on line 2
     *      c) use StdIn.readChar() to get rid of the space between the id and the name
     *      d) the string that follows is the ingredient name (NOTE! --> there are spaces between certain strings)
     *          i) example: Lettuce
     *      e) the double on the next line corresponds to the ingredient's cost
     *          i) example: 3.12 on line 3
     *      f) the next integer is the stock amount for that ingredient
     *          i) example: 30 on line 3
     * 
     * 2. create a Ingredient object followed by a StockNode then add to stockVar
     *      HINT! --> you may use previous methods written to help you!
     * 
     * @param inputFile - the input file with the ingredients and all their information (stock.in)
     */

    public void createStockHashTable (String inputFile){
        
        StdIn.setFile(inputFile); // opens inputFile to be read by StdIn
        stockVarSize = Integer.parseInt(StdIn.readLine());
        
        if(stockVar == null){
            stockVar = new StockNode[stockVarSize];
        }

        for(int i = 0; i < 51; i++){
            
            int ingID = StdIn.readInt();
            StdIn.readChar();
            String name = StdIn.readLine();
            Double cost = StdIn.readDouble();
            StdIn.readChar();
            int stockNum = StdIn.readInt();

            Ingredient ingredient = new Ingredient(ingID, name, stockNum, cost);
            StockNode newStockNode = new StockNode(ingredient, null);
            
            addStockNode(newStockNode);

            /*menu = new MenuNode(newDish1[x], menuVar[i]);
            menu = menu.getNextMenuNode();
            menuVar[i] = new MenuNode(newDish1[x], menu);*/
        }
        

	// WRITE YOUR CODE HERE

    }

    /*
     * Transaction methods
     */

    /**
     * This method adds a TransactionNode to the END of the transactions linked list.
     * The front of the list is transactionVar.
     *
     * 1. create a new TransactionNode with the TransactionData paramenter.
     * 2. add the TransactionNode at the end of the linked list transactionVar.
     * 
     * @param data - TransactionData node to be added to transactionVar
     */

    public void addTransactionNode ( TransactionData data ) { // method adds new transactionNode to the end of LL

        TransactionNode newNode = new TransactionNode(data, null);
        TransactionNode ptr = transactionVar;

        if(transactionVar == null){
            transactionVar = new TransactionNode(data, null);
            return;
        }
        else{
            while(ptr.getNext() != null){
                ptr = ptr.getNext();
            }
            ptr.setNext(newNode);
        }
        
    }
	// WRITE YOUR CODE HERE
	/*
    menu = new MenuNode(newDish1[x], menuVar[i]);
    menu = menu.getNextMenuNode();
    menuVar[i] = new MenuNode(newDish1[x], menu);
    */
    

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you the break command? cuz everything else stops when I see you*
     * 
     * *************
     * This method checks if there's enough in stock to prepare a dish.
     * 
     * 1. use findDish() method to find the menuNode node for dishName
     * 
     * 2. retrieve the Dish, then traverse ingredient array within the Dish
     * 
     * 3. return boolean based on whether you can sell the dish or not
     * HINT! --> once you determine you can't sell the dish, break and return
     * 
     * @param dishName - String of dish that's being requested
     * @param numberOfDishes - int of how many of that dish is being ordered
     * @return boolean
     */

    public boolean checkDishAvailability (String dishName, int numberOfDishes){

        MenuNode nodeDishIsIn = findDish(dishName);
        Dish dish = nodeDishIsIn.getDish();
        boolean canReturn = false;
        //System.out.println(canReturn);
        if(dish.getName().equalsIgnoreCase(dishName)){
            int[] IDs = dish.getStockID();
            
        
        for(int i = 0; i < IDs.length; i++){
            //System.out.println(IDs[i]);
            StockNode stock = findStockNode(IDs[i]);
            //System.out.println(stock.getIngredient());
            if(stock.getIngredient().getStockLevel() >= numberOfDishes){
                canReturn = true;
            }
            else{
                canReturn = false;
                break;
                
            }
        }
        }
        else{
            nodeDishIsIn = nodeDishIsIn.getNextMenuNode();
        }
        
	// WRITE YOUR CODE HERE
        //System.out.println(canReturn);
        return canReturn; // update the return value
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *if you were a while loop and I were a boolean, we could run 
     * together forever because I’ll always stay true to you*
     * 
     * ***************
     * This method simulates a customer ordering a dish. Use the checkDishAvailability() method to check whether the dish can be ordered.
     * If the dish cannot be prepared
     *      - create a TransactionData object of type "order" where the item is the dishName, the amount is the quantity being ordered, and profit is 0 (zero).
     *      - then add the transaction as an UNsuccessful transaction and,
     *      - simulate the customer trying to order other dishes in the same category linked list:
     *          - if the dish that comes right after the dishName can be prepared, great. If not, try the next one and so on.
     *          - you might have to traverse through the entire category searching for a dish that can be prepared. If you reach the end of the list, start from the beginning until you have visited EVERY dish in the category.
     *          - It is possible that no dish in the entire category can be prepared.
     *          - Note: the next dish the customer chooses is always the one that comes right after the one that could not be prepared. 
     * 
     * @param dishName - String of dish that's been ordered
     * @param quantity - int of how many of that dish has been ordered
     */

    public void order (String dishName, int quantity){

        boolean canBePrepared = checkDishAvailability(dishName, quantity);
        //checks originally 
        if(canBePrepared){
            MenuNode node = findDish(dishName);
            Dish currDish = node.getDish();
            Double profitMade = currDish.getProfit()*quantity;

            TransactionData passed = new TransactionData("order", dishName, quantity, profitMade, true);
            addTransactionNode(passed);

            int[] IDs = currDish.getStockID();
            for(int i = 0; i < IDs.length; i++){
                updateStock(null, IDs[i], -quantity);
            }
        }
        //if false and cant be made fun recursive function to check for everynode 
        else{
            TransactionData failed = new TransactionData("order", dishName, quantity, 0.0, false);
            addTransactionNode(failed);

            MenuNode newNode = findDish(dishName);
            MenuNode current = newNode;
            while(current.getNextMenuNode() != null){
                current = current.getNextMenuNode();
                Dish newDish = current.getDish();
                if(checkDishAvailability(newDish.getName(), quantity)){
                    order(newDish.getName(), quantity);
                    return;
                }
                else{
                    addTransactionNode(new TransactionData("order", newDish.getName(), quantity, 0.0, false));
                }
            }
//second part of recursion (the loop)
            int index = findCategoryIndex(newNode.getDish().getCategory());
            current = menuVar[index];
            while(current != newNode){
                Dish newDish = current.getDish();
                if(checkDishAvailability(newDish.getName(), quantity)){
                    order(newDish.getName(), quantity);
                    return;
                }
                else{
                    addTransactionNode(new TransactionData("order", newDish.getName(), quantity, 0.0, false));
                    current = current.getNextMenuNode();
                }
            }
        }
        

    }

    /**
     * This method returns the total profit for the day
     *
     * The profit is computed by traversing the transaction linked list (transactionVar) 
     * adding up all the profits for the day
     * 
     * @return profit - double value of the total profit for the day
     */

    public double profit () {

	double profitReturn = 0.0;
    TransactionNode node = transactionVar;
    while(node != null){
        TransactionData data = node.getData();
        double profit = data.getProfit();
        profitReturn = profitReturn + profit;
        node = node.getNext();
    }

	
        return profitReturn; // update the return value
    }

    /**
     * This method simulates donation requests, successful or not.
     * 
     * 1. check whether the profit is > 50 and whether there's enough ingredients in stock.
     * 
     * 2. add transaction to transactionVar
     * This method simulates the donation of ingredients to Rutgers Pantry.
     *  Donations can only happen if the profit for the day is greater than $50 (50 dollars).

Therefore, this method runs checks on whether the total profit for the day is greater than $50 and if there’s enough 
stock in the stockroom for the donation request to be successful. If there is, the stock is updated accordingly.

The transaction is recorded wether the donation is successful or not:

create a TransactionData object of type “donation” where the item is the ingredientName, the amount is the quantity 
being ordered, and profit is 0 (zero).

then add the transaction as a successful (if donation is happens) or failed (if donation cannot occur) transaction
 (call addTransactionNode()).
Call updateStock() to update the stock accordingly.
NOTE: to test the donation method.

call the order() method on all orders of a order file;
then run the donate on all donations of its correspondent file.
order1.in corresponds to donate1.in
order2.in corresponds to donate2.in
order3.in corresponds to donate3.in
Input File for Donation: In your own test code, read from the donation input file and call donation on each donation. 
The input file is formatted as follows:
1 line containing the number of donations, say n
n lines containing donation quantity and ingredient name, separated by a space
To read one donation do:
                   int amount = StdIn.readInt();
                   StdIn.readChar();
                   String item = StdIn.readLine();

     * @param ingredientName - String of ingredient that's been requested
     * @param quantity - int of how many of that ingredient has been ordered
     * @return void
     */

    public void donation (String ingredientName, int quantity){

        StockNode node = findStockNode(ingredientName);
        StockNode correctNode = null;

        while(node != null){
            if(node.getIngredient().getName().equalsIgnoreCase(ingredientName)){
                correctNode = node;
                break;
            }
            node = node.getNextStockNode();
        }

        TransactionData donate = new TransactionData("donation", ingredientName, quantity, 0.0, false);
        
        if(profit() > 50.0 && correctNode.getIngredient().getStockLevel() >= quantity){
            boolean isAllowed = true;
            TransactionData donateTrue = new TransactionData("donation", ingredientName, quantity, 0.0, isAllowed);
            addTransactionNode(donateTrue);
            updateStock(correctNode.getIngredient().getName(), -1, -quantity);
        }
        else{
            addTransactionNode(donate);
        }
	// WRITE YOUR CODE HERE

    }

    /**
     * This method simulates restock orders
     * 
     * 1. check whether the profit is sufficient to pay for the total cost of ingredient
     *      a) (how much each ingredient costs) * (quantity)
     *      b) if there is enough profit, adjust stock and profit accordingly
     * 
     * 2. add transaction to transactionVar
     * 
     * 
     * 
     * This method simulates restock orders.
This method runs checks on whether there’s enough total profit in the day to pay for the restock request.

The cost of restocking is the ingredient’s cost multiplied by the quantity the restaurant needs to buy.
The restock happens as long as there’s enough profit. Call updateStock() to update the stockroom accordingly.

The transaction is recorded wether the restock is successful or not:

create a TransactionData object of type “restock” where the item is the ingredientName, the amount is the 
quantity being ordered, and profit is:
        0 (zero) if there isn’t enough profit to restock.
        cost of restocking (negative) if the restocking is successful. 
then add the transaction as a successful (if restocking is happens) or failed (if restocking cannot occur) 
transaction (call addTransactionNode()) and updates the stock accordingly.


     * @param ingredientName - ingredient that's been requested
     * @param quantity - how many of that ingredient needs to be ordered
     */

    public void restock (String ingredientName, int quantity){
        
        StockNode node = findStockNode(ingredientName);
        StockNode curr = null;

        while(node != null){
            if(node.getIngredient().getName().equalsIgnoreCase(ingredientName)){
                curr = node;
            }
            node = node.getNextStockNode();
        }

        double costToRestock = curr.getIngredient().getCost() * quantity;

        if(profit() > costToRestock){
            TransactionData restock = new TransactionData("restock", ingredientName, quantity, -costToRestock, true);
            addTransactionNode(restock);
            updateStock(ingredientName, -1, quantity);
        }
        else{
            TransactionData failedRestock = new TransactionData("restock", ingredientName, quantity, 0.0, false);
            addTransactionNode(failedRestock);
        }


	// WRITE YOUR CODE HERE
    }

   /*
    * Seat guests/customers methods
    */

    /**
     * Method to populate tables (which is a 1D integer array) based upon input file
     * 
     * The input file is formatted as follows:
     * - an integer t contains the number of tables
     * - t lines containing number of rows * seats per row for each table
     * 
     * @param inputFile - tables1.in (contains all the tables in the RUHungry restaurant)
     * @return void (aka nothing)
     */

    public void createTables ( String inputFile ) { 

        StdIn.setFile(inputFile);
	
        int numberOfTables = StdIn.readInt();
        tableSeats = new int[numberOfTables];
        tables     = new Party[numberOfTables];

        for ( int t = 0; t < numberOfTables; t++ ) {
            tableSeats[t] = StdIn.readInt() * StdIn.readInt();
        }
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a linked list? cuz nothing could stock up to you and 
     * you’re pretty queue(te)*
     * 
     * ***************
     * This method simulates seating guests at tables. You are guaranteed to be able to sit everyone from the waitingQueue eventually.
     * 
     * 1. initialize a tables array for party that are currently sitting
     * 
     * 2. initialize leftQueueVar a Party queue that represents the people that have left the restaurant
     * 
     * 3. while there are parties waiting to be sat:
     *      - Starting from index 0 (zero), seat the next party in the first available table that fits their party.
     *      - If there is no available table for the next party, kick a party out from the tables array:
     *          1. starting at index 0 (zero), find the first table big enough to hold the next party in line.
     *          2. remove the current party, add them to the leftQueueVar.
     *          3. seat the next party in line.
     *
     * Parallel arrays: tableSeats[i] refers to tables[i]. If tableSeats[i] is 3 then a party with 3 or less people can sit at tables[i].
     * tableSeats contains the number of seats per table.
     * tables contains the Party object currently at the table.
     * 
     * Note: After everyone has been seated (waitingQueue is empty), remove all the parties from tables and add then to the leftQueueVar.
     * 
     * @param waitingQueue - queue containing parties waiting to be seated (each element in queue is a Party <-- you are given this class!)
     */

    public void seatAllGuests ( Queue<Party> waitingQueue ) {

	// WRITE YOUR CODE HERE

    }

    /**
     * Prints all states of the restaurant.
     * 
     * Edit this method if you wish.
     */
    public void printRestaurant() {
        // 1. Print out menu
        System.out.println("Menu:");
        if (categoryVar != null) {
            for (int i=0; i < categoryVar.length; i++) {
                System.out.println(categoryVar[i] + ":");
                System.out.println();

                MenuNode ptr = menuVar[i];
                while (ptr != null) {
                    System.out.println(ptr.getDish().getName() + "  Price: $" +
                    ((Math.round(ptr.getDish().getPriceOfDish() * 100.0)) / 100.0) + " Profit: $" + ((Math.round(ptr.getDish().getProfit() * 100.0)) / 100.0));
                    System.out.println();

                    ptr = ptr.getNextMenuNode();
                }
                System.out.println();
            }
        }
        else {
            System.out.println("Empty - categoryVar is null.");
        }
        // 2. Print out stock
        System.out.println("Stock:");
        if (stockVar != null) {
            for (int i=0; i < 10; i++) {
                System.out.println("Index " + i);
                StockNode ptr = stockVar[i];
                while (ptr != null) {
                    System.out.println(ptr.getIngredient().getName() + "  ID: " + ptr.getIngredient().getID() + " Price: " +
                    ((Math.round(ptr.getIngredient().getCost() *100.0)) / 100.0) + " Stock Level: " + ptr.getIngredient().getStockLevel());
                    System.out.println();
    
                    ptr = ptr.getNextStockNode();
                }
    
                System.out.println();
            }
        }
        else {
            System.out.println("Empty - stockVar is null.");
        }
        // 3. Print out transactions
        System.out.println("Transactions:");
        if (transactionVar != null) {
            TransactionNode ptr = transactionVar;
            int successes = 0;
            int failures = 0;
            while (ptr != null) {
                String type = ptr.getData().getType();
                String item = ptr.getData().getItem();
                int amount = ptr.getData().getAmount();
                double profit = ptr.getData().getProfit();
                boolean success = ptr.getData().getSuccess();
                if (success == true){
                    successes += 1;
                }
                else if (success == false){
                    failures += 1;
                }

                System.out.println("Type: " + type + ", Name: " + item + ", Amount: " + amount + ", Profit: $" + ((Math.round(profit * 100.0)) / 100.0) + ", Was it a Success? " + success);
                
                ptr = ptr.getNext();
            }
            System.out.println("Total number of successful transactions: " + successes);
            System.out.println("Total number of unsuccessful transactions: " + failures);
            System.out.println("Total profit remaining: $" + ((Math.round(profit() * 100.0)) / 100.0));
        }
        else {
            System.out.println("Empty - transactionVar is null.");
        }
        // 4. Print out tables
        System.out.println("Tables and Parties:");
        restaurant.Queue<Party> leftQueue = leftQueueVar;
        if (leftQueueVar != null) {
            System.out.println(("Parties in order of leaving:"));
            int counter = 0;
            while (!leftQueue.isEmpty()) {
                Party removed = leftQueue.dequeue();
                counter += 1;
                System.out.println(counter + ": " + removed.getName());
            }
        }
        else {
            System.out.println("Empty -- leftQueueVar is empty");
        }
    }
}
//StdOut.print
//System.out.println