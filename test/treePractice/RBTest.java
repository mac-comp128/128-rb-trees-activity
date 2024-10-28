package treePractice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RBTest {
    RBTree<Integer> rbTree;
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
