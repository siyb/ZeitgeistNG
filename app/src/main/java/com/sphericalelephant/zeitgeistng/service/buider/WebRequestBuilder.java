package com.sphericalelephant.zeitgeistng.service.buider;

import android.content.Context;
import android.net.Uri;

import com.sphericalelephant.zeitgeistng.service.processor.ItemsProcessor;

import at.diamonddogs.builder.WebRequestBuilder.ConnectionTimeout;
import at.diamonddogs.builder.WebRequestBuilder.ReadTimeout;
import at.diamonddogs.builder.WebRequestBuilderDefaultConfig;
import at.diamonddogs.data.dataobjects.CacheInformation;
import at.diamonddogs.data.dataobjects.WebRequest;

public class WebRequestBuilder {

	// TODO: make configurable / remove all references to this field
	public static final String URL = "http://zeitgeist.li/";

	private final at.diamonddogs.builder.WebRequestBuilder webRequestBuilder =
			new at.diamonddogs.builder.WebRequestBuilder(new WebRequestBuilderDefaultConfig());

	private at.diamonddogs.builder.WebRequestBuilder newBuilder() {
		return newBuilder(null);
	}

	private at.diamonddogs.builder.WebRequestBuilder newBuilder(WebRequest wr) {
		at.diamonddogs.builder.WebRequestBuilder b;
		if (wr == null) {
			b = webRequestBuilder.newWebRequest();
		} else {
			b = webRequestBuilder.newWebRequest(wr);
		}
		//	wr.addHeaderField("X-API-Auth", this.email + "|" + this.apiSecret);
		return b
				.setReadTimeout(ReadTimeout.MEDIUM)
				.setConnectionTimeout(ConnectionTimeout.MEDIUM);
	}

	public WebRequest getItemsRequest(Context c, int page) {
		return getItemsRequest(c, page, -1);
	}

	public WebRequest getItemsRequest(Context c, int page, int itemsPerPage) {
		// @formatter:off
		Uri.Builder urlBuilder = Uri.parse(URL).buildUpon();
		urlBuilder.appendQueryParameter("page", String.valueOf(page));
		if (itemsPerPage != -1) urlBuilder.appendQueryParameter("per_page",String.valueOf(itemsPerPage));
		WebRequest wr = newBuilder()
			.setUrl(urlBuilder.build())
			.setType(WebRequest.Type.GET)
			.withProcessorId(ItemsProcessor.ID)
			.setCacheTime(CacheInformation.CACHE_NO)
			.setReadTimeout(ReadTimeout.MEDIUM)
		.getWebRequest();
		wr.setAppendHeader(true);
		wr.addHeaderField("Accept", "application/json");
		// @formatter:on
		return wr;
	}

	public WebRequest getItemRequest(Context c, int id) {
		// @formatter:off
		String url = Uri.parse(URL).buildUpon().appendPath(String.valueOf(id)).build().toString();
		WebRequest wr = newBuilder()
			.setUrl(url)
			.setType(WebRequest.Type.GET)
			.withProcessorId(ItemsProcessor.ID)
			.setCacheTime(CacheInformation.CACHE_NO)
			.setReadTimeout(ReadTimeout.MEDIUM)
		.getWebRequest();
		wr.setAppendHeader(true);
		wr.addHeaderField("Accept", "application/json");
		// @formatter:on
		return wr;
	}
}
