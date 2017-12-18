package com.jurin_n.kuromoji;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

public class KuromojiExample {
	public static void main(String[] args) throws IOException {
		Tokenizer tokenizer = new Tokenizer();
		Map<String, Integer> tokenMap = new HashMap<>();
		String dataBase = System.getProperty("user.dir") + File.separator + "data" + File.separator;
		Path path = Paths.get(dataBase + "tweets.csv");
		try (ICsvBeanReader beanReader = new CsvBeanReader(Files.newBufferedReader(path),
				CsvPreference.STANDARD_PREFERENCE)) {
			String[] header = new String[] { "tweetId", null, null, null, null, "text", null, null, null, null };
			final CellProcessor[] processors = getProcessors();
			Tweet tweet;
			while ((tweet = beanReader.read(Tweet.class, header, processors)) != null) {

				putTokenMap(tokenizer, tweet.getText(), tokenMap);
			}
		}
		Path outPath = Paths.get(dataBase + "keywords.csv");
		try (ICsvBeanWriter beanWriter = new CsvBeanWriter(Files.newBufferedWriter(outPath),
				CsvPreference.STANDARD_PREFERENCE)) {
			// write the header
			String[] header = new String[] { "word", "count" };
			final CellProcessor[] processors = new CellProcessor[] { new NotNull(), new NotNull() };
			beanWriter.writeHeader(header);

			// write the beans
			for (String token : tokenMap.keySet()) {
				beanWriter.write(new Keywords(token, tokenMap.get(token)), header, processors);
			}
		}
	}

	private static CellProcessor[] getProcessors() {
		return new CellProcessor[] { new NotNull(), null, null, null, null, new NotNull(), null, null, null, null

		};
	}

	static void putTokenMap(Tokenizer tokenizer, String text, Map<String, Integer> tokenMap) {
		List<Token> tokens = tokenizer.tokenize(text);
		for (Token token : tokens) {
			String baseForm = token.getBaseForm();
			String partOfSpeechLevel1 = token.getPartOfSpeechLevel1();
			if (partOfSpeechLevel1.equals("名詞")) {
				if (tokenMap.containsKey(baseForm)) {
					tokenMap.put(baseForm, tokenMap.get(baseForm) + 1);
				} else {
					tokenMap.put(baseForm, 1);
				}
			}
		}
	}
}
