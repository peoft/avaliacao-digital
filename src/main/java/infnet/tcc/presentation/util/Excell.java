package infnet.tcc.presentation.util;

import infnet.tcc.entity.Avaliacao;
import java.io.*;
import java.util.*;
import jxl.*;
import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.Number;

import jxl.write.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.write.biff.RowsExceededException;

public class Excell {

    public static void getReport(List<String> reports, String filename) {

        if ("".equals(filename)) {
            filename = "Relatório";
        }
        try {
            WorkbookSettings ws = new WorkbookSettings();
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(new File(filename + ".xls"));

            WritableSheet sheet = workbook.createSheet(filename, 0);

            for (int i = 0; i < reports.size(); i++) {
                Label label = new Label(i, 0, "Test Count");
                sheet.addCell(label);

            }

            ws.setLocale(new Locale("pt", "BR"));

            //pegar valores report e criar labels e e fields
            workbook.write();
            workbook.close();

        } catch (IOException | WriteException ex) {
            Logger.getLogger(Excell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
