package Bai4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
public class Main {

    public static void main(String[] args) {
        getDanhSachBenhNhan();
    }
    /*
    Phân tích
    - Input: không có hoặc filter
    - Output: danh sách bệnh nhân + list dịch vụ
    - Yêu cầu: load nhanh, không bị chậm khi nhiều bệnh nhân
    */
    /*
    Giải pháp 1 (N+1 Query)
    - Query 1: lấy danh sách bệnh nhân
    - Với mỗi bệnh nhân lại query tiếp dịch vụ
    - Tổng số query = N + 1
    - Nhược điểm: rất chậm khi N lớn
    */
    /*
    Giải pháp 2 (JOIN)
    - Dùng LEFT JOIN để lấy tất cả trong 1 query
    - Sau đó gộp dữ liệu bằng Java nhanh hơn nhiều
    */
    public static void getDanhSachBenhNhan() {
        Connection conn = null;
        try {
            conn = DBContext.getConnection();
            // dùng LEFT JOIN để không mất bệnh nhân chưa có dịch vụ
            String sql = "SELECT b.maBenhNhan, b.tenBenhNhan, d.tenDichVu " + "FROM BenhNhan b LEFT JOIN DichVuSuDung d " + "ON b.maBenhNhan = d.maBenhNhan";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // dùng map để gộp dữ liệu
            Map<Integer, List<String>> map = new HashMap<>();
            Map<Integer, String> tenMap = new HashMap<>();

            while (rs.next()) {
                int ma = rs.getInt("maBenhNhan");
                String ten = rs.getString("tenBenhNhan");
                String dichVu = rs.getString("tenDichVu");
                tenMap.put(ma, ten);
                // nếu chưa có thì tạo list
                if (!map.containsKey(ma)) {
                    map.put(ma, new ArrayList<>());
                }
                /*
                Bẫy 2
                - Nếu bệnh nhân chưa có dịch vụ thì dichVu sẽ null
                - Không được add null vào list
                - Nếu add sẽ lỗi hoặc dữ liệu sai
                */
                if (dichVu != null) {
                    map.get(ma).add(dichVu);
                }
            }
            // in kết quả
            for (int ma : map.keySet()) {
                System.out.println("Bệnh nhân: " + tenMap.get(ma));
                System.out.println("Dịch vụ: " + map.get(ma));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}