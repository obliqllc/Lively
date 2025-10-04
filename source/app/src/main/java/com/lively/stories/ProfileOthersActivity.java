package com.lively.stories;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class ProfileOthersActivity extends AppCompatActivity {
	
	private ScrollView vscroll1;
	private LinearLayout linear11;
	private LinearLayout linear1;
	private ImageView back_img;
	private LinearLayout linear4;
	private CircleImageView circleimageview1;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear13;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private TextView pronoun;
	private TextView name;
	private ImageView verify;
	private TextView bio;
	private TextView date;
	private ImageView flag;
	private TextView location;
	private TextView textview10;
	private LinearLayout linear14;
	private ListView listview1;
	private TextView textview11;
	private TextView textview12;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.profile_others);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		linear11 = findViewById(R.id.linear11);
		linear1 = findViewById(R.id.linear1);
		back_img = findViewById(R.id.back_img);
		linear4 = findViewById(R.id.linear4);
		circleimageview1 = findViewById(R.id.circleimageview1);
		linear5 = findViewById(R.id.linear5);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		linear13 = findViewById(R.id.linear13);
		linear9 = findViewById(R.id.linear9);
		linear10 = findViewById(R.id.linear10);
		pronoun = findViewById(R.id.pronoun);
		name = findViewById(R.id.name);
		verify = findViewById(R.id.verify);
		bio = findViewById(R.id.bio);
		date = findViewById(R.id.date);
		flag = findViewById(R.id.flag);
		location = findViewById(R.id.location);
		textview10 = findViewById(R.id.textview10);
		linear14 = findViewById(R.id.linear14);
		listview1 = findViewById(R.id.listview1);
		textview11 = findViewById(R.id.textview11);
		textview12 = findViewById(R.id.textview12);
	}
	
	private void initializeLogic() {
	}
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.posts, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear15 = _view.findViewById(R.id.linear15);
			final LinearLayout linear17 = _view.findViewById(R.id.linear17);
			final androidx.cardview.widget.CardView cardview_image = _view.findViewById(R.id.cardview_image);
			final androidx.cardview.widget.CardView cardview_video = _view.findViewById(R.id.cardview_video);
			final LinearLayout type_chip = _view.findViewById(R.id.type_chip);
			final LinearLayout linear7 = _view.findViewById(R.id.linear7);
			final de.hdodenhof.circleimageview.CircleImageView pfp = _view.findViewById(R.id.pfp);
			final TextView name = _view.findViewById(R.id.name);
			final ImageView verify = _view.findViewById(R.id.verify);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView date = _view.findViewById(R.id.date);
			final TextView title = _view.findViewById(R.id.title);
			final TextView story = _view.findViewById(R.id.story);
			final RelativeLayout linear4 = _view.findViewById(R.id.linear4);
			final ImageView imageview6 = _view.findViewById(R.id.imageview6);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final LinearLayout linear14 = _view.findViewById(R.id.linear14);
			final LinearLayout linear_like = _view.findViewById(R.id.linear_like);
			final LinearLayout linear11 = _view.findViewById(R.id.linear11);
			final LinearLayout linear_comments = _view.findViewById(R.id.linear_comments);
			final LinearLayout linear12 = _view.findViewById(R.id.linear12);
			final LinearLayout linear_share = _view.findViewById(R.id.linear_share);
			final LinearLayout linear13 = _view.findViewById(R.id.linear13);
			final ImageView imageview_like = _view.findViewById(R.id.imageview_like);
			final TextView textview_likes = _view.findViewById(R.id.textview_likes);
			final ImageView imageview_commemts = _view.findViewById(R.id.imageview_commemts);
			final TextView textview_comments = _view.findViewById(R.id.textview_comments);
			final ImageView imageview_share = _view.findViewById(R.id.imageview_share);
			final TextView textview_share = _view.findViewById(R.id.textview_share);
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}