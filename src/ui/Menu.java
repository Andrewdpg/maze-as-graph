package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.AdjacencyList;
import model.Graph;
import model.Pair;

public class Menu {
    private Graph<Integer> graph;
    private int[][] matrix;
    private int numRows;
    private int numCols;

    public Menu() {
        graph = new AdjacencyList<>();
    }

    public void displayMenu() {
        Scanner scan = new Scanner(System.in);

        numRows = scan.nextInt();

        numCols = scan.nextInt();

        matrix = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                matrix[i][j] = scan.nextInt();
            }
        }

        int numEntrances = scan.nextInt();

        Integer[][] entrances = new Integer[numEntrances][2];
        for (int i = 0; i < numEntrances; i++) {
            int row = scan.nextInt();
            int col = scan.nextInt();
            entrances[i] = new Integer[] { row, col };
        }

        int endRow = scan.nextInt();
        int endCol = scan.nextInt();

        buildGraph();

        int numSolutions = 0;
        List<Integer> minPath = null;
        int minCost = Integer.MAX_VALUE;

        for (Integer[] entrance : entrances) {
            int startRow = entrance[0];
            int startCol = entrance[1];
            List<Integer> path = findShortestPath(startRow, startCol, endRow, endCol);
            if (path != null) {
                numSolutions++;
                int cost = calculateCost(path);
                if (cost < minCost) {
                    minPath = path;
                    minCost = cost;
                }
            }
        }

        // Print result
        System.out.println(numSolutions);
        if (numSolutions > 0) {
            printPath(minPath);
            System.out.println(minCost);
        } else {
            System.out.println("-1");
        }
        scan.close();
    }

    private void buildGraph() {
        int vertex = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (matrix[i][j] > -1) {
                    graph.addVertex(vertex);
                }
                vertex++;
            }
        }

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (matrix[i][j] >= 0) {
                    int currVertex = i * numCols + j;

                    if (i > 0 && matrix[i - 1][j] > -1) {
                        int neighbor = (i - 1) * numCols + j;
                        int weight = matrix[i - 1][j];
                        graph.addEdge(currVertex, neighbor, weight);
                    }
                    if (i < numRows - 1 && matrix[i + 1][j] > -1) {
                        int neighbor = (i + 1) * numCols + j;
                        int weight = matrix[i + 1][j];
                        graph.addEdge(currVertex, neighbor, weight);
                    }
                    if (j > 0 && matrix[i][j - 1] > -1) {
                        int neighbor = i * numCols + (j - 1);
                        int weight = matrix[i][j - 1];
                        graph.addEdge(currVertex, neighbor, weight);
                    }
                    if (j < numCols - 1 && matrix[i][j + 1] > -1) {
                        int neighbor = i * numCols + (j + 1);
                        int weight = matrix[i][j + 1];
                        graph.addEdge(currVertex, neighbor, weight);
                    }
                }
            }
        }
    }

    private List<Integer> findShortestPath(int startRow, int startCol, int endRow, int endCol) {

        int startVertex = startRow * numCols + startCol;
        int endVertex = endRow * numCols + endCol;
        
        Map<Integer, Pair<Integer,Integer>> distances = graph.dijkstra(startVertex);

        if (!distances.containsKey(endVertex)) {
            return null;
        }

        List<Integer> path = new ArrayList<>();
        int currentVertex = endVertex;

        while (currentVertex != startVertex) {
            path.add(currentVertex);
            Integer previousVertex = distances.get(currentVertex).getSecond();
            if (previousVertex == null) {
                return null;
            }
            currentVertex = previousVertex;
        }
        path.add(startVertex);
        Collections.reverse(path);

        return path;
    }

    private int calculateCost(List<Integer> path) {
        int cost = 0;
        for (int i = 1; i < path.size(); i++) {
            int vertex = path.get(i);
            int row = vertex / numCols;
            int col = vertex % numCols;
            cost += matrix[row][col] >= 0 ? matrix[row][col] : 0;
        }
        return cost;
    }

    private void printPath(List<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            int vertex = path.get(i);
            int row = vertex / numCols;
            int col = vertex % numCols;
            System.out.print("(" + row + "," + col + ")");
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

}
