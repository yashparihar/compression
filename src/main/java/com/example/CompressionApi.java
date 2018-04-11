package com.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

//import compression.node;

@WebServlet(name = "Compressing File", urlPatterns = { "/compress" })
@MultipartConfig

public class CompressionApi extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		boolean flg = false;
		
		String Dfile = request.getParameter("downloadFile");
	//	System.out.println("in:" + session.getAttribute("User"));
		
		try {
			if (session.getAttribute("User").toString().equals(session.getId())) {
				flg = true;
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		if (flg) {
		//	System.out.println("in:" + session.getAttribute("User"));

		//	System.out.println(session.getAttribute("compText"));
			String fileName = (String) session.getAttribute("fileName");

			if (Dfile.contains("Encoder")) {
				response.setHeader("Content-Disposition", "attachment;filename=\"encoder" + fileName + "\"");
				response.getWriter().print(session.getAttribute("encoder"));
			} else {
				response.setHeader("Content-Disposition", "attachment;filename=\"compress" + fileName + "\"");
				response.getWriter().print(session.getAttribute("compText"));
			}

		} else {
			response.setContentType("application/json");
			String jsonStr = "{\"resCode\": \"404\"}"; //DOESNT EXIST
			response.getWriter().print(jsonStr);
			
			// SESSION EXPIRE RETURN TO MAIN PAGE : upload again
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		session.setAttribute("User", session.getId());
		// System.out.println("out:" + session.getAttribute("User").toString());

		
		Part filePart = request.getPart("file_upload");
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		InputStream fileContent = filePart.getInputStream();

		session.setAttribute("fileName", fileName);

		freqTab tab = new freqTab();
		Map<Integer, String> freq;

		freq = tab.createTab(filePart.getInputStream());

		// 2. crete min heap from freq table
		minHeapTree enc = new minHeapTree(freq);
		enc.display();

		// 3. process heap for huffman compress tree
		while (enc.size > 1) {
			enc.extractAndSet();
			enc.display();
		}

		// 4. Traverse thru tree till leaf characters and assign it his path (DFS
		// traversal)
		HashMap charPath = new HashMap(); // THIS TABLE CONTAINS ATGC CODE OF A CHARACTER

		new node().dfsTraverseLeaf(charPath, enc.elements[0], "");

		Iterator it = charPath.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry res = (Map.Entry) it.next();
			// System.out.println(res.getKey() + " " + res.getValue());
		}

		// 5. Reading input file char bt char and creating new file with atgc code and
		// binary file

		InputStream in = filePart.getInputStream(); // READING
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;

		int cnt;
		while ((cnt = in.read()) != -1) {

			if (charPath.containsKey((char) cnt)) {
				String val = (String) charPath.get((char) cnt); // WRINTING IN COMPRESS FILE DETAILS IN ATGC
				byte b[] = val.getBytes();
				os.write(b);
			} else
				System.out.println("empty");
		}

		// 6. CREATW ENCODER FILE BY SERIALIZING ROOT NODE

		node storeNode = enc.elements[0]; // THE ROOT TO BE STORE AS IT CONTAINS CHILD NODE WHICH THEMSELVES CONTAIN

		ByteArrayOutputStream f = null;
		OutputStream fe = null;
		try {
			f = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			// Write objects to file
			o.writeObject(storeNode);

			o.close();
			f.close();
		} catch (Exception ex) {
		}

		session.setAttribute("compText", new String(os.toByteArray()));
		session.setAttribute("encoder", new String(f.toByteArray()));
		//System.out.println(session.getAttribute("compText"));

		String jsonStr = "{\"resCode\": \"200\" , \"sessionToken\": \"" + session.getId() + "\"  }";
		response.getWriter().print(jsonStr);
		
	}
}
