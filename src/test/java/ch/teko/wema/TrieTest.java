package ch.teko.wema;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class TrieTest {
    Trie trie;
    @BeforeEach
    public void init() {
        trie = new Trie();
        String[] words = {"hello", "hell", "world", "hi", "wonder", "helloing"};

        // Insert words into the trie
        for (String word : words) {
            trie.insert(word);
        }


    }
    @Test
    public void test_insert() {
        assertTrue(trie.search("hello"));
        assertFalse(trie.search("hababungs"));
    }
    @Test
    public void test_delete() {
        trie.delete("hel");
        trie.delete("he");
        assertTrue(trie.search("hell"));
        assertFalse(trie.search("he"));

        // Test deleting a word that has other words as prefixes
        trie.delete("helloing");
        assertFalse(trie.search("helloing"));
        assertTrue(trie.search("hello"));

        // Test deleting the last remaining word in the trie
        trie.delete("world");
        assertFalse(trie.search("world"));

        // Test deleting a character from a word that exists in the trie
        trie.delete("hellp");
        assertFalse(trie.search("hellp"));

    }
    @Test
    public void test_startsWith() {
        assertTrue(trie.startsWith("h"));
        assertFalse(trie.startsWith("NedVorhande"));

    }

    @Test
    public void test_main() {

        // Test searching for words that do exist in the trie
        assertTrue(trie.search("hello"));
        // Test searching for words that do not exist in the trie
        assertFalse(trie.search("foo"));


        trie.print();

    }
}