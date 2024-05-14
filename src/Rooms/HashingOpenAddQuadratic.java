package Rooms;

public class HashingOpenAddQuadratic<T extends Comparable<T>> {

    private Comparable<T>[] table;
    private int countOccupied;

    public HashingOpenAddQuadratic(int size) {
        // constructor: creates a table of 1.33 times of given size and intializes count as 0
        table = (T[]) new Comparable[size + (size / 3)];
        // table size should be a prime number and 1/3 extra.
        this.countOccupied = 0;
    }

    public HashingOpenAddQuadratic() {
        //constructor: passes to next constructor with standard table size 100
        this(10);
    }

    @Override
    public String toString() {
        // method: converts the hashtable into a string
        String str = "";
        for (int i = 0; i < table.length; i++) {
            str += (i + 1) + ":'" + table[i] + "'";
            if (i != table.length - 1) {
                str = str + " , \n";
            }
        }
        return "[" + str + "]";
    }

    public void display() {
        // method: displays hash table
        System.out.println(toString());
    }

    private int strToInt(String stringToBeChanged) {
        // method: convert string into integer by summing the ASCII values
        int sum = 0;
        for (int i = 0; i < stringToBeChanged.length(); i++) {
            sum += ((int) stringToBeChanged.charAt(i)) * 2;
        }
//        System.out.println("sum of " + stringToBeChanged + " is " + sum);
        return sum;
    }

    private int Hash(int sum) {
        //method: hash value calculator. compute hash value by taking mod on table length
        return sum % table.length;
    }

    private int Rehash(int sum, int i) {
        /* 
        method: rehash function. 
        in quadratic addressing, we will square 'i' which is the number of times we 
        encounter a collision. then we will call hash() function and find the hash of the new 
        value (value + i^2)
         */
        return Hash(sum + (int) (Math.pow(i, 2)));
    }

    public int insert(Comparable<T> valueToBeInserted) {
        /*
        method: inserts a value in the table using its hash() value. 
        call strToInt(v) and change the value to string, save return value in sum.
        call Hash(sum) and save return value in sum
        if (no collision on hash value) then place value
        else call rehash function until empty cell found
        also compute number of collisions on insertion of each word
         */
        if (countOccupied == table.length) {
//            System.out.println("ERROR: Cannot be inserted as hash table is full. ");
            return -1;
        }
        int sum = strToInt(valueToBeInserted.toString());
        sum = Hash(sum);
        int i = 0; // counts the number of times re-hashed i.e. no. of collisions faced
        while (this.table[sum] != null) {
            sum = Rehash(sum, ++i);
//            System.out.println("sum after rehashing " + sum + " , i is " + i);
        }
        this.table[sum] = valueToBeInserted;
        countOccupied++;
//        System.out.println("SUCCESS: '"+valueToBeInserted+"' successfully inserted. ");
        return i;
    }

    public boolean delete(Comparable<T> valueToBeDeleted) {
        /*
        method: delete word from hash table
        first search for the value. if found, then proceed deletion. 
        run a loop and keep checking if the hash() and rehash() is equal to the value 
        that is being deleted. if yes, make that value null. 
         */
        if (search(valueToBeDeleted) != null) {
            int sum = strToInt(valueToBeDeleted.toString());
            sum = Hash(sum);
            int i = 0;
            while (table[sum] == null || table[sum].compareTo((T) valueToBeDeleted) != 0) {
                sum = Rehash(sum, ++i);
            }
            if (table[sum] != null && table[sum].compareTo((T) valueToBeDeleted) == 0) {
                table[sum] = null;
                countOccupied--;
//                System.out.println("SUCCESS: '" + valueToBeDeleted + "' is deleted successfully. ");
                return true;
            }
        }
//        System.out.println("ERROR: '" + valueToBeDeleted.toString() + "' cannot be deleted as it is not found. ");
        return false;

    }

    public Comparable<T> search(Comparable<T> valueToBeSearched) {
        /*
        method: search word in a hash table
        first it changes the object to string, computes its sum, then computes its 
        hash() value. then it iteratively searches for that value at 
         */
        int sum = strToInt(valueToBeSearched.toString());
        sum = Hash(sum);
        int i = 0;
        while (i <= 50 && table[sum] == null || table[sum].compareTo((T) valueToBeSearched) != 0) {
            sum = Rehash(sum, ++i);
        }
        if (table[sum] != null && table[sum].compareTo((T) valueToBeSearched) == 0) {
//            System.out.println("SUCCESS: '" + valueToBeSearched.toString() + "' is found at index "+sum);
            return table[sum];
        }
//        System.out.println("ERROR: '" + valueToBeSearched.toString() + "' could not be found. ");
        return null;
    }

    public Comparable<T>[] toArray() {
        return this.table;
    }

    public int getCountOccupied() {
        return countOccupied;
    }

}
