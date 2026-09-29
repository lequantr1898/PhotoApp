package thanhdnh.ueh.edu.article_app;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
  private ArrayList<UserProfile> user_list;
  private Context context;
  private Handler mainHandler = new Handler(Looper.getMainLooper());

  public UserAdapter(ArrayList<UserProfile> user_list, Context context) {
    this.user_list = user_list;
    this.context = context;
  }

  @Override
  public int getCount() {
    return user_list.size();
  }

  @Override
  public Object getItem(int position) {
    return user_list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return user_list.get(position).getId();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final MyView dataitem;
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (convertView == null) {
      dataitem = new MyView();
      convertView = inflater.inflate(R.layout.user_disp_tpl, null);
      dataitem.iv_photo = convertView.findViewById(R.id.imv_photo);
      dataitem.tv_caption = convertView.findViewById(R.id.tv_title);
      dataitem.pb_item = convertView.findViewById(R.id.pb_item);
      convertView.setTag(dataitem);
    } else {
      dataitem = (MyView) convertView.getTag();
    }

    UserProfile user = user_list.get(position);
    dataitem.tv_caption.setText(user.getUsername());

    // Su dung ham downloadWithProgress voi thanh tien trinh nam ngang
    Downloader.downloadWithProgress(
      user.getAvatar_url(),
      mainHandler,
      context,
      context.getCacheDir(),
      dataitem.pb_item,
      dataitem.iv_photo
    );

    return convertView;
  }

  private static class MyView {
    ImageView iv_photo;
    TextView tv_caption;
    ProgressBar pb_item;
  }
}
