package Bai2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DBContext.getConnection();
            /*
            Phần 1: Phân tích
            - Đã tắt auto commit nên dữ liệu chưa lưu ngay
            - Khi lỗi xảy ra ở bước 2 thì chương trình dừng
            - Trong catch chỉ in lỗi ra màn hình
            - Không có xử lý gì với transaction đang chạy
            - Kết nối vẫn giữ trạng thái chưa hoàn tất
            - Có thể gây lỗi hoặc tốn tài nguyên
            - Lỗi chính là thiếu rollback
            */
            conn.setAutoCommit(false);
            // trừ tiền ví
            String sql1 = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setDouble(1, 500);
            ps1.setInt(2, 1);
            ps1.executeUpdate();
            // cập nhật hóa đơn (cố tình sai để gây lỗi)
            String sql2 = "UPDATE Invoicess SET status = 'PAID' WHERE invoice_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, 1);
            ps2.executeUpdate();
            conn.commit();
            System.out.println("Thanh toán thành công");
        } catch (SQLException e) {
            /*
            Phần 2: Thực thi- Khi có lỗi phải rollback để hủy transaction
            */
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Đã rollback dữ liệu");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Lỗi xảy ra: " + e.getMessage());

        } finally {
            DBContext.closeConnection(conn);
        }
    }
}