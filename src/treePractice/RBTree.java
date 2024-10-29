package treePractice;


/**
 * A class to represent an RB binary search tree.
 * RB trees self-balance to maintain 3 properties:
 * 1. Nodes are colored red or black
 * 2. Red Nodes cannot be children of red nodes (the red property)
 * 3. The number of black nodes on any path from root to null ptr are equal (the black property)
 * 
 *
 * @author Suhas Arehalli, Bret Jackson, Mayank Jaiswal
 */
public class RBTree<E extends Comparable<E>> extends BinarySearchTree<E> {

    // Inner class that extends Node to add color 
    protected static class RBNode<E> extends Node<E>{
        public boolean black;

        public RBNode(E data) {
            super(data);
            black = false; // nodes are created red
        }

        @Override
        // Override toString to indicate the color of each node.
        public String toString() {
            return String.format("%s (%s)", data.toString(), black? "B" : "R");
        }
    }

    /**
     * Left rotation of around the pivot node
     * @param pivot
     * @return  the new root of the subtree 
     */
    private RBNode<E> rotateLeft(RBNode<E> pivot) {
        RBNode<E> x = (RBNode<E>) pivot.right; 
        pivot.right = x.left;
        if (pivot.right != null) pivot.right.parent = pivot;

        x.left = pivot;
        x.parent = pivot.parent;
        x.left.parent = x;
        return x; // new root
    }

    /**
     * Right rotation around the pivot node
     * @param pivot
     * @return the new root of the subtree
     */
    private RBNode<E> rotateRight(RBNode<E> pivot) {
        RBNode<E> x = (RBNode<E>) pivot.left; 
        pivot.left = x.right;
        if (pivot.left != null) pivot.left.parent = pivot;

        x.right = pivot;
        x.parent = pivot.parent;
        x.right.parent = x;
        return x; // new root
    }


    /**
     * An Iterative add method
     *
     * @param item The object being inserted
     * @return true if the object is inserted, false
     * if the object already exists in the tree
     * @pre The object to insert must implement the
     * Comparable interface.
     */
    @Override
    public boolean add(E item) {
        RBNode<E> currentNode = (RBNode<E>) root;
        RBNode<E> parent = null;
        while (currentNode != null) {
            if (item.compareTo(currentNode.data) == 0) {
                // item is already contained in the tree
                return false;
            } else if (item.compareTo(currentNode.data) < 0) {
                // look left
                parent = currentNode;
                currentNode = (RBNode<E>) currentNode.left;
            } else {
                // look right
                parent = currentNode;
                currentNode = (RBNode<E>) currentNode.right;
            }
        }
        // We've found the position for the new node!
        RBNode<E> newNode = new RBNode<E>(item);
        newNode.parent = parent;

        if (parent == null) {
            // Handle inserting the root
            root = newNode;
            return true;
        }
        // Insert a non-root node.
        if (item.compareTo(parent.data) < 0) {
            parent.left = newNode;  
        } else {
            parent.right = newNode;
        }

        // Rebalance
        addRebalance(newNode, parent);
        return true;
    }

    /**
     * Returns a node's sibling if it exists, null otherwise.
     * @param node  the node who's sibling we want
     * @return the sibling
     */
    private RBNode<E> getSibling(RBNode<E> node) {
        if (node.parent == null) return null;
        if (node.parent.left == node) return (RBNode<E>) node.parent.right;
        else return (RBNode<E>) node.parent.left;
    }

