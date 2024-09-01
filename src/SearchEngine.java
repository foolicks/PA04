/*
 * Name: Felix Najera
 * PID:  A17618969
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.*;

/**
 * Search Engine implementation.
 *
 * @author Felix Najera
 * @since  8/27/24
 */
public class SearchEngine {

    /**
     * Populate BSTrees from a file
     *
     * @param movieTree  - BST to be populated with actors
     * @param studioTree - BST to be populated with studios
     * @param ratingTree - BST to be populated with ratings
     * @param fileName   - name of the input file
     * @return  false if file not found, true otherwise
     */
    public static boolean populateSearchTrees(
            BSTree<String> movieTree, BSTree<String> studioTree,
            BSTree<String> ratingTree, String fileName
    ) {
        // open and read file
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // read 5 lines per batch:
                // movie, cast, studios, rating, trailing hyphen
                String movie = scanner.nextLine().trim();
                String[] cast = scanner.nextLine().split(" ");
                String[] studios = scanner.nextLine().split(" ");
                String rating = scanner.nextLine().trim();
                scanner.nextLine();


                // populate three trees with the information you just read
                // hint: create a helper function and reuse it to build all three trees


                for (String actor : cast) {
                    String lowerCaseActor = actor.toLowerCase();
                    if (!movieTree.findKey(lowerCaseActor)) {
                        movieTree.insert(lowerCaseActor);
                    }
                    movieTree.insertData(lowerCaseActor, movie);
                }



                for (String studio : studios) {
                    String lowerCaseStudio = studio.toLowerCase();
                    if (!studioTree.findKey(lowerCaseStudio)) {
                        studioTree.insert(lowerCaseStudio);
                    }
                    studioTree.insertData(lowerCaseStudio, movie);
                }

                String lowerCaseRating = rating.toLowerCase();
                if (!ratingTree.findKey(lowerCaseRating)) {
                    ratingTree.insert(lowerCaseRating);
                }
                ratingTree.insertData(lowerCaseRating, movie);


            }
            scanner.close();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * Search a query in a BST
     *
     * @param searchTree - BST to be searched
     * @param query      - query string
     */
    public static void searchMyQuery(BSTree<String> searchTree, String query) {



        // Process query
        String[] keys = query.toLowerCase().split(" ");
        Set<String> uniqueKeys = new HashSet<>(Arrays.asList(keys));

        // Search for common results (intersection)
        LinkedList<String> commonResults = new LinkedList<>();
        boolean firstKey = true;
        for (String key : uniqueKeys) {
            if (searchTree.findKey(key)) {
                LinkedList<String> results = searchTree.findDataList(key);
                if (firstKey) {
                    commonResults.addAll(results);
                    firstKey = false;
                } else {
                    commonResults.retainAll(results);
                }
            } else {
                commonResults.clear();
                break;
            }
        }

        // Print common results if any, otherwise print no results for the full query
        if (!commonResults.isEmpty()) {
            Collections.sort(commonResults);
            print(query, commonResults);
        } else {
            print(query, new LinkedList<>());
        }

        // Search and print individual results
        Set<String> printedResults = new HashSet<>(commonResults);
        for (String key : keys) {
            if (searchTree.findKey(key)) {
                LinkedList<String> results = searchTree.findDataList(key);
                results.removeAll(printedResults);
                if (!results.isEmpty()) {
                    Collections.sort(results);
                    print(key, results);
                    printedResults.addAll(results);
                }
            } else {
                // Print no results for individual key if not found
                print(key, new LinkedList<>());
            }
        }

    }

    /**
     * Print output of query
     *
     * @param query     Query used to search tree
     * @param documents Output of documents from query
     */
    public static void print(String query, LinkedList<String> documents) {

        if (documents == null || documents.isEmpty()) {
            System.out.println("The search yielded no results for " + query);
        } else {
            System.out.println("Documents related to " + query
                    + " are: " + documents.toString());
        }

    }

    /**
     * Main method that processes and query the given arguments
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        // initialize search trees
        BSTree<String> movieTree = new BSTree<>();
        BSTree<String> studioTree = new BSTree<>();
        BSTree<String> ratingTree = new BSTree<>();

        // process command line arguments
        String fileName = args[0];
        int searchKind = Integer.parseInt(args[1]);

        // populate search trees
        if (!populateSearchTrees(movieTree, studioTree, ratingTree, fileName)) {
            System.out.println("File not found.");
            return;
        }

        // choose the right tree to query

        BSTree<String> searchTree = null;
        switch (searchKind) {
            case 0:
                searchTree = movieTree;
                break;
            case 1:
                searchTree = studioTree;
                break;
            case 2:
                searchTree = ratingTree;
                break;
            default:
                System.out.println("Invalid search kind.");
                return;
        }

        // Build the query from the remaining command line arguments
        StringBuilder query = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            query.append(args[i]).append(' ');
        }
        String queryString = query.toString().trim();

        // Call searchMyQuery
        searchMyQuery(searchTree, queryString);
    }
}
