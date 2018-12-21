package com.haedhutner.graphs;

import org.junit.Assert;
import org.junit.Test;

public class GraphTest {

    public static final String NODE_A = "A";
    public static final String NODE_B = "B";
    public static final String NODE_C = "C";
    public static final String NODE_D = "D";
    public static final String NODE_E = "E";
    public static final String NODE_F = "F";

    @Test
    public void graphLinkTest() {
        Graph<String> graph = new Graph<>();

        graph.insert(NODE_A);
        graph.insert(NODE_A, NODE_B);
        graph.insert(NODE_A, NODE_C);
        graph.insert(NODE_A, NODE_D);

        graph.insert(NODE_D, NODE_E);
        graph.insert(NODE_D, NODE_F);

        graph.findNode(NODE_B);

        Assert.assertTrue("Failed to create link: B <--> D", graph.link(NODE_B, NODE_D));
        Assert.assertTrue("Failed to create link: B <--> E", graph.link(NODE_B, NODE_E));
        Assert.assertTrue("Failed to create link: D <--> E", graph.link(NODE_D, NODE_E));
        Assert.assertTrue("Failed to create link: E <--> F", graph.link(NODE_E, NODE_F));
        Assert.assertTrue("Failed to create link: C <--> F", graph.link(NODE_C, NODE_F));
    }

}
