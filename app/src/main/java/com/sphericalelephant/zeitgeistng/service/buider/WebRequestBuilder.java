package com.sphericalelephant.zeitgeistng.service.buider;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.sphericalelephant.zeitgeistng.fragment.preference.PreferenceFacade;
import com.sphericalelephant.zeitgeistng.service.processor.ItemsProcessor;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.util.ArrayList;

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
		WebRequest wr = getNewItemRequest(c, tags, announce);
		addUploadUrl(wr, remoteUrls);
		return wr;
	}

	public WebRequest getNewItemRequest(Context c, String[] tags, boolean announce, File[] files) {
		WebRequest wr = getNewItemRequest(c, tags, announce);
		addUploadFiles(wr, files);
		return wr;
	}

	private WebRequest getNewItemRequest(Context c, String[] tags, boolean announce) {
		Uri.Builder urlBuilder = PreferenceFacade.getInstance().getHostAddress(context).buildUpon();
		Uri u = urlBuilder.appendPath(URL_PATH_NEW).build();
		WebRequest wr = newBuilder()
				.setUrl(u)
				.setType(WebRequest.Type.POST)
				.setCacheTime(CacheInformation.CACHE_NO)
				.setReadTimeout(ReadTimeout.LONG)
				.setConnectionTimeout(ConnectionTimeout.LONG)
				.getWebRequest();
		addAnnounce(wr, announce);
		addTags(wr, tags);
		return wr;
	}

	private boolean addAnnounce(WebRequest wr, boolean announce) {
		MultipartEntity entity = getMultiPartEntity(wr);
		try {
			entity.addPart("announce", new StringBody(String.valueOf(announce)));
			return true;
		} catch (Throwable tr) {
			return false;
		}
	}

	private boolean addUploadUrl(WebRequest wr, String[] urls) {
		MultipartEntity entity = getMultiPartEntity(wr);
		for (String url : urls) {
			try {
				entity.addPart("remote_url[]", new StringBody(url));
			} catch (Throwable tr) {
				return false;
			}
		}
		return true;

	}

	private boolean addUploadFiles(WebRequest wr, File[] files) {
		MultipartEntity entity = getMultiPartEntity(wr);
		for (File file : files) {
			entity.addPart("image_upload[]", new FileBody(file));
		}
		wr.setHttpEntity(entity);
		return true;

	}

	private boolean addTags(WebRequest wr, String[] tags) {
		MultipartEntity entity = getMultiPartEntity(wr);
		try {
			entity.addPart("tags", new StringBody(TextUtils.join(" ", tags)));
		} catch (Throwable tr) {
			return false;
		}
		return true;
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
