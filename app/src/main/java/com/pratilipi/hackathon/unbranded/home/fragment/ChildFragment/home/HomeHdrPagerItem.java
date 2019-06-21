package com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home;

public class HomeHdrPagerItem {
	private String title, thumbnailUrl;
	private String subTitle;
	private String header;
	private String id;
//	private String header;
//	private ArrayList<String> reporter;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HomeHdrPagerItem() {
	}

	public HomeHdrPagerItem(String name, String thumbnailUrl, String subTitle, String header
	) {
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.subTitle = subTitle;
		this.header = header;
	//	this.reporter = reporter;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

/*
	public ArrayList<String> getReporter() {
		return reporter;
	}
*/

/*
	public void setReporter(ArrayList<String> reporter) {
		this.reporter = reporter;
	}
*/

}
