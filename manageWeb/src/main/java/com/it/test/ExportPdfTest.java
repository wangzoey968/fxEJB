package com.it.test;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.junit.Test;

import java.io.FileOutputStream;

/**
 * Created by wangzy on 2018/4/14.
 */
public class ExportPdfTest {

    /**
     * 使用itext生成pdf
     * 导出pdf
     */
    @Test
    public void exportPdf() throws Exception {
        //页面大小
        Rectangle rect = new Rectangle(PageSize.A4);
        //页面背景色
        rect.setBackgroundColor(BaseColor.CYAN);
        Document doc = new Document(rect);
        FileOutputStream out = new FileOutputStream("E:/createSamplePDF.pdf");
        PdfWriter writer = PdfWriter.getInstance(doc, out);

        //PDF版本(默认1.4)
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
        //设置密码,打开的时候需要输入密码
        /*writer.setEncryption("Hello".getBytes(), "World".getBytes(),
                PdfWriter.ALLOW_SCREENREADERS,
                PdfWriter.STANDARD_ENCRYPTION_128);*/
        //文档属性
        doc.addTitle("Title@sample");
        doc.addAuthor("Author@rensanning");
        doc.addSubject("Subject@iText sample");
        doc.addKeywords("Keywords@iText");
        doc.addCreator("Creator@iText");
        //页边空白
        doc.setMargins(10, 20, 30, 40);
        doc.open();
        doc.add(new Paragraph("Hello World"));

        //为pdf添加图片
        Image image = Image.getInstance("E:/test.png");
        image.setAlignment(Image.TEXTWRAP);
        image.setBorder(Image.BOX);
        image.setBorderWidth(10);
        image.setBorderColor(BaseColor.CYAN);
        image.scaleToFit(1000,72);
        image.setRotationDegrees(-10);
        doc.add(image);

        //输出为zip
        //ZipOutputStream zip = new ZipOutputStream(new FileOutputStream("E:/zipPDF.zip"));

        PdfContentByte cb = writer.getDirectContent();
        String myString = "http://www.baidu.com";
        //生成条形码
        Barcode128 code128 = new Barcode128();
        code128.setCode(myString.trim());
        code128.setCodeType(Barcode128.CODE128);
        Image code128Image = code128.createImageWithBarcode(cb, BaseColor.GRAY, BaseColor.GREEN);
        code128Image.setAbsolutePosition(20,50);
        code128Image.scalePercent(125);
        doc.add(code128Image);
        //生成二维码
        BarcodeQRCode qrcode = new BarcodeQRCode(myString.trim(), 1, 1, null);
        Image qrcodeImage = qrcode.getImage();
        qrcodeImage.setAbsolutePosition(40,100);
        qrcodeImage.scalePercent(200);
        doc.add(qrcodeImage);

        doc.close();

    }

}
