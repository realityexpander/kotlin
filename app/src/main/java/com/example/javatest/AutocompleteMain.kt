package com.example.javatest

import java.util.*

class AutocompleteMain {

    fun start() {
        val dict =
            arrayOf("Hello", "Hellya", "goodbye", "goofball", "gooey", "jonny", "johns", "johnny")
        autocomplete(dict)

        val listOfFound = getWordsForPrefix("jo")
        println("\nList of found words:")
        for (s in listOfFound)
            print("$s, ")
    }

    // Trie node class
    inner class Node(internal var prefix: String,
                     internal var isWord: Boolean = false, // Does this node represent the last character in a word?
                     internal var children: HashMap<Char, Node> = HashMap()
    )

    // Construct the trie from the dictionary
    private fun autocomplete(dict: Array<String>) {
        trie = Node("")
        for (s in dict) {
            println("Insert word into dict: \"$s\"")
            insertWord(s)
        }
    }

    // Insert a word into the trie
    private fun insertWord(s: String) {
        var curr = trie

        // Iterate through each character in the string. If the character is not
        // already in the trie then add it
        for (i in 0 until s.length) {
            print(
                "Is char '" + s[i] +
                        "' in keySet=" + curr!!.children.keys + "?"
            )
            if (!curr.children.containsKey(s[i])) {
                curr.children[s[i]] = Node(s.substring(0, i + 1))
                println(
                    " No, so Put \"" + s.substring(0, i + 1) + "\" into key [" +
                            s[i] + "]"
                )
            } else {
                println(" Yes, continue to next character.")
            }

            curr = curr.children[s[i]]
            if (i == s.length - 1) { // End of the word?
                curr!!.isWord = true
                println("Finally, set \"" + s.substring(0, i + 1) + "\" isWord=true")
                println()
            }
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val program = AutocompleteMain()
            program.start()
        }

        // The trie
        private var trie: Node? = null

        // Find all words in trie that start with prefix
        fun getWordsForPrefix(pre: String): List<String> {
            val results = LinkedList<String>()

            println("Searching for: $pre")

            // Iterate to the end of the prefix
            var curr = trie
            for (c in pre.toCharArray()) {
                println(
                    "@ char '" + c + "', searching in keySet=" +
                            curr!!.children.keys
                )
                if (curr.children.containsKey(c)) {
                    curr = curr.children[c]
                } else {
                    println("Didn't find the prefix \"$pre\"")
                    return results // This will always return an empty list
                }
            }

            println("Found prefix:$pre")
            println("Now recursively searching for all child words...")

            // At the end of the prefix, find all child words
            findAllChildWords(curr!!, results)
            return results
        }

        // Recursively find every child word
        private fun findAllChildWords(n: Node, results: MutableList<String>) {
            if (n.isWord) {
                println("Found word:" + n.prefix)
                results.add(n.prefix)
            }

            for (c in n.children.keys) {
                println("keySet=${n.children.keys}, recursively search @ char '$c'")
                findAllChildWords(n.children[c]!!, results)
            }
        }
    }
}
