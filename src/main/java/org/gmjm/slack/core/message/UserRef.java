package org.gmjm.slack.core.message;

public class UserRef {

	private static final String USER_REF_TEMPLATE = "<@%s|%s>";

	private final String id;
	private final String username;

	private final String userRef;

	public UserRef(String id, String username) {
		this.id = id;
		this.username = username;

		this.userRef = String.format(USER_REF_TEMPLATE, id, username);
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	/**
	 * @return message friendly format of user.  Allows users to be notified in slack when mentioned.
	 */
	public String getUserRef() {
		return userRef;
	}
}
