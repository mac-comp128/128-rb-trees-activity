package treePractice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class RBTest {
    RBTree<Integer> rbTree;
    RBTree<Integer> randomTree;

    @BeforeEach
    public void BuildTree() {
        rbTree = new RBTree<>();
        /* Constructing tree given in the above figure */
        rbTree.add(9);
        rbTree.add(5);
        rbTree.add(10);
        rbTree.add(0);
        rbTree.add(6);
        rbTree.add(11);
        rbTree.add(-1);
        rbTree.add(1);
        rbTree.add(2);
    }

        /*
     * Computes the black height of the tree and asserts the black heights of subtrees
     * are equal
     * @return the black height of the tree
     */
    private void assertBlackProperty(RBTree<Integer> tree) {
        assertBlackProperty((RBTree.RBNode<Integer>) tree.root);
    }

    private int assertBlackProperty(RBTree.RBNode<Integer> localRoot) {
        if (localRoot == null) {
            return 0;
        }
        int left_bh = assertBlackProperty((RBTree.RBNode<Integer>) localRoot.left);
        int right_bh = assertBlackProperty((RBTree.RBNode<Integer>) localRoot.right);
        assertEquals(left_bh, right_bh, String.format("Black Violation at Nodes %s in Tree \n%s",
                                                      localRoot.toString(),
                                                      randomTree.toString()));

        return (localRoot.black? (1 + left_bh) : left_bh);
        
    }

    /*
     * Asserts that all red nodes have black parents.
     */
    private void assertRedProperty(RBTree<Integer> tree) {
        if (tree.root != null) assertRedProperty((RBTree.RBNode<Integer>) tree.root);
    }

    private void assertRedProperty(RBTree.RBNode<Integer> localRoot) {
        // A red node cannot have a red parent
        if (!localRoot.black && (localRoot.parent != null)) {
            RBTree.RBNode<Integer> parent = (RBTree.RBNode<Integer>) localRoot.parent;
            assertTrue(parent.black, String.format("Red Violation at Nodes %s, %s in Tree \n%s" , 
                                                    parent.toString(), 
                                                    localRoot.toString(),
                                                    randomTree.toString()));
        }
        if (localRoot.left != null) assertRedProperty((RBTree.RBNode<Integer>) localRoot.left);
        if (localRoot.right != null) assertRedProperty((RBTree.RBNode<Integer>) localRoot.right);
    }

    @RepeatedTest(100)
    public void testAddRandomized() {
        Random rand = new Random();
        randomTree = new RBTree<Integer>();

        for (int i = 0; i < 10; i++) {
            randomTree.add(rand.nextInt(25));

            assertRedProperty(randomTree);
            assertBlackProperty(randomTree);
        }
    }

    @RepeatedTest(100)
    public void testDeleteRandomized() {
        Random rand = new Random();
        randomTree = new RBTree<Integer>();
        List<Integer> elements = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++) {
            Integer e = rand.nextInt(25);
            randomTree.add(e);
            elements.add(e);
        }
        Collections.shuffle(elements);
        for (Integer e : elements) {
            randomTree.delete(e);

            System.err.println(randomTree.toString());
            assertRedProperty(randomTree);
            assertBlackProperty(randomTree);
        }
    }

    @Test
    public void testAdd() {
        assertEquals(
                "5 (B)\n" + //
                "  0 (R)\n" + //
                "    -1 (B)\n" + //
                "      null\n" + //
                "      null\n" + //
                "    1 (B)\n" + //
                "      null\n" + //
                "      2 (R)\n" + //
                "        null\n" + //
                "        null\n" + //
                "  9 (R)\n" + //
                "    6 (B)\n" + //
                "      null\n" + //
                "      null\n" + //
                "    10 (B)\n" + //
                "      null\n" + //
                "      11 (R)\n" + //
                "        null\n" + //
                "        null\n",
                rbTree.toString());
    }
    @Test
    public void testDelete() {
        rbTree.delete(6);
        assertEquals(
                "5 (B)\n" + //
                "  0 (R)\n" + //
                "    -1 (B)\n" + //
                "      null\n" + //
                "      null\n" + //
                "    1 (B)\n" + //
                "      null\n" + //
                "      2 (R)\n" + //
                "        null\n" + //
                "        null\n" + //
                "  10 (R)\n" + //
                "    9 (B)\n" + //
                "      null\n" + //
                "      null\n" + //
                "    11 (B)\n" + //
                "      null\n" + //
                "      null\n",
                rbTree.toString());
    }
    
    @Test
    public void testDelete2() {
        rbTree.delete(9);
        assertEquals(
                "5 (B)\n" + //
                "  0 (R)\n" + //
                "    -1 (B)\n" + //
                "      null\n" + //
                "      null\n" + //
                "    1 (B)\n" + //
                "      null\n" + //
                "      2 (R)\n" + //
                "        null\n" + //
                "        null\n" + //
                "  10 (R)\n" + //
                "    6 (B)\n" + //
                "      null\n" + //
                "      null\n" + //
                "    11 (B)\n" + //
                "      null\n" + //
                "      null\n",
                rbTree.toString());
    }
}
