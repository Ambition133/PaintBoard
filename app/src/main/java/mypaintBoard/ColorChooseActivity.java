package mypaintBoard;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.HashMap;
public class ColorChooseActivity extends Activity  {
  ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_choose);
        listView = findViewById(R.id.listView);
        int white = Color.WHITE;
        int black = Color.BLACK;
        int red = Color.RED;
        int magenta = Color.MAGENTA;
        int blue = Color.BLUE;
        int green = Color.GREEN;
        int yellow = Color.YELLOW;
        int ltgray = Color.LTGRAY;
        int gray = Color.GRAY;
        int cyan = Color.CYAN;
        int dkgray = Color.DKGRAY;
        String[] strs = {"白色","黑色","红色","紫红", "蓝色", "绿色","黄色","亮灰","灰色","青色","灰黑"};
        int[] imageID = {R.drawable.white,R.drawable.black,R.drawable.red,R.drawable.magenta ,R.drawable.blue,R.drawable.green,R.drawable.yellow,R.drawable.ligth_gray,R.drawable.gray,R.drawable.cyan,R.drawable.dkgray};
        final HashMap<String, Integer> map = new HashMap<>();
        map.put("白色",white);
        map.put("黑色",black);
        map.put("红色",red);
        map.put("紫红",magenta);
        map.put("蓝色",blue);
        map.put("绿色",green);
        map.put("黄色",yellow);
        map.put("亮灰",ltgray);
        map.put("灰色",gray);
        map.put("青色",cyan);
        map.put("灰黑",dkgray);
        ImageAndTextAdapter imageAndTextAdapter = new ImageAndTextAdapter(this, imageID, strs);
        listView.setAdapter(imageAndTextAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String key= adapterView.getItemAtPosition(i).toString();
                MainActivity.chooseColor=map.get(key);
                Toast.makeText(ColorChooseActivity.this, "你选择了:"+key+"画笔" , Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}