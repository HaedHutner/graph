package com.haedhutner.graphs;

public class Application {

    public static final String NODE_A = "A";
    public static final String NODE_B = "B";
    public static final String NODE_C = "C";
    public static final String NODE_D = "D";
    public static final String NODE_E = "E";
    public static final String NODE_F = "F";

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();

        graph.insert(NODE_A);
        graph.insert(NODE_A, NODE_B);
        graph.insert(NODE_A, NODE_C);
        graph.insert(NODE_A, NODE_D);

        graph.insert(NODE_D, NODE_E);
        graph.insert(NODE_D, NODE_F);

        graph.findNode(NODE_B);

        System.out.printf("Link Node B with Node D: %b%n", graph.link(NODE_B, NODE_D));
        System.out.printf("Link Node B with Node E: %b%n", graph.link(NODE_B, NODE_E));

        System.out.printf("Link Node D with Node E: %b%n", graph.link(NODE_D, NODE_E));

        System.out.printf("Link Node E with Node F: %b%n", graph.link(NODE_E, NODE_F));

        System.out.printf("Link Node C with Node F: %b%n", graph.link(NODE_C, NODE_F));
    }

}
