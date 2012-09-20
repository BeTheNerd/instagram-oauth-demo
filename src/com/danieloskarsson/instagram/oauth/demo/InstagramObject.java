package com.danieloskarsson.instagram.oauth.demo;

import java.net.URL;
import java.util.Date;

/**
 * @author Daniel Oskarsson (daniel.oskarson@gmail.com)
 */
class InstagramObject {

	private double latitude;
	private double longitude;
	private String filterName;
	private Date createdTime = new Date();
	private URL publicLink;
	private int numberOfLikes;
	private URL imageLink;
	private String caption;
	private String username;
	private String fullName;

	InstagramObject(double latitude, double longitude, String filterName, String createdTime, URL publicLink,
			int numberOfLikes, URL imageLink, String caption, String username, String fullName) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.filterName = filterName;
		this.createdTime.setTime(Long.valueOf(createdTime)*1000);
		this.publicLink = publicLink;
		this.numberOfLikes = numberOfLikes;
		this.imageLink = imageLink;
		this.caption = caption;
		this.username = username;
		this.fullName = fullName;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getFilterName() {
		return filterName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public URL getPublicLink() {
		return publicLink;
	}

	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	public URL getImageLink() {
		return imageLink;
	}

	public String getCaption() {
		return caption;
	}

	public String getUsername() {
		return username;
	}

	public String getFullName() {
		return fullName;
	}

	@Override
	public String toString() {
		return "InstagramObject [latitude=" + latitude + ", longitude=" + longitude + ", filterName=" + filterName
				+ ", createdTime=" + createdTime + ", publicLink=" + publicLink + ", numberOfLikes=" + numberOfLikes
				+ ", imageLink=" + imageLink + ", caption=" + caption + ", username=" + username + ", fullName="
				+ fullName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caption == null) ? 0 : caption.hashCode());
		result = prime * result + ((createdTime == null) ? 0 : createdTime.hashCode());
		result = prime * result + ((filterName == null) ? 0 : filterName.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((imageLink == null) ? 0 : imageLink.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + numberOfLikes;
		result = prime * result + ((publicLink == null) ? 0 : publicLink.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstagramObject other = (InstagramObject) obj;
		if (caption == null) {
			if (other.caption != null)
				return false;
		} else if (!caption.equals(other.caption))
			return false;
		if (createdTime == null) {
			if (other.createdTime != null)
				return false;
		} else if (!createdTime.equals(other.createdTime))
			return false;
		if (filterName == null) {
			if (other.filterName != null)
				return false;
		} else if (!filterName.equals(other.filterName))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (imageLink == null) {
			if (other.imageLink != null)
				return false;
		} else if (!imageLink.equals(other.imageLink))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (numberOfLikes != other.numberOfLikes)
			return false;
		if (publicLink == null) {
			if (other.publicLink != null)
				return false;
		} else if (!publicLink.equals(other.publicLink))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
