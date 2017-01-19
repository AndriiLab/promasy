package model;

import gui.Labels;
import gui.Utils;
import model.dao.Database;
import model.enums.Role;
import model.models.*;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Default values for Institute of biochemistry
 */
public class DefaultValues {

    public static void setAmountUnits() throws SQLException {
        String[] amUnits = {
                "кг",
                "г",
                "мкг",
                "л",
                "мл",
                "уп.",
                "шт."
        };
        for (String amUnit : amUnits) {
            AmountUnitsModel model = new AmountUnitsModel(amUnit);
            model.setCreated();
            Database.AMOUNTUNITS.createOrUpdate(model);
        }
    }

    /* У складі Інституту є 10 наукових відділів:
        - відділ регуляції обміну речовин – в.о. зав. відділу  к.б.н. С.Г. Шандренко.
        - відділ структури і функції білка – зав. відділу чл.-кор. НАН України Е.В. Луговськой;
        - відділ нейрохімії  – зав. відділу д.б.н. Т.О. Борисова;
        - відділ біохімії вітамінів і коензимів –  зав.відділу  д.б.н. М.М.Великий;
        - відділ біохімії м’язів  – зав. відділу  акад. НАН України С.О. Костерін;
        - відділ біохімії ліпідів – зав. відділу чл.-кор. НАН України Н.М. Гула;
        - відділ хімії і біохімії ферментів –  зав. відділу д.б.н. Т.В. Гриненко;
        - відділ молекулярної імунології – зав. відділу акад. НАН України С.В. Комісаренко;
        - відділ молекулярної біології – зав. відділу д.б.н. О.Г. Мінченко;
        - відділ науково-технічної інформації – в.о.зав. відділу к.б.н. В.М. Данилова.

        ++
        - Адміністративно-господарський відділ
        - Бухгалтерія
        - Відділ постачання
        - Дирекція
        - Відділ техніки безпеки
        - Експлуатаційно-технічний відділ

    У складі Інституту функціонують також 4  наукових лабораторії.
        - лабораторія сигнальних механізмів клітини –  керівник –  пров.н.співр., д.б.н. Л.Б.Дробот,  що має самостійну тематику.
     При відділі молекулярної імунології інституту функціонують три лабораторії:
        - лабораторія імунобіології, – керівник  – в.о. зав. лабораторії д.б.н. Д.В. Колибо;
        - лабораторія імунології клітинних рецепторів, – керівник –  в.о. зав. лабораторії  д.б.н.  М.В. Скок
        - лабораторія  нанобіотехнологій – керівник –  в.о. зав. лабораторії д.б.н. О.П.Демченко, що має самостійну тематику.
    */

