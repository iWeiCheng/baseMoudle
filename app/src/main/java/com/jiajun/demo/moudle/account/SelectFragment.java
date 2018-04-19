package com.jiajun.demo.moudle.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jiajun.demo.R;
import com.jiajun.demo.base.BottomDialogFragment;
import com.jiajun.demo.moudle.account.entities.OrgInfo;

import java.util.ArrayList;

/**
 * 选择集团、公司
 * Created by danjj on 2016/12/6.
 */
public class SelectFragment extends BottomDialogFragment implements AdapterView.OnItemClickListener {

    private ArrayList<OrgInfo.OrgInfoBean> list;
    private int position;
    private OnClickSubmitListener listener;

    public static SelectFragment getInstance(ArrayList<OrgInfo.OrgInfoBean> list, int position) {
        SelectFragment f = new SelectFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("data", list);
        args.putInt("position", position);
        f.setArguments(args);
        return f;
    }

    @Override
    protected void getExtra(@NonNull Bundle bundle) {
        list = bundle.getParcelableArrayList("data");
        position = bundle.getInt("position");
    }

    public void setClickItemListener(OnClickSubmitListener onClickSubmitListener) {
        this.listener = onClickSubmitListener;

    }

    private ListView listView;

    @Override
    protected void loadLayout() {
//        setContentView(R.layout.fragment_list);
    }

    @Override
    protected void initialize(View view, Bundle savedInstanceState) {
        super.initialize(view, savedInstanceState);
//        listView = (ListView) view.findViewById(R.id.listView);
//        if (position == -1 && list != null) {
//            listView.setAdapter(new OrgAdapter(getContext(), list));
//        } else if(position>=0){
//            listView.setAdapter(new CompanyAdapter(getContext(), list.get(position).getSubOrg()));
//        }
//        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listener != null) {
            listener.onClickItem(this.position, position);
        }
    }


    public interface OnClickSubmitListener {
        /**
         * 提供方法供调用者调用
         *
         * @param index    判断是否是集团还是公司的标志 -1为集团 其他为公司
         * @param position
         */
        void onClickItem(int index, int position);
    }
}
