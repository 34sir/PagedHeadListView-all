package com.example.chukc.pagedheadlistview_all;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AutoScrollViewPager myPager;
    private LinearLayout ovalLayout; //
    int oldIndex = 0;
    int curIndex = 0;
    private static final ArrayList<Integer> listImg = new ArrayList<Integer>();
    private ListView listview;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView ();
        setHeadView();
        initViewPager();
    }

    private void initView (){
        listImg.add(R.mipmap.bgtop );
        listImg.add(R.mipmap.a );
        listImg.add(R.mipmap.b );
        listImg.add(R.mipmap.c );
        adapter=new MainAdapter();
        listview= (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
    }

    private void initViewPager() {
        // TODO Auto-generated method stub

        final ImageBunnerAdapter imageAdapter = new ImageBunnerAdapter(LayoutInflater.from(MainActivity.this), MainActivity.this);
        myPager.setAdapter(imageAdapter);
        myPager.setCurrentItem(0);
        myPager.setPageTransformer(true, new ParallaxPageTransformer(R.id.image));
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                myPager.setInterval(4000); // /设置滚动时间间隔
                myPager.setDirection(AutoScrollViewPager.RIGHT); // 设置滚动方向
                myPager.setCycle(true);
                myPager.setAutoScrollDurationFactor(3);
                myPager.setStopScrollWhenTouch(true);
                myPager.setBorderAnimation(true);
                myPager.startAutoScroll();
            }
        }).start();
        /*
         * list.get(myPager.getCurrentItem()).setOnClickListener(new
		 * OnClickListener() {
		 *
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub startIntentActivity(myPager.getCurrentItem()); } });
		 */

        setOvalLayout(ovalLayout, R.layout.ad_bottom_item, R.id.ad_item_v, R.drawable.dot_focused, R.drawable.dot_normal, listImg);
    }

    private void setHeadView(){
        View viewHeader = LayoutInflater.from(this).inflate(R.layout.list_microshop_header, null);
        listview.addHeaderView(viewHeader);
        myPager= (AutoScrollViewPager) viewHeader.findViewById(R.id.myPager);
    }

    // 设置圆点
    private void setOvalLayout(final LinearLayout ovalLayout, int ovalLayoutId, final int ovalLayoutItemId, final int focusedId, final int normalId,
                               final List<Integer> list) {

        if (ovalLayout != null) {
            ovalLayout.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(this);
            for (int i = 0; i < list.size(); i++) {
                ovalLayout.addView(inflater.inflate(ovalLayoutId, null));
            }
            ovalLayout.getChildAt(0).findViewById(ovalLayoutItemId).setBackgroundResource(focusedId);
            if (list.size() > 0) {
                myPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    public void onPageSelected(final int i) {
                        curIndex = i % list.size();
                        ovalLayout.getChildAt(oldIndex).findViewById(ovalLayoutItemId).setBackgroundResource(normalId);
                        ovalLayout.getChildAt(curIndex).findViewById(ovalLayoutItemId).setBackgroundResource(focusedId);
                        oldIndex = curIndex;
                    }

                    public void onPageScrolled(int arg0, float arg1, int arg2) {
                    }

                    public void onPageScrollStateChanged(int arg0) {
                    }
                });
            }

        }
    }
    class ImageBunnerAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        public ImageBunnerAdapter(LayoutInflater inflater, Context context) {
            super();
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
//            return ESiteBanner.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            /*
			 * ImageView view = images.get(position % images.size());//添加一个
			 * view.setScaleType(ScaleType.FIT_XY); container.addView(view);
			 */

            View imageLayout = inflater.inflate(R.layout.item_homepager_image, container, false);
            ImageView mImageView = (ImageView) imageLayout.findViewById(R.id.image);
            if (listImg.size() > 0) {
                mImageView.setImageDrawable(getDrawable(listImg.get(position % listImg.size())));
            }
            try {
                if (imageLayout.getParent() == null) {
                    // container.addView(view);//这里可能会报一个错。must call
                    // removeView().on the child....first
                    container.addView(imageLayout);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return imageLayout;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }
}
