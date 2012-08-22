package za.co.thumbtribe.sandbox.twitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class Twitter4JDataObjectFactoryTests {

	Twitter twitter;

	@Before
	public void init() {
		Configuration configuration = new ConfigurationBuilder().setJSONStoreEnabled(true).setGZIPEnabled(true).build();
		twitter = new TwitterFactory(configuration).getInstance();
	}

	@Test
	public void testDataObjectFactoryForWeeklyTrends() {
		try {
			List<Trends> trendsList = twitter.getWeeklyTrends();
			System.out.println("trendsList.size() = " + trendsList.size());
			Set<String> results = new HashSet<String>();
			for (Trends trends : trendsList) {
				String json = DataObjectFactory.getRawJSON(trends);
				if (json != null) {
					results.add(json);
				}
			}
			assertFalse("Results expected!", results.isEmpty());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDataObjectFactoryForDailyTrends() {
		try {
			List<Trends> trendsList = twitter.getDailyTrends();
			System.out.println("trendList.size() = " + trendsList.size());
			Set<String> results = new HashSet<String>();
			for (Trends trends : trendsList) {
				String json = DataObjectFactory.getRawJSON(trends);
				if (json != null) {
					results.add(json);
				}
			}
			assertFalse("Daily Trends expected!", results.isEmpty());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDataObjectFactoryForLocationTrends() {
		try {
			Trends locationTrends = twitter.getLocationTrends(1);
			System.out.println("Getting JSON for locationTrends: " + locationTrends);
			String json = DataObjectFactory.getRawJSON(locationTrends);
			assertNotNull("Non-null string expected here.", json);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}
