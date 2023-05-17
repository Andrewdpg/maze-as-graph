package model;

import java.util.*;

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

        sourceNode.addNeighbor(destinationNode, weight);

    }

    @Override
    public List<T> getNeighbors(T value) {
        Node<T> node = map.get(value);
        if (node == null) {
            return Collections.emptyList();
        } else {
            List<T> neighbors = new ArrayList<>();
            for (Node<T> neighbor : node.getNeighbors()) {
                neighbors.add(neighbor.getValue());
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

            for (Node<T> neighbor : currentNode.getNeighbors()) {
                T neighborValue = neighbor.getValue();
                if (!visited.containsKey(neighborValue)) {
                    visited.put(neighborValue, true);
                    queue.add(neighbor);
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

            for (Node<T> neighbor : currentNode.getNeighbors()) {
                if (!visited.containsKey(neighbor.getValue())) {
                    stack.push(neighbor);
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
            for (Node<T> neighbor : node.getNeighbors()) {
                neighbor.removeNeighbor(node);
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
    
            for (Node<T> neighbor : currentNode.getNeighbors()) {
                T neighborVertex = neighbor.getValue();
                int distanceToNeighbor = distances.get(currentVertex) + 1;
                if (distanceToNeighbor < distances.get(neighborVertex)) {
                    distances.put(neighborVertex, distanceToNeighbor);
                    queue.add(neighbor);
                }
            }
        }
    
        return distances;
    }
    

}
