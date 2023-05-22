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

public class AdjacencyList<T> implements Graph<T> {
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

    public List<T> getVertices() {
        return new ArrayList<>(map.keySet());
    }

    @Override
    public void removeVertex(T value) {
        Node<T> node = map.get(value);
        if (node != null) {
            for (Edge<T> neighbor : node.getEdges()) {
                neighbor.getNode().removeNeighbor(node);
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
    public Map<T, Integer> dijkstra(T start) {
        Map<T, Integer> distances = new HashMap<>();
        Map<T, Boolean> visited = new HashMap<>();
        PriorityQueue<Node<T>> queue = new PriorityQueue<>();
    
        for (T vertex : map.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
    
        distances.put(start, 0);
    
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
                int distanceToNeighbor = distances.get(currentVertex) + edge.getWeight();
                if (distanceToNeighbor < distances.get(neighborVertex)) {
                    distances.put(neighborVertex, distanceToNeighbor);
                    queue.add(edge.getNode());
                }
            }
        }
    
        return distances;
    }
    

}
