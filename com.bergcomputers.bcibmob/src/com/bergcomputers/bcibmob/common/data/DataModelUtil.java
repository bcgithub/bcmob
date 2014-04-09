package com.bergcomputers.bcibmob.common.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.bergcomputers.bcibmob.common.Util;
import com.bergcomputers.bcibmob.common.activity.ContextUtil;

public final class DataModelUtil {
	public static final String DATAEN = "dataen";

	public static final String STATUS = "status";
	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_ERROR = "error";
	public static final String ERROR_CODE = "code";
	public static final String ERROR = "error";
	public static final String ERROR_DETAILS = "error_details";
	public static final String DOMAIN = "domain";

	public static final String RESPONSE = "response";


	public static final String JUNK_DATA = "junk_data";

	private DataModelUtil() {
	}

	/**
	 * @param pJson
	 *            json object
	 * @return error code or -1 for success
	 * @throws JSONException
	 */
	public static int handleJSONResponse(String pJSONString) {
		try {
			JSONObject jsonObject = new JSONObject(pJSONString);
			String status = jsonObject.getString(STATUS);
			if (status.equals(STATUS_SUCCESS)) {
				return -1;
			}
			// status error
			return jsonObject.getInt(ERROR_CODE);
		} catch (JSONException e) {
			// unable to read the status
			return 0;
		}
	}

