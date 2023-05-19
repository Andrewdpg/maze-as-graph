package model;

import java.util.ArrayList;
import java.util.List;

public class Node<T> implements Comparable<Node<T>> {

    private T value;
    private List<Node<T>> neighbors;
    private List<Integer> weights; 

    public Node(T value) {
        this.value = value;
        this.neighbors = new ArrayList<>();
        this.weights = new ArrayList<>();
    }

    public T getValue() {
        return value;
    }

    public void addNeighbor(Node<T> neighbor, int weight) {
        if (!neighbors.contains(neighbor)) {
            neighbors.add(neighbor);
            weights.add(weight);
        }
    }

    public void removeNeighbor(Node<T> neighbor) {
        int index = neighbors.indexOf(neighbor);
        if (index != -1) {
            neighbors.remove(index);
            weights.remove(index);
        }
    }

    public List<Node<T>> getNeighbors() {
        return neighbors;
    }

    @Override
    public int compareTo(Node<T> o) {
        return 0;
    }
}