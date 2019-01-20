package service;
 
import java.util.List;
 
import dao.RecordDAO;
import entity.Record;
import gui.page.SpendPage;
import util.DateUtil;
 
public class SpendService {
 
    public SpendPage getSpendPage() {
        RecordDAO dao = new RecordDAO();
        // Record of this month
        List<Record> thisMonthRecords = dao.listThisMonth();
        // Record of today
        List<Record> toDayRecords = dao.listToday();
        // Total days of current month
        int thisMonthTotalDay = DateUtil.thisMonthTotalDay();
 
        int monthSpend = 0;
        int todaySpend = 0;
        int avgSpendPerDay = 0;
        int monthAvailable = 0;
        int dayAvgAvailable = 0;
        int monthLeftDay = 0;
        int usagePercentage = 0;
 
        // Budget
        int monthBudget = new ConfigService().getIntBudget();
 
        // Calculate monthly cost
        for (Record record : thisMonthRecords) {
            monthSpend += record.getSpend();
        }
 
        // Calculate daily cost
        for (Record record : toDayRecords) {
            todaySpend += record.getSpend();
        }
        // Calculate daily average cost
        avgSpendPerDay = monthSpend / thisMonthTotalDay;
        // Calculate money left of current month
        monthAvailable = monthBudget - monthSpend;
 
        monthLeftDay = DateUtil.thisMonthLeftDay();
 
        dayAvgAvailable = monthAvailable / monthLeftDay;
 
        // Calculate spend percentage
        if (monthBudget > 0)
            usagePercentage = monthSpend * 100 / monthBudget;
        else
            usagePercentage = 100;
 
        return new SpendPage(monthSpend, todaySpend, avgSpendPerDay, monthAvailable, dayAvgAvailable, monthLeftDay,
                usagePercentage);
    }
}