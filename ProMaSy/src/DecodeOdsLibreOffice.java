import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class DecodeOdsLibreOffice {

	public static void main(String[] args) {

		File odsFile = new File("D:\\Dropbox\\IBChem DB\\old_db\\users.ods");
		try {
			Sheet sheet = SpreadSheet.createFromFile(odsFile).getSheet(0);
			for(int i = 2; i<29; i++){
				sheet.getCellAt("B"+i).setValue(decodeString(sheet.getCellAt("B"+i).getTextValue()));
			}
			File outputFile = new File("D:\\Dropbox\\IBChem DB\\old_db\\users_ed.ods");
			OOUtils.open(sheet.getSpreadSheet().saveAs(outputFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
		
		
	}
	
	private static String decodeString(String encoded){
		byte[] b = encoded.getBytes(Charset.forName("Cp1252"));
		String decoded = new String(b, Charset.forName("Cp1251") );
		return decoded;
	}

}