	/**
	 * Build the list of meetings from the received json in the following format
	 * {"response":
	 *
	 * {"last_update":"1365426163", "meetings":[
	 * {"id":"464","name":"2013 Texas HOSA SLC "
	 * ,"code":"HOSA2013","startDate":1365552000000
	 * ,"endDate":1365811200000,"isFeatured"
	 * :"0","image":"2530fa65c67f628021c287e828a11183.jpg"
	 * ,"address_details":"5600 Seawall Blvd\nGalveston TX 77551"},
	 * {"id":"219","name"
	 * :"2013 Food & Nutrition Conference & Exhibition","code":
	 * "TAND2013","startDate"
	 * :1365638400000,"endDate":1365811200000,"isFeatured":
	 * "0","image":"450cf4aa57b0905c7b6dec4eff409d54.jpg","address_details":
	 * "9721 Arboretum Boulevard  Austin, TX 78759\r\n(512) 343-2626"}, ] },
	 * "status":"success", "junkData":"OmTTXVpUC6EreggOONrG"}
	 *
	 *
	 * @param pJSONString
	 *            the json to parse
	 * @param featuredFirst
	 *            flag to order by featured property also
	 * @return the list of meetings sorted ascending over the featured flag and
	 *         start date
	 *
	 * @author ionutz
	 */
	/*public static List<Meeting> buildMeetingList(String pJSONString,
			final boolean featuredFirst) {
		List<Meeting> meetingsList = null;

		try {
			meetingsList = new ArrayList<Meeting>();

			JSONObject jsonModel = new JSONObject(pJSONString);
			JSONObject jsonResponse = jsonModel.getJSONObject(RESPONSE);
			JSONArray jsonMeetings = jsonResponse.getJSONArray(MEETINGS);
			JSONObject jsonMeeting = null;
			Meeting meeting = null;

			for (int idx = 0; idx < jsonMeetings.length(); idx++) {
				jsonMeeting = (JSONObject) jsonMeetings.get(idx);

				meeting = new Meeting();
				meeting.setId(jsonMeeting.getLong(ID));
				meeting.setName(getNonMandatoryStringProperty(jsonMeeting, NAME));
				meeting.setCode(jsonMeeting.getString(CODE));
				meeting.setStartDate(getDateFromMillisProperty(jsonMeeting,
						START_DATE));
				meeting.setEndDate(getDateFromMillisProperty(jsonMeeting,
						END_DATE));
				meeting.setFeatured(Util.getBoolean(jsonMeeting
						.getInt(FEATURED)));
				meeting.setImage(getNonMandatoryStringProperty(jsonMeeting,
						IMAGE));
				meeting.setAddress(buildAddress(getNonMandatoryProperty(
						jsonMeeting, ADDRESS)));
				meetingsList.add(meeting);
			}

			// sort the list over the featured flag and start date ascending
			Collections.sort(meetingsList, new Comparator<Meeting>() {

				@Override
				public int compare(Meeting lhs, Meeting rhs) {
					int result = 0;
					if (featuredFirst) {
						result = Boolean.valueOf(lhs.isFeatured()).compareTo(
								Boolean.valueOf(rhs.isFeatured()));
					}
					if (0 == result) {
						result = lhs.getStartDate().compareTo(
								rhs.getStartDate());
					}
					return -result;
				}
			});

			return meetingsList;
		} catch (JSONException e) {
			// can not parse the meeting
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Meeting buildMeeting(String pJSONString, Context context) {
		try {
			Meeting meeting = new Meeting();

			JSONObject jsonModel = new JSONObject(pJSONString);
			JSONObject jsonResponse = jsonModel.getJSONObject(RESPONSE);
			JSONObject jsonMeeting = jsonResponse.getJSONObject(MEETING);

			final int gaSampleRate = jsonMeeting.getInt(GA_SAMPLE_RATE);
			ContextUtil.saveGASampleRate(context, gaSampleRate);

			meeting.setId(jsonMeeting.getLong(ID));
			meeting.setOrganizedBy(getNonMandatoryStringProperty(jsonMeeting,
					ORGANIZED_BY));
			meeting.setName(getNonMandatoryStringProperty(jsonMeeting, NAME));
			meeting.setCode(jsonMeeting.getString(CODE));
			meeting.setImage(getNonMandatoryStringProperty(jsonMeeting, IMAGE));
			meeting.setTwitterTopicId(getNonMandatoryStringProperty(
					jsonMeeting, TWITTER_TOPIC_ID));
			meeting.setFlickrSetId(getNonMandatoryStringProperty(jsonMeeting,
					FLICKR_SET_ID));
			meeting.setFlickrSetURL(getNonMandatoryStringProperty(jsonMeeting,
					FLICKR_SET_URL));
			meeting.setFlickrAccessToken(getNonMandatoryStringProperty(
					jsonMeeting, FLICKR_ACCESS_TOKEN));
			meeting.setFlickrAccessTokenSecret(getNonMandatoryStringProperty(
					jsonMeeting, FLICKR_ACCESS_TOKEN_SECRET));
			meeting.setAllowRatings(Util.getBoolean(jsonMeeting
					.getInt(ALLOW_RATINGS)));
			meeting.setAllowCommercials(Util.getBoolean(jsonMeeting
					.getInt(ALLOW_COMMERCIALS)));
			meeting.setMeetingDescription(getNonMandatoryStringProperty(
					jsonMeeting, MEETING_DESC));
			meeting.setStartDate(getDateFromMillisProperty(jsonMeeting,
					START_DATE));
			meeting.setUpdatedAt(getDateFromMillisProperty(jsonMeeting,
					UPDATED_AT));

			meeting.setAddress(buildAddress(getNonMandatoryProperty(
					jsonMeeting, ADDRESS)));

			meeting.setMaterials(buildMaterials(jsonMeeting
					.getJSONArray(MATERIALS)));

			meeting.setCategories(buildCategories(jsonMeeting
					.getJSONArray(CATEGORIES)));

			JSONArray jsonMeetyingDays = jsonMeeting.getJSONArray(DAYS);
			int size = jsonMeetyingDays.length();
			MeetingDay meetingDay;
			List<MeetingDay> meetingDays = new ArrayList<MeetingDay>();
			for (int i = 0; i < size; i++) {
				meetingDay = buildMeetingDay(jsonMeetyingDays.getJSONObject(i));
				meetingDays.add(meetingDay);
			}
			Collections.sort(meetingDays);
			meeting.setDays(meetingDays);

			return meeting;
		} catch (JSONException e) {
			// can not parse the meeting
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}*/



	private static String getNonMandatoryStringProperty(JSONObject json,
			String property) {
		try {
			String result = json.getString(property);
			if ("null".equals(result)) {
				return null;
			}
			return result;
		} catch (JSONException e) {
			return null;
		}
	}

	private static JSONObject getNonMandatoryProperty(JSONObject json,
			String property) {
		try {
			return json.getJSONObject(property);
		} catch (JSONException e) {
			return null;
		}
	}

	private static Integer getNonMandatoryIntProperty(JSONObject json,
			String property) {
		try {
			return json.getInt(property);
		} catch (JSONException e) {
			return null;
		}
	}

	private static Long getNonMandatoryLongProperty(JSONObject json,
			String property) {
		try {
			return json.getLong(property);
		} catch (JSONException e) {
			return null;
		}
	}

	private static Double getNonMandatoryDoubleProperty(JSONObject json,
			String property) {
		try {
			return json.getDouble(property);
		} catch (JSONException e) {
			return null;
		}
	}

	private static Date getDateFromMillisProperty(JSONObject json,
			String property) {
		try {
			long millis = json.getLong(property);
			return Util.getDate(millis);
		} catch (JSONException e) {
			return null;
		}
	}




}
