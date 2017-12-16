package infnet.tcc.presentation.util;

import java.io.*;
import java.util.*;
import jxl.*;
import jxl.Workbook;

import jxl.write.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

public class Excell {

    public static synchronized void getReport(Map<String, List<String>> reports, String filename) {

        if ("".equals(filename)) {
            filename = "Relatório";
        }

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls");

            WorkbookSettings ws = new WorkbookSettings();
            WritableWorkbook workbook;

            workbook = Workbook.createWorkbook(response.getOutputStream());

            WritableSheet sheet = workbook.createSheet(filename, 0);

            int x = 0;
            for (Map.Entry<String, List<String>> entry : reports.entrySet()) {
                String header = entry.getKey();
                // St celula n1
                Label headerLabel = new Label(x, 0, header);

                sheet.addCell(headerLabel);

                List<String> values = entry.getValue();
                for (int y = 0; y < values.size(); y++) {
                    Label valueLabel = new Label(x, y + 1, values.get(y));

                    sheet.addCell(valueLabel);
                }

                x++;
            }

            ws.setLocale(new Locale("pt", "BR"));

            workbook.write();
            workbook.close();

            context.responseComplete();

        } catch (IOException | WriteException ex) {
            Logger.getLogger(Excell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
