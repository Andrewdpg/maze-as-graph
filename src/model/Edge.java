package model;

public class Edge<T> {

    private Node<T> source;
    private Node<T> node;
    private int weight;

    public Edge(Node<T> source, Node<T> neighbor, int weight) {
        this.source= source;
        this.node = neighbor;
        this.weight = weight;
    }

    public Node<T> getNode() {
        return node;
    }

    public void setNode(Node<T> neighbor) {
        this.node = neighbor;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node<T> getSource() {
        return source;
    }

    public void setSource(Node<T> source) {
        this.source = source;
    }
}
