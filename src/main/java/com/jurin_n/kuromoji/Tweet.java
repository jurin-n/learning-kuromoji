package com.jurin_n.kuromoji;

public class Tweet {
	// tweet_id
	// in_reply_to_status_id
	// in_reply_to_user_id
	// timestamp
	// source
	// text
	// retweeted_status_id
	// retweeted_status_user_id
	// retweeted_status_timestamp
	// expanded_urls
	String tweetId;
	String timestamp;
	String text;

	public String getTweetId() {
		return tweetId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getText() {
		return text;
	}

	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setText(String text) {
		this.text = text;
	}
}
