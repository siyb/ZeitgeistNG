package com.sphericalelephant.zeitgeistng.service.buider;

import android.content.Context;
import android.net.Uri;

import com.sphericalelephant.zeitgeistng.fragment.preference.PreferenceFacade;
import com.sphericalelephant.zeitgeistng.service.processor.ItemsProcessor;

import org.apache.http.entity.mime.MultipartEntity;

import java.io.File;

import at.diamonddogs.builder.WebRequestBuilder.ConnectionTimeout;
import at.diamonddogs.builder.WebRequestBuilder.ReadTimeout;
import at.diamonddogs.builder.WebRequestBuilderDefaultConfig;
import at.diamonddogs.data.dataobjects.CacheInformation;
import at.diamonddogs.data.dataobjects.WebRequest;

public class WebRequestBuilder {

	private final at.diamonddogs.builder.WebRequestBuilder webRequestBuilder =
			new at.diamonddogs.builder.WebRequestBuilder(new WebRequestBuilderDefaultConfig());
	private final Context context;

	private final String URL_PATH_NEW = "new";

	public WebRequestBuilder(Context context) {
		this.context = context;
	}

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

	public WebRequest getNewItemRequest(Context c, String[] tags, boolean announce, String[] remoteUrls) {
		return null;
	}

	public WebRequest getNewItemRequest(Context c, String[] tags, boolean announce, File[] files) {
		return null;
	}

	public WebRequest getItemsRequest(Context c, int page) {
		return getItemsRequest(c, page, -1);
	}

	public WebRequest getItemsRequest(Context c, int page, int itemsPerPage) {
		// @formatter:off
		Uri.Builder urlBuilder = PreferenceFacade.getInstance().getHostAddress(context).buildUpon();
		urlBuilder.appendQueryParameter("page", String.valueOf(page));
		if (itemsPerPage != -1) urlBuilder.appendQueryParameter("per_page", String.valueOf(itemsPerPage));
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
		String url = PreferenceFacade.getInstance().getHostAddress(context).buildUpon().appendPath(String.valueOf(id)).build().toString();
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

	private MultipartEntity getMultiPartEntity(WebRequest wr) {
		MultipartEntity m = (MultipartEntity) wr.getHttpEntity();
		if (m == null) {
			m = new MultipartEntity();
			wr.setHttpEntity(m);
		}
		return m;
	}
}
