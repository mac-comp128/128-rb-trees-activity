package treePractice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AVLTest {
    AVLTree<Integer> avlTree;
    @BeforeEach
    public void BuildTree() {
        avlTree = new AVLTree<>();
        /* Constructing tree given in the above figure */
        avlTree.add(9);
        avlTree.add(5);
        avlTree.add(10);
        avlTree.add(0);
        avlTree.add(6);
        avlTree.add(11);
        avlTree.add(-1);
        avlTree.add(1);
        avlTree.add(2);
    }

    @Test
    public void testAdd() {
        /* The constructed AVL Tree would be
                9
               / \
              1  10
            / \   \
           0  5   11
          /  / \
        -1  2  6
        */
        assertEquals(
                "9\n" + //
                "  1\n" + //
                "    0\n" + //
                "      -1\n" + //
                "        null\n" + //
                "        null\n" + //
                "      null\n" + //
                "    5\n" + //
                "      2\n" + //
                "        null\n" + //
                "        null\n" + //
                "      6\n" + //
                "        null\n" + //
                "        null\n" + //
                "  10\n" + //
                "    null\n" + //
                "    11\n" + //
                "      null\n" + //
                "      null\n",
                avlTree.toString());
    }

    @Test
    public void testRemove() {
        avlTree.delete(10);
        /* The AVL Tree after deletion of 10
              1
            /  \
           0    9
          /    / \
        -1    5  11
            / \
           2  6
        */
        assertEquals(
                "1\n" + //
                "  0\n" + //
                "    -1\n" + //
                "      null\n" + //
                "      null\n" + //
                "    null\n" + //
                "  9\n" + //
                "    5\n" + //
                "      2\n" + //
                "        null\n" + //
                "        null\n" + //
                "      6\n" + //
                "        null\n" + //
                "        null\n" + //
                "    11\n" + //
                "      null\n" + //
                "      null\n",
                avlTree.toString());
    }
}
