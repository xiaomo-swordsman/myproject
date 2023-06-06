package com.mytest.myservlet;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PdfReader {
	public static void main(String[] args) throws Exception {
		readFile();
//		readPage();
//		readTextImage();
//		readRectangle();
	}

	/**
	 * һ�λ�ȡ�����ļ�������
	 *
	 * @throws Exception
	 */
	public static void readFile() throws Exception {
		File file = new File("C:\\Users\\Administrator\\Desktop\\tt2t.pdf");
		RandomAccessFile is = new RandomAccessFile(file, "r");
		PDFParser parser = new PDFParser(is);
		parser.parse();
		PDDocument doc = parser.getPDDocument();
		PDFTextStripper textStripper = new PDFTextStripper();
		String s = textStripper.getText(doc);
		System.out.println("��ҳ����" + doc.getNumberOfPages());
		System.out.println("������ݣ�");
		System.out.println(s);
		doc.close();
	}

	/**
	 * ��ҳ��ȡ���ֵ�����
	 *
	 * @throws Exception
	 */
	public static void readPage() throws Exception {
		File file = new File("C:\\Users\\Administrator\\Desktop\\Abigail Bagshaw.pdf");
		RandomAccessFile is = new RandomAccessFile(file, "r");
		PDFParser parser = new PDFParser(is);
		parser.parse();
		PDDocument doc = parser.getPDDocument();
		PDFTextStripper textStripper = new PDFTextStripper();
		for (int i = 1; i <= doc.getNumberOfPages(); i++) {
			textStripper.setStartPage(i);
			textStripper.setEndPage(i);
			// һ��������ҳʱ����˳�����
			textStripper.setSortByPosition(true);
			String s = textStripper.getText(doc);
			System.out.println("��ǰҳ��" + i);
			System.out.println("������ݣ�");
			System.out.println(s);
		}
		doc.close();
	}

	/**
	 * ��ȡ�ı����ݺ�ͼƬ
	 *
	 * @throws Exception
	 */
	public static void readTextImage() throws Exception {
		File file = new File("C:\\Users\\Administrator\\Desktop\\Abigail Bagshaw.pdf");
		PDDocument doc = PDDocument.load(file);
		PDFTextStripper textStripper = new PDFTextStripper();
		for (int i = 1; i <= doc.getNumberOfPages(); i++) {
			textStripper.setStartPage(i);
			textStripper.setEndPage(i);
			String s = textStripper.getText(doc);
			System.out.println("�� " + i + " ҳ :" + s);
			// ��ȡͼƬ
			PDPage page = doc.getPage(i - 1);
			PDResources resources = page.getResources();
			// ��ȡҳ�еĶ���
			Iterable<COSName> xobjects = resources.getXObjectNames();
			if (xobjects != null) {
				Iterator<COSName> imageIter = xobjects.iterator();
				while (imageIter.hasNext()) {
					COSName cosName = imageIter.next();
					boolean isImageXObject = resources.isImageXObject(cosName);
					if (isImageXObject) {
						// ��ȡÿҳ��Դ��ͼƬ
						PDImageXObject ixt = (PDImageXObject) resources.getXObject(cosName);
						File outputfile = new File("�� " + (i) + " ҳ" + cosName.getName() + ".jpg");
						ImageIO.write(ixt.getImage(), "jpg", outputfile);
					}
				}
			}
		}
		doc.close();
	}

	/**
	 * �������
	 *
	 * @throws Exception
	 */
	public static void readRectangle() throws Exception {
		String filePath = "C:\\Users\\Administrator\\Desktop\\Abigail Bagshaw.pdf";
		File file = new File(filePath);
		PDDocument doc = PDDocument.load(file);
		// ����ı������������� y������Ϊ����x������Ϊ����
		int x = 35;
		int y = 300;
		int width = 50;
		int height = 50;
		PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		stripper.setSortByPosition(true);
		// ��������
		Rectangle2D rect = new Rectangle(x, y, width, height);
		stripper.addRegion("area", rect);
		PDPage page = doc.getPage(1);
		stripper.extractRegions(page);
		// ��ȡ�����text
		String data = stripper.getTextForRegion("area");
		data = data.trim();
		System.out.println(data);
		doc.close();
	}
}
