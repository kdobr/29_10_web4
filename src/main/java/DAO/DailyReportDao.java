package DAO;

import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastDailyReport() {
        List<DailyReport> reports = getAllDailyReport();
        if (reports.size() < 2) return null;
        return reports.get(reports.size() - 2);
    }

    public DailyReport newReport() {
        Transaction transaction = session.beginTransaction();
        DailyReport report = new DailyReport(0l, 0l);
        session.save(report);
        transaction.commit();
        session.close();
        return report;
    }

    public DailyReport getCurrentDailyReport() {
        List<DailyReport> reports = getAllDailyReport();
        if (reports.size() == 0) {
            DailyReport firstReport = newReport();
            reports.add(firstReport);
        }
        return reports.get(reports.size() - 1);
    }

    public void insertToReport(long carPrice) {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> reports = session.createQuery("FROM DailyReport").list();
        if (reports.size() == 0) {
            DailyReport report = new DailyReport(carPrice, 1l);
            session.save(report);
        } else {
            DailyReport report = reports.get(reports.size() - 1);
            report.setEarnings(report.getEarnings() + carPrice);
            report.setSoldCars(report.getSoldCars() + 1);
        }
        transaction.commit();
        session.close();
    }

    public void doDelete() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from Car").executeUpdate();
        session.createQuery("delete from DailyReport").executeUpdate();
        transaction.commit();
        session.close();
    }
}
