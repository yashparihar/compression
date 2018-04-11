package com.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 *
 * @author Yash
 */
public class unCompression {

	public static String decoder = "encoder.txt";
	public static String compFile = "compText.txt";
	public static String decompFile = "newfile.txt";

	public static int getCode(char c) {
		switch (c) {
		case 'A':
			return 0;

		case 'T':
			return 1;

		case 'G':
			return 2;

		case 'C':
			return 3;

		}

		return 0;
	}

	public static void main(String args[]) throws FileNotFoundException, IOException, ClassNotFoundException {

		String fileType = "";// args[0];
		// String outFile=args[1];

		// 1. UNSERIALIZING THE ENCODER TO GET ROOT NODE

		FileInputStream fi = new FileInputStream(new File(decoder));
		ObjectInputStream oi = new ObjectInputStream(fi);



		
		
		
		// Read objects
		node rootNode = (node) oi.readObject(); // WE GOT OUR IMPORTANT ENCODE TREE

		System.out.println(rootNode.toString());

		oi.close();
		fi.close();

		// 2. READ COMP FILE AND EVALUATE IT USING ROOTNODE (HUFFMAN TREE)

		if (fileType.equals("image")) {

			System.out.println(fileType);

			InputStream compin = null;
			OutputStream readout = null;

			try {
				compin = new FileInputStream(new File(compFile)); // READING
				readout = new FileOutputStream(new File(decompFile)); // WRITING
			} catch (Exception ex) {
				System.out.println("Error: " + ex);
			}

			int cnt;
			node routing = rootNode;

			int eleno = 0;

			while ((cnt = compin.read()) != -1) {
				if (routing.isLeaf) {

					if (eleno == 3) {
						/* readout.write(' '); */ eleno = 0;
					} else /* readout.write(','); */

						readout.write(routing.key);
					routing = rootNode.childNode[getCode((char) cnt)];
				} else
					routing = routing.childNode[getCode((char) cnt)];

				eleno++;
			}
			if (routing.isLeaf)
				readout.write(routing.key);

		}
		// ...............NOW FOR TEXT..................................................
		else {

			InputStream compin = null;
			OutputStream readout = null;

			try {
				compin = new FileInputStream(new File(compFile)); // READING
				readout = new FileOutputStream(new File(decompFile)); // WRITING
			} catch (Exception ex) {
				System.out.println("Error: " + ex);
			}

			int cnt;
			node routing = rootNode;

			while ((cnt = compin.read()) != -1) {
				if (routing.isLeaf) {
					readout.write(routing.key);
					routing = rootNode.childNode[getCode((char) cnt)];
				} else
					routing = routing.childNode[getCode((char) cnt)];
			}
			if (routing.isLeaf)
				readout.write(routing.key);
		}
	}
}
