import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

public class BST<T extends Comparable<T>> {
	class BSTNode implements Comparable<BSTNode> {
		private T data;
		private BSTNode left;
		private BSTNode right;

		public BSTNode(T d) {
			setLeft(null);
			setRight(null);
			setData(d);
		}

		public T getData() {
			return data;
		}

		public void setData(T d) {
			data = d;
		}

		public void setLeft(BSTNode l) {
			left = l;
		}

		public void setRight(BSTNode r) {
			right = r;
		}

		public BSTNode getLeft() {
			return left;
		}

		public BSTNode getRight() {
			return right;
		}

		public boolean isLeaf() {
			return (getLeft() == null) && (getRight() == null);
		}

		public int compareTo(BSTNode o) {
			return this.getData().compareTo(o.getData());
		}

	}

	ArrayList<T> nodes = new ArrayList<T>();

	// The different traversal types.
	public static final int INORDER = 0;
	public static final int PREORDER = 1;
	public static final int POSTORDER = 2;
	public static final int LEVELORDER = 3;
	public static final int INORDER2 = 4;

	private BSTNode root;
	private int size;

	public BST() {
		root = null;
		size = 0;
	}

	public Iterator<T> iterator() {
		get();
		Iterator<T> myIterator = nodes.iterator();
		return myIterator;
	}

	public ArrayList<T> get() {
		visitReturn(root);
		return nodes;
	}

	private void visitReturn(BSTNode r) {
		if (r != null)
			fill(r);
	}

	private void fill(BSTNode r) {
		if (r.left != null)
			fill(r.left);
		if (r.data != null)
			nodes.add(r.getData());
		if (r.right != null)
			fill(r.right);
	}

	/**
	 * Return true if element d is present in the tree.
	 */
	public T find(T d) {
		return find(d, root);
	}

	/**
	 * Return the height of the tree.
	 */
	public int height() {
		return height(root);
	}

	/**
	 * Return the number of nodes in the tree.
	 */
	public int size() {
		return size;
	}

	public void inOrder() {
		traverse(root, INORDER);
	}

	public void postOrder() {
		traverse(root, POSTORDER);
	}

	public void preOrder() {
		traverse(root, PREORDER);
	}

	public void levelOrder() {
		traverse(root, LEVELORDER);
	}

	// Private methods.

	private T find(T d, BSTNode r) {
		if (r == null)
			return null;
		int c = d.compareTo(r.getData());
		if (c == 0)
			return r.getData();
		else if (c < 0)
			return find(d, r.getLeft());
		else
			return find(d, r.getRight());
	}

	/**
	 * Add element d to the tree.
	 */
	public void add(T d) {
		BSTNode n = new BSTNode(d);
		if (root == null) {
			size++;
			root = n;
		} else {
			add(root, n);
		}
	}

	/* Do the actual add of node r to tree rooted at n */
	private void add(BSTNode r, BSTNode n) {
		int c = n.compareTo(r);
		if (c < 0) {
			if (r.getLeft() == null) {
				r.setLeft(n);
				size++;
			} else {
				add(r.getLeft(), n);
			}
		} else if (c > 0) {
			if (r.getRight() == null) {
				r.setRight(n);
				size++;
			} else {
				add(r.getRight(), n);
			}
		}
	}

	public void add(T d, Comparator<T> comp) {
		BSTNode n = new BSTNode(d);
		if (root == null) {
			size++;
			root = n;
		} else {
			add(root, n, comp);
		}
	}

	private void add(BSTNode r, BSTNode n, Comparator<T> comp) {
		int c = comp.compare(n.data, r.data);
		if (c < 0) {
			if (r.getLeft() == null) {
				r.setLeft(n);
				size++;
			} else {
				add(r.getLeft(), n, comp);
			}
		} else if (c > 0) {
			if (r.getRight() == null) {
				r.setRight(n);
				size++;
			} else {
				add(r.getRight(), n, comp);
			}
		}
	}

	/* Implement a height method. */
	private int height(BSTNode r) {
		if (r == null) {
			return -1;
		}

		int lefth = height(r.left);
		int righth = height(r.right);

		if (lefth > righth) {
			return lefth + 1;
		} else {
			return righth + 1;
		}
	}

	/*
	 * Traverse the tree. travtype determines the type of traversal to perform.
	 */
	private void traverse(BSTNode r, int travType) {
		if (r != null) {
			switch (travType) {
			case INORDER: {
				traverse(r.getLeft(), travType);
				visit(r);
				traverse(r.getRight(), travType);
				break;
			}
			case PREORDER:
				visit(r);
				traverse(r.getLeft(), travType);
				traverse(r.getRight(), travType);
				break;
			case POSTORDER:
				traverse(r.getLeft(), travType);
				traverse(r.getRight(), travType);
				visit(r);
				break;
			case LEVELORDER:
				traverse(r.getLeft(), travType);
				visit(r);
				traverse(r.getRight(), travType);
				break;
			case INORDER2:
				traverse(r.getLeft(), travType);
				nodes.add(r.data);
				traverse(r.getRight(), travType);
				break;
			}
		}

	}

	private void visit(BSTNode r) {
		if (r != null)
			// nodes.add(r.data);
			System.out.println(r.getData().toString());
	}

	/* traverse the subtree r using level order. */
	private void levelOrder(BSTNode r) {
		traverse(r, LEVELORDER);
	}

	public void delete(T word) {
		root = deleteNode(word, root);
	}

	private BSTNode deleteNode(T word, BSTNode r) {

		if (r == null)
			return r;
		else {
			int c = word.compareTo(r.data);
			if (c == 0) {
				r = rotate(r);
			} else if (c < 0) {
				r.left = deleteNode(word, r.left);
			} else if (c > 0)
				r.right = deleteNode(word, r.right);
		}

		return r;
	}

	private BSTNode findMin(BSTNode r) {
		while (r.getLeft() != null) {
			r = r.left;
		}
		return r;
	}

	// rotate
	private BSTNode rotate(BSTNode r) {
		if (r.getLeft() != null && r.getRight() != null) {
			r.data = findMin(r.right).data;
			r.right = (deleteNode(r.data, r.right));

		} else {
			if (r.left != null) {

				r = r.left;
				size--;
			} else {
				r = r.right;
				size--;
			}
		}
		return r;

	}
}
