import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.read.biff.BiffException;
import jxl.write.*;
import java.io.*;
import java.lang.Boolean;
import java.util.ArrayList;
import java.util.HashMap;

public class FileWorker {
    Date date = Date.getInstance();
    TextHandler textHandler = new TextHandler();

    ArrayList<String> readFile(String pathWithName) {
        ArrayList<String> arrayList = new ArrayList<>();
        File file = new File(pathWithName);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (line != null) {
            arrayList.add(i, line);
            i = i + 1;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    } //возвращает лист из строк

    ArrayList<String> getFileList(String path) {
        ArrayList<String> filenames = new ArrayList<>();
        File folder = new File(path);
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                filenames.add(file.getName());
//                System.out.println(file.getName());
            }
        }
        return filenames;
    } //по указанному адресу, возвращает список файлов

    void checkNumberDocs(String pathOfBuhFile, String pathToRegedits, Integer column, Integer getFirst) {
        ArrayList<String> data = getColumnData(pathOfBuhFile, column, getFirst);
        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i) + " " + findNumberInRegedit(pathToRegedits, data.get(i)));
        }
    } //По бухгалтерской выгрузке позволяет проверить файлы реестров на наличие несданного документа, выводит найденные совпадения с указанием файла и пункта

    String findNumberInRegedit(String pathOfRegedits, String search) {
        String result = "";
        ArrayList<String> fileList = getFileList(pathOfRegedits);
//        System.out.println(pathOfRegedits);
        ArrayList<File> files = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            files.add(new File(pathOfRegedits + fileList.get(i)));
        }
        for (int i = 0; i < files.size(); i++) {
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!"+files.get(i).getName());
            Workbook readWorkBook = null;
            try {
                WorkbookSettings wbSettings = new WorkbookSettings();
                wbSettings.setSuppressWarnings(true);
                readWorkBook = Workbook.getWorkbook(files.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BiffException e) {
                e.printStackTrace();
            }
            Sheet readSheet = readWorkBook.getSheet(0);
            for (int j = 3; j < readSheet.getColumn(4).length; j++) {
                String cell = readSheet.getCell(4, j).getContents();
//                System.out.println(cell);
                if (cell.equals(search)) {
                    result = files.get(i).getName() + " " + readSheet.getCell(0, j).getContents() + " пункт";
                    break;
                }
            }
        }
        return result;
    }  //Ищет в каталоге с реестрами номер документа, возвращает имя файла реестра и пункт в котором найдено совпадение

    ArrayList<String> getColumnData(String pathWithName, Integer column, Integer getFirst) {
        Workbook readWorkBook = null;
        File file = new File(pathWithName);
        ArrayList<String> data = new ArrayList<>();
        try {
            readWorkBook = Workbook.getWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        Sheet readSheet = readWorkBook.getSheet(0);
        for (int i = getFirst; i < readSheet.getColumn(column).length; i++) {
            data.add(readSheet.getCell(column, i).getContents());
        }
        return data;
    } //Возвращает значения из указанной колонки, указанного файла, с указанием первого значения с которого начинать брать значения

    void renameFileListSimple(String pathRenamedFile, String pathSourceNameFile, String doc, String date) {
        int index = 99;
        String company = null;
        String finalResult = null;
        HashMap<String, Integer> counter = new HashMap<>();
        ArrayList<String> fileList = getFileList(pathSourceNameFile);
        for (int i = 0; i < fileList.size(); i++) { //Чтение списка файлов
            ArrayList<String> fileString = readFile(pathSourceNameFile + fileList.get(i)); //получение файла
            System.out.println("Прочитан " + i + " файл" + fileList.get(i));
            company = textHandler.findCompany(fileString, textHandler.counterAgentList);
            company = textHandler.deleteBadSymbols(company);
            System.out.println(company);
            if (company == null) {
                System.out.println("Company is empty");
                company = textHandler.findCompany(fileString);
            }
            if (company != null) {
                if (counter.containsKey(company)) {
                    int count = counter.get(company);
                    count++;
                    counter.put(company, count);
                } else counter.put(company, 1);
                finalResult = company + "_" + counter.get(company) + doc + date;
                System.out.println(index + ") " + finalResult);
                index++;
                File file = new File(pathRenamedFile + fileList.get(i));
                File newFile = new File(pathRenamedFile + finalResult + ".pdf");
                if (file.renameTo(newFile)) {
                } else {
                    System.out.println(newFile.toString());
                }
            }
        }
    } //переименование списка файлов исходя из названий компании  с счетчиком и сортировкой по одинаковым компаниям

    void createRegedit(String pathExcel, String pathSourceNameFile, Boolean original, String department) {
        int index = 100;
        String path = pathExcel + "\\" + "РЕЕСТР " + date.getDateForExcelFileName() + ".xls";
        Integer companyColumn = 0;
        Integer contractColumn = 0;
        String companyCycle = null;
        String contractCycle = null;
        String company = null;
        Contract contract = null;
        String add = "";
        String finalResult = "";
        ArrayList<String> fileList = getFileList(pathSourceNameFile);
        ArrayList<Label> labelList = new ArrayList<>();
        Date date = Date.getInstance();
        WritableWorkbook myFirstWbook = null;
        try {
            myFirstWbook = Workbook.createWorkbook(new File(path));
            WritableSheet excelSheet = myFirstWbook.createSheet("Лист1", 0);
            excelSheet = textHandler.createXLSTemplate(excelSheet, fileList, department);
            WritableFont cellFont = new WritableFont(WritableFont.createFont("CALIBRI"), 11);
            WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
            cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            cellFormat.setAlignment(Alignment.CENTRE);
            cellFormat.setWrap(true);
            cellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);

            for (int i = 0; i < fileList.size(); i++) { //Чтение списка файлов
                String price = null;
                Contract contract1 = null;
                Contract contract2 = null;
                Contract contract3 = null;
                CounterAgent counterAgent = null;
                CounterAgent counterAgent1 = null;
                CounterAgent counterAgent2 = null;
                CounterAgent counterAgent3 = null;
                CounterAgent counterAgent4 = null;
                ArrayList<String> fileString = readFile(pathSourceNameFile + fileList.get(i)); //получение файла
                System.out.println("--------------------------------------------------------");
                System.out.println("файл " + fileList.get(i) + " прочитан");

                for (int j = 0; j < textHandler.counterAgentList.size(); j++) {//поиск в тексте по номеру договора
                    for (int k = 0; k < textHandler.counterAgentList.get(j).contractList.size(); k++) {
                        if (textHandler.findString(fileString, textHandler.counterAgentList.get(j).contractList.get(k).contract.toUpperCase())) {
                            contract1 = textHandler.counterAgentList.get(j).contractList.get(k);
                            counterAgent1 = textHandler.counterAgentList.get(j);
                            break;
                        }
                    }
                    if (contract1 != null) break;
                }
                for (int j = 0; j < textHandler.counterAgentList.size(); j++) {//поиск в тексте имени организации
                    if (textHandler.findString(fileString, textHandler.counterAgentList.get(j).name.toUpperCase())) {
                        counterAgent2 = textHandler.counterAgentList.get(j);
                        break;
                    }
                }
                for (int j = 0; j < textHandler.counterAgentList.size(); j++) {//поиск в тексте по ИНН
                    if (textHandler.findString(fileString, textHandler.counterAgentList.get(j).INN)) {
                        counterAgent3 = textHandler.counterAgentList.get(j);
                        break;
                    }
                }
                for (int j = 0; j < textHandler.counterAgentList.size(); j++) { //поиск в тексте по ЛС
                    for (int k = 0; k < textHandler.counterAgentList.get(j).contractList.size(); k++) {
                        if (textHandler.counterAgentList.get(j).contractList.get(k).LS != null) {
                            if (textHandler.findString(fileString, textHandler.counterAgentList.get(j).contractList.get(k).LS.toUpperCase())) {
                                contract2 = textHandler.counterAgentList.get(j).contractList.get(k);
                                counterAgent4 = textHandler.counterAgentList.get(j);
                                break;
                            }
                        }
                    }
                }
                for (int j = 0; j < textHandler.counterAgentList.size(); j++) {
                    for (int k = 0; k < textHandler.counterAgentList.get(j).contractList.size(); k++) {
                        if (textHandler.findString(fileString, textHandler.counterAgentList.get(j).contractList.get(k).dateOfConclusion.toUpperCase())) { //поиск в тексте дату заключения договора
                            contract3 = textHandler.counterAgentList.get(j).contractList.get(k);
                            break;
                        }
                    }
                }
                //Проверка договора
                if (contract1 == contract2 && contract2 == contract3 && contract1 != null) {
                    contract = contract1;
                } else if (contract1 == contract2 && contract1 != null) {
                    contract = contract1;
                } else if (contract1 == contract3 && contract1 != null) {
                    contract = contract1;
                } else if (contract1 != null) {
                    contract = contract1;
                } else if (contract2 != null) {
                    contract = contract2;
                } else if (contract3 != null) {
                    contract = contract3;
                }
                //проверка организации
                if (counterAgent1 == counterAgent2 && counterAgent2 == counterAgent3 && counterAgent3 == counterAgent4 && counterAgent1 != null) {
                    counterAgent = counterAgent1;
                } else if (counterAgent1 == counterAgent2 && counterAgent2 == counterAgent3 && counterAgent1 != null) {
                    counterAgent = counterAgent1;
                } else if (counterAgent2 == counterAgent3 && counterAgent2 != null) {
                    counterAgent = counterAgent2;
                } else if (counterAgent2 != null) {
                    counterAgent = counterAgent2;
                } else if (counterAgent3 != null) {
                    counterAgent = counterAgent3;
                }
                Label label1 = new Label(0, i + 3, String.valueOf(index), cellFormat);
                excelSheet.addCell(label1);
                index++;
                if (counterAgent != null) {
                    Label companyLabel = new Label(1, i + 3, counterAgent.fullName, cellFormat);
                    excelSheet.addCell(companyLabel);
                    if (contract != null) {
                        if (contract.contract != contractCycle) {
                            System.out.println(contract.contract + ", " + contract.dateOfConclusion);
                            Label label2 = new Label(2, i + 3, contract.contract + ", " + contract.dateOfConclusion, cellFormat);
                            excelSheet.addCell(label2);
                            contractColumn = 2;
                        } else {
//                            excelSheet.mergeCells(2, i+1-contractColumn, 2, i+2);
                            contractColumn++;
                        }
                        contractCycle = contract.contract;
                    }
                }

                for (int j = 0; j < textHandler.documents.size(); j++) {
                    if (textHandler.findString(fileString, textHandler.documents.get(j))) {
                        Label label = new Label(3, i + 3, textHandler.documents.get(j), cellFormat);
                        excelSheet.addCell(label);
                        break;
                    }
                }
                for (int j = 0; j < textHandler.documents.size(); j++) {
                    if (textHandler.findString(fileString, textHandler.documents.get(j))) {
                        String docNumber = textHandler.findDocNumber(fileString, textHandler.documents.get(j), textHandler.exceptions, textHandler.prefixes);
                        Label label2 = new Label(4, i + 3, docNumber, cellFormat);
                        excelSheet.addCell(label2);
                        if (docNumber != null) break;
                    }
                }
                String dateDoc = date.findDate(fileString);
                if (dateDoc != null) {
                    Label labelDate = new Label(5, i + 3, dateDoc, cellFormat);
                    excelSheet.addCell(labelDate);
                    Label labelMonthYear = new Label(6, i + 3, date.getMonthYear(dateDoc), cellFormat);
                    excelSheet.addCell(labelMonthYear);
                }
                if (original) {
                    Label labelOriginal = new Label(7, i + 3, "V", cellFormat);
                    excelSheet.addCell(labelOriginal);
                } else {
                    for (int j = 3; j < fileList.size() + 3; j++) {
                        Label label = new Label(7, j, "", cellFormat);
                        excelSheet.addCell(label);
                    }
                }
                if (price == null) {
                    price = textHandler.findPrice(fileString);
                    Label label = new Label(8, i + 3, price, cellFormat);
                    excelSheet.addCell(label);
                }
            }
            myFirstWbook.write();
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                WriteException e) {
            e.printStackTrace();
        } finally {

            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();

                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }

    } //создаёт Excel файл с реестром документов, на основе распознанных сканов (в txt) и базы контрагентов

}