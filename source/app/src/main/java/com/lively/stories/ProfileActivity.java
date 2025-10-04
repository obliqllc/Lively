package com.lively.stories;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
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
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class ProfileActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String pronoun_ = "";
	
	private ScrollView vscroll1;
	private LinearLayout linear11;
	private LinearLayout linear1;
	private ImageView back_img;
	private TextView textview1;
	private LinearLayout linear4;
	private CircleImageView circleimageview1;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout edit_btn;
	private LinearLayout linear12;
	private LinearLayout logout_btn;
	private LinearLayout linear9;
	private AdView adview1;
	private LinearLayout linear10;
	private TextView pronoun;
	private TextView name;
	private ImageView verify;
	private TextView bio;
	private TextView date;
	private TextView uid;
	private ImageView flag;
	private TextView location;
	private ImageView imageview4;
	private TextView textview7;
	private ImageView imageview6;
	private TextView textview9;
	private ImageView imageview5;
	private TextView textview8;
	
	private TimerTask ad_loader;
	private FirebaseAuth fauth;
	private OnCompleteListener<AuthResult> _fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
	private OnCompleteListener<Void> _fauth_reset_password_listener;
	private OnCompleteListener<Void> fauth_updateEmailListener;
	private OnCompleteListener<Void> fauth_updatePasswordListener;
	private OnCompleteListener<Void> fauth_emailVerificationSentListener;
	private OnCompleteListener<Void> fauth_deleteUserListener;
	private OnCompleteListener<Void> fauth_updateProfileListener;
	private OnCompleteListener<AuthResult> fauth_phoneAuthListener;
	private OnCompleteListener<AuthResult> fauth_googleSignInListener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.profile);
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
		textview1 = findViewById(R.id.textview1);
		linear4 = findViewById(R.id.linear4);
		circleimageview1 = findViewById(R.id.circleimageview1);
		linear5 = findViewById(R.id.linear5);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		edit_btn = findViewById(R.id.edit_btn);
		linear12 = findViewById(R.id.linear12);
		logout_btn = findViewById(R.id.logout_btn);
		linear9 = findViewById(R.id.linear9);
		adview1 = findViewById(R.id.adview1);
		linear10 = findViewById(R.id.linear10);
		pronoun = findViewById(R.id.pronoun);
		name = findViewById(R.id.name);
		verify = findViewById(R.id.verify);
		bio = findViewById(R.id.bio);
		date = findViewById(R.id.date);
		uid = findViewById(R.id.uid);
		flag = findViewById(R.id.flag);
		location = findViewById(R.id.location);
		imageview4 = findViewById(R.id.imageview4);
		textview7 = findViewById(R.id.textview7);
		imageview6 = findViewById(R.id.imageview6);
		textview9 = findViewById(R.id.textview9);
		imageview5 = findViewById(R.id.imageview5);
		textview8 = findViewById(R.id.textview8);
		fauth = FirebaseAuth.getInstance();
		
		back_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		edit_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivity(new Intent(ProfileActivity.this, EditAccountActivity.class)); Animatoo.animateSlideLeft(ProfileActivity.this);
			}
		});
		
		logout_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog = new com.google.android.material.bottomsheet.BottomSheetDialog(ProfileActivity.this);
				
				View bottomSheetView; bottomSheetView = getLayoutInflater().inflate(R.layout.logout_confirmatiom,null );
				bottomSheetDialog.setContentView(bottomSheetView);
				
				bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
				LinearLayout bg = (LinearLayout) bottomSheetView.findViewById(R.id.bg);
				bg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFFFFFFFF));
				TextView txt1 = (TextView) bottomSheetView.findViewById(R.id.txt1);
				txt1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_bold.ttf"), 0);
				TextView txt2 = (TextView) bottomSheetView.findViewById(R.id.txt2);
				txt2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 0);
				LinearLayout camera = (LinearLayout) bottomSheetView.findViewById(R.id.camera);
				camera.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)5, 0xFFF44336, 0xFFFFFFFF));
				LinearLayout gallery = (LinearLayout) bottomSheetView.findViewById(R.id.gallery);
				gallery.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)5, 0xFF212121, 0xFFFFFFFF));
				camera.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
						FirebaseAuth.getInstance().signOut();
						startActivity(new Intent(ProfileActivity.this, MainActivity.class)); Animatoo.animateSlideLeft(ProfileActivity.this);
						finish();
					}
				});
				gallery.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
						bottomSheetDialog.dismiss();
					}
				});
				bottomSheetDialog.setCancelable(true);
				bottomSheetDialog.show();
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					try{
						name.setText(_childValue.get("name").toString());
						pronoun_ = _childValue.get("gender").toString();
						pronoun.setText("(".concat(pronoun_.concat(")")));
						bio.setText(_childValue.get("bio").toString());
						date.setText("Date Joined: ".concat(_childValue.get("date_created").toString()));
						location.setText(_childValue.get("country").toString());
						uid.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());
						if (_childValue.get("pfp").toString().equals("def")) {
							circleimageview1.setImageResource(R.drawable.default_pfp);
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("pfp").toString())).into(circleimageview1);
						}
						if (_childValue.get("verified").toString().equals("no")) {
							verify.setVisibility(View.GONE);
						} else {
							
						}
					}catch(Exception e){
						name.setText(_childValue.get("name").toString());
						pronoun_ = _childValue.get("gender").toString();
						pronoun.setText("(".concat(pronoun_.concat(")")));
						bio.setText(_childValue.get("bio").toString());
						date.setText("Date Joined: ".concat(_childValue.get("date_created").toString()));
						location.setText(_childValue.get("country").toString());
						uid.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());
						if (_childValue.get("pfp").toString().equals("def")) {
							circleimageview1.setImageResource(R.drawable.default_pfp);
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("pfp").toString())).into(circleimageview1);
						}
						if (_childValue.get("verified").toString().equals("no")) {
							verify.setVisibility(View.GONE);
						} else {
							
						}
					}
				} else {
					
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					try{
						name.setText(_childValue.get("name").toString());
						pronoun_ = _childValue.get("gender").toString();
						pronoun.setText("(".concat(pronoun_.concat(")")));
						bio.setText(_childValue.get("bio").toString());
						date.setText("Date Joined: ".concat(_childValue.get("date_created").toString()));
						location.setText(_childValue.get("country").toString());
						uid.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());
						if (_childValue.get("pfp").toString().equals("def")) {
							circleimageview1.setImageResource(R.drawable.default_pfp);
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("pfp").toString())).into(circleimageview1);
						}
						if (_childValue.get("verified").toString().equals("no")) {
							verify.setVisibility(View.GONE);
						} else {
							
						}
					}catch(Exception e){
						name.setText(_childValue.get("name").toString());
						pronoun_ = _childValue.get("gender").toString();
						pronoun.setText("(".concat(pronoun_.concat(")")));
						bio.setText(_childValue.get("bio").toString());
						date.setText("Date Joined: ".concat(_childValue.get("date_created").toString()));
						location.setText(_childValue.get("country").toString());
						uid.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());
						if (_childValue.get("pfp").toString().equals("def")) {
							circleimageview1.setImageResource(R.drawable.default_pfp);
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("pfp").toString())).into(circleimageview1);
						}
						if (_childValue.get("verified").toString().equals("no")) {
							verify.setVisibility(View.GONE);
						} else {
							
						}
					}
				} else {
					
				}
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		users.addChildEventListener(_users_child_listener);
		
		fauth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		fauth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_fauth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fauth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			final Window window = ProfileActivity.this.getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(0xFF212121);
		}
		//This block is Coded by CyberAlpha PH
		
		TextviewFonts(this,getWindow().getDecorView());
	}
	private void TextviewFonts(final android.content.Context context, final View v) {
		
		String type = "normal";
		int op = 0;
		
		if (type.equals("normal")) {
			op = 0;
		}
		if (type.equals("bold")) {
			op = 1;
		}
		if (type.equals("italic")) {
			op = 2;
		}
		if (type.equals("bold|italic")) {
			op = 3;
		}
		
		try {
			Typeface typeace = Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf");
			if (v instanceof ViewGroup) 
			{
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0;
				i < vg.getChildCount();
				i++) {
					View child = vg.getChildAt(i);
					TextviewFonts(context, child);
				}
			}
			else {
				if ((v instanceof TextView)) {
					((TextView) v).setTypeface(typeace, (op));
					
					
				}
			}
		}
		catch(Exception e)
		
		{
			SketchwareUtil.showMessage(getApplicationContext(), "Error Loading Font");
		};
		_set_radius(linear4, "#FFFFFF", 60, 60, 0, 0);
		_set_radius_with_ripple(edit_btn, "#212121", 30, 0, "#212121", true);
		_set_radius_with_ripple(linear12, "#212121", 30, 0, "#212121", true);
		_set_radius_with_ripple(logout_btn, "#FFFFFF", 30, 2, "#F44336", true);
		ad_loader = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						{
							AdRequest adRequest = new AdRequest.Builder().build();
							adview1.loadAd(adRequest);
						}
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(ad_loader, (int)(0), (int)(5000));
		uid.setTextIsSelectable(true);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (adview1 != null) {
			adview1.destroy();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (adview1 != null) {
			adview1.pause();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (adview1 != null) {
			adview1.resume();
		}
	}
	public void _set_radius(final View _view, final String _color, final double _left, final double _right, final double _bottom_left, final double _bottom_right) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_color));
		gd.setCornerRadii(new float[] { (int)_left, (int)_left, (int)_right, (int)_right, (int)_bottom_right, (int)_bottom_right, (int)_bottom_left, (int)_bottom_left });
		_view.setBackground(gd);
	}
	
	
	public void _set_radius_with_ripple(final View _view, final String _color, final double _radius, final double _stroke, final String _s_color, final boolean _ripple) {
		if (_ripple) {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			gd.setStroke((int) _stroke, Color.parseColor(_s_color));
			_view.setBackground(gd);
			android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#eeeeee")});
			android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb , gd, null);
			_view.setClickable(true);
			_view.setBackground(ripdrb);
		} else {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			gd.setStroke((int) _stroke, Color.parseColor(_s_color));
			_view.setBackground(gd);
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