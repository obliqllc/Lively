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
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.io.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class PostActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> upload = new HashMap<>();
	private String key = "";
	private double posts_count = 0;
	
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
	private TextView textview2;
	private LinearLayout linear2;
	private TextView textview6;
	private LinearLayout linear7;
	private TextView textview4;
	private LinearLayout linear4;
	private LinearLayout sign_up_button;
	private EditText name_edittext;
	private TextView username_txt;
	private TextView textview3;
	private EditText username_edittext;
	private TextView pass_edittext;
	private TextView edittext1;
	private CheckBox checkbox1;
	private CheckBox checkbox3;
	private CheckBox checkbox2;
	private TextView button_txt;
	
	private Calendar cal = Calendar.getInstance();
	private TimerTask t;
	private FirebaseAuth fbauth;
	private OnCompleteListener<AuthResult> _fbauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fbauth_sign_in_listener;
	private OnCompleteListener<Void> _fbauth_reset_password_listener;
	private OnCompleteListener<Void> fbauth_updateEmailListener;
	private OnCompleteListener<Void> fbauth_updatePasswordListener;
	private OnCompleteListener<Void> fbauth_emailVerificationSentListener;
	private OnCompleteListener<Void> fbauth_deleteUserListener;
	private OnCompleteListener<Void> fbauth_updateProfileListener;
	private OnCompleteListener<AuthResult> fbauth_phoneAuthListener;
	private OnCompleteListener<AuthResult> fbauth_googleSignInListener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private DatabaseReference posts = _firebase.getReference("posts");
	private ChildEventListener _posts_child_listener;
	private DatabaseReference forum = _firebase.getReference("forum");
	private ChildEventListener _forum_child_listener;
	private DatabaseReference verify = _firebase.getReference("verify");
	private ChildEventListener _verify_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.post);
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
		textview2 = findViewById(R.id.textview2);
		linear2 = findViewById(R.id.linear2);
		textview6 = findViewById(R.id.textview6);
		linear7 = findViewById(R.id.linear7);
		textview4 = findViewById(R.id.textview4);
		linear4 = findViewById(R.id.linear4);
		sign_up_button = findViewById(R.id.sign_up_button);
		name_edittext = findViewById(R.id.name_edittext);
		username_txt = findViewById(R.id.username_txt);
		textview3 = findViewById(R.id.textview3);
		username_edittext = findViewById(R.id.username_edittext);
		pass_edittext = findViewById(R.id.pass_edittext);
		edittext1 = findViewById(R.id.edittext1);
		checkbox1 = findViewById(R.id.checkbox1);
		checkbox3 = findViewById(R.id.checkbox3);
		checkbox2 = findViewById(R.id.checkbox2);
		button_txt = findViewById(R.id.button_txt);
		fbauth = FirebaseAuth.getInstance();
		
		back_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		password_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals("eOcgUBpsSENy3GClrxejoC0w4mX2")) {
					// Example genres list
					final String[] genres = {
						"Action",
						"Adventure",
						"Announcement",
						"Comedy",
						"Drama",
						"Fantasy",
						"Fiction",
						"Funny",
						"Historical",
						"Horror",
						"Mystery",
						"Romance",
						"Sci-Fi",
						"Thriller",
						"Young Adult"
					};
					
					// Show dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
					builder.setTitle("Select Story Genre");
					
					builder.setItems(genres, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							pass_edittext.setText(genres[which]); // replace edittext1 with your TextView/EditText
						}
					});
					
					builder.show();
					
				} else {
					// Example genres list
					final String[] genres = {
						"Action",
						"Adventure",
						"Comedy",
						"Drama",
						"Fantasy",
						"Fiction",
						"Funny",
						"Historical",
						"Horror",
						"Mystery",
						"Romance",
						"Sci-Fi",
						"Thriller",
						"Young Adult"
					};
					
					// Show dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
					builder.setTitle("Select Story Genre");
					
					builder.setItems(genres, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							pass_edittext.setText(genres[which]); // replace edittext1 with your TextView/EditText
						}
					});
					
					builder.show();
					
				}
			}
		});
		
		linear5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				// Example genres list
				final String[] types = {
					"Story",
					"Poem",
					"Life Advice",
					"Shayari"
				};
				
				// Show dialog
				AlertDialog.Builder builder2 = new AlertDialog.Builder(PostActivity.this);
				builder2.setTitle("Select Story Type");
				
				builder2.setItems(types, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which2) {
						edittext1.setText(types[which2]); // replace edittext1 with your TextView/EditText
					}
				});
				
				builder2.show();
				
			}
		});
		
		sign_up_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (name_edittext.getText().toString().equals("")) {
					name_edittext.setError("Please enter an appropriate title!");
				} else {
					if (username_edittext.getText().toString().equals("")) {
						username_edittext.setError("Please write your story here!");
					} else {
						if (pass_edittext.getText().toString().equals("")) {
							SketchwareUtil.showMessage(getApplicationContext(), "A genre is required!");
						} else {
							if (edittext1.getText().toString().equals("")) {
								SketchwareUtil.showMessage(getApplicationContext(), "A Compose Type is required!");
							} else {
								if (checkbox2.isChecked()) {
									cal = Calendar.getInstance();
									key = posts.push().getKey();
									upload = new HashMap<>();
									upload.put("title", name_edittext.getText().toString().trim());
									upload.put("story", username_edittext.getText().toString().trim());
									upload.put("date", new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
									upload.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
									upload.put("genre", pass_edittext.getText().toString());
									upload.put("ai", "no");
									if (checkbox3.isChecked()) {
										upload.put("nsfw", "yes");
									}
									upload.put("key", key);
									upload.put("like_uid", "");
									upload.put("likes", (int)(0));
									upload.put("type", edittext1.getText().toString());
									upload.put("warn", "yes");
									_loading_block();
									t = new TimerTask() {
										@Override
										public void run() {
											runOnUiThread(new Runnable() {
												@Override
												public void run() {
													verify.child(key).updateChildren(upload);
													SketchwareUtil.showMessage(getApplicationContext(), "We have successfully sent the compose for verification!");
													finish();
												}
											});
										}
									};
									_timer.schedule(t, (int)(SketchwareUtil.getRandom((int)(4000), (int)(8000))));
								} else {
									if (checkbox2.isChecked()) {
										cal = Calendar.getInstance();
										key = posts.push().getKey();
										upload = new HashMap<>();
										upload.put("title", name_edittext.getText().toString().trim());
										upload.put("story", username_edittext.getText().toString().trim());
										upload.put("date", new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
										upload.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
										upload.put("genre", pass_edittext.getText().toString());
										upload.put("ai", "no");
										upload.put("key", key);
										upload.put("like_uid", "");
										upload.put("likes", (int)(0));
										upload.put("type", edittext1.getText().toString());
										upload.put("warn", "yes");
										_loading_block();
										t = new TimerTask() {
											@Override
											public void run() {
												runOnUiThread(new Runnable() {
													@Override
													public void run() {
														verify.child(key).updateChildren(upload);
														SketchwareUtil.showMessage(getApplicationContext(), "We have successfully sent the compose for verification!");
														finish();
													}
												});
											}
										};
										_timer.schedule(t, (int)(SketchwareUtil.getRandom((int)(4000), (int)(8000))));
									} else {
										cal = Calendar.getInstance();
										key = posts.push().getKey();
										upload = new HashMap<>();
										upload.put("title", name_edittext.getText().toString().trim());
										upload.put("story", username_edittext.getText().toString().trim());
										upload.put("date", new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
										upload.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
										upload.put("genre", pass_edittext.getText().toString());
										if (checkbox1.isChecked()) {
											upload.put("ai", "yes");
										} else {
											upload.put("ai", "no");
										}
										upload.put("key", key);
										upload.put("like_uid", "");
										upload.put("likes", (int)(0));
										upload.put("type", edittext1.getText().toString());
										upload.put("warn", "no");
										_loading_block();
										t = new TimerTask() {
											@Override
											public void run() {
												runOnUiThread(new Runnable() {
													@Override
													public void run() {
														posts.child(key).updateChildren(upload);
														finish();
													}
												});
											}
										};
										_timer.schedule(t, (int)(SketchwareUtil.getRandom((int)(4000), (int)(8000))));
									}
								}
							}
						}
					}
				}
			}
		});
		
		username_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				textview3.setText(String.valueOf((long)(_charSeq.length())).concat(" Characters"));
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					textview2.setVisibility(View.GONE);
					linear2.setVisibility(View.GONE);
				} else {
					textview2.setVisibility(View.VISIBLE);
					linear2.setVisibility(View.VISIBLE);
				}
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
						posts_count = Integer.parseInt(_childValue.get("posts_count").toString());
					}catch(Exception e){
						posts_count = Integer.parseInt(_childValue.get("posts_count").toString());
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
		
		_posts_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		
		_forum_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		forum.addChildEventListener(_forum_child_listener);
		
		_verify_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		verify.addChildEventListener(_verify_child_listener);
		
		fbauth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fbauth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fbauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fbauth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fbauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		fbauth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fbauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_fbauth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fbauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fbauth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			final Window window = PostActivity.this.getWindow();
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
		_set_radius(main_background, "#FFFFFF", 60, 60, 0, 0);
		_set_radius_with_ripple(sign_up_button, "#212121", 30, 0, "#212121", true);
		_set_radius_with_ripple(story_linear, "#EEEEEE", 30, 0, "#212121", false);
		_set_radius_with_ripple(story_text_linear, "#EEEEEE", 30, 0, "#212121", false);
		_set_radius_with_ripple(password_linear, "#EEEEEE", 30, 0, "#212121", false);
		_set_radius_with_ripple(linear5, "#EEEEEE", 30, 0, "#212121", false);
		vscroll1.setVerticalScrollBarEnabled(false);
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
	
	
	public void _set_radius(final View _view, final String _color, final double _left, final double _right, final double _bottom_left, final double _bottom_right) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_color));
		gd.setCornerRadii(new float[] { (int)_left, (int)_left, (int)_right, (int)_right, (int)_bottom_right, (int)_bottom_right, (int)_bottom_left, (int)_bottom_left });
		_view.setBackground(gd);
	}
	
	
	public void _loading_block() {
		final AlertDialog d = new AlertDialog.Builder(PostActivity.this).create();
		View inflate = getLayoutInflater().inflate(R.layout.loading, null);
		d.setView(inflate);
		d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		final LinearLayout bg = (LinearLayout)
		inflate.findViewById(R.id.bg);
		bg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFFFFFFFF));
		d.setCancelable(false);
		d.show();
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