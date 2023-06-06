package com.mytest.myservlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.LogManager;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


/**
 * Servlet implementation class ServletDemo
 */
public class ServletDemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String outPmids_XIAN = "";

	private static final String fileName = "C:\\Users\\Administrator\\Desktop\\11.png";
	
    /**
     * Default constructor. 
     */
    public ServletDemo() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("进入xiaomotest项目的servlet...");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	//captures/84L47717LF062833J
	public static void main(String[] args) throws Exception {
		//sendMail("787503936@qq.com","tsssss","test demo",false);
		
//		Tesseract tesseract = new Tesseract();
//        try {
//            // 设置Tesseract OCR库的数据文件路径
//            tesseract.setDatapath("/tessdata");
//
//            // 读取图片文件
//            File imageFile = new File(fileName);
//
//            // 使用Tesseract OCR库进行文本识别
//            String result = tesseract.doOCR(imageFile);
//
//            // 输出识别结果
//            System.out.println(result);
//        } catch (TesseractException e) {
//            e.printStackTrace();
//        }
		
		/*
		 * // 创建实例 ITesseract instance = new Tesseract();
		 * 
		 * // 设置识别语言
		 * 
		 * instance.setLanguage("chi_sim"); //instance.setLanguage("jpn");
		 * 
		 * // 设置识别引擎
		 * 
		 * instance.setOcrEngineMode(1); instance.setPageSegMode(6);
		 * 
		 * // 读取文件
		 * 
		 * BufferedImage image = ImageIO.read(new File(fileName)); try { // 识别 //String
		 * res = instance.doOCR(new
		 * File("C:\\Users\\Lenovo\\Pictures\\联想截图\\联想截图_20230220144409.png")); String
		 * result = instance.doOCR(image); System.out.println(result); } catch
		 * (TesseractException e) { System.err.println(e.getMessage()); }
		 */
		
//		PDDocument document = PDDocument.load(new File("C:\\Users\\Administrator\\Desktop\\test.pdf"));
//		if(!document.isEncrypted()) {
//			PDFTextStripperByArea stripper = new PDFTextStripperByArea();
//			stripper.setSortByPosition(true);
//			PDFTextStripper tStripper = new PDFTextStripper();
//			
//			String text = tStripper.getText(document);
//			System.out.println("text == " + text);
//			String[] lines = text.split("\\r?\\n");
//			
//			for (String string : lines) {
//				System.out.println("string == " + string);
//			}
//			
//		}
		
		// 创建一个空的Word文档并插入图片
//		XWPFDocument document = new XWPFDocument();
//		XWPFParagraph paragraph = document.createParagraph();
//		XWPFRun run = paragraph.createRun();
//		InputStream inputStream = new FileInputStream(fileName);
//		run.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_JPEG, "image.jpg", Units.toEMU(200), Units.toEMU(200));
//		
//		// 保存Word文档
//		FileOutputStream outputStream = new FileOutputStream("document.docx");
//		document.write(outputStream);
//		outputStream.close();
//		document.close();
		
		// 读取Word文档内容
//		XWPFDocument document1 = new XWPFDocument(new FileInputStream(fileName));
//		List<XWPFParagraph> paragraphs = document1.getParagraphs();
//		for (XWPFParagraph paragraph1 : paragraphs) {
//		    System.out.println(paragraph1.getText());
//		}
//		document1.close();
		
//		try {
//            FileInputStream fis = new FileInputStream(fileName);
//            HWPFDocument document = new HWPFDocument(fis);
//            WordExtractor extractor = new WordExtractor(document);
//            String[] paragraphs = extractor.getParagraphText();
//            for (String paragraph : paragraphs) {
//                System.out.println(paragraph);
//            }
//            fis.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
		 // 读取要识别的图片
        File imageFile = new File(fileName);
        Tesseract tesseract = new Tesseract();

        try {
            // 设置Tesseract OCR库的数据文件路径
            tesseract.setDatapath("D:\\OCR\\tessdata");

            // 对图片进行识别处理，获取文字信息
            String result = tesseract.doOCR(imageFile);

            // 输出文字信息到控制台
            System.out.println(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
		
		
//        }
//        reader.close();
//        isr.close();
//        inputStream2.close();
	    // 将图片转成word文档格式
//        XWPFDocument document = new XWPFDocument();
//        XWPFParagraph paragraph = document.createParagraph();
//        XWPFRun run = paragraph.createRun();
//        InputStream inputStream = new FileInputStream(fileName);
//        run.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_PNG, "11.png", Units.toEMU(400), Units.toEMU(400));
//        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\output.docx");
//        document.write(outputStream);
//        outputStream.close();
//        
//        // 读取word文档
//		try {
//            FileInputStream fis = new FileInputStream("C:\\Users\\Administrator\\Desktop\\ttt.docx");
//            XWPFDocument docx = new XWPFDocument(fis);
//            for (XWPFParagraph para : docx.getParagraphs()) {
//                System.out.println(para.getText());
//            }
//            for (XWPFTable table : docx.getTables()) {
//                for (XWPFTableRow row : table.getRows()) {
//                    for (XWPFTableCell cell : row.getTableCells()) {
//                        System.out.print(cell.getText() + "\t");
//                    }
//                    System.out.println();
//                }
//            }
//            fis.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
	        
	        
        
    }
		
		
		
		
	
	/**
	 * xiaomo_swordsman@163.com
	 * CECWOMIRIHYMCDUS
	 * 
	 * 发送文本邮件
	 * @param email
	 * @param subject
	 * @param textContent
	 * @return
	 */
	private static String sendMail(String email, String subject, String textContent, boolean isHtml) {
		MailAccount account = new MailAccount();
//	    account.setHost("smtp.zoho.com");
//	    account.setPort(465);
//	    account.setAuth(true);
//	    account.setFrom("Panda Hut<service@pandahutai.com>");
//	    account.setUser("service@pandahutai.com");
//	    account.setPass("Ecl123456!");
//	    account.setSocketFactoryClass("javax.net.ssl.SSLSocketFactory");
//	    account.setSslEnable(true);
		
		account.setHost("smtp.163.com");
	    account.setPort(465);
	    account.setAuth(true);
	    account.setFrom("xiaomo_swordsman@163.com");
	    account.setUser("xiaomo_swordsman");
	    account.setPass("CECWOMIRIHYMCDUS");
	    account.setSocketFactoryClass("javax.net.ssl.SSLSocketFactory");
	    account.setSslEnable(true);
		
	    System.out.println("AI课发送邮件[邮箱：" + email + "，标题：" + subject  + "]。");
	    
		return MailUtil.send(account, CollUtil.newArrayList(email), subject, textContent, isHtml); 
	}
	

}
