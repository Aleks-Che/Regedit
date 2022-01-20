
public class Main {
    public static void main(String[] args) {
        FileWorker fileWorker = new FileWorker();
        String folder = "C:\\git\\1\\";
        fileWorker.createRegedit("C:\\git\\", folder,  true, "ОИТ"); //Создание реестра документов по распознанным сканам в txt формате
//        fileIO.renameFileListSimple("C:\\git\\4\\", "C:\\git\\3\\", "_Счет_","31_03_2021"); //Переименование сканов документов с сортировкой по каталогам по компаниям, на основе распознанных документов
//        fileIO.checkNumberDocs("C:\\git\\analis_23.11.2021.xls","C:\\git\\Реестры\\",5,4); //Проверка файла xls, на сданные в бухгалтерию файлы (проверка всех сданных реестров из папки с реестрами)
    }
}

