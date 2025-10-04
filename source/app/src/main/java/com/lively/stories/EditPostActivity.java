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
import android.widget.EditText;
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

public class EditPostActivity extends AppCompatActivity {
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout background;
	private ImageView back_img;
	private TextView sign_up_txt;
	private LinearLayout main_background;
	private TextView password_txt;
	private LinearLayout story_linear;
	private LinearLayout linear3;
	private LinearLayout story_text_linear;
	private TextView textview1;
	private LinearLayout password_linear;
	private TextView textview5;
	private LinearLayout linear5;
	private LinearLayout sign_up_button;
	private EditText name_edittext;
	private TextView username_txt;
	private TextView textview3;
	private EditText username_edittext;
	private TextView pass_edittext;
	private TextView edittext1;
	private TextView button_txt;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.edit_post);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		background = findViewById(R.id.background);
		back_img = findViewById(R.id.back_img);
		sign_up_txt = findViewById(R.id.sign_up_txt);
		main_background = findViewById(R.id.main_background);
		password_txt = findViewById(R.id.password_txt);
		story_linear = findViewById(R.id.story_linear);
		linear3 = findViewById(R.id.linear3);
		story_text_linear = findViewById(R.id.story_text_linear);
		textview1 = findViewById(R.id.textview1);
		password_linear = findViewById(R.id.password_linear);
		textview5 = findViewById(R.id.textview5);
		linear5 = findViewById(R.id.linear5);
		sign_up_button = findViewById(R.id.sign_up_button);
		name_edittext = findViewById(R.id.name_edittext);
		username_txt = findViewById(R.id.username_txt);
		textview3 = findViewById(R.id.textview3);
		username_edittext = findViewById(R.id.username_edittext);
		pass_edittext = findViewById(R.id.pass_edittext);
		edittext1 = findViewById(R.id.edittext1);
		button_txt = findViewById(R.id.button_txt);
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