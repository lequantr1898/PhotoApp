package thanhdnh.ueh.edu.article_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ViewUserActivity extends AppCompatActivity {
  Button btn_back;
  ImageView iv_detail_avatar;
  TextView tv_detail_username, tv_detail_id, tv_detail_email, tv_detail_tel, tv_detail_description, tv_detail_hobby;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_user);
    getSupportActionBar().hide();

    btn_back = findViewById(R.id.btn_back);
    iv_detail_avatar = findViewById(R.id.iv_detail_avatar);
    tv_detail_username = findViewById(R.id.tv_detail_username);
    tv_detail_id = findViewById(R.id.tv_detail_id);
    tv_detail_email = findViewById(R.id.tv_detail_email);
    tv_detail_tel = findViewById(R.id.tv_detail_tel);
    tv_detail_description = findViewById(R.id.tv_detail_description);
    tv_detail_hobby = findViewById(R.id.tv_detail_hobby);

    // Xử lý sự kiện bấm nút Back để quay về trang Home
    btn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    int id = (int) getIntent().getLongExtra("user_id", 0);
    UserProfile user = UserData.getUserFromId(id);

    if (user != null) {
      // Load avatar với Picasso, resize và cắt ảnh vừa khung
      Picasso.get()
        .load(user.getAvatar_url())
        .resize(400, 400)
        .centerCrop()
        .into(iv_detail_avatar);

      tv_detail_username.setText(user.getUsername());
      tv_detail_id.setText("ID: " + user.getId());
      tv_detail_email.setText("Email: " + user.getEmail());
      tv_detail_tel.setText("Tel: " + user.getTel());
      tv_detail_description.setText("Description: " + user.getDescription());
      tv_detail_hobby.setText("Hobby: " + user.getHobby());
    }
  }
}
