package com.jurin_n.kuromoji;

public class Keywords {
	String word;
	int count;

	public Keywords(String word, Integer count) {
		super();
		this.word = word;
		this.count = count;
	}

	public String getWord() {
		return word;
	}

	public int getCount() {
		return count;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
