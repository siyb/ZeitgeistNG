package com.sphericalelephant.zeitgeistng.fragment.imagegrid;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sphericalelephant.zeitgeistng.R;
import com.sphericalelephant.zeitgeistng.data.Item;
import com.sphericalelephant.zeitgeistng.data.Items;
import com.sphericalelephant.zeitgeistng.fragment.itemdetail.AbstractItemDetailFragment;
import com.sphericalelephant.zeitgeistng.service.buider.WebRequestBuilder;
import com.sphericalelephant.zeitgeistng.service.processor.ItemsProcessor;

import at.diamonddogs.data.dataobjects.WebRequest;
import at.diamonddogs.service.processor.ServiceProcessorMessageUtil;
import at.diamonddogs.ui.fragment.HttpFragment;

public class ImageGridFragment extends HttpFragment implements ImageGridAdapter.OnImageClickedListener {
	// TODO: maybe make this configurable
	private static final int SPAN_COUNT = 5;
	private static final int ITEMS_PER_PAGE = 100;

	private RecyclerView recyclerView;
	private GridLayoutManager gridLayoutManager;

	private ImageGridAdapter adapter;

	// TODO: save in instancestate or something
	private int currentPage = 0;
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
		recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_imagegridfragment, container, false);
		gridLayoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
		recyclerView.setLayoutManager(gridLayoutManager);
		recyclerView.setAdapter(adapter);
		return recyclerView;
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
		});
	}

	private void loadItems() {
		loading = true;
		WebRequestBuilder wrb = new WebRequestBuilder(getContext());
		WebRequest wr = wrb.getItemsRequest(getContext(), currentPage, ITEMS_PER_PAGE);
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
