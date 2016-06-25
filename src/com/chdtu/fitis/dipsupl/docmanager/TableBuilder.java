package com.chdtu.fitis.dipsupl.docmanager;

import com.chdtu.fitis.dipsupl.grademodel.CourseGrade;
import com.chdtu.fitis.dipsupl.grademodel.GradeSummary;
import com.chdtu.fitis.dipsupl.grademodel.GroupSummary;
import com.chdtu.fitis.dipsupl.grademodel.StudentSummary;
import org.docx4j.Docx4jProperties;
import org.docx4j.UnitsOfMeasurement;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Properties;


public class TableBuilder {
    private static WordprocessingMLPackage wordMLPackage;
    private static ObjectFactory factory;
    private static org.docx4j.wml.P prop;
    private static int currentNumber;
    private static int totalHours;
    private static double totalCreditsECTS;
    private static int availableGradesCount;
    private static int totalPoints;
    private static int pageWidth = 30000;
    private static int pageHeight = 16838;
    private static String[] rowSignatures = {"Номер", "Назва дисципліни", "Період", "Кредити ЄКТС", "Години", "Бали", "За національною шкалою", "Рейтинг ЄКТС"};


    private static void addTableCell(Tr tableRow, String content) {
        Tc tableCell = factory.createTc();
        tableCell.getContent().add(
                wordMLPackage.getMainDocumentPart().createParagraphOfText(content));
        tableRow.getContent().add(tableCell);
    }

    private static void addSpecialTableCell(Tr tableRow, String firstLine, String secondLine) {
        Tc tableCell = factory.createTc();
        setCellWeight(tableCell, 7000);
        tableCell.getContent().add(
                wordMLPackage.getMainDocumentPart().createParagraphOfText(firstLine));
        tableCell.getContent().add(
                wordMLPackage.getMainDocumentPart().createParagraphOfText(secondLine));
        tableRow.getContent().add(tableCell);

    }

    private static void addTableCell(Tr tableRow, String content, int width) {
        Tc tableCell = factory.createTc();
        setCellWeight(tableCell, width);
        tableCell.getContent().add(
                wordMLPackage.getMainDocumentPart().createParagraphOfText(content));
        tableRow.getContent().add(tableCell);
    }

    private static void addTableCellWithVerticalText(Tr tableRow, String content) {
        Tc tableCell = factory.createTc();
        TcPr tableCellProperties = new TcPr();
        tableCell.setTcPr(tableCellProperties);
        tableCell.getContent().add(wordMLPackage.getMainDocumentPart().createParagraphOfText(content));
        tableRow.getContent().add(tableCell);
        TextDirection tD = new TextDirection();
        tD.setVal("btLr");
        tableCell.getTcPr().setTextDirection(tD);

    }

    private static void addTableRowHeightSetting(Tr tr, int pxFont) {
        int twip = UnitsOfMeasurement.pxToTwip(pxFont);
        BigInteger rowHeight = BigInteger.valueOf(twip);
        TrPr trPr = new TrPr();
        CTHeight ctHeight = new CTHeight();
        ctHeight.setHRule(STHeightRule.EXACT);
        ctHeight.setVal(rowHeight);
        JAXBElement<CTHeight> jaxbElement = factory.createCTTrPrBaseTrHeight(ctHeight);
        trPr.getCnfStyleOrDivIdOrGridBefore().add(jaxbElement);
        tr.setTrPr(trPr);
    }


