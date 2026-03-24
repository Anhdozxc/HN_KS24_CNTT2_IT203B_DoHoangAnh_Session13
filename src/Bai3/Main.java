package Bai3;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Main {
    public static void main(String[] args) {
        xuatVienVaThanhToan(1, 500);
    }
    public static void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection conn = null;
        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false);
            /*
            Phân tích bài toán
            - Input: maBenhNhan (int), tienVienPhi (double)
            - Output: thông báo thành công hoặc lỗi
            Giải pháp
            - Dùng transaction để gom 3 thao tác
            - Nếu có lỗi thì rollback
            */
            // lấy số dư hiện tại
            String checkSql = "SELECT balance, bed_id FROM Patients WHERE patient_id = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkSql);
            psCheck.setInt(1, maBenhNhan);
            ResultSet rs = psCheck.executeQuery();
            if (!rs.next()) {
                throw new Exception("Không tìm thấy bệnh nhân");
            }
            double balance = rs.getDouble("balance");
            int bedId = rs.getInt("bed_id");
            /*
            Bẫy 1: thiếu tiền
            - nếu số dư < tiền viện phí thì không cho trừ
            */
            if (balance < tienVienPhi) {
                throw new Exception("Không đủ tiền");
            }
            // trừ tiền
            String sql1 = "UPDATE Patients SET balance = balance - ? WHERE patient_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setDouble(1, tienVienPhi);
            ps1.setInt(2, maBenhNhan);
            int row1 = ps1.executeUpdate();
            /*
            Bẫy 2: row affected = 0
            - nếu update không ảnh hưởng dòng nào thì lỗi
            */
            if (row1 == 0) {
                throw new Exception("Trừ tiền thất bại");
            }
            // giải phóng giường
            String sql2 = "UPDATE Beds SET status = 'TRONG' WHERE bed_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, bedId);
            int row2 = ps2.executeUpdate();
            if (row2 == 0) {
                throw new Exception("Cập nhật giường thất bại");
            }
            // cập nhật trạng thái bệnh nhân
            String sql3 = "UPDATE Patients SET status = 'Đã xuất viện' WHERE patient_id = ?";
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, maBenhNhan);
            int row3 = ps3.executeUpdate();
            if (row3 == 0) {
                throw new Exception("Cập nhật bệnh nhân thất bại");
            }
            conn.commit();
            System.out.println("Xuất viện thành công");
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
            DBContext.closeConnection(conn);
        }
    }
}