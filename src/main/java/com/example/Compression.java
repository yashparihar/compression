package com.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;

//import compression.minHeapTree;
//import compression.node;

@MultipartConfig


public class Compression {
	/**
	 *
	 * @author Yash
	 */
	static String FileType = "text";// DEFAULT;
	// static String FileName = "text.txt";
	static String CompressFileName = "compText.txt";
	static String BinaryFileName = "BinText.txt";

	static String toBinary(String s) {
		// String s = "@a";
		byte[] bytes = s.getBytes();
		StringBuilder binary = new StringBuilder();
		for (int j = 0; j < bytes.length; j++) {
			int val = bytes[j];
			for (int i = 0; i < 7; i++) {
				val <<= 1;
				binary.append((val & 128) == 0 ? 0 : 1);
			}
		}
		return binary.toString();
	}

	public static byte[] toByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}

		return os.toByteArray();

	}

	public static byte[] getStream(Part filePart) throws IOException {
		// 1. create frequency table list
		InputStream content = filePart.getInputStream();
		
		freqTab tab = new freqTab();
		Map<Integer, String> freq;

	//	System.out.println("dd:"+new String(Compression.toByteArray(content)));

		freq = tab.createTab(content);
		System.out.println("just finished");
		// 2. crete min heap from freq table
		// minHeapTree enc = new minHeapTree(freq);
		// enc.display();

		// 2. crete min heap from freq table
		minHeapTree enc = new minHeapTree(freq);
		enc.display();
		// System.out.println("-----------------");

		// extracting all to view if functionality working poperly or not
		// enc.extractingAll(); //test with all possible data

		// 3. process heap for huffman compress tree
		while (enc.size > 1) {
			// System.out.println("-----------------");
			enc.extractAndSet();
			// enc.display();
		}

		// 4. Traverse thru tree till leaf characters and assign it his path (DFS
		// traversal)

		HashMap charPath = new HashMap(); // THIS TABLE CONTAINS ATGC CODE OF A CHARACTER

		// enc.dfsTraverseLeaf( charPath , enc.elements[0] , "" ) ;
		// node n=
		new node().dfsTraverseLeaf(charPath, enc.elements[0], "");

		Iterator it = charPath.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry res = (Map.Entry) it.next();
			// System.out.println(res.getKey()+" "+res.getValue());
		}

		return Compression.toByteArray(filePart.getInputStream());

	}
	/*
	 * public InputStream compressStart(String fileName, InputStream fileContent)
	 * throws IOException { // TODO code application logic here
	 * 
	 * // 1. create freq table list freqTab tab = new freqTab(); Map<Integer,
	 * String> freq = null;
	 * 
	 * // if (args.length != 0) { // if (args[0].equals("image")) { // FileName =
	 * args[1]; // FileType = args[0]; // } // }
	 * 
	 * freq = tab.createTab("text", fileName, fileContent);
	 * 
	 * System.out.println("Filename:" + fileName);
	 * 
	 * // 2. crete min heap from freq table minHeapTree enc = new minHeapTree(freq);
	 * enc.display(); // System.out.println("-----------------");
	 * 
	 * // extracting all to view if functionality working poperly or not //
	 * enc.extractingAll(); //test with all possible data
	 * 
	 * // 3. process heap for huffman compress tree while (enc.size > 1) { //
	 * System.out.println("-----------------"); enc.extractAndSet(); //
	 * enc.display(); }
	 * 
	 * // 4. Traverse thru tree till leaf characters and assign it his path (DFS //
	 * traversal)
	 * 
	 * HashMap charPath = new HashMap(); // THIS TABLE CONTAINS ATGC CODE OF A
	 * CHARACTER
	 * 
	 * // enc.dfsTraverseLeaf( charPath , enc.elements[0] , "" ) ;
	 * 
	 * try { new node().dfsTraverseLeaf(charPath, enc.elements[0], ""); } catch
	 * (Exception err) { }
	 * 
	 * Iterator it = charPath.entrySet().iterator();
	 * 
	 * while (it.hasNext()) { Map.Entry res = (Map.Entry) it.next(); //
	 * System.out.println(res.getKey()+" "+res.getValue()); }
	 * 
	 * // 5. Reading input file char bt char and creating new file with atgc code
	 * and // binary file InputStream in = null; OutputStream compout = null;
	 * OutputStream binout = null; ByteArrayOutputStream baos = null; try {
	 * 
	 * in = fileContent;// new FileInputStream(new File(FileName)); // READING
	 * binout = new FileOutputStream(new File(BinaryFileName)); // WRITING compout =
	 * new FileOutputStream(new File(CompressFileName)); // WRITING
	 * 
	 * baos = new ByteArrayOutputStream(); // byte[] buffer = new byte[1024];
	 * 
	 * } catch (Exception ex) { System.out.println("Error: " + ex); // return false;
	 * }
	 * 
	 * int cnt;
	 * 
	 * while ((cnt = in.read()) != -1) {
	 * 
	 * if (charPath.containsKey((char) cnt)) {
	 * 
	 * // String key =""+(char)cnt; // binout.write( toBinary(key).getBytes() );
	 * //WRINTING IN BINARY FILE WHILE // CONVERTING KEY TO BINARY
	 * 
	 * String val = (String) charPath.get((char) cnt); // WRINTING IN COMPRESS FILE
	 * DETAILS IN ATGC // System.out.println(val ); byte b[] = val.getBytes();
	 * baos.write(b); // compout.write(b);
	 * 
	 * } else System.out.println("empty"); }
	 * 
	 * // 6. CREATW ENCODER FILE BY SERIALIZING ROOT NODE
	 * 
	 * try { node storeNode = enc.elements[0]; // THE ROOT TO BE STORE AS IT
	 * CONTAINS CHILD NODE WHICH THEMSELVES CONTAIN // MORE CHILD
	 * 
	 * // if (FileType.equals("image")){ // // storeNode.img.width =
	 * Integer.parseInt(args[2]); // // storeNode.img.height =
	 * Integer.parseInt(args[3]); // }
	 * 
	 * FileOutputStream f = new FileOutputStream(new File("encoder.txt"));
	 * ObjectOutputStream o = new ObjectOutputStream(f); // Write objects to file
	 * o.writeObject(storeNode);
	 * 
	 * o.close(); f.close(); } catch (Exception ex) { // return false; }
	 * 
	 * System.out.println("finishes"); // return true; return fileContent;// new
	 * FileInputStream(new File(CompressFileName)); }
	 * 
	 * /* public static void main(String[] args) throws IOException { // TODO code
	 * application logic here
	 * 
	 * // 1. create freq table list freqTab tab = new freqTab(); Map<Integer,
	 * String> freq = null;
	 * 
	 * if (args.length != 0) { if (args[0].equals("image")) { FileName = args[1];
	 * FileType = args[0]; } }
	 * 
	 * freq = tab.createTab(FileType, FileName);
	 * 
	 * System.out.println("Filename:" + FileName);
	 * 
	 * // 2. crete min heap from freq table minHeapTree enc = new minHeapTree(freq);
	 * enc.display(); // System.out.println("-----------------");
	 * 
	 * // extracting all to view if functionality working poperly or not //
	 * enc.extractingAll(); //test with all possible data
	 * 
	 * // 3. process heap for huffman compress tree while (enc.size > 1) { //
	 * System.out.println("-----------------"); enc.extractAndSet(); //
	 * enc.display(); }
	 * 
	 * // 4. Traverse thru tree till leaf characters and assign it his path (DFS //
	 * traversal)
	 * 
	 * HashMap charPath = new HashMap(); // THIS TABLE CONTAINS ATGC CODE OF A
	 * CHARACTER
	 * 
	 * // enc.dfsTraverseLeaf( charPath , enc.elements[0] , "" ) ; // node n= new
	 * node().dfsTraverseLeaf(charPath, enc.elements[0], "");
	 * 
	 * Iterator it = charPath.entrySet().iterator();
	 * 
	 * while (it.hasNext()) { Map.Entry res = (Map.Entry) it.next(); //
	 * System.out.println(res.getKey()+" "+res.getValue()); }
	 * 
	 * // 5. Reading input file char bt char and creating new file with atgc code
	 * and // binary file
	 * 
	 * InputStream in = null; OutputStream compout = null; OutputStream binout =
	 * null;
	 * 
	 * try {
	 * 
	 * in = new FileInputStream(new File(FileName)); // READING binout = new
	 * FileOutputStream(new File(BinaryFileName)); // WRITING compout = new
	 * FileOutputStream(new File(CompressFileName)); // WRITING } catch (Exception
	 * ex) { System.out.println("Error: " + ex); }
	 * 
	 * int cnt;
	 * 
	 * while ((cnt = in.read()) != -1) {
	 * 
	 * if (charPath.containsKey((char) cnt)) {
	 * 
	 * // String key =""+(char)cnt; // binout.write( toBinary(key).getBytes() );
	 * //WRINTING IN BINARY FILE WHILE // CONVERTING KEY TO BINARY
	 * 
	 * String val = (String) charPath.get((char) cnt); // WRINTING IN COMPRESS FILE
	 * DETAILS IN ATGC // System.out.println(val ); byte b[] = val.getBytes();
	 * compout.write(b);
	 * 
	 * } else System.out.println("empty"); }
	 * 
	 * // 6. CREATW ENCODER FILE BY SERIALIZING ROOT NODE
	 * 
	 * node storeNode = enc.elements[0]; // THE ROOT TO BE STORE AS IT CONTAINS
	 * CHILD NODE WHICH THEMSELVES CONTAIN // MORE CHILD
	 * 
	 * // if (FileType.equals("image")){ // // storeNode.img.width =
	 * Integer.parseInt(args[2]); // // storeNode.img.height =
	 * Integer.parseInt(args[3]); // }
	 * 
	 * try { FileOutputStream f = new FileOutputStream(new File("encoder.txt"));
	 * ObjectOutputStream o = new ObjectOutputStream(f); // Write objects to file
	 * o.writeObject(storeNode);
	 * 
	 * o.close(); f.close(); } catch (Exception ex) { }
	 * 
	 * }
	 */
}
