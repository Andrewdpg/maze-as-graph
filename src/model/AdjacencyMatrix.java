package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class AdjacencyMatrix<T> implements IGraph<T> {

    private HashMap<T, HashMap<T, Integer>> map = new HashMap<>();

    @Override
    public void addVertex(T value) {
        map.putIfAbsent(value, new HashMap<>());
    }

    @Override
    public void addEdge(T source, T destination, int weight) {
        addVertex(source);
        addVertex(destination);
        map.get(source).putIfAbsent(destination, weight);
    }

    @Override
    public List<T> getNeighbors(T value) {
        HashMap<T, Integer> nodes = map.get(value);
        if (nodes == null) {
            return Collections.emptyList();
        } else {
            List<T> neighbors = new ArrayList<>();
            for (T neighbor : nodes.keySet()) {
                neighbors.add(neighbor);
            }
            return neighbors;
        }
    }

    @Override
    public void bfs(T start) {
        if (!map.containsKey(start))
            return;
        Map<T, Boolean> visited = new HashMap<>();
        Queue<T> queue = new LinkedList<>();
        visited.put(start, true);
        queue.add(start);

        while (!queue.isEmpty()) {
            T currentNode = queue.poll();
            System.out.print(currentNode + " ");
            Set<T> edges = map.get(currentNode).keySet();
            for (T neighbor : edges) {
                if (!visited.containsKey(neighbor)) {
                    visited.put(neighbor, true);
                    queue.add(neighbor);
                }
            }
        }
    }

    @Override
    public void dfs(T start) {
        if (!map.containsKey(start))
            return;
        Map<T, Boolean> visited = new HashMap<>();
        Stack<T> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            T currentNode = stack.pop();
            if (visited.containsKey(currentNode))
                continue;

            visited.put(currentNode, true);
            System.out.print(currentNode + " ");
            Set<T> edges = map.get(currentNode).keySet();
            for (T neighbor : edges) {
                if (!visited.containsKey(neighbor)) {
                    stack.push(neighbor);
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
        if (map.containsKey(value)) {
            Set<T> edges = map.keySet();
            for (T node : edges) {
                map.get(node).remove(value);
            }
            map.remove(value);
        }
    }

    @Override
    public void removeEdge(T source, T destination) {
        if (map.containsKey(source) && map.containsKey(destination)) {
            map.get(source).remove(destination);
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
        PriorityQueue<T> queue = new PriorityQueue<>(
                (n1, n2) -> Integer.compare(distances.get(n1).getFirst(), distances.get(n2).getFirst()));

        for (T vertex : map.keySet()) {
            distances.put(vertex, new Pair<>(Integer.MAX_VALUE, null));
        }

        distances.put(start, new Pair<>(0, null));

        queue.add(start);

        while (!queue.isEmpty()) {
            T currentNode = queue.poll();

            if (visited.containsKey(currentNode)) {
                continue;
            }

            visited.put(currentNode, true);

            Set<T> edges = map.get(currentNode).keySet();
            for (T neighbor : edges) {
                Integer distanceToNeighbor = distances.get(currentNode).getFirst() + map.get(currentNode).get(neighbor);
                if (distanceToNeighbor < distances.get(neighbor).getFirst()) {
                    distances.put(neighbor, new Pair<>(distanceToNeighbor, currentNode));
                    queue.add(neighbor);
                }
            }
        }

        return distances;
    }

    public Map<T, Map<T, Integer>> floydWarshall() {
        Map<T, Map<T, Integer>> distances = new HashMap<>(map);
        List<T> vertices = new ArrayList<>(map.keySet());

        for (T k : vertices) {
            for (T i : vertices) {
                for (T j : vertices) {
                    int ik = distances.get(i).get(k);
                    int kj = distances.get(k).get(j);
                    int ij = distances.get(i).get(j);

                    if (ik != Integer.MAX_VALUE && kj != Integer.MAX_VALUE && ik + kj < ij) {
                        distances.get(i).put(j, ik + kj);
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

        key.put(getVertices().get(0), 0);

        for (int count = 0; count < map.size() - 1; count++) {
            T u = getMinKey(key, mstSet);
            mstSet.put(u, true);

            for (T v : getNeighbors(u)) {
                int weight = map.get(u).get(v);

                if (!mstSet.get(v) && weight < key.get(v)) {
                    parent.put(v, u);
                    key.put(v, weight);
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
