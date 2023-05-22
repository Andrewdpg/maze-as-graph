package model;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private T value;
    private List<Edge<T>> edges;

    public Node(T value) {
        this.value = value;
        this.edges = new ArrayList<>();
    }

    public T getValue() {
        return value;
    }

    public void addNeighbor(Edge<T> edge) {
        for (Edge<T> neighbor : edges) {
            if(neighbor.getNode().equals(edge.getNode())){
                return;
            }
        }
        edges.add(edge);
    }

    public void removeNeighbor(Node<T> neighbor) {
        edges.removeIf((a) -> {
            return a.getNode().equals(neighbor);
        });
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }
}