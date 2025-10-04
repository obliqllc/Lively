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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class AboutUsActivity extends AppCompatActivity {
	
	private LinearLayout background;
	private ImageView back_img;
	private TextView sign_up_txt;
	private ScrollView vscroll1;
	private LinearLayout linear18;
	private LinearLayout main_background;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear24;
	private LinearLayout linear20;
	private TextView textview11;
	private HorizontalScrollView hscroll1;
	private LinearLayout linear23;
	private LinearLayout genre_chip;
	private LinearLayout type_chip;
	private LinearLayout ai_chip;
	private LinearLayout copyright;
	private LinearLayout edited_chip;
	private TextView textview5;
	private TextView textview7;
	private TextView textview4;
	private TextView textview8;
	private TextView textview10;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.about_us);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		background = findViewById(R.id.background);
		back_img = findViewById(R.id.back_img);
		sign_up_txt = findViewById(R.id.sign_up_txt);
		vscroll1 = findViewById(R.id.vscroll1);
		linear18 = findViewById(R.id.linear18);
		main_background = findViewById(R.id.main_background);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		linear24 = findViewById(R.id.linear24);
		linear20 = findViewById(R.id.linear20);
		textview11 = findViewById(R.id.textview11);
		hscroll1 = findViewById(R.id.hscroll1);
		linear23 = findViewById(R.id.linear23);
		genre_chip = findViewById(R.id.genre_chip);
		type_chip = findViewById(R.id.type_chip);
		ai_chip = findViewById(R.id.ai_chip);
		copyright = findViewById(R.id.copyright);
		edited_chip = findViewById(R.id.edited_chip);
		textview5 = findViewById(R.id.textview5);
		textview7 = findViewById(R.id.textview7);
		textview4 = findViewById(R.id.textview4);
		textview8 = findViewById(R.id.textview8);
		textview10 = findViewById(R.id.textview10);
	}
	
	private void initializeLogic() {
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