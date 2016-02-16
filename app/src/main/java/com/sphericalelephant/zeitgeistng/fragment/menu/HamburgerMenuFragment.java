package com.sphericalelephant.zeitgeistng.fragment.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sphericalelephant.zeitgeistng.R;

public class HamburgerMenuFragment extends Fragment {
	private RecyclerView recyclerView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_hamburgermenufragment, container, false);
		return recyclerView;
	}
}