    public static void setInstituteStructure() throws SQLException {
        //institute
        InstituteModel instituteModel = new InstituteModel(
                "Інститут біохімії ім. О.В. Палладіна Національної академії наук України"
        );
        instituteModel.setCreated();
        Database.INSTITUTES.createOrUpdate(instituteModel);

        //departments
        String[] departments = {
                "Відділ біохімії вітамінів і коензимів", //0
                "Відділ біохімії ліпідів",              //1
                "Відділ біохімії м\'язів",              //2
                "Відділ молекулярної біології",         //3
                "Відділ молекулярної імунології",       //4
                "Відділ науково-технічної інформації",  //5
                "Відділ нейрохімії",                    //6
                "Відділ регуляції обміну речовин",      //7
                "Відділ структури і функції білка",     //8
                "Відділ хімії і біохімії ферментів",    //9
                "Лабораторія сигнальних механізмів клітини", //10
                "Адміністративно-господарський відділ"  //11
        };

        //laboratories
        String[] laboratories = {
                "Лабораторія імунобіології",                    //1 (0 = відсутній)
                "Лабораторія імунології клітинних рецепторів",  //2
                "Лабораторія нанобіотехнологій",                //3
        };

        for (String department : departments) {
            DepartmentModel departmentModel = new DepartmentModel(department);
            departmentModel.setCreated();
            SubdepartmentModel subdepartmentModel = new SubdepartmentModel("<" + Labels.getProperty("null") + ">");
            subdepartmentModel.setCreated();
            departmentModel.addSubdepartment(subdepartmentModel);
            Database.DEPARTMENTS.createOrUpdate(departmentModel);
            if (department.equals("Відділ молекулярної імунології")) {
                for (String subdepartment : laboratories) {
                    subdepartmentModel = new SubdepartmentModel(subdepartment);
                    subdepartmentModel.setCreated();
                    departmentModel.addSubdepartment(subdepartmentModel);
                    Database.DEPARTMENTS.createOrUpdate(departmentModel);
                }
            }
            instituteModel.addDepartment(departmentModel);
        }

        //employees
        long salt = Utils.makeSalt();
        EmployeeModel komisarenko = new EmployeeModel(
                "Сергій",
                "Васильович",
                "Комісаренко",
                "svk@biochem.kiev.ua",
                "31-95",
                "234-59-74",
                instituteModel.getDepartments().get(11).getSubdepartments().get(0),
                Role.DIRECTOR,
                "svk",
                Utils.makePass("bTbs3BUD".toCharArray(), salt),
                salt
        );
        komisarenko.setCreated();
        instituteModel.getDepartments().get(11).getSubdepartments().get(0).addEmployee(komisarenko);

        salt = Utils.makeSalt();
        EmployeeModel kosterin = new EmployeeModel(
                "Сергій",
                "Олексійович",
                "Костерін",
                "kinet@biochem.kiev.ua",
                "31-12",
                "234-16-53",
                instituteModel.getDepartments().get(11).getSubdepartments().get(0),
                Role.DEPUTY_DIRECTOR,
                "kinet",
                Utils.makePass("hEm36YPq".toCharArray(), salt),
                salt
        );
        kosterin.setCreated();
        instituteModel.getDepartments().get(11).getSubdepartments().get(0).addEmployee(kosterin);

        salt = Utils.makeSalt();
        EmployeeModel kolibo = new EmployeeModel(
                "Денис",
                "Володимирович",
                "Колибо",
                "kolibo@biochem.kiev.ua",
                "31-94",
                "234-81-33",
                instituteModel.getDepartments().get(11).getSubdepartments().get(0),
                Role.DEPUTY_DIRECTOR,
                "kolibo",
                Utils.makePass("9Urq6LAa".toCharArray(), salt),
                salt
        );
        kolibo.setCreated();
        instituteModel.getDepartments().get(11).getSubdepartments().get(0).addEmployee(kolibo);

        salt = Utils.makeSalt();
        EmployeeModel secretar = new EmployeeModel(
                "Людмила",
                "Євгенівна",
                "Науменко",
                "secretar@biochem.kiev.ua",
                "31-95",
                "234-59-74",
                instituteModel.getDepartments().get(11).getSubdepartments().get(0),
                Role.SECRETARY_OF_TENDER_COMMITTEE,
                "secretar",
                Utils.makePass("5q2MDmSM".toCharArray(), salt),
                salt
        );
        secretar.setCreated();
        instituteModel.getDepartments().get(11).getSubdepartments().get(0).addEmployee(secretar);

        salt = Utils.makeSalt();
        EmployeeModel accountant = new EmployeeModel(
                "Наталія",
                "Дмитрівна",
                "Пархоменко",
                "siren@biochem.kiev.ua",
                "31-07",
                "235-40-18",
                instituteModel.getDepartments().get(11).getSubdepartments().get(0),
                Role.ACCOUNTANT,
                "siren",
                Utils.makePass("2m2uhGvf".toCharArray(), salt),
                salt
        );
        accountant.setCreated();
        instituteModel.getDepartments().get(11).getSubdepartments().get(0).addEmployee(accountant);

        salt = Utils.makeSalt();
        EmployeeModel economist = new EmployeeModel(
                "Лілія",
                "Віталіївна",
                "Бернацька",
                "bernatska@biochem.kiev.ua",
                "31-10",
                "278-64-03",
                instituteModel.getDepartments().get(11).getSubdepartments().get(0),
                Role.ECONOMIST,
                "bernatska",
                Utils.makePass("QkU59UsR".toCharArray(), salt),
                salt
        );
        economist.setCreated();
        instituteModel.getDepartments().get(11).getSubdepartments().get(0).addEmployee(economist);

        salt = Utils.makeSalt();
        EmployeeModel borisova = new EmployeeModel(
                "Тетяна",
                "Олександрівна",
                "Борисова",
                "tborisov@biochem.kiev.ua",
                "31-85",
                "234-32-54",
                instituteModel.getDepartments().get(6).getSubdepartments().get(0),
                Role.HEAD_OF_DEPARTMENT,
                "tborisov",
                Utils.makePass("7cdKe8PH".toCharArray(), salt),
                salt
        );
        borisova.setCreated();
        instituteModel.getDepartments().get(6).getSubdepartments().get(0).addEmployee(borisova);

        salt = Utils.makeSalt();
        EmployeeModel kosterinDep = new EmployeeModel(
                "Сергій",
                "Олексійович",
                "Костерін",
                "kinet@biochem.kiev.ua",
                "31-12",
                "234-16-53",
                instituteModel.getDepartments().get(2).getSubdepartments().get(0),
                Role.HEAD_OF_DEPARTMENT,
                "kinetD",
                Utils.makePass("hEm36YPq".toCharArray(), salt),
                salt
        );
        kosterinDep.setCreated();
        instituteModel.getDepartments().get(2).getSubdepartments().get(0).addEmployee(kosterinDep);

        salt = Utils.makeSalt();
        EmployeeModel lugovskoy = new EmployeeModel(
                "Едуард",
                "Віталійович",
                "Луговськой",
                "lougovskoy@yahoo.com",
                "31-29",
                "234-23-35",
                instituteModel.getDepartments().get(8).getSubdepartments().get(0),
                Role.HEAD_OF_DEPARTMENT,
                "lougovskoy",
                Utils.makePass("z7VWqtcr".toCharArray(), salt),
                salt
        );
        lugovskoy.setCreated();
        instituteModel.getDepartments().get(8).getSubdepartments().get(0).addEmployee(lugovskoy);

        salt = Utils.makeSalt();
        EmployeeModel velikiy = new EmployeeModel(
                "Микола",
                "Миколайович",
                "Великий",
                "veliky@biochem.kiev.ua",
                "31-38",
                "234-71-78",
                instituteModel.getDepartments().get(0).getSubdepartments().get(0),
                Role.HEAD_OF_DEPARTMENT,
                "veliky",
                Utils.makePass("ZPT8vQ5J".toCharArray(), salt),
                salt
        );
        velikiy.setCreated();
        instituteModel.getDepartments().get(0).getSubdepartments().get(0).addEmployee(velikiy);

        salt = Utils.makeSalt();
        EmployeeModel grinenko = new EmployeeModel(
                "Тетяна",
                "Вікторівна",
                "Великий",
                "grinenko@biochem.kiev.ua",
                "31-52",
                "234-90-56",
                instituteModel.getDepartments().get(9).getSubdepartments().get(0),
                Role.HEAD_OF_DEPARTMENT,
                "grinenko",
                Utils.makePass("RyX8mAfV".toCharArray(), salt),
                salt
        );
        grinenko.setCreated();
        instituteModel.getDepartments().get(9).getSubdepartments().get(0).addEmployee(grinenko);

        salt = Utils.makeSalt();
        EmployeeModel komisarenkoDep = new EmployeeModel(
                "Сергій",
                "Васильович",
                "Комісаренко",
                "svk@biochem.kiev.ua",
                "32-17",
                "234-59-74",
                instituteModel.getDepartments().get(4).getSubdepartments().get(0),
                Role.HEAD_OF_DEPARTMENT,
                "svkD",
                Utils.makePass("bTbs3BUD".toCharArray(), salt),
                salt
        );
        komisarenkoDep.setCreated();
        instituteModel.getDepartments().get(4).getSubdepartments().get(0).addEmployee(komisarenkoDep);

        salt = Utils.makeSalt();
        EmployeeModel skok = new EmployeeModel(
                "Марина",
                "Володимирівна",
                "Скок",
                "skok@biochem.kiev.ua",
                "31-13",
                "234-33-54",
                instituteModel.getDepartments().get(4).getSubdepartments().get(2),
                Role.HEAD_OF_DEPARTMENT,
                "skok",
                Utils.makePass("bTbs3BUD".toCharArray(), salt),
                salt
        );
        skok.setCreated();
        instituteModel.getDepartments().get(4).getSubdepartments().get(2).addEmployee(skok);

        salt = Utils.makeSalt();
        EmployeeModel koliboDep = new EmployeeModel(
                "Денис",
                "Володимирович",
                "Колибо",
                "kolibo@biochem.kiev.ua",
                "31-23",
                "234-33-54",
                instituteModel.getDepartments().get(4).getSubdepartments().get(1),
                Role.HEAD_OF_DEPARTMENT,
                "koliboD",
                Utils.makePass("9Urq6LAa".toCharArray(), salt),
                salt
        );
        koliboDep.setCreated();
        instituteModel.getDepartments().get(4).getSubdepartments().get(1).addEmployee(koliboDep);

        salt = Utils.makeSalt();
        EmployeeModel demchenko = new EmployeeModel(
                "Олександр",
                "Петрович",
                "Демченко",
                "alexdem@bk.ru",
                "31-40",
                "234-11-06",
                instituteModel.getDepartments().get(4).getSubdepartments().get(3),
                Role.HEAD_OF_DEPARTMENT,
                "alexdem",
                Utils.makePass("6jaYpZ9y".toCharArray(), salt),
                salt
        );
        demchenko.setCreated();
        instituteModel.getDepartments().get(4).getSubdepartments().get(3).addEmployee(demchenko);

        salt = Utils.makeSalt();
        EmployeeModel shandrenko = new EmployeeModel(
                "Сергій",
                "Григорович",
                "Шандренко",
                "sergshandr@gmail.com",
                "31-70",
                "234-43-45",
                instituteModel.getDepartments().get(7).getSubdepartments().get(0),
                Role.HEAD_OF_DEPARTMENT,
                "sergshandr",
                Utils.makePass("bA2RdqAG".toCharArray(), salt),
                salt
        );
        shandrenko.setCreated();
        instituteModel.getDepartments().get(7).getSubdepartments().get(0).addEmployee(shandrenko);

        salt = Utils.makeSalt();
        EmployeeModel gula = new EmployeeModel(
                "Надія",
                "Максимівна",
                "Гула",
                "ngula@biochem.kiev.ua",
                "31-46",
                "234-82-29",
                instituteModel.getDepartments().get(1).getSubdepartments().get(0),
                Role.HEAD_OF_DEPARTMENT,
                "ngula",
                Utils.makePass("e8DBQR53".toCharArray(), salt),
                salt
        );
        gula.setCreated();
        instituteModel.getDepartments().get(1).getSubdepartments().get(0).addEmployee(gula);

        salt = Utils.makeSalt();
        EmployeeModel minchenko = new EmployeeModel(
                "Олександр",
                "Григорович",
                "Мінченко",
                "ominchenko@yahoo.com",
                "31-51",
                "235-61-51",
                instituteModel.getDepartments().get(3).getSubdepartments().get(0),
                Role.HEAD_OF_DEPARTMENT,
                "ominchenko",
                Utils.makePass("7bKXwa2C".toCharArray(), salt),
                salt
        );
        minchenko.setCreated();
        instituteModel.getDepartments().get(3).getSubdepartments().get(0).addEmployee(minchenko);

        salt = Utils.makeSalt();
        EmployeeModel drobot = new EmployeeModel(
                "Людмила",
                "Борисівна",
                "Дробот",
                "drobot@biochem.kiev.ua",
                "31-88",
                "234-39-22",
                instituteModel.getDepartments().get(10).getSubdepartments().get(0),
                Role.HEAD_OF_DEPARTMENT,
                "drobot",
                Utils.makePass("mY3e8467".toCharArray(), salt),
                salt
        );
        drobot.setCreated();
        instituteModel.getDepartments().get(10).getSubdepartments().get(0).addEmployee(drobot);

        salt = Utils.makeSalt();
        EmployeeModel danilova = new EmployeeModel(
                "Валентина",
                "Михайлівна",
                "Данилова",
                "valdan@biochem.kiev.ua",
                "31-93",
                "234-13-45",
                instituteModel.getDepartments().get(5).getSubdepartments().get(0),
                Role.HEAD_OF_DEPARTMENT,
                "valdan",
                Utils.makePass("68eKqgAU".toCharArray(), salt),
                salt
        );
        danilova.setCreated();
        instituteModel.getDepartments().get(5).getSubdepartments().get(0).addEmployee(danilova);

        Database.INSTITUTES.createOrUpdate(instituteModel);
    }

