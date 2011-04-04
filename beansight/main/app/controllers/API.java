package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Category;
import models.Insight;
import models.User;
import models.Vote.State;
import exceptions.CannotVoteTwiceForTheSameInsightException;
import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.mvc.*;

public class API extends Controller {

	// TODO : what if the content evolves between two calls ?
	// Maybe a better solution would be to give the uniqueId of the latest
	// downloaded insight
	/**
	 * Get a list of insights
	 * 
	 * @param from
	 *            index of the first insight to return, default = 0
	 * @param number
	 *            number of insights to return, default = 20
	 * 
	 * @param sort
	 *            possible values : ["updated", "trending", "incoming"]
	 *            (String), default = "updated"
	 * @param category
	 *            id of the category to restrict to, default = null
	 * @param vote
	 *            filter by vote state, possible values : ["all", "voted",
	 *            "non-voted"] (String), default = "all"
	 * @param topic
	 *            String of the topic, default = null
	 * @param closed
	 *            true to return closed insights, default = false
	 * @param created
	 *            true to return only insights created by the user, default =
	 *            false
	 * 
	 * @return [{content, startDate, endDate, category, agreeCount,
	 *         disagreeCount, currentUserVote}, ...]
	 */
	public static void getInsights(@Min(0) Integer from,
			@Min(1) @Max(100) Integer number, String sort, Integer category,
			String vote, String topic, Boolean closed, Boolean created) {

	}

	/**
	 * Get detailed information about a given insight
	 * 
	 * @param insightUniqueId
	 * @return {content, endDate, startDate, category, agreeCount,
	 *         disagreeCount, comments[], tags[] }
	 */
	public static void getInsight(@Required String insightUniqueId) {

	}

	/**
	 * The current user agree a given insight
	 * 
	 * @param insightUniqueId
	 * @return {uniqueId, updatedAgreeCount, updatedDisagreeCount, voteState}
	 */
	public static void agree(@Required String insightUniqueId) {
		vote(insightUniqueId, State.AGREE);
	}

	/**
	 * The current user disagree a given insight
	 * 
	 * @param insightId
	 * @return {uniqueId, updatedAgreeCount, updatedDisagreeCount, voteState}
	 */
	public static void disagree(@Required String insightUniqueId) {
		vote(insightUniqueId, State.DISAGREE);
	}

	/**
	 * Get a list of all the categories
	 * 
	 * @return JSON [{label, id}, ...]
	 */
	public static void getCategories() {
		List<Category> categories = Category.findAll();
		renderJSON(categories);
	}

	private static void vote(String insightUniqueId, State voteState) {
		User currentUser = CurrentUser.getCurrentUser();

		try {
			currentUser.voteToInsight(insightUniqueId, voteState);
		} catch (CannotVoteTwiceForTheSameInsightException e) {
			// its ok, do not show anything
		}

		Insight insight = Insight.findByUniqueId(insightUniqueId);

		Map<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("uniqueId", insight.uniqueId);
		jsonResult.put("updatedAgreeCount", insight.agreeCount);
		jsonResult.put("updatedDisagreeCount", insight.disagreeCount);
		if (voteState.equals(State.AGREE)) {
			jsonResult.put("voteState", "agree");
		} else {
			jsonResult.put("voteState", "disagree");
		}

		renderJSON(jsonResult);
	}

}
