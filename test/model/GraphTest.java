package model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class GraphTest {

    IGraph<String> graph;

    void initSetup() {
        graph = new AdjacencyList<>();
        //graph = new AdjacencyMatrix<>();
    }

    @Test
    public void testAddVertex() {
        initSetup();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        List<String> vertices = graph.getVertices();

        assertEquals(3, vertices.size());
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("B"));
        assertTrue(vertices.contains("C"));
    }

    @Test
    public void testAddEdge() {
        initSetup();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", 10);

        List<String> neighbors = graph.getNeighbors("A");
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains("B"));
    }

    @Test
    public void testGetNeighbors() {
        initSetup();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B", 10);
        graph.addEdge("A", "C", 5);

        List<String> neighbors = graph.getNeighbors("A");
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains("B"));
        assertTrue(neighbors.contains("C"));
    }

    @Test
    public void testRemoveVertex() {
        initSetup();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B", 10);
        graph.addEdge("B", "C", 5);

        graph.removeVertex("B");

        List<String> vertices = graph.getVertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("C"));

        List<String> neighbors = graph.getNeighbors("A");
        assertEquals(0, neighbors.size());
    }

    @Test
    public void testRemoveEdge() {
        initSetup();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B", 10);
        graph.addEdge("B", "C", 5);

        graph.removeEdge("A", "B");

        List<String> neighbors = graph.getNeighbors("A");
        assertEquals(0, neighbors.size());
    }

    @Test
    public void testDFS() {
        initSetup();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addEdge("A", "B", 10);
        graph.addEdge("B", "C", 5);
        graph.addEdge("C", "D", 3);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        graph.dfs("A");
        String output = outputStream.toString().trim();
        assertEquals("A B C D", output);
    }

    @Test
    public void testBFS() {
        initSetup();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addEdge("A", "B", 10);
        graph.addEdge("A", "C", 5);
        graph.addEdge("B", "D", 7);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        graph.bfs("A");
        String output = outputStream.toString().trim();
        assertEquals("A B C D", output);
    }

    @Test
    public void testDijkstra() {
        initSetup();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addEdge("A", "B", 10);
        graph.addEdge("A", "C", 5);
        graph.addEdge("B", "C", 2);
        graph.addEdge("C", "D", 4);

        Map<String, Pair<Integer, String>> distances = graph.dijkstra("A");

        assertEquals(0, distances.get("A").getFirst().intValue());
        assertEquals(10, distances.get("B").getFirst().intValue());
        assertEquals(5, distances.get("C").getFirst().intValue());
        assertEquals(9, distances.get("D").getFirst().intValue());
    }

    @Test
    public void testEmptyGraph() {
        initSetup();

        List<String> vertices = graph.getVertices();
        assertEquals(0, vertices.size());

        List<String> neighbors = graph.getNeighbors("A");
        assertEquals(0, neighbors.size());

        graph.bfs("A");
        graph.dfs("A");
    }

    @Test
    public void testDisconnectedGraph() {
        initSetup();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", 10);
        graph.addEdge("C", "D", 5);

        List<String> neighborsA = graph.getNeighbors("A");
        assertEquals(1, neighborsA.size());
        assertTrue(neighborsA.contains("B"));

        List<String> neighborsC = graph.getNeighbors("C");
        assertEquals(1, neighborsC.size());
        assertTrue(neighborsC.contains("D"));

        List<String> neighborsB = graph.getNeighbors("B");
        assertEquals(0, neighborsB.size());

        List<String> neighborsD = graph.getNeighbors("D");
        assertEquals(0, neighborsD.size());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        graph.bfs("A");
        String bfsOutput = outputStream.toString().trim();
        assertEquals("A B", bfsOutput);

        outputStream.reset();

        graph.dfs("C");
        String dfsOutput = outputStream.toString().trim();
        assertEquals("C D", dfsOutput);
    }

    @Test
    public void testNegativeEdgeWeights() {
        initSetup();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", -5);

        List<String> neighbors = graph.getNeighbors("A");
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains("B"));

        Map<String, Pair<Integer, String>> distances = graph.dijkstra("A");

        assertEquals(0, distances.get("A").getFirst().intValue());
        assertEquals(-5, distances.get("B").getFirst().intValue());
    }

    @Test
    public void testAddAndRemoveVerticesAndEdges() {
        initSetup();

        // Add vertices A, B, C
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        // Add edges: A -> B, B -> C, C -> A
        graph.addEdge("A", "B", 10);
        graph.addEdge("B", "C", 5);
        graph.addEdge("C", "A", 3);

        List<String> neighborsA = graph.getNeighbors("A");
        List<String> neighborsB = graph.getNeighbors("B");
        List<String> neighborsC = graph.getNeighbors("C");

        // Check neighbors
        assertEquals(1, neighborsA.size());
        assertEquals(1, neighborsB.size());
        assertEquals(1, neighborsC.size());
        assertTrue(neighborsA.contains("B"));
        assertTrue(neighborsB.contains("C"));
        assertTrue(neighborsC.contains("A"));

        // Remove vertex B
        graph.removeVertex("B");

        neighborsA = graph.getNeighbors("A");
        neighborsC = graph.getNeighbors("C");

        // Check neighbors after removal
        assertEquals(0, neighborsA.size());
        assertEquals(1, neighborsC.size());

        // Remove non-existing edge A -> B
        graph.removeEdge("A", "B");

        // Clear the graph
        graph.clear();

        List<String> vertices = graph.getVertices();

        // Check if the graph is empty
        assertEquals(0, vertices.size());
    }

    @Test
    public void testLargeGraph() {
        initSetup();

        // Add 1000 vertices (0 to 999)
        for (int i = 0; i < 1000; i++) {
            graph.addVertex(String.valueOf(i));
        }

        // Connect vertices with edges: i -> (i+1) for i from 0 to 998
        for (int i = 0; i < 999; i++) {
            graph.addEdge(String.valueOf(i), String.valueOf(i + 1), 1);
        }

        List<String> neighbors0 = graph.getNeighbors("0");
        List<String> neighbors999 = graph.getNeighbors("999");

        // Check neighbors of vertices 0 and 999
        assertEquals(1, neighbors0.size());
        assertEquals(0, neighbors999.size());
        assertTrue(neighbors0.contains("1"));

        // Remove vertex 500
        graph.removeVertex("500");

        List<String> vertices = graph.getVertices();

        // Check the number of vertices after removal
        assertEquals(999, vertices.size());
        assertFalse(vertices.contains("500"));

        // Remove edge 998 -> 999
        graph.removeEdge("998","999");

        List<String> neighbors998 = graph.getNeighbors("998");

        // Check neighbors of vertex 998 after edge removal
        assertEquals(0, neighbors998.size());
    }

    @Test
    public void testDuplicateEdges() {
        initSetup();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "B", 10); // Duplicate edge

        List<String> neighborsA = graph.getNeighbors("A");

        // Check neighbors of vertex A (should have only one edge to B)
        assertEquals(1, neighborsA.size());
        assertTrue(neighborsA.contains("B"));
    }

    @Test
    public void testDirectedCyclicGraph() {
        initSetup();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("C", "D", 3);
        graph.addEdge("D", "A", 4);

        List<String> neighborsA = graph.getNeighbors("A");
        List<String> neighborsB = graph.getNeighbors("B");
        List<String> neighborsC = graph.getNeighbors("C");
        List<String> neighborsD = graph.getNeighbors("D");

        // Check neighbors of each vertex
        assertEquals(1, neighborsA.size());
        assertEquals(1, neighborsB.size());
        assertEquals(1, neighborsC.size());
        assertEquals(1, neighborsD.size());

        assertTrue(neighborsA.contains("B"));
        assertTrue(neighborsB.contains("C"));
        assertTrue(neighborsC.contains("D"));
        assertTrue(neighborsD.contains("A"));
    }

    @Test
    public void testGraphWithSelfLoops() {
        initSetup();

        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");

        graph.addEdge("1", "1", 0); // Self-loop on vertex 1
        graph.addEdge("2", "3", 5);
        graph.addEdge("3", "4", 2);
        graph.addEdge("4", "2", 3);

        List<String> neighbors1 = graph.getNeighbors("1");
        List<String> neighbors2 = graph.getNeighbors("2");
        List<String> neighbors3 = graph.getNeighbors("3");
        List<String> neighbors4 = graph.getNeighbors("4");

        // Check neighbors of each vertex
        assertEquals(1, neighbors1.size());
        assertEquals(1, neighbors2.size());
        assertEquals(1, neighbors3.size());
        assertEquals(1, neighbors4.size());

        assertTrue(neighbors1.contains("1"));
        assertTrue(neighbors2.contains("3"));
        assertTrue(neighbors3.contains("4"));
        assertTrue(neighbors4.contains("2"));
    }

    @Test
    public void testLargeDenseGraph() {
        initSetup();

        // Add 100 vertices (1 to 100)
        for (int i = 1; i <= 100; i++) {
            graph.addVertex(String.valueOf(i));
        }

        // Connect each vertex to every other vertex with an edge of weight 1
        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                if (i != j) {
                    graph.addEdge(String.valueOf(i), String.valueOf(j), 1);
                }
            }
        }

        List<String> neighbors1 = graph.getNeighbors("1");
        List<String> neighbors50 = graph.getNeighbors("50");
        List<String> neighbors100 = graph.getNeighbors("100");

        // Check neighbors of vertices 1, 50, and 100
        assertEquals(99, neighbors1.size());
        assertEquals(99, neighbors50.size());
        assertEquals(99, neighbors100.size());

        for (int i = 2; i <= 100; i++) {
            assertTrue(neighbors1.contains(String.valueOf(i)) || i == 1);
            assertTrue(neighbors50.contains(String.valueOf(i)) || i == 50);
            assertTrue(neighbors100.contains(String.valueOf(i)) || i == 100);
        }
    }

    @Test
    public void testFloydWarshall() {
        initSetup();
        // Add vertex to the graph
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        // Add edges with their respective weight
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "C", 2);

        //Execute Floyd-Warshall algorithm 
        Map<String, Map<String, Integer>> distances = graph.floydWarshall();

        //Verify the minimum path expected
        assertEquals(0, distances.get("A").get("A").intValue());
        assertEquals(1, distances.get("A").get("B").intValue());
        assertEquals(3, distances.get("A").get("C").intValue());
        assertEquals(Integer.MAX_VALUE, distances.get("B").get("A").intValue());
        assertEquals(0, distances.get("B").get("B").intValue());
        assertEquals(2, distances.get("B").get("C").intValue());
        assertEquals(Integer.MAX_VALUE, distances.get("C").get("A").intValue());
        assertEquals(Integer.MAX_VALUE, distances.get("C").get("B").intValue());
        assertEquals(0, distances.get("C").get("C").intValue());
    }

    @Test
    public void testPrimMST() {
        initSetup();

        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");

        graph.addEdge("1", "2", 10);
        graph.addEdge("2", "3", 15);
        graph.addEdge("1", "3", 5);
        graph.addEdge("2", "4", 2);
        graph.addEdge("3", "4", 6);

        Map<String, String> mst = graph.primMST();

        assertEquals(4, mst.size());
        assertTrue(mst.containsKey("1"));
        assertTrue(mst.containsKey("2"));
        assertTrue(mst.containsKey("3"));
        assertNull(mst.get("1"));
        assertEquals("1", mst.get("2"));
        assertEquals("1", mst.get("3"));
    }

    @Test
    public void testKruskalMST() {
        initSetup();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");

        graph.addEdge("A", "B", 10);
        graph.addEdge("A", "C", 6);
        graph.addEdge("A", "D", 5);
        graph.addEdge("B", "D", 15);
        graph.addEdge("C", "D", 4);
        graph.addEdge("C", "E", 2);
        graph.addEdge("D", "E", 8);

        List<Edge<String>> mst = graph.kruskalMST();

        // Verificar que se haya obtenido el MST correcto
        assertEquals(4, mst.size());

        int totalWeight = 0;
        for (Edge<String> edge : mst) {
            totalWeight += edge.getWeight();
        }
        assertEquals(19, totalWeight);
    }

}
