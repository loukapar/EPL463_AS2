/**
 * Copyright (c) 2018 Paraskevas Louka.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author EPL463
 */
public class iTextPDFWriter {

	public iTextPDFWriter(String input) {

		try {
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);

			@SuppressWarnings("unused")
			//create a new pdf writer,file with name Statement.pdf
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Statement.pdf"));
			document.open();
			//create a new paragraph with line spacing 12
			Paragraph paraT = new Paragraph(12);
			//set spacing after paragraph 
			paraT.setSpacingAfter(8f);
			//set center alignment for the paragraph
			paraT.setAlignment(Element.ALIGN_CENTER);
			//create a new font
			Font titleF = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLDITALIC);
			//set text color for the font titleF
			titleF.setColor(BaseColor.BLUE);
			//create a new chunk type of text with font titleF
			Chunk title = new Chunk("BANK ACCOUNT STATEMENT\n", titleF);
			//underline the above line
			title.setUnderline(2f, -4f);
			//add title in paragraph
			paraT.add(title);
			//add new line in paragraph
			paraT.add(new Phrase("\n"));
			//create a new font
			Font dateFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.ITALIC);
			//find current date
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			Chunk d = new Chunk(dateFormat.format(date), dateFont);
			//add date in paragraph
			paraT.add(d);
			//add paragraph in pdf document
			document.add(paraT);

			document.add(new Chunk("\n"));
			//create new paragraph line spacing = 24
			Paragraph paraB = new Paragraph(24);
			paraB.setAlignment(Element.ALIGN_LEFT);
			String data[] = input.split("\n");
			//declare fonts
			Font infodataF = new Font(Font.FontFamily.COURIER, 15, Font.ITALIC);
			Font infoF = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.NORMAL);

			//declare table
			PdfPTable table = null;
			for (int i = 0; i < data.length; i++) {
				//if i = 5 then transaction history
				if (i == 5) {
					String[] history = (data[i].split(":", 2)[1]).split(",");

					//set table with 4 columns
					table = new PdfPTable(4);
					//set table to have width 100%
					table.setWidthPercentage(100f);

					Font f = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLDITALIC);
					f.setColor(BaseColor.WHITE);
					//add Phrase in first row of table
					PdfPCell arraytitle = new PdfPCell(new Phrase("Transaction History", f));
					//set background color for cell
					arraytitle.setBackgroundColor(BaseColor.BLUE);
					//merge all columns for first row
					arraytitle.setColspan(4);
					//set horizontal and vertical allignment as center
					arraytitle.setHorizontalAlignment(Element.ALIGN_CENTER);
					arraytitle.setVerticalAlignment(Element.ALIGN_CENTER);
					//add cell to table
					table.addCell(arraytitle);

					//create other cells for the fields date, time, amount and location with font
					//add cells in table
					PdfPCell datecell = new PdfPCell(new Phrase("Date",
							new Font(Font.FontFamily.HELVETICA, 15, Font.BOLDITALIC, BaseColor.BLUE)));
					table.addCell(datecell);

					PdfPCell timecell = new PdfPCell(new Phrase("Time",
							new Font(Font.FontFamily.HELVETICA, 15, Font.BOLDITALIC, BaseColor.BLUE)));
					table.addCell(timecell);

					PdfPCell amountcell = new PdfPCell(new Phrase("Amount",
							new Font(Font.FontFamily.HELVETICA, 15, Font.BOLDITALIC, BaseColor.BLUE)));
					table.addCell(amountcell);

					PdfPCell locationcell = new PdfPCell(new Phrase("Location",
							new Font(Font.FontFamily.HELVETICA, 15, Font.BOLDITALIC, BaseColor.BLUE)));
					table.addCell(locationcell);

					//add data of transaction history in table
					for (int j = 0; j < history.length; j++) {
						table.addCell(history[j].split(" - ")[1]);
						table.addCell(history[j].split(" - ")[0]);
						table.addCell(history[j].split(" - ")[2]);
						table.addCell(history[j].split(" - ")[3]);
					}
					table.setHorizontalAlignment(0);

				} else {
					String line[] = data[i].split(":");
					Chunk info = new Chunk(line[0] + ":", infoF);
					//underline chunk
					info.setUnderline(1f, -4f);
					paraB.add(info);
					Chunk infodata = null;
					infodata = new Chunk(line[1] + "\n", infodataF);
					paraB.add(infodata);
				}
			}
			//add table to paraB
			paraB.add(table);
			//add paraB to document
			document.add(paraB);
			document.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}
}
