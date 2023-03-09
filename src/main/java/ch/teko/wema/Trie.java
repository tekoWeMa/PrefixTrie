/**

 Trie class
 A trie (prefix tree) is a tree-like data structure that is used to store a dynamic set of strings,
 where each node in the tree represents a character in a string. Tries are used to efficiently
 search for a string in a large set of strings.
 The Trie class has a nested TrieNode class that represents a node in the trie. Each TrieNode
 has a HashMap of children, where the key is the character and the value is the child TrieNode.
 The TrieNode also has a boolean field called isEndOfWord that indicates if the node represents
 the end of a word.
 The Trie class has a root TrieNode that represents the root of the trie. The Trie class has
 methods for inserting a word into the trie, searching for a word in the trie, checking if the
 trie contains any words with a given prefix, deleting a word from the trie, and printing the
 trie in a tree-like structure.
 */
package ch.teko.wema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Trie{
    private static class TrieNode {
        private final HashMap<Character, TrieNode> children;
        private boolean isEndOfWord;
        public String colorName;
        private int count; // added counter variable
        /**
         * TrieNode constructor
         *
         * Initializes the children HashMap and sets the isEndOfWord field to false.
         */
        public TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
            count = 0; // initialize counter to 0

        }
    }
    /**
     * Trie constructor
     *
     * Initializes the root TrieNode.
     */
    private final TrieNode root;
    public Trie() {
        root = new TrieNode();
    }

    /**
     * Inserts a word into the trie.
     *
     * @param hexValue      The word to insert into the trie.
     * @param colorName The Name of the color.
     */
    public void insert(String hexValue, String colorName) {
        TrieNode current = root;

        for (int i = 0; i < hexValue.length(); i++) {
            char ch = hexValue.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode();
                current.children.put(ch, node);
            }
            current = node;
        }

        if (current.isEndOfWord) { // if the word is already present in the Trie, increment its count
            current.count++;
        } else {
            current.isEndOfWord = true;
            current.count = 1;
            current.colorName = colorName;
        }
    }

    /**
     * Returns whether the trie contains the given word.
     *
     * @param word The word to search for in the trie.
     * @return True if the trie contains the word, False otherwise.
     */
    public boolean search(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.isEndOfWord;
    }
    /**

     Returns the TrieNode corresponding to the specified word, if present in the Trie.

     If the word is not present in the Trie, returns null.

     @param word the word to search for in the Trie

     @return the TrieNode corresponding to the specified word, or null if the word is not present in the Trie
     */
    public TrieNode get(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return null;
            }
            current = node;
        }
        return current.isEndOfWord ? current : null;
    }

    /**

     Returns whether the trie contains any word with the given prefix.

     @param prefix The prefix to search for in the trie.

     @return True if the trie contains any words with the given prefix, False otherwise.
     */
    public boolean startsWith(String prefix) {
        TrieNode current = root;

        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return true;
    }
    /**
     Deletes a word from the trie.
     @param word The word to delete from the trie.
     */
    public void delete(String word) {
        delete(root, word, 0);
    }
    /**

     Deletes a word from the trie (recursive helper function).
     @param node The current TrieNode.
     @param word The word to delete from the trie.
     @param index The index of the character in the word.
     @return True if the current node can be deleted, False otherwise.
     */

    private boolean delete(TrieNode node, String word, int index) {
        if (index == word.length()) {
            // if the current node is not the end of a word, there's nothing to delete
            if (!node.isEndOfWord) {
                return false;
            }
            // mark the current node as not being the end of a word
            node.isEndOfWord = false;
            // if the current node has no children, we can delete it
            return node.children.isEmpty();
        }
        char ch = word.charAt(index);
        TrieNode child = node.children.get(ch);
        if (child == null) {
            return false;
        }
        boolean shouldDeleteChild = delete(child, word, index + 1);
        if (shouldDeleteChild) {
            node.children.remove(ch);
            // we can delete the current node if it's not the end of a word and has no children
            return !node.isEndOfWord && node.children.isEmpty();
        }
        return false;
    }


    /**
     * Prints the trie in a tree-like structure.
     */
    public void print() {
        // start recursive printing at the root node with an empty prefix and the root node being the leaf
        print(root, "", true);
    }

    /**
     * Prints the trie in a tree-like structure (recursive helper function). This function is called
     * recursively for each child of the current node, building up the prefix as it goes down the tree.
     * When the function reaches a node that is the end of a word, it prints the prefix and the label
     * "END" to indicate that the prefix is a valid word.
     *
     * @param node The current TrieNode.
     * @param prefix The current prefix.
     * @param isLeaf Whether the current node is the last child of its parent. (Leaf of a branch of a Tree)
     *
     * Notes: In the Print line, the statement can be read as: "If isLeaf is true, return " ", otherwise return "│ "".
     * This is to ensure to connect to other Branches visually.
     */

    public void print(TrieNode node, String prefix, boolean isLeaf) {
        // create the label for the current node
        // modified label to print the count of the word in the Node
        String nodeLabel = node.isEndOfWord && isLeaf ? "END "+ node.colorName + "(" + node.count + ")" : ""; // modified nodeLabel

        // if the current node is the end of a word, print the prefix and the label
        if (node.isEndOfWord) {
            System.out.println(prefix + "└── " + nodeLabel);
        }

        // create a list of the children of the current node
        List<Character> children = new ArrayList<>(node.children.keySet());

        // iterate through the children of the current node (except for the last one)
        for (int i = 0; i < children.size() - 1; i++) {
            // get the current child character
            char ch = children.get(i);

            // print the prefix and the character, followed by an arrow
            System.out.println(prefix + (isLeaf ? "    " : "│   ") + ch + " ->");

            // recursive call to print the child node with the updated prefix and the child node not being the leaf
            print(node.children.get(ch), prefix + (isLeaf ? "    " : "│   "), false);
        }

        // if there are any children, print the last child
        if (children.size() > 0) {
            // get the last child character
            char ch = children.get(children.size() - 1);

            // print the prefix and the character, followed by an arrow
            System.out.println(prefix + (isLeaf ? "    " : "│   ") + ch + " ->");

            // recursive call to print the child node with the updated prefix and the child node being the leaf
            print(node.children.get(ch), prefix + (isLeaf ? "    " : "│   "), true);
        }
    }
    /**

     Searches for a hex value in the trie data structure.
     Each character in the hex value is traversed through the trie.
     @param hexValue the hex value to search for
     @return the color name associated with the hex value, or null if not found
     */
    public String searchHex(String hexValue) {
        TrieNode current = root;
        // traverse the trie for each character in the hex value
        for (int i = 0; i < hexValue.length(); i++) {
            char ch = hexValue.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
              return null;
            }
            current = node;
        }
        // if the hex value is found in the trie, print out the color name
        return current.colorName;

    }

    /**

     Converts a hex value to its corresponding RGB values.

     @param hexValue the hex value to convert

     @return a string containing the RGB values in the format "R: <r>, G: <g>, B: <b>"
     */

    public String getDecimal(String hexValue){
        int r, g, b;
        r = Integer.valueOf( hexValue.substring( 0, 2 ), 16 );
        g = Integer.valueOf( hexValue.substring( 2, 4 ), 16 );
        b = Integer.valueOf( hexValue.substring( 4, 6 ), 16 ) ;

        return "R: " + r + ", G: " + g + ", B: " + b;

    }
    /**

     The main method reads in a csv file containing hex values and color names, inserts them into the trie data structure


     Prompts the user to enter a language, reads a hexadecimal color value from the user, and retrieves the

     corresponding color name from the prefix trie data structure with the

     corresponding RGB values printed if found.

     @param args the command line arguments

     @throws IOException if an I/O error occurs
     */

    public static void main(String[] args) throws IOException {
        Trie trie = new Trie();



        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a language: ");
        String language = scanner.nextLine();

// switch case statement to handle the language input and set the langIndex accordingly
        int langIndex;
        switch (language) {
            case "German":
                langIndex = 3;
                break;
            case "English":
                langIndex = 4;
                break;
            case "French":
                langIndex = 5;
                break;
            case "Spanish":
                langIndex = 6;
                break;
            case "Italian":
                langIndex = 7;
                break;
            case "Nederlands":
                langIndex = 8;
                break;
            default:
                System.out.println("Invalid language input");
                return; // exit the program if an invalid language is entered
        }

        BufferedReader reader = new BufferedReader(new FileReader("D:\\Notes\\PrefixTrie\\src\\main\\resources\\resources\\ral_standard.csv"));
        String line = reader.readLine(); // read the header line
        String[] headers = line.split(",");
        if (langIndex < 0 || langIndex >= headers.length) {
            System.out.println("Invalid language index: " + langIndex);
            return; // exit the program if the language index is out of range
        }

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String hexValue = parts[2].substring(1); // remove '#' from hex value
            String colorName = parts[langIndex];
            trie.insert(hexValue, colorName);
        }
        reader.close();

        Scanner scanner2 = new Scanner(System.in);
        System.out.print("Enter a hex value: ");
        String hexValue = scanner2.nextLine();
        try {
            String colorName = trie.searchHex(hexValue);
            if (colorName != null) {
                System.out.println("The color name for the hex value " + hexValue + " is " + colorName + " and has the RGB Values of:" + trie.getDecimal(hexValue));
            } else {
                System.out.println("Color not found for hex value " + hexValue + " has the RGB Values of:" + trie.getDecimal(hexValue));
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid hex value entered: " + hexValue);
        }
        trie.print();

/**Loops through the first line
 * If there is a match, the program saves the index of that column header
 * (i.e., the language column) and then proceeds to read the remaining lines of the CSV file,
 * splitting each line into its individual columns and inserting the relevant data into the prefix trie.
 */

    }
}