    /**
     * A method to rebalance the tree after an add, if necessary.
     *
     * @param node      the node that may violate the red-black properties
     * @param parent    the parent of node
     */
    private void addRebalance(RBNode<E> node, RBNode<E> parent) {
        // Case 1: Parent is black or null (i.e., root)
        if ((parent == null) || parent.black) {
            // We don't have to do anything
            // Since the parent is black, the red property isn't violated by insertion.
            // Since new nodes are red, we can't cause a violation of the black property with insertion.
            return;
        }


        // Case 2: the Parent is red and the root
        RBNode<E> grandparent = (RBNode<E>) parent.parent;
        if (grandparent == null) {
            // Solution: Make the root black
            parent.black = true;
            return;
        }


        // Case 3: parent and uncle are red, grandparent exists (and must be black!)
        // TODO: Complete this case when instructed
        RBNode<E> uncle = getSibling(parent);
        if ((uncle != null) && !uncle.black) {

            return;
        }
        // Case 4: Parent is red, Uncle is black or null, grandparent exists (and must be black). 
        // Solution: Rotate and Recolor!
        // Make the parent the grandparent and color the former-parent black and the former-grandparent 
        // red.

        // Case 4a: If we're an inner grandchild, this won't work, so we can rotate to make sure we're an
        // outer grandchild by rotating outward around the parent and swapping out labels for parent and child
        if ((grandparent.left != null) && (grandparent.left.right == node)) {
            // Do a leftward rotation if we're a left inner grandchild
            grandparent.left = rotateLeft(parent);

            // Relabel our parent and child, since they swap in the rotation!
            RBNode<E> temp = parent;
            parent = node;
            node = temp;
        }
        else if ((grandparent.right != null) && (grandparent.right.left == node)) {
            // Do a rightward rotation if we're a right inner grandchild
            grandparent.right = rotateRight(parent);

            // Relabel our parent and child, since they swap in the rotation!
            RBNode<E> temp = parent;
            parent = node;
            node = temp;
        }
        // 4b. Now we're guaranteed to be an outer grandchild, so we can rotate!
        
        // TODO: Complete when instructed
    }

    /**
     * Rotate around pivot toward the node toward.
     * @param pivot     the node to rotate around.
     * @param toward    child of pivot to rotate toward.
     */
    private void rotateToward(RBNode<E> pivot, RBNode<E> toward) {
        if (pivot.left == toward) {
            if (pivot.parent == null) {
                root = rotateLeft(pivot);
            } else if (pivot.parent.left == pivot) {
                pivot.parent.left = rotateLeft(pivot);
            } else {
                pivot.parent.right = rotateLeft(pivot);
            }
        } else if (pivot.right == toward) {
            if (pivot.parent == null) {
                root = rotateRight(pivot);
            } else if (pivot.parent.left == pivot) {
                pivot.parent.left = rotateRight(pivot);
            } else {
                pivot.parent.right = rotateRight(pivot);
            }
        }
    }

    /**
     * Delete a node with value target.
     *
     * @param target The object to be deleted
     * @return The object deleted from the tree
     * or null if the object was not in the tree
     * @throws ClassCastException if target does not implement
     *                            Comparable
     * @post The object is not in the tree.
     */
    @Override
    public E delete(E target) {
        RBNode<E> currentNode = (RBNode<E>) root;
        while (currentNode != null) {
            if (target.compareTo(currentNode.data) == 0) {
                // item is already contained in the tree
                delete(currentNode);
                return target;
            } else if (target.compareTo(currentNode.data) < 0) {
                // look left
                currentNode = (RBNode<E>) currentNode.left;
            } else {
                // look right
                currentNode = (RBNode<E>) currentNode.right;
            }
        }
        return null;
    }

    /**
     * Delete the Node<E> node
     *
     * @param node The node to be deleted
     * @post The node is removed from the tree
     */
    private void delete(RBNode<E> node) {
        // Check for easy cases first
        // If we have two children, replace with in-order successor and delete
        if ((node.left != null) && (node.right != null)) {
            node.data = replaceWithSuccessor((RBNode<E>) node.right);
            return;
        } 
        // If there's one child, replace the node with it's child
        // and color it black. If a node has a single child, the 
        // child must be red and thus the node must be black.
        if ((node.left != null) || (node.right != null)) {
            // get the non-null child
            RBNode<E> child;
            if (node.left != null) {
                child = (RBNode<E>) node.left;
            } else {
                child = (RBNode<E>) node.right;
            }
            // recolor
            child.black = true;

            // handle the root case
            if (node.parent == null) {
                root = child;
                root.parent = null;
                return;
            } 
            // Handle the general case 
            if (node.parent.left == node) {
                node.parent.left = child;
                child.parent = node.parent;
            } else if (node.parent.right == node) {
                node.parent.right = child;
                child.parent = node.parent;
            }
            return;
        }
        // Node is a leaf!
        // Root case:
        if (node.parent == null) {
            root = null;
            return;
        }
        
        // If the leaf is red, we can just delete it
        if (!node.black) {
            if (node.parent.left == node) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }  
        
        // If we have a black leaf, we're at one of the hard cases 
        // and have to rebalance
        if (node.black) {
            deleteRebalance((RBNode<E>) node, (RBNode<E>)node.parent);
            if (node.parent.left == node) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }
    }

