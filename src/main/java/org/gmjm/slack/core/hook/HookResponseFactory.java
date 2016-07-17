package org.gmjm.slack.core.hook;

import org.gmjm.slack.api.hook.HookResponse;

class HookResponseFactory {

	private static class HookResponseImpl implements HookResponse {

		private String message;
		private int statusCode;
		private Status status;
		private Throwable throwable;

		public HookResponseImpl(
			String message,
			int statusCode,
			Status status,
			Throwable throwable
		) {
			this.message = message;
			this.statusCode = statusCode;
			this.status = status;
			this.throwable = throwable;
		}

		@Override
		public String getMessage() {
			return message;
		}

		@Override
		public int getStatusCode() {
			return statusCode;
		}

		@Override
		public Status getStatus() {
			return status;
		}

		@Override
		public Throwable getThrowable() {
			return throwable;
		}
	}

	static HookResponse success(
		String message,
		int statusCode
	) {
		return new HookResponseImpl(message, statusCode, HookResponse.Status.SUCCESS, null);
	}

	static HookResponse fail(
		String message,
		int statusCode,
		Throwable throwable
	) {
		return new HookResponseImpl(message, statusCode, HookResponse.Status.FAILED, throwable);
	}
}
