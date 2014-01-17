package com.example.pinnedrefreshlistsample;

import com.android.pinnedrefreshlist.PullListView;
import com.android.pinnedrefreshlist.PullListView.OnRefreshListener;
import com.android.pinnedrefreshlist.RefreshLoadingLayoutImp;
import com.android.pinnedrefreshlist.SectionedBaseAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private PullListView list;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TestSectionedAdapter sectionedAdapter = new TestSectionedAdapter();

		list = (PullListView) getListView();
		list.setTopLoadingLayout(new RefreshLoadingLayoutImp(this, "list"));
		LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout header1 = (LinearLayout) inflator.inflate(
				R.layout.list_item, null);
		list.addHeaderView(header1);

		list.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				list.setRefreshing(true);
				new GetDataTask().execute();
			}

		});
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("====="+arg2+"=====arg3===="+arg3);
			}
			
		});

		setListAdapter(sectionedAdapter);
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			list.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	public class TestSectionedAdapter extends SectionedBaseAdapter {

		@Override
		public Object getItem(int section, int position) {
			return null;
		}

		@Override
		public long getItemId(int section, int position) {
			return 0;
		}

		@Override
		public int getSectionCount() {
			return 7;
		}

		@Override
		public int getCountForSection(int section) {
			return 15;
		}

		@Override
		public View getItemView(int section, int position, View convertView,
				ViewGroup parent) {
			LinearLayout layout = null;
			if (convertView == null) {
				LayoutInflater inflator = (LayoutInflater) parent.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				layout = (LinearLayout) inflator.inflate(R.layout.list_item,
						null);
			} else {
				layout = (LinearLayout) convertView;
			}
			((TextView) layout.findViewById(R.id.textItem)).setText("Section "
					+ section + " Item " + position);
			return layout;
		}

		@Override
		public View getSectionHeaderView(int section, View convertView,
				ViewGroup parent) {
			LinearLayout layout = null;
			if (convertView == null) {
				LayoutInflater inflator = (LayoutInflater) parent.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				layout = (LinearLayout) inflator.inflate(R.layout.header_item,
						null);
			} else {
				layout = (LinearLayout) convertView;
			}
			((TextView) layout.findViewById(R.id.textItem))
					.setText("Header for section " + section);
			return layout;
		}

	}

}
