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
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie{
    private static class TrieNode {
        private final HashMap<Character, TrieNode> children;
        private boolean isEndOfWord;
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
     * @param word The word to insert into the trie.
     */
    public void insert(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
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
     * @param IsLeaf Whether the current node is the last child of its parent. (Leaf of a branch of a Tree)
     *
     * Notes: In the Print line, the statement can be read as: "If IsLeaf is true, return " ", otherwise return "│ "".
     * This is to ensure to connect to other Branches visually.
     */

    private void print(TrieNode node, String prefix, boolean IsLeaf) {
        // create the label for the current node
        // modified label to print the count of the word in the Node
        String nodeLabel = node.isEndOfWord && IsLeaf ? "END (" + node.count + ")" : ""; // modified nodeLabel

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
            System.out.println(prefix + (IsLeaf ? "    " : "│   ") + ch + " ->");

            // recursive call to print the child node with the updated prefix and the child node not being the leaf
            print(node.children.get(ch), prefix + (IsLeaf ? "    " : "│   "), false);
        }

        // if there are any children, print the last child
        if (children.size() > 0) {
            // get the last child character
            char ch = children.get(children.size() - 1);

            // print the prefix and the character, followed by an arrow
            System.out.println(prefix + (IsLeaf ? "    " : "│   ") + ch + " ->");

            // recursive call to print the child node with the updated prefix and the child node being the leaf
            print(node.children.get(ch), prefix + (IsLeaf ? "    " : "│   "), true);
        }
    }

    /**

     The main method of the PrefixTrie class that creates a new instance of the Trie and

     inserts words into it. The words are either read from a text file or are provided in an array.

     The Trie class supports basic functionality such as searching, checking if a word starts with

     a given prefix and printing the Trie. The main method also prints the count of occurrences

     of specific words in the Trie.

     @param args The command line arguments passed to the program.
     */
    public static void main(String[] args) {
        Trie trie = new Trie();

        // Words to insert into the Trie
        // String[] words = {"hello", "hell", "world", "hi", "wonder", "wonderful", "winter", "Wizard", "halloween"};
        String fileName = "C:\\Dev\\PrefixTrie\\src\\main\\resources\\resources\\kjv.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StreamTokenizer tokenizer = new StreamTokenizer(br);

            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                    trie.insert(tokenizer.sval.toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Insert words into the trie
        //for (String word : words) {
        //    trie.insert(word);
        //}

        // Returns if the Word is in the Trie
        System.out.println(trie.search("hello"));


        // Returns if a Word in the Trie starts with the characters provided
        System.out.println(trie.startsWith("wo"));

        // Print out the trie
        trie.print();

        System.out.println("The Wordcount of god is:" +trie.get("god").count);
        System.out.println("The Wordcount of lord is:" +trie.get("lord").count);
    }
}

