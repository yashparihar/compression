package com.example;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Yash
 */
public class node implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public char key;
	public int freq;
	public boolean isLeaf;
	public String combChar;

	public node childNode[] = new node[4];
	/*
	 * public node one; public node two; public node three;
	 */

	public node() {
	}

	public node(char k, int f, boolean l) {
		key = k;
		freq = f;
		isLeaf = l;
		combChar = "";
	}

	@Override
	public String toString() {
		return "key=" + this.key + " freq=" + this.freq + " comb=" + this.combChar;
	}

	public void dfsTraverseLeaf(HashMap charPath, node currNode, String path) {

		if (currNode.isLeaf) {
			charPath.put(currNode.key, path);
			return;
		}

		// if ( currNode.childNode[0] )
		if (currNode.childNode[0] == null)
			return;
		dfsTraverseLeaf(charPath, currNode.childNode[0], path.concat("A"));

		if (currNode.childNode[1] == null)
			return;
		dfsTraverseLeaf(charPath, currNode.childNode[1], path.concat("T"));

		if (currNode.childNode[2] == null)
			return;
		dfsTraverseLeaf(charPath, currNode.childNode[2], path.concat("G"));

		if (currNode.childNode[3] == null)
			return;
		dfsTraverseLeaf(charPath, currNode.childNode[3], path.concat("C"));

		return;

	}

}
