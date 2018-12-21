package com.haedhutner.graphs;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class GraphTest {

    public static final String NODE_A = "A";
    public static final String NODE_B = "B";
    public static final String NODE_C = "C";
    public static final String NODE_D = "D";
    public static final String NODE_E = "E";
    public static final String NODE_F = "F";

    public Graph<String> graph = new Graph<>();

    @Test
    public void insertTest() {
        graph.insert(NODE_A);
        graph.insert(NODE_A, NODE_B);
        graph.insert(NODE_A, NODE_C);
        graph.insert(NODE_A, NODE_D);

        graph.insert(NODE_D, NODE_E);
        graph.insert(NODE_D, NODE_F);

        Assert.assertTrue("Failed to confirm link: A <--> B", graph.areLinked(NODE_A, NODE_B));
        Assert.assertTrue("Failed to confirm link: A <--> C", graph.areLinked(NODE_A, NODE_C));
        Assert.assertTrue("Failed to confirm link: A <--> D", graph.areLinked(NODE_A, NODE_D));
        Assert.assertTrue("Failed to confirm link: D <--> E", graph.areLinked(NODE_D, NODE_E));
        Assert.assertTrue("Failed to confirm link: D <--> F", graph.areLinked(NODE_D, NODE_F));
    }

    @Test
    public void findNodeTest() {
        Assert.assertEquals("Failed to find Node A.", Optional.of(Graph.Node.of(NODE_A)), graph.findNode(NODE_A));
        Assert.assertEquals("Failed to find Node B.", Optional.of(Graph.Node.of(NODE_B)), graph.findNode(NODE_B));
        Assert.assertEquals("Failed to find Node C.", Optional.of(Graph.Node.of(NODE_C)), graph.findNode(NODE_C));
        Assert.assertEquals("Failed to find Node D.", Optional.of(Graph.Node.of(NODE_D)), graph.findNode(NODE_D));
        Assert.assertEquals("Failed to find Node E.", Optional.of(Graph.Node.of(NODE_E)), graph.findNode(NODE_E));
        Assert.assertEquals("Failed to find Node F.", Optional.of(Graph.Node.of(NODE_F)), graph.findNode(NODE_F));
    }

    @Test
    public void linkTest() {
        Assert.assertTrue("Failed to create link: B <--> D.", graph.link(NODE_B, NODE_D));
        Assert.assertTrue("Failed to create link: B <--> E.", graph.link(NODE_B, NODE_E));
        Assert.assertTrue("Failed to create link: D <--> E.", graph.link(NODE_D, NODE_E));
        Assert.assertTrue("Failed to create link: E <--> F.", graph.link(NODE_E, NODE_F));
        Assert.assertTrue("Failed to create link: C <--> F.", graph.link(NODE_C, NODE_F));
    }

}
