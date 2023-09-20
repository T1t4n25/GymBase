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
import java.util.Date;
import java.util.ResourceBundle;

public class billingController implements Initializable {
    @FXML
    Label BBLabel;
    @FXML
    Label BBMoneyLabel;
    @FXML
    Label fitLabel;
    @FXML
    Label fitMoneyLabel;
    @FXML
    Label privateLabel;
    @FXML
    Label privateMoneyLabel;
    @FXML
    Label sessionLabel;
    @FXML
    Label sessionMoneyLabel;
    @FXML
    Label totalLabel;
    @FXML
    Label totalMoneyLabel;
    @FXML
    Label monthIncome;
    @FXML
    Arc BBArc;
    @FXML
    Arc fitArc;
    @FXML
    Arc privateArc;
    @FXML
    Arc sessionArc;
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

    @FXML
    TableView<Income> incomeTable;
    @FXML
    TableColumn<Income, Integer> valueCol;
    @FXML
    TableColumn<Income, String> nameCol;
    @FXML
    TableColumn<Income, Date> inDateCol;
    ResultSet resultSet1, resultSet2;
    ObservableList<FinancialReport> monthlyData;
    ObservableList<Income> incomeData;
    int incomeSum = 0;
    public billingController(){

    }

    private void setIncomeTable() throws SQLException {
        resultSet1 = DBConnection.statement.executeQuery(
                "SELECT `name`, `date`, `value` FROM `money_in_data` WHERE (MONTH(`date`) = MONTH(CURDATE()) AND YEAR(`date`) = YEAR(CURDATE()))");
        int value;
        String name;
        Date date;
        incomeData = FXCollections.observableArrayList();
        resultSet1.beforeFirst();
        while(resultSet1.next()){
            value = resultSet1.getInt("value");
            incomeSum += value;
            name = resultSet1.getString("name");
            date = resultSet1.getDate("date");
            incomeData.add(new Income(value, name, date));
        }
        incomeTable.setItems(incomeData);
        monthIncome.setText(String.valueOf(incomeSum) + "ج.م");
        incomeSum = 0;
    }
    private void setMonthlyReportTable() throws SQLException {
        resultSet1 = DBConnection.statement.executeQuery("SELECT `id`,`date`, `value` FROM `money_in_data` ORDER BY date DESC;");
        resultSet2 = DBConnection.statement2.executeQuery("SELECT `id`, `date`, `value` FROM `expenses_data` ORDER BY date DESC;");
        LocalDate date1 = null, date2  = null, date3  = null;
        int incomeTotal = 0;
        int expenseTotal = 0;
        boolean firstime = true;
        monthlyData = FXCollections.observableArrayList();
        resultSet1.beforeFirst();
        resultSet2.beforeFirst();
        if (!resultSet1.isLast()) {
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
                    } else
                        break;

                }
                while (resultSet2.next()) {
                    date3 = resultSet2.getDate("date").toLocalDate();
                    if (date3.getYear() == date1.getYear() && date3.getMonth() == date1.getMonth()) {
                        expenseTotal += resultSet2.getInt("value");
                    } else {
                        resultSet2.previous();
                        break;
                    }
                }
                monthlyData.add(new FinancialReport(incomeTotal,
                        expenseTotal,
                        (date1.getYear() + " / " + date1.getMonthValue())));
                incomeTotal = 0;
                expenseTotal = 0;
            } while (!resultSet1.isAfterLast());
            monthlyReportTable.setItems(monthlyData);
        }
    }
    private int getBBMembers() throws SQLException {
        resultSet1 = DBConnection.statement.executeQuery(
                "SELECT `id` FROM `members_data` WHERE `subType` = 'كمال اجسام' AND `subState` = 'نشط'");
        resultSet1.last();
        return resultSet1.getRow();
    }
    private int getFitMembers() throws SQLException {
        resultSet1 = DBConnection.statement.executeQuery(
                "SELECT `id` FROM `members_data` WHERE `subType` = 'تخسيس' AND `subState` = 'نشط'");
        resultSet1.last();
        return resultSet1.getRow();
    }
    private int getPrivateMembers() throws SQLException {
        resultSet1 = DBConnection.statement.executeQuery(
                "SELECT `id` FROM `members_data` WHERE `subType` = 'برايفت' AND `subState` = 'نشط'");
        resultSet1.last();
        return resultSet1.getRow();
    }
    private int getSessionMembers() throws SQLException {
        resultSet1 = DBConnection.statement.executeQuery(
                "SELECT `id` FROM `members_data` WHERE `subType` = 'حصة' AND (MONTH(`lastPayDate`) = MONTH(CURDATE()) AND YEAR(`lastPayDate`) = YEAR(CURDATE()))");
        resultSet1.last();
        return resultSet1.getRow();
    }
    private int getMoney(String type) throws SQLException {
        int sum = 0;
        String query = "SELECT `subValue` FROM `members_data` WHERE `subType` = '"+ type +"' AND (MONTH(`lastPayDate`) = MONTH(CURDATE()) AND YEAR(`lastPayDate`) = YEAR(CURDATE()))";
        resultSet1 = DBConnection.statement.executeQuery(query);
        resultSet1.beforeFirst();
        while(resultSet1.next())
            sum += resultSet1.getInt("subValue");
        return sum;
    }
    private int getTotalMoney() throws SQLException {
        int sum = 0;
        resultSet1 = DBConnection.statement.executeQuery(
                "SELECT `subValue` FROM `members_data` WHERE (MONTH(`lastPayDate`) = MONTH(CURDATE()) AND YEAR(`lastPayDate`) = YEAR(CURDATE()))");
        resultSet1.beforeFirst();
        while(resultSet1.next())
            sum += resultSet1.getInt("subValue");
        return sum;
    }
    private void setArcsLabels() throws SQLException {
        int BB = getBBMembers();
        int fit = getFitMembers();
        int privateNum = getPrivateMembers();
        int session = getSessionMembers();
        int totalMembers = BB + fit + privateNum + session;

        int BBMny = getMoney("كمال اجسام");
        int fitMny = getMoney("تخسيس");
        int privateMny = getMoney("برايفت");
        int sessionMny = getMoney("حصة");
        int totalMny = getTotalMoney();
        BBArc.setLength((double) (BB * 360) /totalMembers);
        fitArc.setLength((double) (fit * 360) /totalMembers);
        privateArc.setLength((double) (privateNum * 360) /totalMembers);
        sessionArc.setLength((double) (session * 360) /totalMembers);

        BBLabel.setText(String.valueOf(BB));
        fitLabel.setText(String.valueOf(fit));
        privateLabel.setText(String.valueOf(privateNum));
        sessionLabel.setText(String.valueOf(session));
        totalLabel.setText(String.valueOf(totalMembers));

        BBMoneyLabel.setText(String.valueOf(BBMny) + "ج.م");
        fitMoneyLabel.setText(String.valueOf(fitMny) + "ج.م");
        privateMoneyLabel.setText(String.valueOf(privateMny) + "ج.م");
        sessionMoneyLabel.setText(String.valueOf(sessionMny) + "ج.م");
        totalMoneyLabel.setText(String.valueOf(totalMny) + "ج.م");
    }
    @FXML
    private void refreshData() throws SQLException {
        setArcsLabels();
        setMonthlyReportTable();
        setIncomeTable();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        incomeCol.setCellValueFactory(new PropertyValueFactory<>("incomeTotal"));
        expensesCol.setCellValueFactory(new PropertyValueFactory<>("expensesTotal"));
        profitCol.setCellValueFactory(new PropertyValueFactory<>("estimatedProfit"));
        ////
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        try {
            refreshData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
