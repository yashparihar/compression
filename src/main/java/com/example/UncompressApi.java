package com.example;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@WebServlet(name = "uncompressing file", urlPatterns = { "/uncompress" })
@MultipartConfig

public class UncompressApi extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jsonStr = "{\"resCode\": \"200\"}"; // DOESNT EXIST

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		Part filePart = request.getPart("encoderfile_upload");
		// String fileName =
		// Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE
		// fix.
		InputStream fileContent = filePart.getInputStream();

		int cnt; String ser="";
		while ((cnt = fileContent.read()) != -1) {
			ser+=""+(char)cnt;
			System.out.println(cnt+" "+(char)cnt);
		}
		
		System.out.println(ser);
	/*	
		byte[] buff = new byte[8000];
		int bytesRead = 0;

		ByteArrayOutputStream bao = new ByteArrayOutputStream();

		while ((bytesRead = fileContent.read(buff)) != -1) {
			bao.write(buff, 0, bytesRead);
		}

		byte[] data = bao.toByteArray();

		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		System.out.println(bin.available()); */
		//
		// System.out.println(toByteArray(filePart.getInputStream()).toString());
		// ObjectInputStream oi = new ObjectInputStream();

		// String val = "";
		// int cnt;
		// while ((cnt = fileContent.read()) != -1) {
		// val += (char) cnt; // WRITING IN COMPRESS FILE DETAILS IN ATGC
		// // System.out.println(val );
		// System.out.println(val);
		// // byte b[] = val.getBytes();
		// }

		// FileInputStream fi = new FileInputStream(bin);
		//ObjectInputStream ois = new ObjectInputStream(bin);
		ByteArrayInputStream bin = new ByteArrayInputStream(ser.getBytes());
		ObjectInputStream ois = new ObjectInputStream(bin);
		
		System.out.println("input.available(): " + ois.available());
		System.out.println("input.readByte(): " + ois.readByte());
		// rootNode;
		try {
			node rootNode = (node) ois.readObject();
			System.out.println(rootNode.toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ByteArrayInputStream bb = new
		// ByteArrayInputStream(toByteArray(filePart.getInputStream()));

		// while (bb.read() != -1)
		// System.out.println(bb.read());
		/*
		 * try { ObjectInputStream oi = new ObjectInputStream(bb); node rootNode =
		 * (node) oi.readObject(); System.out.println(rootNode.toString()); } catch
		 * (Exception ex) { System.out.println("error: " + ex); jsonStr =
		 * "{\"resCode\": \"" + ex + "\"}"; // DOESNT EXIST }
		 */
		// // Read objects
		// node rootNode = null;
		// try {
		// rootNode = (node) oi.readObject();
		// } catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } // WE GOT OUR IMPORTANT ENCODE TREE
		//
		// System.out.println(rootNode.toString());

		// oi.close();// bb.close();

		response.getWriter().print(jsonStr);

	}

}
