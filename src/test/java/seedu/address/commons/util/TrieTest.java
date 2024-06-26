package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TrieTest {

    @Test
    void search() {
        Trie trie = new Trie();
        trie.insert("hello");
        trie.insert("world");
        trie.insert("hell");
        trie.insert("word");
        trie.insert("work");
        trie.insert("wording");

        assertTrue(trie.search("hello"));
        assertTrue(trie.search("world"));
        assertTrue(trie.search("hell"));
        assertTrue(trie.search("word"));
        assertTrue(trie.search("work"));
        assertTrue(trie.search("wording"));
        assertFalse(trie.search("he"));
        assertFalse(trie.search("wor"));
        assertFalse(trie.search("wordings"));
    }

    @Test
    void delete() {
        Trie trie = new Trie(
            "hello",
            "world",
            "hell",
            "word",
            "work",
            "wording"
        );

        trie.delete("hello");
        assertFalse(trie.search("hello"));
        assertTrue(trie.search("world"));
        assertTrue(trie.search("hell"));
        assertTrue(trie.search("word"));
        assertTrue(trie.search("work"));
        assertTrue(trie.search("wording"));

        trie.delete("world");
        assertFalse(trie.search("hello"));
        assertFalse(trie.search("world"));
        assertTrue(trie.search("hell"));
        assertTrue(trie.search("word"));
        assertTrue(trie.search("work"));
        assertTrue(trie.search("wording"));

        trie.delete("hell");
        assertFalse(trie.search("hello"));
        assertFalse(trie.search("world"));
        assertFalse(trie.search("hell"));
        assertTrue(trie.search("word"));
        assertTrue(trie.search("work"));
        assertTrue(trie.search("wording"));

        trie.delete("word");
        assertFalse(trie.search("hello"));
        assertFalse(trie.search("world"));
        assertFalse(trie.search("hell"));
        assertFalse(trie.search("word"));
        assertTrue(trie.search("work"));
        assertTrue(trie.search("wording"));

        trie.delete("work");
        assertFalse(trie.search("hello"));
        assertFalse(trie.search("world"));
        assertFalse(trie.search("hell"));
        assertFalse(trie.search("word"));
        assertFalse(trie.search("work"));
        assertTrue(trie.search("wording"));

        trie.delete("wording");
        assertFalse(trie.search("hello"));
        assertFalse(trie.search("world"));
        assertFalse(trie.search("hell"));
        assertFalse(trie.search("word"));
        assertFalse(trie.search("work"));
        assertFalse(trie.search("wording"));
    }

    @Test
    void findFirstWordWithPrefix() {
        Trie trie = new Trie(
            "hello",
            "world",
            "hell",
            "word",
            "work",
            "wording"
        );

        assertEquals("hell", trie.findFirstWordWithPrefix("he"));
        assertEquals("hello", trie.findFirstWordWithPrefix("hello"));
        assertEquals("word", trie.findFirstWordWithPrefix("wor"));
        assertEquals("world", trie.findFirstWordWithPrefix("world"));
        assertEquals("wording", trie.findFirstWordWithPrefix("wording"));
        assertEquals("", trie.findFirstWordWithPrefix("a"));
        assertEquals("", trie.findFirstWordWithPrefix("ab"));
    }

    @Test
    void findAllWordsWithPrefix() {
        Trie trie = new Trie(
                "hello",
                "world",
                "hell",
                "word",
                "work",
                "wording"
        );

        assertEquals(2, trie.findAllWordsWithPrefix("he").size());
        assertEquals("hell", trie.findAllWordsWithPrefix("he").get(0));
        assertEquals("hello", trie.findAllWordsWithPrefix("he").get(1));

        assertEquals(4, trie.findAllWordsWithPrefix("w").size());
        assertEquals("word", trie.findAllWordsWithPrefix("w").get(0));
        assertEquals("wording", trie.findAllWordsWithPrefix("w").get(1));
        assertEquals("work", trie.findAllWordsWithPrefix("w").get(2));
        assertEquals("world", trie.findAllWordsWithPrefix("w").get(3));

        assertEquals(1, trie.findAllWordsWithPrefix("hello").size());
        assertEquals("hello", trie.findAllWordsWithPrefix("hello").get(0));

        assertEquals(0, trie.findAllWordsWithPrefix("x").size());
    }
}
