package mypaintBoard;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
public class OpenHistoriesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitity_open_histories);
      final ImageView imageView= findViewById(R.id.imageView);
       final ListView listView=findViewById(R.id.listView);
        listView.setStackFromBottom(true);
      Button returnMain = findViewById(R.id.btn_returnAuthor);
      try {
          File file = new File(Environment.getExternalStorageDirectory().getPath() + "/绘图程序APP/绘图保存");
          final String[] list = file.list();
          if (list.length!=0){
              Bitmap map = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + "/绘图程序APP/绘图保存/" + list[list.length-1]);
              imageView.setImageBitmap(map);
          }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_expandable_list_item_1, list);
        listView.setAdapter(adapter);
          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                  Bitmap map = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + "/绘图程序APP/绘图保存/" + list[i]);
                  imageView.setImageBitmap(map);
                  MyView.map=map;
                  Toast.makeText(OpenHistoriesActivity.this, "选择保存记录 "+list[i]+" 继续绘图 ", Toast.LENGTH_SHORT).show();
              }
          });
      }catch (Exception e){
          Toast.makeText(OpenHistoriesActivity.this, "暂无保存记录 ", Toast.LENGTH_SHORT).show();
         finish();
      }
          returnMain.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  finish();
              }
          });
    }
}