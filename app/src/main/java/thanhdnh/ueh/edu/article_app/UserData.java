package thanhdnh.ueh.edu.article_app;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserData {
  public static UserList data;
  private Context context;
  private GridView gridview;
  private final ExecutorService executor = Executors.newSingleThreadExecutor();

  public UserData(Context context, GridView gridview) {
    this.context = context;
    this.gridview = gridview;
  }

  public static UserProfile getUserFromId(int id) {
    if (data == null || data.getUsers() == null) return null;
    for (int i = 0; i < data.getUsers().size(); i++)
      if (data.getUsers().get(i).getId() == id)
        return data.getUsers().get(i);
    return null;
  }

  public void loadData(String url, Activity activity){
      executor.execute(()->{
          File file = Downloader.downloadFile(url, context.getCacheDir());
          if(file != null) {
            String jsonString = readText(file);
            try {
              Gson gson = new Gson();
              UserList parsedData = gson.fromJson(jsonString, (Type) UserList.class);
              if (parsedData != null && parsedData.getUsers() != null && !parsedData.getUsers().isEmpty()) {
                data = parsedData;
                activity.runOnUiThread(()->{
                  UserAdapter adapter = new UserAdapter(data.getUsers(), context);
                  gridview.setAdapter(adapter);
                });
                return;
              }
            } catch (Exception e) {
              Log.e("UserData", "Lỗi parse JSON: " + e.getMessage());
            }
          }

          // Dữ liệu dự phòng (Fallback) nếu chưa tải được từ GitHub hoặc URL lỗi 404
          activity.runOnUiThread(()->{
            Toast.makeText(context, "Không thể tải từ GitHub URL, hiển thị dữ liệu mẫu...", Toast.LENGTH_SHORT).show();
            data = createFallbackUsers();
            UserAdapter adapter = new UserAdapter(data.getUsers(), context);
            gridview.setAdapter(adapter);
          });
      });
  }

  public String readText(File file){
    if (file == null) return "";
    StringBuilder buffer = new StringBuilder();
    try (InputStream stream = new FileInputStream(file);
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      String line;
      while ((line = reader.readLine()) != null) {
        buffer.append(line).append("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return buffer.toString();
  }

  private UserList createFallbackUsers() {
    ArrayList<UserProfile> list = new ArrayList<>();
    list.add(new UserProfile(1, "nguyenvana", "nguyenvana@gmail.com", "0901234567", "Sinh vien nam 3 nganh CNTT", "https://i.pravatar.cc/300?img=1", "Lap trinh, Doc sach"));
    list.add(new UserProfile(2, "tranthib", "tranthib@gmail.com", "0912345678", "Designer tai cong ty ABC", "https://i.pravatar.cc/300?img=2", "Ve tranh, Chup anh"));
    list.add(new UserProfile(3, "levanc", "levanc@gmail.com", "0923456789", "Ky su phan mem Mobile", "https://i.pravatar.cc/300?img=3", "Boi loi, Chay bo"));
    list.add(new UserProfile(4, "phamthid", "phamthid@gmail.com", "0934567890", "Giang vien dai hoc AI", "https://i.pravatar.cc/300?img=4", "Nghien cuu, Viet blog"));
    list.add(new UserProfile(5, "hoangvane", "hoangvane@gmail.com", "0945678901", "Freelancer Web UI/UX", "https://i.pravatar.cc/300?img=5", "Code, Nghe nhac"));
    list.add(new UserProfile(6, "dovang", "dovang@gmail.com", "0956789012", "Marketing Specialist", "https://i.pravatar.cc/300?img=6", "Viet content, The thao"));
    list.add(new UserProfile(7, "buithih", "buithih@gmail.com", "0967890123", "Data Analyst", "https://i.pravatar.cc/300?img=7", "Phan tich, Tap gym"));
    list.add(new UserProfile(8, "ngovani", "ngovani@gmail.com", "0978901234", "Product Manager", "https://i.pravatar.cc/300?img=8", "Quan ly, Co vua"));
    list.add(new UserProfile(9, "lythik", "lythik@gmail.com", "0989012345", "Sinh vien thuc tap", "https://i.pravatar.cc/300?img=9", "Hoc ngoai ngu, Du lich"));
    list.add(new UserProfile(10, "trinhvanl", "trinhvanl@gmail.com", "0990123456", "Backend Java Developer", "https://i.pravatar.cc/300?img=10", "Lap trinh, Guitar"));
    return new UserList(list);
  }
}
