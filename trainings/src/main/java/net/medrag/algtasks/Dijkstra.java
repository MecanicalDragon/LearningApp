package net.medrag.algtasks;


import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Stanislav Tretyakov
 * 24.03.2023
 */
public class Dijkstra {

    private static final int INF = Integer.MAX_VALUE;

    // Answer for 0 and 2: 0,4,3,1,0
    private static final int[][] GRAPH = {
            {0, 40, INF, INF, 18},
            {40, 0, 22, 6, 12},
            {INF, 22, 0, 14, INF},
            {INF, 6, 14, 0, 20},
            {18, 12, INF, 20, 0},
    };

    /*
    https://graphonline.ru/en/
0, 13, 0, 0, 18, 0, 0, 0, 4,
13, 0, 22, 6, 3, 0, 0, 14, 32,
0, 22, 0, 14, 0, 23, 0, 0, 0,
0, 6, 14, 0, 17, 0, 0, 0, 0,
18, 3, 0, 17, 0, 0, 0, 0, 0,
0, 0, 23, 0, 0, 0, 0, 3, 0,
0, 0, 0, 0, 0, 0, 0, 39, 7,
0, 14, 0, 0, 0, 3, 39, 0, 0,
4, 32, 0, 0, 0, 0, 7, 0, 0,
     */
    // Answer for 2 and 6: 44 [1, 3, 0, 2, 1, 2, 8, 5, 0]
    // Ordered answer for 2 and 6: [2, 3, 1, 0, 8, 6]
    private static final int[][] GRAPH_9 = {
            {0, 13, 0, 0, 18, 0, 0, 0, 4},
            {13, 0, 22, 6, 3, 0, 0, 14, 32},
            {0, 22, 0, 14, 0, 23, 0, 0, 0},
            {0, 6, 14, 0, 17, 0, 0, 0, 0},
            {18, 3, 0, 17, 0, 0, 0, 0, 0},
            {0, 0, 23, 0, 0, 0, 0, 3, 0},
            {0, 0, 0, 0, 0, 0, 0, 39, 7},
            {0, 14, 0, 0, 0, 3, 39, 0, 0},
            {4, 32, 0, 0, 0, 0, 7, 0, 0},
    };

    public static void main(String[] args) {

        System.out.println(Arrays.toString(shortestRouteWithoutQueue(GRAPH_9, 2, 6)));
    }

    private static int[] shortestRouteWithoutQueue(int[][] graph, int start, int end) {

        int[] minLengths = new int[graph.length];
        Arrays.fill(minLengths, Integer.MAX_VALUE);

        int[] route = new int[graph.length];
        boolean[] visited = new boolean[graph.length];

        int currentVertex = start;
        minLengths[currentVertex] = 0;

        while (currentVertex != end) {
            //1. visit vertex
            visited[currentVertex] = true;

            //2. find routs to adjacent vertices
            for (int i = 0; i < graph.length; i++) {
                if (graph[currentVertex][i] > 0) { // edge exists
                    final var investigatedVertexRouteLength = graph[currentVertex][i] + minLengths[currentVertex];
                    if (minLengths[i] > investigatedVertexRouteLength) {
                        minLengths[i] = investigatedVertexRouteLength;
                        route[i] = currentVertex;
                    }
                }
            }

            //3. find not visited vertex with minimal route length
            int minimalVertexIndex = currentVertex;
            int minimalLength = Integer.MAX_VALUE;
            for (int i = 0; i < minLengths.length; i++) {
                if (!visited[i] && minLengths[i] < minimalLength) {
                    minimalLength = minLengths[i];
                    minimalVertexIndex = i;
                }
            }
            currentVertex = minimalVertexIndex;
            //4. repeat until vertex is not end
        }
        System.out.println(minLengths[end]); // minimal route length

        //5. decode route
        int lengthCounter = 0;
        int cv = end;
        int[] orderedRoute = minLengths;
        orderedRoute[lengthCounter++] = end;
        while (cv != start) {
            orderedRoute[lengthCounter++] = route[cv];
            cv = route[cv];
        }
        cv = 0;
        int[] result = new int[lengthCounter];
        for (int i = lengthCounter - 1; i >= 0; i--) {
            result[i] = orderedRoute[cv++];
        }
        return result;
    }

    private static int[] shortestRouteWithQueue(int[][] graph, int start, int end) {

        final class Vertex {
            final int index; // index if the vertex in the matrix
            final int minRouteLength; // min length of the route
            final int prevVertex; // index of previous node on the shortest route

            public Vertex(int index, int minRouteLength, int prevVertex) {
                this.index = index;
                this.minRouteLength = minRouteLength;
                this.prevVertex = prevVertex;
            }

            public int getMinRouteLength() {
                return minRouteLength;
            }
        }

        // we need this queue to retrieve vertex with minimal route length
        PriorityQueue<Vertex> shortsQueue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getMinRouteLength));

        int[] shortestRouteLengths = new int[graph.length]; // lengths of the shortest routes to vertices
        int[] visited = new int[graph.length]; // -1 if vertex is not visited yet, index of the previous vertex in a shortest route otherwise
        Arrays.fill(shortestRouteLengths, INF);
        Arrays.fill(visited, -1);

        shortsQueue.add(new Vertex(start, 0, 0)); // add start vertex
        while (!shortsQueue.isEmpty()) { // if queue is empty, we traversed the whole graph
            final var currentNode = shortsQueue.remove(); // get the vertex with the shortest route
            if (visited[currentNode.index] != -1) {
                // skip if we visited it. We need it because of queue: it can contain multiple routes to vertex but since it is a queue, shortest is the first
                continue;
            }
            visited[currentNode.index] = currentNode.prevVertex;
            shortestRouteLengths[currentNode.index] = currentNode.getMinRouteLength();
            if (currentNode.index == end) {
                break; // we traversed to the target node
            }
            for (int i = 0; i < graph.length; i++) { // checking all edges
                final var currentEdgeWeight = graph[currentNode.index][i];
                if (currentEdgeWeight > 0 && currentEdgeWeight < INF) { // if edge exists
                    int pathLength = currentNode.minRouteLength + currentEdgeWeight;
                    // add vertex with length to it. Can duplicate in the queue, but the shortest will be the first
                    shortsQueue.add(new Vertex(i, pathLength, currentNode.index));
                }
            }
        }
        System.out.println(shortestRouteLengths[end]);
        return visited; // number on a position of vertex index in resulting array points to the index of the previous vertex in the shortest route.
    }

}
