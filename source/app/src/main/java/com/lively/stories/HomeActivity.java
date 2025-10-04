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
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import android.provider.Settings;
import android.Manifest;
import android.net.Uri;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public class HomeActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private String _ad_unit_id;
	
	private FloatingActionButton _fab;
	private String shareURL = "";
	private String noti = "";
	private String username_ = "";
	private String name = "";
	private String pfp_url = "";
	private boolean verified = false;
	private String url = "";
	
	private ArrayList<HashMap<String, Object>> tu = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private SwipeRefreshLayout swiperefreshlayout1;
	private AdView adview1;
	private TextView textview1;
	private ImageView ai;
	private CircleImageView circleimageview1;
	private ListView listview1;
	
	private TimerTask ad_changer;
	private InterstitialAd iad;
	private InterstitialAdLoadCallback _iad_interstitial_ad_load_callback;
	private FullScreenContentCallback _iad_full_screen_content_callback;
	private DatabaseReference posts = _firebase.getReference("posts");
	private ChildEventListener _posts_child_listener;
	private Intent screen = new Intent();
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
	private AlertDialog.Builder d;
	private Intent link = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		MobileAds.initialize(this);
		_ad_unit_id = "ca-app-pub-5617050185794481/4718484290";
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_fab = findViewById(R.id._fab);
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		swiperefreshlayout1 = findViewById(R.id.swiperefreshlayout1);
		adview1 = findViewById(R.id.adview1);
		textview1 = findViewById(R.id.textview1);
		ai = findViewById(R.id.ai);
		circleimageview1 = findViewById(R.id.circleimageview1);
		listview1 = findViewById(R.id.listview1);
		fauth = FirebaseAuth.getInstance();
		d = new AlertDialog.Builder(this);
		
		swiperefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				posts.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						tu = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								tu.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						Collections.reverse(tu);
						listview1.setAdapter(new Listview1Adapter(tu));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
						swiperefreshlayout1.setRefreshing(false);
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
		});
		
		circleimageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (textview1.getText().toString().equals("Loading...")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Loading your information...");
				} else {
					final AlertDialog d = new AlertDialog.Builder(HomeActivity.this).create();
					View inflate = getLayoutInflater().inflate(R.layout.dialog, null);
					d.setView(inflate);
					d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
					final LinearLayout bg = (LinearLayout)
					inflate.findViewById(R.id.bg);
					bg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFFFFFFFF));
					final LinearLayout account = (LinearLayout)
					inflate.findViewById(R.id.account);
					final LinearLayout composer = (LinearLayout)
					inflate.findViewById(R.id.composer);
					final LinearLayout linear8 = (LinearLayout)
					inflate.findViewById(R.id.linear8);
					if (verified) {
						composer.setVisibility(View.GONE);
					} else {
						composer.setVisibility(View.VISIBLE);
					}
					final ImageView pfp_ = (ImageView)
					inflate.findViewById(R.id.pfp_);
					if (pfp_url.equals("def")) {
						pfp_.setImageResource(R.drawable.default_pfp);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(pfp_url)).into(pfp_);
					}
					final TextView txt1 = (TextView)
					inflate.findViewById(R.id.txt1);
					txt1.setText(name);
					final TextView txt2 = (TextView)
					inflate.findViewById(R.id.txt2);
					txt2.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
					account.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
							startActivity(new Intent(HomeActivity.this, ProfileActivity.class)); Animatoo.animateSlideLeft(HomeActivity.this);
							d.dismiss();
						}
					});
					composer.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
							d.dismiss();
							url = "https://forms.gle/jLLFXhF5sUw47hRB8";
							link.setAction(Intent.ACTION_VIEW);
							link.setData(Uri.parse(url));
							startActivity(link);
						}
					});
					linear8.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
							startActivity(new Intent(HomeActivity.this, AboutUsActivity.class)); Animatoo.animateSlideLeft(HomeActivity.this);
						}
					});
					d.setCancelable(true);
					d.show();
				}
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				screen.setClass(getApplicationContext(), PostActivity.class);
				startActivity(screen);
			}
		});
		
		_posts_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				tu.add(_childValue);
				listview1.setAdapter(new Listview1Adapter(tu));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				swiperefreshlayout1.setRefreshing(false);
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
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					try{
						if (_childValue.get("pfp").toString().equals("def")) {
							circleimageview1.setImageResource(R.drawable.default_pfp);
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("pfp").toString())).into(circleimageview1);
						}
						pfp_url = _childValue.get("pfp").toString();
						if (_childValue.get("name").toString().length() < 8) {
							textview1.setText("Welcome, ".concat(_childValue.get("name").toString().concat("!")));
						} else {
							textview1.setText("Welcome, ".concat(_childValue.get("name").toString().substring((int)(0), (int)(6)).concat("...").concat("!")));
						}
						name = _childValue.get("name").toString();
						if (!_childValue.get("verified").toString().equals("no")) {
							_fab.show();
							verified = true;
						}
					}catch(Exception e){
						startActivity(new Intent(HomeActivity.this, PfpSelectionAndOthersActivity.class)); Animatoo.animateSlideLeft(HomeActivity.this);
						finish();
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					pfp_url = _childValue.get("pfp").toString();
					if (_childValue.get("pfp").toString().equals("def")) {
						circleimageview1.setImageResource(R.drawable.default_pfp);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("pfp").toString())).into(circleimageview1);
					}
					name = _childValue.get("name").toString();
					if (_childValue.get("name").toString().length() < 8) {
						textview1.setText("Welcome, ".concat(_childValue.get("name").toString().concat("!")));
					} else {
						textview1.setText("Welcome, ".concat(_childValue.get("name").toString().substring((int)(0), (int)(6)).concat("...").concat("!")));
					}
					if (!_childValue.get("verified").toString().equals("no")) {
						_fab.show();
					}
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
		
		_iad_interstitial_ad_load_callback = new InterstitialAdLoadCallback() {
			@Override
			public void onAdLoaded(InterstitialAd _param1) {
				
			}
			
			@Override
			public void onAdFailedToLoad(LoadAdError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		
		_iad_full_screen_content_callback = new FullScreenContentCallback() {
			@Override
			public void onAdDismissedFullScreenContent() {
				
			}
			
			@Override
			public void onAdFailedToShowFullScreenContent(AdError _adError) {
				final int _errorCode = _adError.getCode();
				final String _errorMessage = _adError.getMessage();
				
			}
			
			@Override
			public void onAdShowedFullScreenContent() {
				
			}
		};
		
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
		linear2.setElevation((float)5);
		swiperefreshlayout1.setRefreshing(true);
		linear2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFFF5F5F5));
		ad_changer = new TimerTask() {
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
		_timer.scheduleAtFixedRate(ad_changer, (int)(0), (int)(5000));
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
		_fab.hide();
		listview1.setVerticalScrollBarEnabled(false);
		{
			AdRequest adRequest = new AdRequest.Builder().build();
			InterstitialAd.load(HomeActivity.this, _ad_unit_id, adRequest, _iad_interstitial_ad_load_callback);
		}
		ai.setColorFilter(0xFF212121, PorterDuff.Mode.MULTIPLY);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
		
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
	public void _LikePost(final String _PostID) {
		FirebaseDatabase.getInstance().getReference("posts").child(_PostID).child("like_uid").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(true);
	}
	
	
	public void _UnlikePost(final String _PostID) {
		FirebaseDatabase.getInstance().getReference("posts").child(_PostID).child("like_uid").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
	}
	
	
	public void _IsPostLiked(final String _PostID, final ImageView _SetImage) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("posts").child(_PostID).child("like_uid");
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					_SetImage.setImageResource(R.drawable.icon_thumb_up_round); 
				} else {
					_SetImage.setImageResource(R.drawable.icon_thumb_up_outline); // 🤍
				}
			}
			
			@Override
			public void onCancelled(DatabaseError error) {}
		});
		
	}
	
	
	public void _GetLikesCount(final String _PostID, final TextView _Textview) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("posts").child(_PostID).child("like_uid");
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				long likeCount = snapshot.getChildrenCount();
				_Textview.setText(String.  valueOf(likeCount));
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {}
		});
		
		
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
			
			if (_data.get((int)_position).containsKey("uid")) {
				DatabaseReference getUserReference = FirebaseDatabase.getInstance()
				.getReference("users")
				.child(_data.get(_position).get("uid").toString());
				
				getUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						if (dataSnapshot.exists()) {
							String pfpUrl = dataSnapshot.child("pfp").getValue(String.class);
							String userName = dataSnapshot.child("name").getValue(String.class);
							username_ = userName;
							String verifie = dataSnapshot.child("verified").getValue(String.class);
							String token = dataSnapshot.child("token").getValue(String.class);
							noti = token;
							
							if (pfpUrl != null && pfpUrl.equals("def")) {
								pfp.setImageResource(R.drawable.default_pfp);
							} else if (pfpUrl != null) {
								Glide.with(getApplicationContext())
								.load(Uri.parse(pfpUrl))
								.into(pfp);
							}
							
							if (userName != null) {
								name.setText(userName);
							}
							
							if (verifie != "no"){
								verify.setVisibility(View.VISIBLE);
							}
							else{
								verify.setVisibility(View.GONE);
							}
						}
					}
					
					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {
						
					}
				});
				
				title.setText(_data.get((int)_position).get("title").toString());
				date.setText(_data.get((int)_position).get("date").toString());
				try{
					story.setText(_data.get((int)_position).get("story").toString().substring((int)(0), (int)(150)).concat(" ...Read More"));
					String map = story.getText().toString().replace("...Read More", "<font color=\"".concat("#616161".concat("\">".concat("...Read More".concat("</font>")))));
					story.setText(Html.fromHtml(map));
				}catch(Exception e){
					story.setText(_data.get((int)_position).get("story").toString());
				}
				story.setBackgroundColor(0xFFFAFAFA);
				_GetLikesCount(_data.get((int)_position).get("key").toString(), textview_likes);
				_IsPostLiked(_data.get((int)_position).get("key").toString(), imageview_like);
				if (_data.get((int)_position).get("type").toString().equals("Story")) {
					textview1.setText(_data.get((int)_position).get("type").toString());
					_set_radius_with_ripple(type_chip, "#E91E63", 50, 0, "#FFFFFF", true);
				} else {
					if (_data.get((int)_position).get("type").toString().equals("Poem")) {
						textview1.setText(_data.get((int)_position).get("type").toString());
						_set_radius_with_ripple(type_chip, "#2196F3", 50, 0, "#FFFFFF", true);
					} else {
						if (_data.get((int)_position).get("type").toString().equals("Life Advice")) {
							textview1.setText(_data.get((int)_position).get("type").toString());
							_set_radius_with_ripple(type_chip, "#FF5722", 50, 0, "#FFFFFF", true);
						} else {
							if (_data.get((int)_position).get("type").toString().equals("Shayari")) {
								textview1.setText(_data.get((int)_position).get("type").toString());
								_set_radius_with_ripple(type_chip, "#1B5E20", 50, 0, "#FFFFFF", true);
							} else {
								textview1.setText("Error!");
								_set_radius_with_ripple(type_chip, "#FF0000", 50, 0, "#FFFFFF", true);
							}
						}
					}
				}
				linear_like.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						DatabaseReference ref = FirebaseDatabase.getInstance().getReference("posts").child(_data.get((int)_position).get("key").toString()).child("like_uid");
						ref.addListenerForSingleValueEvent(new ValueEventListener() {
							@Override
							public void onDataChange(DataSnapshot snapshot) {
								if (snapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
									_UnlikePost(_data.get((int)_position).get("key").toString());
								} else {
									_LikePost(_data.get((int)_position).get("key").toString());
								}
							}
							
							@Override
							public void onCancelled(DatabaseError error) {
							}
						});
					}
				});
				linear2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
							startActivity(new Intent(HomeActivity.this, ProfileActivity.class)); Animatoo.animateSlideLeft(HomeActivity.this);
						} else {
							screen.setClass(getApplicationContext(), ProfileOthersActivity.class);
							screen.putExtra("uid", _data.get((int)_position).get("uid").toString());
							startActivity(screen);
						}
					}
				});
				linear1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						screen.setClass(getApplicationContext(), PostsFullViewActivity.class);
						screen.putExtra("key", _data.get((int)_position).get("key").toString());
						screen.putExtra("uid", _data.get((int)_position).get("uid").toString());
						startActivity(screen);
					}
				});
				linear_comments.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						screen.setClass(getApplicationContext(), ForumPageActivity.class);
						screen.putExtra("key", _data.get((int)_position).get("key").toString());
						screen.putExtra("tok", noti);
						startActivity(screen);
					}
				});
				linear_share.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						shareURL = "Check out this Story posted in Lively!".concat("\n\n".concat("https://lively-stories.vercel.app/posts/")).concat(_data.get((int)_position).get("key").toString());
						Intent i = new Intent(android.content.Intent.ACTION_SEND); i.setType("text/plain"); i.putExtra(android.content.Intent.EXTRA_TEXT, shareURL); startActivity(Intent.createChooser(i,"Share With "));
					}
				});
			} else {
				linear1.setVisibility(View.GONE);
			}
			title.setSelected(true);
			linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFFFAFAFA));
			_set_radius_with_ripple(linear_like, "#FFFFFF", 30, 0, "#FFFFFF", true);
			_set_radius_with_ripple(linear_comments, "#FFFFFF", 30, 0, "#FFFFFF", true);
			_set_radius_with_ripple(linear_share, "#FFFFFF", 30, 0, "#FFFFFF", true);
			title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 0);
			name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 0);
			date.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 0);
			story.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 0);
			textview_likes.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 0);
			textview_comments.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 0);
			textview_share.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 0);
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins_medium.ttf"), 0);
			
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