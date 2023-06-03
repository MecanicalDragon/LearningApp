package net.medrag.algtasks;

/**
 * H-index is a scientific index that determines scientist's quoting level.
 * H-index represents a number of scientist's articles that were quoted a number of times.
 * The objective of this task is to find h-index of a scientist based on an array of quotes for each of his/her articles.
 *
 * @author Stanislav Tretiakov
 * 03.06.2023
 */
public class Hindex {
    private static final int[][] SOURCE = new int[][]{
            new int[]{1, 9, 0, 4, 7, 5, 6, 8, 3, 4, 1, 8, 7, 3, 5, 7, 12},
            new int[]{17, 3, 7, 3, 8, 15, 10, 9, 6, 3, 9, 2, 19, 10, 8},
            new int[]{3, 0, 15, 22, 11, 9, 5, 7, 2, 17, 4, 9, 6, 13, 0}
    };

    public static void main(String[] args) {
        for (int[] quotesByArticles : SOURCE) {
            System.out.println(getIndexBySumming(quotesByArticles));
            System.out.println(getIndexByCountingSort(quotesByArticles));
        }
    }

    private static int getIndexBySumming(int[] quotesByArticles) {
        int[] articlesByQuotes = new int[quotesByArticles.length + 1];
        for (int quoteNumberForArticle : quotesByArticles) {
            int limitedQuoteNumber = Math.min(quoteNumberForArticle, quotesByArticles.length);
            articlesByQuotes[limitedQuoteNumber]++;
        }
        int x = 0;
        for (int i = articlesByQuotes.length - 1; i >= 0; i--) {
            x += articlesByQuotes[i];
            if (x >= i) {
                return i;
            }
        }
        return 0;
    }

    private static int getIndexByCountingSort(int[] quotesByArticles) {
        int[] sortedArticlesByQuotes = new int[quotesByArticles.length + 1];
        for (int quotesByArticle : quotesByArticles) {
            int limitedQuoteNumber = Math.min(quotesByArticle, quotesByArticles.length);
            sortedArticlesByQuotes[limitedQuoteNumber]++;
        }
        int quotesByArticlesIterator = 0;
        for (int i = 0; i < sortedArticlesByQuotes.length; i++) {
            final var articlesNumber = sortedArticlesByQuotes[i];
            for (int k = 0; k < articlesNumber; k++) {
                quotesByArticles[quotesByArticlesIterator + k] = i;
            }
            quotesByArticlesIterator += articlesNumber;
        }
        for (int i = quotesByArticles.length - 1; i >= 0; i--) {
            if (quotesByArticles.length - i >= quotesByArticles[i]) {
                return quotesByArticles[i];
            }
        }
        return 0;
    }
}
