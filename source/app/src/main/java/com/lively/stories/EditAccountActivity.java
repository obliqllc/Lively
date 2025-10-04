package com.lively.stories;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Build;
import android.provider.MediaStore;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
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
import java.io.File;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class EditAccountActivity extends AppCompatActivity {
	
	public final int REQ_CD_FP = 101;
	public final int REQ_CD_C = 102;
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> update_map = new HashMap<>();
	private boolean exceeds = false;
	private String img_url = "";
	private double number = 0;
	private String path = "";
	
	private ScrollView vscroll1;
	private LinearLayout linear3;
	private LinearLayout background;
	private TextView sign_up_txt;
	private LinearLayout main_background;
	private LinearLayout linear1;
	private TextView password_txt;
	private LinearLayout name_linear;
	private LinearLayout linear4;
	private LinearLayout username_linear;
	private TextView email_txt;
	private LinearLayout email_linear;
	private TextView textview1;
	private LinearLayout linear2;
	private LinearLayout sign_up_button;
	private AdView adview1;
	private CircleImageView circleimageview1;
	private EditText name_edittext;
	private TextView username_txt;
	private EditText username_edittext;
	private MultiAutoCompleteTextView multiautocomplete1;
	private TextView edittext1;
	private ImageView imageview1;
	private TextView button_txt;
	
	private TimerTask t;
	private AlertDialog.Builder d;
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
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private Intent c = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	private File _file_c;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.edit_account);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		MobileAds.initialize(this);
		
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);} else {
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
		vscroll1 = findViewById(R.id.vscroll1);
		linear3 = findViewById(R.id.linear3);
		background = findViewById(R.id.background);
		sign_up_txt = findViewById(R.id.sign_up_txt);
		main_background = findViewById(R.id.main_background);
		linear1 = findViewById(R.id.linear1);
		password_txt = findViewById(R.id.password_txt);
		name_linear = findViewById(R.id.name_linear);
		linear4 = findViewById(R.id.linear4);
		username_linear = findViewById(R.id.username_linear);
		email_txt = findViewById(R.id.email_txt);
		email_linear = findViewById(R.id.email_linear);
		textview1 = findViewById(R.id.textview1);
		linear2 = findViewById(R.id.linear2);
		sign_up_button = findViewById(R.id.sign_up_button);
		adview1 = findViewById(R.id.adview1);
		circleimageview1 = findViewById(R.id.circleimageview1);
		name_edittext = findViewById(R.id.name_edittext);
		username_txt = findViewById(R.id.username_txt);
		username_edittext = findViewById(R.id.username_edittext);
		multiautocomplete1 = findViewById(R.id.multiautocomplete1);
		edittext1 = findViewById(R.id.edittext1);
		imageview1 = findViewById(R.id.imageview1);
		button_txt = findViewById(R.id.button_txt);
		d = new AlertDialog.Builder(this);
		fbauth = FirebaseAuth.getInstance();
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		_file_c = FileUtil.createNewPictureFile(getApplicationContext());
		Uri _uri_c;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			_uri_c = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_c);
		} else {
			_uri_c = Uri.fromFile(_file_c);
		}
		c.putExtra(MediaStore.EXTRA_OUTPUT, _uri_c);
		c.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		
		linear2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				// Get all ISO countries
				String[] countries = java.util.Locale.getISOCountries();
				List<String> countryList = new ArrayList<String>();
				
				for (String code : countries) {
					Locale locale = new Locale("", code);
					countryList.add(locale.getDisplayCountry());
				}
				
				// Sort alphabetically
				Collections.sort(countryList);
				
				// Convert to array
				final String[] sortedCountries = countryList.toArray(new String[0]);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(EditAccountActivity.this);
				builder.setTitle("Select Your Country");
				
				builder.setItems(sortedCountries, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						edittext1.setText(sortedCountries[which]);
					}
				});
				
				builder.show();
				
			}
		});
		
		sign_up_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				sign_up_button.setVisibility(View.GONE);
				if (name_edittext.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Name can not be empty!");
				} else {
					if (username_txt.getText().toString().equals("")) {
						SketchwareUtil.showMessage(getApplicationContext(), "Please type in a Bio!");
					} else {
						if (multiautocomplete1.getText().toString().equals("")) {
							SketchwareUtil.showMessage(getApplicationContext(), "Please add your promouns!");
						} else {
							if (exceeds) {
								ObjectAnimator anim = ObjectAnimator.ofFloat(username_edittext, "ScaleY", 0, 1);
								anim.setInterpolator(new BounceInterpolator()); 
								anim.setDuration(1000);
								anim.start();
								SketchwareUtil.showMessage(getApplicationContext(), "Bio cannot be greater than 200 characters!");
							} else {
								if (path.equals("")) {
									_loading_block();
									update_map = new HashMap<>();
									update_map.put("bio", username_edittext.getText().toString().trim());
									update_map.put("gender", multiautocomplete1.getText().toString().trim());
									update_map.put("country", edittext1.getText().toString().trim());
									update_map.put("name", name_edittext.getText().toString().trim());
									t = new TimerTask() {
										@Override
										public void run() {
											runOnUiThread(new Runnable() {
												@Override
												public void run() {
													users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(update_map);
													finish();
												}
											});
										}
									};
									_timer.schedule(t, (int)(2000));
								} else {
									_loading_block();
									ImageUploader.uploadImage(path, new ImageUploader.UploadCallback() {
										@Override
										public void onUploadComplete(String imageUrl) {
											img_url = imageUrl;
											update_map = new HashMap<>();
											update_map.put("pfp", img_url);
											update_map.put("bio", username_edittext.getText().toString().trim());
											update_map.put("gender", multiautocomplete1.getText().toString().trim());
											update_map.put("dob", edittext1.getText().toString().trim());
											update_map.put("name", name_edittext.getText().toString().trim());
											t = new TimerTask() {
												@Override
												public void run() {
													runOnUiThread(new Runnable() {
														@Override
														public void run() {
															users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(update_map);
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
											finish();
										}
									});
									
								}
							}
						}
					}
				}
			}
		});
		
		circleimageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog = new com.google.android.material.bottomsheet.BottomSheetDialog(EditAccountActivity.this);
				
				View bottomSheetView; bottomSheetView = getLayoutInflater().inflate(R.layout.photo_selection_dialog,null );
				bottomSheetDialog.setContentView(bottomSheetView);
				
				bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
				LinearLayout bg = (LinearLayout) bottomSheetView.findViewById(R.id.bg);
				bg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFFFFFFFF));
				TextView txt1 = (TextView) bottomSheetView.findViewById(R.id.txt1);
				txt1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_bold.ttf"), 0);
				TextView txt2 = (TextView) bottomSheetView.findViewById(R.id.txt2);
				txt2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 0);
				LinearLayout camera = (LinearLayout) bottomSheetView.findViewById(R.id.camera);
				camera.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)5, 0xFF212121, 0xFFFFFFFF));
				LinearLayout gallery = (LinearLayout) bottomSheetView.findViewById(R.id.gallery);
				gallery.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)5, 0xFF212121, 0xFFFFFFFF));
				LinearLayout url = (LinearLayout) bottomSheetView.findViewById(R.id.url);
				url.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)5, 0xFF212121, 0xFFFFFFFF));
				camera.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
						startActivityForResult(c, REQ_CD_C);
						bottomSheetDialog.dismiss();
					}
				});
				gallery.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
						startActivityForResult(fp, REQ_CD_FP);
						bottomSheetDialog.dismiss();
					}
				});
				url.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
						SketchwareUtil.showMessage(getApplicationContext(), "Coming Soon...");
					}
				});
				bottomSheetDialog.setCancelable(true);
				bottomSheetDialog.show();
			}
		});
		
		username_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				number = _charSeq.length();
				if (number > 200) {
					exceeds = true;
				} else {
					exceeds = false;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
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
						name_edittext.setText(_childValue.get("name").toString());
						multiautocomplete1.setText(_childValue.get("gender").toString());
						username_edittext.setText(_childValue.get("bio").toString());
						edittext1.setText(_childValue.get("country").toString());
						if (_childValue.get("pfp").toString().equals("def")) {
							circleimageview1.setImageResource(R.drawable.default_pfp);
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("pfp").toString())).into(circleimageview1);
						}
					}catch(Exception e){
						name_edittext.setText(_childValue.get("name").toString());
						multiautocomplete1.setText(_childValue.get("gender").toString());
						username_edittext.setText(_childValue.get("bio").toString());
						edittext1.setText(_childValue.get("country").toString());
						if (_childValue.get("pfp").toString().equals("def")) {
							circleimageview1.setImageResource(R.drawable.default_pfp);
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("pfp").toString())).into(circleimageview1);
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
			final Window window = EditAccountActivity.this.getWindow();
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
		path = "";
		_ui();
		vscroll1.setVerticalScrollBarEnabled(false);
		{
			AdRequest adRequest = new AdRequest.Builder().build();
			adview1.loadAd(adRequest);
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FP:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				path = _filePath.get((int)(0));
				circleimageview1.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(path, 1024, 1024));
			}
			else {
				
			}
			break;
			
			case REQ_CD_C:
			if (_resultCode == Activity.RESULT_OK) {
				String _filePath = _file_c.getAbsolutePath();
				
				path = _filePath;
				circleimageview1.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath, 1024, 1024));
			}
			else {
				
			}
			break;
			default:
			break;
		}
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
	
	
	public void _ui() {
		_set_radius(main_background, "#FFFFFF", 60, 60, 0, 0);
		_set_radius_with_ripple(sign_up_button, "#212121", 30, 0, "#212121", true);
		_set_radius_with_ripple(username_linear, "#EEEEEE", 30, 0, "#212121", false);
		_set_radius_with_ripple(email_linear, "#EEEEEE", 30, 0, "#212121", false);
		_set_radius_with_ripple(linear2, "#EEEEEE", 30, 0, "#212121", false);
		_set_radius_with_ripple(name_linear, "#EEEEEE", 30, 0, "#212121", false);
	}
	
	
	public void _loading_block() {
		final AlertDialog d = new AlertDialog.Builder(EditAccountActivity.this).create();
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