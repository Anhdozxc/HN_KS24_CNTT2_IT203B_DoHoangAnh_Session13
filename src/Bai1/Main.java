package Bai1;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {
    /*
-Phần 1: Phân tích logic
-Do JDBC mặc định auto commit nên mỗi lệnh chạy xong là lưu luôn.
-Ở đây trừ thuốc chạy trước nên bị trừ ngay.
-Nếu sau đó bị lỗi thì chương trình dừng, không chạy tiếp phần lưu lịch sử.
-Nên bị lệch dữ liệu, kho bị trừ mà không có lịch sử.
-Vì không dùng transaction nên không quay lại được dữ liệu ban đầu.
*/
    public static void main(String[] args) {

        Connection conn = null;

        try {
            conn = DBContext.getConnection();
            /*
            Phần 2: Thực thi
            */
            // tắt auto commit
            conn.setAutoCommit(false);

            // thao tác 1: trừ thuốc
            String sql1 = "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE medicine_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, 1);
            ps1.executeUpdate();
            // test lỗi chia 0 để kiểm tra rollback
            // int x = 10 / 0;
            // thao tác 2: lưu lịch sử
            String sql2 = "INSERT INTO Prescription_History(patient_id, medicine_id, date) VALUES (?, ?, NOW())";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, 101);
            ps2.setInt(2, 1);
            ps2.executeUpdate();
            // commit nếu thành công
            conn.commit();
            System.out.println("Cấp phát thuốc thành công");
        } catch (Exception e) {

            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Đã rollback dữ liệu");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Lỗi xảy ra: " + e.getMessage());

        } finally {
            DBContext.closeConnection(conn);
        }
    }
}