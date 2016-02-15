package com.sphericalelephant.zeitgeistng.data;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Item {
	public enum ItemType {
		@SerializedName("image")
		IMAGE,
		@SerializedName("audio")
		AUDIO,
		@SerializedName("video")
		VIDEO,
		@SerializedName("link")
		LINK;
	}

	private int id;
	private ItemType type;
	private Image image;
	private String source;
	private String title;
	@SerializedName("created_at")
	private String createdAt; // TODO: use Date data type here
	private boolean nsfw;
	@SerializedName("size")
	private int imageSizeBytes;
	private String mimetype;
	private String checksum;
	private String dimensions;
	@SerializedName("upvote_count")
	private int upvotes;
	@SerializedName("dm_user_id")
	private int userId;
	@SerializedName("username")
	private String userName;
	private Tag[] tags;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isNsfw() {
		return nsfw;
	}

	public void setNsfw(boolean nsfw) {
		this.nsfw = nsfw;
	}

	public int getImageSizeBytes() {
		return imageSizeBytes;
	}

	public void setImageSizeBytes(int imageSizeBytes) {
		this.imageSizeBytes = imageSizeBytes;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public int getUpvotes() {
		return upvotes;
	}

	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Tag[] getTags() {
		return tags;
	}

	public void setTags(Tag[] tags) {
		this.tags = tags;
	}

	public static class Image {
		@SerializedName("image")
		private String imageUrl;
		@SerializedName("thumbnail")
		private String thumbnailUrl;

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public String getThumbnailUrl() {
			return thumbnailUrl;
		}

		public void setThumbnailUrl(String thumbnailUrl) {
			this.thumbnailUrl = thumbnailUrl;
		}

		@Override
		public String toString() {
			return "Image{" +
					"imageUrl='" + imageUrl + '\'' +
					", thumbnailUrl='" + thumbnailUrl + '\'' +
					'}';
		}
	}

	@Override
	public String toString() {
		return "Item{" +
				"id=" + id +
				", type=" + type +
				", image=" + image +
				", source='" + source + '\'' +
				", title='" + title + '\'' +
				", createdAt='" + createdAt + '\'' +
				", nsfw=" + nsfw +
				", imageSizeBytes=" + imageSizeBytes +
				", mimetype='" + mimetype + '\'' +
				", checksum='" + checksum + '\'' +
				", dimensions='" + dimensions + '\'' +
				", upvotes=" + upvotes +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", tags=" + Arrays.toString(tags) +
				'}';
	}
}
