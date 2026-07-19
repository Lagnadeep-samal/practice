/*
 * Exercise 2: E-commerce Platform Search Function
 * =================================================
 * Topic: Data Structures and Algorithms
 *
 * Goal: Implement a search feature for an e-commerce platform that lets
 * users find products efficiently by keyword, get autocomplete
 * suggestions as they type, and filter/sort the results.
 *
 * Data structures used:
 *   1. Inverted Index (HashMap<String, List<Product>>)
 *      - Maps each word found in a product's name/category to the list
 *        of products containing that word. This gives O(1) average-case
 *        lookup per keyword instead of scanning every product (O(n)).
 *   2. Trie (prefix tree)
 *      - Supports fast autocomplete: given a prefix typed by the user,
 *        return all matching product names in O(k) time, where k is the
 *        length of the prefix (plus the number of matches), rather than
 *        checking every product name.
 *
 * To compile and run:
 *   javac ECommerceSearchFunction.java
 *   java ECommerceSearchFunction
 */

import java.util.*;

// ---------------------------------------------------------------------
// Product model
// ---------------------------------------------------------------------
class Product {
    final int id;
    final String name;
    final String category;
    final double price;
    final double rating;

    Product(int id, String name, String category, double price, double rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("[#%d] %-22s | %-12s | $%-8.2f | %.1f★",
                id, name, category, price, rating);
    }
}

// ---------------------------------------------------------------------
// Trie node & Trie for prefix-based autocomplete
// ---------------------------------------------------------------------
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    // Stores the full lowercase product names that pass through this node,
    // so we can quickly return all completions of a given prefix.
    List<String> wordsWithPrefix = new ArrayList<>();
}

class Trie {
    private final TrieNode root = new TrieNode();

    void insert(String word) {
        String lower = word.toLowerCase();
        TrieNode node = root;
        for (char c : lower.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
            node.wordsWithPrefix.add(lower);
        }
    }

    /** Returns up to `limit` distinct words that start with the given prefix. */
    List<String> autocomplete(String prefix, int limit) {
        String lower = prefix.toLowerCase();
        TrieNode node = root;
        for (char c : lower.toCharArray()) {
            node = node.children.get(c);
            if (node == null) return Collections.emptyList();
        }
        return node.wordsWithPrefix.stream()
                .distinct()
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
    }
}

// ---------------------------------------------------------------------
// Search engine: inverted index + trie, with filter & sort support
// ---------------------------------------------------------------------
class ProductSearchEngine {
    private final List<Product> catalog = new ArrayList<>();
    private final Map<String, List<Product>> invertedIndex = new HashMap<>();
    private final Trie nameTrie = new Trie();

    void addProduct(Product product) {
        catalog.add(product);
        nameTrie.insert(product.name);

        for (String token : tokenize(product.name + " " + product.category)) {
            invertedIndex.computeIfAbsent(token, k -> new ArrayList<>()).add(product);
        }
    }

    private List<String> tokenize(String text) {
        String[] parts = text.toLowerCase().split("[^a-z0-9]+");
        List<String> tokens = new ArrayList<>();
        for (String p : parts) {
            if (!p.isEmpty()) tokens.add(p);
        }
        return tokens;
    }

    /** Full-text keyword search: returns products matching ANY query word,
     *  ranked by how many of the query words they matched (most first). */
    List<Product> search(String query) {
        Map<Product, Integer> scores = new LinkedHashMap<>();
        for (String token : tokenize(query)) {
            List<Product> matches = invertedIndex.get(token);
            if (matches == null) continue;
            for (Product p : matches) {
                scores.merge(p, 1, Integer::sum);
            }
        }
        List<Product> results = new ArrayList<>(scores.keySet());
        results.sort((a, b) -> scores.get(b) - scores.get(a));
        return results;
    }

    /** Autocomplete suggestions for a partially typed product name. */
    List<String> suggest(String prefix, int limit) {
        return nameTrie.autocomplete(prefix, limit);
    }

    /** Filters a result list by category and a maximum price. */
    List<Product> filter(List<Product> results, String category, Double maxPrice) {
        List<Product> filtered = new ArrayList<>();
        for (Product p : results) {
            boolean categoryOk = (category == null) || p.category.equalsIgnoreCase(category);
            boolean priceOk = (maxPrice == null) || p.price <= maxPrice;
            if (categoryOk && priceOk) filtered.add(p);
        }
        return filtered;
    }

    /** Sorts results by price ascending or rating descending. */
    List<Product> sortByPrice(List<Product> results) {
        List<Product> sorted = new ArrayList<>(results);
        sorted.sort(Comparator.comparingDouble(p -> p.price));
        return sorted;
    }

    List<Product> sortByRating(List<Product> results) {
        List<Product> sorted = new ArrayList<>(results);
        sorted.sort((a, b) -> Double.compare(b.rating, a.rating));
        return sorted;
    }
}

// ---------------------------------------------------------------------
// Client code / demo
// ---------------------------------------------------------------------
public class ECommerceSearchFunction {
    public static void main(String[] args) {
        ProductSearchEngine engine = new ProductSearchEngine();

        engine.addProduct(new Product(1, "Wireless Mouse", "Electronics", 19.99, 4.3));
        engine.addProduct(new Product(2, "Wireless Keyboard", "Electronics", 29.99, 4.5));
        engine.addProduct(new Product(3, "Bluetooth Headphones", "Electronics", 49.99, 4.7));
        engine.addProduct(new Product(4, "Running Shoes", "Footwear", 59.99, 4.2));
        engine.addProduct(new Product(5, "Wireless Earbuds", "Electronics", 39.99, 4.1));
        engine.addProduct(new Product(6, "Yoga Mat", "Fitness", 15.99, 4.6));
        engine.addProduct(new Product(7, "Trail Running Shoes", "Footwear", 69.99, 4.4));

        System.out.println("== Search: \"wireless\" ==");
        List<Product> results = engine.search("wireless");
        results.forEach(System.out::println);

        System.out.println("\n== Search: \"running shoes\" sorted by rating ==");
        List<Product> runResults = engine.sortByRating(engine.search("running shoes"));
        runResults.forEach(System.out::println);

        System.out.println("\n== Search: \"wireless\" filtered to Electronics under $35 ==");
        List<Product> filtered = engine.filter(engine.search("wireless"), "Electronics", 35.0);
        filtered.forEach(System.out::println);

        System.out.println("\n== Autocomplete suggestions for prefix \"wi\" ==");
        System.out.println(engine.suggest("wi", 5));
    }
}
