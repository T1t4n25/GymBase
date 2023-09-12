package com.t1t4n.gymbase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Arc;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class billingController implements Initializable {
    @FXML
    Label BBLabel;
    @FXML
    Label fitLabel;
    @FXML
    Label privateLabel;
    @FXML
    Arc BBArc;
    @FXML
    Arc fitArc;
    @FXML
    Arc privateArc;
    @FXML
    TableView<FinancialReport> monthlyReportTable;
    @FXML
    TableColumn<FinancialReport, String> dateCol;
    @FXML
    TableColumn<FinancialReport, Integer> incomeCol;
    @FXML
    TableColumn<FinancialReport, Double> expensesCol;
    @FXML
    TableColumn<FinancialReport, Double> profitCol;
    ResultSet resultSet1, resultSet2;
    ObservableList<FinancialReport> monthlyData;
    public billingController(){

    }

    private void setMonthlyReportTable() throws SQLException {
        resultSet1 = DBConnection.statement.executeQuery("SELECT `id`,`date`, `value` FROM `money_in_data` ORDER BY date ASC;");
        resultSet2 = DBConnection.statement2.executeQuery("SELECT `id`, `date`, `value` FROM `expenses_data` ORDER BY date ASC;");
        LocalDate date1, date2, date3;
        int incomeTotal = 0;
        int expenseTotal = 0;
        boolean firstime = true;
        monthlyData = FXCollections.observableArrayList();
        resultSet1.beforeFirst();
        resultSet2.beforeFirst();
        do {
            if (firstime) {
                resultSet1.next();
                firstime = false;
            }
            date1 = resultSet1.getDate("date").toLocalDate();
            incomeTotal += resultSet1.getInt("value");
            while (resultSet1.next()) {
                date2 = resultSet1.getDate("date").toLocalDate();
                if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth()) {
                    incomeTotal += resultSet1.getInt("value");
                }else
                    break;

            }
            while (resultSet2.next()) {
                date3 = resultSet2.getDate("date").toLocalDate();
                if (date3.getYear() == date1.getYear() && date3.getMonth() == date1.getMonth()) {
                    expenseTotal += resultSet2.getInt("value");
                    System.out.println("expensesTable Row : " + resultSet2.getInt(1) + " /  " + resultSet2.getDate("date") + " /  " + expenseTotal);
                } else {
                    resultSet2.previous();
                    break;
                }
            }
            monthlyData.add(new FinancialReport(incomeTotal,
                    expenseTotal,
                    (String.valueOf(date1.getYear()) + " / " + String.valueOf(date1.getMonthValue()))));
            incomeTotal = 0;
            expenseTotal = 0;
        } while (!resultSet1.isAfterLast());
        monthlyReportTable.setItems(monthlyData);
    }
    private void getBBMembers(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        incomeCol.setCellValueFactory(new PropertyValueFactory<>("incomeTotal"));
        expensesCol.setCellValueFactory(new PropertyValueFactory<>("expensesTotal"));
        profitCol.setCellValueFactory(new PropertyValueFactory<>("estimatedProfit"));
        try {
            setMonthlyReportTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
