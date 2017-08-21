package com.dlc.electronicbalance.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.interfaces.OnDateSelectedCallBack;
import com.dlc.electronicbalance.widget.wheel_view.NumericWheelAdapter;
import com.dlc.electronicbalance.widget.wheel_view.OnWheelScrollListener;
import com.dlc.electronicbalance.widget.wheel_view.WheelView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerPopWindow extends PopupWindow {
	private Context context;
	private String startTime;
	private int curYear,curMonth;
	private LayoutInflater mInflater;
	private View dateView;
	private WheelView yearView;
	private WheelView monthView;
	private WheelView dayView;
	private int[] timeInt;
	private TextView btnok;
	private TextView btncancle;
	private TextView showtext;
	private OnDateSelectedCallBack dateSelectedCallBack;

	public DatePickerPopWindow(Context context, String startTime, OnDateSelectedCallBack dateSelectedCallBack){
		this.context=context;
		this.startTime=startTime;
		this.dateSelectedCallBack = dateSelectedCallBack;
		setStartTime();
		initWindow();
	}
	private void setStartTime() {
		// TODO Auto-generated method stub
		timeInt=new int[6];
		timeInt[0]= Integer.valueOf(startTime.substring(0, 4));
		timeInt[1]= Integer.valueOf(startTime.substring(4, 6));
		timeInt[2]= Integer.valueOf(startTime.substring(6, 8));
		timeInt[3]= Integer.valueOf(startTime.substring(8, 10));
		timeInt[4]= Integer.valueOf(startTime.substring(10, 12));
		timeInt[5]= Integer.valueOf(startTime.substring(12, 14));
	}
	private void initWindow() {
		// TODO Auto-generated method stub
		mInflater= LayoutInflater.from(context);
		dateView=mInflater.inflate(R.layout.wheel_date_picker, null);
		yearView=(WheelView) dateView.findViewById(R.id.year);
		monthView=(WheelView) dateView.findViewById(R.id.month);
		dayView=(WheelView) dateView.findViewById(R.id.day);
		btnok = (TextView)dateView.findViewById(R.id.btn_ok);

		//用于将时间转成data类型 比较大小
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		btnok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(dateSelectedCallBack != null){
					//获取当前选中时间
					StringBuilder presentData=new StringBuilder().append((yearView.getCurrentItem() + curYear)).append("-").append((monthView.getCurrentItem() + 1) < 10 ? "0" + (monthView.getCurrentItem() + 1) : (monthView.getCurrentItem() + 1)).append("-").append(((dayView.getCurrentItem() + 1) < 10) ? "0" + (dayView.getCurrentItem() + 1) : (dayView.getCurrentItem() + 1));
					String presentDatas = presentData.toString();
					Date date1 = null;
					try {
						//系统将控件上获取的 " " 转换为了 "\t"
						String newpresentData = presentDatas.replace("\t", " ");
						date1 = sdf.parse(newpresentData);
						int result = date1.compareTo(new Date());
							if(result < 0){
								dateSelectedCallBack.onError("开始时间必须大于当前时间！");
								return;
							}
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					dateSelectedCallBack.onSelectedOk(presentDatas,sdf.format(date1));
				}
			}
		});
		btncancle = (TextView)dateView.findViewById(R.id.btn_cancle);
		btncancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(dateSelectedCallBack != null){
					dateSelectedCallBack.ondestory();
				}
			}
		});

		initWheel();
	}

	/**
	 * 截取时间
	 * @param myActivityDate
	 * @return
	 * @throws Exception
	 */
	private Date[] MyDateTrianSiTion(String myActivityDate) throws Exception {
		String[] split1 =  myActivityDate.split("~");
		SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d1 = sformat.parse(split1[0]);
		Date d2 = sformat.parse(split1[1]);
		return new Date[]{d1,d2};
	}

	/**
	 * 初始化滚筒效果
	 */
	private void initWheel() {
		// TODO Auto-generated method stub
		Calendar calendar= Calendar.getInstance();
		curYear=calendar.get(Calendar.YEAR);
		curMonth=calendar.get(Calendar.MONTH)+1;
		
		NumericWheelAdapter numericWheelAdapter1=new NumericWheelAdapter(context,curYear, curYear+10);
		numericWheelAdapter1.setLabel("年");
		yearView.setViewAdapter(numericWheelAdapter1);
		yearView.setCyclic(true);
		yearView.addScrollingListener(scrollListener);
		
		NumericWheelAdapter numericWheelAdapter2=new NumericWheelAdapter(context,1, 12, "%02d"); 
		numericWheelAdapter2.setLabel("月");
		monthView.setViewAdapter(numericWheelAdapter2);
		monthView.setCyclic(true);
		monthView.addScrollingListener(scrollListener);
		
		NumericWheelAdapter numericWheelAdapter3=new NumericWheelAdapter(context,1, getDay(curYear, curMonth), "%02d");
		numericWheelAdapter3.setLabel("日");
		dayView.setViewAdapter(numericWheelAdapter3);
		dayView.setCyclic(true);
		dayView.addScrollingListener(scrollListener);
		
		/*NumericWheelAdapter numericWheelAdapter4=new NumericWheelAdapter(context,0, 23, "%02d");
		numericWheelAdapter4.setLabel("时");
		hourView.setViewAdapter(numericWheelAdapter4);
		hourView.setCyclic(true);
		hourView.addScrollingListener(scrollListener);
		
		NumericWheelAdapter numericWheelAdapter5=new NumericWheelAdapter(context,0, 59, "%02d"); 
		numericWheelAdapter5.setLabel("分");
		minView.setViewAdapter(numericWheelAdapter5);
		minView.setCyclic(true);
		minView.addScrollingListener(scrollListener);*/
		
		yearView.setCurrentItem(timeInt[0]-curYear);
		monthView.setCurrentItem(timeInt[1]-1);
		dayView.setCurrentItem(timeInt[2]-1);
		yearView.setVisibleItems(5);//设置显示行数
		monthView.setVisibleItems(5);
		dayView.setVisibleItems(5);
		setContentView(dateView);
		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
		setBackgroundDrawable(dw);
		setFocusable(true);
	}
	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			int n_year = yearView.getCurrentItem() + curYear;//年
			int n_month = monthView.getCurrentItem() + 1;//月
			
			initDay(n_year, n_month);
			
		/*	String birthday=new StringBuilder().append((yearView.getCurrentItem()+curYear)).append("-").append((monthView.getCurrentItem() + 1) < 10 ? "0" + (monthView.getCurrentItem() + 1) : (monthView.getCurrentItem() + 1)).append("-").append(((dayView.getCurrentItem()+1) < 10) ? "0" + (dayView.getCurrentItem()+1) : (dayView.getCurrentItem()+1)).toString();
			birthday+="	"+hourView.getCurrentItem()+":"+minView.getCurrentItem();
			Toast.makeText(context, birthday, Toast.LENGTH_SHORT).show();*/
		}
	};
	private void initDay(int arg1, int arg2) {
		NumericWheelAdapter numericWheelAdapter=new NumericWheelAdapter(context,1, getDay(arg1, arg2), "%02d");
		numericWheelAdapter.setLabel("日");
		dayView.setViewAdapter(numericWheelAdapter);
	}
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}


}
