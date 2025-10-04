package com.lively.stories;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class PfpSelectionAndOthersActivity extends AppCompatActivity {
	
	public final int REQ_CD_FP = 101;
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String img_url = "";
	private String dob = "";
	private String path = "";
	private double text_length = 0;
	private boolean error = false;
	private HashMap<String, Object> data_upload = new HashMap<>();
	
	private ArrayList<String> for_multiautocomplete_textview = new ArrayList<>();
	
	private LinearLayout background;
	private TextView sign_up_txt;
	private LinearLayout main_background;
	private CircleImageView circleimageview1;
	private LinearLayout linear1;
	private LinearLayout username_linear;
	private TextView email_txt;
	private LinearLayout email_linear;
	private TextView password_txt;
	private LinearLayout password_linear;
	private LinearLayout sign_up_button;
	private LinearLayout dot_prog_linear;
	private TextView username_txt;
	private TextView textview1;
	private EditText username_edittext;
	private MultiAutoCompleteTextView multiautocomplete1;
	private TextView pass_edittext;
	private TextView button_txt;
	
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private TimerTask t;
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
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.pfp_selection_and_others);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		MobileAds.initialize(this);
		
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		background = findViewById(R.id.background);
		sign_up_txt = findViewById(R.id.sign_up_txt);
		main_background = findViewById(R.id.main_background);
		circleimageview1 = findViewById(R.id.circleimageview1);
		linear1 = findViewById(R.id.linear1);
		username_linear = findViewById(R.id.username_linear);
		email_txt = findViewById(R.id.email_txt);
		email_linear = findViewById(R.id.email_linear);
		password_txt = findViewById(R.id.password_txt);
		password_linear = findViewById(R.id.password_linear);
		sign_up_button = findViewById(R.id.sign_up_button);
		dot_prog_linear = findViewById(R.id.dot_prog_linear);
		username_txt = findViewById(R.id.username_txt);
		textview1 = findViewById(R.id.textview1);
		username_edittext = findViewById(R.id.username_edittext);
		multiautocomplete1 = findViewById(R.id.multiautocomplete1);
		pass_edittext = findViewById(R.id.pass_edittext);
		button_txt = findViewById(R.id.button_txt);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		fauth = FirebaseAuth.getInstance();
		
		circleimageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fp, REQ_CD_FP);
			}
		});
		
		password_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Calendar c = Calendar.getInstance();
				int y = c.get(Calendar.YEAR);
				int m = c.get(Calendar.MONTH);
				int d = c.get(Calendar.DAY_OF_MONTH);
				
				DatePickerDialog datePickerDialog = new DatePickerDialog(
				PfpSelectionAndOthersActivity.this,   // use ActivityName.this instead of "this"
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int month, int day) {
						pass_edittext.setText(String.format("%02d/%02d/%04d", day, month + 1, year));
					}
				},
				y, m, d
				);
				
				datePickerDialog.show();
			}
		});
		
		sign_up_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (multiautocomplete1.getText().toString().equals("")) {
					multiautocomplete1.setError("Please choose your Pronouns!");
				} else {
					if (pass_edittext.getText().toString().equals("")) {
						pass_edittext.setError("Please select your Date Of Birth (DOB)!");
					} else {
						if (error) {
							ObjectAnimator anim = ObjectAnimator.ofFloat(textview1, "ScaleY", 0, 1);
							anim.setInterpolator(new BounceInterpolator()); 
							anim.setDuration(1000);
							anim.start();
							username_edittext.setError("Biography exceeded the character limit!");
						} else {
							if (path.equals("")) {
								img_url = "def";
								data_upload = new HashMap<>();
								data_upload.put("pfp", img_url);
								data_upload.put("bio", username_edittext.getText().toString());
								data_upload.put("gender", multiautocomplete1.getText().toString());
								data_upload.put("dob", pass_edittext.getText().toString());
								t = new TimerTask() {
									@Override
									public void run() {
										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(data_upload);
												startActivity(new Intent(PfpSelectionAndOthersActivity.this, HomeActivity.class)); Animatoo.animateSlideLeft(PfpSelectionAndOthersActivity.this);
												finish();
											}
										});
									}
								};
								_timer.schedule(t, (int)(2000));
							} else {
								ImageUploader.uploadImage(path, new ImageUploader.UploadCallback() {
									@Override
									public void onUploadComplete(String imageUrl) {
										img_url = imageUrl;
										data_upload = new HashMap<>();
										data_upload.put("pfp", img_url);
										data_upload.put("bio", username_edittext.getText().toString());
										data_upload.put("gender", multiautocomplete1.getText().toString());
										data_upload.put("dob", pass_edittext.getText().toString());
										t = new TimerTask() {
											@Override
											public void run() {
												runOnUiThread(new Runnable() {
													@Override
													public void run() {
														users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(data_upload);
														startActivity(new Intent(PfpSelectionAndOthersActivity.this, HomeActivity.class)); Animatoo.animateSlideLeft(PfpSelectionAndOthersActivity.this);
														finish();
													}
												});
											}
										};
										_timer.schedule(t, (int)(2000));
									}
									
									@Override
									public void onUploadError(String errorMessage) {
										
										
										
										SketchwareUtil.showMessage(getApplicationContext(), "Something Went Wrong! Please try again!");
										img_url = "def";
									}
								});
								
							}
							sign_up_button.setVisibility(View.GONE);
							dot_prog_linear.setVisibility(View.VISIBLE);
						}
					}
				}
			}
		});
		
		_users_child_listener = new ChildEventListener() {
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
		_ui_design();
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			final Window window = PfpSelectionAndOthersActivity.this.getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(0xFF212121);
		}
		img_url = "def";
		dob = "Unknown";
		path = "";
		for_multiautocomplete_textview.add("He");
		for_multiautocomplete_textview.add("His");
		for_multiautocomplete_textview.add("She");
		for_multiautocomplete_textview.add("Her");
		for_multiautocomplete_textview.add("They");
		for_multiautocomplete_textview.add("Their");
		for_multiautocomplete_textview.add("Ze");
		for_multiautocomplete_textview.add("Hir");
		for_multiautocomplete_textview.add("Xe");
		for_multiautocomplete_textview.add("Xyrs");
		multiautocomplete1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, for_multiautocomplete_textview));
		multiautocomplete1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		text_length = 54;
		error = false;
	}
	
	public void _ui_design() {
		sign_up_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_bold.ttf"), 1);
		username_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 1);
		password_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 1);
		username_edittext.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 1);
		pass_edittext.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 1);
		button_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 1);
		multiautocomplete1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 1);
		_set_radius(main_background, "#FFFFFF", 60, 60, 0, 0);
		main_background.setElevation(60f);
		_dotsProgress(dot_prog_linear, "#FFFFFF", "#BDBDBD", 3, 8, 1, 600, 11);
		_set_radius_with_ripple(username_linear, "#EEEEEE", 30, 0, "#000000", false);
		_set_radius_with_ripple(email_linear, "#EEEEEE", 30, 0, "#000000", false);
		_set_radius_with_ripple(password_linear, "#EEEEEE", 30, 0, "#000000", false);
		_set_radius_with_ripple(sign_up_button, "#212121", 30, 0, "#000000", true);
		_set_radius_with_ripple(dot_prog_linear, "#212121", 30, 0, "#000000", false);
		dot_prog_linear.setVisibility(View.GONE);
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
	
	
	public void _ripple(final View _view) {
		
		int[] attrs = new int[]{android.R.attr.selectableItemBackgroundBorderless};
		android.content.res.TypedArray typedArray = this.obtainStyledAttributes(attrs);
		int backgroundResource = typedArray.getResourceId(0, 0); _view.setBackgroundResource(backgroundResource);
		_view.setClickable(true);
	}
	
	
	public void _dotsProgress(final View _view, final String _color1, final String _color2, final double _count, final double _size, final double _scale, final double _growth, final double _spacing) {
		final DilatingDotsProgressBar bar = new DilatingDotsProgressBar(this); 
		bar.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT)); 
		/*dots size here*/
		
		bar.setDotRadius((float)_size);
		/* dots color here*/
		
		bar.setDotColors(Color.parseColor("#" + _color1.replace("#", "")),Color.parseColor("#" + _color2.replace("#", "")));
		/*total dots here*/
		
		bar.setNumberOfDots((int)_count);
		bar.setDotScaleMultiplier((float)_scale);
		/*Set animation speed here*/
		
		bar.setGrowthSpeed((int)_growth);
		/* dot spacing between dots here*/
		
		bar.setDotSpacing((float)_spacing);
		((LinearLayout) _view).addView(bar); 
		bar.showNow();
		/*bar.hideNow();*/
		
	}
	public static class DilatingDotDrawable extends android.graphics.drawable.Drawable {
		private static final String TAG = DilatingDotDrawable.class.getSimpleName();
		private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		private float radius;
		private float maxRadius;
		final Rect mDirtyBounds = new Rect(0, 0, 0, 0);
		
		public DilatingDotDrawable(final int color, final float radius, final float maxRadius) {
			mPaint.setColor(color);
			mPaint.setStyle(Paint.Style.FILL);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStrokeJoin(Paint.Join.ROUND);
			
			this.radius = radius;
			setMaxRadius(maxRadius);
		}
		
		@Override
		public void draw(Canvas canvas) {
			final Rect bounds = getBounds();
			canvas.drawCircle(bounds.centerX(), bounds.centerY(), radius - 1, mPaint);
		}
		
		@Override
		public void setAlpha(int alpha) {
			if (alpha != mPaint.getAlpha()) {
				mPaint.setAlpha(alpha);
				invalidateSelf();
			}
		}
		
		@Override
		public void setColorFilter(ColorFilter colorFilter) {
			mPaint.setColorFilter(colorFilter);
			invalidateSelf();
		}
		
		@Override
		public int getOpacity() {
			return PixelFormat.TRANSLUCENT;
		}
		
		public void setColor(int color) {
			mPaint.setColor(color);
			invalidateSelf();
		}
		
		public void setRadius(float radius) {
			this.radius = radius;
			invalidateSelf();
		}
		
		public float getRadius() {
			return radius;
		}
		
		@Override
		public int getIntrinsicWidth() {
			return (int) (maxRadius * 2) + 2;
		}
		
		@Override
		public int getIntrinsicHeight() {
			return (int) (maxRadius * 2) + 2;
		}
		
		public void setMaxRadius(final float maxRadius) {
			this.maxRadius = maxRadius;
			mDirtyBounds.bottom = (int) (maxRadius * 2) + 2;
			mDirtyBounds.right = (int) (maxRadius * 2) + 2;
		}
		
		@Override
		public Rect getDirtyBounds() {
			return mDirtyBounds;
		}
		
		@Override
		protected void onBoundsChange(final Rect bounds) {
			super.onBoundsChange(bounds);
			mDirtyBounds.offsetTo(bounds.left, bounds.top);
		}
	}
	
	
	public static class DilatingDotsProgressBar extends View {
		public static final String TAG = DilatingDotsProgressBar.class.getSimpleName();
		public static final double START_DELAY_FACTOR = 0.35;
		private static final float DEFAULT_GROWTH_MULTIPLIER = 1.75f;
		private static final int MIN_SHOW_TIME = 500; // ms
		private static final int MIN_DELAY = 500; // ms
		private int mDotColor;
		private int mDotEndColor;
		private int mAnimationDuration;
		private int mWidthBetweenDotCenters;
		private int mNumberDots;
		private float mDotRadius;
		private float mDotScaleMultiplier;
		private float mDotMaxRadius;
		private float mHorizontalSpacing;
		private long mStartTime = -1;
		private boolean mShouldAnimate;
		private boolean mDismissed = false;
		private boolean mIsRunning = false;
		private boolean mAnimateColors = false;
		private ArrayList<DilatingDotDrawable> mDrawables = new ArrayList<>();
		private final List<android.animation.Animator> mAnimations = new ArrayList<>();
		/** delayed runnable to stop the progress */
		private final Runnable mDelayedHide = new Runnable() {
			@Override
			public void run() {
				mStartTime = -1;
				mIsRunning = false;
				setVisibility(View.GONE);
				stopAnimations();
			}
		};
		
		/** delayed runnable to start the progress */
		private final Runnable mDelayedShow = new Runnable() {
			@Override
			public void run() {
				if (!mDismissed) {
					mStartTime = System.currentTimeMillis();
					setVisibility(View.VISIBLE);
					startAnimations();
				}
			}
		};
		
		public DilatingDotsProgressBar(Context context) {
			this(context, null);
		}
		
		public DilatingDotsProgressBar(Context context, AttributeSet attrs) {
			this(context, attrs, 0);
		}
		
		public DilatingDotsProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
			init(attrs);
		}
		
		private void init(AttributeSet attrs) {
			//TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DilatingDotsProgressBar);
			//mNumberDots = a.getInt(R.styleable.DilatingDotsProgressBar_dd_numDots, 3);
			//mDotRadius = a.getDimension(R.styleable.DilatingDotsProgressBar_android_radius, 8);
			//mDotColor = a.getColor(R.styleable.DilatingDotsProgressBar_android_color, 0xff9c27b0);
			//mDotEndColor = a.getColor(R.styleable.DilatingDotsProgressBar_dd_endColor, mDotColor);
			//mDotScaleMultiplier = a.getFloat(R.styleable.DilatingDotsProgressBar_dd_scaleMultiplier, DEFAULT_GROWTH_MULTIPLIER);
			//mAnimationDuration = a.getInt(R.styleable.DilatingDotsProgressBar_dd_animationDuration, 300);
			//mHorizontalSpacing = a.getDimension(R.styleable.DilatingDotsProgressBar_dd_horizontalSpacing, 12);
			//a.recycle();
			
			
			mNumberDots = 3;
			mDotRadius = 8;
			mDotColor = Color.RED;
			mDotEndColor = mDotColor;
			mDotScaleMultiplier = DEFAULT_GROWTH_MULTIPLIER;
			mAnimationDuration = 300;
			mHorizontalSpacing = 12;
			
			
			
			mShouldAnimate = false;
			mAnimateColors = mDotColor != mDotEndColor;
			calculateMaxRadius();
			calculateWidthBetweenDotCenters();
			
			initDots();
			updateDots();
		}
		
		@Override
		protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			if (computeMaxHeight() != h || w != computeMaxWidth()) {
				updateDots();
			}
		}
		
		@Override
		public void onDetachedFromWindow() {
			super.onDetachedFromWindow();
			removeCallbacks();
		}
		
		private void removeCallbacks() {
			removeCallbacks(mDelayedHide);
			removeCallbacks(mDelayedShow);
		}
		
		public void reset() {
			hideNow();
		}
		
		/**
     * Hide the progress view if it is visible. The progress view will not be
     * hidden until it has been shown for at least a minimum show time. If the
     * progress view was not yet visible, cancels showing the progress view.
     */
		@SuppressWarnings ("unused")
		public void hide() {
			hide(MIN_SHOW_TIME);
		}
		
		public void hide(int delay) {
			mDismissed = true;
			removeCallbacks(mDelayedShow);
			long diff = System.currentTimeMillis() - mStartTime;
			if ((diff >= delay) || (mStartTime == -1)) {
				mDelayedHide.run();
			} else {
				if ((delay - diff) <= 0) {
					mDelayedHide.run();
				} else {
					postDelayed(mDelayedHide, delay - diff);
				}
			}
		}
		
		/**
     * Show the progress view after waiting for a minimum delay. If
     * during that time, hide() is called, the view is never made visible.
     */
		@SuppressWarnings ("unused")
		public void show() {
			show(MIN_DELAY);
		}
		
		@SuppressWarnings ("unused")
		public void showNow() {
			show(0);
		}
		
		@SuppressWarnings ("unused")
		public void hideNow() {
			hide(0);
		}
		
		public void show(int delay) {
			if (mIsRunning) {
				return;
			}
			
			mIsRunning = true;
			
			mStartTime = -1;
			mDismissed = false;
			removeCallbacks(mDelayedHide);
			
			if (delay == 0) {
				mDelayedShow.run();
			} else {
				postDelayed(mDelayedShow, delay);
			}
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			if (shouldAnimate()) {
				for (DilatingDotDrawable dot : mDrawables) {
					dot.draw(canvas);
				}
			}
		}
		
		@Override
		protected boolean verifyDrawable(final android.graphics.drawable.Drawable who) {
			if (shouldAnimate()) {
				return mDrawables.contains(who);
			}
			return super.verifyDrawable(who);
		}
		
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			setMeasuredDimension((int) computeMaxWidth(), (int) computeMaxHeight());
		}
		
		private float computeMaxHeight() {
			return mDotMaxRadius * 2;
		}
		
		private float computeMaxWidth() {
			return computeWidth() + ((mDotMaxRadius - mDotRadius) * 2);
		}
		
		private float computeWidth() {
			return (((mDotRadius * 2) + mHorizontalSpacing) * mDrawables.size()) - mHorizontalSpacing;
		}
		
		private void calculateMaxRadius() {
			mDotMaxRadius = mDotRadius * mDotScaleMultiplier;
		}
		
		private void calculateWidthBetweenDotCenters() {
			mWidthBetweenDotCenters = (int) (mDotRadius * 2) + (int) mHorizontalSpacing;
		}
		
		private void initDots() {
			mDrawables.clear();
			mAnimations.clear();
			for (int i = 1; i <= mNumberDots; i++) {
				final DilatingDotDrawable dot = new DilatingDotDrawable(mDotColor, mDotRadius, mDotMaxRadius);
				dot.setCallback(this);
				mDrawables.add(dot);
				
				final long startDelay = (i - 1) * (int) (START_DELAY_FACTOR * mAnimationDuration);
				
				// Sizing
				android.animation.ValueAnimator growAnimator = android.animation.ObjectAnimator.ofFloat(dot, "radius", mDotRadius, mDotMaxRadius, mDotRadius);
				growAnimator.setDuration(mAnimationDuration);
				growAnimator.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
				if (i == mNumberDots) {
					growAnimator.addListener(new android.animation.AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(android.animation.Animator animation) {
							if (shouldAnimate()) {
								startAnimations();
							}
						}
					});
				}
				growAnimator.setStartDelay(startDelay);
				
				mAnimations.add(growAnimator);
				
				if (mAnimateColors) {
					// Gradient
					android.animation.ValueAnimator colorAnimator = android.animation.ValueAnimator.ofInt(mDotEndColor, mDotColor);
					colorAnimator.setDuration(mAnimationDuration);
					colorAnimator.setEvaluator(new android.animation.ArgbEvaluator());
					colorAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
						
						@Override
						public void onAnimationUpdate(android.animation.ValueAnimator animator) {
							dot.setColor((int) animator.getAnimatedValue());
						}
						
					});
					if (i == mNumberDots) {
						colorAnimator.addListener(new android.animation.AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(android.animation.Animator animation) {
								if (shouldAnimate()) {
									startAnimations();
								}
							}
						});
					}
					colorAnimator.setStartDelay(startDelay);
					
					mAnimations.add(colorAnimator);
				}
			}
		}
		
		private void updateDots() {
			if (mDotRadius <= 0) {
				mDotRadius = getHeight() / 2 / mDotScaleMultiplier;
			}
			
			int left = (int) (mDotMaxRadius - mDotRadius);
			int right = (int) (left + mDotRadius * 2) + 2;
			int top = 0;
			int bottom = (int) (mDotMaxRadius * 2) + 2;
			
			for (int i = 0; i < mDrawables.size(); i++) {
				final DilatingDotDrawable dot = mDrawables.get(i);
				dot.setRadius(mDotRadius);
				dot.setBounds(left, top, right, bottom);
				android.animation.ValueAnimator growAnimator = (android.animation.ValueAnimator) mAnimations.get(i);
				growAnimator.setFloatValues(mDotRadius, mDotRadius * mDotScaleMultiplier, mDotRadius);
				
				left += mWidthBetweenDotCenters;
				right += mWidthBetweenDotCenters;
			}
		}
		
		protected void startAnimations() {
			mShouldAnimate = true;
			for (android.animation.Animator anim : mAnimations) {
				anim.start();
			}
		}
		
		protected void stopAnimations() {
			mShouldAnimate = false;
			removeCallbacks();
			for (android.animation.Animator anim : mAnimations) {
				anim.cancel();
			}
		}
		
		protected boolean shouldAnimate() {
			return mShouldAnimate;
		}
		
		// -------------------------------
		// ------ Getters & Setters ------
		// -------------------------------
		
		public void setDotRadius(float radius) {
			reset();
			mDotRadius = radius;
			calculateMaxRadius();
			calculateWidthBetweenDotCenters();
			setupDots();
		}
		
		public void setDotSpacing(float horizontalSpacing) {
			reset();
			mHorizontalSpacing = horizontalSpacing;
			calculateWidthBetweenDotCenters();
			setupDots();
		}
		
		public void setGrowthSpeed(int growthSpeed) {
			reset();
			mAnimationDuration = growthSpeed;
			setupDots();
		}
		
		public void setNumberOfDots(int numDots) {
			reset();
			mNumberDots = numDots;
			setupDots();
		}
		
		public void setDotScaleMultiplier(float multiplier) {
			reset();
			mDotScaleMultiplier = multiplier;
			calculateMaxRadius();
			setupDots();
		}
		
		public void setDotColor(int color) {
			if (color != mDotColor) {
				if (mAnimateColors) {
					// Cancel previous animations
					reset();
					mDotColor = color;
					mDotEndColor = color;
					mAnimateColors = false;
					
					setupDots();
					
				} else {
					mDotColor = color;
					for (DilatingDotDrawable dot : mDrawables) {
						dot.setColor(mDotColor);
					}
				}
			}
		}
		
		/**
     * Set different start and end colors for dots. This will result in gradient behaviour. In case you want set 1 solid
     * color - use {@link #setDotColor(int)} instead
     *
     * @param startColor starting color of the dot
     * @param endColor   ending color of the dot
     */
		public void setDotColors(int startColor, int endColor) {
			if (mDotColor != startColor || mDotEndColor != endColor) {
				if (mAnimateColors) {
					reset();
				}
				mDotColor = startColor;
				mDotEndColor = endColor;
				
				mAnimateColors = mDotColor != mDotEndColor;
				
				setupDots();
			}
		}
		
		private void setupDots() {
			initDots();
			updateDots();
			showNow();
		}
		
		public int getDotGrowthSpeed() {
			return mAnimationDuration;
		}
		
		public float getDotRadius() {
			return mDotRadius;
		}
		
		public float getHorizontalSpacing() {
			return mHorizontalSpacing;
		}
		
		public int getNumberOfDots() {
			return mNumberDots;
		}
		
		public float getDotScaleMultiplier() {
			return mDotScaleMultiplier;
		}
	}
	{
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