    public static void preparePackage() {
        try {
            wordMLPackage = WordprocessingMLPackage.createPackage();
            factory = Context.getWmlObjectFactory();
            SectPr sectionLandscape = factory.createSectPr();
            SectPr.PgSz landscape = new SectPr.PgSz();
            landscape.setOrient(STPageOrientation.LANDSCAPE);
            landscape.setH(BigInteger.valueOf(pageHeight));
            landscape.setW(BigInteger.valueOf(pageWidth));
            sectionLandscape.setPgSz(landscape);
            prop = factory.createP();
            PPr createPPr = factory.createPPr();
            createPPr.setSectPr(sectionLandscape);
            prop.setPPr(createPPr);
            Properties properties = Docx4jProperties.getProperties();
            properties.setProperty("docx4j.PageOrientationLandscape", "true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buildDocument(StudentSummary studentGradeSummaries, String filePath) {
        try {
            wordMLPackage = WordprocessingMLPackage.createPackage();
            factory = Context.getWmlObjectFactory();
            SectPr sectionLandscape = factory.createSectPr();
            SectPr.PgSz landscape = new SectPr.PgSz();
            landscape.setOrient(STPageOrientation.LANDSCAPE);
            landscape.setH(BigInteger.valueOf(11906));
            landscape.setW(BigInteger.valueOf(20000));
            sectionLandscape.setPgSz(landscape);
            org.docx4j.wml.P p = factory.createP();
            PPr createPPr = factory.createPPr();
            createPPr.setSectPr(sectionLandscape);
            p.setPPr(createPPr);
            Properties properties = Docx4jProperties.getProperties();
            properties.setProperty("docx4j.PageOrientationLandscape", "true");
            totalHours = 0;
            setCurrentNumber(1);
            wordMLPackage.getMainDocumentPart().addParagraphOfText(studentGradeSummaries.getStudentName() + " " + studentGradeSummaries.getGroupName());
            Tbl table = factory.createTbl();
            addBorders(table);
            addSignaturesToRows(table);
            for (GradeSummary grade : studentGradeSummaries.getGradeSummaries()) {
                addRowsToTable(grade, table);
                totalHours += grade.getTotalHours();
                totalPoints += grade.calculateTotalPoints();
                availableGradesCount += grade.calculateAvailableGradesCount();
            }
            totalCreditsECTS = totalHours / 36.0;
            addSummation(table);
            wordMLPackage.getMainDocumentPart().addObject(table);
            wordMLPackage.getMainDocumentPart().addObject(p);
            wordMLPackage.save(new java.io.File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buildDocument(GroupSummary groupSummary, String filePath) {
        try {
            preparePackage();
            wordMLPackage.getMainDocumentPart().addParagraphOfText(groupSummary.getGroupName());
            Tbl table = factory.createTbl();
            addBorders(table);
            addVerticalSignaturesToRows(table, groupSummary);
            wordMLPackage.getMainDocumentPart().addObject(table);
            wordMLPackage.getMainDocumentPart().addObject(prop);
            wordMLPackage.save(new java.io.File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void addBorders(Tbl table) {
        table.setTblPr(new TblPr());
        CTBorder border = new CTBorder();
        border.setColor("auto");
        border.setSz(new BigInteger("4"));
        border.setSpace(new BigInteger("0"));
        border.setVal(STBorder.SINGLE);
        TblBorders borders = new TblBorders();
        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setTop(border);
        borders.setInsideH(border);
        borders.setInsideV(border);
        table.getTblPr().setTblBorders(borders);
    }


    public static void addRowsToTable(GradeSummary gradeSummary, Tbl table) {
        Tr tableRow = factory.createTr();
        String title = gradeSummary.getTitle();
        if (!title.equals("")) {
            addTableCell(tableRow, "");
            addTableCell(tableRow, title);
            for (int i = 0; i < 6; i++)
                addTableCell(tableRow, "");
            table.getContent().add(tableRow);
        }
        for (int i = 0; i < gradeSummary.getCourseGrades().size(); i++) {
            CourseGrade courseGrade = gradeSummary.getCourseGrades().get(i);
            tableRow = factory.createTr();
            addTableCell(tableRow, currentNumber + "");
            addTableCell(tableRow, courseGrade.getSubjectName());
            addTableCell(tableRow, courseGrade.getStudyingPeriod(), 1300);
            if (courseGrade.getCreditsECTS() == 0) addTableCell(tableRow, "");
            else addTableCell(tableRow, courseGrade.getValueInString(courseGrade.getCreditsECTS()));
            if (courseGrade.getHours() == 0) addTableCell(tableRow, "");
            else addTableCell(tableRow, courseGrade.getHours() + "");
            addTableCell(tableRow, courseGrade.getPoints() + "");
            addTableCell(tableRow, courseGrade.getGradeNationalScale() + "");
            addTableCell(tableRow, courseGrade.getGradeECTS() + "");
            table.getContent().add(tableRow);
            setCurrentNumber(getCurrentNumber() + 1);
        }
    }

    public static void addSignaturesToRows(Tbl table) {
        Tr tableRow = factory.createTr();
        for (int i = 0; i < rowSignatures.length; i++) {
            addTableCell(tableRow, rowSignatures[i]);
        }
        table.getContent().add(tableRow);

    }

    public static void addVerticalSignaturesToRows(Tbl table, GroupSummary groupSummary) {
        Tr tableRow = factory.createTr();
        addTableCell(tableRow, "");
        addTableCell(tableRow, "");
        for (StudentSummary studentSummary : groupSummary.getStudentGradesSummaries()) {
            addTableCellWithVerticalText(tableRow, studentSummary.getStudent().getInitials());
        }
        table.getContent().add(tableRow);
        addTableRowHeightSetting(tableRow, 200);
        ArrayList<Tr> tableRows = generatePointRows(groupSummary);
        table.getContent().addAll(tableRows);
    }

    public static ArrayList<Tr> generatePointRows(GroupSummary groupSummary) {
        ArrayList<Tr> tableRows = new ArrayList();
        addSummationForGroup(tableRows);
        int rowNumber = 1;
        for (GradeSummary gradeSummary : groupSummary.getStudentGradesSummaries().get(0).getGradeSummaries()) {
            {
                tableRows.add(factory.createTr());
                addTableCell(tableRows.get(tableRows.size() - 1), "");
                addTableCell(tableRows.get(tableRows.size() - 1), gradeSummary.getTitle());
            }
            for (CourseGrade courseGrade : gradeSummary.getCourseGrades()) {
                tableRows.add(factory.createTr());
                addTableCell(tableRows.get(tableRows.size() - 1), rowNumber + "", 30);
                addSpecialTableCell(tableRows.get(tableRows.size() - 1), courseGrade.getSubjectName() + courseGrade.getMarkTypeInUkrainian() + courseGrade.getMultipleSemesterInUkrainian(), courseGrade.getStudyingPeriod() +
                        "   " + courseGrade.getHours() + "   " + CourseGrade.getValueInString(courseGrade.getCreditsECTS()));
                rowNumber++;
            }
        }

        int k = 0;
        for (StudentSummary studentSummary : groupSummary.getStudentGradesSummaries()) {
            k = 0;
            addTableCell(tableRows.get(k++), Math.round(studentSummary.getAveragePoint()) + "");
            addTableCell(tableRows.get(k++), Math.round(studentSummary.getAverageScale() * 100) / (double) 100 + "");
            addTableCell(tableRows.get(k++), studentSummary.getTotalHours() + "");
            addTableCell(tableRows.get(k++), studentSummary.getTotalCreditsECTS() + "");
            System.out.println(groupSummary.getGroupName() + " " + studentSummary.getStudentName());
            for (GradeSummary gradeSummary : studentSummary.getGradeSummaries()) {
                k++;
                for (CourseGrade courseGrade : gradeSummary.getCourseGrades()) {
                    if (courseGrade.getGradeScale() != null)
                        addTableCell(tableRows.get(k),
                                Math.round(courseGrade.getPoints()) + "");
                    else addTableCell(tableRows.get(k), "");
                    k++;
                }
            }
        }

        return tableRows;
    }

    public static void addSummation(Tbl table) {
        Tr tableRow = factory.createTr();
        addTableCell(tableRow, "");
        addTableCell(tableRow, "Всього кредитів");
        addTableCell(tableRow, "");
        addTableCell(tableRow, CourseGrade.getValueInString(totalCreditsECTS));
        addTableCell(tableRow, totalHours + "");
        addTableCell(tableRow, "");
        addTableCell(tableRow, "");
        addTableCell(tableRow, "");
        table.getContent().add(tableRow);

        tableRow = factory.createTr();
        addTableCell(tableRow, "");
        addTableCell(tableRow, "Підсумкова оцінка (середній бал додатка)");
        addTableCell(tableRow, "");
        addTableCell(tableRow, Math.round(totalPoints / (double) availableGradesCount) + "");
        for (int i = 0; i < 4; i++) {
            addTableCell(tableRow, "");
        }
        table.getContent().add(tableRow);
    }

    public static void addSummationForGroup(ArrayList<Tr> tableRows) {
//        tableRows.add(factory.createTr());
//        addTableCell(tableRows.get(tableRows.size() - 1), "");
//        addTableCell(tableRows.get(tableRows.size() - 1), "Підсумкова оцінка");
        tableRows.add(factory.createTr());
        addTableCell(tableRows.get(tableRows.size() - 1), "");
        addTableCell(tableRows.get(tableRows.size() - 1), "Підсумкова оцінка");
        tableRows.add(factory.createTr());
        addTableCell(tableRows.get(tableRows.size() - 1), "");
        addTableCell(tableRows.get(tableRows.size() - 1), "Середній бал");
        tableRows.add(factory.createTr());
        addTableCell(tableRows.get(tableRows.size() - 1), "");
        addTableCell(tableRows.get(tableRows.size() - 1), "Кількість годин");
        tableRows.add(factory.createTr());
        addTableCell(tableRows.get(tableRows.size() - 1), "");
        addTableCell(tableRows.get(tableRows.size() - 1), "Кількість кредитів");
    }

    private static void setCellWeight(Tc tableCell, int weight) {
        TcPr tableCellProperties = new TcPr();
        TblWidth tableWidth = new TblWidth();
        tableWidth.setW(BigInteger.valueOf(weight));
        tableWidth.setType("dxa");
        tableCellProperties.setTcW(tableWidth);
        tableCell.setTcPr(tableCellProperties);
    }

    private static void setRowHeight(Tr row) {
        TrPr trPr = new TrPr();
        CTHeight ctHeight = new CTHeight();
        ctHeight.setHRule(STHeightRule.EXACT);
        JAXBElement<CTHeight> jaxbElement = factory.createCTTrPrBaseTrHeight(ctHeight);
        trPr.getCnfStyleOrDivIdOrGridBefore().add(jaxbElement);
        row.setTrPr(trPr);


    }

    public static int getCurrentNumber() {
        return currentNumber;
    }

    public static void setCurrentNumber(int currentNumber) {
        TableBuilder.currentNumber = currentNumber;
    }

    public static int calculateTextLength(String text, int size) {
        Font font = new Font("Calibri", Font.PLAIN, size);
        AffineTransform affinetransform = font.getTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        int textwidth = (int) (font.getStringBounds(text, frc).getWidth());
        return textwidth;
    }

}
