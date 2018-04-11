package com.example;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Yash
 */
public class minHeapTree {

	public int size;
	// public int capacity;
	public node elements[];

	public minHeapTree(Map<Integer, String> ele) {
		size = ele.size();
		elements = new node[size];

		// Initial insertion

		Set set = ele.entrySet();
		Iterator iterator2 = set.iterator();

		int ptr = 0;

		while (iterator2.hasNext()) {
			Map.Entry mapEle = (Map.Entry) iterator2.next();
			elements[ptr] = new node(freqTab.objectToChar(mapEle.getKey()), freqTab.objectToInteger(mapEle.getValue()),
					true);
			ptr++;
		}

	//	System.out.println("ini size = " + this.size);

	}


	public int parent(int i) {
		return ((i - 1) / 4);
	}

	public int zeroPos(int i) {
		return ((i * 4) + 1);
	}

	public int onePos(int i) {
		return ((i * 4) + 2);
	}

	public int twoPos(int i) {
		return ((i * 4) + 3);
	}

	public int threePos(int i) {
		return ((i * 4) + 4);
	}

	public node extractMin() {

		node minNode;

		if (size == 0)
			return null;
		else if (size == 1) {
			minNode = elements[0];
			size--;
			return minNode;
		}

		minNode = elements[0];
		elements[0] = elements[size - 1];
		size--;
		minHeapify(0);

		return minNode;

	}

	public void minHeapify(int i) {
		int zero = zeroPos(i);
		int one = onePos(i);
		int two = twoPos(i);
		int three = threePos(i);

		int smallest = i;

		if (zero < size && elements[zero].freq < elements[smallest].freq) // number[l] < number[i] )
			smallest = zero;
		if (one < size && elements[one].freq < elements[smallest].freq) // number[l] < number[i] )
			smallest = one;
		if (two < size && elements[two].freq < elements[smallest].freq) // number[l] < number[i] )
			smallest = two;
		if (three < size && elements[three].freq < elements[smallest].freq) // number[l] < number[i] )
			smallest = three;

		if (smallest != i) {
			node temp = elements[i];
			elements[i] = elements[smallest];
			elements[smallest] = temp;
			// number[smallest] = temp;

			minHeapify(smallest);
		}

	}

	public void extractingAll() {
		System.out.println();
		while (size > 0) {
			node ele = this.extractMin();
			// System.out.println(freqTab.objectToChar( ele.key ) +" has f "+ele.freq);
			// size--;
		}
		// System.out.println("finish doing size = "+this.size);

	}

	public void extractAndSet() {

		node newNode = new node('\0', 0, false);

		for (int i = 0; i < 4; i++) {
			node tempNode = extractMin();

			if (tempNode == null)
				break;

			newNode.childNode[i] = tempNode;

			newNode.combChar += tempNode.key; // conNCATINATING NODE WITH ARENT LEAF
			// System.out.println(" <==: "+Node.key);
			// newNode.key = '\0';
			newNode.freq += tempNode.freq;
		}

		// check if newNode is empty
		if (newNode.freq <= 0)
			return;

		this.insertNode(newNode);

	}

	public void insertNode(node newNode) {

		int cnt = size;
		elements[size] = newNode;

		while (cnt > 0 && elements[cnt].freq < elements[parent(cnt)].freq) {
			node temp = elements[cnt];
			elements[cnt] = elements[parent(cnt)];
			elements[parent(cnt)] = temp;
			cnt = parent(cnt);
		}
		size++;
	}

	public void display() {

		for (int i = 0; i < size; i++) {
			// System.out.println(" "+elements[i].key+" "+elements[i].freq+ "
			// "+elements[i].combChar);
			if (elements[i].combChar != null) {
				// System.out.print(" child="+elements[i].toString());
				for (int j = 0; j < 4; j++) {
					try { // System.out.println(j+" child="+elements[i].childNode[j].toString());
					} catch (Exception er) {
						// System.out.println(j+" no child=");
						break;
					}
				}
			}
			/*
			 * if (i>0) System.out.print(" parent="+freqTab.objectToChar(
			 * elements[parent(i)].key) + ":"+freqTab.objectToInteger(
			 * elements[parent(i)].freq) ); int zth = zeroPos(i); int oth = onePos(i); int
			 * twth = twoPos(i); int thth = threePos(i);
			 * 
			 * if (zth<size) System.out.print(" z="+freqTab.objectToChar( elements[zth].key
			 * )+ ":"+freqTab.objectToInteger( elements[zth].freq ) +
			 * ": combineString: "+elements[zth].combChar );
			 * 
			 * if (oth<size) System.out.print("|| o="+ freqTab.objectToChar(
			 * elements[oth].key)+ ":"+freqTab.objectToInteger( elements[oth].freq ) +
			 * ": combineString: "+elements[oth].combChar );
			 * 
			 * if (twth<size) System.out.print("||  t="+ freqTab.objectToChar(
			 * elements[twth].key)+ ":"+freqTab.objectToInteger( elements[twth].freq) +
			 * ": combineString: "+elements[twth].combChar );
			 * 
			 * if (thth<size) System.out.print("||  th="+ freqTab.objectToChar(
			 * elements[thth].key)+ ":"+freqTab.objectToInteger( elements[thth].freq ) +
			 * ": combineString: "+elements[thth].combChar );
			 */
		}
	}

}
