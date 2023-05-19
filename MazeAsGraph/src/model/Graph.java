package model;

import java.util.List;
import java.util.Map;

public interface Graph<T> {
    void addVertex(T vertex);
    void addEdge(T source, T destination, int weight);
    List<T> getNeighbors(T vertex);
    void bfs(T start);
    void dfs(T start);
    void removeVertex(T value);
    void removeEdge(T source, T destination);
    void clear();
    Map<T, Integer> dijkstra(T start);
}