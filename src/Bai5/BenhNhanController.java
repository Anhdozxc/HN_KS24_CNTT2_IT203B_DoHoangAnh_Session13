package Bai5;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class BenhNhanController {
    public void tiepNhan(String name, int age, int bedId, double money) {
        Connection conn = null;
        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false);
            // kiểm tra giường
            String checkBed = "SELECT status FROM Beds WHERE bed_id = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkBed);
            psCheck.setInt(1, bedId);
            ResultSet rs = psCheck.executeQuery();
            if (!rs.next()) {
                throw new Exception("Giường không tồn tại");
            }
            if (!rs.getString("status").equals("TRONG")) {
                throw new Exception("Giường đã có người");
            }
            // thêm bệnh nhân
            String sql1 = "INSERT INTO Patients(name, age, bed_id) VALUES (?, ?, ?)";
            PreparedStatement ps1 = conn.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
            ps1.setString(1, name);
            ps1.setInt(2, age);
            ps1.setInt(3, bedId);
            ps1.executeUpdate();
            ResultSet genKey = ps1.getGeneratedKeys();
            int patientId = 0;
            if (genKey.next()) {
                patientId = genKey.getInt(1);
            }
            // update giường
            String sql2 = "UPDATE Beds SET status = 'DA_CO_NGUOI' WHERE bed_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, bedId);
            int row = ps2.executeUpdate();
            if (row == 0) {
                throw new Exception("Update giường thất bại");
            }
            // thêm tài chính
            String sql3 = "INSERT INTO Finance(patient_id, amount) VALUES (?, ?)";
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, patientId);
            ps3.setDouble(2, money);
            ps3.executeUpdate();
            conn.commit();
            System.out.println("Tiếp nhận thành công");

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Đã rollback dữ liệu");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Lỗi: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}