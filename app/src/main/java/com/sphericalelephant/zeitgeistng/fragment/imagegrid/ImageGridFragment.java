package com.sphericalelephant.zeitgeistng.fragment.imagegrid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;
import com.sphericalelephant.zeitgeistng.R;
import com.sphericalelephant.zeitgeistng.data.Item;
import com.sphericalelephant.zeitgeistng.data.Items;
import com.sphericalelephant.zeitgeistng.fragment.itemdetail.AbstractItemDetailFragment;
import com.sphericalelephant.zeitgeistng.fragment.preference.PreferenceFacade;
import com.sphericalelephant.zeitgeistng.service.buider.WebRequestBuilder;
import com.sphericalelephant.zeitgeistng.service.processor.ItemsProcessor;

import java.util.List;

import at.diamonddogs.data.dataobjects.WebRequest;
import at.diamonddogs.service.processor.ServiceProcessorMessageUtil;
import at.diamonddogs.ui.fragment.HttpFragment;

public class ImageGridFragment extends HttpFragment implements ImageGridAdapter.OnImageClickedListener {
	private static final int INTENT_REQUESTCODE_IMAGEPICK = 1;
	private static final int PICKER_MAX_IMAGES = 5;// TODO: maybe this should be configurable by the user


	private RecyclerView recyclerView;
	private GridLayoutManager gridLayoutManager;

	private ImageGridAdapter adapter;

	// TODO: save in instancestate or something
	private int currentPage = 1;
	private boolean loading = true;

	public static ImageGridFragment newInstance() {
		return new ImageGridFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new ImageGridAdapter(getContext());
		adapter.setOnImageClickedListener(this);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_imagegridfragment, container, false);
		recyclerView = (RecyclerView) v.findViewById(R.id.fragment_imagegridfragment_rv_imagegrid);
		v.findViewById(R.id.fragment_imagegridfragment_fab_addimage).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FishBun.with(ImageGridFragment.this)
						.setRequestCode(INTENT_REQUESTCODE_IMAGEPICK)
						.setCamera(true)
						.setPickerCount(PICKER_MAX_IMAGES)
						.setActionBarColor(
								getResources().getColor(R.color.colorPrimary),
								getResources().getColor(R.color.colorPrimaryDark)
						)
						.startAlbum();
			}
		});
		gridLayoutManager = new GridLayoutManager(getContext(), PreferenceFacade.getInstance().getColumns(getContext()));
		recyclerView.setLayoutManager(gridLayoutManager);
		recyclerView.setAdapter(adapter);
		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == INTENT_REQUESTCODE_IMAGEPICK && resultCode == Activity.RESULT_OK) {
			List<String> paths = data.getStringArrayListExtra(Define.INTENT_PATH);
			WebRequestBuilder wrb = new WebRequestBuilder(getContext());
			assister.runWebRequest(new Handler.Callback() {
				@Override
				public boolean handleMessage(Message msg) {
					return false;
				}
			}, wrb.getNewItemRequest(null, false, paths.toArray(new String[paths.size()])), new ItemsProcessor());
		}
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

		loadItems();

		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

			int pastVisiblesItems, visibleItemCount, totalItemCount;

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				if (dy > 0) {
					visibleItemCount = gridLayoutManager.getChildCount();
					totalItemCount = gridLayoutManager.getItemCount();
					pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();

					if (!loading) {
						if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
							loadItems();
						}
					}
				}
			}

			// TODO: hide floating view here as well
		});
	}

	private void loadItems() {
		loading = true;
		WebRequestBuilder wrb = new WebRequestBuilder(getContext());
		WebRequest wr = wrb.getItemsRequest(getContext(), currentPage, PreferenceFacade.getInstance().getItemsPerPage(getContext()));
		assister.runWebRequest(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				if (ServiceProcessorMessageUtil.isSuccessful(msg)) {
					Items newItems = ServiceProcessorMessageUtil.getCastedPayLoad(msg);
					adapter.addAll(newItems.getItems());
					currentPage++;
					loading = false;
					return true;
				}
				return false;
			}
		}, wr, new ItemsProcessor());
	}

	@Override
	public void onImageClicked(Item item) {
		AbstractItemDetailFragment.showWithItem(getActivity().getSupportFragmentManager(), "", item);
	}
}
