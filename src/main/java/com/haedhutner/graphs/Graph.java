package com.haedhutner.graphs;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Graph<T> {

    public static class Node<T> {

        private T data;

        private Set<Edge<T>> links = new HashSet<>();

        Node(T data) {
            this.data = data;
        }

        public static <T> Node<T> of(T data) {
            return new Node<>(data);
        }

        public T get() {
            return data;
        }

        public Set<Edge<T>> getLinks() {
            return links;
        }

        public boolean addLink(Node<T> target) {
            Edge<T> edge = Edge.of(this, target);

            if ( links.contains(edge) ) return false;

            boolean result = links.add(edge);

            if (!target.containsLink(this)) {
                result = result && target.addLink(this);
            }

            return result;
        }

        public boolean removeLink(Node<T> target) {
            Edge<T> edge = Edge.of(this, target);

            if ( !links.contains(edge) ) return false;

            boolean result = links.remove(edge);

            if (target.containsLink(this)) {
                result = result && target.removeLink(this);
            }

            return result;
        }

        public boolean containsLink(Node<T> criteria) {
            return links.stream().anyMatch(edge -> edge.getTarget().equals(criteria));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(data, node.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }

    public static class Edge<T> {

        private Node<T> source;
        private Node<T> target;

        Edge(Node<T> source, Node<T> target) {
            this.source = source;
            this.target = target;
        }

        public static <T> Edge<T> of(Node<T> source, Node<T> target) {
            return new Edge<>(source, target);
        }

        public Node<T> getSource() {
            return source;
        }

        public Node<T> getTarget() {
            return target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?> edge = (Edge<?>) o;
            return Objects.equals(source, edge.source) &&
                    Objects.equals(target, edge.target);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, target);
        }
    }

    private Node<T> root;

    public Graph() {
    }

    public Graph(T firstElement) {
        this.root = Node.of(firstElement);
    }

    public Node<T> getRoot() {
        return root;
    }

    public void insert(T data) {
        if (root == null) {
            root = Node.of(data);
        } else {
            root.addLink(Node.of(data));
        }
    }

    public void insert(T parent, T data) {
        findNode(parent).ifPresent(parentNode -> {
            parentNode.addLink(Node.of(data));
        });
    }

    public void remove(T data) {
        findNode(data).ifPresent(node -> node.getLinks().forEach(edge -> node.removeLink(edge.getTarget())));
    }

    /**
     * Link 2 nodes with the provided data values
     *
     * @param source the source node
     * @param target the target node
     * @return true, if both the source and target node already exist in the graph and have been linked successfully.
     * False if otherwise.
     */
    public boolean link(T source, T target) {
        Optional<Node<T>> sourceNode = findNode(source);
        Optional<Node<T>> targetNode = findNode(target);

        if (sourceNode.isPresent() && targetNode.isPresent()) {
            return sourceNode.get().addLink(targetNode.get());
        }

        return false;
    }

    /**
     * Remove the link between 2 nodes with the provided data values
     *
     * @param source the source data
     * @param target the target data
     * @return true, if the nodes were unlinked successfully. False if otherwise.
     */
    public boolean unlink(T source, T target) {
        Optional<Node<T>> sourceNode = findNode(source);
        Optional<Node<T>> targetNode = findNode(target);

        if (sourceNode.isPresent() && targetNode.isPresent()) {
            return sourceNode.get().removeLink(targetNode.get());
        }

        return false;
    }

    public boolean areLinked(T source, T target) {
        Optional<Node<T>> sourceNode = findNode(source);
        Optional<Node<T>> targetNode = findNode(target);

        if (sourceNode.isPresent() && targetNode.isPresent()) {
            return sourceNode.get().containsLink(targetNode.get());
        }

        return false;
    }

    public Optional<Node<T>> findNode(T data) {
        return Optional.ofNullable(depthFirstSearch(new HashSet<>(), root, data));
    }

    private Node<T> depthFirstSearch(Set<Node<T>> checkedNodes, Node<T> start, T criteria) {

        if (start.get().equals(criteria)) return start;
        else {
            checkedNodes.add(start); // set starting node to checked
            Node<T> result = null;

            for (Edge<T> edge : start.getLinks()) {
                if (!checkedNodes.contains(edge.getTarget())) { // if target has not already been checked, check it
                    result = depthFirstSearch(checkedNodes, edge.getTarget(), criteria);
                }
            }

            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph<?> graph = (Graph<?>) o;
        return Objects.equals(root, graph.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root);
    }
}
