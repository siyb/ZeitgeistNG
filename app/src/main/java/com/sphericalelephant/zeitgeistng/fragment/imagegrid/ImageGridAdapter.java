package com.sphericalelephant.zeitgeistng.fragment.imagegrid;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sphericalelephant.zeitgeistng.R;
import com.sphericalelephant.zeitgeistng.data.Item;
import com.sphericalelephant.zeitgeistng.service.buider.WebRequestBuilder;
import com.sphericalelephant.zeitgeistng.view.SquaredImageView;
import com.squareup.picasso.Picasso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ViewHolder> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageGridAdapter.class.getSimpleName());

	private ArrayList<Item> items = new ArrayList<>();
	private Context context;


	public ImageGridAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(context).inflate(R.layout.adapter_imagegridadapter, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Item i = items.get(position);
		Item.Image image = i.getImage();

		if (i.getType() == Item.ItemType.IMAGE || i.getType() == Item.ItemType.VIDEO) {
			String url = Uri.parse(WebRequestBuilder.URL).buildUpon().appendPath(image.getThumbnailUrl()).build().toString();
			LOGGER.info("Loading image with url: " + url);
			Picasso.with(context).load(url).into(holder.imageView);
		}
		// TODO: handle video, audio and link items
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public void addAll(Item[] items) {
		this.items.addAll(Arrays.asList(items));
		notifyDataSetChanged();
	}

	protected static final class ViewHolder extends RecyclerView.ViewHolder {
		private SquaredImageView imageView;

		public ViewHolder(View itemView) {
			super(itemView);
			imageView = (SquaredImageView) itemView;
		}
	}
}