    private void deleteRebalance(RBNode<E> node, RBNode<E> parent) {
        // Case 1: If we're at the root, we're rebalanced
        if (parent == null) return;


        // Get the sibling and it's children (i.e., nephews)
        RBNode<E> sibling = getSibling(node);
        RBNode<E> closeChild = null;
        RBNode<E> distantChild = null;
        if (sibling != null) {
            if (parent.left == node) {
                closeChild = (RBNode<E>) sibling.left;
                distantChild = (RBNode<E>) sibling.right;
            } else {
                closeChild = (RBNode<E>) sibling.right;
                distantChild = (RBNode<E>) sibling.left;
            }
        }

        // Case 2: parent and sibling and sibling's children are black/null
        // recolor sibling so the # of black nodes on any path
        // through P are even (reduced by 1). Recurse on parent
        // to ensure this is true for paths not through P
        if (parent.black && sibling.black &&
            ((closeChild == null) || closeChild.black) &&
            ((distantChild == null || distantChild.black))) {

            sibling.black = false;
            deleteRebalance(parent, (RBNode<E>) parent.parent);
            return;
        }
        // Case 3: Sibling red, so parent and sibling's children
        // are black/null
        if ((sibling != null) && !sibling.black) {
            // rotate toward node
            rotateToward(parent, node);
            // recolor
            parent.black = false;
            sibling.black = true;

            // reassign
            sibling = closeChild;
            if (parent.left == node) {
                closeChild = (RBNode<E>) sibling.left;
                distantChild = (RBNode<E>) sibling.right;
            } else {
                closeChild = (RBNode<E>) sibling.right;
                distantChild = (RBNode<E>) sibling.left;
            }
            // pass through
        }

        // Case 4: If sibling is black and only closeChild is red
        if ((sibling != null) && sibling.black && 
            ((closeChild != null) && !closeChild.black) &&
            ((distantChild == null) || distantChild.black)) {

            // Rotate sibling away from root
            if (parent.left == sibling) {
                parent.left = rotateLeft(sibling);
            } else {
                parent.right = rotateRight(sibling);
            }
            // recolor
            sibling.black = false;
            closeChild.black = true;

            // reassign
            distantChild = sibling;
            sibling = closeChild;

            // pass through
        }

        // Case 5: Sibling is black and distantChild is red
        if ((sibling != null) && sibling.black && 
            ((distantChild != null) && !distantChild.black)) {

            // Rotate around parent toward node
            rotateToward(parent, node);

            // recolor 
            sibling.black = parent.black;
            parent.black = true;
            distantChild.black = true;

            // done
            return;
        }

        // P red, sibling and sibling's children are black
        // recolor
        sibling.black = false;
        parent.black = true;
    }

    /**
     * Find the node that is the
     * inorder successor, delete it, and return it's
     * value
     *
     * @param node a left descendent of the right subtree of to-be-deleted node
     * @return The data in the inorder successor
     * @post The inorder successor is removed from the tree.
     */
    private E replaceWithSuccessor(RBNode<E> node) {
        if (node.left == null) {
            E returnValue = node.data;
            delete(node);
            return returnValue;
        } else {
            return replaceWithSuccessor((RBNode<E>) node.left);
        }
    }
}
