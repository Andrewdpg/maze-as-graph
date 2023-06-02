package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class AdjacencyList<T extends Comparable<T>> implements IGraph<T> {
    private Map<T, Node<T>> map = new HashMap<>();

    @Override
    public void addVertex(T value) {
        if (!map.containsKey(value)) {
            Node<T> node = new Node<>(value);
            map.put(value, node);
        }
    }

    @Override
    public void addEdge(T source, T destination, int weight) {
        Node<T> sourceNode = map.get(source);
        Node<T> destinationNode = map.get(destination);

        if (sourceNode == null) {
            sourceNode = new Node<>(source);
            map.put(source, sourceNode);
        }

        if (destinationNode == null) {
            destinationNode = new Node<>(destination);
            map.put(destination, destinationNode);
        }

        sourceNode.addNeighbor(new Edge<>(destinationNode, weight));
    }

    @Override
    public List<T> getNeighbors(T value) {
        Node<T> node = map.get(value);
        if (node == null) {
            return Collections.emptyList();
        } else {
            List<T> neighbors = new ArrayList<>();
            for (Edge<T> neighbor : node.getEdges()) {
                neighbors.add(neighbor.getNode().getValue());
            }
            return neighbors;
        }
    }

    @Override
    public void bfs(T start) {
        if (!map.containsKey(start))
            return;
        Map<T, Boolean> visited = new HashMap<>();
        Queue<Node<T>> queue = new LinkedList<>();

        Node<T> startNode = map.get(start);
        visited.put(start, true);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node<T> currentNode = queue.poll();
            System.out.print(currentNode.getValue() + " ");

            for (Edge<T> neighbor : currentNode.getEdges()) {
                T neighborValue = neighbor.getNode().getValue();
                if (!visited.containsKey(neighborValue)) {
                    visited.put(neighborValue, true);
                    queue.add(neighbor.getNode());
                }
            }
        }
    }

    @Override
    public void dfs(T start) {
        if (!map.containsKey(start))
            return;
        Map<T, Boolean> visited = new HashMap<>();
        Stack<Node<T>> stack = new Stack<>();

        Node<T> startNode = map.get(start);
        stack.push(startNode);

        while (!stack.isEmpty()) {
            Node<T> currentNode = stack.pop();
            if (visited.containsKey(currentNode.getValue()))
                continue;

            visited.put(currentNode.getValue(), true);
            System.out.print(currentNode.getValue() + " ");

            for (Edge<T> neighbor : currentNode.getEdges()) {
                if (!visited.containsKey(neighbor.getNode().getValue())) {
                    stack.push(neighbor.getNode());
                }
            }
        }
    }

    @Override
    public List<T> getVertices() {
        return new ArrayList<>(map.keySet());
    }

    @Override
    public void removeVertex(T value) {
        Node<T> node = map.get(value);
        if (node != null) {
            for (Node<T> vertex : map.values()) {
                vertex.removeNeighbor(node);
            }
            map.remove(value);
        }
    }

    @Override
    public void removeEdge(T source, T destination) {
        Node<T> sourceNode = map.get(source);
        Node<T> destinationNode = map.get(destination);
        if (sourceNode != null && destinationNode != null) {
            sourceNode.removeNeighbor(destinationNode);
            destinationNode.removeNeighbor(sourceNode);
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Map<T, Pair<Integer, T>> dijkstra(T start) {
        if (!map.containsKey(start))
            return new HashMap<>();
        Map<T, Pair<Integer, T>> distances = new HashMap<>();
        Map<T, Boolean> visited = new HashMap<>();
        PriorityQueue<Node<T>> queue = new PriorityQueue<>(
                (n1, n2) -> Integer.compare(distances.get(n1.getValue()).getFirst(), distances.get(n2.getValue())
                        .getFirst()));

        for (T vertex : map.keySet()) {
            distances.put(vertex, new Pair<>(Integer.MAX_VALUE, null));
        }

        distances.put(start, new Pair<>(0, null));

        Node<T> startNode = map.get(start);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node<T> currentNode = queue.poll();
            T currentVertex = currentNode.getValue();

            if (visited.containsKey(currentVertex)) {
                continue;
            }

            visited.put(currentVertex, true);

            for (Edge<T> edge : currentNode.getEdges()) {
                T neighborVertex = edge.getNode().getValue();
                Integer distanceToNeighbor = distances.get(currentVertex).getFirst() + edge.getWeight();
                if (distanceToNeighbor < distances.get(neighborVertex).getFirst()) {
                    distances.put(neighborVertex, new Pair<>(distanceToNeighbor, currentNode.getValue()));
                    queue.add(edge.getNode());
                }
            }
        }

        return distances;
    }

    public Map<T, Map<T, Integer>> floydWarshall() {
        Map<T, Map<T, Integer>> distances = new HashMap<>();

        for (T vertex : map.keySet()) {
            distances.put(vertex, new HashMap<>());
            for (T otherVertex : map.keySet()) {
                if (vertex.equals(otherVertex)) {
                    distances.get(vertex).put(otherVertex, 0);
                } else {
                    distances.get(vertex).put(otherVertex, Integer.MAX_VALUE);
                }
            }
        }

        for (Node<T> node : map.values()) {
            for (Edge<T> edge : node.getEdges()) {
                distances.get(node.getValue()).put(edge.getNode().getValue(), edge.getWeight());
            }
        }

        for (T k : map.keySet()) {
            for (T i : map.keySet()) {
                for (T j : map.keySet()) {
                    int distanceIK = distances.get(i).get(k);
                    int distanceKJ = distances.get(k).get(j);
                    int distanceIJ = distances.get(i).get(j);

                    if (distanceIK != Integer.MAX_VALUE && distanceKJ != Integer.MAX_VALUE
                            && distanceIK + distanceKJ < distanceIJ) {
                        distances.get(i).put(j, distanceIK + distanceKJ);
                    }
                }
            }
        }

        return distances;
    }

    public Map<T, T> primMST() {
        if (map.isEmpty()) {
            return new HashMap<>();
        }

        Map<T, Integer> key = new HashMap<>();
        Map<T, T> parent = new HashMap<>();
        Map<T, Boolean> mstSet = new HashMap<>();

        for (T vertex : map.keySet()) {
            key.put(vertex, Integer.MAX_VALUE);
            parent.put(vertex, null);
            mstSet.put(vertex, false);
        }

        key.put(map.keySet().iterator().next(), 0);

        for (int count = 0; count < map.size() - 1; count++) {
            T u = getMinKey(key, mstSet);
            mstSet.put(u, true);

            for (Edge<T> edge : map.get(u).getEdges()) {
                T v = edge.getNode().getValue();

                if (!mstSet.get(v) && edge.getWeight() < key.get(v)) {
                    parent.put(v, u);
                    key.put(v, edge.getWeight());
                }
            }
        }

        return parent;
    }

    private T getMinKey(Map<T, Integer> key, Map<T, Boolean> mstSet) {
        int min = Integer.MAX_VALUE;
        T minKey = null;

        for (T vertex : map.keySet()) {
            if (!mstSet.get(vertex) && key.get(vertex) < min) {
                min = key.get(vertex);
                minKey = vertex;
            }
        }

        return minKey;
    }
}