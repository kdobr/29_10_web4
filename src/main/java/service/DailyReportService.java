package service;

import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
    }

public void insertToReport(long carPrice) {
        new DailyReportDao(sessionFactory.openSession()).insertToReport(carPrice);
    }

    public DailyReport getLastReport() {
        return new DailyReportDao(sessionFactory.openSession()).getLastDailyReport();
    }

    public DailyReport getCurrentReport() {
        return new DailyReportDao(sessionFactory.openSession()).getCurrentDailyReport();
    }

    public DailyReport newReport() {
        return new DailyReportDao(sessionFactory.openSession()).newReport();
    }


    public void doDelete() {
        new DailyReportDao(sessionFactory.openSession()).doDelete();
    }
}
