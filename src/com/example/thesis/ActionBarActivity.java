package com.example.thesis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import com.example.thesis.R;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

@SuppressLint("ResourceAsColor")
public class ActionBarActivity extends FragmentActivity implements
		View.OnClickListener {

	private ResideMenu resideMenu;

	private ResideMenuItem itemHome;
	private ResideMenuItem itemFavorite;
	private ResideMenuItem itemIrregularVerbs;
	private ResideMenuItem itemGame;
	private ResideMenuItem itemExtraFunctions;
	private ResideMenuItem itemDashBoard;
	private ResideMenuItem itemHelp;
	private ResideMenuItem itemAboutUs;

	// Fragment
	private HomeFragment homeFragment;
	private FavoritesFragment favoriteFragment;
	private IrregularVerbsFragment irregularVerbFragment;
	private GameFragment gameFragment;
	private ExtraFunctionsFragment extraFunctionsFragment;
	private DashBoardFragment dashboardFragment;
	private HelpFragment helpFragment;
	private AboutUsFragment aboutUsFragment;
	
	// Get input method
	private static InputMethodManager imm;
	
	// Image View
	ImageView imageView1;
	
	// Button
	Button clickableSearchIcon;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_action_bar);

		// Set ID
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		clickableSearchIcon = (Button) findViewById(R.id.clickableSearchIcon);
		
		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		setUpMenu();
		changeFragment(homeFragment);
		itemHome.setBackgroundColor(Color.argb(33, 255, 255, 255));
	}

	private void setUpMenu() {
		// Initial fragment
		homeFragment = new HomeFragment();
		favoriteFragment = new FavoritesFragment();
		irregularVerbFragment = new IrregularVerbsFragment();
		gameFragment = new GameFragment();
		extraFunctionsFragment = new ExtraFunctionsFragment();
		dashboardFragment = new DashBoardFragment();
		helpFragment = new HelpFragment();
		aboutUsFragment = new AboutUsFragment();
		
		// Attach to current activity;
		resideMenu = new ResideMenu(getApplicationContext());
		// Set background of menu
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);
		// Valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);

		// Create menu items;
		itemHome = new ResideMenuItem(getApplicationContext(),
				R.drawable.icon_home, "Home");
		itemFavorite = new ResideMenuItem(getApplicationContext(),
				R.drawable.icon_favorites, "Favorites");
		itemIrregularVerbs = new ResideMenuItem(getApplicationContext(),
				R.drawable.icon_word, "Irregular Verbs");
		itemGame = new ResideMenuItem(getApplicationContext(),
				R.drawable.icon_game, "Game");
		itemExtraFunctions = new ResideMenuItem(getApplicationContext(),
				R.drawable.icon_plus, "More");
		itemDashBoard = new ResideMenuItem(getApplicationContext(),
				R.drawable.icon_dashboard, "Dash Board");
		itemHelp = new ResideMenuItem(getApplicationContext(),
				R.drawable.icon_help, "Help");
		itemAboutUs = new ResideMenuItem(getApplicationContext(),
				R.drawable.icon_about_us, "About Us");

		itemHome.setOnClickListener(this);
		itemFavorite.setOnClickListener(this);
		itemIrregularVerbs.setOnClickListener(this);
		itemGame.setOnClickListener(this);
		itemExtraFunctions.setOnClickListener(this);
		itemDashBoard.setOnClickListener(this);
		itemHelp.setOnClickListener(this);
		itemAboutUs.setOnClickListener(this);

		resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemFavorite, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemIrregularVerbs, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemGame, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemExtraFunctions, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemDashBoard, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemHelp, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemAboutUs, ResideMenu.DIRECTION_LEFT);

		// You can disable a direction by setting ->
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		findViewById(R.id.clickableDrawerIcon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
		clickableSearchIcon.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						changeFragment(homeFragment);
						
						imageView1.setVisibility(View.INVISIBLE);
						
						clickableSearchIcon.setVisibility(View.INVISIBLE);
					}
				});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View view) {

		itemHome.setBackgroundColor(android.R.color.transparent);
		itemFavorite.setBackgroundColor(android.R.color.transparent);
		itemIrregularVerbs.setBackgroundColor(android.R.color.transparent);
		itemGame.setBackgroundColor(android.R.color.transparent);
		itemExtraFunctions.setBackgroundColor(android.R.color.transparent);
		itemDashBoard.setBackgroundColor(android.R.color.transparent);
		itemHelp.setBackgroundColor(android.R.color.transparent);
		itemAboutUs.setBackgroundColor(android.R.color.transparent);
		
		if (view == itemHome) {
			changeFragment(homeFragment);
			itemHome.setBackgroundColor(Color.argb(33, 255, 255, 255));
			imageView1.setVisibility(View.INVISIBLE);
			clickableSearchIcon.setVisibility(View.INVISIBLE);
		} else if (view == itemDashBoard) {
			changeFragment(dashboardFragment);
			itemDashBoard.setBackgroundColor(Color.argb(33, 255, 255, 255));
			imageView1.setVisibility(View.VISIBLE);
			clickableSearchIcon.setVisibility(View.VISIBLE);
		} else if (view == itemHelp) {
			changeFragment(helpFragment);
			itemHelp.setBackgroundColor(Color.argb(33, 255, 255, 255));
			imageView1.setVisibility(View.VISIBLE);
			clickableSearchIcon.setVisibility(View.VISIBLE);
		} else if (view == itemAboutUs) {
			changeFragment(aboutUsFragment);
			itemAboutUs.setBackgroundColor(Color.argb(33, 255, 255, 255));
			imageView1.setVisibility(View.VISIBLE);
			clickableSearchIcon.setVisibility(View.VISIBLE);
		}  else if (view == itemIrregularVerbs) {
			changeFragment(irregularVerbFragment);
			itemIrregularVerbs.setBackgroundColor(Color.argb(33, 255, 255, 255));
			imageView1.setVisibility(View.VISIBLE);
			clickableSearchIcon.setVisibility(View.VISIBLE);
		} else if (view == itemGame) {
			changeFragment(gameFragment);
			itemGame.setBackgroundColor(Color.argb(33, 255, 255, 255));
			imageView1.setVisibility(View.VISIBLE);
			clickableSearchIcon.setVisibility(View.VISIBLE);
		}  else if (view == itemFavorite) {
			changeFragment(favoriteFragment);
			itemFavorite.setBackgroundColor(Color.argb(33, 255, 255, 255));
			imageView1.setVisibility(View.VISIBLE);
			clickableSearchIcon.setVisibility(View.VISIBLE);
		} else if (view == itemExtraFunctions) {
			changeFragment(extraFunctionsFragment);
			itemExtraFunctions.setBackgroundColor(Color.argb(33, 255, 255, 255));
			imageView1.setVisibility(View.VISIBLE);
			clickableSearchIcon.setVisibility(View.VISIBLE);
		}

		resideMenu.closeMenu();
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			// Toast.makeText(mContext, "Menu is opened!",
			// Toast.LENGTH_SHORT).show();
			try {
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void closeMenu() {
			// Toast.makeText(mContext, "Menu is closed!",
			// Toast.LENGTH_SHORT).show();
		}
	};

	private void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	// What good method is to access resideMenu？
	public ResideMenu getResideMenu() {
		return resideMenu;
	}
}
