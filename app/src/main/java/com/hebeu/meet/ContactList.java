package com.hebeu.meet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.hebeu.meet.domain.ActivityJoinUser;
import java.util.ArrayList;

import static com.hebeu.meet.tools.ImageHandler.stringToBitmap;

/**
 * 申请者联系方式列表页
 * 5-28
 * liHang
 */
public class ContactList extends AppCompatActivity {

    private ArrayList<ActivityJoinUser>  activityJoinUserList = null;
    private ListView listView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        listView = findViewById(R.id.Contact_list);

        activityJoinUserList = (ArrayList<ActivityJoinUser>) getIntent().getSerializableExtra("list2");

        MyBaseAdapter myBaseAdapter = new MyBaseAdapter();
        listView.setAdapter(myBaseAdapter);
    }

    class MyBaseAdapter extends BaseAdapter {
        /**
         * 列表适配器
         * @return
         */
        //获取当前items项的大小，也可以看成是数据源的大小
        @Override
        public int getCount() {
            return activityJoinUserList.size();
        }
        //根据item的下标获取到View对象
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }
        //获取到items的id
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            LayoutInflater inflater = ContactList.this.getLayoutInflater();
            view = inflater.inflate(R.layout.contact_list_item, null);
            final ActivityJoinUser u = activityJoinUserList.get(position);//通过回调这个方法传过来的position参数获取到指定数据源中的对象

            TextView Contact_qq;
            TextView Contact_phone;
            ImageView Contact_Publisher_head;
            ImageView Contact_sex;
            TextView Contact_mc;
            TextView Contact_bj;
            TextView Contact_words;

            Contact_qq = view.findViewById(R.id.contact_qq);
            Contact_bj = view.findViewById(R.id.contact_bj);
            Contact_phone = view.findViewById(R.id.contact_phone);
            Contact_Publisher_head = view.findViewById(R.id.Contact_Publisher_head);
            Contact_sex = view.findViewById(R.id.Contact_sex);
            Contact_mc = view.findViewById(R.id.contact_mc);
            Contact_words = view.findViewById(R.id.contact_words);

            Contact_bj.setText(u.getClassName());
            Contact_qq.setText("QQ："+u.getQq());
            Contact_phone.setText("电话："+u.getPhone());
            Contact_words.setText(u.getWords());
            Contact_Publisher_head.setImageBitmap(stringToBitmap(u.getHead()));//设置头像
            switch (u.getSex())//设置性别图片
            {
                case 0: Contact_sex.setImageResource(R.drawable.man);break;
                case 1:Contact_sex.setImageResource(R.drawable.woman);break;
                default:break;
            }
            Contact_mc.setText(u.getUserName());

            return view;
        }

    }
}
