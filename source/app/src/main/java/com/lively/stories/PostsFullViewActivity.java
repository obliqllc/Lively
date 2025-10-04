package com.lively.stories;

import android.animation.*;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
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
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class PostsFullViewActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private FloatingActionButton _fab;
	private String key = "";
	private String uid = "";
	
	private LinearLayout background;
	private ImageView back_img;
	private TextView sign_up_txt;
	private ScrollView vscroll1;
	private LinearLayout linear18;
	private LinearLayout main_background;
	private LinearLayout linear22;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear20;
	private LinearLayout linear19;
	private CircleImageView circleimageview1;
	private TextView textview6;
	private ImageView imageview6;
	private ImageView imageview3;
	private ImageView imageview7;
	private HorizontalScrollView hscroll1;
	private LinearLayout linear23;
	private LinearLayout genre_chip;
	private LinearLayout type_chip;
	private LinearLayout copyright;
	private LinearLayout edited_chip;
	private LinearLayout ai_chip;
	private TextView textview5;
	private TextView textview7;
	private ImageView imageview4;
	private TextView textview8;
	private ImageView imageview5;
	private TextView textview10;
	private ImageView imageview2;
	private TextView textview4;
	private ImageView imageview1;
	private TextView textview3;
	
	private DatabaseReference posts = _firebase.getReference("posts");
	private ChildEventListener _posts_child_listener;
	private AlertDialog.Builder ai;
	private TextToSpeech tts;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
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
	private Intent i = new Intent();
	private Intent reader_mode = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.posts_full_view);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		MobileAds.initialize(this);
		
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_fab = findViewById(R.id._fab);
		background = findViewById(R.id.background);
		back_img = findViewById(R.id.back_img);
		sign_up_txt = findViewById(R.id.sign_up_txt);
		vscroll1 = findViewById(R.id.vscroll1);
		linear18 = findViewById(R.id.linear18);
		main_background = findViewById(R.id.main_background);
		linear22 = findViewById(R.id.linear22);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		linear20 = findViewById(R.id.linear20);
		linear19 = findViewById(R.id.linear19);
		circleimageview1 = findViewById(R.id.circleimageview1);
		textview6 = findViewById(R.id.textview6);
		imageview6 = findViewById(R.id.imageview6);
		imageview3 = findViewById(R.id.imageview3);
		imageview7 = findViewById(R.id.imageview7);
		hscroll1 = findViewById(R.id.hscroll1);
		linear23 = findViewById(R.id.linear23);
		genre_chip = findViewById(R.id.genre_chip);
		type_chip = findViewById(R.id.type_chip);
		copyright = findViewById(R.id.copyright);
		edited_chip = findViewById(R.id.edited_chip);
		ai_chip = findViewById(R.id.ai_chip);
		textview5 = findViewById(R.id.textview5);
		textview7 = findViewById(R.id.textview7);
		imageview4 = findViewById(R.id.imageview4);
		textview8 = findViewById(R.id.textview8);
		imageview5 = findViewById(R.id.imageview5);
		textview10 = findViewById(R.id.textview10);
		imageview2 = findViewById(R.id.imageview2);
		textview4 = findViewById(R.id.textview4);
		imageview1 = findViewById(R.id.imageview1);
		textview3 = findViewById(R.id.textview3);
		ai = new AlertDialog.Builder(this);
		tts = new TextToSpeech(getApplicationContext(), null);
		fauth = FirebaseAuth.getInstance();
		
		imageview6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				reader_mode.putExtra("key", key);
				reader_mode.setClass(getApplicationContext(), ReaderModeActivity.class);
				startActivity(reader_mode);
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (tts.isSpeaking()) {
					tts.stop();
				} else {
					tts.setSpeechRate((float)0.7d);
					tts.speak(textview2.getText().toString(), TextToSpeech.QUEUE_ADD, null);
				}
			}
		});
		
		imageview7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				PopupMenu popup = new PopupMenu(PostsFullViewActivity.this, _view);
				popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
				
				// Resize icons
				for (int i = 0; i < popup.getMenu().size(); i++) {
					MenuItem item = popup.getMenu().getItem(i);
					Drawable icon = item.getIcon();
					if (icon != null) {
						icon.setBounds(0, 0, 60, 60); // custom size
						item.setIcon(icon);
					}
				}
				
				// Force icons + set background
				try {
					java.lang.reflect.Field mFieldPopup = popup.getClass().getDeclaredField("mPopup");
					mFieldPopup.setAccessible(true);
					Object mPopup = mFieldPopup.get(popup);
					
					// Force show icons
					mPopup.getClass().getDeclaredMethod("setForceShowIcon", boolean.class).invoke(mPopup, true);
					
					// Set custom background (rounded white)
					android.widget.ListPopupWindow popupWindow =
					(android.widget.ListPopupWindow) mPopup.getClass().getMethod("getPopup").invoke(mPopup);
					popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_bg));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// Handle item clicks
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
							case R.id.report:
							SketchwareUtil.showMessage(getApplicationContext(), "Report clicked");
							return true;
							case R.id.policy:
							SketchwareUtil.showMessage(getApplicationContext(), "Copyright Policy clicked");
							return true;
							default:
							return false;
						}
					}
				});
				
				popup.show();
			}
		});
		
		ai_chip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final AlertDialog ai = new AlertDialog.Builder(PostsFullViewActivity.this).create();
				View inflate = getLayoutInflater().inflate(R.layout.ai_dialog, null);
				ai.setView(inflate);
				ai.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
				final LinearLayout bg = (LinearLayout)
				inflate.findViewById(R.id.bg);
				bg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFFFFFFFF));
				final TextView txt1 = (TextView)
				inflate.findViewById(R.id.txt1);
				txt1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_bold.ttf"), 0);
				final TextView txt2 = (TextView)
				inflate.findViewById(R.id.txt2);
				txt2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 0);
				ai.setCancelable(true);
				ai.show();
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), EditPostActivity.class);
				i.putExtra("key", key);
				startActivity(i);
			}
		});
		
		_posts_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(key)) {
					textview1.setText(_childValue.get("title").toString());
					textview3.setText("Liked by ".concat(_childValue.get("likes").toString().concat(" people!")));
					textview2.setText(_childValue.get("story").toString());
					textview5.setText(_childValue.get("genre").toString());
					if (_childValue.get("ai").toString().equals("yes")) {
						ai_chip.setVisibility(View.VISIBLE);
					} else {
						ai_chip.setVisibility(View.GONE);
					}
					if (_childValue.get("warn").toString().equals("yes")) {
						copyright.setVisibility(View.VISIBLE);
						getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
					} else {
						copyright.setVisibility(View.GONE);
					}
					if (_childValue.containsKey("type")) {
						type_chip.setVisibility(View.VISIBLE);
						textview7.setText(_childValue.get("type").toString());
					} else {
						type_chip.setVisibility(View.GONE);
					}
					if (_childValue.containsKey("edited")) {
						edited_chip.setVisibility(View.VISIBLE);
					} else {
						edited_chip.setVisibility(View.GONE);
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		posts.addChildEventListener(_posts_child_listener);
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(uid)) {
					textview6.setText(_childValue.get("name").toString());
					if (_childValue.get("pfp").toString().equals("def")) {
						circleimageview1.setImageResource(R.drawable.default_pfp);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("pfp").toString())).into(circleimageview1);
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		key = getIntent().getStringExtra("key");
		uid = getIntent().getStringExtra("uid");
		ai_chip.setVisibility(View.GONE);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			final Window window = PostsFullViewActivity.this.getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(0xFF212121);
		}
		_GetLikesCount(key, textview3);
		textview1.setSelected(true);
		textview2.setVerticalScrollBarEnabled(false);
		vscroll1.setVerticalScrollBarEnabled(false);
		hscroll1.setHorizontalScrollBarEnabled(false);
		if (uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
			_fab.show();
		} else {
			_fab.hide();
		}
		_set_radius(vscroll1, "#FFFFFF", 60, 60, 0, 0);
		_set_radius_with_ripple(genre_chip, "#009688", 30, 0, "#ffffff", true);
		_set_radius_with_ripple(ai_chip, "#616161", 30, 0, "#ffffff", true);
		_set_radius_with_ripple(copyright, "#ff0000", 30, 0, "#ffffff", true);
		_set_radius_with_ripple(type_chip, "#3F51B5", 30, 0, "#ffffff", true);
		_set_radius_with_ripple(edited_chip, "#FF5722", 30, 0, "#ffffff", true);
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
	
	
	public void _GetLikesCount(final String _PostID, final TextView _Textview) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("posts").child(_PostID).child("like_uid");
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				long likeCount = snapshot.getChildrenCount();
				_Textview.setText("Liked by ".concat(String.valueOf(likeCount).concat(" people!")));
			}
			
			@Override
			public void onCancelled(DatabaseError error) {}
		});
		
		
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