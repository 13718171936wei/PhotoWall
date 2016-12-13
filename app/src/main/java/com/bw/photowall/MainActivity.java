package com.bw.photowall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.bw.photowall.adapter.MyAdapter;
import com.bw.photowall.datasource.Images;

public class MainActivity extends AppCompatActivity {

    /**
     * RecyclerView展示图片
     */
    private RecyclerView recyclerView;
    /**
     * 列宽
     */
    private int mImageThumbSize;
    /**
     * 条目间隔
     */
    private int mImageThumbSpacing;
    /**
     * 适配器
     */
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设定列宽、间隔
        mImageThumbSize = getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_spacing);

        //找控件
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //添加GridLayoutManager布局管理器
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        //适配器
        myAdapter = new MyAdapter(this, Images.imageThumbUrls,recyclerView);
        recyclerView.setAdapter(myAdapter);

        //设置监听事件
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        final int numColumns = (int) Math.floor(recyclerView
                                .getWidth()
                                / (mImageThumbSize + mImageThumbSpacing));
                        if (numColumns > 0) {
                            int columnWidth = (recyclerView.getWidth() / numColumns)
                                    - mImageThumbSpacing;
                            myAdapter.setItemHeight(columnWidth);
                            recyclerView.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                    }
                });
    }
}
