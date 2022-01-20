import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date { //Класс для распознавания даты в сканированном документе и отправки её в готовом виде в реестр
    TextHandler textHandler = new TextHandler();

    private static Date instance;
    private Date() {
    }
    public static Date getInstance() { // #3
        if (instance == null) {        //если объект еще не создан
            instance = new Date();    //создать новый объект
        }
        return instance;        // вернуть ранее созданный объект
    }

    String getMonthYear(String date) {
        ArrayList<String> months = new ArrayList<>(Arrays.asList("", "январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"));
        Integer month = Integer.valueOf(date.substring(3, 5));
        String year = date.substring(6, 10);
        System.out.println(months.get(month) + " " + year);
        return months.get(month) + " " + year;
    } //возвращает письменный формат даты из численного, например, 31.01.2020 переведет "январь 2020"

    String getMonth(Integer month) {
        ArrayList<String> months = new ArrayList<>(Arrays.asList("", "январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"));
        System.out.println(months.get(month));
        return months.get(month);
    } //Возвращает месяц в письменном виде из числа, например, 5 вернет май

    String getDateForExcelFileName() {
        String date = new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime());
        return date;
    } //Возвращает текущую дату в формате ддммгггг для имени файла

    String getDateForExcelHeading() {
        String date = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        return date;
    } //Возвращает текущую дату для заголовка внутри Excel документа

    String findDate(ArrayList<String> text) {
        Integer year = Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime())) - 5;
        ArrayList<String> dates19 = new ArrayList<>(Arrays.asList("от 31 января 2019", "от 29 февраля 2019", "от 31 марта 2019", "от 30 апреля 2019", "от 31 мая 2019", "от 30 июня 2019", "от 31 июля 2019", "от 31 августа 2019", "от 30 сентября 2019", "от 31 октября 2019", "от 30 ноября 2019", "от 31 декабря 2019"));
        ArrayList<String> dates20 = new ArrayList<>(Arrays.asList("от 31 января 2020", "от 29 февраля 2020", "от 31 марта 2020", "от 30 апреля 2020", "от 31 мая 2020", "от 30 июня 2020", "от 31 июля 2020", "от 31 августа 2020", "от 30 сентября 2020", "от 31 октября 2020", "от 30 ноября 2020", "от 31 декабря 2020"));
        ArrayList<String> dates21 = new ArrayList<>(Arrays.asList("от 31 января 2021", "от 29 февраля 2021", "от 31 марта 2021", "от 30 апреля 2021", "от 31 мая 2021", "от 30 июня 2021", "от 31 июля 2021", "от 31 августа 2021", "от 30 сентября 2021", "от 31 октября 2021", "от 30 ноября 2021", "от 31 декабря 2021"));
        ArrayList<String> dates22 = new ArrayList<>(Arrays.asList("от 31 января 2022", "от 28 февраля 2022", "от 31 марта 2022", "от 30 апреля 2022", "от 31 мая 2022", "от 30 июня 2022", "от 31 июля 2022", "от 31 августа 2022", "от 30 сентября 2022", "от 31 октября 2022", "от 30 ноября 2022", "от 31 декабря 2022"));
        String date;
        String result = null;
        Calendar cal = new GregorianCalendar(2015, 0, 0);
        for (int i = 2015; i < 2026; i++) {
            for (int j = 0; j < 12; j++) {
                cal.set(i, j, 5);
                cal.set(cal.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                date = new SimpleDateFormat("dd.MM.yyyy").format(cal.getTime());
                if (textHandler.findString(text, date)) {
                    result = date;
                    break;
                }
            }
            if (result != null) break;
        }
        if (result == null) {
            if (textHandler.findString(text, "от 29 февраля 2019")) {
                result = "29.02.2019";
            } else {
                if (textHandler.findString(text, "от 29 февраля 2020")) {
                    result = "29.02.2020";
                } else {
                    if (textHandler.findString(text, "от 29 февраля 2021")) {
                        result = "29.02.2021";
                    } else {
                        if (textHandler.findString(text, "от 29 февраля 2022")) {
                            result = "28.02.2022";
                        }
                    }
                }
            }
        }
        if (result == null) {
            for (int i = 0; i < dates19.size(); i++) {
                if (textHandler.findString(text, dates19.get(i))) {
                    result = dates19.get(i).substring(3, 5) + "." + i + ".2019";
                }
            }
        }
        if (result == null) {
            for (int i = 0; i < dates20.size(); i++) {
                if (textHandler.findString(text, dates20.get(i))) {
                    result = dates20.get(i).substring(3, 5) + "." + i + ".2020";
                }
            }
        }
        if (result == null) {
            for (int i = 0; i < dates21.size(); i++) {
                if (textHandler.findString(text, dates21.get(i))) {
                    result = dates21.get(i).substring(3, 5) + "." + i + ".2021";
                }
            }
        }
        if (result == null) {
            for (int i = 0; i < dates22.size(); i++) {
                if (textHandler.findString(text, dates22.get(i))) {
                    result = dates22.get(i).substring(3, 5) + "." + i + ".2022";
                }
            }
        }
        if (result != null) {
            if (result.length() == 9) {
                String s = result;
                result = s.substring(0, 3) + "0" + s.substring(3, 9);
            }
        }
        return result;
    }  //ищет в тексте варианты даты, при нахождении возвращает нужный враиант
}