    public static void setCpv() throws IOException, SQLException {
        File ods = new File("reports/cpv.ods");
        final Sheet sheet = SpreadSheet.createFromFile(ods).getSheet(0);
        int i = 1;
        while (!sheet.getCellAt("A" + (i + 1)).getTextValue().equals("")) {
            i++;
            String cpvCode = sheet.getCellAt("A" + (i)).getTextValue();
            String cpvUkr = sheet.getCellAt("B" + (i)).getTextValue();
            String cpvEng = sheet.getCellAt("C" + (i)).getTextValue();
            int cpvLevel = Integer.parseInt(sheet.getCellAt("D" + (i)).getTextValue());
            boolean terminal = Boolean.parseBoolean(sheet.getCellAt("E" + (i)).getTextValue());
            CPVModel model = new CPVModel(cpvCode, cpvUkr, cpvEng, cpvLevel, terminal);
            Database.CPV.create(model);
        }
    }

//    public static void main(String[] args) {
//        try {
//            setCpv();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(getInstituteStructure().toString());
//        for (DepartmentModel model : getInstituteStructure().getDepartments()) {
//            System.out.println("\t" + model.toString());
//            for (SubdepartmentModel subdepartmentModel1 : model.getSubdepartments()) {
//                System.out.println("\t\t" + subdepartmentModel1.toString());
//                for (EmployeeModel emp : subdepartmentModel1.getEmployees()){
//                    System.out.println("\t\t\t"+emp.getShortName());
//                }
//            }
//        }
//    }
}
