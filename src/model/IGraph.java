package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IGraph<T> {
    void addVertex(T vertex);
    void addEdge(T source, T destination, int weight);
    List<T> getVertices();
    List<T> getNeighbors(T vertex);
    List<T> bfs(T start);
    void dfs(T start);
    void removeVertex(T value);
    void removeEdge(T source, T destination);
    void clear();
    Map<T, Pair<Integer, T>> dijkstra(T start);
    Map<T, Map<T, Integer>> floydWarshall();
    Map<T, T> primMST();
    List<Edge<T>> kruskalMST();
}

class DisjointSet<T> {
    private Map<T, Node<T>> map = new HashMap<>();

    private static class Node<T> {
        private T value;
        private Node<T> parent;
        private int rank;

        public Node(T value) {
            this.value = value;
            this.parent = this;
            this.rank = 0;
        }
    }

    public void makeSet(T value) {
        if (!map.containsKey(value)) {
            Node<T> node = new Node<>(value);
            map.put(value, node);
        }
    }

    public T findSet(T value) {
        Node<T> node = map.get(value);
        if (node == null) {
            return null;
        }
        Node<T> root = findSetRoot(node);
        return root.value;
    }

    private Node<T> findSetRoot(Node<T> node) {
        if (node != node.parent) {
            node.parent = findSetRoot(node.parent);
        }
        return node.parent;
    }

    public void union(T value1, T value2) {
        Node<T> node1 = map.get(value1);
        Node<T> node2 = map.get(value2);
        if (node1 == null || node2 == null) {
            return;
        }
        Node<T> root1 = findSetRoot(node1);
        Node<T> root2 = findSetRoot(node2);

        if (root1 == root2) {
            return;
        }

        if (root1.rank < root2.rank) {
            root1.parent = root2;
        } else if (root1.rank > root2.rank) {
            root2.parent = root1;
        } else {
            root2.parent = root1;
            root1.rank++;
        }
    }
}
