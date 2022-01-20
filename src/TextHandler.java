import jxl.Sheet;
import jxl.Workbook;
import jxl.format.BorderLineStyle;
import jxl.read.biff.BiffException;
import jxl.write.*;

import java.io.File;
import java.io.IOException;
import java.lang.Boolean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class TextHandler {
    ArrayList<String> exceptions = new ArrayList<>(Arrays.asList("EX466", "ЕХ466", "PTK ", "РТК "));
    ArrayList<String> prefixes = new ArrayList<>(Arrays.asList("СМР", "ПНЗ", "ПРМ"));
    ArrayList<String> letters = new ArrayList<>(Arrays.asList("а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "щ", "ш", "ы", "э", "ю", "я"));
    ArrayList<String> badSymbols = new ArrayList(Arrays.asList("/", ".", "@", ";", ":", "\\", "?", "*", "|", ">", "<", "\"", "’", "■","N°"));
    ArrayList<String> numbers = new ArrayList(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"));
    ArrayList<String> documents = new ArrayList(Arrays.asList("Универсальный передаточный документ", "Счёт-фактура", "Счет-фактура", "Акт оказанных услуг", "Акт выполненных работ", "Акт ", "Отчет", "Лист согласования", "Счет №", "Счет №", "Счет ", "Счет:"));
    ArrayList<CounterAgent> counterAgentList = createCounterAgentList();

    ArrayList<CounterAgent> createCounterAgentList() {
        Contract rostElanTel = new Contract("566001839135", "3904121", "30.03.2012");
        Contract rostElanInt = new Contract("566002464683", "3358688", "28.03.2016");
        Contract rostSvobTel = new Contract("566001188311", "3939081", "16.04.2012");
        Contract rostSvobInt = new Contract("566001177463", "3939024", "10.04.2012");
        Contract rostChebInt = new Contract("574001004052", "3686161", "18.01.2012");
        Contract rostChebTel = new Contract("574000972460", "3448206", "03.11.2011");
        Contract rostKarabInt = new Contract("574001006581", "3204503", "01.09.2011");
        Contract rostKarabTel = new Contract("574000937621", "3168209", "01.09.2011");
        Contract rostRoshinsk = new Contract("363000062951", "562951", "01.09.2012");
        Contract rostSizranInt = new Contract("363000019363", "1019363", "01.01.2014");
        Contract rostSizranTel = new Contract("363000034666", "34666", "01.01.2014");
        Contract rostUlyanovsk = new Contract("373000007846", "7846", "30.09.2011");
        Contract rostSaratovInt = new Contract("364000008208", "138475", "24.05.2011");
        Contract rostSaratovTel = new Contract("364000040542", "650000140542", "22.12.2010");
        Contract rostVolskInt = new Contract("364000011163", "643190011163", "28.09.2011");
        Contract rostVolskTel = new Contract("364000067583", "650000167583", "27.09.2011");
        Contract rostBalashovInt = new Contract("364000002270", "643990002270", "01.09.2011");
        Contract rostBalashovTel = new Contract("364000221882", "650000121882", "01.09.2011");
        Contract rostTockoe = new Contract("356000102872", "6002872", "19.09.2011");
        Contract rostYasnensk = new Contract("356000011311", "268111311", "01.09.2010");
        Contract rostKuzneck = new Contract("358000271548", "2571548", "28.12.2012");
        Contract rostKamenka = new Contract("358000170720", "2670720", "01.10.2013");
        ArrayList<Contract> rostelekomList = new ArrayList<>(Arrays.asList(rostKamenka, rostBalashovInt, rostBalashovTel, rostChebInt, rostChebTel, rostElanInt, rostElanTel, rostKarabInt, rostKarabTel, rostKuzneck, rostRoshinsk, rostSaratovInt, rostSaratovTel, rostSizranInt, rostSizranTel, rostSvobInt, rostSvobTel, rostTockoe, rostUlyanovsk, rostVolskInt, rostVolskTel, rostYasnensk));
        CounterAgent rostelecom = new CounterAgent("Ростелеком", "ПАО \"Ростелеком\"", "7707049388", rostelekomList);
        Contract ertPermATL = new Contract(null, "10677326 ATL", "01.11.2011");
        Contract ertPermSOV = new Contract(null, "10677326 SOV", "01.11.2011");
        Contract ertPermTel = new Contract(null, "10677326", "01.11.2011");
        Contract ertPermInt = new Contract(null, "Е10677270", "01.11.2011");
        Contract ertTumen = new Contract(null, "TMN03020", "31.03.2013");
        Contract ertSamaraInt = new Contract(null, "Е7759963", "01.09.2011");
        Contract ertSamaraTel = new Contract(null, "9289671", "20.10.2012");
        Contract ertPenza = new Contract(null, "Е10508284", "21.12.2016");
        ArrayList<Contract> ertList = new ArrayList<>(Arrays.asList(ertPenza, ertPermATL, ertPermInt, ertPermSOV, ertPermTel, ertSamaraInt, ertSamaraTel, ertTumen));
        CounterAgent ertelecom = new CounterAgent("ЭР-Телеком", "АО \"ЭР-Телеком Холдинг\"", "5902202276", ertList);
        Contract mtsIzhevsk = new Contract("218302150461", "118302085780/11", "23.09.2011");
        Contract mtsAupInt = new Contract("266385341319", "166384481134", "01.09.2011");
        Contract mtsAupGTS = new Contract("50108013254", "50108574336", "01.09.2011");
        Contract mtsAupMg = new Contract("50108013256", "К-50108574336", "01.09.2011");
        Contract mtsSaratov = new Contract("30338001902", "30338001902", "01.11.2016");
        ArrayList<Contract> mtsList = new ArrayList<>(Arrays.asList(mtsAupGTS, mtsAupInt, mtsAupMg, mtsIzhevsk, mtsSaratov));
        CounterAgent mts = new CounterAgent("МТС", "ПАО \"МТС\"", "7740000076", mtsList);
        Contract megAup = new Contract("17339915", "ФД-11/36", "10.08.2010");
        Contract megUfa = new Contract("44847972", "ФД-11/36", "06.06.2012");
        Contract megSamara = new Contract("42274724", "ФД-11/36", "07.02.2013");
        Contract megChelyabinsk = new Contract("735035706", "К-2456", "10.08.2010");
        ArrayList<Contract> megafonList = new ArrayList<>(Arrays.asList(megSamara, megUfa, megAup, megChelyabinsk));
        CounterAgent megafon = new CounterAgent("МегаФон", "ПАО \"МегаФон\"", "7812014560", megafonList);
        Contract beelineShihani = new Contract(null, "416391081", "22.09.2011");
        Contract beelineUlInt = new Contract(null, "EX466", "23.10.2012");
        Contract beelineUlSot = new Contract(null, "548855176", "26.01.2015");
        ArrayList<Contract> beelineList = new ArrayList<>(Arrays.asList(beelineShihani, beelineUlInt, beelineUlSot));
        CounterAgent beeline = new CounterAgent("ВымпелКом", "ПАО \"ВымпелКом\"", "7713076301", beelineList);
        Contract mkvis = new Contract(null, "15110006", "11.08.2015");
        ArrayList<Contract> mkvisList = new ArrayList<>(Arrays.asList(mkvis));
        CounterAgent mkvisota = new CounterAgent("Высота", "АО \"МК \"Высота\"", "6672212600", mkvisList);
        Contract impSvob = new Contract(null, "1476-ю", "16.11.2011");
        ArrayList<Contract> impulsList = new ArrayList<>(Arrays.asList(impSvob));
        CounterAgent impuls = new CounterAgent("Импульс", "МУП связи \"Импульс\"", "6607008523", impulsList);
        Contract ufanetInt = new Contract(null, "RK2709-12", "19.10.2012");
        Contract ufanetTel = new Contract(null, "ГТЮ0670-12", "19.10.2012");
        ArrayList<Contract> ufanetList = new ArrayList<>(Arrays.asList(ufanetInt, ufanetTel));
        CounterAgent ufanet = new CounterAgent("Уфанет", "АО \"Уфанет\"", "0278109628", ufanetList);
        Contract mediasetiSamara = new Contract(null, "СМР-657-01/14", "01.02.2014");
        ArrayList<Contract> mediasetiList = new ArrayList<>(Arrays.asList(mediasetiSamara));
        CounterAgent mediaseti = new CounterAgent("МедиаСети", "ООО \"МедиаСети\"", "7714955136", mediasetiList);
        Contract ttkShihani = new Contract(null, "641100588", "26.11.2013");
        ArrayList<Contract> ttkList = new ArrayList<>(Arrays.asList(ttkShihani));
        CounterAgent ttk = new CounterAgent("ТрансТелеКом", "АО \"Компания ТрансТелеКом\"", "7709219099", ttkList);
        Contract tele2Sizgan = new Contract("65270594/54566", "65270594", "20.09.2016");
        ArrayList<Contract> tele2List = new ArrayList<>(Arrays.asList(tele2Sizgan));
        CounterAgent tele2 = new CounterAgent("Т2 Мобайл", "ООО \"Т2 Мобайл\"", "7743895280", tele2List);
        Contract bashInt = new Contract("302000042192", "8416807", "29.09.2011");
        Contract bashGTS = new Contract("302000016807", "16807", "29.09.2011");
        Contract bashMg = new Contract("302000016807", "16807/РТК", "29.09.2011");
        ArrayList<Contract> bashlist = new ArrayList<>(Arrays.asList(bashGTS, bashInt, bashMg));
        CounterAgent bash = new CounterAgent("БАШИНФОРМСВЯЗЬ", "ПАО \"БАШИНФОРМСВЯЗЬ\"", "0274018377", bashlist);
        Contract auto = new Contract(null,"184-УРЛ-2021", "18.08.2021");
        ArrayList<Contract> autonom = new ArrayList<>(Arrays.asList(auto));
        CounterAgent autonomService = new CounterAgent("АВТОНОМСЕРВИС","ООО \"АВТОНОМСЕРВИС\"", "7325160225",autonom);
        Contract universalTehsnab = new Contract(null,"113-УРЛ-2021", "09.06.2021");
        ArrayList<Contract> uniTehsnab = new ArrayList<>(Arrays.asList(universalTehsnab));
        CounterAgent universalTehsnabС = new CounterAgent("УНИВЕРСАЛТЕХСНАБ","ООО \"УНИВЕРСАЛТЕХСНАБ\"", "63116201160",uniTehsnab);

//    CounterAgent sctehnologii = new CounterAgent("Технологии", "ООО СЦ \"Технологии\"", "7447234139");
        ArrayList<CounterAgent> counterAgentList = new ArrayList(Arrays.asList(rostelecom, ertelecom, mts, megafon, beeline, mkvisota, impuls, ufanet, mediaseti, ttk, tele2, bash, autonomService, universalTehsnabС));
//        ArrayList<Contract> contractList = new ArrayList<>(Arrays.asList(megAup, megUfa, megChelyabinsk, megSamara, mtsIzhevsk, mtsAupGTS, mtsAupInt, mtsAupMg, mtsSaratov, ertPermATL, ertPermInt, ertPermSOV, ertPermTel, ertSamaraInt, ertSamaraTel, ertTumen, ertPenza, bashGTS, bashInt, bashMg, impSvob, mkvis, mediasetiSamara, beelineShihani, beelineUlInt, beelineUlSot, tele2Sizgan, ttkShihani, ufanetInt, ufanetTel, rostKamenka, rostBalashovInt, rostBalashovTel, rostChebInt, rostChebTel, rostElanInt, rostElanTel, rostKarabInt, rostKarabTel, rostKuzneck, rostRoshinsk, rostSaratovInt, rostSaratovTel, rostSizranInt, rostSizranTel, rostSvobInt, rostSvobTel, rostYasnensk, rostVolskInt, rostVolskTel, rostTockoe, rostUlyanovsk, uniTehsnab, autonom));
        return counterAgentList;
    }

    boolean findString(ArrayList<String> text, String request) {
        Boolean result = false;
        for (int i = 0; i < text.size(); i++) {
            if (deleteSymbols(text.get(i).toUpperCase(), " ").contains(deleteSymbols(request, " ").toUpperCase())) {
                result = true;
                break;
            }
        }
        return result;
    }

    String findCompany(ArrayList<String> text) {
        String result = null;
        for (int i = 0; i < text.size(); i++) {
            for (int j = 0; j < counterAgentList.size(); j++) {
                if (deleteBadSymbols(text.get(i).toUpperCase()).contains(deleteBadSymbols(counterAgentList.get(j).fullName.toUpperCase()))) {
                    result = counterAgentList.get(j).fullName;
                    System.out.println("Find counter Agent " + counterAgentList.get(j));
                    break;
                }
            }
            if (result != null) break;
        }
        return result;
    }

    String findDocNumber(ArrayList<String> text, String request, ArrayList<String> exceptions, ArrayList<String> prefixes) {
        String result = null;
        for (int i = 0; i < text.size(); i++) {
            if (deleteSymbols(text.get(i), " ").toUpperCase().contains(deleteSymbols(request, " ").toUpperCase())) {
                System.out.println("1. ");
                result = findDocNumberInString(deleteSymbols(text.get(i), " "), deleteSymbols(request, " "), exceptions, prefixes);
                if (result == null && (text.size() > i + 1)) {
                    if (text.get(i + 1).contains("№ ")) {
                        result = findDocNumberInString(text.get(i + 1), "№ ", exceptions, prefixes);
                        System.out.println("2. ");
                    }
                }
                if (result == null && (text.size() > i + 2)) {
                    if (text.get(i + 2).contains("№ ")) {
                        result = findDocNumberInString(text.get(i + 2), "№ ", exceptions, prefixes);
                        System.out.println("3. ");
                    }
                }
                if (result == null && (text.size() > i + 1)) {
                    result = findDocNumberInString(text.get(i + 1), "Ns ", exceptions, prefixes);
                    System.out.println("4. ");
                }
            }
            if (result != null) break;
        }
        return result;
    }

    String findPrice(ArrayList<String> text) {
        String result = null;
        Integer startIndex = null;
        Integer endIndex = null;
        for (int i = 0; i < text.size(); i++) {
            if (text.get(i).contains("Итого") || text.get(i).contains("Всего к оплате")) {
                for (int j = text.get(i).length(); j > 0; j--) {
                    for (int k = 0; k < numbers.size(); k++) {
                        if (endIndex != null) {
                            break;
                        }
                        if (text.get(i).substring(j - 1, j).contains(numbers.get(k))) {
                            endIndex = j;
                            break;
                        }
                    }
                    if (endIndex != null && startIndex != null) break;
                    if (endIndex != null) {
                        if ((text.get(i).substring(j - 1, j).contains("\t")) || (text.get(i).substring(j - 1, j).contains("\n"))) {
                            startIndex = j;
                            break;
                        }
                    }
                }
                if (startIndex != null && endIndex != null) {
                    result = text.get(i).substring(startIndex, endIndex);
                    break;
                }
            }
        }
        if (result != null) {
            if (result.contains("'")) {
                result = result.replace("'", ",");
            }
            if (result.contains("-")) {
                result = result.replace("-", ",");
            }
            if (result.contains(".")) {
                result = result.replace(".", ",");
            }
        }
        System.out.println(result);
        return result;
    }

    String findDocNumberInString(String text, String request, ArrayList<String> exceptions, ArrayList<String> prefixes) {
        String result = null;
        Integer startIndex;
        Integer endIndex = null;
        String exception = null;
        String prefix = null;
        startIndex = text.toUpperCase().indexOf(request.toUpperCase()) + request.length();
        for (int j = 0; j < exceptions.size(); j++) {
            if (text.toUpperCase().substring(startIndex, text.length()).contains(exceptions.get(j).toUpperCase())) {
                exception = exceptions.get(j);
            }
        }
        for (int i = 0; i < prefixes.size(); i++) {
            if (text.toUpperCase().substring(startIndex, text.length()).contains(prefixes.get(i).toUpperCase())) {
                prefix = prefixes.get(i);
                startIndex = text.toUpperCase().substring(startIndex, text.length()).indexOf(prefix.toUpperCase()) + prefix.length();
                System.out.println(text.substring(startIndex, text.length()));
            }
        }
        for (int j = 0; j < text.substring(startIndex, text.length()).length(); j++) {
            for (int k = 0; k < letters.size(); k++) {
                if (text.substring(startIndex + j, startIndex + j + 1).toUpperCase().equals(letters.get(k).toUpperCase()) || startIndex + j + 1 == text.length()) {
                    endIndex = startIndex + j;
                    result = text.substring(startIndex, endIndex);
                    if (result != null) {
                        result = deleteSymbols(result, "Ns ");
                        result = deleteSymbols(result, "Ns");
                        result = deleteSymbols(result, "Na ");
                        result = deleteSymbols(result, "Na");
                        result = deleteSymbols(result, " ");
                        result = deleteSymbols(result, "№");
                    }
                    if (result.length() > 2 && result != null) {
                        if (exception != null) {
                            result = result + exception;
                        }
                        if (prefix != null) {
                            if (result.contains(prefix)) {
                            } else {
                                result = prefix + result;
                            }
                        }
                        System.out.println("результат поиска номера дока: " + result);
                        break;
                    }
                }
            }
            if (endIndex != null && result.length() > 3) {
                break;
            }
        }
        return result;
    }

    String findDocName(ArrayList<String> text, ArrayList<String> request, ArrayList<CounterAgent> counterAgentList) {
        String result = null;
        for (int i = 0; i < request.size(); i++) {
            for (int j = 0; j < text.size(); j++) {
                if (text.get(j).toUpperCase().contains(request.get(i).toUpperCase())) { //ищем в тексте название типа документа (счет-фактура, акт ...)
                    result = text.get(j).substring(text.get(j).toUpperCase().indexOf(request.get(i).toUpperCase()));
                    break;
                }
                for (int k = 0; k < counterAgentList.size(); k++) {
                    for (int l = 0; l < counterAgentList.get(k).contractList.size(); l++) {
                        if (text.get(j).toUpperCase().contains(counterAgentList.get(k).contractList.get(l).LS.toUpperCase())) {
                            for (int m = 0; m < request.size(); m++) {
                                for (int n = 0; n < text.size(); n++) {
                                    if (text.get(n).toUpperCase().contains(request.get(m).toUpperCase())) {
                                        result = text.get(n).substring(text.get(n).toUpperCase().indexOf(request.get(m).toUpperCase()));
                                        break;
                                    }
                                    if (result != null) break;
                                }
                                if (result != null) break;
                            }
                        }
                        if (result != null) break;
                    }
                    if (result != null) break;
                }
                if (result != null) break;
            }
            if (result != null) break;
        }
        return result;
    }

    String findLS(ArrayList<String> text, String request) {
        String result = null;
        for (int j = 0; j < text.size(); j++) {
            if (text.get(j).toUpperCase().contains(request.toUpperCase())) {
                result = text.get(j).substring(text.get(j).toUpperCase().indexOf(request.toUpperCase()));
                break;
            }
        }
        return result;
    }

    String findCompany(ArrayList<String> text, ArrayList<CounterAgent> counterAgentList) {
        String result = null;
        for (int i = 0; i < counterAgentList.size(); i++) { //поиск контрагента по имени
            for (int j = 0; j < text.size(); j++) {
                if (text.get(j).toUpperCase().contains(counterAgentList.get(i).name.toUpperCase())) {
                    result = counterAgentList.get(i).fullName;
                }
                if (result != null) break;
            }
        }
        if (result == null) {
            for (int i = 0; i < text.size(); i++) {//поиск контрагента по ИНН
                for (int j = 0; j < counterAgentList.size(); j++) {
                    for (int k = 0; k < counterAgentList.get(j).contractList.size(); k++) {
                        if (text.get(i).contains(counterAgentList.get(j).INN)) {
                            result = counterAgentList.get(j).fullName;
                        } else {
                            for (int o = 0; o < counterAgentList.size(); o++) { //поиск по ЛС
                                for (int r = 0; r < counterAgentList.get(o).contractList.size(); r++) {
                                    if (text.get(i).toUpperCase().contains(counterAgentList.get(o).contractList.get(r).LS.toUpperCase())) {
                                        result = counterAgentList.get(o).fullName;
                                        break;
                                    }
                                    if (result != null) break;
                                }
                                if (result != null) break;
                            }
                        }
                        if (result != null) break;
                    }
                    if (result != null) break;
                }
                if (result != null) break;
            }
//            else{
//                for (int p = 0; p < counterAgentList.size(); p++) {
//                    for (int l = 0; l < text.size(); l++) {
//                        String s = deleteBadSymbols(text.get(l).toUpperCase());
//                        s = deleteSymbols(s, "\"");
//                        if (s.contains(contractList.get(p).counterAgent.name.toUpperCase().trim())) {
//                            result = contractList.get(p).counterAgent.fullName;
//                        }
//                    }
//                }
//            }
        }
        return result;
    }

    String replaceOnDashSymbol(String str) {
        if (str != null) {
            str = str.replace("\\", "-");
            str = str.replace("/", "-");
            str = str.replace(".", "_");
        }
        return str;
    } //Замена символов на тире и нижнее подчеркивание

    String cutText(String text, String cutText) {
        int endIndex = text.toUpperCase().indexOf(cutText.toUpperCase());
        text = text.substring(0, endIndex) + "_" + text.substring(endIndex + cutText.length(), text.length());
        return text;
    }

    String deleteTextToEnd(String text, String deleteText) {
        text = text.substring(0, text.toUpperCase().indexOf(deleteText.toUpperCase()));
        return text;
    }

    String replaceLine(String str, String dateText) {
        Date date = Date.getInstance();
        Integer month;
        if (dateText.substring(6, 7) == "0") {
            month = Integer.parseInt(dateText.substring(7, 8));
        } else month = Integer.parseInt(dateText.substring(6, 8));
        if (str != null) {
            if (str.toUpperCase().contains("OT" + dateText.substring(9, 11))) {
                str = deleteTextToEnd(str, "OT" + dateText.substring(9, 11));
            }
            if (str.toUpperCase().contains("ОТ" + dateText.substring(9, 11))) {
                str = deleteTextToEnd(str, "ОТ" + dateText.substring(9, 11));
            }
            if (str.toUpperCase().contains("ЗА" + date.getMonth(month).toUpperCase())) {
                str = deleteTextToEnd(str, "ЗА" + date.getMonth(month).toUpperCase());
            }
            if (str.toUpperCase().contains("ПО" + dateText.substring(9, 11))) {
                str = deleteTextToEnd(str, "ПО" + dateText.substring(9, 11));
            }
            if (str.toLowerCase().contains("№")) {
                str = str.replace("№", "_");
            }
            if (str.toUpperCase().contains("ВЫПОЛНЕННЫХРАБОТ(ОКАЗАННЫХУСЛУГ)")) {
                str = cutText(str, "ВЫПОЛНЕННЫХРАБОТ(ОКАЗАННЫХУСЛУГ)");
            }
            if (str.toUpperCase().contains("(ОКАЗАННЫХУСЛУГ)")) {
                str = cutText(str, "(ОКАЗАННЫХУСЛУГ)");
            }
            if (str.toUpperCase().contains("ОКАЗАННЫХУСЛУГ")) {
                str = cutText(str, "ОКАЗАННЫХУСЛУГ");
            }
            if (str.toUpperCase().contains("ВЫПОЛНЕННЫХРАБОТ")) {
                str = cutText(str, "ВЫПОЛНЕННЫХРАБОТ");
            }
            if (str.toUpperCase().contains("ПРИЕМАИСДАЧИУСЛУГ")) {
                str = cutText(str, "ПРИЕМАИСДАЧИУСЛУГ");
            }
            if (str.toUpperCase().contains("СДАЧИ-ПРИЕМКИ")) {
                str = cutText(str, "СДАЧИ-ПРИЕМКИ");
            }
            if (str.toUpperCase().contains("ПРИЕМКИ-СДАЧИ")) {
                str = deleteTextToEnd(str, "ПРИЕМКИ-СДАЧИ");
            }
            if (str.toUpperCase().contains("ПОТРЕБЛЕНИЯВПОЛНОМО")) {
                str = deleteTextToEnd(str, "ПОТРЕБЛЕНИЯВПОЛНОМО");
            }
            if (str.toUpperCase().contains("ИНАЗВАНИЕОРГАНИЗАЦИИ")) {
                str = deleteTextToEnd(str, "ИНАЗВАНИЕОРГАНИЗАЦИИ");
            }
            if (str.toUpperCase().contains("НАОПЛАТУ")) {
                str = cutText(str, "НАОПЛАТУ");
            }
            if (str.toUpperCase().contains("АКТNS")) {
                str = str.substring(0, str.toUpperCase().indexOf("АКТNS")) + "Акт_" + str.substring(str.toUpperCase().indexOf("АКТNS") + 5, str.length());
            }
            if (str.substring(str.length() - 1, str.length()).contains("_")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.substring(str.length() - 1, str.length()).contains("_")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.substring(str.length() - 1, str.length()).contains("_")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.length() > 60) {
                str = str.substring(0, 60);
            }
        }
        return str;
    } //Замена строк, редактирование текста

    String deleteSymbols(String text, String deleteText) {
        String returnText = text;
        if (text != null) {
            if (text.contains(deleteText)) {
                returnText = text.replace(deleteText, "");
            }
        }
        return returnText;
    } //Удаление указанных символов

    String deleteBadSymbols(String str) {
        if (str != null) {
            for (int j = 0; j < badSymbols.size(); j++) {
                str = deleteSymbols(str, badSymbols.get(j));
//                str = trimText(str, "по договору ").replace(" ", "");
            }
        } else {
            System.out.println(str + " строка пустая.");
        }
        return str;
    } //Удаление недопустимых символов при переименовании файла

    WritableSheet createXLSTemplate(WritableSheet excelSheet, ArrayList<String> fileList, String department) {
        try {
            Date date = Date.getInstance();
            WritableFont cellFontHead = new WritableFont(WritableFont.createFont("CALIBRI"), 14);
            cellFontHead.setBoldStyle(WritableFont.BOLD);
            WritableCellFormat cellFormatHead = new WritableCellFormat(cellFontHead);
            cellFormatHead.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            cellFormatHead.setAlignment(jxl.format.Alignment.CENTRE);
            cellFormatHead.setWrap(true);
            Label label00 = new Label(0, 0, "Реестр первичных документов \n ЦФО УОП/" + department + " от " + date.getDateForExcelHeading(), cellFormatHead);
            WritableFont cellFont = new WritableFont(WritableFont.createFont("CALIBRI"), 11);
            WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
            cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
            cellFormat.setWrap(true);
            cellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
            Label label02 = new Label(0, 2, "№ п/п", cellFormat);
            Label label12 = new Label(1, 2, "Наименование организации-контагента", cellFormat);
            Label label22 = new Label(2, 2, "№ договор, дата", cellFormat);
            Label label32 = new Label(3, 2, "Вид документа", cellFormat);
            Label label42 = new Label(4, 2, "№ документа", cellFormat);
            Label label52 = new Label(5, 2, "Дата", cellFormat);
            Label label62 = new Label(6, 2, "Период", cellFormat);
            Label label72 = new Label(7, 2, "Оригинал", cellFormat);
            Label label82 = new Label(8, 2, "Сумма", cellFormat);
            Label label92 = new Label(9, 2, "Подпись", cellFormat);
            excelSheet.addCell(label00);
            excelSheet.setRowView(0, 820);
            excelSheet.setColumnView(0, 9);
            excelSheet.setColumnView(1, 20);
            excelSheet.setColumnView(2, 19);
            excelSheet.setColumnView(3, 19);
            excelSheet.setColumnView(4, 23);
            excelSheet.setColumnView(5, 12);
            excelSheet.setColumnView(6, 15);
            excelSheet.setColumnView(7, 11);
            excelSheet.setColumnView(8, 16);
            excelSheet.setColumnView(9, 17);
            excelSheet.mergeCells(0, 0, 9, 0);
            excelSheet.addCell(label02);
            excelSheet.addCell(label12);
            excelSheet.addCell(label22);
            excelSheet.addCell(label32);
            excelSheet.addCell(label42);
            excelSheet.addCell(label52);
            excelSheet.addCell(label62);
            excelSheet.addCell(label72);
            excelSheet.addCell(label82);
            excelSheet.addCell(label92);
            for (int i = 3; i < fileList.size() + 3; i++) { //создание рамки
                for (int j = 0; j < 10; j++) {
                    Label label = new Label(j, i, "", cellFormat);
                    excelSheet.addCell(label);
                }
            }
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return excelSheet;
    } //Создание шаблона xls страницы для реестра

}
